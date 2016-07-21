package com.example.telkomsel.myshoppingmall;

import android.app.ProgressDialog;
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

import com.example.telkomsel.myshoppingmall.api.request.PostLoginRequest;
import com.example.telkomsel.myshoppingmall.api.response.User;
import com.loopj.android.http.RequestParams;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener,
        PostLoginRequest.OnPostLoginRequestListener{

    private TextView tvreg;
    private Button butLogin;
    private AppPreferences appPreferences;
    private EditText etUsername, etPass;

    private ProgressDialog progressDialog;
    private PostLoginRequest postLoginRequest;

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

        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setTitle("Login");
        progressDialog.setMessage("Please wait...");

        postLoginRequest = new PostLoginRequest();
        postLoginRequest.setOnPostLoginRequestListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        //boolean isLogin = false;
        switch(v.getId()){
            case R.id.tv_register:
                intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.but_login:
                String username = etUsername.getText().toString().trim();
                String password = etPass.getText().toString().trim();

                if(TextUtils.isEmpty(username)||TextUtils.isEmpty(password)){
                    Toast.makeText(LoginActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
                } else {
                    RequestParams requestParams = new RequestParams();
                    requestParams.put("username",username);
                    requestParams.put("password",password);
                    postLoginRequest.setRequestParams(requestParams);
                    progressDialog.show();
                    postLoginRequest.callApi();
                    //intent = new Intent(LoginActivity.this, HomeActivity.class);
                    //isLogin = true;
                }

                break;
        }
        /*if(intent != null) {
            startActivity(intent);
            if(isLogin){
                finish();
            }
        }*/
    }


    @Override
    public void onPostLoginSuccess(User user) {
        progressDialog.cancel();
        appPreferences.setUserid(user.getUserid());

        Toast.makeText(LoginActivity.this, user.getMessage(), Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onPostLoginFailure(String errorMessage) {
        progressDialog.cancel();

        Toast.makeText(LoginActivity.this, errorMessage , Toast.LENGTH_SHORT).show();
    }
}
