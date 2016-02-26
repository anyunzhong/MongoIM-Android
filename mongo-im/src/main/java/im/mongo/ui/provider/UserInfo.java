package im.mongo.ui.provider;

/**
 * Created by zhonganyun on 15/11/20.
 */
public class UserInfo {
    private String userId;
    private String nick;
    private String avatar;

    public UserInfo(String userId, String nick, String avatar) {
        this.userId = userId;
        this.nick = nick;
        this.avatar = avatar;
    }

    public String getUserId() {
        return userId;
    }

    public UserInfo setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public String getNick() {
        return nick;
    }

    public UserInfo setNick(String nick) {
        this.nick = nick;
        return this;
    }

    public String getAvatar() {
        return avatar;
    }

    public UserInfo setAvatar(String avatar) {
        this.avatar = avatar;
        return this;
    }
}
