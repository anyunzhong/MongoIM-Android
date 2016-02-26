package im.mongo.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import net.datafans.android.common.helper.LogHelper;
import net.datafans.android.common.widget.imageview.CommonImageView;

import java.util.ArrayList;
import java.util.List;

import im.mongo.MongoIM;
import im.mongo.ui.emotion.BaseEmotion;
import im.mongo.ui.emotion.EmotionManager;

/**
 * Created by zhonganyun on 16/2/11.
 */
public class EmotionTestFragment extends Fragment {

    private LinearLayout emotionContainer;

    private LinearLayout emotionTabContainer;

    private ViewPager viewPager;

    private List<View> pageViews;

    private int pageSize;


    public EmotionTestFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

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
        emotionTabContainer = (LinearLayout) view.findViewById(im.mongo.R.id.emotion_tab_container);

        initTabs();

        EmotionPagerAdapter adapter = new EmotionPagerAdapter();
        viewPager.setAdapter(adapter);

        //TODO ===  TEMP=====================
        //TODO ===  TEMP=====================
        //TODO ===  TEMP=====================
        //TODO ===  TEMP=====================
        //TODO ===  TEMP=====================
        //TODO ===  TEMP=====================
        //TODO ===  TEMP=====================
        emotionContainer.setVisibility(View.VISIBLE);

        return view;
    }

    private void initTabs() {
        List<BaseEmotion> emotions = EmotionManager.sharedInstance().getEmotions();
        for (final BaseEmotion emotion : emotions) {
            View view = LayoutInflater.from(MongoIM.sharedInstance().getContext()).inflate(R.layout.emotion_tab_item, null);
            CommonImageView imageView = (CommonImageView) view.findViewById(R.id.icon);
            emotionTabContainer.addView(view);
            if (emotion.getIcon() != null && !emotion.getIcon().equals(""))
                imageView.loadImage(emotion.getIcon());
            else imageView.getImageView().setImageResource(emotion.getIconRes());

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pagerScrollToIndex(emotion.getPageOffset());
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

    private void pagerScrollToIndex(int index){
        viewPager.setCurrentItem(index, true);
    }


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
}
