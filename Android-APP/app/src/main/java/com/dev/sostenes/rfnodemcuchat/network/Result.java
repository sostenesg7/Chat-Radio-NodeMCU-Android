package com.dev.sostenes.rfnodemcuchat.network;

/**
 * Created by Sostenes on 09/08/2017.
 */


public class Result{
    private String message;
    private boolean error;
    private int messageId;

    public Result() {
        this.error = false;
    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }
}