package im.mongo.demo.extend.rong.mongo2rong;

import im.mongo.core.model.content.media.LocationMessage;
import io.rong.imlib.model.MessageContent;

/**
 * Created by zhonganyun on 16/2/9.
 */
public class MongoToRongLocationMessageContentAdapter extends MongoToRongMessageContentAdapter<LocationMessage> {

    @Override
    public MessageContent getRongCloudMessageContent(LocationMessage locationMessage) {
        return io.rong.message.LocationMessage.obtain(locationMessage.getLat(), locationMessage.getLng(), locationMessage.getAddress(), locationMessage.getImageUri());
    }
}
