package im.mongo.ui.plugin.redbag;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import net.datafans.android.common.widget.controller.FragmentController;

import im.mongo.R;
import im.mongo.ui.plugin.PluginPresentController;

public class RedBagCreateController extends PluginPresentController {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected Fragment getRootFragment() {
        return new RedBagCreateFragment();
    }

    @Override
    protected String getNavTitle() {
        return "发红包";
    }
}
