package com.example.telkomsel.myshoppingmall;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Multimatics on 19/07/2016.
 */
public class Produk implements Parcelable {
    private long id;
    private String nama;
    private String harga;
    private String imageUrl;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.nama);
        dest.writeString(this.harga);
        dest.writeString(this.imageUrl);
    }

    public Produk() {
    }

    protected Produk(Parcel in) {
        this.id = in.readLong();
        this.nama = in.readString();
        this.harga = in.readString();
        this.imageUrl = in.readString();
    }

    public static final Parcelable.Creator<Produk> CREATOR = new Parcelable.Creator<Produk>() {
        @Override
        public Produk createFromParcel(Parcel source) {
            return new Produk(source);
        }

        @Override
        public Produk[] newArray(int size) {
            return new Produk[size];
        }
    };
}
