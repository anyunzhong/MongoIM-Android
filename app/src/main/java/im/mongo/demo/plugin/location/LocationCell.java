package im.mongo.demo.plugin.location;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import net.datafans.android.common.widget.table.TableViewCell;

import im.mongo.R;

/**
 * Created by zhonganyun on 16/2/8.
 */
public class LocationCell extends TableViewCell<LocationInfo> {

    private TextView nameTextView;
    private TextView addressTextView;
    private ImageView checkMarkView;


    public LocationCell(int layout, Context context) {
        super(layout, context);

        nameTextView = (TextView) cell.findViewById(R.id.name);
        addressTextView = (TextView) cell.findViewById(R.id.address);
        checkMarkView = (ImageView) cell.findViewById(R.id.checkmark);
    }

    @Override
    protected void refresh(LocationInfo locationInfo) {
        nameTextView.setText(locationInfo.getName());
        addressTextView.setText(locationInfo.getAddress());
        if (locationInfo.isChecked())
            checkMarkView.setVisibility(View.VISIBLE);
        else
            checkMarkView.setVisibility(View.GONE);
    }
}
