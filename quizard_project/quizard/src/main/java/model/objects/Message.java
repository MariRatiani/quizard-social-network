package model.objects;

import model.enums.MessageType;

import java.sql.Timestamp;

public class Message {
    private int messageId;
    private int fromId;
    private int toId;
    private MessageType messageType;
    private String message;
    private Timestamp timeSent;
    private String messageTxt;

    public Message() {
    }

    public Message(int messageId, int fromId, int toId, MessageType messageType, String message, Timestamp timeSent) {
        this.messageId = messageId;
        this.fromId = fromId;
        this.toId = toId;
        this.messageType = messageType;
        this.message = message;
        this.timeSent = timeSent;
    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public int getFromId() {
        return fromId;
    }

    public void setFromId(int fromId) {
        this.fromId = fromId;
    }

    public int getToId() {
        return toId;
    }

    public void setToId(int toId) {
        this.toId = toId;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Timestamp getTimeSent() {
        return timeSent;
    }

    public void setTimeSent(Timestamp timeSent) {
        this.timeSent = timeSent;
    }

    public String getMessageTxt() {
        return messageTxt;
    }

    public void setMessageTxt(String messageTxt) {
        this.messageTxt = messageTxt;
    }
}
