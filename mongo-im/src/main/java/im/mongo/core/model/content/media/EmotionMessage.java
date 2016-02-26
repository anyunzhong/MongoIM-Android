package im.mongo.core.model.content.media;

import im.mongo.core.model.content.MessageContent;
import im.mongo.ui.emotion.PackageEmotionItem;

/**
 * Created by zhonganyun on 16/2/9.
 */
@MessageContent.Tag(type = MessageContent.Type.EMOTION, action = MessageContent.Tag.PERSIST | MessageContent.Tag.COUNT)
public class EmotionMessage extends MediaMessage {

    private PackageEmotionItem emotionItem;

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

    public PackageEmotionItem getEmotionItem() {
        return emotionItem;
    }

    public void setEmotionItem(PackageEmotionItem emotionItem) {
        this.emotionItem = emotionItem;
    }
}
