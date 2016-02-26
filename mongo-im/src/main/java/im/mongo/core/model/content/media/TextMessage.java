package im.mongo.core.model.content.media;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;


import net.datafans.android.common.helper.LogHelper;

import im.mongo.core.model.content.MessageContent;

/**
 * Created by zhonganyun on 15/11/20.
 */

@MessageContent.Tag(type = MessageContent.Type.TEXT, action = MessageContent.Tag.PERSIST | MessageContent.Tag.COUNT)
public class TextMessage extends MediaMessage {

    private String text;

    private String parsedText;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getParsedText() {
        return parsedText;
    }

    public void setParsedText(String parsedText) {
        this.parsedText = parsedText;
    }

    @Override
    public byte[] encode() {
        return toJson().getBytes();
    }

    @Override
    public String toJson() {
        JSONObject object = new JSONObject();
        try {
            object.put("text", text);
        } catch (JSONException e) {
            LogHelper.error(e.toString());
        }
        return object.toString();
    }

    @Override
    public void decode(byte[] bytes) {
        JSONObject object = JSON.parseObject(new String(bytes));
        text = object.getString("text");
    }
}
