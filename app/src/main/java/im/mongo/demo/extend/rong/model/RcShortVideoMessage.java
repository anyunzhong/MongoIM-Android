package im.mongo.demo.extend.rong.model;

import android.os.Parcel;

import net.datafans.android.common.helper.LogHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import io.rong.common.ParcelUtils;
import io.rong.imlib.MessageTag;
import io.rong.imlib.model.MessageContent;

/**
 * Created by zhonganyun on 16/2/6.
 */
@MessageTag(value = im.mongo.core.model.content.MessageContent.Type.RC_SHORT_VIDEO, flag = MessageTag.ISCOUNTED | MessageTag.ISPERSISTED)
public class RcShortVideoMessage extends MessageContent {

    private String cover;
    private String url;

    public RcShortVideoMessage() {

    }

    public RcShortVideoMessage(Parcel in) {
        setCover(ParcelUtils.readFromParcel(in));
        setUrl(ParcelUtils.readFromParcel(in));
    }


    public RcShortVideoMessage(byte[] data) {
        String jsonStr = null;

        try {
            jsonStr = new String(data, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            LogHelper.error(e);
        }

        try {
            JSONObject jsonObj = new JSONObject(jsonStr);

            if (jsonObj.has("cover"))
                setCover(jsonObj.getString("cover"));
            if (jsonObj.has("url"))
                setUrl(jsonObj.getString("url"));

        } catch (JSONException e) {
            LogHelper.error(e);
        }
    }

    public static RcShortVideoMessage obtain(String cover, String url) {
        RcShortVideoMessage model = new RcShortVideoMessage();
        model.setCover(cover);
        model.setUrl(url);
        return model;
    }


    @Override
    public byte[] encode() {
        JSONObject jsonObj = new JSONObject();

        try {
            jsonObj.put("cover", getCover());
            jsonObj.put("url", getUrl());

        } catch (JSONException e) {
            LogHelper.error(e);
        }

        try {
            return jsonObj.toString().getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            LogHelper.error(e);
        }

        return null;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        ParcelUtils.writeToParcel(dest, getCover());
        ParcelUtils.writeToParcel(dest, getUrl());
    }


    public static final Creator<RcShortVideoMessage> CREATOR = new Creator<RcShortVideoMessage>() {
        @Override
        public RcShortVideoMessage createFromParcel(Parcel source) {
            return new RcShortVideoMessage(source);
        }

        @Override
        public RcShortVideoMessage[] newArray(int size) {
            return new RcShortVideoMessage[size];
        }
    };

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }
}
