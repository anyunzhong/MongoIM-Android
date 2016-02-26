package im.mongo.ui.view.voiceplay;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageButton;

import im.mongo.R;
import im.mongo.core.model.Message;
import im.mongo.core.model.content.MessageContent;
import im.mongo.core.model.content.media.VoiceMessage;

/**
 * Created by zhonganyun on 15/9/8.
 */
public class VoicePlayButton extends ImageButton {


    private Message message;

    private Context context;

    private boolean isPlaying = false;

    public VoicePlayButton(Context context) {
        super(context);
        setBackgroundColor(Color.TRANSPARENT);
        setOnClickListener(listener);


    }

    private OnClickListener listener = new OnClickListener() {
        @Override
        public void onClick(View view) {

            if (isPlaying)
                stopPlaying();
            else
                startPlaying();
        }
    };


    public void stopPlaying() {
        if (message.getDirection() == Message.Direction.SEND) {
            setImageResource(R.mipmap.sender_voice_play);
        } else {
            setImageResource(R.mipmap.receiver_voice_play);
        }

        isPlaying = false;

        VoicePlayManager.sharedIntance().stopPlay(this);
    }

    public void startPlaying() {

        if (message.getDirection() == Message.Direction.SEND) {
            setImageResource(R.anim.sender_voice_play_anim);
        } else {
            setImageResource(R.anim.receiver_voice_play_anim);
        }

        isPlaying = true;

        MessageContent messageContent = message.getContent();
        if (messageContent instanceof VoiceMessage) {
            VoicePlayManager.sharedIntance().play(this, (VoiceMessage) messageContent);
        }

    }

    public void update(Context context, Message message) {

        this.message = message;
        this.context = context;


        if (message.getDirection() == Message.Direction.SEND) {
            if (isPlaying)
                setImageResource(R.anim.sender_voice_play_anim);
            else
                setImageResource(R.mipmap.sender_voice_play);
        } else {
            if (isPlaying)
                setImageResource(R.anim.receiver_voice_play_anim);
            else
                setImageResource(R.mipmap.receiver_voice_play);
        }
    }
}
