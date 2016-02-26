package im.mongo.ui.plugin.photo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Base64;

import net.datafans.android.common.helper.IOHelper;
import net.datafans.android.common.helper.ImageHelper;
import net.datafans.android.common.helper.LogHelper;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import im.mongo.core.model.content.MessageContent;
import im.mongo.core.model.content.media.ImageMessage;
import im.mongo.ui.plugin.PluginProvider;

/**
 * Created by zhonganyun on 16/2/5.
 */
public abstract class PhotoPluginProvider extends PluginProvider {

    protected void handleResult(Uri originalUri) throws FileNotFoundException {

        ImageMessage imageMessage = new ImageMessage();
        imageMessage.setLocalPath(originalUri);

        InputStream inputStream = new FileInputStream(new File(originalUri.getPath()));
        Bitmap source = BitmapFactory.decodeStream(inputStream);
        IOHelper.closeQuietly(inputStream);
        Bitmap thumb = ImageHelper.scale(source, 600);

        ByteArrayOutputStream thumbOutputStream = new ByteArrayOutputStream();
        source.compress(Bitmap.CompressFormat.JPEG, 70, thumbOutputStream);
        byte[] thumbBytes = thumbOutputStream.toByteArray();
        IOHelper.closeQuietly(thumbOutputStream);
        final String thumbData = Base64.encodeToString(thumbBytes, Base64.DEFAULT);
        LogHelper.debug(thumbData.length() + "");

        imageMessage.setThumbData(thumbData);

        send(imageMessage, MessageContent.Type.IMAGE);
    }

}
