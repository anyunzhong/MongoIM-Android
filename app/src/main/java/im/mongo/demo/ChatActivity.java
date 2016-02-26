package im.mongo.demo;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import net.datafans.android.common.widget.controller.FragmentController;

import im.mongo.ui.view.message.MessageFragment;

public class ChatActivity extends FragmentController {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    protected Fragment getRootFragment() {
        return new MessageFragment();
    }

    @Override
    protected boolean enableReturnButton() {
        return true;
    }

    @Override
    protected String getNavTitle() {
        return "";
    }

}
