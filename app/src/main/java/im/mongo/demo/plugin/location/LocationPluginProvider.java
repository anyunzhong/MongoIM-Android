package im.mongo.demo.plugin.location;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import net.datafans.android.common.helper.LogHelper;

import im.mongo.R;
import im.mongo.core.model.content.MessageContent;
import im.mongo.core.model.content.media.LocationMessage;
import im.mongo.ui.plugin.PluginProvider;

/**
 * Created by zhonganyun on 16/2/7.
 */
public class LocationPluginProvider extends PluginProvider {

    public static final int LOCATION_CHOOSE = 4;

    public LocationPluginProvider() {
        icon = R.mipmap.sharemore_location;
        name = "位置";
    }

    @Override
    public void onClickDefault() {

        Intent intent = new Intent(getChatViewFragment().getActivity(), LocationChooseController.class);
        startActivityForResult(intent, LOCATION_CHOOSE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, final Intent data) {
        if (resultCode != Activity.RESULT_OK) return;

        try {
            String address = data.getStringExtra("address");
            double lat = data.getDoubleExtra("lat", 0);
            double lng = data.getDoubleExtra("lng", 0);
            Uri uri = data.getParcelableExtra("uri");

            LocationMessage locationMessage = new LocationMessage();
            locationMessage.setLat(lat);
            locationMessage.setLng(lng);
            locationMessage.setAddress(address);
            locationMessage.setImageUri(uri);

            send(locationMessage, MessageContent.Type.LOCATION);

        } catch (Exception e) {
            LogHelper.error(e);
        }
    }
}
