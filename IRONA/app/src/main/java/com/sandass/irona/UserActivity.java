package com.sandass.irona;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class UserActivity extends AppCompatActivity {

    private Toolbar musrToolbar;
    private RecyclerView musrview;
    private DatabaseReference muserData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        musrToolbar = findViewById(R.id.user_toolbar);
        musrview = findViewById(R.id.user_rec_view);
        muserData = FirebaseDatabase.getInstance().getReference().child("Users");

        setSupportActionBar(musrToolbar);
        getSupportActionBar().setTitle("All User");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        musrview.hasFixedSize();
        musrview.setLayoutManager(new LinearLayoutManager(this));
    }
}


       /* Query query = FirebaseDatabase.getInstance().getReference();
        FirebaseListOptions<users> options = new FirebaseListOptions
                .Builder<users>()
                .setLayout(R.layout.users_single)
                .setQuery(query, users.class) .build();
        }
    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<Userviewholder, users> firebaseRecyclerAdapter=
                new FirebaseRecyclerAdapter<Userviewholder, users>(
                        users.class,
                         R.layout.users_single,
                        Userviewholder.class,
                        muserData

                ){
                    @Override
                    public void populateViewHolder(Userviewholder userviewholder, users users, int i){

                    }

                };
                */







