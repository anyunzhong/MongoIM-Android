package im.mongo.demo;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.widget.Toast;

import net.datafans.android.common.AndroidCommon;
import net.datafans.android.common.widget.imageview.ImageViewType;

import java.util.ArrayList;
import java.util.List;

import im.mongo.MongoIM;
import im.mongo.core.model.Conversation;
import im.mongo.core.model.Message;
import im.mongo.core.model.content.MessageContent;
import im.mongo.core.model.content.media.LocationMessage;
import im.mongo.demo.extend.rong.handler.RongCloudMessageHandler;
import im.mongo.demo.plugin.location.LocationController;
import im.mongo.demo.plugin.location.LocationPluginProvider;
import im.mongo.demo.plugin.video.VideoPluginProvider;
import im.mongo.handler.MessageHandler;
import im.mongo.handler.MongoMessageHandler;
import im.mongo.ui.emotion.PackageEmotion;
import im.mongo.ui.emotion.PackageEmotionItem;
import im.mongo.ui.plugin.favourite.FavouriteChooseController;
import im.mongo.ui.plugin.favourite.FavouritePluginProvider;
import im.mongo.ui.plugin.namecard.NameCardChooseController;
import im.mongo.ui.plugin.namecard.NameCardPluginProvider;
import im.mongo.ui.plugin.redbag.RedBagCreateController;
import im.mongo.ui.plugin.redbag.RedBagPluginProvider;
import im.mongo.ui.provider.UserInfo;
import im.mongo.ui.provider.UserInfoProvider;
import im.mongo.ui.view.template.MessageTemplateManager;


/**
 * Created by zhonganyun on 15/11/20.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //MultiDex.install(this);

        //初始化
        MongoIM im = MongoIM.sharedInstance();
        im.init(this);

        MessageHandler mongoMessageHandler = new MongoMessageHandler("100000");


        //设置消息处理器为融云
        String token = "faO5NqYgpb9vIDoZ1zuROIq5DctN/R+V4QoOwF9US6Z6ZBIJAqt8na6N+CACEbVqpxU8QP7jU3RttHSdno8Ncw==";
        MessageHandler messageHandler = new RongCloudMessageHandler(this, token);
        im.setMessageHandler(messageHandler);


        //设置用户信息
        im.setUserInfoProvider(new UserInfoProvider() {

            @Override
            public void getUserInfo(String userId, Callback callback) {
                UserInfo userInfo;
                switch (userId) {
                    case "100010":
                        userInfo = new UserInfo(userId, "Allen", "http://file-cdn.datafans.net/avatar/1.jpeg");
                        break;
                    case "100020":
                        userInfo = new UserInfo(userId, "Yanhua", "http://file-cdn.datafans.net/avatar/2.jpg");
                        break;
                    default:
                        userInfo = new UserInfo(userId, userId, "");
                }

                //userInfo可以直接从本地获取 也可以从网络获取后 然后callback
                callback.callback(userInfo);
            }
        });

        //增加小视频插件
        im.registerPlugin(VideoPluginProvider.class, Conversation.Type.PRIVATE);

        //增加地图插件
        im.registerPlugin(LocationPluginProvider.class, Conversation.Type.PRIVATE);


        //重新配置插件
        //im.configPluginProviders(Conversation.Type.PRIVATE, new PluginProvider[]{new PhotoAlbumPluginProvider()});


        //添加表情包测试

        //模拟一组小黄鸡动态表情 一共32个
        List<PackageEmotionItem> items = new ArrayList<>();
        for (int i = 1; i <= 32; i++) {
            PackageEmotionItem item = new PackageEmotionItem();
            item.setName("小黄鸡" + i);
            item.setRemoteGif("http://file-cdn.datafans.net/emotion/yellow_chicken/gif/" + i + ".gif");
            item.setRemoteThumb("http://file-cdn.datafans.net/emotion/yellow_chicken/png/" + i + ".png");
            items.add(item);
        }

        String tabIconPath = "http://file-cdn.datafans.net/emotion/yellow_chicken/icon3x.png";
        PackageEmotion emotion = new PackageEmotion(tabIconPath, items);
        MongoIM.sharedInstance().addPackageEmotion(emotion);

        //模拟一组小鸟动态表情 一共16个
        List<PackageEmotionItem> items2 = new ArrayList<>();
        for (int i = 1; i <= 16; i++) {
            PackageEmotionItem item = new PackageEmotionItem();
            item.setName("小小鸟" + i);
            item.setRemoteGif("http://file-cdn.datafans.net/emotion/bird/gif/" + i + ".gif");
            item.setRemoteThumb("http://file-cdn.datafans.net/emotion/bird/png/" + i + ".png");
            items2.add(item);
        }

        String tabIconPath2 = "http://file-cdn.datafans.net/emotion/bird/icon3x.png";
        PackageEmotion emotion2 = new PackageEmotion(tabIconPath2, items2);
        MongoIM.sharedInstance().addPackageEmotion(emotion2);


        //设置点击插件后需要推入的自定义controller
        //默认已经实现的有 照片选择 拍照 选地点 如果需要自定义弹出界面 直接通过注册的方式覆盖原有实现即可
        //其它的都需要你去实现 通过传入的plugin来发送消息 发送后退出当前controller
        //如果是自定义plugin 则直接覆盖基类onClick方法即可实现相同效果
        MongoIM.sharedInstance().registerPluginPresentController(RedBagPluginProvider.class, RedBagCreateController.class);
        MongoIM.sharedInstance().registerPluginPresentController(NameCardPluginProvider.class, NameCardChooseController.class);
        MongoIM.sharedInstance().registerPluginPresentController(FavouritePluginProvider.class, FavouriteChooseController.class);


        //设置点击消息后的处理方式
        //默认实现的有 图片 分享 位置三种消息 如果要设置系统自带消息的点击行为 则通过注册消息点击handler的方式执行
        //如果是自己定义的消息显示cell 则直接覆盖基类onClickMessage方法即可实现同样效果

        MongoIM.sharedInstance().registerClickHandler(MessageContent.Type.RED_BAG, new MessageTemplateManager.ClickHandler() {
            @Override
            public void onClickMessage(Message message, Activity activity) {

                Toast toast = Toast.makeText(activity, "点击了消息", Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        MongoIM.sharedInstance().registerClickHandler(MessageContent.Type.LOCATION, new MessageTemplateManager.ClickHandler() {
            @Override
            public void onClickMessage(Message message, Activity activity) {

                MessageContent content = message.getContent();
                if (content instanceof LocationMessage) {
                    LocationMessage locationMessage = (LocationMessage) content;
                    Intent intent = new Intent(activity, LocationController.class);
                    intent.putExtra("lat", locationMessage.getLat());
                    intent.putExtra("lng", locationMessage.getLng());
                    activity.startActivity(intent);
                }
            }
        });

    }
}
