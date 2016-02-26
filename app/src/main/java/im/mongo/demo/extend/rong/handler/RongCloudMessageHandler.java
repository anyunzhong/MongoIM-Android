package im.mongo.demo.extend.rong.handler;

import android.app.ActivityManager;
import android.content.Context;

import net.datafans.android.common.helper.LogHelper;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import im.mongo.core.model.Conversation;
import im.mongo.core.model.Message;
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
import im.mongo.ui.event.ConnectSuccessEvent;
import im.mongo.ui.event.MessageFailEvent;
import im.mongo.ui.event.MessageReceiveEvent;
import im.mongo.ui.event.MessageSentEvent;
import im.mongo.ui.event.MessageStoreEvent;
import im.mongo.demo.extend.rong.model.RcEmotionMessage;
import im.mongo.demo.extend.rong.model.RcNameCardMessage;
import im.mongo.demo.extend.rong.model.RcRedBagMessage;
import im.mongo.demo.extend.rong.model.RcShareMessage;
import im.mongo.demo.extend.rong.model.RcShortVideoMessage;
import im.mongo.demo.extend.rong.mongo2rong.MongoToRongCloudMessageContentAdapterManager;
import im.mongo.demo.extend.rong.mongo2rong.MongoToRongEmotionMessageContentAdapter;
import im.mongo.demo.extend.rong.mongo2rong.MongoToRongImageMessageContentAdapter;
import im.mongo.demo.extend.rong.mongo2rong.MongoToRongLocationMessageContentAdapter;
import im.mongo.demo.extend.rong.mongo2rong.MongoToRongMessageContentAdapter;
import im.mongo.demo.extend.rong.mongo2rong.MongoToRongNameCardMessageContentAdapter;
import im.mongo.demo.extend.rong.mongo2rong.MongoToRongRedBagMessageContentAdapter;
import im.mongo.demo.extend.rong.mongo2rong.MongoToRongShareMessageContentAdapter;
import im.mongo.demo.extend.rong.mongo2rong.MongoToRongShortVideoMessageContentAdapter;
import im.mongo.demo.extend.rong.mongo2rong.MongoToRongTextMessageContentAdapter;
import im.mongo.demo.extend.rong.mongo2rong.MongoToRongVoiceMessageContentAdapter;
import im.mongo.demo.extend.rong.rong2mongo.RongCloudToMongoEmotionMessageContentAdapter;
import im.mongo.demo.extend.rong.rong2mongo.RongCloudToMongoImageMessageContentAdapter;
import im.mongo.demo.extend.rong.rong2mongo.RongCloudToMongoInfoNotifyMessageContentAdapter;
import im.mongo.demo.extend.rong.rong2mongo.RongCloudToMongoLocationMessageContentAdapter;
import im.mongo.demo.extend.rong.rong2mongo.RongCloudToMongoMessageContentAdapter;
import im.mongo.demo.extend.rong.rong2mongo.RongCloudToMongoMessageContentAdapterManager;
import im.mongo.demo.extend.rong.rong2mongo.RongCloudToMongoNameCardMessageContentAdapter;
import im.mongo.demo.extend.rong.rong2mongo.RongCloudToMongoRedBagMessageContentAdapter;
import im.mongo.demo.extend.rong.rong2mongo.RongCloudToMongoShareMessageContentAdapter;
import im.mongo.demo.extend.rong.rong2mongo.RongCloudToMongoShortVideoMessageContentAdapter;
import im.mongo.demo.extend.rong.rong2mongo.RongCloudToMongoTextMessageContentAdapter;
import im.mongo.demo.extend.rong.rong2mongo.RongCloudToMongoVoiceMessageContentAdapter;
import io.rong.imlib.AnnotationNotFoundException;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.MessageContent;
import io.rong.message.InformationNotificationMessage;

/**
 * Created by zhonganyun on 16/1/30.
 */
public class RongCloudMessageHandler implements MessageHandler {

    public RongCloudMessageHandler(Context context, String token) {

        if (context.getApplicationInfo().packageName.equals(RongCloudMessageHandler.getCurProcessName(context))) {

            //初始化融云
            RongIMClient.init(context);

            //注册自定义融云消息
            try {
                //红包
                RongIMClient.registerMessageType(RcRedBagMessage.class);
                //分享网页
                RongIMClient.registerMessageType(RcShareMessage.class);
                //表情
                RongIMClient.registerMessageType(RcEmotionMessage.class);
                //名片
                RongIMClient.registerMessageType(RcNameCardMessage.class);
                //小视频
                RongIMClient.registerMessageType(RcShortVideoMessage.class);

            } catch (AnnotationNotFoundException e) {
                LogHelper.error(e);
            }

            //初始化内容适配器
            initContentAdapter();

            //连接
            RongIMClient.connect(token, new RongIMClient.ConnectCallback() {
                @Override
                public void onTokenIncorrect() {
                    LogHelper.error("Token Incorrect");
                }

                @Override
                public void onSuccess(String userid) {
                    LogHelper.info("Connect Success " + userid);

                    EventBus.getDefault().post(new ConnectSuccessEvent());

                    RongIMClient.setOnReceiveMessageListener(new RongIMClient.OnReceiveMessageListener() {
                        @Override
                        public boolean onReceived(io.rong.imlib.model.Message message, int i) {
                            LogHelper.info("收到消息 " + message);
                            Message m = getMongoMessage(message);
                            EventBus.getDefault().post(new MessageReceiveEvent(m));

                            return false;
                        }
                    });

                }

                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {
                    LogHelper.error("Error " + errorCode);
                }
            });
        }

    }


