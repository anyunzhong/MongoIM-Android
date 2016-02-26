package im.mongo.ui.plugin;

import android.app.Activity;

import net.datafans.android.common.helper.LogHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import im.mongo.core.model.Conversation;
import im.mongo.ui.plugin.favourite.FavouritePluginProvider;
import im.mongo.ui.plugin.namecard.NameCardPluginProvider;
import im.mongo.ui.plugin.photo.PhotoAlbumPluginProvider;
import im.mongo.ui.plugin.photo.PhotoCameraPluginProvider;
import im.mongo.ui.plugin.redbag.RedBagPluginProvider;

/**
 * Created by zhonganyun on 15/6/14.
 */
public class PluginProviderManager {

    private final static PluginProviderManager manager = new PluginProviderManager();

    public static PluginProviderManager sharedInstance() {
        return manager;
    }

    private Map<String, List<PluginProvider>> providersMap;

    private Map<Class<? extends PluginProvider>, Class<? extends Activity>> presentControllerMap = new HashMap<>();

    private PluginProviderManager() {
        providersMap = new HashMap<>();
    }

    private PluginProvider[] defaultProviders = new PluginProvider[]{
            new PhotoAlbumPluginProvider(),
            new PhotoCameraPluginProvider(),
            new RedBagPluginProvider(),
            new FavouritePluginProvider(),
            new NameCardPluginProvider()
    };


    public List<PluginProvider> getProviders(String conversationType) {

        List<PluginProvider> providers = providersMap.get(conversationType);
        if (providers == null) {
            providers = new ArrayList<>();
            providers.addAll(Arrays.asList(defaultProviders));
            providersMap.put(conversationType, providers);

        }
        return providers;
    }

    public void setProviders(Conversation.Type type, List<PluginProvider> providers) {
        providersMap.put(String.valueOf(type.getType()), providers);
    }

    public void registerPluginPresentController(Class<? extends PluginProvider> pluginClass, Class<? extends Activity> presentClass) {
        presentControllerMap.put(pluginClass, presentClass);
    }

    public Class<? extends Activity> getPresentController(Class<? extends PluginProvider> pluginClass) {
        return presentControllerMap.get(pluginClass);
    }

    public void registerPlugin(Class<? extends PluginProvider> pluginClass, Conversation.Type type) {
        List<PluginProvider> providers = getProviders(String.valueOf(type.getType()));
        try {
            providers.add(pluginClass.newInstance());
        } catch (Exception e) {
            LogHelper.error(e);
        }

    }


}
