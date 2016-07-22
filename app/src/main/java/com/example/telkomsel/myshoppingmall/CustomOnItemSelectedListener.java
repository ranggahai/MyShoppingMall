package com.example.telkomsel.myshoppingmall;

import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

/**
 * Created by Multimatics on 22/07/2016.
 */
public class CustomOnItemSelectedListener implements AdapterView.OnItemSelectedListener {
    private OnItemSelectedCallback onItemSelectedCallback;
    private TextView tv_subtotal;
    private int cartRowPosition;

    public CustomOnItemSelectedListener(int cartRowPosition, OnItemSelectedCallback onItemSelectedCallback, TextView tv_subtotal){
        this.cartRowPosition = cartRowPosition;
        this.onItemSelectedCallback = onItemSelectedCallback;
        this.tv_subtotal = tv_subtotal;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        onItemSelectedCallback.onItemSelected(view,tv_subtotal, this.cartRowPosition, position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public interface OnItemSelectedCallback {
        void onItemSelected(View v, TextView tv_subtotal, int cartRowPos, int spnArrayPos);
    }
}
