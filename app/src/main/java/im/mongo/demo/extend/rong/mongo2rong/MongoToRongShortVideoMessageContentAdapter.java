package im.mongo.demo.extend.rong.mongo2rong;

import im.mongo.core.model.content.media.ShortVideoMessage;
import im.mongo.demo.extend.rong.model.RcShortVideoMessage;
import io.rong.imlib.model.MessageContent;

/**
 * Created by zhonganyun on 16/2/9.
 */
public class MongoToRongShortVideoMessageContentAdapter extends MongoToRongMessageContentAdapter<ShortVideoMessage> {

    @Override
    public MessageContent getRongCloudMessageContent(ShortVideoMessage shortVideoMessage) {
        return RcShortVideoMessage.obtain(shortVideoMessage.getCover(), shortVideoMessage.getUrl());
    }
}
