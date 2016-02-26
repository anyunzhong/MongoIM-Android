package im.mongo.ui.view.template;

import android.widget.RelativeLayout;

import net.datafans.android.common.helper.DipHelper;

import im.mongo.R;
import im.mongo.core.model.Message;
import im.mongo.core.model.content.MessageContent;
import im.mongo.core.model.content.media.VoiceMessage;
import im.mongo.ui.view.voiceplay.VoicePlayButton;

/**
 * Created by zhonganyun on 15/6/22.
 */
@MessageTemplate.Tag(model = VoiceMessage.class)
public class VoiceMessageTemplate extends MessageTemplate {

    private RelativeLayout bubble;

    private VoicePlayButton voicePlayButton;

    public VoiceMessageTemplate() {

        bubble = new RelativeLayout(context);

        RelativeLayout.LayoutParams bubbleParams = new RelativeLayout.LayoutParams(DipHelper.dip2px(context, 300), DipHelper.dip2px(context, 63));
        messageContentView.addView(bubble, bubbleParams);

        voicePlayButton = new VoicePlayButton(context);
        bubble.addView(voicePlayButton);
    }


    @Override
    protected void refresh(Message message) {
        super.refresh(message);

        MessageContent content = message.getContent();
        VoiceMessage voiceMessage = null;
        if (content instanceof VoiceMessage)
            voiceMessage = (VoiceMessage) content;
        if (voiceMessage == null)
            return;

        RelativeLayout.LayoutParams voicePlayParams = (RelativeLayout.LayoutParams) voicePlayButton.getLayoutParams();
        RelativeLayout.LayoutParams bubbleParams = (RelativeLayout.LayoutParams) bubble.getLayoutParams();

        int width = 20 * voiceMessage.getDuration();
        if (width < 70) width = 70;
        bubbleParams.width = DipHelper.dip2px(context, width);

        if (message.getDirection() == Message.Direction.SEND) {
            bubble.setBackgroundResource(R.drawable.sender_text_node);
            voicePlayParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
            //voicePlayParams.removeRule(RelativeLayout.ALIGN_PARENT_LEFT);

        } else {
            bubble.setBackgroundResource(R.drawable.receiver_text_node);

            voicePlayParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
            //voicePlayParams.removeRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        }


        voicePlayButton.update(context, message);


    }
}
