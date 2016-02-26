package im.mongo.ui.view.template;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import im.mongo.R;
import im.mongo.core.model.Message;
import im.mongo.core.model.content.MessageContent;
import im.mongo.core.model.content.media.RedBagMessage;

/**
 * Created by zhonganyun on 16/2/6.
 */

@MessageTemplate.Tag(model = RedBagMessage.class)
public class RedBagMessageTemplate extends MessageTemplate {

    private View container;

    private TextView titleView;

    public RedBagMessageTemplate() {

        container = LayoutInflater.from(context).inflate(R.layout.red_bag_msg_tpl, null);
        messageContentView.addView(container);

        titleView = (TextView) container.findViewById(R.id.title);
    }


    @Override
    protected void refresh(Message message) {
        super.refresh(message);


        MessageContent content = message.getContent();
        RedBagMessage redBagMessage = null;
        if (content instanceof RedBagMessage)
            redBagMessage = (RedBagMessage) content;
        if (redBagMessage == null)
            return;


        if (isSender) {
            container.setBackgroundResource(R.drawable.sender_money_node_bg);
        } else {
            container.setBackgroundResource(R.drawable.receiver_money_node_bg);
        }

        titleView.setText(redBagMessage.getTitle());


    }
}
