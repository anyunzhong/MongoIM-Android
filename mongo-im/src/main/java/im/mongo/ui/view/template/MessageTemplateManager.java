package im.mongo.ui.view.template;

import android.app.Activity;

import net.datafans.android.common.helper.LogHelper;
import net.datafans.android.common.widget.table.TableViewCell;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import im.mongo.core.model.Message;

/**
 * Created by zhonganyun on 16/2/13.
 */
public class MessageTemplateManager {
    private static MessageTemplateManager manager = new MessageTemplateManager();

    private Map<String, Class<? extends TableViewCell>> tplMap = new HashMap<>();
    private Map<String, Integer> typeMap;

    private Map<String, ClickHandler> handlerMap = new HashMap<>();

    private MessageTemplateManager() {
    }

    public static MessageTemplateManager sharedInstance() {
        return manager;
    }

    public void register(String type, Class<? extends TableViewCell> clazz) {
        tplMap.put(type, clazz);
    }

    public int getCellTypeCount() {
        return tplMap.keySet().size();
    }


    public int getTypeIndex(String type) {

        if (typeMap == null) {
            typeMap = new HashMap<>();
            Set<String> keyset = tplMap.keySet();
            int counter = 0;
            for (String key : keyset) {
                typeMap.put(key, counter);
                counter++;
            }
        }

        return typeMap.get(type);

    }


    public TableViewCell create(String type) {
        Class<? extends TableViewCell> clazz = tplMap.get(type);
        if (clazz == null) {
            //TODO
            return null;
        }

        try {
            return clazz.newInstance();
        } catch (Exception e) {
            LogHelper.error(e.toString());
        }
        return null;
    }


    public interface ClickHandler {
        void onClickMessage(Message message, Activity activity);
    }


    public void registerClickHandler(String contentType, ClickHandler handler) {
        handlerMap.put(contentType, handler);
    }

    public ClickHandler getClickHandler(String contentType) {
        return handlerMap.get(contentType);
    }


}
