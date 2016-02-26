package im.mongo.ui.view.template;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.datafans.android.common.helper.LogHelper;
import net.datafans.android.common.widget.imageview.CommonImageView;

import im.mongo.R;
import im.mongo.core.model.Message;
import im.mongo.core.model.content.MessageContent;
import im.mongo.core.model.content.media.NameCardMessage;

/**
 * Created by zhonganyun on 15/6/22.
 */

@MessageTemplate.Tag(model = NameCardMessage.class)
public class NameCardMessageTemplate extends MessageTemplate {


    private TextView nickView;
    private TextView numView;
    private CommonImageView avatarView;
    private View rootView;

    public NameCardMessageTemplate() {

        rootView = inflater.inflate(R.layout.name_card_msg_tpl, null);
        LinearLayout.LayoutParams viewParams = new LinearLayout.LayoutParams(MAX_WIDTH, ViewGroup.LayoutParams.WRAP_CONTENT);
        messageContentView.addView(rootView, viewParams);


        nickView = (TextView) rootView.findViewById(R.id.nick);
        numView = (TextView) rootView.findViewById(R.id.num);
        avatarView = (CommonImageView) rootView.findViewById(R.id.avatar);

    }


    @Override
    protected void refresh(Message message) {
        super.refresh(message);

        if (isSender) {
            rootView.setBackgroundResource(R.drawable.sender_app_node_bg);
        } else {
            rootView.setBackgroundResource(R.drawable.receiver_app_node_bg);

        }

        MessageContent content = message.getContent();
        NameCardMessage nameCardMessage = null;
        if (content instanceof NameCardMessage)
            nameCardMessage = (NameCardMessage) content;
        if (nameCardMessage == null)
            return;

        nickView.setText(nameCardMessage.getUserNick());
        numView.setText(nameCardMessage.getUserNum());
        avatarView.loadImage(nameCardMessage.getUserAvatar());

    }

}
