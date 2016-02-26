package im.mongo.demo.plugin.location;

import android.support.v4.app.Fragment;
import android.os.Bundle;

import net.datafans.android.common.widget.controller.FragmentController;

public class LocationController extends FragmentController {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected Fragment getRootFragment() {
        return new LocationFragment();
    }

    @Override
    protected String getNavTitle() {
        return "位置";
    }
}
