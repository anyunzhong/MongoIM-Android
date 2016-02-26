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

@MessageContent.Tag(type = MessageContent.Type.VOICE, action = MessageContent.Tag.PERSIST | MessageContent.Tag.COUNT)
public class VoiceMessage extends MediaMessage {

    private String content;
    private int duration;
    private Uri uri;


    public String getContent() {
        return content;
    }

    public VoiceMessage setContent(String content) {
        this.content = content;
        return this;
    }

    public int getDuration() {
        return duration;
    }

    public VoiceMessage setDuration(int duration) {
        this.duration = duration;
        return this;
    }

    @Override
    public byte[] encode() {
        return toJson().getBytes();
    }

    @Override
    public String toJson() {
        JSONObject object = new JSONObject();
        try {
            object.put("content", content);
            object.put("duration", duration);
        } catch (JSONException e) {
            LogHelper.error(e.toString());
        }
        return object.toString();
    }

    @Override
    public void decode(byte[] bytes) {
        JSONObject object = JSON.parseObject(new String(bytes));
        content = object.getString("content");
        duration = object.getInteger("duration");
    }

    public Uri getUri() {
        return uri;
    }

    public VoiceMessage setUri(Uri uri) {
        this.uri = uri;
        return this;
    }
}
