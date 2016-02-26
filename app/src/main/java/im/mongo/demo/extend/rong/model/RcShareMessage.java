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
@MessageTag(value = im.mongo.core.model.content.MessageContent.Type.RC_SHARE, flag = MessageTag.ISCOUNTED | MessageTag.ISPERSISTED)
public class RcShareMessage extends MessageContent {

    private String title;
    private String desc;
    private String thumb;
    private String link;
    private String sourceLogo;
    private String sourceName;


    public RcShareMessage() {

    }

    public RcShareMessage(Parcel in) {
        setTitle(ParcelUtils.readFromParcel(in));
        setDesc(ParcelUtils.readFromParcel(in));
        setThumb(ParcelUtils.readFromParcel(in));
        setLink(ParcelUtils.readFromParcel(in));
        setSourceLogo(ParcelUtils.readFromParcel(in));
        setSourceName(ParcelUtils.readFromParcel(in));

    }


    public RcShareMessage(byte[] data) {
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
            if (jsonObj.has("desc"))
                setDesc(jsonObj.getString("desc"));
            if (jsonObj.has("thumb"))
                setThumb(jsonObj.getString("thumb"));
            if (jsonObj.has("link"))
                setLink(jsonObj.getString("link"));
            if (jsonObj.has("sourceLogo"))
                setSourceLogo(jsonObj.getString("sourceLogo"));
            if (jsonObj.has("sourceName"))
                setSourceName(jsonObj.getString("sourceName"));

        } catch (JSONException e) {
            LogHelper.error(e);
        }
    }

    public static RcShareMessage obtain(String title, String desc, String thumb, String link, String sourceLogo, String sourceName) {
        RcShareMessage model = new RcShareMessage();
        model.setTitle(title);
        model.setDesc(desc);
        model.setThumb(thumb);
        model.setLink(link);
        model.setSourceName(sourceName);
        model.setSourceLogo(sourceLogo);
        return model;
    }


    @Override
    public byte[] encode() {
        JSONObject jsonObj = new JSONObject();

        try {
            jsonObj.put("title", getTitle());
            jsonObj.put("desc", getDesc());
            jsonObj.put("thumb", getThumb());
            jsonObj.put("link", getLink());
            jsonObj.put("sourceLogo", getSourceLogo());
            jsonObj.put("sourceName", getSourceName());

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
        ParcelUtils.writeToParcel(dest, getDesc());
        ParcelUtils.writeToParcel(dest, getThumb());
        ParcelUtils.writeToParcel(dest, getLink());
        ParcelUtils.writeToParcel(dest, getSourceLogo());
        ParcelUtils.writeToParcel(dest, getSourceName());
    }


    public static final Creator<RcShareMessage> CREATOR = new Creator<RcShareMessage>() {
        @Override
        public RcShareMessage createFromParcel(Parcel source) {
            return new RcShareMessage(source);
        }

        @Override
        public RcShareMessage[] newArray(int size) {
            return new RcShareMessage[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public RcShareMessage setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDesc() {
        return desc;
    }

    public RcShareMessage setDesc(String desc) {
        this.desc = desc;
        return this;
    }

    public String getThumb() {
        return thumb;
    }

    public RcShareMessage setThumb(String thumb) {
        this.thumb = thumb;
        return this;
    }

    public String getLink() {
        return link;
    }

    public RcShareMessage setLink(String link) {
        this.link = link;
        return this;
    }

    public String getSourceLogo() {
        return sourceLogo;
    }

    public RcShareMessage setSourceLogo(String sourceLogo) {
        this.sourceLogo = sourceLogo;
        return this;
    }

    public String getSourceName() {
        return sourceName;
    }

    public RcShareMessage setSourceName(String sourceName) {
        this.sourceName = sourceName;
        return this;
    }
}
