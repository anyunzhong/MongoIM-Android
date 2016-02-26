package im.mongo.core.model.content.media;

import android.net.Uri;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

import net.datafans.android.common.helper.LogHelper;

import im.mongo.core.model.content.MessageContent;

/**
 * Created by zhonganyun on 15/11/20.
 */

@MessageContent.Tag(type = MessageContent.Type.IMAGE, action = MessageContent.Tag.PERSIST | MessageContent.Tag.COUNT)
public class ImageMessage extends MediaMessage {

    private String thumbData;
    private String remoteUrl;
    private Uri localPath;
    private Uri thumbPath;


    @Override
    public byte[] encode() {
        return toJson().getBytes();
    }

    @Override
    public String toJson() {
        JSONObject object = new JSONObject();
        try {
            object.put("thumb", thumbData);
            object.put("url", remoteUrl);
        } catch (JSONException e) {
            LogHelper.error(e.toString());
        }
        return object.toString();
    }

    @Override
    public void decode(byte[] bytes) {
        JSONObject object = JSON.parseObject(new String(bytes));
        thumbData = object.getString("thumb");
        remoteUrl = object.getString("url");
    }

    public String getThumbData() {
        return thumbData;
    }

    public ImageMessage setThumbData(String thumbData) {
        this.thumbData = thumbData;
        return this;
    }

    public String getRemoteUrl() {
        return remoteUrl;
    }

    public ImageMessage setRemoteUrl(String remoteUrl) {
        this.remoteUrl = remoteUrl;
        return this;
    }

    public Uri getLocalPath() {
        return localPath;
    }

    public ImageMessage setLocalPath(Uri localPath) {
        this.localPath = localPath;
        return this;
    }

    public Uri getThumbPath() {
        return thumbPath;
    }

    public ImageMessage setThumbPath(Uri thumbPath) {
        this.thumbPath = thumbPath;
        return this;
    }
}
