package im.mongo.demo.extend.rong.mongo2rong;

import im.mongo.core.model.content.media.RedBagMessage;
import im.mongo.demo.extend.rong.model.RcRedBagMessage;
import io.rong.imlib.model.MessageContent;

/**
 * Created by zhonganyun on 16/2/9.
 */
public class MongoToRongRedBagMessageContentAdapter extends MongoToRongMessageContentAdapter<RedBagMessage> {

    @Override
    public MessageContent getRongCloudMessageContent(RedBagMessage redBagMessage) {
        return RcRedBagMessage.obtain(redBagMessage.getTitle());
    }
}
