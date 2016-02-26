package im.mongo.ui.plugin.redbag;

import android.app.Activity;
import android.content.Intent;

import net.datafans.android.common.helper.LogHelper;

import im.mongo.R;
import im.mongo.core.model.content.MessageContent;
import im.mongo.core.model.content.media.RedBagMessage;
import im.mongo.ui.plugin.PluginProvider;

/**
 * Created by zhonganyun on 16/2/7.
 */
public class RedBagPluginProvider extends PluginProvider {

    public static final int CREATE_RED_BAG = 3;

    public RedBagPluginProvider() {
        icon = R.mipmap.sharemore_redbag;
        name = "红包";
    }

}
