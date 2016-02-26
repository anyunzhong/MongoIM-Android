package im.mongo.ui.plugin.photo;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import net.datafans.android.common.AndroidCommon;
import net.datafans.android.common.helper.LogHelper;

import java.io.File;
import java.util.List;

import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;
import im.mongo.R;

/**
 * Created by zhonganyun on 15/6/14.
 */
public class PhotoAlbumPluginProvider extends PhotoPluginProvider {

    public static final int PICK_PHOTO_FROM_ALBUM = 1;

    public PhotoAlbumPluginProvider() {

        icon = R.mipmap.sharemore_pic;
        name = "照片";
    }

    @Override
    public void onClickDefault() {

        //Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        //intent.setType("image/*");
        //startActivityForResult(intent, PICK_PHOTO_FROM_ALBUM);

        AndroidCommon.configImagePicker(getChatViewFragment().getActivity().getTheme());

        GalleryFinal.openGalleryMuti(1, 9, new GalleryFinal.OnHanlderResultCallback() {
            public void onHanlderSuccess(int i, List<PhotoInfo> photoInfos) {

                for (PhotoInfo photoInfo: photoInfos){
                    try {
                        Uri originalUri = Uri.fromFile(new File(photoInfo.getPhotoPath()));
                        if (!originalUri.getScheme().equals("file"))
                            originalUri = getUri(originalUri.toString());
                        handleResult(originalUri);
                    } catch (Exception e) {
                        LogHelper.error(e);
                    }
                }
            }

            @Override
            public void onHanlderFailure(int i, String s) {
                Log.e("fail", s + "");
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, final Intent data) {

        if (resultCode != Activity.RESULT_OK) return;

        try {
            Uri originalUri = data.getData();
            if (!originalUri.getScheme().equals("file"))
                originalUri = getUri(originalUri.toString());
            handleResult(originalUri);
        } catch (Exception e) {
            LogHelper.error(e);
        }
    }

    private Uri getUri(String path) {
        Uri uri = Uri.parse(path);
        String[] strings = {MediaStore.Images.Media.DATA};
        Cursor cursor = getChatViewFragment().getActivity().managedQuery(uri, strings, null, null, null);
        int index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String imgPath = cursor.getString(index);
        File file = new File(imgPath);
        return Uri.fromFile(file);
    }

}
