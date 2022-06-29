package com.example.messageviewer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Logger;

public class Calls extends AppCompatActivity {

    DatabaseReference reference,calleelist;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    String sendmsg = "Sent";
    String remove = "Empty";

    Button recievelast,reset;
    TextView textView;

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Recieverdatastore> options =
                new FirebaseRecyclerOptions.Builder<Recieverdatastore>()
                        .setQuery(calleelist, Recieverdatastore.class)
                        .build();

        FirebaseRecyclerAdapter<Recieverdatastore, SenderViewHolder> adapter =
                new FirebaseRecyclerAdapter<Recieverdatastore, SenderViewHolder>(options) {
                    @NonNull
                    @Override
                    public SenderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.call_viewer, parent, false);
                        SenderViewHolder holder = new SenderViewHolder(view);
                        return holder;
                    }


                    @Override
                    protected void onBindViewHolder(@NonNull final SenderViewHolder holder, int position, @NonNull final Recieverdatastore model) {
                        holder.callernametag.setText(model.getCaller());
                        holder.calleraudio.setText(model.getRecorded_Call());
                        final String urlholder = model.getRecorded_Call();





                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {




                                MediaPlayer mediaPlayer = new MediaPlayer();
                                try {
                                    mediaPlayer.setDataSource(urlholder);
                                    mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                                        @Override
                                        public void onPrepared(MediaPlayer mediaPlayer) {
                                            starter(mediaPlayer);


                                        }
                                    });
                                    mediaPlayer.prepareAsync();
                                } catch (IOException e) {

                                    e.printStackTrace();
                                }

                            }
                        });







                    }
                };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }



    private void starter(MediaPlayer mediaPlayer) {
        mediaPlayer.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calls);

        recyclerView = findViewById(R.id.calllist);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recievelast = findViewById(R.id.rlc);
        reset = findViewById(R.id.reset);
        textView = (TextView) findViewById(R.id.cc);





        reference = FirebaseDatabase.getInstance().getReference().child("Recieved Calls");
        calleelist = FirebaseDatabase.getInstance().getReference().child("Detailed Calls");

        recievelast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String ,Object> hashMap = new HashMap<>();
                hashMap.put("Command",sendmsg);

                reference.updateChildren(hashMap);

                updateTextview();






            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String ,Object> hashMap = new HashMap<>();
                hashMap.put("Command",remove);

                reference.updateChildren(hashMap);

                updateTextview();

            }
        });


    }

    private void updateTextview() {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                textView.setText(snapshot.child("Command").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


}