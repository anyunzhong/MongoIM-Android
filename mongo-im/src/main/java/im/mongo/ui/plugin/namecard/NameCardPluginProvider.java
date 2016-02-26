package im.mongo.ui.plugin.namecard;

import im.mongo.R;
import im.mongo.core.model.content.MessageContent;
import im.mongo.core.model.content.media.NameCardMessage;
import im.mongo.ui.plugin.PluginProvider;

/**
 * Created by zhonganyun on 16/2/7.
 */
public class NameCardPluginProvider extends PluginProvider {


    public NameCardPluginProvider() {
        icon = R.mipmap.sharemore_friendcard;
        name = "名片";
    }

}
