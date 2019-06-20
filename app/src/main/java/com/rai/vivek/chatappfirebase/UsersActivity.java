package com.rai.vivek.chatappfirebase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UsersActivity extends AppCompatActivity {

    private static final String TAG = UsersActivity.class.getSimpleName();
    private Toolbar mtoolbar;
    private RecyclerView mUsers_RecyclerView;
    private DatabaseReference mUserDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        mtoolbar = findViewById(R.id.users_appbar);
        setSupportActionBar(mtoolbar);
        getSupportActionBar().setTitle("All User");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mUsers_RecyclerView = findViewById(R.id.Users_recyclerView);
        //  mUsers_RecyclerView.setHasFixedSize(true);
        mUsers_RecyclerView.setLayoutManager(new LinearLayoutManager(this));


        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users");


    }


    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<Users_modelclass, UsersAdapter> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Users_modelclass, UsersAdapter>(
                Users_modelclass.class,
                R.layout.allusers_layout,
                UsersAdapter.class,
                mUserDatabase) {

            @Override
            protected void populateViewHolder(UsersAdapter viewHolder, Users_modelclass model, int position) {

                viewHolder.setName(model.getName());
                viewHolder.setstatus(model.getStatus());
                viewHolder.setthumbUser_Image(model.getImage(), getApplicationContext());
                Log.e(TAG, "populateViewHolder: Thumb_image....." + model.getName());

                final String User_id = getRef(position).getKey();

               viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent profileIntent = new Intent(UsersActivity.this, ProfileActivity.class);
                        profileIntent.putExtra("user_id",User_id);
                        startActivity(profileIntent);

                    }
                });
            }
        };

        mUsers_RecyclerView.setAdapter(firebaseRecyclerAdapter);

    }


}
