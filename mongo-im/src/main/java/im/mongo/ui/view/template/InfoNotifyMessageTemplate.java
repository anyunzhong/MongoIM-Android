package im.mongo.ui.view.template;

import android.graphics.Color;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import im.mongo.R;
import im.mongo.core.model.Message;
import im.mongo.core.model.content.MessageContent;
import im.mongo.core.model.content.notify.InfoNotifyMessage;

/**
 * Created by zhonganyun on 16/2/5.
 */

@MessageTemplate.Tag(model = InfoNotifyMessage.class)
public class InfoNotifyMessageTemplate extends BaseMessageTemplate {

    private TextView contentLabel;

    public InfoNotifyMessageTemplate() {

        LinearLayout contentView = new LinearLayout(context);
        LinearLayout.LayoutParams contentParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        contentParams.setMargins(0, 40, 0, 20);
        contentView.setGravity(Gravity.CENTER);
        baseContentView.addView(contentView, contentParams);

        contentLabel = new TextView(context);
        LinearLayout.LayoutParams labelParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        labelParams.setMargins(80, 0, 80, 0);
        contentLabel.setBackgroundResource(R.drawable.corner_time_label);
        contentLabel.setPadding(12, 7, 12, 7);
        contentLabel.setTextSize(12);
        contentLabel.setTextColor(Color.WHITE);
        contentLabel.setGravity(Gravity.CENTER);
        contentView.addView(contentLabel, labelParams);

    }


    @Override
    protected void refresh(Message message) {
        super.refresh(message);

        MessageContent content = message.getContent();
        InfoNotifyMessage infoNotifyMessage = null;
        if (content instanceof InfoNotifyMessage)
            infoNotifyMessage = (InfoNotifyMessage) content;
        if (infoNotifyMessage == null)
            return;

        contentLabel.setText(infoNotifyMessage.getContent());
    }
}
