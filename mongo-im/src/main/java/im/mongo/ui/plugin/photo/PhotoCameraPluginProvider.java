package im.mongo.ui.plugin.photo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

import net.datafans.android.common.helper.CacheHepler;
import net.datafans.android.common.helper.LogHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import im.mongo.MongoIM;
import im.mongo.R;

/**
 * Created by zhonganyun on 15/6/14.
 */
public class PhotoCameraPluginProvider extends PhotoPluginProvider {

    public static final int PICK_PHOTO_FROM_CAMERA = 2;

    public PhotoCameraPluginProvider() {

        icon = R.mipmap.sharemore_video;
        name = "拍摄";
    }


    @Override
    public void onClickDefault() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, PICK_PHOTO_FROM_CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, final Intent data) {

        if (resultCode != Activity.RESULT_OK) return;

        try {

            Bitmap bmp = (Bitmap) data.getExtras().get("data");
            String path = saveImage(bmp);

            Uri originalUri = Uri.fromFile(new File(path));
            handleResult(originalUri);
        } catch (Exception e) {
            LogHelper.error(e);
        }

    }

}
