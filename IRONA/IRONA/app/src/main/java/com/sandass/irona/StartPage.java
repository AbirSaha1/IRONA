package com.sandass.irona;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class StartPage extends AppCompatActivity {
    private Button mRegBtn;
    private Button mloginbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_page);

        mRegBtn = findViewById(R.id.start_reg_button);
        mloginbtn=findViewById(R.id.login_btn);

        mRegBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent RegIntent = new Intent(StartPage.this, RegisterActivity.class);
                startActivity(RegIntent);
                finish();

            }
        });

        mloginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginIntent = new Intent(StartPage.this, LoginActivity.class);
                startActivity(loginIntent);
                finish();

            }
        });
    }
}









