package im.mongo.demo.extend.rong.rong2mongo;

import im.mongo.core.model.content.MessageContent;
import im.mongo.core.model.content.media.EmotionMessage;
import im.mongo.ui.emotion.PackageEmotionItem;
import im.mongo.demo.extend.rong.model.RcEmotionMessage;

/**
 * Created by zhonganyun on 16/2/9.
 */
public class RongCloudToMongoEmotionMessageContentAdapter extends RongCloudToMongoMessageContentAdapter<RcEmotionMessage> {

    @Override
    protected String getConversationSubTitle(RcEmotionMessage content) {
        return String.format("[%s]", content.getName());
    }

    @Override
    public String getMongoMessageType() {
        return MessageContent.Type.EMOTION;
    }

    @Override
    public MessageContent getMongoMessageContent(RcEmotionMessage emotionMessage) {
        EmotionMessage emotionMsg = new EmotionMessage();

        PackageEmotionItem item = new PackageEmotionItem();
        emotionMsg.setEmotionItem(item);

        item.setName(emotionMessage.getName());
        item.setRemoteGif(emotionMessage.getRemoteGif());
        item.setRemoteThumb(emotionMessage.getRemoteThumb());
        item.setLocalGif(emotionMessage.getLocalGif());
        item.setLocalThumb(emotionMessage.getLocalThumb());
        return emotionMsg;
    }
}
