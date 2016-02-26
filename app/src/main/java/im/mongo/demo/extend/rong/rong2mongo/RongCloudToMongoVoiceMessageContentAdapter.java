package im.mongo.demo.extend.rong.rong2mongo;

import im.mongo.core.model.content.MessageContent;
import io.rong.message.VoiceMessage;

/**
 * Created by zhonganyun on 16/2/9.
 */
public class RongCloudToMongoVoiceMessageContentAdapter extends RongCloudToMongoMessageContentAdapter<VoiceMessage> {

    @Override
    protected String getConversationSubTitle(VoiceMessage content) {
        return "[语音]";
    }

    @Override
    public String getMongoMessageType() {
        return MessageContent.Type.VOICE;
    }

    @Override
    public MessageContent getMongoMessageContent(VoiceMessage voiceMessage) {
        im.mongo.core.model.content.media.VoiceMessage voiceMsg = new im.mongo.core.model.content.media.VoiceMessage();
        voiceMsg.setUri(voiceMessage.getUri());
        voiceMsg.setDuration(voiceMessage.getDuration());
        return voiceMsg;
    }
}
