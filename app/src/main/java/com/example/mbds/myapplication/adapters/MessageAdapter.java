package com.example.mbds.myapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.mbds.myapplication.R;
import com.example.mbds.myapplication.entities.Message;

import java.util.List;

public class MessageAdapter extends ArrayAdapter<Message> {

    private Context ctx;
    private List<Message> messages;

    public MessageAdapter(Context ctx, List<Message> messages) {
        super(ctx, 0, messages);

        this.ctx = ctx;
        this.messages = messages;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItem = convertView;

        if(listItem == null) {
            listItem = LayoutInflater.from(ctx).inflate(R.layout.list_item, parent, false);
        }

        Message curMes = messages.get(position);

        //TextView receiverTv = listItem.findViewById(R.id.receiver_tv);
        //receiverTv.setText(curMes.getReceiver());

        TextView contentTv = listItem.findViewById(R.id.content_tv);
        contentTv.setText(curMes.getContent());

        TextView receivedAt = listItem.findViewById(R.id.date_tv);
        receivedAt.setText(curMes.getFormattedReceivedAt());

        return listItem;
    }


}
