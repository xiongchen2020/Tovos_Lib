package com.example.filgthhublibrary.network.bean;

import java.io.Serializable;

public class ResPlay implements Serializable {

    private PlayRtmpBean   rtmp;
    private  Hlvbean hlv;


    public PlayRtmpBean getRtmp() {
        return rtmp;
    }

    public void setRtmp(PlayRtmpBean rtmp) {
        this.rtmp = rtmp;
    }

    public Hlvbean getHlv() {
        return hlv;
    }

    public void setHlv(Hlvbean hlv) {
        this.hlv = hlv;
    }

    public   class PlayRtmpBean implements Serializable {
      private   String playUrl;

        public String getPlayUrl() {
            return playUrl;
        }

        public void setPlayUrl(String playUrl) {
            this.playUrl = playUrl;
        }
    }
    public class Hlvbean implements Serializable {
        private String playUrl;

        public String getPlayUrl() {
            return playUrl;
        }

        public void setPlayUrl(String playUrl) {
            this.playUrl = playUrl;
        }
    }
}
