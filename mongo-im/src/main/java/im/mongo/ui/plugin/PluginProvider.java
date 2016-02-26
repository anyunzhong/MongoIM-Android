package im.mongo.ui.plugin;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;

import net.datafans.android.common.helper.CacheHepler;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import im.mongo.MongoIM;
import im.mongo.core.model.content.MessageContent;
import im.mongo.ui.view.message.MessageFragment;
import im.mongo.ui.view.plugin.PluginViewFragment;

/**
 * Created by zhonganyun on 15/6/14.
 */
public abstract class PluginProvider {

    @SuppressLint("SimpleDateFormat")
    private static SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");

    protected int icon;
    protected String name;

    private PluginViewFragment fragment;


    public int getIcon() {
        return icon;
    }

    public String getName() {
        return name;
    }


    protected void onClickDefault() {
    }

    public void onClick() {
        PluginProviderManager manager = PluginProviderManager.sharedInstance();
        Class<? extends Activity> controller = manager.getPresentController(this.getClass());

        if (controller == null) {
            //走默认实现
            onClickDefault();
        } else {
            //用户自定义
            Intent intent = new Intent(getChatViewFragment().getActivity(), controller);
            startActivityForResult(intent, 1);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode != Activity.RESULT_OK) return;

        MessageContent content = (MessageContent) data.getSerializableExtra("content");
        String contentType = data.getStringExtra("type");
        send(content, contentType);
    }

    public void startActivityForResult(Intent intent, int requestCode) {
        fragment.startActivityForResult(intent, requestCode);
    }


    public void onAttached(PluginViewFragment fragment) {
        this.fragment = fragment;
    }

    public void onDetached() {
        this.fragment = null;
    }

    protected MessageFragment getChatViewFragment() {

        return (MessageFragment) fragment.getParentFragment();
    }

    public void send(MessageContent content, String contentType) {
        getChatViewFragment().send(content, contentType);
    }


    protected String saveImage(Bitmap bmp) throws Exception {
        String path = CacheHepler.getDiskCacheDir(MongoIM.sharedInstance().getContext(), formatter.format(new Date()));
        File file = new File(path);
        FileOutputStream os = new FileOutputStream(file);
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, os);
        os.flush();
        os.close();
        return file.getPath();
    }
}
