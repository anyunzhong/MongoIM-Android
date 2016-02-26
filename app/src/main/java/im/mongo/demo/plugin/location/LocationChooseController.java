package im.mongo.demo.plugin.location;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuItem;

import net.datafans.android.common.widget.controller.FragmentController;

import im.mongo.R;

public class LocationChooseController extends FragmentController {

    private LocationChooseFragment fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected String getNavTitle() {
        return "选择位置";
    }

    @Override
    protected Fragment getRootFragment() {
        fragment = new LocationChooseFragment();
        return fragment;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.location_choose, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.send) {

            fragment.send();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
