package com.example.telkomsel.myshoppingmall;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;


/**
 * A simple {@link Fragment} subclass.
 */
public class ImageFragment extends Fragment {

    private String ImageUrl;
    private ImageView imgview;


    public ImageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_image, container, false);
        setImgview((ImageView)view.findViewById(R.id.fragment_img));
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Glide.with(ImageFragment.this).load(getImageUrl()).into(imgview);
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public ImageView getImgview() {
        return imgview;
    }

    public void setImgview(ImageView imgview) {
        this.imgview = imgview;
    }
}
