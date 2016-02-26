package im.mongo.ui.plugin.favourite;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import im.mongo.ui.plugin.PluginPresentController;
import im.mongo.ui.plugin.namecard.NameCardChooseFragment;

public class FavouriteChooseController extends PluginPresentController {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected Fragment getRootFragment() {
        return new FavouriteChooseFragment();
    }

    @Override
    protected String getNavTitle() {
        return "发送文章";
    }
}
