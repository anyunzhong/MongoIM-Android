package im.mongo.demo.extend.rong.mongo2rong;

import im.mongo.core.model.content.media.ImageMessage;
import io.rong.imlib.model.MessageContent;

/**
 * Created by zhonganyun on 16/2/9.
 */
public class MongoToRongImageMessageContentAdapter extends MongoToRongMessageContentAdapter<ImageMessage> {

    @Override
    public MessageContent getRongCloudMessageContent(ImageMessage imageMessage) {
        return io.rong.message.ImageMessage.obtain(imageMessage.getLocalPath(), imageMessage.getLocalPath());
    }
}
