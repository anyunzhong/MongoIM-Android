package im.mongo.ui.plugin.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.LinearLayout;

import net.datafans.android.common.widget.imageview.CommonImageView;

import java.util.ArrayList;
import java.util.List;

import im.mongo.MongoIM;
import im.mongo.R;
import im.mongo.ui.emotion.BaseEmotion;
import im.mongo.ui.emotion.EmotionManager;

/**
 * Created by zhonganyun on 16/2/11.
 */
public class WebViewFragment extends Fragment {

    public WebViewFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Intent intent = getActivity().getIntent();

        String url = intent.getStringExtra("url");
        View view = inflater.inflate(R.layout.fragment_web_view, null);
        WebView webView = (WebView) view.findViewById(R.id.webView);
        webView.loadUrl(url);
        return view;
    }
}
