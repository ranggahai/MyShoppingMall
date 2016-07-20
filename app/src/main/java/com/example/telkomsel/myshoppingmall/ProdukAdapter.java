package com.example.telkomsel.myshoppingmall;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by Multimatics on 19/07/2016.
 */
public class ProdukAdapter extends BaseAdapter {
    private Activity activity;
    private ArrayList<Produk> listItem;

    public ProdukAdapter(Activity activity){
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return getListItem().size();
    }

    @Override
    public Object getItem(int position) {
        return getListItem().get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        View convertView = view;
        ViewHolder holder;
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_produk,null);
            holder = new ViewHolder();
            holder.imgProduk = (ImageView)convertView.findViewById(R.id.img_item_produk);
            holder.tv_nama_produk = (TextView) convertView.findViewById(R.id.nama_item_produk);
            holder.tv_harga_produk=(TextView) convertView.findViewById(R.id.harga_item_produk);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        Produk item = getListItem().get(position);
        holder.tv_nama_produk.setText(item.getNama());
        holder.tv_harga_produk.setText(item.getHarga());

        Glide.with(activity).load(item.getImageUrl()).into(holder.imgProduk);
        return convertView;
    }

    static class ViewHolder {
        ImageView imgProduk;
        TextView tv_nama_produk;
        TextView tv_harga_produk;

    }

    public ArrayList<Produk> getListItem() {
        return listItem;
    }

    public void setListItem(ArrayList<Produk> listItem) {
        this.listItem = listItem;
    }
}
