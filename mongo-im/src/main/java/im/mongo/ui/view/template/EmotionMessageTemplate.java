package im.mongo.ui.view.template;

import android.view.ViewGroup;
import android.widget.RelativeLayout;

import net.datafans.android.common.helper.DipHelper;
import net.datafans.android.common.widget.imageview.GIFImageView;

import im.mongo.core.model.Message;
import im.mongo.core.model.content.MessageContent;
import im.mongo.core.model.content.media.EmotionMessage;

/**
 * Created by zhonganyun on 15/6/22.
 */

@MessageTemplate.Tag(model = EmotionMessage.class)
public class EmotionMessageTemplate extends MessageTemplate {


    private GIFImageView imageView;

    public EmotionMessageTemplate() {

        imageView = new GIFImageView(context);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        int margin = DipHelper.dip2px(context, 10);
        params.setMargins(margin, margin, margin, margin);
        messageContentView.addView(imageView, params);
    }


    @Override
    protected void refresh(Message message) {
        super.refresh(message);

        MessageContent content = message.getContent();
        EmotionMessage emotionMessage = null;
        if (content instanceof EmotionMessage)
            emotionMessage = (EmotionMessage) content;
        if (emotionMessage == null)
            return;

        imageView.load(emotionMessage.getEmotionItem().getRemoteGif());
    }

}
