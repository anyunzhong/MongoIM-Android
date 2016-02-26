package im.mongo.ui.event;

import net.datafans.android.common.event.BaseEvent;

/**
 * Created by zhonganyun on 15/9/15.
 */
public class EmojiClickEvent extends BaseEvent {
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
