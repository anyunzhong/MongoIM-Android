package im.mongo.core.model;

/**
 * Created by zhonganyun on 15/11/20.
 */
public class Conversation {

    private String targetId;
    private Type conversationType;
    private String title;
    private String subTitle;
    private int unreadCount;
    private long updateTime;


    public String getTargetId() {
        return targetId;
    }

    public Conversation setTargetId(String targetId) {
        this.targetId = targetId;
        return this;
    }

    public Type getConversationType() {
        return conversationType;
    }

    public Conversation setConversationType(Type conversationType) {
        this.conversationType = conversationType;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Conversation setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public Conversation setSubTitle(String subTitle) {
        this.subTitle = subTitle;
        return this;
    }

    public int getUnreadCount() {
        return unreadCount;
    }

    public Conversation setUnreadCount(int unreadCount) {
        this.unreadCount = unreadCount;
        return this;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public Conversation setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
        return this;
    }


    @Override
    public String toString() {
        return "Conversation{" +
                "targetId='" + targetId + '\'' +
                ", conversationType=" + conversationType +
                ", title='" + title + '\'' +
                ", subTitle='" + subTitle + '\'' +
                ", unreadCount=" + unreadCount +
                ", updateTime=" + updateTime +
                '}';
    }

    public enum Type {

        PRIVATE(1, "private"),
        DISCUSSION(2, "discussion"),
        GROUP(3, "group"),
        ROOM(4, "room"),
        SYSTEM(100, "system");

        private int type;
        private String alias = "";

        Type(int type, String alias) {
            this.type = type;
            this.alias = alias;
        }

        public int getType() {
            return this.type;
        }

        public String getAlias() {
            return this.alias;
        }

        public static Type valueOf(int type) {
            Type[] values = values();
            for (Type t : values) {
                if (type == t.getType()) {
                    return t;
                }
            }
            return null;
        }

    }

}
