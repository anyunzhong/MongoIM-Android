package im.mongo.core.model.content.media;

import im.mongo.core.model.content.MessageContent;

/**
 * Created by zhonganyun on 16/2/8.
 */
@MessageContent.Tag(type = MessageContent.Type.SHORT_VIDEO, action = MessageContent.Tag.PERSIST | MessageContent.Tag.COUNT)
public class ShortVideoMessage extends MediaMessage {

    private String cover;
    private String url;

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public byte[] encode() {
        return new byte[0];
    }

    @Override
    public String toJson() {
        return null;
    }

    @Override
    public void decode(byte[] bytes) {

    }
}
