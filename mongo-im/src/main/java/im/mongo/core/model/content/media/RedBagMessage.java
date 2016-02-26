package im.mongo.core.model.content.media;

import im.mongo.core.model.content.MessageContent;

/**
 * Created by zhonganyun on 16/2/6.
 */
@MessageContent.Tag(type = MessageContent.Type.RED_BAG, action = MessageContent.Tag.PERSIST | MessageContent.Tag.COUNT)
public class RedBagMessage extends MediaMessage {
    private String title;
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

    public RedBagMessage setTitle(String title) {
        this.title = title;
        return this;
    }
}
