package com.example.telkomsel.myshoppingmall;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.telkomsel.myshoppingmall.api.request.PostRegisterRequest;
import com.example.telkomsel.myshoppingmall.api.response.BaseResponse;
import com.loopj.android.http.RequestParams;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends AppCompatActivity implements
        PostRegisterRequest.OnPostRegisterRequestListener, View.OnClickListener {


    @BindView(R.id.et_name_reg)
    EditText etNameReg;
    @BindView(R.id.et_pass_reg)
    EditText etPassReg;
    @BindView(R.id.et_email_reg)
    EditText etEmailReg;
    @BindView(R.id.cb_1)
    CheckBox cb1;
    @BindView(R.id.but_register)
    Button butRegister;

    private ProgressDialog progressDialog;
    private PostRegisterRequest postRegisterRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        getSupportActionBar().setTitle("REGISTER");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progressDialog = new ProgressDialog(RegisterActivity.this);
        progressDialog.setTitle("Register");
        progressDialog.setMessage("Please wait...");

        postRegisterRequest = new PostRegisterRequest();
        postRegisterRequest.setOnPostRegisterRequestListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPostRegisterSuccess(BaseResponse response) {
        progressDialog.cancel();

        Toast.makeText(RegisterActivity.this, response.getMessage(), Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onPostRegisterFailure(String errorMessage) {
        progressDialog.cancel();

        Toast.makeText(RegisterActivity.this, errorMessage , Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {

    }

    @OnClick(R.id.but_register)
    public void onClick() {
        String username = etNameReg.getText().toString().trim();
        String password = etPassReg.getText().toString().trim();
        String email = etEmailReg.getText().toString().trim();

        if(TextUtils.isEmpty(username)||TextUtils.isEmpty(password)||TextUtils.isEmpty(email)){
            Toast.makeText(RegisterActivity.this,"All fields are required", Toast.LENGTH_SHORT).show();
        } else {
            RequestParams requestParams = new RequestParams();
            requestParams.put("username",username);
            requestParams.put("password",password);
            requestParams.put("email",email);

            postRegisterRequest.setRequestParams(requestParams);
            progressDialog.show();
            postRegisterRequest.callApi();
        }
    }

    @Override
    protected void onDestroy() {
        postRegisterRequest.cancelRequest();
        super.onDestroy();
    }
}
