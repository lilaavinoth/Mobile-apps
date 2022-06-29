package com.example.testing2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Trustedlist extends AppCompatActivity {

    EditText enterphone,name;
    Button saver,view;
    ListView listView;
    Switch locationswitch;

    ArrayAdapter personarrayadapter;
    DatabaseHelper databaseHelper;

    DatabaseReference databaseReference;

    private static final String TAG = "ListDataActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trustedlist);

        enterphone = findViewById(R.id.enteraphone);
        name = findViewById(R.id.name);
        listView = findViewById(R.id.listofall);
        saver = findViewById(R.id.savebtn);
        view = findViewById(R.id.view);
        locationswitch = findViewById(R.id.allowtrack);

        databaseHelper = new DatabaseHelper(this);

//        ShowPeopleOnListView(databaseHelper);

        saver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String newphone = enterphone.getText().toString();
                String newname = name.getText().toString();
                boolean state = locationswitch.isChecked();

                
                if (newphone.length() != 0) {
                    String newphone2 = "+91"+newphone;
                    Query userchecker = FirebaseDatabase.getInstance().getReference("Google Sign-in Users").orderByChild("Phone").equalTo(newphone2);
                    userchecker.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists())
                            {
                                String providedName = snapshot.child(newphone2).child("Name").getValue(String.class);
                                name.setText(providedName);
//                                AddaData(newphone2,newname,state);
                                AddaData(newphone2,providedName,state);
                            }
                            else
                            {
                                Toast.makeText(Trustedlist.this, "User not found in Server", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                } else {
                    Toast.makeText(Trustedlist.this, "You must put something in the text field!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor data = databaseHelper.getData();
                ArrayList<String> listData = new ArrayList<>();
                while(data.moveToNext()){
                    //get the value from the database in column 1
                    //then add it to the ArrayList
                    listData.add(data.getString(1));
                }

                ListAdapter adapter = new ArrayAdapter<>(Trustedlist.this, android.R.layout.simple_list_item_1, listData);
                listView.setAdapter(adapter);

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String name = adapterView.getItemAtPosition(i).toString();
                Log.d(TAG, "onItemClick: You Clicked on " + name);

                Cursor data = databaseHelper.getItemID(name); //get the id associated with that name
                int itemID = -1;
                while(data.moveToNext()){
                    itemID = data.getInt(0);
                }
                if(itemID > -1){
                    Log.d(TAG, "onItemClick: The ID is: " + itemID);
                    Intent editScreenIntent = new Intent(Trustedlist.this, EditDataActivity.class);
                    editScreenIntent.putExtra("id",itemID);
                    editScreenIntent.putExtra("name",name);
                    startActivity(editScreenIntent);
                }
                else{
                    Toast.makeText(Trustedlist.this, "No ID associated with that name", Toast.LENGTH_SHORT).show();
                }
            }

        });

    }

    private void AddaData(String newphone, String newname, boolean state) {

        boolean insertdata = databaseHelper.addOne(newphone,newname,state);

        if (insertdata) {

            Toast.makeText(Trustedlist.this, "Data Successfully Inserted!", Toast.LENGTH_SHORT).show();

        } else {

            Toast.makeText(Trustedlist.this, "Something went wrong", Toast.LENGTH_SHORT).show();
        }
    }

//    private void ShowPeopleOnListView(DatabaseHelper databaseHelper2) {
//        personarrayadapter = new ArrayAdapter<>(Trustedlist.this, android.R.layout.simple_list_item_1, databaseHelper2.getEveryone());
//        listView.setAdapter(personarrayadapter);
//    }
}