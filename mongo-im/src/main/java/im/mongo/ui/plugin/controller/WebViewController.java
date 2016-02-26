package im.mongo.ui.plugin.controller;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;

import net.datafans.android.common.widget.controller.FragmentController;

public class WebViewController extends FragmentController {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();

        String title = intent.getStringExtra("title");
        changeTitle(title);
    }

    @Override
    protected Fragment getRootFragment() {
        return new WebViewFragment();
    }
}
