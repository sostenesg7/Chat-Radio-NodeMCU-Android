package com.dev.sostenes.rfnodemcuchat.message;

import android.view.View;
import android.widget.CheckedTextView;
import android.widget.TextView;

import com.dev.sostenes.rfnodemcuchat.R;

/**
 * Created by Sostenes on 08/08/2017.
 */

public class MessageViewHolder {
    final CheckedTextView txtMsg;
    final TextView txtData;

    public MessageViewHolder(View view) {
        txtMsg = (CheckedTextView) view.findViewById(R.id.txtMsg);
        txtData = (TextView) view.findViewById(R.id.txtData);
    }
}
