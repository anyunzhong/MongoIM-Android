package im.mongo.ui.view.message;


import android.app.Activity;
import android.content.Intent;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import net.datafans.android.common.event.BaseEvent;
import net.datafans.android.common.helper.CacheHepler;
import net.datafans.android.common.helper.IOHelper;
import net.datafans.android.common.helper.LogHelper;
import net.datafans.android.common.widget.controller.FragmentController;
import net.datafans.android.common.widget.popup.PopupView;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import de.greenrobot.event.EventBus;
import im.mongo.MongoIM;
import im.mongo.R;
import im.mongo.core.model.Conversation;
import im.mongo.core.model.Message;
import im.mongo.core.model.content.MessageContent;
import im.mongo.core.model.content.media.EmotionMessage;
import im.mongo.core.model.content.media.ImageMessage;
import im.mongo.core.model.content.media.TextMessage;
import im.mongo.core.model.content.media.VoiceMessage;
import im.mongo.ui.event.MessageDeleteEvent;
import im.mongo.ui.event.MessageFailEvent;
import im.mongo.ui.event.MessageReceiveEvent;
import im.mongo.ui.event.MessageSentEvent;
import im.mongo.ui.event.MessageStoreEvent;
import im.mongo.ui.event.PackageEmotionClickEvent;
import im.mongo.ui.provider.UserInfo;
import im.mongo.ui.provider.UserInfoProvider;
import im.mongo.ui.view.body.MessageTableViewFragment;
import im.mongo.ui.view.emotion.EmotionViewFragment;
import im.mongo.ui.view.hud.RecordHudFragment;
import im.mongo.ui.view.input.InputToolbarFragment;
import im.mongo.ui.view.plugin.PluginViewFragment;

public class MessageFragment extends Fragment implements InputToolbarFragment.Delegate, MessageTableViewFragment.Delegate {

    private static final int MESSAGE_SIZE_PER_PAGE = 20;
    private InputToolbarFragment inputToolbarFragment;

    private MessageTableViewFragment messageTableViewFragment;
    private PluginViewFragment pluginViewFragment;
    private EmotionViewFragment emotionViewFragment;


    private RecordHudFragment voiceRecorderHud;

    private View mask;

    private boolean isKeyboardShow = false;

    private Handler handler = new Handler();


    private MediaRecorder recorder;

    private volatile boolean isRecording = false;

    private long start;

    private Conversation.Type conversationType;

    private String targetId;

    private View rootView;


    public MessageFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        EventBus.getDefault().register(this);

        Uri uri = getActivity().getIntent().getData();
        conversationType = Conversation.Type.valueOf(Integer.parseInt(uri.getQueryParameter("conversationType")));
        targetId = uri.getQueryParameter("targetId");

        UserInfoProvider userInfoProvider = MongoIM.sharedInstance().getUserInfoProvider();
        userInfoProvider.getUserInfo(targetId, new UserInfoProvider.Callback() {
            @Override
            public void callback(UserInfo userInfo) {

                if (userInfo != null) {
                    Activity activity = MessageFragment.this.getActivity();
                    if (activity instanceof FragmentController) {
                        ((FragmentController) activity).changeTitle(userInfo.getNick());
                    }
                }
            }
        });


        rootView = inflater.inflate(R.layout.fragment_chat_view, container, false);

        initView(rootView);

        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        LogHelper.info(requestCode + "  " + resultCode + "   " + data);

