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
@MessageTag(value = im.mongo.core.model.content.MessageContent.Type.RC_RED_BAG, flag = MessageTag.ISCOUNTED | MessageTag.ISPERSISTED)
public class RcRedBagMessage extends MessageContent {

    private String title;

    public RcRedBagMessage(){

    }

    public RcRedBagMessage(Parcel in) {
        setTitle(ParcelUtils.readFromParcel(in));
    }


    public RcRedBagMessage(byte[] data) {
        String jsonStr = null;

        try {
            jsonStr = new String(data, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            LogHelper.error(e);
        }

        try {
            JSONObject jsonObj = new JSONObject(jsonStr);

            if (jsonObj.has("title"))
                setTitle(jsonObj.getString("title"));

        } catch (JSONException e) {
            LogHelper.error(e);
        }
    }

    public static RcRedBagMessage obtain(String title) {
        RcRedBagMessage model = new RcRedBagMessage();
        model.setTitle(title);
        return model;
    }


    @Override
    public byte[] encode() {
        JSONObject jsonObj = new JSONObject();

        try {
            jsonObj.put("title", getTitle());

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
        ParcelUtils.writeToParcel(dest, getTitle());
    }


    public static final Creator<RcRedBagMessage> CREATOR = new Creator<RcRedBagMessage>() {
        @Override
        public RcRedBagMessage createFromParcel(Parcel source) {
            return new RcRedBagMessage(source);
        }

        @Override
        public RcRedBagMessage[] newArray(int size) {
            return new RcRedBagMessage[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public RcRedBagMessage setTitle(String title) {
        this.title = title;
        return this;
    }
}
