package im.mongo.demo.extend.rong.rong2mongo;

import im.mongo.core.model.content.MessageContent;
import im.mongo.core.model.content.notify.InfoNotifyMessage;
import io.rong.message.InformationNotificationMessage;

/**
 * Created by zhonganyun on 16/2/9.
 */
public class RongCloudToMongoInfoNotifyMessageContentAdapter extends RongCloudToMongoMessageContentAdapter<InformationNotificationMessage> {

    @Override
    protected String getConversationSubTitle(InformationNotificationMessage content) {
        return "[系统消息]";
    }

    @Override
    public String getMongoMessageType() {
        return MessageContent.Type.InfoNotify;
    }

    @Override
    public MessageContent getMongoMessageContent(InformationNotificationMessage content) {
        InfoNotifyMessage infoMsg = new InfoNotifyMessage();
        infoMsg.setContent(content.getMessage());
        return infoMsg;
    }
}
