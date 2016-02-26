
MongoIM
============
iOS：[https://github.com/anyunzhong/MongoIM-iOS](https://github.com/anyunzhong/MongoIM-iOS)
<br />
Android：[https://github.com/anyunzhong/MongoIM-Android](https://github.com/anyunzhong/MongoIM-Android)
<br />
Server：开发中....

MongoIM-Android
=============

<br />

即时通信 IM 支持发送文字 语音 图片 短视频 位置 红包 名片...

[![Alt][screenshot1_thumb]][screenshot1]    [![Alt][screenshot2_thumb]][screenshot2]    [![Alt][screenshot3_thumb]][screenshot3]    [![Alt][screenshot4_thumb]][screenshot4]    [![Alt][screenshot5_thumb]][screenshot5]    [![Alt][screenshot6_thumb]][screenshot6]    [![Alt][screenshot7_thumb]][screenshot7]    [![Alt][screenshot8_thumb]][screenshot8]    [![Alt][screenshot9_thumb]][screenshot9]

[screenshot1_thumb]: http://file-cdn.datafans.net/github/mongoimandroid/1.png_250.jpeg
[screenshot1]: http://file-cdn.datafans.net/github/mongoimandroid/1.png
[screenshot2_thumb]: http://file-cdn.datafans.net/github/mongoimandroid/2.png_250.jpeg
[screenshot2]: http://file-cdn.datafans.net/github/mongoimandroid/2.png
[screenshot3_thumb]: http://file-cdn.datafans.net/github/mongoimandroid/3.png_250.jpeg
[screenshot3]: http://file-cdn.datafans.net/github/mongoimandroid/3.png
[screenshot4_thumb]: http://file-cdn.datafans.net/github/mongoimandroid/4.png_250.jpeg
[screenshot4]: http://file-cdn.datafans.net/github/mongoimandroid/4.png
[screenshot5_thumb]: http://file-cdn.datafans.net/github/mongoimandroid/5.png_250.jpeg
[screenshot5]: http://file-cdn.datafans.net/github/mongoimandroid/5.png
[screenshot6_thumb]: http://file-cdn.datafans.net/github/mongoimandroid/6.png_250.jpeg
[screenshot6]: http://file-cdn.datafans.net/github/mongoimandroid/6.png
[screenshot7_thumb]: http://file-cdn.datafans.net/github/mongoimandroid/7.png_250.jpeg
[screenshot7]: http://file-cdn.datafans.net/github/mongoimandroid/7.png
[screenshot8_thumb]: http://file-cdn.datafans.net/github/mongoimandroid/8.png_250.jpeg
[screenshot8]: http://file-cdn.datafans.net/github/mongoimandroid/8.png
[screenshot9_thumb]: http://file-cdn.datafans.net/github/mongoimandroid/9.png_250.jpeg
[screenshot9]: http://file-cdn.datafans.net/github/mongoimandroid/9.png

<br />
架构
============
![http://file-cdn.datafans.net/github/mongoimios/arch.png_600.jpeg](http://file-cdn.datafans.net/github/mongoimios/arch.png_600.jpeg)

<br />
#### UI层风格可切换
######目前已经实现微信风格 你也可以自定义风格 完全自定义风格的相关逻辑还需要开发

<br />
#### 消息处理层可切换
######目前已经对接上融云 支持单聊 群聊 讨论组 聊天室
######其它公有IM云服务还在计划中.....
######IM后段逻辑相对复杂 MongoIM消息处理和服务端还在开发中.... 远期目标是开发一套稳定的可部署在私有云的IM服务


<br />
安装
============
```gradle
compile 'net.datafans:mongo-im:1.0.1'
```

<br />
配置Manifest
===============
```xml
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
    <!-- 获取机型信息权限 -->
    <uses-permission android:name="android.permission.READ_PHONE_STATE" />
    <uses-permission android:name="android.permission.GET_TASKS" />
    <uses-permission android:name="android.permission.INTERACT_ACROSS_USERS_FULL" />

    <!-- 查看 Wi-Fi 状态 -->
    <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
    <!-- 查看网络状态 -->
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    <uses-permission android:name="android.permission.CAMERA" />
    <!-- 录音 -->
    <uses-permission android:name="android.permission.RECORD_AUDIO" />

    <uses-permission android:name="android.permission.MOUNT_UNMOUNT_FILESYSTEMS" />

    <!-- 控制振动器 -->
    <uses-permission android:name="android.permission.VIBRATE" />
    <!-- 防止设备休眠 -->
    <uses-permission android:name="android.permission.WAKE_LOCK" />
    <uses-permission android:name="android.permission.WRITE_SETTINGS" />
    <uses-permission android:name="android.permission.MODIFY_AUDIO_SETTINGS" />
    <uses-permission android:name="android.permission.RECEIVE_BOOT_COMPLETED" />

    <!-- 高德 -->
    <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
    <uses-permission android:name="android.permission.ACCESS_LOCATION_EXTRA_COMMANDS" />

    <uses-sdk tools:overrideLibrary="com.github.hiteshsondhi88.libffmpeg" />
```

<br />
快速开始
===============

####初始化
```java
MongoIM im = MongoIM.sharedInstance();
im.init(this);
```


<br />
####设置消息处理器(消息的接收和存储 连接等)

#####1.默认消息消息处理器 目前还在完善中 需要配合MongoIM服务端项目使用 目前还在开发中

```java
MessageHandler messageHandler = new MongoMessageHandler("发送者ID");
im.setMessageHandler(messageHandler);
```

#####2.融云消息处理器 具体请参考 [http://rongcloud.cn](http://rongcloud.cn)
引入融云框架 需要下载或者直接复制项目中的IMKit模块

```gradle
compile project(':IMKit')
```

在Manifest中添加
```xml
        <!--融云开始-->
        <service
            android:name="io.rong.push.PushService"
            android:process="io.rong.push" > <!-- Waring: The name of the push process can't be changed!!! -->
            <intent-filter>
                <category android:name="android.intent.category.DEFAULT" />

                <action android:name="io.rong.push" />
            </intent-filter>
        </service>
        <service
            android:name="io.rong.push.CommandService"
            android:process="io.rong.push" > <!-- Waring: The name of the push process can't be changed!!! -->
            <intent-filter>
                <category android:name="android.intent.category.DEFAULT" />

                <action android:name="io.rong.command" />
            </intent-filter>
        </service>

        <receiver
            android:name="io.rong.push.PushReceiver"
            android:process="io.rong.push" > <!-- Waring: The name of the push process can't be changed!!! -->
            <intent-filter>
                <action android:name="io.rong.imlib.action.push.heartbeat" />
            </intent-filter>
            <intent-filter>
                <action android:name="android.net.conn.CONNECTIVITY_CHANGE" />
            </intent-filter>
            <intent-filter>
                <action android:name="android.intent.action.BOOT_COMPLETED" />
            </intent-filter>
            <intent-filter>
                <action android:name="android.intent.action.USER_PRESENT" />
                <action android:name="android.intent.action.ACTION_POWER_CONNECTED" />
                <action android:name="android.intent.action.ACTION_POWER_DISCONNECTED" />
            </intent-filter>
            <intent-filter>
                <action android:name="android.intent.action.PACKAGE_REMOVED" />

                <data android:scheme="package" />
            </intent-filter>
        </receiver>

        <activity
            android:name="io.rong.imkit.tools.SelectPictureActivity"
            android:screenOrientation="portrait" />
        <activity
            android:name="io.rong.imkit.tools.PreviewPictureActivity"
            android:screenOrientation="portrait" />
        <activity
            android:name="io.rong.imkit.tools.RongWebviewActivity"
            android:screenOrientation="portrait" />
        <activity
            android:name="io.rong.imkit.widget.provider.TakingPicturesActivity"
            android:configChanges="orientation|keyboardHidden"
            android:screenOrientation="portrait" />

        <service
            android:name="io.rong.imlib.ipc.RongService"
            android:process=":ipc" />
        <service android:name="io.rong.imlib.ReConnectService" />

        <receiver android:name="io.rong.imlib.ConnectChangeReceiver" />
        <receiver android:name="io.rong.imlib.ipc.PushMessageReceiver" >
            <intent-filter>
                <action android:name="io.rong.push.message" />
            </intent-filter>
        </receiver>
        <receiver
            android:name="io.rong.imlib.HeartbeatReceiver"
            android:process=":ipc" />

        <meta-data
            android:name="RONG_CLOUD_APP_KEY"
            android:value="0vnjpoadnzn1z" />

        <!--融云结束-->
```

设置消息处理器为融云
```java
 String token = "通过融云api获取到的token";
 MessageHandler messageHandler = new RongCloudMessageHandler(this, token);
 im.setMessageHandler(messageHandler);
```


<br />
####会话列表
```java
new ConversationListFragment() 然后嵌入到你需要的地方
```

<br />
####聊天
```java
new MessageFragment() 然后嵌入到你需要的地方
```
同时配置Activity
```xml
        <activity
            android:name=".ChatActivity" //你自己的Activity
            android:label="@string/title_activity_chat" >
            <intent-filter>
                <action android:name="android.intent.action.VIEW" />

                <category android:name="android.intent.category.DEFAULT" />

                <data
                    android:host="im.mongo.demo" //你项目的包名
                    android:pathPrefix="/conversation"
                    android:scheme="mongo" />
            </intent-filter>
        </activity>
```

发起单聊
```java
MongoIM.sharedInstance().startPrivateConversation(context, "目标用户ID");
```


<br />
界面及相关逻辑
===============

####设置用户头像 昵称等

```java
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
```

####添加表情包

```java
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
```


####点击插件后弹出的界面
```java
        //默认已经实现的有 照片选择 拍照 选地点 如果需要自定义弹出界面 直接通过注册的方式覆盖原有实现即可
        //其它的都需要你去实现 通过传入的plugin来发送消息 发送后退出当前controller
        //如果是自定义plugin 则直接覆盖基类onClick方法即可实现相同效果
        MongoIM.sharedInstance().registerPluginPresentController(RedBagPluginProvider.class, RedBagCreateController.class);
        MongoIM.sharedInstance().registerPluginPresentController(NameCardPluginProvider.class, NameCardChooseController.class);
        MongoIM.sharedInstance().registerPluginPresentController(FavouritePluginProvider.class, FavouriteChooseController.class);

```

####配置需要使用的插件
```java
im.configPluginProviders(Conversation.Type.PRIVATE, new PluginProvider[]{new PhotoAlbumPluginProvider()});
```

####点击消息气泡执行的动作
```java
        MongoIM.sharedInstance().registerClickHandler(MessageContent.Type.RED_BAG, new MessageTemplateManager.ClickHandler() {
            @Override
            public void onClickMessage(Message message, Activity activity) {

                Toast toast = Toast.makeText(activity, "点击了消息", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
```

####位置发送和显示 需要使用高德地图
将demo中的jar包放入到你的工程libs中

配置高德地图
```xml
        <!--高德地图开始-->
        <meta-data
            android:name="com.amap.api.v2.apikey"
            android:value="高德地图key" />

        <service android:name="com.amap.api.location.APSService" />
        <!--高德地图结束-->
```
注册插件
```java
        im.registerPlugin(LocationPluginProvider.class, Conversation.Type.PRIVATE);
```

点击地图消息后跳转
```java
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
```


<br />
自定义扩展
============

####自定义新的消息及显示

##### 1.新的消息类型
如果是媒体类消息(比如图片 文字 视频等) 直接继承 MediaMessage 如果是通知类消息(比如系统提示) 直接继承 InfoNotifyMessage
当然你也可以直接继承MessageContent

```java
@MessageContent.Tag(type = MessageContent.Type.YOUR_TYPE, action = MessageContent.Tag.PERSIST | MessageContent.Tag.COUNT)
public class YourMessage extends MediaMessage {

    private String yourField;

    @Override
    public byte[] encode() {
        return new byte[0];
    }

    @Override
    public String toJson() {
        return null;
    }

    @Override
    public void decode(byte[] bytes) {

    }
}
```

#####2. 消息显示
如果需要显示头像 直接继承MessageTemplate 如果不需要 直接继承BaseMessageTemplate

例如：定义一个需要显示头像类型的模版
```java
@MessageTemplate.Tag(model = YourMessage.class)
public class YourMessageTemplate extends MessageTemplate {

    private TextView yourView;

    public YourMessageTemplate() {
        //messageContentView指消息中间部分容器
        messageContentView.addView(yourView, params);
    }


    @Override
    protected void refresh(Message message) {
        super.refresh(message);
        //更新数据
    }


    //长按消息后 需要增加自定义的弹出菜单
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
```


不需要显示头像
```java
@MessageTemplate.Tag(model = YourMessage.class)
public class YourMessageTemplate extends BaseMessageTemplate {

    private TextView yourView;

    public YourMessageTemplate() {
        //contentView指整个显示内容的区域
        contentView.addView(yourView, params);
    }


    @Override
    protected void refresh(Message message) {
        super.refresh(message);
        //将数据绑定到view上
    }
}

```

#####3. 注册消息实体及显示模版

```java
MongoIM.sharedInstance().registerMessageContent(YourMessage.class);
MongoIM.sharedInstance().registerMessageTemplate(YourMessageTemplate.class);
```


####自定义插件
```java
public class YourPluginProvider extends PluginProvider {


    public YourPluginProvider() {
        icon = R.mipmap.icon;
        name = "你的插件名称";
    }

}
```

注册插件
```java
im.registerPlugin(YourPluginProvider.class, Conversation.Type.PRIVATE);
```



<br />
融云消息扩展
============

####新增融云消息 具体可参照默认的红包 分享等消息的实现 具体细节可以参考融云官方文档

```java
请参考RcShareMessage这个类
```


####注册融云消息
```java
RongIMClient.registerMessageType(YourRongCloudMessage.class);
```

####对接融云消息

将融云消息转换为MongoIM消息
```java
public class RongCloudToMongoYourMessageContentAdapter extends RongCloudToMongoMessageContentAdapter<YourRongCloudMessage> {

    @Override
    protected String getConversationSubTitle(YourRongCloudMessage content) {
        //返回消息汇总列表地方的文字
    }

    @Override
    public String getMongoMessageType() {
        return MessageContent.Type.Your_RONG_CLOUD_TYPE;
    }

    @Override
    public MessageContent getMongoMessageContent(YourRongCloudMessage message) {
        //你的转换逻辑
    }
}
@end
```

将MongoIM的消息转换成融云的
```java
public class MongoToRongYourMessageContentAdapter extends MongoToRongMessageContentAdapter<YourMessage> {

    @Override
    public MessageContent getRongCloudMessageContent(YourMessage messageContent) {
        return //你的转化逻辑
    }
}
```

注册新增的类型 

```java
    RongCloudToMongoMessageContentAdapterManager rongManager = RongCloudToMongoMessageContentAdapterManager.sharedInstance();
    rongManager.register(YourRongCloudMessage.class, RongCloudToMongoYourMessageContentAdapter.class);
    
    
    MongoToRongCloudMessageContentAdapterManager mongoManager = MongoToRongCloudMessageContentAdapterManager.sharedInstance();
    mongoManager.register(YourMessage.class, MongoToRongYourMessageContentAdapter.class);    
```
