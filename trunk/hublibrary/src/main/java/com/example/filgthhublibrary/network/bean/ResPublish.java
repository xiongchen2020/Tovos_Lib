package com.example.filgthhublibrary.network.bean;

import java.io.Serializable;

public class ResPublish implements Serializable {
   public RtmpBean rtmp;

    public RtmpBean getRtmp() {
        return rtmp;
    }


    public void setRtmp(RtmpBean rtmp) {
        this.rtmp = rtmp;
    }

    public class RtmpBean implements Serializable{
       private String publishUrl;

        public String getPublishUrl() {
            return publishUrl;
        }

        public void setPublishUrl(String publishUrl) {
            this.publishUrl = publishUrl;
        }
    }
}
