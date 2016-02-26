package im.mongo.ui.view.conversation;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import net.datafans.android.common.helper.TimeHelper;
import net.datafans.android.common.widget.badge.BadgeView;
import net.datafans.android.common.widget.imageview.CommonImageView;
import net.datafans.android.common.widget.table.TableViewCell;

import im.mongo.MongoIM;
import im.mongo.R;
import im.mongo.core.model.Conversation;
import im.mongo.ui.provider.UserInfo;
import im.mongo.ui.provider.UserInfoProvider;

/**
 * Created by zhonganyun on 15/6/24.
 */
public class ConversationCell extends TableViewCell<Conversation> {

    TextView nickView;

    CommonImageView avatarView;

    TextView subTitleView;

    TextView timeView;

    FrameLayout avatarContainer;

    private BadgeView badge;

    private UserInfo userInfo;

    public ConversationCell(int layout, Context context) {
        super(layout, context);
        arrow.setVisibility(View.GONE);

        nickView = (TextView) cell.findViewById(R.id.nick);
        avatarView = (CommonImageView) cell.findViewById(R.id.avatar);
        subTitleView = (TextView) cell.findViewById(R.id.subTitle);
        timeView = (TextView) cell.findViewById(R.id.time);
        avatarContainer = (FrameLayout) cell.findViewById(R.id.avatarContainer);

    }

    @Override
    protected void refresh(Conversation conversation) {

        if (conversation.getConversationType() == Conversation.Type.PRIVATE) {

            if (userInfo == null) {
                UserInfoProvider provider = MongoIM.sharedInstance().getUserInfoProvider();
                provider.getUserInfo(conversation.getTargetId(), new UserInfoProvider.Callback() {
                    @Override
                    public void callback(UserInfo userInfo) {
                        if (userInfo != null) {
                            nickView.setText(userInfo.getNick());
                            if (userInfo.getAvatar() == null || userInfo.getAvatar().equals("")) {
                                avatarView.getImageView().setImageResource(R.mipmap.userhead);
                            } else {
                                avatarView.loadImage(userInfo.getAvatar());
                            }
                        }
                    }
                });
            }else{
                nickView.setText(userInfo.getNick());
                if (userInfo.getAvatar() == null || userInfo.getAvatar().equals("")) {
                    avatarView.getImageView().setImageResource(R.mipmap.userhead);
                } else {
                    avatarView.loadImage(userInfo.getAvatar());
                }
            }


        }


        subTitleView.setText(conversation.getSubTitle());
        timeView.setText(TimeHelper.prettyTime(conversation.getUpdateTime()));

        if (badge == null) {
            badge = new BadgeView(MongoIM.sharedInstance().getContext(), avatarContainer);
            badge.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);
            badge.setTextColor(Color.WHITE);
            badge.setBadgeBackgroundColor(Color.RED);
            badge.setTextSize(12);
            badge.setBadgeMargin(7, 7);
        }
        badge.setText(String.valueOf(conversation.getUnreadCount()));
        if (conversation.getUnreadCount() > 0)
            badge.show();
        else
            badge.hide();

    }
}
