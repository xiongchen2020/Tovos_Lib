package com.example.filgthhublibrary.manager.listener;

import dji.common.error.DJIError;

public interface CustomUserAccountListener {

    void getUserAccountName(String s);

    void getUserAccountNameFailure(DJIError djiError);
}
