package im.mongo.ui.view.template;

import android.graphics.Color;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import net.datafans.android.common.helper.LogHelper;
import net.datafans.android.common.widget.imageview.MaskImageView;

import im.mongo.R;
import im.mongo.core.model.Message;
import im.mongo.core.model.content.MessageContent;
import im.mongo.core.model.content.media.LocationMessage;

/**
 * Created by zhonganyun on 15/6/22.
 */

@MessageTemplate.Tag(model = LocationMessage.class)
public class LocationMessageTemplate extends MessageTemplate {


    private MaskImageView imageView;

    private TextView addressView;


    public LocationMessageTemplate() {


        RelativeLayout relativeLayout = new RelativeLayout(context);
        RelativeLayout.LayoutParams frameParams = new RelativeLayout.LayoutParams(MAX_WIDTH, ViewGroup.LayoutParams.WRAP_CONTENT);
        messageContentView.addView(relativeLayout, frameParams);

        imageView = new MaskImageView(context);
        RelativeLayout.LayoutParams imageParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        relativeLayout.addView(imageView, imageParams);


        addressView = new TextView(context);
        RelativeLayout.LayoutParams addressParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        addressParams.setMargins(40, 322, 20, 10);
        addressView.setTextColor(Color.WHITE);
        addressView.setTextSize(11);
        addressView.setSingleLine(true);
        addressView.setEllipsize(TextUtils.TruncateAt.END);
        relativeLayout.addView(addressView, addressParams);

        ImageView pinView = new ImageView(context);
        pinView.setImageResource(R.mipmap.fileicon_loc);
        RelativeLayout.LayoutParams pingParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        pingParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        pingParams.setMargins(0, 60, 0, 0);
        relativeLayout.addView(pinView, pingParams);


    }


    @Override
    protected void refresh(Message message) {
        super.refresh(message);

        MessageContent content = message.getContent();
        LocationMessage locationMessage = null;
        if (content instanceof LocationMessage)
            locationMessage = (LocationMessage) content;
        if (locationMessage == null)
            return;


        if (locationMessage.getMapStaticUrl() == null) {
            String url = String.format("http://restapi.amap.com/v3/staticmap?location=%f,%f&zoom=12&size=224*134&key=f7548383632a917fcd362ee2ccc8d928&scale=2", locationMessage.getLng(), locationMessage.getLat());
            locationMessage.setMapStaticUrl(url);
        }

        int mask;
        String maskAlias;
        if (isSender) {
            mask = R.drawable.sender_text_node;
            maskAlias = "sender_text_node6";
        } else {
            mask = R.drawable.receiver_text_node;
            maskAlias = "receiver_text_node6";
        }


        LogHelper.debug("url: " + locationMessage.getMapStaticUrl());
        imageView.load(locationMessage.getMapStaticUrl(), locationMessage.getThumbImage(), mask, maskAlias, MAX_WIDTH, true);

        addressView.setText(locationMessage.getAddress());

    }
}
