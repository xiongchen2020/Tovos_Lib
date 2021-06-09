package com.example.filgthhublibrary.listener;

import com.example.filgthhublibrary.network.bean.BaseResponse;

import okhttp3.ResponseBody;

public interface HubVideoListener {

    void getVideoUrl(ResponseBody baseResponse);
}
