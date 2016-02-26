package im.mongo.ui.plugin.redbag;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import net.datafans.android.common.helper.LogHelper;

import im.mongo.R;
import im.mongo.core.model.content.MessageContent;
import im.mongo.core.model.content.media.RedBagMessage;

public class RedBagCreateFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_red_bag_create, container, false);

        final EditText wordsEditText = (EditText) view.findViewById(R.id.words);
        Button send = (Button) view.findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String words = wordsEditText.getText().toString();

                RedBagMessage redBagMessage = new RedBagMessage();
                redBagMessage.setTitle(words);

                Intent intent = new Intent();
                intent.putExtra("content", redBagMessage);
                intent.putExtra("type", MessageContent.Type.RED_BAG);

                //返回结果
                getActivity().setResult(Activity.RESULT_OK, intent);

                //退出
                getActivity().finish();
            }
        });
        return view;
    }


}
