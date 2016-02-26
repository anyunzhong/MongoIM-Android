package im.mongo.demo.extend.rong.rong2mongo;

import im.mongo.core.model.content.MessageContent;
import io.rong.message.TextMessage;

/**
 * Created by zhonganyun on 16/2/9.
 */
public class RongCloudToMongoTextMessageContentAdapter extends RongCloudToMongoMessageContentAdapter<TextMessage> {

    @Override
    protected String getConversationSubTitle(TextMessage content) {
        return content.getContent();
    }

    @Override
    public String getMongoMessageType() {
        return MessageContent.Type.TEXT;
    }

    @Override
    public MessageContent getMongoMessageContent(TextMessage content) {
        im.mongo.core.model.content.media.TextMessage textMsg = new im.mongo.core.model.content.media.TextMessage();
        textMsg.setText(content.getContent());
        return textMsg;
    }
}