    private void initContentAdapter() {
        RongCloudToMongoMessageContentAdapterManager rongManager = RongCloudToMongoMessageContentAdapterManager.sharedInstance();
        rongManager.register(io.rong.message.TextMessage.class, RongCloudToMongoTextMessageContentAdapter.class);
        rongManager.register(io.rong.message.VoiceMessage.class, RongCloudToMongoVoiceMessageContentAdapter.class);
        rongManager.register(io.rong.message.ImageMessage.class, RongCloudToMongoImageMessageContentAdapter.class);
        rongManager.register(RcRedBagMessage.class, RongCloudToMongoRedBagMessageContentAdapter.class);
        rongManager.register(io.rong.message.LocationMessage.class, RongCloudToMongoLocationMessageContentAdapter.class);
        rongManager.register(RcShareMessage.class, RongCloudToMongoShareMessageContentAdapter.class);
        rongManager.register(InformationNotificationMessage.class, RongCloudToMongoInfoNotifyMessageContentAdapter.class);
        rongManager.register(RcEmotionMessage.class, RongCloudToMongoEmotionMessageContentAdapter.class);
        rongManager.register(RcNameCardMessage.class, RongCloudToMongoNameCardMessageContentAdapter.class);
        rongManager.register(RcShortVideoMessage.class, RongCloudToMongoShortVideoMessageContentAdapter.class);


        MongoToRongCloudMessageContentAdapterManager mongoManager = MongoToRongCloudMessageContentAdapterManager.sharedInstance();
        mongoManager.register(TextMessage.class, MongoToRongTextMessageContentAdapter.class);
        mongoManager.register(VoiceMessage.class, MongoToRongVoiceMessageContentAdapter.class);
        mongoManager.register(ImageMessage.class, MongoToRongImageMessageContentAdapter.class);
        mongoManager.register(RedBagMessage.class, MongoToRongRedBagMessageContentAdapter.class);
        mongoManager.register(LocationMessage.class, MongoToRongLocationMessageContentAdapter.class);
        mongoManager.register(ShareMessage.class, MongoToRongShareMessageContentAdapter.class);
        mongoManager.register(EmotionMessage.class, MongoToRongEmotionMessageContentAdapter.class);
        mongoManager.register(NameCardMessage.class, MongoToRongNameCardMessageContentAdapter.class);
        mongoManager.register(ShortVideoMessage.class, MongoToRongShortVideoMessageContentAdapter.class);


    }

    private RongCloudToMongoMessageContentAdapter getAdapter(Class<? extends MessageContent> contentClass) {
        RongCloudToMongoMessageContentAdapterManager manager = RongCloudToMongoMessageContentAdapterManager.sharedInstance();
        return manager.getAdapter(contentClass);
    }


