package im.mongo.ui.view.input;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import net.datafans.android.common.event.BaseEvent;
import net.datafans.android.common.helper.LogHelper;

import de.greenrobot.event.EventBus;
import im.mongo.R;
import im.mongo.ui.event.EmojiClickEvent;

public class InputToolbarFragment extends Fragment {

    private EditText inputEditText;
    private TextView inputVoiceHolder;
    private ImageButton textVoiceButton;
    private ImageButton emotionButton;
    private ImageButton pluginButton;
    private Button sendButton;


    private Context context;


    private State toolbarState;


    private Delegate delegate;

    private boolean isKeyboardHide;


    public InputToolbarFragment() {

        toolbarState = State.None;
        isKeyboardHide = true;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        context = getActivity();
        View view = inflater.inflate(R.layout.fragment_input_tool_bar, container, false);
        initView(view);


        EventBus.getDefault().register(this);

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    private void initView(View view) {


        //文本输入框
        inputEditText = (EditText) view.findViewById(R.id.inputEditText);
        inputEditText.setOnClickListener(editTextListener);
        inputEditText.addTextChangedListener(editTextWatcher);

        //语音输入按钮
        inputVoiceHolder = (TextView) view.findViewById(R.id.inputVoiceHolder);
        inputVoiceHolder.setOnClickListener(voiceHolderClickListener);
        inputVoiceHolder.setOnTouchListener(voiceHolderTouchListener);
        switchInputVoiceAndTextViewState(TextVoiceViewState.Text);


        //语音文本切换按钮
        textVoiceButton = (ImageButton) view.findViewById(R.id.toolview_inputTextVoice);
        textVoiceButton.setOnClickListener(voiceButtonListener);
        switchTextVoiceBtnState(TextVoiceButtonState.Text);


        //表情切换按钮
        emotionButton = (ImageButton) view.findViewById(R.id.toolview_emotion);
        emotionButton.setOnClickListener(emotionButtonListener);
        switchEmotionsBtnState(EmotionButtonState.Emotion);

        //插件切换按钮
        pluginButton = (ImageButton) view.findViewById(R.id.toolview_plugin);
        pluginButton.setOnClickListener(pluginButtonListener);

        //发送按钮
        sendButton = (Button) view.findViewById(R.id.toolview_send);
        sendButton.setOnClickListener(sendButtonListener);


    }


    private View.OnClickListener editTextListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            if (!isKeyboardHide) return;

            isKeyboardHide = false;

            if (delegate != null) delegate.onKeyboardShow();
        }
    };

    private TextWatcher editTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if (charSequence != null && charSequence.length() > 0) {
                sendButton.setVisibility(View.VISIBLE);
            } else {
                sendButton.setVisibility(View.GONE);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    private View.OnClickListener sendButtonListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            if (delegate != null) delegate.onTextMessageSend(inputEditText.getText().toString());
            inputEditText.setText("");
            sendButton.setVisibility(View.GONE);
        }
    };


    private View.OnClickListener voiceHolderClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            //Log.d("Chat", "click click");
        }
    };


    private float lastVoiceTouchY = 0.f;

    private View.OnTouchListener voiceHolderTouchListener = new View.OnTouchListener() {

        @Override
        public boolean onTouch(View view, MotionEvent event) {

            float y = event.getY();

            if (event.getAction() == MotionEvent.ACTION_UP) {
                LogHelper.debug("松开button");

                if (y < 0) {
                    delegate.onVoiceRecordCancelled();
                } else {
                    delegate.onVoiceRecordFinished();
                }
            }
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                LogHelper.debug("按下button");
                delegate.onVoiceRecordStart();
            }


            if (lastVoiceTouchY > 0 && y < 0) {
                LogHelper.debug("离开button区域");
                delegate.onVoiceRecordTouchLeave();
            }

            if (lastVoiceTouchY < 0 && y > 0) {
                LogHelper.debug("进入button区域");
                delegate.onVoiceRecordTouchReturn();
            }

            lastVoiceTouchY = y;

            return false;
        }
    };


    private View.OnClickListener voiceButtonListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {

            switch (toolbarState) {
                case None: {
                    switchTextVoiceBtnState(TextVoiceButtonState.Text);
                    switchEmotionsBtnState(EmotionButtonState.Emotion);
                    toolbarState = State.Voice;
                    hideKeyboard();
                    switchInputVoiceAndTextViewState(TextVoiceViewState.Voice);
                    break;
                }

                case Text: {
                    switchTextVoiceBtnState(TextVoiceButtonState.Text);
                    switchEmotionsBtnState(EmotionButtonState.Emotion);
                    toolbarState = State.Voice;
                    hideKeyboard();
                    switchInputVoiceAndTextViewState(TextVoiceViewState.Voice);
                    break;
                }

                case Voice: {
                    switchTextVoiceBtnState(TextVoiceButtonState.Voice);
                    toolbarState = State.Text;
                    switchInputVoiceAndTextViewState(TextVoiceViewState.Text);
                    showKeyboard();
                    break;
                }

                case Emotions: {
                    switchTextVoiceBtnState(TextVoiceButtonState.Text);
                    switchEmotionsBtnState(EmotionButtonState.Emotion);
                    toolbarState = State.Voice;
                    hideKeyboard();
                    switchInputVoiceAndTextViewState(TextVoiceViewState.Voice);
                    break;
                }

                case Plugins: {

                    toolbarState = State.Voice;
                    switchInputVoiceAndTextViewState(TextVoiceViewState.Voice);
                    break;
                }

                default:
                    break;
            }

            if (delegate != null) {
                delegate.onClickTextAndVoiceBtn();
            }

        }
    };

    private View.OnClickListener emotionButtonListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {

            switch (toolbarState) {
                case None: {
                    switchEmotionsBtnState(EmotionButtonState.Text);
                    switchTextVoiceBtnState(TextVoiceButtonState.Voice);
                    toolbarState = State.Emotions;
                    hideKeyboard();
                    switchInputVoiceAndTextViewState(TextVoiceViewState.Text);
                    break;
                }

                case Text: {
                    switchEmotionsBtnState(EmotionButtonState.Text);
                    switchTextVoiceBtnState(TextVoiceButtonState.Voice);
                    toolbarState = State.Emotions;
                    switchInputVoiceAndTextViewState(TextVoiceViewState.Text);
                    hideKeyboard();
                    break;
                }

                case Voice: {
                    switchEmotionsBtnState(EmotionButtonState.Text);
                    switchTextVoiceBtnState(TextVoiceButtonState.Voice);
                    toolbarState = State.Emotions;
                    hideKeyboard();
                    switchInputVoiceAndTextViewState(TextVoiceViewState.Text);
                    break;
                }

                case Emotions: {

                    switchEmotionsBtnState(EmotionButtonState.Emotion);
                    toolbarState = State.Text;
                    switchInputVoiceAndTextViewState(TextVoiceViewState.Text);
                    showKeyboard();
                    break;
                }

                case Plugins: {

                    switchEmotionsBtnState(EmotionButtonState.Text);
                    switchTextVoiceBtnState(TextVoiceButtonState.Voice);
                    toolbarState = State.Emotions;
                    break;
                }

                default:
                    break;
            }


            if (delegate != null) {
                delegate.onClickEmotionsBtn();
            }

        }

    };


    private View.OnClickListener pluginButtonListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {

            switch (toolbarState) {
                case None: {
                    toolbarState = State.Plugins;
                    hideKeyboard();
                    break;
                }

                case Text: {
                    toolbarState = State.Plugins;
                    hideKeyboard();
                    break;
                }

                case Voice: {
                    toolbarState = State.Plugins;
                    hideKeyboard();
                    break;
                }

                case Emotions: {
                    toolbarState = State.Plugins;
                    break;
                }

                case Plugins: {
                    showKeyboard();
                    toolbarState = State.Text;
                    break;
                }

                default:
                    break;
            }

            if (delegate != null) {
                delegate.onClickPluginsBtn();
            }

        }

    };


    private void showKeyboard() {
        showKeyboard(true);
    }

    public void hideKeyboard() {
        showKeyboard(false);
    }

    private void showKeyboard(boolean show) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (show) {

            if (!isKeyboardHide) return;

            isKeyboardHide = false;

            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);

            if (delegate != null) delegate.onKeyboardShow();
        } else {

            if (isKeyboardHide) return;
            isKeyboardHide = true;

            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);

            if (delegate != null) delegate.onKeyboardHide();
        }
    }


    private void switchTextVoiceBtnState(TextVoiceButtonState state) {
        if (state == TextVoiceButtonState.Voice) {
            textVoiceButton.setImageResource(R.drawable.toolview_inputvoice);
        } else {
            textVoiceButton.setImageResource(R.drawable.toolview_inputtext);
        }
    }

    private enum TextVoiceButtonState {
        Text, Voice
    }


    private void switchEmotionsBtnState(EmotionButtonState state) {

        if (state == EmotionButtonState.Emotion) {
            emotionButton.setImageResource(R.drawable.toolview_emotion);
        } else {
            //emotionButton.setImageResource(R.drawable.toolview_inputtext);
            emotionButton.setImageResource(R.mipmap.toolview_emotion_green);
        }
    }

    private enum EmotionButtonState {
        Emotion, Text
    }


    //中间
    private void switchInputVoiceAndTextViewState(TextVoiceViewState state) {
        if (state == TextVoiceViewState.Text) {
            inputEditText.setVisibility(View.VISIBLE);
            inputVoiceHolder.setVisibility(View.GONE);
        } else {
            inputEditText.setVisibility(View.GONE);
            inputVoiceHolder.setVisibility(View.VISIBLE);
        }
    }

    private enum TextVoiceViewState {
        Voice, Text
    }


    public void setDelegate(Delegate delegate) {
        this.delegate = delegate;
    }

    public State getToolbarState() {
        return toolbarState;
    }


    public void onEvent(BaseEvent event) {
        if (event instanceof EmojiClickEvent) {
            EmojiClickEvent clickEvent = (EmojiClickEvent) event;

            String text = clickEvent.getText();
            if (text.equals("")) {
                String inputText = inputEditText.getText().toString();
                if (!inputText.equals("")) {
                    inputText = inputText.substring(0, inputText.lastIndexOf("["));
                }
                inputEditText.setText(inputText);
            } else {
                inputEditText.setText(inputEditText.getText() + text);
            }

            inputEditText.setSelection(inputEditText.getText().length());
        }
    }


    /**
     * Created by zhonganyun on 15/6/11.
     */
    public static interface Delegate {

        void onKeyboardShow();

        void onKeyboardHide();


        //btn
        void onClickTextAndVoiceBtn();

        void onClickEmotionsBtn();

        void onClickPluginsBtn();

        //message
        void onTextMessageSend(String text);


        //voice
        void onVoiceRecordStart();

        void onVoiceRecordFinished();

        void onVoiceRecordCancelled();

        void onVoiceRecordTouchLeave();

        void onVoiceRecordTouchReturn();

    }

    /**
     * Created by zhonganyun on 15/6/10.
     */
    public static enum State {
        None,
        Text,
        Voice,
        Emotions,
        Plugins
    }
}
