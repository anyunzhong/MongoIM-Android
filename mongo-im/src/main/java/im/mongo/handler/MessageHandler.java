package im.mongo.handler;

import java.util.List;

import im.mongo.core.model.Conversation;
import im.mongo.core.model.Message;
import im.mongo.ui.event.MessageReceiveEvent;

/**
 * Created by zhonganyun on 16/1/30.
 */
public interface MessageHandler {

    void sendMessage(Message message);

    List<Message> getMessages(Conversation.Type conversationType, String targetId, int start, int size);

    List<Message> getImageMessages(Conversation.Type conversationType, String targetId, int size);

    List<Conversation> getConversations();

    void setMessageReceiveStatus(int messageId, Message.ReceiveStatus receiveStatus);

    void deleteMessage(int messageId);
}
