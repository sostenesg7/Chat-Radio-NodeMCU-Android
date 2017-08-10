package com.dev.sostenes.rfnodemcuchat.message;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

import com.dev.sostenes.rfnodemcuchat.R;

import java.util.List;

/**
 * Created by Sostenes on 08/08/2017.
 */

public class MessageAdapter extends BaseAdapter {
    private List<Message> msgs;
    private Activity act;

    public MessageAdapter(List<Message> msgs, Activity activity) {
        this.msgs = msgs;
        act = activity;
    }

    @Override
    public int getCount() {
        return msgs.size();
    }

    @Override
    public Object getItem(int position) {
        return msgs.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        MessageViewHolder viewHolder;

        if(convertView == null){
            view = act.getLayoutInflater()
                    .inflate(R.layout.layout_msg_item, parent, false);
            viewHolder = new MessageViewHolder(view);
            view.setTag(viewHolder);
        }else{
            view = convertView;
            viewHolder = (MessageViewHolder) view.getTag();
        }
        Message message = msgs.get(position);
        viewHolder.txtMsg.setText(message.getMessage());

        LayoutParams layoutParams = new LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);

        if(message.isReceived()){
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        }else {
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            if(message.isSentStatus()){
                viewHolder.txtMsg.setCheckMarkDrawable(android.R.drawable.checkbox_on_background);
            }else{
                viewHolder.txtMsg.setCheckMarkDrawable(android.R.drawable.checkbox_off_background);
            }
        }

        viewHolder.txtMsg.setLayoutParams(layoutParams);
        viewHolder.txtMsg.setChecked(message.isSentStatus());
        viewHolder.txtData.setText(message.getSentDate());
        return view;
    }
}
