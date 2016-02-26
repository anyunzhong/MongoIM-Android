package im.mongo.demo.extend.rong.mongo2rong;

import im.mongo.core.model.content.media.EmotionMessage;
import im.mongo.demo.extend.rong.model.RcEmotionMessage;
import io.rong.imlib.model.MessageContent;

/**
 * Created by zhonganyun on 16/2/9.
 */
public class MongoToRongEmotionMessageContentAdapter extends MongoToRongMessageContentAdapter<EmotionMessage> {

    @Override
    public MessageContent getRongCloudMessageContent(EmotionMessage emotionMessage) {
        return RcEmotionMessage.obtain(emotionMessage.getEmotionItem().getName(), emotionMessage.getEmotionItem().getRemoteGif(), emotionMessage.getEmotionItem().getRemoteThumb(), emotionMessage.getEmotionItem().getLocalGif(), emotionMessage.getEmotionItem().getLocalThumb());
    }
}
