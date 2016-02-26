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
@MessageTag(value = im.mongo.core.model.content.MessageContent.Type.RC_EMOTION, flag = MessageTag.ISCOUNTED | MessageTag.ISPERSISTED)
public class RcEmotionMessage extends MessageContent {

    private String name;
    private String remoteGif;
    private String remoteThumb;

    private String localGif;
    private String localThumb;



    public RcEmotionMessage() {

    }

    public RcEmotionMessage(Parcel in) {
        setName(ParcelUtils.readFromParcel(in));
        setRemoteGif(ParcelUtils.readFromParcel(in));
        setRemoteThumb(ParcelUtils.readFromParcel(in));
        setLocalGif(ParcelUtils.readFromParcel(in));
        setLocalThumb(ParcelUtils.readFromParcel(in));

    }


    public RcEmotionMessage(byte[] data) {
        String jsonStr = null;

        try {
            jsonStr = new String(data, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            LogHelper.error(e);
        }

        try {
            JSONObject jsonObj = new JSONObject(jsonStr);

            if (jsonObj.has("name"))
                setName(jsonObj.getString("name"));
            if (jsonObj.has("remoteGif"))
                setRemoteGif(jsonObj.getString("remoteGif"));
            if (jsonObj.has("remoteThumb"))
                setRemoteThumb(jsonObj.getString("remoteThumb"));
            if (jsonObj.has("localGif"))
                setLocalGif(jsonObj.getString("localGif"));
            if (jsonObj.has("localThumb"))
                setLocalThumb(jsonObj.getString("localThumb"));

        } catch (JSONException e) {
            LogHelper.error(e);
        }
    }

    public static RcEmotionMessage obtain(String name, String remoteGif, String remoteThumb, String localGif, String localThumb) {
        RcEmotionMessage model = new RcEmotionMessage();
        model.setName(name);
        model.setRemoteGif(remoteGif);
        model.setRemoteThumb(remoteThumb);
        model.setLocalGif(localGif);
        model.setLocalThumb(localThumb);
        return model;
    }


    @Override
    public byte[] encode() {
        JSONObject jsonObj = new JSONObject();

        try {
            jsonObj.put("name", getName());
            jsonObj.put("remoteGif", getRemoteGif());
            jsonObj.put("remoteThumb", getRemoteThumb());
            jsonObj.put("localGif", getLocalGif());
            jsonObj.put("localThumb", getLocalThumb());

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
        ParcelUtils.writeToParcel(dest, getName());
        ParcelUtils.writeToParcel(dest, getRemoteGif());
        ParcelUtils.writeToParcel(dest, getRemoteThumb());
        ParcelUtils.writeToParcel(dest, getLocalGif());
        ParcelUtils.writeToParcel(dest, getLocalThumb());
    }


    public static final Creator<RcEmotionMessage> CREATOR = new Creator<RcEmotionMessage>() {
        @Override
        public RcEmotionMessage createFromParcel(Parcel source) {
            return new RcEmotionMessage(source);
        }

        @Override
        public RcEmotionMessage[] newArray(int size) {
            return new RcEmotionMessage[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemoteThumb() {
        return remoteThumb;
    }

    public void setRemoteThumb(String remoteThumb) {
        this.remoteThumb = remoteThumb;
    }

    public String getRemoteGif() {
        return remoteGif;
    }

    public void setRemoteGif(String remoteGif) {
        this.remoteGif = remoteGif;
    }

    public String getLocalThumb() {
        return localThumb;
    }

    public void setLocalThumb(String localThumb) {
        this.localThumb = localThumb;
    }

    public String getLocalGif() {
        return localGif;
    }

    public void setLocalGif(String localGif) {
        this.localGif = localGif;
    }

    public static Creator<RcEmotionMessage> getCREATOR() {
        return CREATOR;
    }
}
