package com.example.telkomsel.myshoppingmall.api.response;

/**
 * Created by Multimatics on 21/07/2016.
 */
public class User extends BaseResponse {
    private String name;
    private String userid;
    private String email;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
