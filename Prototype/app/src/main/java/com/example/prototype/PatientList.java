package com.example.prototype;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.prototype.ViewHolder.PatientViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PatientList extends AppCompatActivity {


    DatabaseReference reference;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_list);

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        reference = FirebaseDatabase.getInstance().getReference().child("Patients Information");



        loadingBar = new ProgressDialog(this);

        loadingBar.setTitle("Loading data");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

    }


    @Override
    protected void onStart() {
        super.onStart();


        FirebaseRecyclerOptions<RecieverDataStore> options =
                new FirebaseRecyclerOptions.Builder<RecieverDataStore>()
                        .setQuery(reference , RecieverDataStore.class)
                        .build();

        FirebaseRecyclerAdapter<RecieverDataStore ,PatientViewHolder> adapter =
                new FirebaseRecyclerAdapter<RecieverDataStore, PatientViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull PatientViewHolder holder, int position, @NonNull RecieverDataStore model) {
                        holder.firstnamev.setText(model.getPatient_First_Name());
                        holder.lastnamev.setText(model.getPatient_Last_Name());
                        holder.agev.setText(model.getDOB());
                        holder.genderv.setText(model.getGender());
                        holder.addressv.setText(model.getAddress());
                        holder.cityv.setText(model.getCity());
                        holder.zipv.setText(model.getZip_Code());
                        holder.phonev.setText(model.getPhone_Number());
                        holder.emailv.setText(model.geteMail());



                    }

                    @NonNull
                    @Override
                    public PatientViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

                        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.patientviewer,viewGroup,false);
                        PatientViewHolder holder = new PatientViewHolder(view);
                        return holder;


                    }
                };

        recyclerView.setAdapter(adapter);
        adapter.startListening();


        loadingBar.dismiss();

    }
}