    @Override
    public void sendMessage(final Message msg) {

        io.rong.imlib.model.Conversation.ConversationType type = getRongConversationType(msg.getConversationType());

        MessageContent content = getRongCloudMessageContent(msg);
        if (content == null) {
            LogHelper.error("客户端不支持发送此消息");
            return;
        }

        if (content instanceof io.rong.message.ImageMessage) {

            RongIMClient.getInstance().sendImageMessage(type, msg.getTargetId(), content, null, null, new RongIMClient.SendImageMessageCallback() {

                @Override
                public void onAttached(io.rong.imlib.model.Message message) {
                    //保存数据库成功
                    LogHelper.info("发送消息 Id: " + message.getMessageId());

                    Message m = getMongoMessage(message);
                    EventBus.getDefault().post(new MessageStoreEvent(m));
                }

                @Override
                public void onError(io.rong.imlib.model.Message message, RongIMClient.ErrorCode errorCode) {
                    //发送失败
                    LogHelper.error("发送失败: " + message.getMessageId());
                    EventBus.getDefault().post(new MessageFailEvent(message.getMessageId()));
                }

                @Override
                public void onSuccess(io.rong.imlib.model.Message message) {
                    //发送成功
                    LogHelper.info("发送成功: " + message.getMessageId());
                    String url = ((io.rong.message.ImageMessage) message.getContent()).getRemoteUri().toString();
                    EventBus.getDefault().post(new MessageSentEvent(message.getMessageId(), url));
                }

                @Override
                public void onProgress(io.rong.imlib.model.Message message, int i) {
                    //发送进度
                    LogHelper.error("发送进度: " + i);
                }

            });


        } else {

            RongIMClient.getInstance().sendMessage(type, msg.getTargetId(), content, "", "", new RongIMClient.SendMessageCallback() {
                @Override
                public void onError(Integer messageId, RongIMClient.ErrorCode errorCode) {
                    LogHelper.error("发送失败: " + messageId);
                    EventBus.getDefault().post(new MessageFailEvent(messageId));
                }

                @Override
                public void onSuccess(Integer messageId) {
                    LogHelper.info("发送成功: " + messageId);
                    EventBus.getDefault().post(new MessageSentEvent(messageId, ""));
                }

            }, new RongIMClient.ResultCallback<io.rong.imlib.model.Message>() {
                @Override
                public void onSuccess(io.rong.imlib.model.Message message) {
                    LogHelper.info("发送消息 Id: " + message.getMessageId());

                    Message m = getMongoMessage(message);
                    EventBus.getDefault().post(new MessageStoreEvent(m));
                }

                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {
                    LogHelper.error("发送错误: " + errorCode);
                }

            });
        }


    }

    @Override
    public List<Message> getMessages(Conversation.Type conversationType, String targetId, int start, int size) {

        List<io.rong.imlib.model.Message> rcMessages;
        if (start == 0) {
            rcMessages = RongIMClient.getInstance().getLatestMessages(getRongConversationType(conversationType), targetId, size);
        } else {
            rcMessages = RongIMClient.getInstance().getHistoryMessages(getRongConversationType(conversationType), targetId, start, size);
        }

        List<Message> messages = new ArrayList<>();
        if (rcMessages == null) return messages;

        for (io.rong.imlib.model.Message msg : rcMessages) {
            Message message = getMongoMessage(msg);
            messages.add(message);
        }
        return messages;
    }

    @Override
    public List<Message> getImageMessages(Conversation.Type conversationType, String targetId, int size) {
        //获得最新的id  不知道为啥传一个integer.max 无法获取到消息
        List<io.rong.imlib.model.Message> rcMessages = RongIMClient.getInstance().getLatestMessages(getRongConversationType(conversationType), targetId, size);
        if (rcMessages == null || rcMessages.isEmpty()) return new ArrayList<>();

        rcMessages = RongIMClient.getInstance().getHistoryMessages(getRongConversationType(conversationType), targetId, "RC:ImgMsg", rcMessages.get(0).getMessageId(), 100);
        if (rcMessages == null || rcMessages.isEmpty()) return new ArrayList<>();

        List<Message> messages = new ArrayList<>();
        for (io.rong.imlib.model.Message msg : rcMessages) {
            Message message = getMongoMessage(msg);
            messages.add(message);
        }
        return messages;
    }

    @Override
    public List<Conversation> getConversations() {

        List<io.rong.imlib.model.Conversation> conversationList = RongIMClient.getInstance().getConversationList();

        if (conversationList == null) return new ArrayList<>();

        List<Conversation> conversations = new ArrayList<>();
        for (io.rong.imlib.model.Conversation conversation : conversationList) {
            conversations.add(getMongoConversation(conversation));
        }
        return conversations;
    }

