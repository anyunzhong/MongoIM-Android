package im.mongo.ui.event;

import net.datafans.android.common.event.BaseEvent;

import im.mongo.core.model.content.media.TextMessage;

/**
 * Created by zhonganyun on 16/1/31.
 */
public class MessageFailEvent extends BaseEvent {
    private int messageId;

    public MessageFailEvent(int messageId){
        this.messageId = messageId;
    }

    public int getMessageId() {
        return messageId;
    }

    public MessageFailEvent setMessageId(int messageId) {
        this.messageId = messageId;
        return this;
    }
}
