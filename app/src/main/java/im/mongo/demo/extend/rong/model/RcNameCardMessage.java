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
@MessageTag(value = im.mongo.core.model.content.MessageContent.Type.RC_NAME_CARD, flag = MessageTag.ISCOUNTED | MessageTag.ISPERSISTED)
public class RcNameCardMessage extends MessageContent {

    private String userId;
    private String userNick;
    private String userNum;
    private String userAvatar;

    public RcNameCardMessage() {

    }

    public RcNameCardMessage(Parcel in) {
        setUserId(ParcelUtils.readFromParcel(in));
        setUserNick(ParcelUtils.readFromParcel(in));
        setUserNum(ParcelUtils.readFromParcel(in));
        setUserAvatar(ParcelUtils.readFromParcel(in));

    }


    public RcNameCardMessage(byte[] data) {
        String jsonStr = null;

        try {
            jsonStr = new String(data, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            LogHelper.error(e);
        }

        try {
            JSONObject jsonObj = new JSONObject(jsonStr);

            if (jsonObj.has("userId"))
                setUserId(jsonObj.getString("userId"));
            if (jsonObj.has("userNick"))
                setUserNick(jsonObj.getString("userNick"));
            if (jsonObj.has("userNum"))
                setUserNum(jsonObj.getString("userNum"));
            if (jsonObj.has("userAvatar"))
                setUserAvatar(jsonObj.getString("userAvatar"));

        } catch (JSONException e) {
            LogHelper.error(e);
        }
    }

    public static RcNameCardMessage obtain(String userId, String userNick, String userNum, String userAvatar) {
        RcNameCardMessage model = new RcNameCardMessage();
        model.setUserId(userId);
        model.setUserNum(userNum);
        model.setUserNick(userNick);
        model.setUserAvatar(userAvatar);
        return model;
    }


    @Override
    public byte[] encode() {
        JSONObject jsonObj = new JSONObject();

        try {
            jsonObj.put("userId", getUserId());
            jsonObj.put("userNick", getUserNick());
            jsonObj.put("userNum", getUserNum());
            jsonObj.put("userAvatar", getUserAvatar());

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
        ParcelUtils.writeToParcel(dest, getUserId());
        ParcelUtils.writeToParcel(dest, getUserNick());
        ParcelUtils.writeToParcel(dest, getUserNum());
        ParcelUtils.writeToParcel(dest, getUserAvatar());
    }


    public static final Creator<RcNameCardMessage> CREATOR = new Creator<RcNameCardMessage>() {
        @Override
        public RcNameCardMessage createFromParcel(Parcel source) {
            return new RcNameCardMessage(source);
        }

        @Override
        public RcNameCardMessage[] newArray(int size) {
            return new RcNameCardMessage[size];
        }
    };

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserNick() {
        return userNick;
    }

    public void setUserNick(String userNick) {
        this.userNick = userNick;
    }

    public String getUserNum() {
        return userNum;
    }

    public void setUserNum(String userNum) {
        this.userNum = userNum;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }
}
