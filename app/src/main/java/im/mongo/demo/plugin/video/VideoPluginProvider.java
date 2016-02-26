package im.mongo.demo.plugin.video;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.Environment;
import android.util.Base64;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.hiteshsondhi88.libffmpeg.ExecuteBinaryResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.FFmpeg;
import com.github.hiteshsondhi88.libffmpeg.LoadBinaryResponseHandler;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import net.datafans.android.common.helper.IOHelper;
import net.datafans.android.common.helper.LogHelper;

import org.apache.http.Header;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import im.mongo.R;
import im.mongo.core.model.content.MessageContent;
import im.mongo.core.model.content.media.ShortVideoMessage;
import im.mongo.ui.plugin.PluginProvider;
import im.mongo.ui.plugin.video.VideoRecordActivity;

/**
 * Created by zhonganyun on 16/2/7.
 */
public class VideoPluginProvider extends PluginProvider {

    private static AsyncHttpClient client = new AsyncHttpClient();

    public VideoPluginProvider() {
        icon = R.mipmap.sharemore_sight;
        name = "视频";
    }

    @Override
    public void onClickDefault() {

        Intent intent = new Intent(getChatViewFragment().getActivity(), VideoRecordActivity.class);
        startActivityForResult(intent, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, final Intent data) {
        if (resultCode != Activity.RESULT_OK) return;

        String videoPath = data.getStringExtra("videoPath");

        FFmpeg ffmpeg = FFmpeg.getInstance(getChatViewFragment().getActivity());
        try {
            ffmpeg.loadBinary(new LoadBinaryResponseHandler() {

                @Override
                public void onStart() {
                }

                @Override
                public void onFailure() {
                }

                @Override
                public void onSuccess() {
                }

                @Override
                public void onFinish() {
                }
            });

            final String output = videoPath.replace(".mp4", "_final.mp4");
            String cmd = String.format("-y -i %s -vf crop=480:480:0:0 -acodec copy -threads 5 %s", videoPath, output);

            ffmpeg.execute(cmd, new ExecuteBinaryResponseHandler() {

                @Override
                public void onStart() {
                }

                @Override
                public void onProgress(String message) {
                    LogHelper.info("处理进度 " + message);
                }

                @Override
                public void onFailure(String message) {
                    LogHelper.error("处理失败 " + message);
                }

                @Override
                public void onSuccess(String message) {
                    LogHelper.info("处理成功 " + message);
                }

                @Override
                public void onFinish() {
                    LogHelper.info("处理完成");

                    String screenShot = getScreenShot(output);
                    upload(output, screenShot);
                }
            });




        } catch (Exception e) {
            LogHelper.error(e);
        }

    }

    private void upload(String path, final String screenShot) {

        File file = new File(path);
        RequestParams params = new RequestParams();
        try {
            params.put("file", file);
        } catch (FileNotFoundException e) {
            LogHelper.error(e);
        }

        client.post("http://debug.crazyfit.appcomeon.com/upload/video/", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                JSONObject jsonObject = JSON.parseObject(new String(responseBody));
                String url = jsonObject.getJSONObject("data").getString("url");

                LogHelper.info(url);

                try {
                    ShortVideoMessage shortVideoMessage = new ShortVideoMessage();
                    shortVideoMessage.setUrl(url);

                    byte[] bytes = IOHelper.toByteArray(new FileInputStream(new File(screenShot)));
                    String data = Base64.encodeToString(bytes, Base64.DEFAULT);
                    shortVideoMessage.setCover(data);

                    send(shortVideoMessage, MessageContent.Type.SHORT_VIDEO);
                } catch (Exception e) {
                    LogHelper.error(e);
                }


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    private String getScreenShot(String filepath) {
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(filepath);
        Bitmap bitmap = retriever.getFrameAtTime(1000 * 1000, MediaMetadataRetriever.OPTION_CLOSEST_SYNC);
        String path = Environment.getExternalStorageDirectory() + File.separator + System.currentTimeMillis() + ".jpg";
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(path);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fos);
            fos.close();
        } catch (Exception e) {
            LogHelper.error(e);
        }

        return path;

    }

}
