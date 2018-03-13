package com.sandass.irona;

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
    private ViewPager mainPager;
    private SectionPagerAdapter mSectionPagerAdapter;
    private TabLayout maintab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        mtoolbar= findViewById(R.id.main_page_toolbar);
        maintab=findViewById(R.id.main_tab);

        //toolbar Action
        setSupportActionBar(mtoolbar);
        getSupportActionBar().setTitle("IRONA");

        //tabs
        mainPager=findViewById(R.id.main_pager);
        mSectionPagerAdapter= new SectionPagerAdapter(getSupportFragmentManager());

        mainPager.setAdapter(mSectionPagerAdapter);
        maintab.setupWithViewPager(mainPager);


    }







    //If not signed in
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.

       sentToStart();


    }

    //Method
    public void sentToStart() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            Intent startIntent = new Intent(MainActivity.this, StartPage.class);
            startActivity(startIntent);
            finish();
        }

    }

    //Menu_Create

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    //Menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if(item.getItemId() == R.id.main_logout){

            FirebaseAuth.getInstance().signOut();
            sentToStart();
        }
        if(item.getItemId() == R.id.settings_account){

            Intent accIntent= new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(accIntent);
        }
        return true;
    }
}
