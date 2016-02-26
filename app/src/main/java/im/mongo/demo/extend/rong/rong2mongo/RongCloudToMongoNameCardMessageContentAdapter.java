package im.mongo.demo.extend.rong.rong2mongo;

import im.mongo.core.model.content.MessageContent;
import im.mongo.core.model.content.media.NameCardMessage;
import im.mongo.demo.extend.rong.model.RcNameCardMessage;

/**
 * Created by zhonganyun on 16/2/9.
 */
public class RongCloudToMongoNameCardMessageContentAdapter extends RongCloudToMongoMessageContentAdapter<RcNameCardMessage> {

    @Override
    protected String getConversationSubTitle(RcNameCardMessage content) {
        return "[名片] " + content.getUserNick();
    }

    @Override
    public String getMongoMessageType() {
        return MessageContent.Type.NAME_CARD;
    }

    @Override
    public MessageContent getMongoMessageContent(RcNameCardMessage nameCardMessage) {
        NameCardMessage nameCardMsg = new NameCardMessage();
        nameCardMsg.setUserId(nameCardMessage.getUserId());
        nameCardMsg.setUserNick(nameCardMessage.getUserNick());
        nameCardMsg.setUserNum(nameCardMessage.getUserNum());
        nameCardMsg.setUserAvatar(nameCardMessage.getUserAvatar());
        return nameCardMsg;
    }
}
