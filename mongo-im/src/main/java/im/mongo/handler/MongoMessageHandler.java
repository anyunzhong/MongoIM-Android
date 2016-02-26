package im.mongo.handler;

import java.util.ArrayList;
import java.util.List;

import im.mongo.core.model.Conversation;
import im.mongo.core.model.Message;
import im.mongo.core.model.content.MessageContent;
import im.mongo.core.model.content.media.TextMessage;
import im.mongo.core.storage.ConversationStorageService;
import im.mongo.core.storage.MessageStorageService;

/**
 * Created by zhonganyun on 16/1/30.
 */
public class MongoMessageHandler implements MessageHandler {

    private String senderId;

    public MongoMessageHandler(String senderId){
        this.senderId = senderId;
    }
    @Override
    public void sendMessage(Message message) {

        message.setSenderId(senderId);

        MessageStorageService.sharedInstance().insertMessage(message);

        Conversation conversation = new Conversation();
        conversation.setTargetId(message.getTargetId());
        conversation.setConversationType(message.getConversationType());
        conversation.setTitle("");
        conversation.setUpdateTime(System.currentTimeMillis());

        switch (message.getContentType()) {
            case MessageContent.Type.TEXT:
                conversation.setSubTitle(((TextMessage) message.getContent()).getText());
                break;
            case MessageContent.Type.VOICE:
                conversation.setSubTitle("语音");
                break;
            case MessageContent.Type.IMAGE:
                conversation.setSubTitle("图片");
                break;
        }

        ConversationStorageService.sharedInstance().insertOrUpdate(conversation);
    }

    @Override
    public List<Message> getMessages(Conversation.Type conversationType, String targetId, int start, int size) {
        return MessageStorageService.sharedInstance().getMessages(conversationType, targetId, start, size);
    }

    @Override
    public List<Message> getImageMessages(Conversation.Type conversationType, String targetId, int size) {
        return null;
    }

    @Override
    public List<Conversation> getConversations() {
        return ConversationStorageService.sharedInstance().select();
    }

    @Override
    public void setMessageReceiveStatus(int messageId, Message.ReceiveStatus receiveStatus) {

    }

    @Override
    public void deleteMessage(int messageId) {

    }
}
