package com.example.messageviewer;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Frag1 extends Fragment{


//    DatabaseReference reference;
//    ProgressDialog loadingBar;
//    ListView listView;
//    ArrayAdapter<String> arrayAdapter;
//    ArrayList<String> senderlist = new ArrayList<>();
    View senderlistview;

    DatabaseReference reference;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    private ProgressDialog loadingBar;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        senderlistview =  inflater.inflate(R.layout.frag1layout,container,false);
//
//
//        Initialize();
//
//        RetrieveAndDisplay();
//
//
//        reference = FirebaseDatabase.getInstance().getReference().child("Whatsapp Senders");
//
//
//
       return senderlistview;

        recyclerView = (RecyclerView) senderlistview.findViewById(R.id.whatsapplist);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(layoutManager);



        reference = FirebaseDatabase.getInstance().getReference().child("Whatsapp Senders");


    }

    private void RetrieveAndDisplay() {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Set<String> set = new HashSet<>();
                Iterator iterator = snapshot.getChildren().iterator();

                while (iterator.hasNext()) {

                            set.add(((DataSnapshot) iterator.next()).getKey());

                }
                senderlist.clear();
                senderlist.addAll(set);
                arrayAdapter.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void Initialize() {
        listView = (ListView) senderlistview.findViewById(R.id.whatsapplist);
        arrayAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,senderlist);
        listView.setAdapter(arrayAdapter);

    }


    @Override
    public void onStart() {
        super.onStart();


        FirebaseRecyclerOptions<SenderDataStore> options =
                new FirebaseRecyclerOptions.Builder<SenderDataStore>()
                        .setQuery(reference , SenderDataStore.class)
                        .build();

        FirebaseRecyclerAdapter<SenderDataStore ,senderNameHolder> adapter =
                new FirebaseRecyclerAdapter<SenderDataStore, senderNameHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull senderNameHolder senderNameHolder, int i, @NonNull SenderDataStore senderDataStore) {
                        senderNameHolder.sender.setText(senderDataStore.getSender());
                    }



                    @NonNull
                    @Override
                    public senderNameHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

                        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.messageviewer,viewGroup,false);
                        senderNameHolder holder = new senderNameHolder(view);
                        return holder;


                    }
                };



    }
}
