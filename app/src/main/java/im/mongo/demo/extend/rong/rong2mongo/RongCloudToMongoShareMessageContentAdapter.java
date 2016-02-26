package im.mongo.demo.extend.rong.rong2mongo;

import im.mongo.core.model.content.MessageContent;
import im.mongo.core.model.content.media.ShareMessage;
import im.mongo.demo.extend.rong.model.RcShareMessage;

/**
 * Created by zhonganyun on 16/2/9.
 */
public class RongCloudToMongoShareMessageContentAdapter extends RongCloudToMongoMessageContentAdapter<RcShareMessage> {

    @Override
    protected String getConversationSubTitle(RcShareMessage content) {
        return "[链接] "+content.getTitle();
    }

    @Override
    public String getMongoMessageType() {
        return MessageContent.Type.SHARE;
    }

    @Override
    public MessageContent getMongoMessageContent(RcShareMessage shareMessage) {
        ShareMessage shareMsg = new ShareMessage();
        shareMsg.setTitle(shareMessage.getTitle());
        shareMsg.setDesc(shareMessage.getDesc());
        shareMsg.setThumb(shareMessage.getThumb());
        shareMsg.setLink(shareMessage.getLink());
        shareMsg.setSourceLogo(shareMessage.getSourceLogo());
        shareMsg.setSourceName(shareMessage.getSourceName());
        return shareMsg;
    }
}
