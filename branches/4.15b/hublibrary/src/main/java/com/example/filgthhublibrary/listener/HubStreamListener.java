package com.example.filgthhublibrary.listener;

import com.example.filgthhublibrary.network.bean.ResPlay;

public interface HubStreamListener {
    void getPublishUrlSuccess(String publishUrl);

    void  getPublishUrlFail();
    void updateVideoInfo();
    void playUrl(ResPlay publish);
}
