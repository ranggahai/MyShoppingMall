package com.example.telkomsel.myshoppingmall.db;

import android.content.Context;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

/**
 * Created by Multimatics on 22/07/2016.
 */
public class CartHelper {

    private Realm realm;
    private Context mContext;

    public CartHelper(Context c){
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(c).build();
        realm = Realm.getInstance(realmConfiguration);
    }

    public void create(int productId,
                       String productName, String productImage,
                       int productQty, Double productPrice){
        realm.beginTransaction();
        CartItem cartItem = realm.createObject(CartItem.class);
        cartItem.setId(productId);
        cartItem.setName(productName);
        cartItem.setImage(productImage);
        cartItem.setPrice(productPrice);
        cartItem.setQty(productQty);
        realm.commitTransaction();
    }


    public ArrayList<CartItem> getAll(){
        ArrayList<CartItem> list = new ArrayList<>();
        RealmResults<CartItem> results = realm.where(CartItem.class).findAll();
        if(results.size()>0){
            list = new ArrayList<>();
            for(CartItem item: results){
                list.add(item);
            }
        }
        return list;
    }

    public void update(int productID, int productQty){
        realm.beginTransaction();
        CartItem cartItem = realm.where(CartItem.class).equalTo("id",productID).findFirst();
        cartItem.setQty(productQty);
        realm.commitTransaction();
    }

    public void delete(int productID){
        realm.beginTransaction();
        CartItem cartItem = realm.where(CartItem.class).equalTo("id",productID).findFirst();
        cartItem.deleteFromRealm();
        realm.commitTransaction();
    }

    public boolean isItemAlreadyExist(int productID){
        CartItem cartItem = realm.where(CartItem.class).equalTo("id",productID).findFirst();
        if(cartItem == null){
            return false;
        } else {
            return true;
        }
    }
}
