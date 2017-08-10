package com.dev.sostenes.rfnodemcuchat.network;

/**
 * Created by Sostenes on 09/08/2017.
 */

public class MessageNotSentException extends Exception {
    public MessageNotSentException(String message) {
        super(message);
    }
}
