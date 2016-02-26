package im.mongo.ui.plugin.namecard;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import im.mongo.ui.plugin.PluginPresentController;

public class NameCardChooseController extends PluginPresentController {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected Fragment getRootFragment() {
        return new NameCardChooseFragment();
    }

    @Override
    protected String getNavTitle() {
        return "发送名片";
    }
}
