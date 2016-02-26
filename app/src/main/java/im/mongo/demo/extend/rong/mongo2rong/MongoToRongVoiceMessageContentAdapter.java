package im.mongo.demo.extend.rong.mongo2rong;

import im.mongo.core.model.content.media.VoiceMessage;
import io.rong.imlib.model.MessageContent;

/**
 * Created by zhonganyun on 16/2/9.
 */
public class MongoToRongVoiceMessageContentAdapter extends MongoToRongMessageContentAdapter<VoiceMessage> {

    @Override
    public MessageContent getRongCloudMessageContent(VoiceMessage voiceMessage) {
        return io.rong.message.VoiceMessage.obtain(voiceMessage.getUri(), voiceMessage.getDuration());
    }
}
