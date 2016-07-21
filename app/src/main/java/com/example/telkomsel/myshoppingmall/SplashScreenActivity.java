package com.example.telkomsel.myshoppingmall;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.widget.Space;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashScreenActivity extends AppCompatActivity {

    private AppPreferences appPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        getSupportActionBar().hide();
        appPreferences = new AppPreferences(SplashScreenActivity.this);

        new DelayAsync().execute();
    }

    class DelayAsync extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... params) {
            try{
                Thread.sleep(3000);
            } catch (Exception e) {

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            Intent intent = null;
            if(appPreferences.getUserid().equals("")){
                intent = new Intent(SplashScreenActivity.this,LoginActivity.class);
            } else {
                intent = new Intent(SplashScreenActivity.this,HomeActivity.class);
            }
            startActivity(intent);
            finish();
        }
    }
}
