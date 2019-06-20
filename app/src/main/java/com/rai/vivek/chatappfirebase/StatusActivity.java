package com.rai.vivek.chatappfirebase;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class StatusActivity extends AppCompatActivity {

    private Toolbar mtoolbar;

    private TextInputLayout txtChangeStatus;
    private Button btnChangeStatus;

    //Firebase
    private DatabaseReference mStatusDatabase;
    private FirebaseUser mCurrentUser;

    //ProgressDialog
    private ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        //Firebase
        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();


        String current_uid = mCurrentUser.getUid();


        mStatusDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(current_uid);

        mtoolbar = findViewById(R.id.status_bar);
        setSupportActionBar(mtoolbar);
        getSupportActionBar().setTitle("Account Status");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        String status_value = getIntent().getStringExtra("status_value");
        txtChangeStatus = (TextInputLayout) findViewById(R.id.txtstatus);
        btnChangeStatus = (Button) findViewById(R.id.btn_ChangeStatus);

        txtChangeStatus.getEditText().setText(status_value);
        btnChangeStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgress = new ProgressDialog(StatusActivity.this);
                mProgress.setTitle("Saving Changes");
                mProgress.setMessage("Please wait while we save the changes");
                mProgress.show();
                mProgress.setCancelable(false);
                String status = txtChangeStatus.getEditText().getText().toString();
                mStatusDatabase.child("status").setValue(status).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            mProgress.dismiss();
                            Toast.makeText(StatusActivity.this, "Status Change Successfully", Toast.LENGTH_LONG).show();

                        } else {
                            Toast.makeText(StatusActivity.this, "There is some error in saving Changes", Toast.LENGTH_LONG).show();
                        }
                    }
                });

                //finish();

            }
        });


    }
}
