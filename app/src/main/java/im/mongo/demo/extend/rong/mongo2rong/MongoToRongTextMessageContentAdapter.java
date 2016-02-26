package im.mongo.demo.extend.rong.mongo2rong;

import im.mongo.core.model.content.media.TextMessage;
import io.rong.imlib.model.MessageContent;

/**
 * Created by zhonganyun on 16/2/9.
 */
public class MongoToRongTextMessageContentAdapter extends MongoToRongMessageContentAdapter<TextMessage> {

    @Override
    public MessageContent getRongCloudMessageContent(TextMessage textMessage) {
        return io.rong.message.TextMessage.obtain(textMessage.getText());
    }
}
