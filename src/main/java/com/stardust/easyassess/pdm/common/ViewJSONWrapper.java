package com.stardust.easyassess.pdm.common;

import java.util.ArrayList;
import java.util.List;

public class ViewJSONWrapper {

    private ResultCode result = ResultCode.UNDEFINED;

    private List<Message> messages = new ArrayList<Message>();

    private Object data = null;


    public ViewJSONWrapper(ResultCode result, List<Message> messages, Object data) {
        this.result = result;
        this.messages = messages;
        this.data = data;
    }

    public ViewJSONWrapper(Object data) {
        this(ResultCode.SUCC, new ArrayList<Message>(), data);
    }

    public ViewJSONWrapper(Message message, Object data) {
        messages.add(message);
        this.data = data;
        result = message.getType().equals(Message.MessageType.ERROR) ? ResultCode.FAILED : ResultCode.SUCC;
    }

    public ViewJSONWrapper(Message message, ResultCode code) {
        messages.add(message);
        result = code;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public ResultCode getResult() {
        return result;
    }

    public void setResult(ResultCode result) {
        this.result = result;
    }
}
