package im.mongo.ui.emotion;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import net.datafans.android.common.helper.ResHelper;
import net.datafans.android.common.helper.face.FaceHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.greenrobot.event.EventBus;
import im.mongo.MongoIM;
import im.mongo.R;
import im.mongo.ui.event.EmojiClickEvent;

/**
 * Created by zhonganyun on 16/2/11.
 */
public class EmojiEmotion extends BaseEmotion {


    private final int PAGE_ITEM_COUNT = 21;
    private final int PAGE_COUNT = 5;

    public EmojiEmotion() {
        setPageCount(PAGE_COUNT);
        setItemCount(PAGE_ITEM_COUNT * PAGE_COUNT);
        setIconRes(R.mipmap.emotion_emoji);
    }


    @Override
    public View getPageView(final int pageIndex) {

        final Context context = MongoIM.sharedInstance().getContext();

        View view = LayoutInflater.from(context).inflate(R.layout.emotion_emoji_grid, null);

        GridView gridView = (GridView) view.findViewById(R.id.gridview);

        List<Map<String, ?>> tabPlugins = new ArrayList<>();
        for (int i = pageIndex * PAGE_ITEM_COUNT; i < (pageIndex + 1) * PAGE_ITEM_COUNT && i < 105; i++) {
            Map<String, Object> item = new HashMap<>();
            if (i == (pageIndex + 1) * PAGE_ITEM_COUNT - 1) {
                item.put("image", R.mipmap.emoji_delete);
            } else {
                item.put("image", ResHelper.getMipmapResId("expression_" + (i + 1 - pageIndex)));
            }
            tabPlugins.add(item);
        }
        String[] from = {"image"};
        int[] to = {R.id.image};
        SimpleAdapter adapter = new SimpleAdapter(context, tabPlugins, R.layout.emotion_emoji_item, from, to);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int p, long l) {

                EmojiClickEvent event = new EmojiClickEvent();
                if (p == PAGE_ITEM_COUNT - 1) {
                    event.setText("");
                } else {
                    int index = (pageIndex * PAGE_ITEM_COUNT + (p + 1) - pageIndex);
                    event.setText(FaceHelper.getEmojiText(context, index));
                }

                EventBus.getDefault().post(event);
            }
        });

        return view;
    }
}
