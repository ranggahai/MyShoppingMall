package com.example.telkomsel.myshoppingmall;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.telkomsel.myshoppingmall.db.CartHelper;
import com.example.telkomsel.myshoppingmall.db.CartItem;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.lang.reflect.Array;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CartActivity extends AppCompatActivity {

    @BindView(R.id.lv_cart)
    ListView lvCart;
    @BindView(R.id.tv_totalqty)
    TextView tvTotalqty;
    @BindView(R.id.tv_totalpay)
    TextView tvTotalpay;
    @BindView(R.id.cart_summary)
    LinearLayout cartSummary;
    private ArrayList<CartItem> listCartItem;
    private CartHelper cartHelper;
    private CartAdapter cartAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        ButterKnife.bind(this);

        EventBus.getDefault().register(this);

        lvCart = (ListView) findViewById(R.id.lv_cart);

        getSupportActionBar().setTitle("Cart");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        cartAdapter = new CartAdapter(CartActivity.this);
        cartHelper = new CartHelper(CartActivity.this);
        listCartItem = cartHelper.getAll();

        cartSummary.setVisibility(View.GONE);

        if (listCartItem != null) {
            if (listCartItem.size() > 0) {
                cartSummary.setVisibility(View.VISIBLE);
                cartAdapter.setListItem(listCartItem);
            } else {
                cartAdapter.setListItem(new ArrayList<CartItem>());
                Toast.makeText(CartActivity.this, "Cart is empty", Toast.LENGTH_SHORT).show();
            }
        } else {
            cartAdapter.setListItem(new ArrayList<CartItem>());
            Toast.makeText(CartActivity.this, "Cart is empty", Toast.LENGTH_SHORT).show();
        }
        lvCart.setAdapter(cartAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void showTotalTransactionInfo(){
        ArrayList<CartItem> list = cartHelper.getAll();
        int totalQty = 0;
        int totalPay = 0;

        for (CartItem item: list) {
            totalQty += item.getQty();
            totalPay += (item.getQty()*item.getPrice());
        }

        if(totalQty > 0) {
            tvTotalpay.setText(String.valueOf(totalPay));
            tvTotalqty.setText(String.valueOf(totalQty));
        } else {
            cartSummary.setVisibility(View.GONE);
            Toast.makeText(CartActivity.this, "Cart is empty", Toast.LENGTH_SHORT).show();
        }
    }

    @Subscribe
    public void onEvent(RefreshCartEvent e){
        showTotalTransactionInfo();
    }

    @Override
    protected void onResume() {
        super.onResume();
        showTotalTransactionInfo();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
