package com.example.telkomsel.myshoppingmall;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvreg;
    private Button butLogin;
    private AppPreferences appPreferences;
    private EditText etUsername, etPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tvreg = (TextView)findViewById(R.id.tv_register);
        tvreg.setOnClickListener(this);
        butLogin = (Button)findViewById(R.id.but_login);
        butLogin.setOnClickListener(this);
        etUsername = (EditText)findViewById(R.id.et_name);
        etPass = (EditText)findViewById(R.id.et_pass);

        appPreferences = new AppPreferences(LoginActivity.this);
        getSupportActionBar().setTitle("Login");
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        boolean isLogin = false;
        switch(v.getId()){
            case R.id.tv_register:
                intent = new Intent(LoginActivity.this, RegisterActivity.class);
                break;
            case R.id.but_login:
                String username = etUsername.getText().toString().trim();
                String password = etPass.getText().toString().trim();

                if(TextUtils.isEmpty(username)||TextUtils.isEmpty(password)){
                    Toast.makeText(LoginActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
                } else {
                    appPreferences.setUsername(username);
                    intent = new Intent(LoginActivity.this, HomeActivity.class);
                    isLogin = true;
                }

                break;
        }
        if(intent != null) {
            startActivity(intent);
            if(isLogin){
                finish();
            }
        }
    }
}
