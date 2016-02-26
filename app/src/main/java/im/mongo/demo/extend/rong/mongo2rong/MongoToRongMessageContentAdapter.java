package im.mongo.demo.extend.rong.mongo2rong;


import io.rong.imlib.model.MessageContent;

/**
 * Created by zhonganyun on 16/2/9.
 */
public abstract class MongoToRongMessageContentAdapter<T extends im.mongo.core.model.content.MessageContent> {

    public abstract MessageContent getRongCloudMessageContent(T content);

}
