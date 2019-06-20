package com.rai.vivek.chatappfirebase;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private Toolbar mtoolbar;
    private ViewPager mViewPager;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private TabLayout mtablayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        mtoolbar = (Toolbar) findViewById(R.id.toolbar_layout);
        setSupportActionBar(mtoolbar);
        getSupportActionBar().setTitle("Chat App");


        //Tabs
        mViewPager = (ViewPager) findViewById(R.id.Viewpager);
        mSectionsPagerAdapter=new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mSectionsPagerAdapter);

        mtablayout=(TabLayout)findViewById(R.id.main_tabs);
        mtablayout.setupWithViewPager(mViewPager);

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser == null) {
            sendTostart();
        }
    }

    private void sendTostart() {
        Intent startIntenet = new Intent(MainActivity.this, StartActivity.class);
        startActivity(startIntenet);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        if (item.getItemId() == R.id.main_logout_btn) {
            FirebaseAuth.getInstance().signOut();
            sendTostart();

        }

        if (item.getItemId()==R.id.account_setting_btn){
            Intent settingIntent=new Intent(this,SettingActivity.class);
            startActivity(settingIntent);

        }
        if (item.getItemId()==R.id.all_users_btn){
            Intent alluser=new Intent(this,UsersActivity.class);
            startActivity(alluser);

        }


        return true;
    }


}