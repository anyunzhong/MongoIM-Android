package im.mongo.ui.view.template;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.datafans.android.common.helper.DipHelper;
import net.datafans.android.common.helper.LogHelper;
import net.datafans.android.common.helper.TimeHelper;
import net.datafans.android.common.widget.popup.PopItem;
import net.datafans.android.common.widget.popup.PopupView;
import net.datafans.android.common.widget.table.TableViewCell;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.greenrobot.event.EventBus;
import im.mongo.MongoIM;
import im.mongo.R;
import im.mongo.core.model.Message;
import im.mongo.core.model.content.MessageContent;
import im.mongo.ui.event.MessageDeleteEvent;

/**
 * Created by zhonganyun on 16/2/5.
 */
public abstract class BaseMessageTemplate extends TableViewCell<Message> {

    private TextView timeLabel;

    protected int screenWidth;

    protected LayoutInflater inflater;

    protected FrameLayout baseContentView;

    protected Message message;

    private Activity currentActivity;

    public BaseMessageTemplate() {
        super(R.layout.base_message_cell, MongoIM.sharedInstance().getContext());

        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);

        screenWidth = DipHelper.px2dip(context, wm.getDefaultDisplay().getWidth());

        inflater = LayoutInflater.from(context);

        LinearLayout rootView = new LinearLayout(context);
        LinearLayout.LayoutParams rootParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rootParams.setMargins(0, 40, 0, 40);
        rootView.setOrientation(LinearLayout.VERTICAL);
        cell.addView(rootView, rootParams);


        timeLabel = new TextView(context);
        LinearLayout.LayoutParams timeParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        timeParams.gravity = Gravity.CENTER_HORIZONTAL;
        timeParams.setMargins(0, 40, 0, 20);
        timeLabel.setBackgroundResource(R.drawable.corner_time_label);
        timeLabel.setGravity(Gravity.CENTER);
        timeLabel.setPadding(12, 7, 12, 7);
        timeLabel.setTextSize(11);
        timeLabel.setTextColor(Color.WHITE);
        rootView.addView(timeLabel, timeParams);

        //中间主体内容
        baseContentView = new FrameLayout(context);
        //RelativeLayout.LayoutParams mainContentParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //mainContentView.setBackgroundColor(Color.RED);
        rootView.addView(baseContentView);
    }

    @Override
    protected void refresh(Message message) {

        this.message = message;
        timeLabel.setText(TimeHelper.prettyTime(message.getReceivedTime()));
        if (message.isShowTime())
            timeLabel.setVisibility(View.VISIBLE);
        else
            timeLabel.setVisibility(View.GONE);
    }


    protected void showMenu() {

        List<PopItem> allItems = new ArrayList<>();
        allItems.add(new PopItem("删除", new PopItem.Listener() {
            @Override
            public void onClick() {
                MessageDeleteEvent deleteEvent = new MessageDeleteEvent();
                deleteEvent.setMessageId(message.getMessageId());
                EventBus.getDefault().post(deleteEvent);
            }
        }));
        allItems.addAll(getPopItems());

        PopupView popupView = new PopupView(getCurrentActivity(), ((ViewGroup) getCurrentActivity().findViewById(android.R.id.content)).getChildAt(0), true);
        popupView.setItems(allItems);
        popupView.show();
    }

    protected List<PopItem> getPopItems() {
        return new ArrayList<>();
    }

    protected void onClickMessage() {
        MessageTemplateManager.ClickHandler handler = MessageTemplateManager.sharedInstance().getClickHandler(message.getContentType());
        if (handler == null) {
            onClickMessageDefault();
        } else {
            handler.onClickMessage(message, getCurrentActivity());
        }
    }

    protected void onClickMessageDefault() {

    }

    public Activity getCurrentActivity() {
        return currentActivity;
    }

    public void setCurrentActivity(Activity currentActivity) {
        this.currentActivity = currentActivity;
    }

    @Target({ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    public static @interface Tag {
        Class<? extends MessageContent> model();
    }

}
