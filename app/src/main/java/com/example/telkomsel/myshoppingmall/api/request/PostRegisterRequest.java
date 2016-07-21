package com.example.telkomsel.myshoppingmall.api.request;

import com.example.telkomsel.myshoppingmall.api.BaseApi;
import com.example.telkomsel.myshoppingmall.api.response.BaseResponse;
import com.example.telkomsel.myshoppingmall.api.response.User;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Multimatics on 21/07/2016.
 */
public class PostRegisterRequest extends BaseApi {

    private OnPostRegisterRequestListener onPostRegisterRequestListener;
    private RequestParams requestParams;
    private AsyncHttpClient client;

    public PostRegisterRequest(){
        client = getHttpClient();
    }

    public OnPostRegisterRequestListener getOnPostRegisterRequestListener() {
        return onPostRegisterRequestListener;
    }

    public void setOnPostRegisterRequestListener(OnPostRegisterRequestListener onPostRegisterRequestListener) {
        this.onPostRegisterRequestListener = onPostRegisterRequestListener;
    }

    public RequestParams getRequestParams() {
        return requestParams;
    }

    public void setRequestParams(RequestParams requestParams) {
        this.requestParams = requestParams;
    }

    public AsyncHttpClient getClient() {
        return client;
    }

    public void setClient(AsyncHttpClient client) {
        this.client = client;
    }

    public interface OnPostRegisterRequestListener{
        void onPostRegisterSuccess(BaseResponse response);
        void onPostRegisterFailure(String errorMessage);
    }

    @Override
    public void callApi() {
        super.callApi();
        client.post(POST_REGISTER, getRequestParams(), new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = new String(responseBody);
                BaseResponse bresp = getBaseResponse(response);
                if(bresp != null) {
                    if (bresp.getStatus().equals("200")) {
                        getOnPostRegisterRequestListener().onPostRegisterSuccess(bresp);
                    } else {
                        getOnPostRegisterRequestListener().onPostRegisterFailure(bresp.getMessage());
                    }
                } else {
                    getOnPostRegisterRequestListener().onPostRegisterFailure("Internal Error");
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                getOnPostRegisterRequestListener().onPostRegisterFailure("Could not connect to server");
            }
        });
    }


    private BaseResponse getBaseResponse(String response){
        BaseResponse baseResponse = null;
        try{
            JSONObject jsonObject = new JSONObject(response);
            baseResponse = new BaseResponse();
            baseResponse.setStatus(jsonObject.getString("status"));
            baseResponse.setMessage(jsonObject.getString("message"));
        } catch (Exception e){
            return null;
        }
        return baseResponse;
    }
}
