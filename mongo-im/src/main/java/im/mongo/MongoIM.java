package im.mongo;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import net.datafans.android.common.helper.LogHelper;
import net.datafans.android.common.widget.table.TableViewCell;

import java.lang.annotation.Annotation;
import java.util.List;

import im.mongo.core.model.Conversation;
import im.mongo.core.model.content.MessageContent;
import im.mongo.core.model.content.media.EmotionMessage;
import im.mongo.core.model.content.media.ImageMessage;
import im.mongo.core.model.content.media.LocationMessage;
import im.mongo.core.model.content.media.NameCardMessage;
import im.mongo.core.model.content.media.RedBagMessage;
import im.mongo.core.model.content.media.ShareMessage;
import im.mongo.core.model.content.media.ShortVideoMessage;
import im.mongo.core.model.content.media.TextMessage;
import im.mongo.core.model.content.media.VoiceMessage;
import im.mongo.core.model.content.notify.InfoNotifyMessage;
import im.mongo.handler.MessageHandler;
import im.mongo.handler.MongoMessageHandler;
import im.mongo.ui.emotion.EmojiEmotion;
import im.mongo.ui.emotion.EmotionManager;
import im.mongo.ui.emotion.PackageEmotion;
import im.mongo.ui.plugin.PluginPresentController;
import im.mongo.ui.plugin.PluginProvider;
import im.mongo.ui.plugin.PluginProviderManager;
import im.mongo.ui.provider.UserInfoProvider;
import im.mongo.ui.view.template.EmotionMessageTemplate;
import im.mongo.ui.view.template.ImageMessageTemplate;
import im.mongo.ui.view.template.InfoNotifyMessageTemplate;
import im.mongo.ui.view.template.LocationMessageTemplate;
import im.mongo.ui.view.template.MessageTemplate;
import im.mongo.ui.view.template.MessageTemplateManager;
import im.mongo.ui.view.template.NameCardMessageTemplate;
import im.mongo.ui.view.template.RedBagMessageTemplate;
import im.mongo.ui.view.template.ShareMessageTemplate;
import im.mongo.ui.view.template.ShortVideoMessageTemplate;
import im.mongo.ui.view.template.TextMessageTemplate;
import im.mongo.ui.view.template.VoiceMessageTemplate;

/**
 * Created by zhonganyun on 15/11/20.
 */
public class MongoIM {

    private static MongoIM im = new MongoIM();

    private Context context;

    private MongoIM() {
    }

    public static MongoIM sharedInstance() {
        return im;
    }

    private UserInfoProvider userInfoProvider;

    private MessageHandler messageHandler;

    public void init(Context context) {
        this.context = context;

        initMessageComponent();
        addDefaultEmotion();

    }

    private void initMessageComponent() {
        registerMessageContent(TextMessage.class);
        registerMessageContent(VoiceMessage.class);
        registerMessageContent(ImageMessage.class);
        registerMessageContent(RedBagMessage.class);
        registerMessageContent(LocationMessage.class);
        registerMessageContent(ShareMessage.class);
        registerMessageContent(InfoNotifyMessage.class);
        registerMessageContent(EmotionMessage.class);
        registerMessageContent(NameCardMessage.class);
        registerMessageContent(ShortVideoMessage.class);


        registerMessageTemplate(TextMessageTemplate.class);
        registerMessageTemplate(VoiceMessageTemplate.class);
        registerMessageTemplate(ImageMessageTemplate.class);
        registerMessageTemplate(RedBagMessageTemplate.class);
        registerMessageTemplate(LocationMessageTemplate.class);
        registerMessageTemplate(ShareMessageTemplate.class);
        registerMessageTemplate(InfoNotifyMessageTemplate.class);
        registerMessageTemplate(EmotionMessageTemplate.class);
        registerMessageTemplate(ShortVideoMessageTemplate.class);
        registerMessageTemplate(NameCardMessageTemplate.class);
    }

    private void addDefaultEmotion() {
        EmotionManager manager = EmotionManager.sharedInstance();
        manager.addEmotion(new EmojiEmotion());
    }

    public void initWithMongoMessageHandler(Context context, String senderId) {
        init(context);
        MessageHandler handler = new MongoMessageHandler(senderId);
        setMessageHandler(handler);
    }

    public MessageHandler getMessageHandler() {
        if (messageHandler == null) {
            LogHelper.error("请设置MessageHandler");
        }
        return messageHandler;
    }

