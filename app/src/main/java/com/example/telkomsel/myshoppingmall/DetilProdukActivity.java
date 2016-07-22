package com.example.telkomsel.myshoppingmall;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.telkomsel.myshoppingmall.db.CartHelper;
import com.example.telkomsel.myshoppingmall.db.CartItem;

import java.util.ArrayList;

public class DetilProdukActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_name_detail;
    private Button btn_addchart;
    private ImageView img_detail, thumb1, thumb2, thumb3, thumb4;
    private Spinner spinner_ukuran;
    private TextView tv_harga_detail;
    private TextView tv_desc;
    private CartHelper cartHelper;

    private TextView tvTitle, tvCart;
    private ImageView imgCart;

    private Produk selectedProduk;

    private int currentImagePos = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detil_produk);
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_toolbar);

        selectedProduk = getIntent().getParcelableExtra("produk");

        tv_name_detail = (TextView)findViewById(R.id.tv_nama_detail);
        tv_harga_detail = (TextView)findViewById(R.id.tv_harga_detail);
        spinner_ukuran = (Spinner)findViewById(R.id.spinner_ukuran);
        btn_addchart = (Button)findViewById(R.id.btn_addchart);
        tv_desc = (TextView)findViewById(R.id.tv_desc);
        img_detail = (ImageView)findViewById(R.id.img_detail);
        tvTitle = (TextView)findViewById(R.id.Title);
        tvCart = (TextView)findViewById(R.id.tv_cart);
        imgCart = (ImageView)findViewById(R.id.img_cart);
        imgCart.setOnClickListener(this);

        cartHelper = new CartHelper(this);

        img_detail.setOnClickListener(this);
        thumb1 = (ImageView)findViewById(R.id.thumb1);
        thumb2 = (ImageView)findViewById(R.id.thumb2);
        thumb3 = (ImageView)findViewById(R.id.thumb3);
        thumb4 = (ImageView)findViewById(R.id.thumb4);
        thumb1.setOnClickListener(this);
        thumb2.setOnClickListener(this);
        thumb3.setOnClickListener(this);
        thumb4.setOnClickListener(this);
        btn_addchart.setOnClickListener(this);

        setSupportActionBar(toolbar);
        tvTitle.setText("Detail Produk");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Produk dProduk = getIntent().getParcelableExtra("produk");
        tv_name_detail.setText(dProduk.getNama());
        tv_harga_detail.setText(dProduk.getHarga());
        tv_desc.setText("Kami bertekad untuk melindungi dan menghargai hak pribadi pengguna kami. Kenyamanan dan rasa aman anda sebagai pengguna dalam menggunakan situs web OLX Indonesia yang saat ini terletak di www.olx.co.id, aplikasi mobile OLX Indonesia dan peralatan dan layanan lain yang terasosiasi dengan OLX Indonesia (“Layanan”) sangatlah penting bagi kami.\n" +
                "\n" +
                "Kebijakan Privasi ini beserta Syarat & Ketentuan Umum menggambarkan praktek kami terkait dengan data pribadi yang terkumpul melalui Layanan kami, dimana pengguna dapat terlibat di dalam kegiatan, antara lain, mendaftar dengan kami, membuat profil, memuat atau melihat iklan baris online, bertukar pesan dan melaksanakan sejumlah fungsi lain yang terkait.\n" +
                "\n" +
                "Kebijakan Privasi ini tunduk pada hukum yang berlaku di Republik Indonesia khususnya Undang-undang No. 11 tahun 2008 tentang Informasi dan Transaksi Elektronik dan Peraturan Pemerintah No. 82 tahun 2012 tentang Penyelenggaraan Sistem dan Transaksi Elektronik.\n" +
                "\n" +
                "Dengan menggunakan Layanan dimana Kebijakan Privasi ini muncul, anda dianggap telah membaca, mengerti dan memberikan persetujuan kepada penggunaan data pribadi anda oleh kami sesuai dengan Kebijakan Privasi ini.");

        Glide.with(DetilProdukActivity.this).load(SampleData.thumbnails[0]).into(thumb1);
        Glide.with(DetilProdukActivity.this).load(SampleData.thumbnails[1]).into(thumb2);
        Glide.with(DetilProdukActivity.this).load(SampleData.thumbnails[2]).into(thumb3);
        Glide.with(DetilProdukActivity.this).load(SampleData.thumbnails[3]).into(thumb4);
        Glide.with(DetilProdukActivity.this).load(dProduk.getImageUrl()).into(img_detail);

        String[] size = new String[]{
                "Pilih Ukuran","36","37","38","39","40","41","42","43"};

        ArrayAdapter<String> ukuranAdapter = new ArrayAdapter<String>(DetilProdukActivity.this,
                android.R.layout.simple_list_item_1, android.R.id.text1, size);
        spinner_ukuran.setAdapter(ukuranAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.thumb1:
                Glide.with(DetilProdukActivity.this).load(SampleData.thumbnails[0]).into(img_detail);
                currentImagePos = 0;
                break;
            case R.id.thumb2:
                Glide.with(DetilProdukActivity.this).load(SampleData.thumbnails[1]).into(img_detail);
                currentImagePos = 1;
                break;
            case R.id.thumb3:
                Glide.with(DetilProdukActivity.this).load(SampleData.thumbnails[2]).into(img_detail);
                currentImagePos = 2;
                break;
            case R.id.thumb4:
                Glide.with(DetilProdukActivity.this).load(SampleData.thumbnails[3]).into(img_detail);
                currentImagePos = 3;
                break;
            case R.id.img_detail:
                ArrayList<String> list = new ArrayList<>();
                for(int i=0 ; i<SampleData.thumbnails.length; i++){
                    list.add(SampleData.thumbnails[i]);
                }
                Intent intent = new Intent(DetilProdukActivity.this, DetilImageActivity.class);
                intent.putExtra("url_images",list);
                intent.putExtra("position",currentImagePos);
                startActivity(intent);
                break;
            case R.id.btn_addchart:
                if(cartHelper.isItemAlreadyExist((int) selectedProduk.getId())){
                    Toast.makeText(DetilProdukActivity.this, "This product is already in cart", Toast.LENGTH_SHORT).show();
                } else {
                    cartHelper.create((int)selectedProduk.getId(), selectedProduk.getNama(), selectedProduk.getImageUrl(),1,Double.parseDouble(selectedProduk.getHarga()));
                    Toast.makeText(DetilProdukActivity.this, "This product is successfully added into cart", Toast.LENGTH_SHORT).show();
                }
                updateCartQty();
                break;
            case R.id.img_cart:
                intent = new Intent(DetilProdukActivity.this, CartActivity.class);
                startActivity(intent);
                break;
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
