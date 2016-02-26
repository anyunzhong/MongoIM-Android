package im.mongo.demo.extend.rong.mongo2rong;

import net.datafans.android.common.helper.LogHelper;

import java.util.HashMap;
import java.util.Map;

import im.mongo.core.model.content.MessageContent;


/**
 * Created by zhonganyun on 16/2/9.
 */
public class MongoToRongCloudMessageContentAdapterManager {

    private final static MongoToRongCloudMessageContentAdapterManager manager = new MongoToRongCloudMessageContentAdapterManager();

    private Map<Class<? extends MessageContent>, MongoToRongMessageContentAdapter> map = new HashMap<>();

    public static MongoToRongCloudMessageContentAdapterManager sharedInstance() {
        return manager;
    }

    public void register(Class<? extends MessageContent> contentClass, Class<? extends MongoToRongMessageContentAdapter> adapterClass) {
        try {
            map.put(contentClass, adapterClass.newInstance());
        } catch (Exception e) {
            LogHelper.error(e);
        }
    }

    public MongoToRongMessageContentAdapter getAdapter(Class<? extends MessageContent> contentClass) {
        return map.get(contentClass);
    }


}
