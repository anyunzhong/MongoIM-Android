package im.mongo.core.storage;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import net.datafans.android.common.helper.LogHelper;

import java.util.ArrayList;
import java.util.List;

import im.mongo.core.model.Conversation;
import im.mongo.core.model.Message;
import im.mongo.core.model.content.MessageContent;

/**
 * Created by zhonganyun on 15/8/5.
 */
public class MessageStorageService extends StorageService {


    private static MessageStorageService service;

    private MessageStorageService() {
    }

    public synchronized static MessageStorageService sharedInstance() {
        if (service == null) service = new MessageStorageService();
        return service;
    }


    public static class TABLE {
        public static final String NAME = "message";
        public static final String CREATE_SQL = "CREATE TABLE message(message_id INTEGER PRIMARY KEY AUTOINCREMENT,target_id VARCHAR (64) NOT NULL,sender_id VARCHAR (64),conversation_type SMALLINT,message_direction SMALLINT,receive_status SMALLINT DEFAULT 0,receive_time INTEGER,send_time INTEGER,send_status SMALLINT DEFAULT 0,content_type VARCHAR (64),content TEXT,extra_column1 INTEGER DEFAULT 0,extra_column2 INTEGER DEFAULT 0,extra_column3 TEXT,extra_column4 TEXT);CREATE INDEX idx_message ON message (target_id, conversation_type, send_time);";

        public static class COLUMN {
            public static final String MESSAGE_ID = "message_id";
            public static final String TARGET_ID = "target_id";
            public static final String SENDER_ID = "sender_id";

            public static final String MESSAGE_DIRECTION = "message_direction";
            public static final String CONVERSATION_TYPE = "conversation_type";

            public static final String RECEIVE_STATUS = "receive_status";
            public static final String RECEIVE_TIME = "receive_time";

            public static final String SEND_TIME = "send_time";
            public static final String SEND_STATUS = "send_status";

            public static final String CONTENT_TYPE = "content_type";
            public static final String CONTENT = "content";

            public static final String EXTRA_COLUMN1 = "extra_column1";
            public static final String EXTRA_COLUMN2 = "extra_column2";
            public static final String EXTRA_COLUMN3 = "extra_column3";
            public static final String EXTRA_COLUMN4 = "extra_column4";
        }

    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


    public void insertMessage(Message message) {

        LogHelper.debug("insert: " + TABLE.NAME);

        ContentValues cv = new ContentValues();

        cv.put(TABLE.COLUMN.TARGET_ID, message.getTargetId());
        cv.put(TABLE.COLUMN.SENDER_ID, message.getSenderId());
        cv.put(TABLE.COLUMN.MESSAGE_DIRECTION, message.getDirection().getDirection());
        cv.put(TABLE.COLUMN.CONVERSATION_TYPE, message.getConversationType().getType());
        cv.put(TABLE.COLUMN.RECEIVE_STATUS, message.getReceiveStatus().getFlag());
        cv.put(TABLE.COLUMN.RECEIVE_TIME, message.getReceivedTime());
        cv.put(TABLE.COLUMN.SEND_STATUS, message.getSendStatus().getStatus());
        cv.put(TABLE.COLUMN.SEND_TIME, message.getSentTime());
        cv.put(TABLE.COLUMN.CONTENT_TYPE, message.getContentType());
        cv.put(TABLE.COLUMN.CONTENT, message.getContent().toJson());
        getWritableDatabase().insert(TABLE.NAME, null, cv);
    }


    public List<Message> getMessages(Conversation.Type conversationType, String targetId, int start, int size) {

        if (start == 0) start = Integer.MAX_VALUE;

        List<Message> messages = new ArrayList<>();

        String sql = String.format("SELECT * from %s WHERE conversation_type=%d AND target_id=%s and message_id < %d ORDER BY message_id DESC limit %d", TABLE.NAME, conversationType.getType(), targetId, start, size);
        Cursor c = getReadableDatabase().rawQuery(sql, null);

        while (c.moveToNext()) {
            Message message = new Message();

            message.setMessageId(c.getInt(c.getColumnIndex(TABLE.COLUMN.MESSAGE_ID)));
            message.setTargetId(targetId);
            message.setSenderId(c.getString(c.getColumnIndex(TABLE.COLUMN.SENDER_ID)));

            message.setConversationType(conversationType);
            int direction = c.getInt(c.getColumnIndex(TABLE.COLUMN.MESSAGE_DIRECTION));
            message.setDirection(Message.Direction.valueOf(direction));

            int sendStatus = c.getInt(c.getColumnIndex(TABLE.COLUMN.SEND_STATUS));
            message.setSendStatus(Message.SendStatus.valueOf(sendStatus));
            message.setSentTime(c.getLong(c.getColumnIndex(TABLE.COLUMN.SEND_TIME)));

            int receiveStatus = c.getInt(c.getColumnIndex(TABLE.COLUMN.RECEIVE_STATUS));
            message.setReceiveStatus(new Message.ReceiveStatus(receiveStatus));
            message.setReceivedTime(c.getLong(c.getColumnIndex(TABLE.COLUMN.RECEIVE_TIME)));

            String contentType = c.getString(c.getColumnIndex(TABLE.COLUMN.CONTENT_TYPE));
            String content = c.getString(c.getColumnIndex(TABLE.COLUMN.CONTENT));

            message.setContentType(contentType);

            MessageContent messageContent = MessageContent.Manager.sharedInstance().create(contentType);
            messageContent.decode(content.getBytes());
            message.setContent(messageContent);

            messages.add(message);

        }
        c.close();

        return messages;

    }

}
