package im.mongo.ui.view.template;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.datafans.android.common.helper.DipHelper;
import net.datafans.android.common.widget.imageview.CommonImageView;

import im.mongo.R;
import im.mongo.core.model.Message;
import im.mongo.core.model.content.MessageContent;
import im.mongo.core.model.content.media.ShareMessage;
import im.mongo.ui.plugin.controller.WebViewController;

/**
 * Created by zhonganyun on 15/6/22.
 */

@MessageTemplate.Tag(model = ShareMessage.class)
public class ShareMessageTemplate extends MessageTemplate {

    private View bodyView;
    private TextView titleView;
    private TextView descView;
    private CommonImageView thumbView;

    private CommonImageView sourceLogoView;
    private TextView sourceNameView;

    public ShareMessageTemplate() {

        View view = inflater.inflate(R.layout.share_msg_tpl, null);
        LinearLayout.LayoutParams viewParams = new LinearLayout.LayoutParams(MAX_WIDTH, ViewGroup.LayoutParams.WRAP_CONTENT);
        messageContentView.addView(view, viewParams);

        bodyView = view.findViewById(R.id.body);
        titleView = (TextView) view.findViewById(R.id.title);
        descView = (TextView) view.findViewById(R.id.desc);
        thumbView = (CommonImageView) view.findViewById(R.id.thumb);

        sourceNameView = (TextView) view.findViewById(R.id.sourceName);
        sourceLogoView = (CommonImageView) view.findViewById(R.id.sourceLogo);
    }


    @Override
    protected void refresh(Message message) {
        super.refresh(message);

        if (isSender) {
            bodyView.setBackgroundResource(R.drawable.sender_app_node_bg);
        } else {
            bodyView.setBackgroundResource(R.drawable.receiver_app_node_bg);

        }

        MessageContent content = message.getContent();
        ShareMessage shareMessage = null;
        if (content instanceof ShareMessage)
            shareMessage = (ShareMessage) content;
        if (shareMessage == null)
            return;


        titleView.setText(shareMessage.getTitle());
        descView.setText(shareMessage.getDesc());
        thumbView.loadImage(shareMessage.getThumb());

        sourceLogoView.loadImage(shareMessage.getSourceLogo());
        sourceNameView.setText(shareMessage.getSourceName());

    }

    @Override
    protected void onClickMessageDefault() {

        ShareMessage shareMessage = (ShareMessage) message.getContent();
        Intent intent = new Intent(getCurrentActivity(), WebViewController.class);
        intent.putExtra("title", shareMessage.getTitle());
        intent.putExtra("url", shareMessage.getLink());
        getCurrentActivity().startActivity(intent);
    }
}
