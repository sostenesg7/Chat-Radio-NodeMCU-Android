package com.dev.sostenes.rfnodemcuchat.network;

import com.dev.sostenes.rfnodemcuchat.message.Message;

/**
 * Created by Sostenes on 09/08/2017.
 */

public interface MessageInterface {

    void onSuccess(Result result);
    void onSuccess(String message);
    void onError(String message);
    void onError(Result result);

}
