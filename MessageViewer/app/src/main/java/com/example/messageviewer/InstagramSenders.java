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

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class InstagramSenders extends AppCompatActivity {

    DatabaseReference reference;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instagram_senders);

        recyclerView = findViewById(R.id.instalist);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);



        reference = FirebaseDatabase.getInstance().getReference().child("Instagram Senders");





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

                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.whatsappsenderviewer, parent, false);
                        SenderViewHolder holder = new SenderViewHolder(view);
                        return holder;
                    }


                    @Override
                    protected void onBindViewHolder(@NonNull final SenderViewHolder holder, int position, @NonNull final Recieverdatastore model) {
                        holder.whatsappsender.setText(model.getSender());

                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(InstagramSenders.this,DetailedInstagramView.class);
                                intent.putExtra("sendersname", model.getSender());
                                startActivity(intent);
                            }
                        });



                    }
                };
        recyclerView.setAdapter(adapter);
        adapter.startListening();


    }
}