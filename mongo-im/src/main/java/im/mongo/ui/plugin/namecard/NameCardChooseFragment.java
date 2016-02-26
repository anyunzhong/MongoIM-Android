package im.mongo.ui.plugin.namecard;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import im.mongo.R;
import im.mongo.core.model.content.MessageContent;
import im.mongo.core.model.content.media.NameCardMessage;

public class NameCardChooseFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_name_card_choose, container, false);
        TextView textView = (TextView) view.findViewById(R.id.text);


        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                NameCardMessage nameCardMessage = new NameCardMessage();
                nameCardMessage.setUserId("100020");
                nameCardMessage.setUserNick("Yanhua");
                nameCardMessage.setUserNum("huang");
                nameCardMessage.setUserAvatar("http://file-cdn.datafans.net/avatar/2.jpg");

                Intent intent = new Intent();
                intent.putExtra("content", nameCardMessage);
                intent.putExtra("type", MessageContent.Type.NAME_CARD);

                //返回结果
                getActivity().setResult(Activity.RESULT_OK, intent);

                //退出
                getActivity().finish();
            }
        });
        return view;
    }


}
