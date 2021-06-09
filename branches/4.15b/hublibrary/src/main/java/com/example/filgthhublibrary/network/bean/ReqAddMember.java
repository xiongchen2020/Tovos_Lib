package com.example.filgthhublibrary.network.bean;

import java.io.Serializable;

public class ReqAddMember implements Serializable {
    private String id;
    private int userId;
    private int role;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }
}
