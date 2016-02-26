package im.mongo.ui.provider;

/**
 * Created by zhonganyun on 15/11/20.
 */
public interface UserInfoProvider {

    void getUserInfo(String uid, Callback callback);

    interface Callback {
        void callback(UserInfo userInfo);
    }

}
