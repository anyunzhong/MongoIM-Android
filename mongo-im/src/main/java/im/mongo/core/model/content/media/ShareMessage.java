package im.mongo.core.model.content.media;

import im.mongo.core.model.content.MessageContent;

/**
 * Created by zhonganyun on 16/2/9.
 */
@MessageContent.Tag(type = MessageContent.Type.SHARE, action = MessageContent.Tag.PERSIST | MessageContent.Tag.COUNT)
public class ShareMessage extends MediaMessage {

    private String title;
    private String desc;
    private String thumb;
    private String link;
    private String sourceLogo;
    private String sourceName;

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

    public String getTitle() {
        return title;
    }

    public ShareMessage setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDesc() {
        return desc;
    }

    public ShareMessage setDesc(String desc) {
        this.desc = desc;
        return this;
    }

    public String getThumb() {
        return thumb;
    }

    public ShareMessage setThumb(String thumb) {
        this.thumb = thumb;
        return this;
    }

    public String getLink() {
        return link;
    }

    public ShareMessage setLink(String link) {
        this.link = link;
        return this;
    }

    public String getSourceLogo() {
        return sourceLogo;
    }

    public ShareMessage setSourceLogo(String sourceLogo) {
        this.sourceLogo = sourceLogo;
        return this;
    }

    public String getSourceName() {
        return sourceName;
    }

    public ShareMessage setSourceName(String sourceName) {
        this.sourceName = sourceName;
        return this;
    }
}
