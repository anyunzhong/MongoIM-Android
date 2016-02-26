package im.mongo.ui.view.template;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.datafans.android.common.helper.DipHelper;
import net.datafans.android.common.helper.ResHelper;
import net.datafans.android.common.helper.face.FaceHelper;
import net.datafans.android.common.widget.popup.PopItem;

import java.util.ArrayList;
import java.util.List;

import im.mongo.R;
import im.mongo.core.model.Message;
import im.mongo.core.model.content.MessageContent;
import im.mongo.core.model.content.media.TextMessage;

/**
 * Created by zhonganyun on 15/6/22.
 */

@MessageTemplate.Tag(model = TextMessage.class)
public class TextMessageTemplate extends MessageTemplate {

    private TextView textView;

    public TextMessageTemplate() {

        textView = new TextView(context);
        LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        textView.setTextSize(17);
        textView.setMinHeight(DipHelper.dip2px(context, AVATAR_SIZE + 14));
        //textView.setLineSpacing(0f, 1.2f);
        textView.setGravity(Gravity.CENTER_VERTICAL);
        textView.setTextColor(Color.BLACK);
        messageContentView.addView(textView, textParams);

    }


    @Override
    protected void refresh(Message message) {
        super.refresh(message);

        if (isSender) {
            textView.setBackgroundResource(R.drawable.sender_text_node);
        } else {
            textView.setBackgroundResource(R.drawable.receiver_text_node);

        }

        MessageContent content = message.getContent();
        TextMessage textMessage = null;
        if (content instanceof TextMessage)
            textMessage = (TextMessage) content;
        if (textMessage == null)
            return;

        //替换表情标签
        String source = textMessage.getParsedText();
        if (source == null) {
            source = FaceHelper.replace(context, textMessage.getText());
            textMessage.setParsedText(source);
        }

        textView.setText(Html.fromHtml(source, imageGetter, null));

    }


    private Html.ImageGetter imageGetter = new Html.ImageGetter() {
        @Override
        public Drawable getDrawable(String source) {
            int resId = ResHelper.getMipmapResId(source);
            Drawable d = context.getResources().getDrawable(resId);
            if (d != null)
                d.setBounds(0, 0, (int) (d.getIntrinsicWidth() * 1.5), (int) (d.getIntrinsicHeight() * 1.5));
            return d;
        }
    };

    @Override
    protected List<PopItem> getPopItems() {
        List<PopItem> items = new ArrayList<>();
        items.add(new PopItem("复制", new PopItem.Listener() {
            @Override
            public void onClick() {
                ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                cmb.setPrimaryClip(ClipData.newPlainText("text", ((TextMessage) message.getContent()).getText()));
            }
        }));
        return items;
    }


}