        pluginViewFragment.onActivityResult(requestCode, resultCode, data);
    }

    private void initView(View view) {


        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


        //底部输入栏
        inputToolbarFragment = (InputToolbarFragment) getChildFragmentManager().findFragmentById(R.id.fragment_input_toolbar);
        inputToolbarFragment.setDelegate(this);

        //消息表格
        messageTableViewFragment = (MessageTableViewFragment) getChildFragmentManager().findFragmentById(R.id.fragment_message_tableview);
        messageTableViewFragment.setDelegate(this);

        //插件面板
        pluginViewFragment = (PluginViewFragment) getChildFragmentManager().findFragmentById(R.id.fragment_plugin);

        //表情面板
        emotionViewFragment = (EmotionViewFragment) getChildFragmentManager().findFragmentById(R.id.fragment_emotion);

        //录音HUD
        voiceRecorderHud = (RecordHudFragment) getChildFragmentManager().findFragmentById(R.id.voiceRecorderHud);


        //消息表格上面的蒙板
        mask = view.findViewById(R.id.mask);
        mask.setOnClickListener(maskOnClickListener);


        //加载历史消息
        onLoadHistoryMessageMore(0);
    }


    private View.OnClickListener maskOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            mask.setVisibility(View.GONE);
            pluginViewFragment.hide();
            emotionViewFragment.hide();
            inputToolbarFragment.hideKeyboard();
        }
    };


    public MessageTableViewFragment getMessageTableViewFragment() {

        return messageTableViewFragment;
    }

    @Override
    public void onClickTextAndVoiceBtn() {

        pluginViewFragment.hide();
        emotionViewFragment.hide();
    }

    @Override
    public void onClickEmotionsBtn() {
        if (isKeyboardShow) return;

        handler.postDelayed(new Runnable() {
            public void run() {
                pluginViewFragment.hide();
                emotionViewFragment.show();
                mask.setVisibility(View.VISIBLE);
            }
        }, 200);


    }

    @Override
    public void onClickPluginsBtn() {

        if (isKeyboardShow) return;


        handler.postDelayed(new Runnable() {
            public void run() {
                pluginViewFragment.show();
                emotionViewFragment.hide();
                mask.setVisibility(View.VISIBLE);
            }
        }, 200);
    }


    @Override
    public void onVoiceRecordStart() {
        voiceRecorderHud.show();

        if (recorder == null) {
            //recorder = new MP3Recorder(new File(CacheHepler.getDiskCacheDir(getActivity(), "test.mp3")));

            String path = CacheHepler.getDiskCacheDir(getActivity(), "test.amr");
            recorder = new MediaRecorder();
            recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            recorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB);
            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            recorder.setOutputFile(path);

        }


        try {
            recorder.prepare();
            recorder.start();

        } catch (IOException e) {
            LogHelper.error(e.toString());
        }

        isRecording = true;

        start = System.currentTimeMillis();

        new Thread(new SignalUpdateTask()).start();
    }

    @Override
    public void onVoiceRecordFinished() {
        stopRecord();

        long end = System.currentTimeMillis();
        int duration = (int) ((end - start) / 1000);

        String localPath = CacheHepler.getDiskCacheDir(getActivity(), "test.amr");
        try {
            FileInputStream inputStream = new FileInputStream(new File(localPath));
            byte[] bytes = IOHelper.toByteArray(inputStream);
            IOHelper.closeQuietly(inputStream);

            String content = Base64.encodeToString(bytes, Base64.DEFAULT);

            VoiceMessage voiceMessage = new VoiceMessage();
            voiceMessage.setContent(content);
            voiceMessage.setDuration(duration);
            voiceMessage.setUri(Uri.fromFile(new File(localPath)));

            send(voiceMessage, MessageContent.Type.VOICE);

        } catch (Exception e) {
            LogHelper.error(e.getMessage());
        }

    }


    @Override
    public void onVoiceRecordCancelled() {
        stopRecord();

        //删除文件
        File file = new File(CacheHepler.getDiskCacheDir(getActivity(), "test.mp3"));
        file.deleteOnExit();

    }

    private void stopRecord() {
        voiceRecorderHud.hide();
        if (recorder != null) recorder.stop();
        isRecording = false;
    }

    @Override
    public void onVoiceRecordTouchLeave() {
        voiceRecorderHud.changeState(RecordHudFragment.State.Cancel);
    }

    @Override
    public void onVoiceRecordTouchReturn() {
        voiceRecorderHud.changeState(RecordHudFragment.State.Record);
    }

    @Override
    public void onKeyboardShow() {
        isKeyboardShow = true;
        pluginViewFragment.hide();
        emotionViewFragment.hide();
        mask.setVisibility(View.VISIBLE);
    }

    @Override
    public void onKeyboardHide() {
        isKeyboardShow = false;
        mask.setVisibility(View.GONE);
    }


    private Handler handlers = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (msg.what == 1) {
                if (recorder != null) {

                    int level = recorder.getMaxAmplitude() / 200 + 1;
                    voiceRecorderHud.changeSignalLevel(level);
                    //LogHelper.error("level:  " + level + "  volume: " + recorder.getVolume());
                }
            }
        }

        ;
    };

    private class SignalUpdateTask implements Runnable {

        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(300);
                    if (!isRecording) break;
                    android.os.Message msg = new android.os.Message();
                    msg.what = 1;
                    handlers.sendMessage(msg);
                } catch (Exception e) {
                    LogHelper.error(e.toString());
                }
            }
        }
    }


    @Override
    public void onTextMessageSend(String text) {

        TextMessage textMessage = new TextMessage();
        textMessage.setText(text);

        send(textMessage, MessageContent.Type.TEXT);
    }

    public void send(MessageContent content, String contentType) {

        if (contentType.equals(MessageContent.Type.IMAGE)) {
            mask.setVisibility(View.GONE);
            pluginViewFragment.hide();
            emotionViewFragment.hide();
            inputToolbarFragment.hideKeyboard();
        }
        Message message = new Message();
        message.setMessageId(0);
        message.setContent(content);
        message.setContentType(contentType);
        message.setConversationType(conversationType);
        message.setDirection(Message.Direction.SEND);
        message.setExtra("");
        message.setReceiveStatus(new Message.ReceiveStatus(Message.ReceiveStatus.READ));
        message.setReceivedTime(System.currentTimeMillis());
        message.setSendStatus(Message.SendStatus.SENDING);
        message.setSentTime(System.currentTimeMillis());
        message.setTargetId(targetId);

        LogHelper.info(message.toString());

        MongoIM.sharedInstance().getMessageHandler().sendMessage(message);
    }


    @Override
    public void onLoadHistoryMessageMore(int messageId) {
        List<Message> messages = MongoIM.sharedInstance().getMessageHandler().getMessages(conversationType, targetId, messageId, MESSAGE_SIZE_PER_PAGE);

        for (Message message : messages) {
            if (!message.getReceiveStatus().isRead()) {
                MongoIM.sharedInstance().getMessageHandler().setMessageReceiveStatus(message.getMessageId(), new Message.ReceiveStatus(Message.ReceiveStatus.READ));
            }
        }

        messageTableViewFragment.addMessages(messages);

    }


    public void onEvent(BaseEvent event) {
        if (event instanceof MessageSentEvent) {
            MessageSentEvent sentEvent = (MessageSentEvent) event;

            //如果是图片消息 更新地址
            String url = sentEvent.getUrl();
            if (url != null && !url.equals("")) {
                Message message = messageTableViewFragment.getMessage(sentEvent.getMessageId());
                ((ImageMessage) message.getContent()).setRemoteUrl(url);
            }
            messageTableViewFragment.changeMessageStatus(sentEvent.getMessageId(), Message.SendStatus.SENT);
        } else if (event instanceof MessageFailEvent) {
            MessageFailEvent sentEvent = (MessageFailEvent) event;
            messageTableViewFragment.changeMessageStatus(sentEvent.getMessageId(), Message.SendStatus.FAILED);
        } else if (event instanceof MessageStoreEvent) {
            MessageStoreEvent storeEvent = (MessageStoreEvent) event;
            messageTableViewFragment.addMessage(storeEvent.getMessage());
        } else if (event instanceof PackageEmotionClickEvent) {
            PackageEmotionClickEvent emotionClickEvent = (PackageEmotionClickEvent) event;
            EmotionMessage emotionMessage = new EmotionMessage();
            emotionMessage.setEmotionItem(emotionClickEvent.getEmotionItem());
            send(emotionMessage, MessageContent.Type.EMOTION);
        } else if (event instanceof MessageDeleteEvent) {
            MessageDeleteEvent deleteEvent = (MessageDeleteEvent) event;
            messageTableViewFragment.deleteMessage(deleteEvent.getMessageId());
            MongoIM.sharedInstance().getMessageHandler().deleteMessage(deleteEvent.getMessageId());
        }
    }

    public void onEventMainThread(BaseEvent event) {
        if (event instanceof MessageReceiveEvent) {
            MessageReceiveEvent receiveEvent = (MessageReceiveEvent) event;
            messageTableViewFragment.addMessage(receiveEvent.getMessage());
        }
    }


}
