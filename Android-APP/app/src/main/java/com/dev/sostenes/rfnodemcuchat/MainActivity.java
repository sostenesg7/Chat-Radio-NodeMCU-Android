package com.dev.sostenes.rfnodemcuchat;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.dev.sostenes.rfnodemcuchat.message.Message;
import com.dev.sostenes.rfnodemcuchat.message.MessageAdapter;
import com.dev.sostenes.rfnodemcuchat.network.MessageInterface;
import com.dev.sostenes.rfnodemcuchat.network.MessageSender;
import com.dev.sostenes.rfnodemcuchat.network.Result;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MessageInterface{

    private Button btn1;
    private ListView lv1;
    private EditText edt1;
    private List<Message> msgList;
    private MessageAdapter msgsAdapter;
    private SimpleDateFormat dateFormat;
    private int messageCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        btn1 = (Button) findViewById(R.id.button7);
        lv1 = (ListView) findViewById(R.id.listView);
        edt1 = (EditText) findViewById(R.id.editText7);
        msgList = new ArrayList<>();

        msgsAdapter = new MessageAdapter(msgList, this);
        lv1.setAdapter(msgsAdapter);

        dateFormat = new SimpleDateFormat("HH:mm:ss");
        messageCount = 0;

        edt1.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                       // System.out.println("PASSOU DUAS VEZES");
                        if (!edt1.getText().toString().equals("")) {
                            putMessage(edt1.getText().toString());
                        }
                        return true;
                    }
                }
                return false;
            }
        });



        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!edt1.getText().toString().equals("")) {
                    putMessage(edt1.getText().toString());
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void putMessage(String msg){
        long date = new Date().getTime();
        dateFormat.format(date);
        Message message = new Message(messageCount, msg, dateFormat.format(date));
        msgList.add(message);
        msgsAdapter.notifyDataSetChanged();
        String url = "http://www.google.com?msg=" + edt1.getText().toString();
        new MessageSender(MainActivity.this, url).execute(messageCount++);
    }

    @Override
    public void onSuccess(Result result) {
        msgList.get(result.getMessageId()).setSentStatus(true);
        msgsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onSuccess(String message) {

    }

    @Override
    public void onError(String message) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG).show();
        Log.d("LOG: ", message);
    }

    @Override
    public void onError(Result result) {

    }
}
