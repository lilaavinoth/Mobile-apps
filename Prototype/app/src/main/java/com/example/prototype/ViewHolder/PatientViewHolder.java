package com.example.prototype.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.prototype.R;

public class PatientViewHolder extends RecyclerView.ViewHolder {


    public TextView firstnamev,lastnamev,agev,genderv,addressv,cityv,zipv,phonev,emailv;


    public PatientViewHolder(@NonNull View itemView) {
        super(itemView);


        firstnamev = (TextView) itemView.findViewById(R.id.firstnamev);
        lastnamev = (TextView) itemView.findViewById(R.id.lastnamev);
        agev = (TextView) itemView.findViewById(R.id.agev);
        genderv = (TextView) itemView.findViewById(R.id.genderv);
        addressv = (TextView) itemView.findViewById(R.id.addressv);
        cityv = (TextView) itemView.findViewById(R.id.cityv);
        zipv = (TextView) itemView.findViewById(R.id.zipv);
        phonev = (TextView) itemView.findViewById(R.id.phonev);
        emailv = (TextView) itemView.findViewById(R.id.emailv);



    }


}
