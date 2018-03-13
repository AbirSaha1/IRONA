package com.sandass.irona;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
    private TextInputEditText name;
    private TextInputEditText email;
    private TextInputEditText password;
    private Button regBtn;
    private FirebaseAuth mAuth;
    private Toolbar regToolbar;
    private ProgressDialog mprogbar;
    private DatabaseReference mdatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name = findViewById(R.id.reg_name);
        email = findViewById(R.id.reg_email);
        password = findViewById(R.id.reg_pass);
        regBtn = findViewById(R.id.regbtn);
        regToolbar= findViewById(R.id.reg_toolbar);
        mprogbar= new ProgressDialog(this);


        setSupportActionBar(regToolbar);
        getSupportActionBar().setTitle("Create Account");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mAuth = FirebaseAuth.getInstance();
        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String dispname= name.getEditableText().toString();
                String mail= email.getEditableText().toString();
                String pass=password.getEditableText().toString();

                if(TextUtils.isEmpty(dispname) || TextUtils.isEmpty(mail) || TextUtils.isEmpty(pass)){
                    Toast.makeText(RegisterActivity.this,"Fields cannot be empty", Toast.LENGTH_LONG).show();
                }

                else{
                    mprogbar.setTitle("Registering User");
                    mprogbar.setMessage("Please wait while registering");
                    mprogbar.setCanceledOnTouchOutside(false);
                    mprogbar.show();
                    reg_user(dispname, mail,pass);


                }



            }
        });
    }

    private void reg_user(final String dispname, String mail, String pass) {
        mAuth.createUserWithEmailAndPassword(mail,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){



                    //Database Stuff
                    FirebaseUser current_user=FirebaseAuth.getInstance().getCurrentUser();
                    String uid= current_user.getUid();

                    mdatabase=FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
                    HashMap<String, String> user_map= new HashMap<>();

                    user_map.put("Name", dispname);
                    user_map.put("Status", "I Love IRONA");
                    user_map.put("image", "Default");
                    user_map.put("thumb_img", "Default");
                    mdatabase.setValue(user_map);



                    //Progress bar
                    mprogbar.dismiss();


                    //Login Stuff
                    Intent mainIntent= new Intent(RegisterActivity.this,MainActivity.class);
                    mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(mainIntent);
                    finish();
                }
                else{
                    mprogbar.hide();
                    Toast.makeText(RegisterActivity.this, "There are some error", Toast.LENGTH_LONG).show();
                }

            }
        });


    }
}
