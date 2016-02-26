package im.mongo.ui.view.template;

import android.content.Intent;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import net.datafans.android.common.widget.imageview.MaskImageView;

import im.mongo.R;
import im.mongo.core.model.Message;
import im.mongo.core.model.content.MessageContent;
import im.mongo.core.model.content.media.ShortVideoMessage;
import im.mongo.ui.plugin.video.VideoPlayController;

/**
 * Created by zhonganyun on 15/6/22.
 */

@MessageTemplate.Tag(model = ShortVideoMessage.class)
public class ShortVideoMessageTemplate extends MessageTemplate {


    private MaskImageView imageView;

    private ShortVideoMessage videoMessage;

    public ShortVideoMessageTemplate() {


        RelativeLayout relativeLayout = new RelativeLayout(context);
        RelativeLayout.LayoutParams frameParams = new RelativeLayout.LayoutParams(MAX_WIDTH, ViewGroup.LayoutParams.WRAP_CONTENT);
        messageContentView.addView(relativeLayout, frameParams);

        imageView = new MaskImageView(context);
        RelativeLayout.LayoutParams imageParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        relativeLayout.addView(imageView, imageParams);

    }


    @Override
    protected void refresh(Message message) {
        super.refresh(message);

        MessageContent content = message.getContent();
        ShortVideoMessage videoMessage = null;
        if (content instanceof ShortVideoMessage)
            videoMessage = (ShortVideoMessage) content;
        if (videoMessage == null)
            return;

        this.videoMessage = videoMessage;

        int mask;
        String maskAlias;
        if (isSender) {
            mask = R.drawable.sender_text_node;
            maskAlias = "sender_text_node6";
        } else {
            mask = R.drawable.receiver_text_node;
            maskAlias = "receiver_text_node6";
        }

        imageView.load(null, videoMessage.getCover(), mask, maskAlias, MAX_WIDTH);

    }


    @Override
    protected void onClickMessageDefault() {
        Intent intent = new Intent(getCurrentActivity(), VideoPlayController.class);
        intent.putExtra("url", videoMessage.getUrl());
        getCurrentActivity().startActivity(intent);
    }
}
