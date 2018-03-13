package com.sandass.irona;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private TextInputEditText email;
    private TextInputEditText pass;
    private Button loginbtn;
    private android.support.v7.widget.Toolbar logintoolbar;
    private ProgressDialog loginbar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        email = findViewById(R.id.login_mail);
        pass = findViewById(R.id.login_pass);
        loginbtn =findViewById(R.id.signin_btn);
        logintoolbar =findViewById(R.id.login_toolbar);

        loginbar = new ProgressDialog(this);

        setSupportActionBar(logintoolbar);
        getSupportActionBar().setTitle("Create Account");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail = email.getEditableText().toString();
                String passwd = pass.getEditableText().toString();

                if (TextUtils.isEmpty(mail) || TextUtils.isEmpty(passwd)) {
                    Toast.makeText(LoginActivity.this, "Fields cannot be empty", Toast.LENGTH_LONG).show();
                } else {
                    loginbar.setTitle("Registering User");
                    loginbar.setMessage("Please wait while registering");
                    loginbar.setCanceledOnTouchOutside(false);
                    loginbar.show();
                    lgin_user(mail, passwd);
                }

            }
        });}


    private void lgin_user(String mail, String passwd) {
        mAuth.signInWithEmailAndPassword(mail, passwd).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {

                            loginbar.dismiss();

                            Intent mainIntent= new Intent(LoginActivity.this,MainActivity.class);
                            mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(mainIntent);
                            finish();
                        }
                else{
                            loginbar.hide();
                            Toast.makeText(LoginActivity.this, "There are some error", Toast.LENGTH_LONG).show();
                        }

                    }
                });
    }
}
