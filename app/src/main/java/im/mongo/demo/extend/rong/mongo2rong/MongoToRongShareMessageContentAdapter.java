package im.mongo.demo.extend.rong.mongo2rong;

import im.mongo.core.model.content.media.ShareMessage;
import im.mongo.demo.extend.rong.model.RcShareMessage;
import io.rong.imlib.model.MessageContent;

/**
 * Created by zhonganyun on 16/2/9.
 */
public class MongoToRongShareMessageContentAdapter extends MongoToRongMessageContentAdapter<ShareMessage> {

    @Override
    public MessageContent getRongCloudMessageContent(ShareMessage shareMessage) {
        return RcShareMessage.obtain(shareMessage.getTitle(), shareMessage.getDesc(), shareMessage.getThumb(), shareMessage.getLink(), shareMessage.getSourceLogo(), shareMessage.getSourceName());
    }
}
