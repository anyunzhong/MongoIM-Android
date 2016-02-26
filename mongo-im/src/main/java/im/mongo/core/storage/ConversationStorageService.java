package im.mongo.core.storage;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import net.datafans.android.common.helper.LogHelper;

import java.util.ArrayList;
import java.util.List;

import im.mongo.core.model.Conversation;

/**
 * Created by zhonganyun on 15/8/5.
 */
public class ConversationStorageService extends StorageService {


    private static ConversationStorageService service;

    private ConversationStorageService() {

    }

    public synchronized static ConversationStorageService sharedInstance() {
        if (service == null) service = new ConversationStorageService();
        return service;
    }


    public static class TABLE {
        public static final String NAME = "conversation";
        public static final String CREATE_SQL = "CREATE TABLE conversation(target_id VARCHAR (64) NOT NULL,conversation_type SMALLINT,update_time INTEGER,title VARCHAR (150),sub_title VARCHAR (150),unread_count MEDIUMINT);CREATE INDEX idx_conversation ON conversation (target_id, conversation_type);";

        public static class COLUMN {
            public static final String TARGET_ID = "target_id";
            public static final String CONVERSATION_TYPE = "conversation_type";
            public static final String TITLE = "title";
            public static final String SUB_TITLE = "sub_title";
            public static final String UPDATE_TIME = "update_time";
            public static final String UNREAD_COUNT = "unread_count";
        }

    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void insertOrUpdate(Conversation conversation) {
        Conversation exist = select(conversation.getTargetId(), conversation.getConversationType());
        if (exist != null)
            update(conversation);
        else
            insert(conversation);
    }

    public void insert(Conversation conversation) {
        LogHelper.debug("insert: " + TABLE.NAME);
        ContentValues cv = new ContentValues();
        cv.put(TABLE.COLUMN.TARGET_ID, conversation.getTargetId());
        cv.put(TABLE.COLUMN.CONVERSATION_TYPE, conversation.getConversationType().getType());
        cv.put(TABLE.COLUMN.TITLE, conversation.getTitle());
        cv.put(TABLE.COLUMN.SUB_TITLE, conversation.getSubTitle());
        cv.put(TABLE.COLUMN.UPDATE_TIME, conversation.getUpdateTime());
        cv.put(TABLE.COLUMN.UNREAD_COUNT, conversation.getUnreadCount());
        getWritableDatabase().insert(TABLE.NAME, null, cv);
    }

    public void update(Conversation conversation) {
        LogHelper.debug("update: " + TABLE.NAME);
        ContentValues cv = new ContentValues();
        cv.put(TABLE.COLUMN.SUB_TITLE, conversation.getSubTitle());
        cv.put(TABLE.COLUMN.UPDATE_TIME, conversation.getUpdateTime());
        getWritableDatabase().update(TABLE.NAME, cv, "target_id ='" + conversation.getTargetId() + "' and conversation_type=" + conversation.getConversationType().getType(), null);
    }


    private Conversation select(String targetId, Conversation.Type type) {
        String sql = "SELECT * from " + TABLE.NAME + " WHERE target_id='" + targetId + "' and conversation_type=" + type.getType();
        Cursor c = getReadableDatabase().rawQuery(sql, null);

        Conversation conversation = null;
        while (c.moveToNext()) {
            conversation = create(c);
        }
        c.close();

        return conversation;
    }


    public List<Conversation> select() {
        List<Conversation> conversations = new ArrayList<>();

        String sql = "SELECT * from " + TABLE.NAME + " ORDER BY update_time DESC";
        Cursor c = getReadableDatabase().rawQuery(sql, null);

        while (c.moveToNext()) {
            Conversation conversation = create(c);
            conversations.add(conversation);
        }
        c.close();

        return conversations;
    }

    private Conversation create(Cursor c) {
        Conversation conversation = new Conversation();
        conversation.setTargetId(c.getString(c.getColumnIndex(TABLE.COLUMN.TARGET_ID)));
        conversation.setConversationType(Conversation.Type.valueOf(c.getInt(c.getColumnIndex(TABLE.COLUMN.CONVERSATION_TYPE))));
        conversation.setTitle(c.getString(c.getColumnIndex(TABLE.COLUMN.TITLE)));
        conversation.setSubTitle(c.getString(c.getColumnIndex(TABLE.COLUMN.SUB_TITLE)));
        conversation.setUnreadCount(c.getInt(c.getColumnIndex(TABLE.COLUMN.UNREAD_COUNT)));
        conversation.setUpdateTime(c.getLong(c.getColumnIndex(TABLE.COLUMN.UPDATE_TIME)));
        return conversation;
    }


}
