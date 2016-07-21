package com.example.telkomsel.myshoppingmall;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Multimatics on 20/07/2016.
 */
public class AppPreferences {
    private Context context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private String PRFS_NAME = "Dota2Mall";
    private String KEY_USERID = "USERID";

    public AppPreferences(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PRFS_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void setUserid(String u){
        editor.putString(KEY_USERID,u);
        editor.commit();
    }

    public String getUserid(){
        return sharedPreferences.getString(KEY_USERID,"");
    }



    public void clear(){
        editor.clear();
        editor.commit();
    }
}
