package im.mongo.ui.view.template;

import android.graphics.Color;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import net.datafans.android.common.helper.LogHelper;
import net.datafans.android.common.widget.imageview.MaskImageView;
import net.datafans.android.common.widget.photobrowser.Photo;
import net.datafans.android.common.widget.photobrowser.PhotoBrowser;

import java.util.ArrayList;
import java.util.List;

import im.mongo.MongoIM;
import im.mongo.R;
import im.mongo.core.model.Message;
import im.mongo.core.model.content.MessageContent;
import im.mongo.core.model.content.media.ImageMessage;
import im.mongo.handler.MessageHandler;

/**
 * Created by zhonganyun on 15/6/22.
 */
@MessageTemplate.Tag(model = ImageMessage.class)
public class ImageMessageTemplate extends MessageTemplate {


    private static final int MAX_WIDTH = 450;
    private MaskImageView imageView;

    public ImageMessageTemplate() {

        imageView = new MaskImageView(context);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        messageContentView.addView(imageView, params);
    }


    @Override
    protected void refresh(Message message) {
        super.refresh(message);

        MessageContent content = message.getContent();
        ImageMessage imageMessage = null;
        if (content instanceof ImageMessage)
            imageMessage = (ImageMessage) content;
        if (imageMessage == null)
            return;

        int mask;
        String maskAlias;
        if (message.getDirection() == Message.Direction.SEND) {
            mask = R.drawable.sender_text_node;
            maskAlias = "sender_text_node6";
        } else {
            mask = R.drawable.receiver_text_node;
            maskAlias = "receiver_text_node6";
        }

        LogHelper.debug("url: " + imageMessage.getRemoteUrl());
        imageView.load(imageMessage.getRemoteUrl(), imageMessage.getThumbData(), mask, maskAlias, MAX_WIDTH);

    }


    @Override
    protected void onClickMessageDefault() {

        //获得图片
        MessageHandler handler = MongoIM.sharedInstance().getMessageHandler();
        List<Message> messages = handler.getImageMessages(message.getConversationType(), message.getTargetId(), 100);//测试加载最多一百张

        List<Photo> photos = new ArrayList<>();
        int currentIndex = 0;
        for (int i = 0; i < messages.size(); i++) {
            Message msg = messages.get(i);
            if (msg.getMessageId() == message.getMessageId()) {
                currentIndex = i;
            }
            ImageMessage imageMessage = (ImageMessage)msg.getContent();
            Photo photo = new Photo();
            photo.setUrl(imageMessage.getRemoteUrl());
            photos.add(photo);
        }

        PhotoBrowser browser = new PhotoBrowser(getCurrentActivity(), ((ViewGroup)getCurrentActivity().findViewById(android.R.id.content)).getChildAt(0));
        browser.addPhoto(photos);
        browser.setCurrentPage(currentIndex);
        browser.show();

    }
}
