package com.example.messageviewer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DetailedWhatsappView extends AppCompatActivity {

    String sendername = "";
    DatabaseReference reference;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    private ProgressDialog loadingBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_whatsapp_view);

        sendername = getIntent().getStringExtra("sendersname");




        recyclerView = findViewById(R.id.detailedwhatsapplist);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        sendername = sendername +"'s"+ " Detailed Whatsapp Messages";



        reference = FirebaseDatabase.getInstance().getReference().child(sendername);



    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Recieverdatastore> options =
                new FirebaseRecyclerOptions.Builder<Recieverdatastore>()
                        .setQuery(reference, Recieverdatastore.class)
                        .build();

        FirebaseRecyclerAdapter<Recieverdatastore, SenderViewHolder> adapter =
                new FirebaseRecyclerAdapter<Recieverdatastore, SenderViewHolder>(options) {
                    @NonNull
                    @Override
                    public SenderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.detailed_view, parent, false);
                        SenderViewHolder holder = new SenderViewHolder(view);
                        return holder;
                    }


                    @Override
                    protected void onBindViewHolder(@NonNull final SenderViewHolder holder, int position, @NonNull final Recieverdatastore model) {
                       // holder.whatsappsender.setText(model.getSender());
                        holder.sendermessage.setText(model.getMessage());
                        holder.sendertime.setText(model.getTime());
                        holder.senderdate.setText(model.getDate());





                    }
                };
        recyclerView.setAdapter(adapter);
        adapter.startListening();

    }

}