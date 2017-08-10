package com.dev.sostenes.rfnodemcuchat.network;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Sostenes on 09/08/2017.
 */

public class MessageListener extends AsyncTask<String, String, String>{
    private HttpURLConnection connection;
    private MessageInterface callback;

    public MessageListener(MessageInterface callback) {
        this.callback = callback;
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
    protected String doInBackground(String... params) {
        final String url = params[0];
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                String msg;
                try {
                    connection = (HttpURLConnection) new URL(url).openConnection();
                    connection.setConnectTimeout(3000);
                    connection.connect();
                    if(connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        msg = receiveMessage(connection.getInputStream());
                        if(!msg.equals("")){
                            publishProgress(msg.toString());
                        }
                    }
                }catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        },0,500, TimeUnit.MILLISECONDS);
        return null;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
        if(callback!=null && values[0]!= null){
            callback.onSuccess(values[0]);
        }
    }
}
