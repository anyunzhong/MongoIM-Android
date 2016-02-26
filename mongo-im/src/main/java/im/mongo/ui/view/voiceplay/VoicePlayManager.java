package im.mongo.ui.view.voiceplay;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.Base64;

import net.datafans.android.common.helper.IOHelper;
import net.datafans.android.common.helper.LogHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import im.mongo.MongoIM;
import im.mongo.core.model.content.media.VoiceMessage;

/**
 * Created by zhonganyun on 15/9/8.
 */
public class VoicePlayManager {

    private static VoicePlayManager manager = new VoicePlayManager();


    private VoicePlayButton previousButton;

    private MediaPlayer mediaPlayer;

    private VoicePlayManager() {


    }

    public static VoicePlayManager sharedIntance() {
        return manager;
    }


    public void play(VoicePlayButton button, final VoiceMessage voiceMessage) {
        if (previousButton != null && button != previousButton) {
            previousButton.stopPlaying();
        }

        previousButton = button;
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                previousButton.stopPlaying();
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
//                    String path = MongoIM.sharedInstance().getContext().getCacheDir() +"temp.mp3";
//                    File tempFile = new File(path);
//                    tempFile.deleteOnExit();
//
//                    FileOutputStream outputStream = new FileOutputStream(tempFile);
//                    byte[] data = Base64.decode(voiceMessage.getContent(), Base64.DEFAULT);
//                    IOHelper.write(data, outputStream);
//                    IOHelper.closeQuietly(outputStream);


                    FileInputStream inputStream = new FileInputStream(voiceMessage.getUri().getPath());
                    mediaPlayer.setDataSource(inputStream.getFD());
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                } catch (IOException e) {
                    LogHelper.error(e.toString());
                }
            }
        }).start();


    }


    public void stopPlay(VoicePlayButton button) {
        if (previousButton != null && button != previousButton) previousButton.stopPlaying();

        if (mediaPlayer == null) return;

        mediaPlayer.stop();
        mediaPlayer.release();
        mediaPlayer = null;
    }

}
