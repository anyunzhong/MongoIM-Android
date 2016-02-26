package im.mongo.ui.plugin.favourite;


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
import im.mongo.core.model.content.media.ShareMessage;

public class FavouriteChooseFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_favourite_send, container, false);
        TextView textView = (TextView) view.findViewById(R.id.text);


        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ShareMessage shareMessage = new ShareMessage();
                shareMessage.setTitle("无器械健身，是否真的比健身房器械训练要好？");
                shareMessage.setDesc("最近看了两本书，一本是无器械健身，一本是囚徒健身，这两本都是提倡利用自身重量做无器械健身的，而且在书中都有点明利用健身房器械健身效果不如自重健身好，我本人只有健身房练器械的经历，所以想问一下有无达人在这两方面都有经验的，去健身房利用器械健身是否像书中说的有害？");
                shareMessage.setThumb("http://img.taopic.com/uploads/allimg/140503/235072-14050309304870.jpg");
                shareMessage.setLink("http://daily.zhihu.com/story/3913827");
                shareMessage.setSourceLogo("http://file.market.xiaomi.com/thumbnail/PNG/l20/AppStore/0413ed456e648472f3164dd83180f072f8d4aa023");
                shareMessage.setSourceName("网易新闻");

                Intent intent = new Intent();
                intent.putExtra("content", shareMessage);
                intent.putExtra("type", MessageContent.Type.SHARE);

                //返回结果
                getActivity().setResult(Activity.RESULT_OK, intent);

                //退出
                getActivity().finish();
            }
        });
        return view;
    }


}
