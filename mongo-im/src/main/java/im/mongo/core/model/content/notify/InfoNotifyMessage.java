package im.mongo.core.model.content.notify;

import im.mongo.core.model.content.MessageContent;

/**
 * Created by zhonganyun on 16/2/5.
 */
@MessageContent.Tag(type = MessageContent.Type.InfoNotify, action = MessageContent.Tag.PERSIST)
public class InfoNotifyMessage extends NotifyMessage {

    private String content;

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

    public String getContent() {
        return content;
    }

    public InfoNotifyMessage setContent(String content) {
        this.content = content;
        return this;
    }
}
