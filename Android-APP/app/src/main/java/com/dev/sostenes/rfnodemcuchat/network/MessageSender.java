package com.dev.sostenes.rfnodemcuchat.network;

import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Sostenes on 09/08/2017.
 */

public class MessageSender extends AsyncTask<Integer, Result, Result>{
    private HttpURLConnection connection;
    private MessageInterface callback;
    private String url;

    public MessageSender(MessageInterface callback, String url) {
        this.callback = callback;
        this.url = url;
    }

    private String receiveMessage(InputStream inputStream){
        StringBuffer msg;
        msg = new StringBuffer();
        Scanner scanner = new Scanner(inputStream);
        while (scanner.hasNext()){
            msg.append(scanner.next());
        }
        return msg.toString();
    }

    @Override
    protected Result doInBackground(Integer... params) {
        Result result;
        result = new Result();
        result.setMessageId(params[0]);
        try {
            connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setConnectTimeout(3000);
            connection.connect();
            if(connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                result.setMessage(receiveMessage(connection.getInputStream()));
            }
        }catch (Exception ex){
            ex.printStackTrace();
            result.setMessage(ex.getMessage());
            result.setError(true);
        }
        return result;
    }

    @Override
    protected void onPostExecute(Result result) {
        super.onPostExecute(result);
        if(result.isError()){
            callback.onError(result);
        }else{
            callback.onSuccess(result);
        }
    }
}

