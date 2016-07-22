package com.example.telkomsel.myshoppingmall;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.telkomsel.myshoppingmall.api.request.GetAllProductRequest;
import com.example.telkomsel.myshoppingmall.db.CartHelper;
import com.example.telkomsel.myshoppingmall.db.CartItem;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        GetAllProductRequest.OnGetAllProductRequestListener,
        AdapterView.OnItemClickListener, View.OnClickListener {

    private ListView lvItem;
    private ProgressBar progressBar;

    private ProdukAdapter produkAdapter;
    private GetAllProductRequest getAllProductRequest;
    private ArrayList<Produk> listItem;

    private TextView tvTitle, tvCart;
    private ImageView imgCart;
    private CartHelper cartHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_toolbar);
        setSupportActionBar(toolbar);

        lvItem = (ListView)findViewById(R.id.lv_item);
        progressBar = (ProgressBar)findViewById(R.id.progress_bar);
        tvTitle = (TextView)findViewById(R.id.Title);
        tvCart = (TextView)findViewById(R.id.tv_cart);
        imgCart = (ImageView)findViewById(R.id.img_cart);
        cartHelper = new CartHelper(this);

        imgCart.setOnClickListener(this);

        produkAdapter = new ProdukAdapter(HomeActivity.this);
        listItem = new ArrayList<>();
        produkAdapter.setListItem(listItem);

        lvItem.setOnItemClickListener(this);
        lvItem.setAdapter(produkAdapter);
        lvItem.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);

        getAllProductRequest = new GetAllProductRequest();
        getAllProductRequest.setOnGetAllProductRequestListener(this);
        getAllProductRequest.callApi();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Intent intent = null;

        if (id == R.id.nav_category) {
            // Handle the camera action
            intent = new Intent(HomeActivity.this, CategoryActivity.class);
        } else if (id == R.id.nav_history) {

        } else if (id == R.id.nav_notif) {

        } else if (id == R.id.nav_info) {

        } else if (id == R.id.nav_logout) {
            showLogOutAlertDialog();
        }

        if(intent != null){
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showLogOutAlertDialog(){
        AlertDialog alertDialog = new AlertDialog.Builder(HomeActivity.this).setTitle("Logout")
                .setMessage("Are you sure?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AppPreferences appPreferences = new AppPreferences(HomeActivity.this);
                        appPreferences.clear();

                        Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).create();
        alertDialog.show();
    }

    @Override
    public void onGetAllProductSuccess(ArrayList<Produk> listProduk) {
        lvItem.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);

        listItem.addAll(listProduk);
        produkAdapter.setListItem(listItem);
        produkAdapter.notifyDataSetChanged();
    }

    @Override
    public void onGetAllProductFailure(String errorMessage) {
        progressBar.setVisibility(View.GONE);
        Toast.makeText(HomeActivity.this, errorMessage, Toast.LENGTH_SHORT);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent;
        intent = new Intent(HomeActivity.this, DetilProdukActivity.class);
        intent.putExtra("produk",listItem.get(position));
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.img_cart){
            Intent intent = new Intent(HomeActivity.this, CartActivity.class);
            startActivity(intent);
        }

    }

    public void updateCartQty(){
        ArrayList<CartItem> list = new ArrayList<>();
        CartHelper cartHelper = new CartHelper(this);
        list = cartHelper.getAll();
        if(list.size()>0){
            int totalQty = list.size();
            tvCart.setVisibility(View.VISIBLE);
            tvCart.setText(String.valueOf(totalQty));
        } else {
            tvCart.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateCartQty();
    }
}
