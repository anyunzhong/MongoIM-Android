package im.mongo.ui.event;

import net.datafans.android.common.event.BaseEvent;

import im.mongo.core.model.Message;

/**
 * Created by zhonganyun on 16/1/31.
 */
public class MessageReceiveEvent extends BaseEvent {
    private Message message;

    public MessageReceiveEvent(Message message) {
        this.message = message;
    }

    public Message getMessage() {
        return message;
    }

    public MessageReceiveEvent setMessage(Message message) {
        this.message = message;
        return this;
    }
}
