package im.mongo.ui.view.plugin;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;

import net.datafans.android.common.widget.indicator.PageIndicator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import im.mongo.R;
import im.mongo.ui.plugin.PluginProviderManager;
import im.mongo.ui.plugin.PluginProvider;

public class PluginViewFragment extends Fragment{


    private RelativeLayout pluginContainer;

    private List<PluginProvider> providers;

    private List<View> pageViews;

    private final int PAGE_ITEM_COUNT = 8;

    private int pageSize = 0;

    private int currentClickIndex = -1;

    private PageIndicator pageIndicator;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        pageViews = new ArrayList<>();
        initTabViews();


        View view = inflater.inflate(R.layout.fragment_plugin_view, container, false);

        pluginContainer = (RelativeLayout) view.findViewById(R.id.plugin_view_container);
        ViewPager viewPager = (ViewPager) pluginContainer.findViewById(R.id.plugin_viewpager);

        PluginPagerAdapter adapter = new PluginPagerAdapter();
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(pageChangeListener);

        pageIndicator = (PageIndicator) pluginContainer.findViewById(R.id.indicator);
        pageIndicator.setPageCount(pageSize);
        pageIndicator.setCurrentPage(0);


        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        Uri uri = getActivity().getIntent().getData();
        String type = uri.getQueryParameter("conversationType");

        providers = PluginProviderManager.sharedInstance().getProviders(type);


        for (PluginProvider provider : providers) {
            provider.onAttached(this);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        for (PluginProvider provider : providers) {
            provider.onDetached();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (currentClickIndex < 0) return;
        providers.get(currentClickIndex).onActivityResult(requestCode, resultCode, data);
    }

    private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            pageIndicator.setCurrentPage(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private void initTabViews() {

        pageSize = (int) Math.ceil(providers.size() / (double) PAGE_ITEM_COUNT);

        for (int i = 0; i < pageSize; i++) {
            pageViews.add(getPageView(i));
        }

    }

    public void hide() {
        pluginContainer.setVisibility(View.GONE);
    }


    public void show() {
        pluginContainer.setVisibility(View.VISIBLE);
    }


    private class PluginPagerAdapter extends PagerAdapter {

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {

            return arg0 == arg1;
        }

        @Override
        public int getCount() {

            return pageSize;
        }

        @Override
        public void destroyItem(ViewGroup container, int position,
                                Object object) {
            container.removeView(pageViews.get(position));

        }

        @Override
        public int getItemPosition(Object object) {

            return super.getItemPosition(object);
        }

        @Override
        public CharSequence getPageTitle(int position) {

            return "";
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = pageViews.get(position);
            container.addView(view);
            return view;
        }

    }


    private View getPageView(final int pageIndex) {

        View view = getActivity().getLayoutInflater().inflate(R.layout.plugin_grid, null);

        GridView gridView = (GridView) view.findViewById(R.id.plugin_gridview);
        gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));

        List<Map<String, ?>> tabPlugins = new ArrayList<>();
        for (int i = pageIndex * PAGE_ITEM_COUNT; i < (pageIndex + 1) * PAGE_ITEM_COUNT && i < providers.size(); i++) {

            PluginProvider plugin = providers.get(i);
            Map<String, Object> item = new HashMap<>();
            item.put("image", plugin.getIcon());
            item.put("text", plugin.getName());
            tabPlugins.add(item);
        }
        String[] from = {"image", "text"};
        int[] to = {R.id.image, R.id.text};
        SimpleAdapter adapter = new SimpleAdapter(getActivity(), tabPlugins, R.layout.plugin_item, from, to);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int index, long l) {

                PluginProvider plugin = providers.get(pageIndex * PAGE_ITEM_COUNT + index);

                currentClickIndex = pageIndex * PAGE_ITEM_COUNT + index;

                if (plugin == null) return;

                plugin.onClick();
            }
        });

        return view;

    }

}
