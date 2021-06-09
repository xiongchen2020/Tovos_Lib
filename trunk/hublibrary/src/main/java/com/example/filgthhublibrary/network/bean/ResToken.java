package com.example.filgthhublibrary.network.bean;

import java.io.Serializable;

public class ResToken implements Serializable {
   private String token;
    private String signkey;
    private String validity;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getSignkey() {
        return signkey;
    }

    public void setSignkey(String signkey) {
        this.signkey = signkey;
    }

    public String getValidity() {
        return validity;
    }

    public void setValidity(String validity) {
        this.validity = validity;
    }
}
