package com.rai.vivek.chatappfirebase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class StartActivity extends AppCompatActivity  {


    private Button mRegbtn,mloginbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        mRegbtn=findViewById(R.id.start_regbtn);
        mRegbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
           Intent reg_intent=new Intent(StartActivity.this,RegistrationActivity.class);
           startActivity(reg_intent);
            }
        });

        mloginbtn=findViewById(R.id.start_loginbtn);
        mloginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login_intent=new Intent(StartActivity.this,LoginActivity.class);
                startActivity(login_intent);


            }
        });

    }




}
