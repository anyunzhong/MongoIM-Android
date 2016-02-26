package im.mongo.ui.event;

import net.datafans.android.common.event.BaseEvent;

import im.mongo.ui.emotion.PackageEmotionItem;

/**
 * Created by zhonganyun on 15/9/15.
 */
public class PackageEmotionClickEvent extends BaseEvent {
    private PackageEmotionItem emotionItem;

    public PackageEmotionClickEvent(PackageEmotionItem emotionItem) {
        this.emotionItem = emotionItem;
    }

    public PackageEmotionItem getEmotionItem() {
        return emotionItem;
    }

    public void setEmotionItem(PackageEmotionItem emotionItem) {
        this.emotionItem = emotionItem;
    }
}
