package com.example.filgthhublibrary.network.bean;

import java.io.Serializable;

public class ReqLogin implements Serializable {
    private String account;
    private  String password;


    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
