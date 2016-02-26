package im.mongo.demo.extend.rong.rong2mongo;

import im.mongo.core.model.Conversation;
import im.mongo.core.model.content.MessageContent;

/**
 * Created by zhonganyun on 16/2/9.
 */
public abstract class RongCloudToMongoMessageContentAdapter<T extends io.rong.imlib.model.MessageContent> {

    public Conversation getMongoConversation(io.rong.imlib.model.Conversation conversation) {

        //TODO ==========设置消息类型==============
        Conversation con = new Conversation();
        con.setTargetId(conversation.getTargetId());
        con.setTitle("");
        con.setUnreadCount(conversation.getUnreadMessageCount());
        con.setUpdateTime(conversation.getSentTime());
        con.setSubTitle(getConversationSubTitle((T)conversation.getLatestMessage()));

        return con;
    }

    protected abstract String getConversationSubTitle(T content);

    public abstract String getMongoMessageType();

    public abstract MessageContent getMongoMessageContent(T content);
}
