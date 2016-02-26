package im.mongo.ui.event;

import net.datafans.android.common.event.BaseEvent;

/**
 * Created by zhonganyun on 16/2/12.
 */
public class MessageDeleteEvent extends BaseEvent {
    private int messageId;

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }
}
