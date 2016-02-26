package im.mongo.demo.extend.rong.mongo2rong;

import im.mongo.core.model.content.media.NameCardMessage;
import im.mongo.demo.extend.rong.model.RcNameCardMessage;
import io.rong.imlib.model.MessageContent;

/**
 * Created by zhonganyun on 16/2/9.
 */
public class MongoToRongNameCardMessageContentAdapter extends MongoToRongMessageContentAdapter<NameCardMessage> {

    @Override
    public MessageContent getRongCloudMessageContent(NameCardMessage nameCardMessage) {
        return RcNameCardMessage.obtain(nameCardMessage.getUserId(), nameCardMessage.getUserNick(), nameCardMessage.getUserNum(), nameCardMessage.getUserAvatar());
    }
}
