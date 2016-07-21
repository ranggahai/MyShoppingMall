package com.example.telkomsel.myshoppingmall.api.request;

import com.example.telkomsel.myshoppingmall.Produk;
import com.example.telkomsel.myshoppingmall.api.BaseApi;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Multimatics on 21/07/2016.
 */
public class GetAllProductRequest extends BaseApi {

    private OnGetAllProductRequestListener onGetAllProductRequestListener;
    private AsyncHttpClient client;

    public GetAllProductRequest(){
        client = getHttpClient();
    }

    public OnGetAllProductRequestListener getOnGetAllProductRequestListener() {
        return onGetAllProductRequestListener;
    }

    public void setOnGetAllProductRequestListener(OnGetAllProductRequestListener onGetAllProductRequestListener) {
        this.onGetAllProductRequestListener = onGetAllProductRequestListener;
    }

    public interface OnGetAllProductRequestListener{
        void onGetAllProductSuccess(ArrayList<Produk> listProduk);
        void onGetAllProductFailure(String errorMessage);
    }

    @Override
    public void callApi() {
        super.callApi();
        client.get(GET_ALL_PRODUCTS, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = new String(responseBody);
                ArrayList<Produk> list = getAllProducts(response);
                if(list != null){
                    if(list.size()>0){
                        getOnGetAllProductRequestListener().onGetAllProductSuccess(list);
                    } else {
                        getOnGetAllProductRequestListener().onGetAllProductFailure("no data found");
                    }
                } else {
                    getOnGetAllProductRequestListener().onGetAllProductFailure("no data found");
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                getOnGetAllProductRequestListener().onGetAllProductFailure("could not connect to server");
            }
        });
    }

    @Override
    public void cancelRequest() {
        super.cancelRequest();
        if(client != null){
            client.cancelAllRequests(true);
        }
    }

    private ArrayList<Produk> getAllProducts(String response){
        ArrayList<Produk> list = null;
        try {
            JSONObject jsonObject = new JSONObject(response);
            if(jsonObject.getString("status").equals("200")){
                JSONArray jsonArray = jsonObject.getJSONArray("products");
                if(jsonArray.length()>0){
                    list = new ArrayList<>();
                    Produk mProduk = null;
                    for( int i = 0 ; i<jsonArray.length(); i++){
                        JSONObject item = jsonArray.getJSONObject(i);
                        mProduk = new Produk();
                        mProduk.setId(Long.parseLong(item.getString("product_id")));
                        mProduk.setNama(item.getString("name"));
                        mProduk.setHarga(item.getString("price"));
                        mProduk.setImageUrl(item.getString("img_url"));

                        list.add(mProduk);
                    }
                }
            } else {
                return null;
            }
        } catch (Exception e){
                return null;
        }
        return list;
    }
}
