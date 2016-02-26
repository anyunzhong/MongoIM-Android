package im.mongo.demo.extend.rong.rong2mongo;

import im.mongo.core.model.content.MessageContent;
import io.rong.message.LocationMessage;

/**
 * Created by zhonganyun on 16/2/9.
 */
public class RongCloudToMongoLocationMessageContentAdapter extends RongCloudToMongoMessageContentAdapter<LocationMessage> {

    @Override
    protected String getConversationSubTitle(LocationMessage content) {
        return "[位置]";
    }

    @Override
    public String getMongoMessageType() {
        return MessageContent.Type.LOCATION;
    }

    @Override
    public MessageContent getMongoMessageContent(LocationMessage locationMessage) {
        im.mongo.core.model.content.media.LocationMessage locationMsg = new im.mongo.core.model.content.media.LocationMessage();
        locationMsg.setLat(locationMessage.getLat());
        locationMsg.setLng(locationMessage.getLng());
        locationMsg.setAddress(locationMessage.getPoi());
        locationMsg.setImageUri(locationMessage.getImgUri());
        locationMsg.setThumbImage(locationMessage.getBase64());
        return locationMsg;
    }
}
