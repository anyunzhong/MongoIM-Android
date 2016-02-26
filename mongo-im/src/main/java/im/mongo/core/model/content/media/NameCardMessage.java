package im.mongo.core.model.content.media;

import im.mongo.core.model.content.MessageContent;

/**
 * Created by zhonganyun on 16/2/9.
 */
@MessageContent.Tag(type = MessageContent.Type.NAME_CARD, action = MessageContent.Tag.PERSIST | MessageContent.Tag.COUNT)
public class NameCardMessage extends MediaMessage {

    private String userId;
    private String userNick;
    private String userNum;
    private String userAvatar;

    @Override
    public byte[] encode() {
        return new byte[0];
    }

    @Override
    public String toJson() {
        return null;
    }

    @Override
    public void decode(byte[] bytes) {

    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserNick() {
        return userNick;
    }

    public void setUserNick(String userNick) {
        this.userNick = userNick;
    }

    public String getUserNum() {
        return userNum;
    }

    public void setUserNum(String userNum) {
        this.userNum = userNum;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    @Override
    public String toString() {
        return "NameCardMessage{" +
                "userId='" + userId + '\'' +
                ", userNick='" + userNick + '\'' +
                ", userNum='" + userNum + '\'' +
                ", userAvatar='" + userAvatar + '\'' +
                '}';
    }
}
