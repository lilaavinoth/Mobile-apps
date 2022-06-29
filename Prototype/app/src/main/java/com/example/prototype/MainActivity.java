package com.example.prototype;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    EditText patientnamefirst,patientnamelast,DOB,address,zipCode,phonenumber,email;
    Spinner gender,city;
    Button uploadbtn,patientdetails;
    DatabaseReference reference;
    private ProgressDialog loadingBar;
    TextView printqrcode;

    String genderinput,cityinput,dateinput,patientnamefirststring,patientnamelaststring,addresssring,emailstring,phonenumberstring,zipcodestring,saveCurrentDate,saveCurrentTime,PatientRandomKey;


    String genderlist[] = {"Male", "Female"};
    ArrayAdapter<String>arrayAdapter;

    String citylist[] = {"Trichy","Madurai","Chennai"};
    ArrayAdapter<String>arrayAdapter1;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        patientnamefirst = (EditText)findViewById(R.id.patientnamefirst);
        patientnamelast = (EditText)findViewById(R.id.patientnamelast);
        address = (EditText)findViewById(R.id.address);
        zipCode = (EditText)findViewById(R.id.zipCode);
        phonenumber = (EditText)findViewById(R.id.phonenumber);
        email = (EditText)findViewById(R.id.email);
        uploadbtn = (Button) findViewById(R.id.uploadbtn);
        patientdetails = (Button) findViewById(R.id.patientdetails);
        loadingBar = new ProgressDialog(this);
        printqrcode = (TextView) findViewById(R.id.printqrcode);


        gender = (Spinner)findViewById(R.id.gender);
        arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,genderlist);
        gender.setAdapter(arrayAdapter);



        printqrcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,QRcode.class);
                startActivity(intent);
            }
        });



        gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                genderinput = genderlist[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });






        city = (Spinner)findViewById(R.id.city);
        arrayAdapter1 = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,citylist);
        city.setAdapter(arrayAdapter1);

        city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                cityinput = citylist[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });




        DOB = (EditText)findViewById(R.id.DOB);
        DOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datePicker = new DataPickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });


        reference = FirebaseDatabase.getInstance().getReference().child("Patients Information");

        uploadbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                upload();
            }
        });



        printqrcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,QRcode.class);
                intent.putExtra("uniquedata",PatientRandomKey);
                startActivity(intent);
            }
        });


        patientdetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,PatientList.class);
                startActivity(intent);
            }
        });

    }



    private void upload()
    {

        patientnamefirststring = patientnamefirst.getText().toString();
        patientnamelaststring = patientnamelast.getText().toString();
        addresssring = address.getText().toString();
        zipcodestring = zipCode.getText().toString();
        phonenumberstring = phonenumber.getText().toString();
        emailstring = email.getText().toString();



        if (TextUtils.isEmpty(patientnamefirststring))
        {
            Toast.makeText(this, "First Name Required", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(dateinput))
        {
            Toast.makeText(this, "Date of Birth Required", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(genderinput))
        {
            Toast.makeText(this, "Gender Required", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(cityinput))
        {
            Toast.makeText(this, "City Name Required", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(zipcodestring))
        {
            Toast.makeText(this, "Zip Code Required", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(phonenumberstring))
        {
            Toast.makeText(this, "Phone Number Required", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(emailstring))
        {
            Toast.makeText(this, "Email Required", Toast.LENGTH_SHORT).show();
        }
        else
        {
            regesterpatient();
        }




    }

    private void regesterpatient()
    {

        loadingBar.setMessage("New Patient Details are Being Added");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();


        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        PatientRandomKey = saveCurrentDate + saveCurrentTime;



        SavePatientDataToDB();

    }


    private void SavePatientDataToDB()
    {

        HashMap<String,Object> patientmap = new HashMap<>();
        patientmap.put("Patient_First_Name",patientnamefirststring);
        patientmap.put("Patient_Last_Name",patientnamelaststring);
        patientmap.put("DOB",dateinput);
        patientmap.put("Gender",genderinput);
        patientmap.put("Address",addresssring);
        patientmap.put("City",cityinput);
        patientmap.put("Zip_Code",zipcodestring);
        patientmap.put("Phone_Number",phonenumberstring);
        patientmap.put("eMail",emailstring);




        reference.child(PatientRandomKey).updateChildren(patientmap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful())
                        {
                            Toast.makeText(MainActivity.this, "Patient Details Uploaded Succesfully", Toast.LENGTH_SHORT).show();

                        }
                        else
                        {
                            Toast.makeText(MainActivity.this, "Unsucessful", Toast.LENGTH_SHORT).show();

                        }
                        loadingBar.dismiss();





                    }
                });



    }





    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year );
        calendar.set(Calendar.MONTH, month );
        calendar.set(Calendar.DAY_OF_MONTH, day );

        dateinput = DateFormat.getDateInstance().format(calendar.getTime());

        DOB.setText(dateinput);


    }

}
