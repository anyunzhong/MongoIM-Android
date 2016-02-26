package im.mongo.ui.emotion;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import net.datafans.android.common.widget.imageview.CommonImageView;

import java.util.List;

import de.greenrobot.event.EventBus;
import im.mongo.MongoIM;
import im.mongo.R;
import im.mongo.ui.event.PackageEmotionClickEvent;

/**
 * Created by zhonganyun on 16/2/11.
 */
public class PackageEmotion extends BaseEmotion {

    private final static int ITEM_COUNT_PER_PAGE = 8;

    private List<PackageEmotionItem> items;

    public PackageEmotion(String icon, List<PackageEmotionItem> items) {
        this.items = items;
        setIcon(icon);
        setPageCount((int) Math.ceil(items.size() / (float) ITEM_COUNT_PER_PAGE));
        setItemCount(items.size());
    }

    @Override
    public View getPageView(final int pageIndex) {

        final Context context = MongoIM.sharedInstance().getContext();

        View view = LayoutInflater.from(context).inflate(R.layout.emotion_package_grid, null);

        GridView gridView = (GridView) view.findViewById(R.id.gridview);

        int itemCount;
        if (pageIndex < pageCount - 1) {
            itemCount = ITEM_COUNT_PER_PAGE;
        } else {
            itemCount = getItemCount() - ITEM_COUNT_PER_PAGE * pageIndex;
        }

        gridView.setAdapter(new GridAdapter(itemCount, pageIndex));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                PackageEmotionItem item = items.get(pageIndex*ITEM_COUNT_PER_PAGE+position);
                EventBus.getDefault().post(new PackageEmotionClickEvent(item));
            }
        });

        return view;
    }


    private class GridAdapter extends BaseAdapter {

        class ViewHolder {
            public CommonImageView imageView;
            public TextView nameView;
        }

        private int itemCount;
        private int pageIndex;

        public GridAdapter(int itemCount, int pageIndex) {
            this.itemCount = itemCount;
            this.pageIndex = pageIndex;
        }

        @Override
        public int getCount() {
            return itemCount;
        }

        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(MongoIM.sharedInstance().getContext()).inflate(R.layout.emotion_package_item, null);
                holder.imageView = (CommonImageView) convertView.findViewById(R.id.image);
                holder.nameView = (TextView) convertView.findViewById(R.id.name);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            PackageEmotionItem item = items.get(pageIndex*ITEM_COUNT_PER_PAGE+position);
            holder.imageView.loadImage(item.getRemoteThumb());
            holder.nameView.setText(item.getName());
            return convertView;
        }


    }
}
