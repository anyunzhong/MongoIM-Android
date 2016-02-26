package im.mongo.demo.extend.rong.rong2mongo;

import net.datafans.android.common.helper.LogHelper;

import java.util.HashMap;
import java.util.Map;

import io.rong.imlib.model.MessageContent;

/**
 * Created by zhonganyun on 16/2/9.
 */
public class RongCloudToMongoMessageContentAdapterManager {

    private final static RongCloudToMongoMessageContentAdapterManager manager = new RongCloudToMongoMessageContentAdapterManager();

    private Map<Class<? extends MessageContent>, RongCloudToMongoMessageContentAdapter> map = new HashMap<>();

    public static RongCloudToMongoMessageContentAdapterManager sharedInstance() {
        return manager;
    }

    public void register(Class<? extends MessageContent> contentClass, Class<? extends RongCloudToMongoMessageContentAdapter> adapterClass) {
        try {
            map.put(contentClass, adapterClass.newInstance());
        } catch (Exception e) {
            LogHelper.error(e);
        }
    }

    public RongCloudToMongoMessageContentAdapter getAdapter(Class<? extends MessageContent> contentClass) {
        return map.get(contentClass);
    }


}
