package com.example.telkomsel.myshoppingmall.api;

import com.loopj.android.http.AsyncHttpClient;

/**
 * Created by Multimatics on 21/07/2016.
 */
public class BaseApi implements BaseApiMethods {
    private String BASE_URL = "http://192.168.20.48:8080/index.php/api/";

    protected String GET_ALL_PRODUCTS = BASE_URL + "products/all";
    protected String GET_DETAIL_PRODUCTS = BASE_URL + "products/detail";
    protected String POST_REGISTER = BASE_URL + "user/register";
    protected String POST_LOGIN = BASE_URL + "user/login";

    public AsyncHttpClient getHttpClient(){
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        return asyncHttpClient;


    }

    @Override
    public void callApi() {

    }

    @Override
    public void cancelRequest() {

    }
}