    @Override
    public void setMessageReceiveStatus(int messageId, Message.ReceiveStatus receiveStatus) {

        RongIMClient.getInstance().setMessageReceivedStatus(messageId, new io.rong.imlib.model.Message.ReceivedStatus(receiveStatus.getFlag()), new RongIMClient.ResultCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean aBoolean) {

            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {

            }
        });
    }

    @Override
    public void deleteMessage(int messageId) {
        RongIMClient.getInstance().deleteMessages(new int[]{messageId}, new RongIMClient.ResultCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean aBoolean) {

            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {

            }
        });
    }


    private Conversation getMongoConversation(io.rong.imlib.model.Conversation conversation) {

        RongCloudToMongoMessageContentAdapter adapter = getAdapter(conversation.getLatestMessage().getClass());

        Conversation con;
        if (adapter == null) {
            con = new Conversation();
            con.setTargetId(conversation.getTargetId());
            con.setTitle("");
            con.setUnreadCount(conversation.getUnreadMessageCount());
            con.setUpdateTime(conversation.getSentTime());
            con.setSubTitle("当前版本不支持查看此消息");
        } else {
            con = adapter.getMongoConversation(conversation);
        }
        con.setConversationType(getMongoConversationType(conversation.getConversationType()));

        return con;
    }


    private Message getMongoMessage(io.rong.imlib.model.Message msg) {
        Message message = new Message();
        message.setMessageId(msg.getMessageId());
        message.setTargetId(msg.getTargetId());
        message.setDirection(getMongoDirection(msg));
        message.setSenderId(msg.getSenderUserId());
        message.setConversationType(getMongoConversationType(msg.getConversationType()));
        message.setSendStatus(getMongoSendStatus(msg));
        message.setReceiveStatus(getMongoReceiveStatus(msg));
        message.setReceivedTime(msg.getReceivedTime());
        message.setSentTime(msg.getSentTime());
        message.setContentType(getMongoContentType(msg));
        message.setExtra("");
        message.setContent(getMongoMessageContent(msg.getContent()));

        return message;
    }

    private Message.Direction getMongoDirection(io.rong.imlib.model.Message msg) {
        if (msg.getMessageDirection() == io.rong.imlib.model.Message.MessageDirection.SEND) {
            return Message.Direction.SEND;
        } else {
            return Message.Direction.RECEIVE;
        }
    }

    private Message.SendStatus getMongoSendStatus(io.rong.imlib.model.Message msg) {
        Message.SendStatus status = null;

        switch (msg.getSentStatus()) {
            case SENDING:
                status = Message.SendStatus.SENDING;
                break;
            case SENT:
                status = Message.SendStatus.SENT;
                break;
            case FAILED:
                status = Message.SendStatus.FAILED;
                break;
        }

        return status;
    }


    private String getMongoContentType(io.rong.imlib.model.Message msg) {

        RongCloudToMongoMessageContentAdapter adapter = getAdapter(msg.getContent().getClass());

        //如果无法解析消息 用消息提示: 客户端暂不支持此消息
        if (adapter == null) {
            return im.mongo.core.model.content.MessageContent.Type.InfoNotify;
        } else {
            return adapter.getMongoMessageType();
        }
    }


    private Message.ReceiveStatus getMongoReceiveStatus(io.rong.imlib.model.Message msg) {
        return new Message.ReceiveStatus(msg.getReceivedStatus().getFlag());
    }


    private Conversation.Type getMongoConversationType(io.rong.imlib.model.Conversation.ConversationType conversationType) {
        Conversation.Type type = null;

        switch (conversationType) {
            case PRIVATE:
                type = Conversation.Type.PRIVATE;
                break;
            case DISCUSSION:
                type = Conversation.Type.DISCUSSION;
                break;
            case GROUP:
                type = Conversation.Type.GROUP;
                break;
            case CHATROOM:
                type = Conversation.Type.ROOM;
                break;
            case SYSTEM:
                type = Conversation.Type.SYSTEM;
                break;

        }

        return type;
    }


    private io.rong.imlib.model.Conversation.ConversationType getRongConversationType(Conversation.Type conversationType) {
        io.rong.imlib.model.Conversation.ConversationType type = null;

        switch (conversationType) {
            case PRIVATE:
                type = io.rong.imlib.model.Conversation.ConversationType.PRIVATE;
                break;
            case DISCUSSION:
                type = io.rong.imlib.model.Conversation.ConversationType.DISCUSSION;
                break;
            case GROUP:
                type = io.rong.imlib.model.Conversation.ConversationType.GROUP;
                break;
            case ROOM:
                type = io.rong.imlib.model.Conversation.ConversationType.CHATROOM;
                break;
            case SYSTEM:
                type = io.rong.imlib.model.Conversation.ConversationType.SYSTEM;
                break;

        }

        return type;
    }


    @SuppressWarnings("unchecked")
    private MessageContent getRongCloudMessageContent(Message message) {

        MongoToRongCloudMessageContentAdapterManager manager = MongoToRongCloudMessageContentAdapterManager.sharedInstance();
        MongoToRongMessageContentAdapter adapter = manager.getAdapter(message.getContent().getClass());


        if (adapter != null) return adapter.getRongCloudMessageContent(message.getContent());
        return null;
    }


    @SuppressWarnings("unchecked")
    private im.mongo.core.model.content.MessageContent getMongoMessageContent(MessageContent content) {
        RongCloudToMongoMessageContentAdapter adapter = getAdapter(content.getClass());

        //如果无法解析消息 用消息提示: 客户端暂不支持此消息
        if (adapter == null) {
            InfoNotifyMessage infoNotifyMessage = new InfoNotifyMessage();
            infoNotifyMessage.setContent("当前版本不支持查看此消息");
            return infoNotifyMessage;
        } else {
            return adapter.getMongoMessageContent(content);
        }
    }


    private static String getCurProcessName(Context context) {

        int pid = android.os.Process.myPid();

        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);

        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager
                .getRunningAppProcesses()) {

            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }
}
