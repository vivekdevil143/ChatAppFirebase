package com.rai.vivek.chatappfirebase;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
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

public class LoginActivity extends AppCompatActivity {

    private TextInputLayout mLoginEmail;
    private TextInputLayout mLoginPassword;
    private Toolbar mtoolbar;
    private Button mloginbtn;
    private FirebaseAuth firebaseAuth;

    //ProgressDialog
    private ProgressDialog mloginProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();

        mtoolbar = findViewById(R.id.login_toolbar);
        setSupportActionBar(mtoolbar);
        getSupportActionBar().setTitle("Login");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mLoginEmail = findViewById(R.id.login_email);
        mLoginPassword = findViewById(R.id.login_password);
        mloginbtn = findViewById(R.id.login_btn);

        mloginProgress = new ProgressDialog(this);

        mloginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Email = mLoginEmail.getEditText().getText().toString().trim();
                String Password = mLoginPassword.getEditText().getText().toString().trim();

                if (Email.isEmpty()) {
                    mLoginEmail.setError("Email is required");
                    mLoginEmail.requestFocus();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
                    mLoginEmail.setError("Please Enter Valid Email");
                    mLoginEmail.requestFocus();
                    return;
                }
                if (Password.isEmpty()) {
                    mLoginPassword.setError("Password is required");
                    mLoginPassword.requestFocus();
                    return;
                }
                if (Password.length() < 6) {
                    mLoginPassword.setError("Minimum length of password should be 6");
                    mLoginPassword.requestFocus();
                    return;
                }
                mloginProgress.setTitle("Logging In...");
                mloginProgress.setMessage("Please wait while we check your credentials");
                mloginProgress.setCanceledOnTouchOutside(false);
                mloginProgress.show();

                loginUser(Email, Password);

            }
        });


    }

    private void loginUser(String Email, String Password) {

        firebaseAuth.signInWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {

                    mloginProgress.dismiss();
                    Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
                    mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(mainIntent);
                    finish();

                } else {
                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                        Toast.makeText(getApplicationContext(), " Login Failed!Please Try Again ", Toast.LENGTH_SHORT).show();
                    } else {
                        mloginProgress.hide();
                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });

    }
}
