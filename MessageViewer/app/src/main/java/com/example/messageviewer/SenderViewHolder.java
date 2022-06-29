package com.example.messageviewer;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SenderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    TextView whatsappsender;
    TextView senderdate,sendertime,sendermessage;
    TextView callernametag,calleraudio;

    public SenderViewHolder(@NonNull View context) {
        super(context);

        whatsappsender = (TextView) context.findViewById(R.id.wsendername);
        senderdate = (TextView) context.findViewById(R.id.datev);
        sendertime = (TextView) context.findViewById(R.id.timev);
        sendermessage = (TextView) context.findViewById(R.id.messagev);
        callernametag = (TextView) context.findViewById(R.id.callname);
        calleraudio = (TextView) context.findViewById(R.id.urlholder);



    }

    @Override
    public void onClick(View view) {

    }
}
