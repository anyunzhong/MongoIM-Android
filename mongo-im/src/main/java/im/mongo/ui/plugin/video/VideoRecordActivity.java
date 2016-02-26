package im.mongo.ui.plugin.video;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.hardware.Camera;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import net.datafans.android.common.helper.LogHelper;

import java.io.File;
import java.io.IOException;

import im.mongo.R;

public class VideoRecordActivity extends AppCompatActivity {

    private SurfaceHolder surfaceHolder;

    private MediaRecorder mediaRecorder;
    private Camera camera;

    private File tempFile;

    private View timeIndicator;
    private View pressContainer;
    private TextView releaseLabel;

    private double length = 0;
    private boolean isFinished = false;

    private int screenWidth;


    private static final int MAX_VIDEO_LENGTH = 8;

    private int greenColor = Color.rgb(9, 187, 7);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setContentView(R.layout.activity_video_record);

        initView();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        releaseCamera();
        releaseRecorder();
    }

    private void initView() {

        WindowManager wm = this.getWindowManager();
        screenWidth = wm.getDefaultDisplay().getWidth();

        SurfaceView surfaceView = (SurfaceView) findViewById(R.id.surface);
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(new SurfaceCallBack());
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);


        pressContainer = findViewById(R.id.pressContainer);
        ImageButton pressButton = (ImageButton) findViewById(R.id.imageButton);
        pressButton.setOnTouchListener(buttonTouchListener);

        timeIndicator = findViewById(R.id.timeIndicator);
        timeIndicator.setVisibility(View.GONE);

        releaseLabel = (TextView) findViewById(R.id.releaseLabel);
        releaseLabel.setVisibility(View.GONE);
    }


    private void initRecord() {

        createFile();

        try {
            initCamera();
        } catch (IOException e) {
            e.printStackTrace();
        }

        mediaRecorder = new MediaRecorder();
        mediaRecorder.reset();
        if (camera != null) mediaRecorder.setCamera(camera);

        mediaRecorder.setOnErrorListener(new MediaRecorder.OnErrorListener() {
            @Override
            public void onError(MediaRecorder mediaRecorder, int i, int i1) {
                LogHelper.error(i + "");
            }
        });


        mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);

        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);

        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        mediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);


        mediaRecorder.setVideoEncodingBitRate((int)(1024 * 1024 * 0.5));
        mediaRecorder.setVideoSize(640, 480);
        mediaRecorder.setOrientationHint(90);
        mediaRecorder.setVideoFrameRate(20);

        mediaRecorder.setPreviewDisplay(surfaceHolder.getSurface());
        mediaRecorder.setOutputFile(tempFile.getAbsolutePath());
        try {
            mediaRecorder.prepare();
        } catch (IOException e) {
            LogHelper.error(e);
        }
    }

    private void releaseRecorder() {
        if (mediaRecorder != null) {

            mediaRecorder.setOnErrorListener(null);
            mediaRecorder.setPreviewDisplay(null);
            mediaRecorder.stop();
            mediaRecorder.reset();
            mediaRecorder.release();
            mediaRecorder = null;
        }
    }


    private void initCamera() throws IOException {
        if (camera != null) {
            return;
        }
        try {
            camera = Camera.open();
        } catch (Exception e) {
            LogHelper.error(e);
        }
        if (camera == null)
            return;

        camera.getParameters().set("orientation", "portrait");
        camera.setDisplayOrientation(90);
        camera.setPreviewDisplay(surfaceHolder);
        camera.startPreview();
        camera.unlock();
    }

    private void releaseCamera() {
        if (camera != null) {
            camera.setPreviewCallback(null);
            camera.stopPreview();
            camera.lock();
            camera.release();
            camera = null;
        }
    }

    private class SurfaceCallBack implements Callback {

        @Override
        public void surfaceCreated(SurfaceHolder holder) {

            try {
                initCamera();
            } catch (IOException e) {
                LogHelper.error(e);

            }
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width,
                                   int height) {

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            releaseCamera();
        }

    }

    private void createFile() {
        File sampleDir = new File(Environment.getExternalStorageDirectory()
                + File.separator + "/video/");
        if (!sampleDir.exists()) {
            boolean result = sampleDir.mkdirs();
            LogHelper.info(result + "");
        } else {
            LogHelper.info("文件目录存在");
        }

        try {
            tempFile = File.createTempFile("temp", ".mp4", sampleDir);
            LogHelper.info(tempFile.getAbsolutePath());
        } catch (IOException e) {
            LogHelper.error(e);
        }
    }


    private float lastVoiceTouchY = 0.f;
    private View.OnTouchListener buttonTouchListener = new View.OnTouchListener() {

        @Override
        public boolean onTouch(View view, MotionEvent event) {

            float y = event.getY();

            if (event.getAction() == MotionEvent.ACTION_UP) {


                if (y < 0) {
                    LogHelper.info("从外部松开button");
                    onButtonUpOutside();

                } else {
                    LogHelper.info("从内部松开button");
                    onButtonUpInside();
                }
            }
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                LogHelper.info("按下button");
                onButtonDown();

            }


            if (lastVoiceTouchY > 0 && y < 0) {
                LogHelper.info("离开button区域");
                timeIndicator.setBackgroundColor(Color.RED);
                releaseLabel.setVisibility(View.VISIBLE);
            }

            if (lastVoiceTouchY < 0 && y > 0) {
                LogHelper.info("进入button区域");
                timeIndicator.setBackgroundColor(greenColor);
                releaseLabel.setVisibility(View.GONE);
            }

            lastVoiceTouchY = y;

            return false;
        }
    };

    private void onButtonDown() {
        length = 0;
        isFinished = false;

        hidePressButton();
        refreshTimeIndicator(1.0f);
        timeIndicator.setVisibility(View.VISIBLE);
        timeIndicator.setBackgroundColor(greenColor);

        releaseLabel.setVisibility(View.VISIBLE);

        handler.postDelayed(runnable, 500);
        startRecord();
    }

    private void onButtonUpOutside() {
        if (length >= MAX_VIDEO_LENGTH) return;
        stopRecord();
    }

    private void onButtonUpInside() {
        if (length >= MAX_VIDEO_LENGTH) return;

        if (length > 1) isFinished = true;

        stopRecord();
    }

    private void hidePressButton() {
        pressContainer.setVisibility(View.GONE);
    }

    private void showPressButton() {
        pressContainer.setVisibility(View.VISIBLE);
    }

    private void refreshTimeIndicator(float rate) {

        int top = timeIndicator.getTop();
        int width = (int) (screenWidth * (1 - rate));
        int height = timeIndicator.getHeight();
        int left = (int) (screenWidth * rate / 2);
        timeIndicator.layout(left, top, left + width, top + height);
    }


    private void startRecord() {
        initRecord();
        mediaRecorder.start();
    }

    private void stopRecord() {
        restoreView();

        releaseRecorder();
        releaseCamera();
        LogHelper.error("临时文件大小:" + tempFile.length() / 1024);

        if (isFinished) {
            Intent intent = new Intent();
            intent.putExtra("videoPath", tempFile.getAbsolutePath());
            setResult(Activity.RESULT_OK, intent);
            finish();
        }
    }

    private void restoreView() {
        showPressButton();
        timeIndicator.setVisibility(View.GONE);

        releaseLabel.setVisibility(View.GONE);
        handler.removeCallbacks(runnable);
    }


    private Handler handler = new Handler();

    private Runnable runnable = new Runnable() {

        public void run() {
            length = length + 0.5;

            refreshTimeIndicator((float) length / MAX_VIDEO_LENGTH);

            if (length >= MAX_VIDEO_LENGTH) {
                LogHelper.info("时间到 完成拍摄");
                stopRecord();
                isFinished = true;
                return;
            }
            handler.postDelayed(this, 500);
        }

    };


}
