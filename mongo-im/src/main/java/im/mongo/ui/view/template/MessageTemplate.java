package im.mongo.ui.view.template;

import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import net.datafans.android.common.helper.DipHelper;
import net.datafans.android.common.widget.imageview.CommonImageView;

import im.mongo.MongoIM;
import im.mongo.R;
import im.mongo.core.model.Conversation;
import im.mongo.core.model.Message;
import im.mongo.ui.provider.UserInfo;
import im.mongo.ui.provider.UserInfoProvider;

/**
 * Created by zhonganyun on 15/6/22.
 */
public class MessageTemplate extends BaseMessageTemplate {

    protected static final int MAX_WIDTH = 700;

    private static final int AVATAR_MARGIN_HORIZONTAL = 13;
    private static final int AVATAR_MARGIN_TOP = 5;
    protected static final int AVATAR_SIZE = 48;
    private static final int BODY_MARGIN = 65;
    private static final int MESSAGE_STATUS_VIEW_SIZE = 80;


    private CommonImageView avatarView;

    private TextView nickView;

    protected RelativeLayout messageContentView;


    private RelativeLayout bottomContentView;
    private RelativeLayout topContentView;

    private RelativeLayout.LayoutParams avatarParams;
    private RelativeLayout.LayoutParams bodyParams;

    private RelativeLayout leftStatusRelativeLayout;

    private ProgressBar sendingBar;

    private ImageView sentFailView;

    protected boolean isSender;

    private UserInfo userInfo;


    public MessageTemplate() {

        bottomContentView = new RelativeLayout(context);
        baseContentView.addView(bottomContentView);

        avatarView = new CommonImageView(context);
        //avatarView.setBackgroundColor(Color.RED);
        avatarParams = new RelativeLayout.LayoutParams(DipHelper.dip2px(context, AVATAR_SIZE), DipHelper.dip2px(context, AVATAR_SIZE));
        bottomContentView.addView(avatarView, avatarParams);


        topContentView = new RelativeLayout(context);
        baseContentView.addView(topContentView);

        LinearLayout bodyView = new LinearLayout(context);
        bodyView.setOrientation(LinearLayout.VERTICAL);
        //bodyView.setBackgroundColor(Color.GREEN);
        bodyParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        topContentView.addView(bodyView, bodyParams);

        nickView = new TextView(context);
        //nickView.setText("AllenAllenAllenAllenAllenAllen");
        nickView.setTextSize(14);
        nickView.setTextColor(Color.rgb(180, 180, 180));
        nickView.setTextColor(Color.WHITE);
        bodyView.addView(nickView);

        LinearLayout contentView = new LinearLayout(context);
        bodyView.addView(contentView);

        leftStatusRelativeLayout = new RelativeLayout(context);
        RelativeLayout.LayoutParams statusRelativeParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        contentView.addView(leftStatusRelativeLayout, statusRelativeParams);
        //leftStatusRelativeLayout.setBackgroundColor(Color.RED);

        messageContentView = new RelativeLayout(context);
        RelativeLayout.LayoutParams contentViewParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //messageContentView.setBackgroundColor(Color.YELLOW);
        contentView.addView(messageContentView, contentViewParams);

        messageContentView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                showMenu();
                return true;
            }
        });

        messageContentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickMessage();
            }
        });


        FrameLayout statusView = (FrameLayout) inflater.inflate(R.layout.base_message_cell_status, null);
        RelativeLayout.LayoutParams statusParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        sendingBar = (ProgressBar) statusView.findViewById(R.id.indicator);
        sentFailView = (ImageView) statusView.findViewById(R.id.image_send_fail);

        statusParams.addRule(RelativeLayout.CENTER_VERTICAL);
        leftStatusRelativeLayout.addView(statusView, statusParams);

    }


    @Override
    protected void refresh(Message message) {

        super.refresh(message);

        isSender = message.getDirection() == Message.Direction.SEND;


        if (!isSender) {
            bottomContentView.setGravity(Gravity.START);
            topContentView.setGravity(Gravity.START);
            avatarParams.setMargins(DipHelper.dip2px(context, AVATAR_MARGIN_HORIZONTAL), AVATAR_MARGIN_TOP, 0, 0);
            bodyParams.setMargins(DipHelper.dip2px(context, BODY_MARGIN), 0, 50, 0);
            messageContentView.setPadding(0, 0, 30, 0);

        } else {
            bottomContentView.setGravity(Gravity.END);
            topContentView.setGravity(Gravity.END);
            avatarParams.setMargins(0, AVATAR_MARGIN_TOP, DipHelper.dip2px(context, AVATAR_MARGIN_HORIZONTAL), 0);
            bodyParams.setMargins(50, 0, DipHelper.dip2px(context, BODY_MARGIN), 0);
            messageContentView.setPadding(30, 0, 0, 0);
        }


        //昵称
        if (!isSender && (message.getConversationType() == Conversation.Type.DISCUSSION || message.getConversationType() == Conversation.Type.GROUP || message.getConversationType() == Conversation.Type.ROOM)) {
            nickView.setVisibility(View.VISIBLE);
        } else {
            nickView.setVisibility(View.GONE);
        }

        if (userInfo == null) {

            UserInfoProvider provider = MongoIM.sharedInstance().getUserInfoProvider();
            provider.getUserInfo(message.getSenderId(), new UserInfoProvider.Callback() {
                @Override
                public void callback(UserInfo userInfo) {
                    if (userInfo == null) return;
                    if (userInfo.getAvatar() == null || userInfo.getAvatar().equals("")) {
                        avatarView.getImageView().setImageResource(R.mipmap.userhead);
                    } else {
                        avatarView.loadImage(userInfo.getAvatar());
                    }
                    nickView.setText(userInfo.getNick());
                }
            });
        } else {
            if (userInfo.getAvatar() == null || userInfo.getAvatar().equals("")) {
                avatarView.getImageView().setImageResource(R.mipmap.userhead);
            } else {
                avatarView.loadImage(userInfo.getAvatar());
            }
            nickView.setText(userInfo.getNick());
        }


        if (isSender) {
            switch (message.getSendStatus()) {
                case SENDING: {
                    sendingBar.setVisibility(View.VISIBLE);
                    sentFailView.setVisibility(View.GONE);
                    break;
                }

                case SENT: {
                    sendingBar.setVisibility(View.GONE);
                    sentFailView.setVisibility(View.GONE);
                    break;
                }

                case FAILED: {
                    sendingBar.setVisibility(View.GONE);
                    sentFailView.setVisibility(View.VISIBLE);
                    break;
                }
            }
        } else {
            sendingBar.setVisibility(View.GONE);
            sentFailView.setVisibility(View.GONE);
        }


    }


}
