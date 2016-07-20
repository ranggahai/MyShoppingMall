package com.example.telkomsel.myshoppingmall;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class CategoryActivity extends AppCompatActivity implements ListView.OnItemClickListener {

    private ListView lvKategori;
    private String[] categories = new String[]{
            "Kaos","Celana","Topi","Tas","Dompet"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvKategori = (ListView)findViewById(R.id.lv_kategoribarang);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(CategoryActivity.this,
                android.R.layout.simple_list_item_1, android.R.id.text1, categories);

        // Assign adapter to ListView
        lvKategori.setAdapter(adapter);
        lvKategori.setOnItemClickListener(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // ListView Clicked item value
        String  itemValue    = (String) lvKategori.getItemAtPosition(position);
        // Show Alert
        Toast.makeText(getApplicationContext(), itemValue , Toast.LENGTH_LONG)
                .show();
        Intent intent = new Intent(CategoryActivity.this, ProdukActivity.class);
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
