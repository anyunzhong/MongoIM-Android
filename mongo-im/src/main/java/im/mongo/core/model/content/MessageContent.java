package im.mongo.core.model.content;

import net.datafans.android.common.helper.LogHelper;

import java.io.Serializable;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhonganyun on 15/11/20.
 */
public abstract class MessageContent implements Serializable {

    public abstract byte[] encode();

    public abstract String toJson();

    public abstract void decode(byte[] bytes);


    public static class Type {

        public static final String TEXT = "MIM:TextMsg";
        public static final String VOICE = "MIM:VoiceMsg";
        public static final String IMAGE = "MIM:ImageMsg";
        public static final String RED_BAG = "MIM:RedBagMsg";
        public static final String LOCATION = "MIM:LocationMsg";
        public static final String SHARE = "MIM:ShareMsg";
        public static final String EMOTION = "MIM:EmotionMsg";
        public static final String NAME_CARD = "MIM:NameCardMsg";
        public static final String SHORT_VIDEO = "MIM:ShortVideoMsg";
        public static final String InfoNotify = "MIM:InfoNotifyMsg";


        public static final String RC_RED_BAG = "RC:MIM:RedBagMsg";
        public static final String RC_SHARE = "RC:MIM:ShareMsg";
        public static final String RC_EMOTION = "RC:MIM:EmotionMsg";
        public static final String RC_NAME_CARD = "RC:MIM:NameCardMsg";
        public static final String RC_SHORT_VIDEO = "RC:MIM:ShortVideoMsg";
    }


    @Target({ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    public static @interface Tag {
        int NONE = 0;
        int PERSIST = 1;
        int COUNT = 2;

        String type();

        int action() default 0;
    }


    public static class Schema {
        private Class<? extends MessageContent> clazz;
        private boolean isPersist = false;
        private boolean isCount = false;

        public Class<? extends MessageContent> getClazz() {
            return clazz;
        }

        public Schema setClazz(Class<? extends MessageContent> clazz) {
            this.clazz = clazz;
            return this;
        }

        public boolean isPersist() {
            return isPersist;
        }

        public void setIsPersist(boolean isPersist) {
            this.isPersist = isPersist;
        }

        public boolean isCount() {
            return isCount;
        }

        public void setIsCount(boolean isCount) {
            this.isCount = isCount;
        }
    }


    public static class Manager {
        private static Manager manager = new Manager();

        private Map<String, Schema> contentMap = new HashMap<>();

        private Manager() {
        }

        public static Manager sharedInstance() {
            return manager;
        }

        public void register(String type, Schema info) {
            contentMap.put(type, info);
        }

        public MessageContent create(String type) {
            Class<? extends MessageContent> clazz = contentMap.get(type).getClazz();
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


    }
}
