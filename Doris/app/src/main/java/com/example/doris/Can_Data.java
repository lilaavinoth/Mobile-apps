package com.example.doris;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.okhttp.internal.DiskLruCache;

public class Can_Data extends AppCompatActivity {

    DatabaseReference databaseReference;
    TextView abswarning,batterywarning,bootdoorwarning,distancetotalizer,driverdoorlocked,enginecollanttemp;
    TextView enginerpm,frontleftdoorwarning,frontrightdoorwarning,fuellow,highbeam,longitudinalaccelratin;
    TextView lowbeam,meaneffectivetorque,parkingbreak,rawsensor,rearleftdoorwarinig,rearrightdoorwarning;
    TextView seatbelt2rowcenter,seatbelt2rowleft,seatbelt2rowright,seatbeltdriverremainder,seatbeltfrontpassremainger;
    TextView speedwheelfrontleft,speedwheelfrontright,speedwheelrearleft,speedwheelrearright,steeringwheelangle;
    TextView steeringwheelangleoffset,steeringwheelrotationspeed,transversalacc,vehiclebatteryvoltage,vehiclespeed;
    TextView warninglights,internalbattvolt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_can__data);

        abswarning = (TextView) findViewById(R.id.abswldata);
        batterywarning = (TextView) findViewById(R.id.bwdata);
        bootdoorwarning = (TextView) findViewById(R.id.bdwdata);
        distancetotalizer = (TextView) findViewById(R.id.dtdata);
        driverdoorlocked = (TextView) findViewById(R.id.ddldata);
        enginecollanttemp = (TextView) findViewById(R.id.ectdata);
        enginerpm = (TextView) findViewById(R.id.erpmdata);
        frontleftdoorwarning = (TextView) findViewById(R.id.fldwdata);
        frontrightdoorwarning = (TextView) findViewById(R.id.frdwdata);
        fuellow = (TextView) findViewById(R.id.fldata);
        highbeam = (TextView) findViewById(R.id.hbdata);
        longitudinalaccelratin = (TextView) findViewById(R.id.ladata);
        lowbeam = (TextView) findViewById(R.id.lbdata);
        meaneffectivetorque = (TextView) findViewById(R.id.metdata);
        parkingbreak = (TextView) findViewById(R.id.pbdata);
        rawsensor = (TextView) findViewById(R.id.rsdata);
        rearleftdoorwarinig = (TextView) findViewById(R.id.rldwdata);
        rearrightdoorwarning = (TextView) findViewById(R.id.rrdwdata);
        seatbelt2rowcenter = (TextView) findViewById(R.id.srcsbsdata);
        seatbelt2rowleft = (TextView) findViewById(R.id.srlssdata);
        seatbelt2rowright = (TextView) findViewById(R.id.srrsbsdata);
        seatbeltdriverremainder = (TextView) findViewById(R.id.dsrdata);
        seatbeltfrontpassremainger = (TextView) findViewById(R.id.fpsrdata);
        speedwheelfrontleft = (TextView) findViewById(R.id.flwsdata);
        speedwheelfrontright = (TextView) findViewById(R.id.frwsdata);
        speedwheelrearleft = (TextView) findViewById(R.id.rlwsdata);
        speedwheelrearright = (TextView) findViewById(R.id.rrwsdata);
        steeringwheelangle = (TextView) findViewById(R.id.swadata);
        steeringwheelangleoffset = (TextView) findViewById(R.id.swaodata);
        steeringwheelrotationspeed = (TextView) findViewById(R.id.swrsdata);
        transversalacc = (TextView) findViewById(R.id.tadata);
        vehiclebatteryvoltage = (TextView) findViewById(R.id.vbvdata);
        vehiclespeed = (TextView) findViewById(R.id.vsdata);
        warninglights = (TextView) findViewById(R.id.wldata);
        internalbattvolt = (TextView) findViewById(R.id.ibvdata);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("CAN Data");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String q = snapshot.child("ABS_Warning_Request").getValue().toString();
                String w = snapshot.child("Battery_Warning").getValue().toString();
                String e = snapshot.child("Boot_Door_Warning").getValue().toString();
                String r = snapshot.child("Distance_Totalizer").getValue().toString();
                String t = snapshot.child("Driver_Door_Locked").getValue().toString();
                String y = snapshot.child("Engine_Coolant_Temperature").getValue().toString();
                String u = snapshot.child("Engine_RPM").getValue().toString();
                String i = snapshot.child("Front_Left_Door_Warning").getValue().toString();
                String o = snapshot.child("Front_Right_Door_Warning").getValue().toString();
                String p = snapshot.child("Fuel_Low").getValue().toString();
                String a = snapshot.child("High_Beam").getValue().toString();
                String s = snapshot.child("Longitudinal_Acceleration").getValue().toString();
                String d = snapshot.child("Low_Beam").getValue().toString();
                String h = snapshot.child("Mean_Effective_Torque").getValue().toString();
                String j = snapshot.child("Parking_Brake").getValue().toString();
                String k = snapshot.child("Raw_Sensor").getValue().toString();
                String l = snapshot.child("Rear_Left_Door_Warning").getValue().toString();
                String z = snapshot.child("Rear_Right_Door_Warning").getValue().toString();
                String x = snapshot.child("Seat_Belt_2nd_Row_Center_Status").getValue().toString();
                String c = snapshot.child("Seat_Belt_2nd_Row_Left_Status").getValue().toString();
                String v = snapshot.child("Seat_Belt_2nd_Row_Right_Status").getValue().toString();
                String b = snapshot.child("Seat_Belt_Driver_Reminder").getValue().toString();
                String n = snapshot.child("Seat_Belt_Front_Passenger_Reminder").getValue().toString();
                String m = snapshot.child("Speed_Wheel_Front_Left").getValue().toString();
                String qw = snapshot.child("Speed_Wheel_Front_Right").getValue().toString();
                String qe = snapshot.child("Speed_Wheel_Rear_Left").getValue().toString();
                String qr = snapshot.child("Speed_Wheel_Rear_Right").getValue().toString();
                String qt = snapshot.child("Steering_Wheel_Angle").getValue().toString();
                String qy = snapshot.child("Steering_Wheel_Angle_Offset").getValue().toString();
                String qu = snapshot.child("Steering_Wheel_Rotation_Speed").getValue().toString();
                String qi = snapshot.child("Transversal_Acceleration").getValue().toString();
                String qo = snapshot.child("VehicleBatteryVoltage").getValue().toString();
                String qp = snapshot.child("Vehicle_Speed").getValue().toString();
                String qa = snapshot.child("Warning_Lights").getValue().toString();
                String qs = snapshot.child("internalbattvolts").getValue().toString();


                abswarning.setText(q);
                batterywarning.setText(w);
                bootdoorwarning.setText(e);
                distancetotalizer.setText(r);
                driverdoorlocked.setText(t);
                enginecollanttemp.setText(y);
                enginerpm.setText(u);
                frontleftdoorwarning.setText(i);
                frontrightdoorwarning.setText(o);
                fuellow.setText(p);
                highbeam.setText(a);
                longitudinalaccelratin.setText(s);
                lowbeam.setText(d);
                meaneffectivetorque.setText(h);
                parkingbreak.setText(j);
                rawsensor.setText(k);
                rearleftdoorwarinig.setText(l);
                rearrightdoorwarning.setText(z);
                seatbelt2rowcenter.setText(x);
                seatbelt2rowleft.setText(c);
                seatbelt2rowright.setText(v);
                seatbeltdriverremainder.setText(b);
                seatbeltfrontpassremainger.setText(n);
                speedwheelfrontleft.setText(m);
                speedwheelfrontright.setText(qw);
                speedwheelrearleft.setText(qe);
                speedwheelrearright.setText(qr);
                speedwheelrearright.setText(qt);
                steeringwheelangleoffset.setText(qy);
                steeringwheelrotationspeed.setText(qu);
                transversalacc.setText(qi);
                vehiclebatteryvoltage.setText(qo);
                vehiclespeed.setText(qp);
                warninglights.setText(qa);
                internalbattvolt.setText(qs);



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}