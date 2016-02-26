package im.mongo.ui.plugin;

import android.app.Activity;

import net.datafans.android.common.widget.controller.FragmentController;

/**
 * Created by zhonganyun on 16/2/13.
 */
public abstract class PluginPresentController extends FragmentController {
    protected PluginProvider pluginProvider;

    public PluginProvider getPluginProvider() {
        return pluginProvider;
    }

    public void setPluginProvider(PluginProvider pluginProvider) {
        this.pluginProvider = pluginProvider;
    }
}
