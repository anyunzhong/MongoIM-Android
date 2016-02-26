package im.mongo.core.model;

import im.mongo.core.model.content.MessageContent;

/**
 * Created by zhonganyun on 15/11/20.
 */
public class Message {

    private int messageId;
    private String targetId;
    private Direction direction;
    private String senderId;
    private Conversation.Type conversationType;
    private ReceiveStatus receiveStatus;
    private SendStatus sendStatus;
    private long receivedTime;
    private long sentTime;
    private String contentType;
    private MessageContent content;
    private String extra;
    private boolean showTime;

    public int getMessageId() {
        return messageId;
    }

    public Message setMessageId(int messageId) {
        this.messageId = messageId;
        return this;
    }

    public String getTargetId() {
        return targetId;
    }

    public Message setTargetId(String targetId) {
        this.targetId = targetId;
        return this;
    }

    public Direction getDirection() {
        return direction;
    }

    public Message setDirection(Direction direction) {
        this.direction = direction;
        return this;
    }

    public String getSenderId() {
        return senderId;
    }

    public Message setSenderId(String senderId) {
        this.senderId = senderId;
        return this;
    }

    public Conversation.Type getConversationType() {
        return conversationType;
    }

    public Message setConversationType(Conversation.Type conversationType) {
        this.conversationType = conversationType;
        return this;
    }

    public ReceiveStatus getReceiveStatus() {
        return receiveStatus;
    }

    public Message setReceiveStatus(ReceiveStatus receiveStatus) {
        this.receiveStatus = receiveStatus;
        return this;
    }

    public SendStatus getSendStatus() {
        return sendStatus;
    }

    public Message setSendStatus(SendStatus sendStatus) {
        this.sendStatus = sendStatus;
        return this;
    }

    public long getReceivedTime() {
        return receivedTime;
    }

    public Message setReceivedTime(long receivedTime) {
        this.receivedTime = receivedTime;
        return this;
    }

    public long getSentTime() {
        return sentTime;
    }

    public Message setSentTime(long sentTime) {
        this.sentTime = sentTime;
        return this;
    }

    public String getContentType() {
        return contentType;
    }

    public Message setContentType(String contentType) {
        this.contentType = contentType;
        return this;
    }

    public MessageContent getContent() {
        return content;
    }

    public Message setContent(MessageContent content) {
        this.content = content;
        return this;
    }

    public String getExtra() {
        return extra;
    }

    public Message setExtra(String extra) {
        this.extra = extra;
        return this;
    }


    @Override
    public String toString() {
        return "Message{" +
                "messageId=" + messageId +
                ", targetId='" + targetId + '\'' +
                ", direction=" + direction +
                ", senderId='" + senderId + '\'' +
                ", conversationType=" + conversationType +
                ", receiveStatus=" + receiveStatus +
                ", sendStatus=" + sendStatus +
                ", receivedTime=" + receivedTime +
                ", sentTime=" + sentTime +
                ", contentType='" + contentType + '\'' +
                ", content=" + content +
                ", extra='" + extra + '\'' +
                '}';
    }

    public boolean isShowTime() {
        return showTime;
    }

    public Message setShowTime(boolean showTime) {
        this.showTime = showTime;
        return this;
    }

    public enum Direction {

        SEND(1),
        RECEIVE(2);

        private int direction;

        Direction(int direction) {
            this.direction = direction;
        }

        public int getDirection() {
            return direction;
        }

        public static Direction valueOf(int direction) {
            Direction[] values = values();
            for (Direction d : values) {
                if (direction == d.getDirection()) {
                    return d;
                }
            }
            return null;
        }

    }


    public enum SendStatus {

        SENDING(1),
        FAILED(2),
        SENT(3);

        private int status;

        SendStatus(int status) {
            this.status = status;
        }

        public int getStatus() {
            return status;
        }

        public static SendStatus valueOf(int status) {
            SendStatus[] values = values();
            for (SendStatus s : values) {
                if (status == s.getStatus()) {
                    return s;
                }
            }
            return null;
        }

    }


    public static class ReceiveStatus {
        public static final int READ = 1;
        public static final int LISTENED = 2;
        public static final int DOWNLOADED = 4;

        private int flag = 0;
        private boolean isRead = false;
        private boolean isListened = false;
        private boolean isDownload = false;

        public ReceiveStatus(int flag) {
            this.flag = flag;
            this.isRead = (flag & 1) == 1;
            this.isListened = (flag & 2) == 2;
            this.isDownload = (flag & 4) == 4;
        }

        public int getFlag() {
            return this.flag;
        }

        public boolean isRead() {
            return this.isRead;
        }

        public boolean isListened() {
            return this.isListened;
        }

        public boolean isDownload() {
            return this.isDownload;
        }

        public void setRead() {
            this.flag |= 1;
            this.isRead = true;
        }

        public void setListened() {
            this.flag |= 2;
            this.isListened = true;
        }

        public void setDownload() {
            this.flag |= 4;
            this.isDownload = true;
        }
    }


}
