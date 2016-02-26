package im.mongo.ui.view.emotion;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import net.datafans.android.common.helper.LogHelper;
import net.datafans.android.common.widget.imageview.CommonImageView;
import net.datafans.android.common.widget.indicator.PageIndicator;

import java.util.ArrayList;
import java.util.List;

import im.mongo.MongoIM;
import im.mongo.R;
import im.mongo.ui.emotion.BaseEmotion;
import im.mongo.ui.emotion.EmotionManager;

public class EmotionViewFragment extends Fragment {


    private LinearLayout emotionContainer;

    private LinearLayout emotionTabContainer;

    private ViewPager viewPager;

    private List<View> pageViews;

    private int pageSize;

    private List<View> tabViews;

    private int lastSelectedTabIndex = 0;

    private PageIndicator pageIndicator;

    public EmotionViewFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        pageViews = new ArrayList<>();

        pageSize = EmotionManager.sharedInstance().getPageSize();

        //初始化所有view
        for (int i = 0; i < pageSize; i++) {
            View view = EmotionManager.sharedInstance().getView(i);
            pageViews.add(view);
        }


        View view = inflater.inflate(im.mongo.R.layout.fragment_emotion_view, container, false);
        emotionContainer = (LinearLayout) view.findViewById(im.mongo.R.id.emotion_view_container);
        viewPager = (ViewPager) emotionContainer.findViewById(im.mongo.R.id.emotion_viewpager);
        viewPager.setOnPageChangeListener(pageChangeListener);
        emotionTabContainer = (LinearLayout) view.findViewById(im.mongo.R.id.emotion_tab_container);
        pageIndicator = (PageIndicator) emotionContainer.findViewById(im.mongo.R.id.indicator);

        initTabs();

        EmotionPagerAdapter adapter = new EmotionPagerAdapter();
        viewPager.setAdapter(adapter);


        return view;
    }


    private void initTabs() {

        tabViews = new ArrayList<>();

        List<BaseEmotion> emotions = EmotionManager.sharedInstance().getEmotions();
        for (int i = 0; i < emotions.size(); i++) {
            final BaseEmotion emotion = emotions.get(i);
            View view = LayoutInflater.from(MongoIM.sharedInstance().getContext()).inflate(R.layout.emotion_tab_item, null);
            tabViews.add(view);
            if (i == 0) {
                view.setBackgroundResource(R.mipmap.emotion_tab_bg_focus);
                pageIndicator.setPageCount(emotion.getPageCount());
                pageIndicator.setCurrentPage(0);
            }
            CommonImageView imageView = (CommonImageView) view.findViewById(R.id.icon);
            emotionTabContainer.addView(view);
            if (emotion.getIcon() != null && !emotion.getIcon().equals(""))
                imageView.loadImage(emotion.getIcon());
            else imageView.getImageView().setImageResource(emotion.getIconRes());

            final int finalI = i;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pagerScrollToIndex(emotion.getPageOffset());
                    setCurrentSelectedTab(finalI);
                }
            });
        }

        //设置
        View view = LayoutInflater.from(MongoIM.sharedInstance().getContext()).inflate(R.layout.emotion_tab_item, null);
        CommonImageView imageView = (CommonImageView) view.findViewById(R.id.icon);
        emotionTabContainer.addView(view);
        imageView.getImageView().setImageResource(R.mipmap.emotion_setting);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void pagerScrollToIndex(int index) {
        viewPager.setCurrentItem(index, true);
    }

    private void setCurrentSelectedTab(int index) {
        if (lastSelectedTabIndex == index) return;
        View last = tabViews.get(lastSelectedTabIndex);
        last.setBackgroundResource(R.mipmap.emotion_tab_bg);
        lastSelectedTabIndex = index;
        View current = tabViews.get(index);
        current.setBackgroundResource(R.mipmap.emotion_tab_bg_focus);
    }

    private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            int index = EmotionManager.sharedInstance().getEmotionIndex(position);
            setCurrentSelectedTab(index);

            BaseEmotion emotion = EmotionManager.sharedInstance().getEmotion(position);
            pageIndicator.setPageCount(emotion.getPageCount());
            pageIndicator.setCurrentPage(position - emotion.getPageOffset());

            LogHelper.error("count: " + emotion.getPageCount() + "  current:" +(position-emotion.getPageOffset()));

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private class EmotionPagerAdapter extends PagerAdapter {

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

    public void hide() {
        emotionContainer.setVisibility(View.GONE);
    }


    public void show() {
        emotionContainer.setVisibility(View.VISIBLE);
    }


}
