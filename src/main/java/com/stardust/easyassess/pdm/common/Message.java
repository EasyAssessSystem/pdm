package com.stardust.easyassess.pdm.common;

/**
 * Created by chengli on 5/22/16.
 */
public class Message {

    public enum MessageType {
        ERROR("error"), INFO("info");

        private String val = "";

        MessageType(String val) {
            this.val = val;
        }

        public String getCode() {
            return this.val;
        }
    }

    private String message;

    private MessageType type;

    public Message(Exception e) {
        type = MessageType.ERROR;
        message = e.getMessage();
    }

    public Message(String message, MessageType type) {
        this.message = message;
        this.type = type;
    }

    public Message(String message) {
        this(message, MessageType.INFO);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public MessageType getType() {
        return this.type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }
}
