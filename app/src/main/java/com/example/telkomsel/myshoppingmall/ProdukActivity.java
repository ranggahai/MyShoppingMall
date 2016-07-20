package com.example.telkomsel.myshoppingmall;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class ProdukActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView lv_barang;
    private ProdukAdapter adapter;
    private ArrayList<Produk> list_produk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produk);

        lv_barang = (ListView)findViewById(R.id.lv_list_produk);
        lv_barang.setOnItemClickListener(this);
        list_produk = new ArrayList<>();
        adapter = new ProdukAdapter(ProdukActivity.this);
        adapter.setListItem(list_produk);

        Produk mProduk = null;
        for(int i=0; i<SampleData.listSepatu.length; i++){
            mProduk = new Produk();
            mProduk.setId(System.currentTimeMillis());
            mProduk.setNama(SampleData.listSepatu[i][0]);
            mProduk.setImageUrl(SampleData.listSepatu[i][1]);
            mProduk.setHarga(SampleData.listSepatu[i][2]);
            list_produk.add(mProduk);
        }
        adapter.setListItem(list_produk);
        adapter.notifyDataSetChanged();
        lv_barang.setAdapter(adapter);

        getSupportActionBar().setTitle("List Produk");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent;
        intent = new Intent(ProdukActivity.this, DetilProdukActivity.class);
        intent.putExtra("produk",list_produk.get(position));
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
