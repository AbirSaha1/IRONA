package com.sandass.irona;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StatusActivity extends AppCompatActivity {

    private TextInputEditText statusTxt;
    private Button statusBtn;
    private Toolbar mtoolbar;
    private DatabaseReference mDataref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
        statusTxt=findViewById(R.id.status_text);
        statusBtn=findViewById(R.id.chng_status);
        mtoolbar= findViewById(R.id.status_toolbar);

        setSupportActionBar(mtoolbar);
        getSupportActionBar().setTitle("Change Status");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String Curr_Uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mDataref= FirebaseDatabase.getInstance().getReference().child("Users").child(Curr_Uid);


        statusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String status= statusTxt.getEditableText().toString();
                mDataref.child("Status").setValue(status).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(StatusActivity.this,"Task Successful", Toast.LENGTH_LONG ).show();

                            Intent sIntent= new Intent(StatusActivity.this, SettingsActivity.class);
                            startActivity(sIntent);
                            finish();

                        }
                        else{
                            Toast.makeText(StatusActivity.this,"Try again Later", Toast.LENGTH_LONG ).show();
                        }
                    }
                });


            }
        });
    }
}
