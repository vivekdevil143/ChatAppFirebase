package com.rai.vivek.chatappfirebase;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegistrationActivity extends AppCompatActivity {


    private TextInputLayout mDisplayname, mEmail, mPassword;
    private Button mcreatebtn;
    private FirebaseAuth mAuth;
    private Toolbar mtoolbar;

    //ProgressDialog
    private ProgressDialog mRegProgress;

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        //Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        mDisplayname = findViewById(R.id.reg_display_name);
        mEmail = findViewById(R.id.reg_email);
        mPassword = findViewById(R.id.reg_password);
        mcreatebtn = findViewById(R.id.reg_create_btn);


        mtoolbar = findViewById(R.id.register_toolbar);
        setSupportActionBar(mtoolbar);
        getSupportActionBar().setTitle("Create Account");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mRegProgress = new ProgressDialog(this);


        mcreatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Displayname = mDisplayname.getEditText().getText().toString().trim();
                String Email = mEmail.getEditText().getText().toString().trim();
                String Password = mPassword.getEditText().getText().toString().trim();

                if (Displayname.isEmpty()) {
                    mDisplayname.setError("Display Name is required");
                    mDisplayname.requestFocus();
                    return;
                }

                if (Email.isEmpty()) {
                    mEmail.setError("Email is required");
                    mEmail.requestFocus();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
                    mEmail.setError("Please Enter Valid Email");
                    mEmail.requestFocus();
                    return;
                }
                if (Password.isEmpty()) {
                    mPassword.setError("Password is required");
                    mPassword.requestFocus();
                    return;
                }
                if (Password.length() < 6) {
                    mPassword.setError("Minimum length of password should be 6");
                    mPassword.requestFocus();
                    return;
                }
                mRegProgress.setTitle("Registrating User...");
                mRegProgress.setMessage("Please wait while we create your account");
                mRegProgress.setCanceledOnTouchOutside(false);
                mRegProgress.show();

                register_user(Displayname,Email, Password);

            }
        });
    }

    private void register_user(final String Displayname, String Email, String Password) {

        mAuth.createUserWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {


                    FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();

                    String uid = current_user.getUid();
                    mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);

                    HashMap<String,String> usermap=new HashMap<>();
                    usermap.put("name",Displayname);
                    usermap.put("status","Hi there i am using Chat App");
                    usermap.put("image","default");
                    usermap.put("thumb_image","default");

                    mDatabase.setValue(usermap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                mRegProgress.dismiss();
                                Intent MainIntent = new Intent(RegistrationActivity.this, MainActivity.class);
                                MainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(MainIntent);
                                finish();
                            }
                        }
                    });
                } else {
                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                        Toast.makeText(getApplicationContext(), "Email Address already Registered", Toast.LENGTH_SHORT).show();
                    } else {
                        mRegProgress.hide();
                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });

    }
}