    public MongoIM setMessageHandler(MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
        return this;
    }

    public Context getContext() {
        return context;
    }


    /**
     * 开启单聊
     *
     * @param context  上下文
     * @param targetId 目标用户ID
     */
    public void startPrivateConversation(Context context, String targetId) {
        Uri.Builder builder = Uri.parse("mongo://" + context.getApplicationInfo().packageName).buildUpon();
        builder.appendPath("conversation");
        builder.appendQueryParameter("targetId", targetId);
        builder.appendQueryParameter("conversationType", String.valueOf(Conversation.Type.PRIVATE.getType()));
        Intent intent = new Intent(Intent.ACTION_VIEW, builder.build());
        context.startActivity(intent);
    }

    /**
     * 注册消息内容
     *
     * @param clazz 内容类
     */
    public void registerMessageContent(Class<? extends MessageContent> clazz) {
        Annotation[] annotations = clazz.getDeclaredAnnotations();
        for (Annotation annotation : annotations) {
            if (annotation instanceof MessageContent.Tag) {
                MessageContent.Tag tag = (MessageContent.Tag) annotation;
                int action = tag.action();
                String type = tag.type();
                MessageContent.Schema info = new MessageContent.Schema();
                info.setClazz(clazz);
                if (action == 1) {
                    info.setIsPersist(true);
                } else if (action == 2) {
                    info.setIsCount(true);
                } else if (action == 3) {
                    info.setIsCount(true);
                    info.setIsPersist(true);
                }
                MessageContent.Manager.sharedInstance().register(type, info);
            }
        }
    }

    /**
     * 注册消息显示的模版
     *
     * @param clazz 内容模版类
     */
    public void registerMessageTemplate(Class<? extends TableViewCell> clazz) {
        Annotation[] annotations = clazz.getDeclaredAnnotations();
        for (Annotation annotation : annotations) {
            if (annotation instanceof MessageTemplate.Tag) {
                MessageTemplate.Tag tag = (MessageTemplate.Tag) annotation;
                Class<? extends MessageContent> contentClazz = tag.model();

                String type = null;

                Annotation[] contentAnnotations = contentClazz.getDeclaredAnnotations();
                for (Annotation contentAnnotation : contentAnnotations) {
                    if (contentAnnotation instanceof MessageContent.Tag) {
                        MessageContent.Tag contentTag = (MessageContent.Tag) contentAnnotation;
                        type = contentTag.type();
                    }
                }
                if (type != null)
                    MessageTemplateManager.sharedInstance().register(type, clazz);
            }
        }
    }


    /**
     * 设置插件
     *
     * @param type      会话类型
     * @param providers 插件数组
     */
    public void configPluginProviders(Conversation.Type type, List<PluginProvider> providers) {
        PluginProviderManager.sharedInstance().setProviders(type, providers);
    }

    /**
     * 注册插件
     *
     * @param pluginClass 插件类
     */
    public void registerPlugin(Class<? extends PluginProvider> pluginClass, Conversation.Type type) {
        PluginProviderManager.sharedInstance().registerPlugin(pluginClass, type);
    }


    /**
     * 添加表情包
     *
     * @param emotion 表情包
     */
    public void addPackageEmotion(PackageEmotion emotion) {
        EmotionManager.sharedInstance().addEmotion(emotion);
    }


    /**
     * 获得用户信息提供者
     *
     * @return 提供者
     */
    public UserInfoProvider getUserInfoProvider() {
        return userInfoProvider;
    }

    /**
     * 设置用户信息提供者
     *
     * @param userInfoProvider 提供者
     */
    public void setUserInfoProvider(UserInfoProvider userInfoProvider) {
        this.userInfoProvider = userInfoProvider;
    }

    /**
     * 注册点击插件按钮后 需要去到的界面
     *
     * @param pluginClass  插件class
     * @param presentClass 需要弹出的activity class
     */
    public void registerPluginPresentController(Class<? extends PluginProvider> pluginClass, Class<? extends PluginPresentController> presentClass) {
        PluginProviderManager.sharedInstance().registerPluginPresentController(pluginClass, presentClass);
    }


    /**
     * 注册消息点击时的处理行为
     *
     * @param contentType 消息类型
     * @param handler     处理行为
     */
    public void registerClickHandler(String contentType, MessageTemplateManager.ClickHandler handler) {
        MessageTemplateManager.sharedInstance().registerClickHandler(contentType, handler);
    }


}
