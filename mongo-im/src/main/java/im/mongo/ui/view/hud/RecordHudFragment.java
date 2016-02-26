package im.mongo.ui.view.hud;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import im.mongo.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecordHudFragment extends Fragment {

    private RelativeLayout layout;
    private View recordHud;

    private ImageView recordMicphone;
    private ImageView recordCancel;
    private ImageView recordSignal;
    private TextView recordTip;

    public RecordHudFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        layout = (RelativeLayout) inflater.inflate(R.layout.record_hud, null);
        layout.setVisibility(View.GONE);

        recordMicphone = (ImageView) layout.findViewById(R.id.record_micphone);
        recordCancel = (ImageView) layout.findViewById(R.id.record_cancel);
        recordSignal = (ImageView) layout.findViewById(R.id.record_signal);
        recordTip = (TextView) layout.findViewById(R.id.record_tip_label);

        return layout;
    }

    public void show() {
        layout.setVisibility(View.VISIBLE);
    }


    public void hide() {
        layout.setVisibility(View.GONE);
    }


    public void changeState(State state) {

        if (state == State.Record) {
            recordMicphone.setVisibility(View.VISIBLE);
            recordSignal.setVisibility(View.VISIBLE);
            recordCancel.setVisibility(View.GONE);

            recordTip.setBackgroundColor(Color.TRANSPARENT);
            recordTip.setText("手指上滑 取消发送");
        }

        if (state == State.Cancel) {

            recordMicphone.setVisibility(View.GONE);
            recordSignal.setVisibility(View.GONE);
            recordCancel.setVisibility(View.VISIBLE);

            recordTip.setBackgroundResource(R.drawable.corner_red);
            recordTip.setText("松开上滑 取消发送");
        }
    }


    public void changeSignalLevel(int level) {

        if (level < 1 || level > 8) return;

        switch (level) {
            case 1:
                recordSignal.setImageResource(R.mipmap.record_signal_1);
                break;
            case 2:
                recordSignal.setImageResource(R.mipmap.record_signal_2);
                break;
            case 3:
                recordSignal.setImageResource(R.mipmap.record_signal_3);
                break;
            case 4:
                recordSignal.setImageResource(R.mipmap.record_signal_4);
                break;
            case 5:
                recordSignal.setImageResource(R.mipmap.record_signal_5);
                break;
            case 6:
                recordSignal.setImageResource(R.mipmap.record_signal_6);
                break;
            case 7:
                recordSignal.setImageResource(R.mipmap.record_signal_7);
                break;
            case 8:
                recordSignal.setImageResource(R.mipmap.record_signal_8);
                break;
        }
    }


    public enum State {
        Record, Cancel
    }


}
