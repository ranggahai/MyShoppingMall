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
public class PostLoginRequest extends BaseApi{

    private OnPostLoginRequestListener onPostLoginRequestListener;
    private RequestParams requestParams;
    private AsyncHttpClient client;

    public PostLoginRequest(){
        client = getHttpClient();
    }

    public OnPostLoginRequestListener getOnPostLoginRequestListener() {
        return onPostLoginRequestListener;
    }

    public void setOnPostLoginRequestListener(OnPostLoginRequestListener onPostLoginRequestListener) {
        this.onPostLoginRequestListener = onPostLoginRequestListener;
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

    public interface OnPostLoginRequestListener {
        void onPostLoginSuccess(User user);
        void onPostLoginFailure(String errorMessage);
    }

    @Override
    public void callApi() {
        super.callApi();
        client.post(POST_LOGIN, getRequestParams(), new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = new String(responseBody);
                User user = getUser(response);
                if(user != null) {
                    if (user.getStatus().equals("200")) {
                        getOnPostLoginRequestListener().onPostLoginSuccess(user);
                    } else {
                        getOnPostLoginRequestListener().onPostLoginFailure(user.getMessage());
                    }
                } else {
                    getOnPostLoginRequestListener().onPostLoginFailure("Internal Error");
                }
            }


            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                getOnPostLoginRequestListener().onPostLoginFailure("Could not connect to server");
            }
        });
    }

    private User getUser(String response){
        User user = null;
        try{
            JSONObject jsonObject = new JSONObject(response);
            user = new User();
            if(jsonObject.getString("status").equals("200")) {
                user.setStatus(jsonObject.getString("status"));
                user.setMessage(jsonObject.getString("message"));
                user.setName(jsonObject.getJSONObject("user").getString("username"));
                user.setEmail(jsonObject.getJSONObject("user").getString("email"));
                user.setUserid(jsonObject.getJSONObject("user").getString("user_id"));
            } else {
                user.setStatus(jsonObject.getString("status"));
                user.setMessage(jsonObject.getString("message"));
            }
        } catch (Exception e) {
            return null;
        }
        return user;
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
