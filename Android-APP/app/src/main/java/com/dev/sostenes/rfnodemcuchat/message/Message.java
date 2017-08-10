package com.dev.sostenes.rfnodemcuchat.message;

import java.util.Date;

/**
 * Created by Sostenes on 08/08/2017.
 */

public class Message {

    private int id;
    private String message;
    private boolean sentStatus;
    private boolean visualizedStatus;
    private String sentDate;
    private boolean received;

    public Message(int id,String message, String sentDate) {
        this.message = message;
        this.sentDate = sentDate;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isReceived() {
        return received;
    }

    public void setReceived(boolean received) {
        this.received = received;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSentStatus() {
        return sentStatus;
    }

    public void setSentStatus(boolean sentStatus) {
        this.sentStatus = sentStatus;
    }

    public boolean isVisualizedStatus() {
        return visualizedStatus;
    }

    public void setVisualizedStatus(boolean visualizedStatus) {
        this.visualizedStatus = visualizedStatus;
    }

    public String getSentDate() {
        return sentDate;
    }

    public void setSentDate(String sentDate) {
        this.sentDate = sentDate;
    }
}
