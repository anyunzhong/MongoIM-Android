package im.mongo.ui.plugin.favourite;

import android.app.Activity;
import android.content.Intent;

import im.mongo.R;
import im.mongo.core.model.content.MessageContent;
import im.mongo.core.model.content.media.RedBagMessage;
import im.mongo.core.model.content.media.ShareMessage;
import im.mongo.ui.plugin.PluginProvider;
import im.mongo.ui.plugin.redbag.RedBagCreateController;

/**
 * Created by zhonganyun on 16/2/7.
 */
public class FavouritePluginProvider extends PluginProvider {


    public FavouritePluginProvider() {
        icon = R.mipmap.sharemore_myfav;
        name = "收藏";
    }

}
