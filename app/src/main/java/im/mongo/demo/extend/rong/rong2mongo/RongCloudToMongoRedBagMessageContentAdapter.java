package im.mongo.demo.extend.rong.rong2mongo;

import im.mongo.core.model.content.MessageContent;
import im.mongo.core.model.content.media.RedBagMessage;
import im.mongo.demo.extend.rong.model.RcRedBagMessage;

/**
 * Created by zhonganyun on 16/2/9.
 */
public class RongCloudToMongoRedBagMessageContentAdapter extends RongCloudToMongoMessageContentAdapter<RcRedBagMessage> {

    @Override
    protected String getConversationSubTitle(RcRedBagMessage content) {
        return "[红包] " + content.getTitle();
    }

    @Override
    public String getMongoMessageType() {
        return MessageContent.Type.RED_BAG;
    }

    @Override
    public MessageContent getMongoMessageContent(RcRedBagMessage rcRedBagMessage) {
        RedBagMessage redBagMessage = new RedBagMessage();
        redBagMessage.setTitle(rcRedBagMessage.getTitle());
        return redBagMessage;
    }
}
