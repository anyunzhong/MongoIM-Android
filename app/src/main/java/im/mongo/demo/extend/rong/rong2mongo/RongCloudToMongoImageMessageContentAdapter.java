package im.mongo.demo.extend.rong.rong2mongo;

import android.net.Uri;
import android.util.Base64;

import net.datafans.android.common.helper.IOHelper;
import net.datafans.android.common.helper.LogHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import im.mongo.core.model.content.MessageContent;
import io.rong.message.ImageMessage;

/**
 * Created by zhonganyun on 16/2/9.
 */
public class RongCloudToMongoImageMessageContentAdapter extends RongCloudToMongoMessageContentAdapter<ImageMessage> {


    @Override
    protected String getConversationSubTitle(ImageMessage content) {
        return "[图片]";
    }

    @Override
    public String getMongoMessageType() {
        return MessageContent.Type.IMAGE;
    }

    @Override
    public MessageContent getMongoMessageContent(ImageMessage imageMessage) {
        im.mongo.core.model.content.media.ImageMessage imageMsg = new im.mongo.core.model.content.media.ImageMessage();

        if (imageMsg.getThumbData() == null) {
            Uri thumbUri = imageMessage.getThumUri();
            String thumbData = imageMessage.getBase64();
            if (thumbData != null) {
                imageMsg.setThumbData(thumbData);
            } else if (thumbUri != null && !thumbUri.getPath().equals("")) {
                try {
                    byte[] bytes = IOHelper.toByteArray(new FileInputStream(new File(thumbUri.getPath())));
                    String thumb = Base64.encodeToString(bytes, Base64.DEFAULT);
                    imageMsg.setThumbData(thumb);
                } catch (IOException e) {
                    LogHelper.error(e);
                }
            }
        }
        Uri uri = imageMessage.getRemoteUri();
        if (uri != null) {
            String url = uri.toString();
            LogHelper.error("remote url:" + url);
            if (url != null && url.startsWith("http://"))
                imageMsg.setRemoteUrl(url);
            else
                imageMsg.setRemoteUrl("");
        } else {
            imageMsg.setRemoteUrl("");
        }

        return imageMsg;
    }
}
