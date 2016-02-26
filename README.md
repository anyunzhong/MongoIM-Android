
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
