package com.example.messageviewer;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class senderNameHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView sender;

    @Override
    public void onClick(View view) {

    }

    public senderNameHolder(@NonNull View itemView) {
        super(itemView);

        sender = (TextView) itemView.findViewById(R.id.sender);


    }
}
