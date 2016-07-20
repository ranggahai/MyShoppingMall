package com.example.telkomsel.myshoppingmall;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextView tvreg = (TextView)findViewById(R.id.tv_register);
        tvreg.setOnClickListener(this);
        Button butLogin = (Button)findViewById(R.id.but_login);
        butLogin.setOnClickListener(this);

        getSupportActionBar().setTitle("Login");
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch(v.getId()){
            case R.id.tv_register:
                intent = new Intent(LoginActivity.this, RegisterActivity.class);
                break;
            case R.id.but_login:
                intent = new Intent(LoginActivity.this, MainActivity.class);
                break;
        }
        if(intent != null) {
            startActivity(intent);
        }
    }
}
