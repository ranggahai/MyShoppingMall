package com.example.telkomsel.myshoppingmall;

import android.view.View;
import android.widget.AdapterView;

/**
 * Created by Multimatics on 22/07/2016.
 */
public class CustomOnItemClickListener implements View.OnClickListener{
    private OnItemClickCallback onItemClickCallback;
    private int position;

    public CustomOnItemClickListener(int position, OnItemClickCallback onItemClickCallback){
        this.onItemClickCallback = onItemClickCallback;
        this.position = position;
    }

    @Override
    public void onClick(View v) {
        onItemClickCallback.onItemClicked(v, position);
    }


    public interface OnItemClickCallback {
        void onItemClicked(View v, int position);
    }
}
