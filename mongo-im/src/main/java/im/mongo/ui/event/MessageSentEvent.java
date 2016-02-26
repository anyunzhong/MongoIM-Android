package im.mongo.ui.event;

import net.datafans.android.common.event.BaseEvent;

/**
 * Created by zhonganyun on 16/1/31.
 */
public class MessageSentEvent extends BaseEvent {
    private int messageId;
    private String url;

    public MessageSentEvent(int messageId, String url) {
        this.messageId = messageId;
        this.url = url;
    }

    public int getMessageId() {
        return messageId;
    }

    public MessageSentEvent setMessageId(int messageId) {
        this.messageId = messageId;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public MessageSentEvent setUrl(String url) {
        this.url = url;
        return this;
    }
}
