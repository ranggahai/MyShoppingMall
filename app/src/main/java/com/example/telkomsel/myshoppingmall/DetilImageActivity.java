package com.example.telkomsel.myshoppingmall;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

public class DetilImageActivity extends AppCompatActivity {
    private ArrayList<String> listImgUrl;
    private ViewPager vPager;
    private int selectedPosition;
    private ImagePagerAdapter imPagerAdp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detil_image);

        getSupportActionBar().hide();
        vPager = (ViewPager)findViewById(R.id.vpager_detil_image);

        listImgUrl = getIntent().getStringArrayListExtra("url_images");
        selectedPosition = getIntent().getIntExtra("position",0);

        imPagerAdp = new ImagePagerAdapter(getSupportFragmentManager());
        imPagerAdp.setListImage(listImgUrl);

        vPager.setAdapter(imPagerAdp);
        vPager.setCurrentItem(selectedPosition);
    }
}
