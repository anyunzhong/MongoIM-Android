package im.mongo.demo.extend.rong.rong2mongo;

import im.mongo.core.model.content.MessageContent;
import im.mongo.core.model.content.media.ShortVideoMessage;
import im.mongo.demo.extend.rong.model.RcShortVideoMessage;

/**
 * Created by zhonganyun on 16/2/9.
 */
public class RongCloudToMongoShortVideoMessageContentAdapter extends RongCloudToMongoMessageContentAdapter<RcShortVideoMessage> {

    @Override
    protected String getConversationSubTitle(RcShortVideoMessage content) {
        return "[视频] ";
    }

    @Override
    public String getMongoMessageType() {
        return MessageContent.Type.SHORT_VIDEO;
    }

    @Override
    public MessageContent getMongoMessageContent(RcShortVideoMessage rcShortVideoMessage) {
        ShortVideoMessage videoMessage = new ShortVideoMessage();
        videoMessage.setUrl(rcShortVideoMessage.getUrl());
        videoMessage.setCover(rcShortVideoMessage.getCover());
        return videoMessage;
    }
}
