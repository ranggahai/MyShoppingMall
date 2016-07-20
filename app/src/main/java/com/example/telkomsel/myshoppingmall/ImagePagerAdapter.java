package com.example.telkomsel.myshoppingmall;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by Multimatics on 20/07/2016.
 */
public class ImagePagerAdapter extends FragmentPagerAdapter {
    private ArrayList<String> listImage;

    public ImagePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        ImageFragment imgFrag = new ImageFragment();
        imgFrag.setImageUrl(getListImage().get(position));
        return imgFrag;
    }

    @Override
    public int getCount() {
        return getListImage().size();
    }

    public ArrayList<String> getListImage() {
        return listImage;
    }

    public void setListImage(ArrayList<String> listImage) {
        this.listImage = listImage;
    }
}
