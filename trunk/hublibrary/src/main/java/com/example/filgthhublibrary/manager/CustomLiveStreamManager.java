package com.example.filgthhublibrary.manager;



import dji.sdk.sdkmanager.DJISDKManager;
import dji.sdk.sdkmanager.LiveStreamManager;

public class CustomLiveStreamManager {

    private LiveStreamManager liveStreamManager;
    private LiveStreamManager.LiveStreamVideoSource currentVideoSource = LiveStreamManager.LiveStreamVideoSource.Primary;
    private LiveStreamManager.OnLiveChangeListener listener;

    public CustomLiveStreamManager() {
        getLiveStreamManager();
        listener = new LiveStreamManager.OnLiveChangeListener() {
            @Override
            public void onStatusChanged(int i) {
               //关闭 2   开启0
               // ToastUtils.setResultToToast("status changed : " + i);


            }
        };
    }

    private LiveStreamManager getLiveStreamManager(){
        if (DJISDKManager.getInstance()!= null){
            liveStreamManager = DJISDKManager.getInstance().getLiveStreamManager();
        }
        return liveStreamManager;
    }

    /**
     * 是否启动直播流
     * @return
     */
    public boolean isStreaming(){
        if (liveStreamManager!=null){
            return liveStreamManager.isStreaming();
        }else {
            return false;
        }
    }

    /**
     * 设置URL 连接RTMP服务
     * @param s
     */
    public void setLiveUrl(String s){
        if (liveStreamManager!=null){
            liveStreamManager.setLiveUrl(s);
        }
    }

    /**
     * 开始流
     */
    public int startStream(){
        if (liveStreamManager!=null){
           return liveStreamManager.startStream();
        }
        return 0;
    }


    /**
     * 获取RTMP的URL连接
     * @return
     */
    public String getLiveUrl(){
        if (liveStreamManager!=null){
            return liveStreamManager.getLiveUrl();
        }else {
            return "";
        }
    }


    public void stopStream(){
        if (liveStreamManager!=null){

            liveStreamManager.stopStream();
        }
    }

    /**
     * 设置直播流开始时间
     * @return
     */
    public void setStartTime(){
        if (liveStreamManager!=null){
             liveStreamManager.setStartTime();
        }
    }
    /**
     * 获取直播流开始时间
     * @return
     */
    public long getStartTime(){
        if (liveStreamManager!=null){
            return liveStreamManager.getStartTime();
        }else {
            return 0;
        }
    }


    /**
     * 开关音频流
     * @param isAble
     */
    public void setAudioStreamingEnabled(boolean isAble){
        if (liveStreamManager!=null){
            liveStreamManager.setAudioStreamingEnabled(isAble);
        }
    }

    /**
     * 设置静音
     * @param isAble
     */
    public void setAudioMuted(boolean isAble){
        if (liveStreamManager!=null){
            liveStreamManager.setAudioMuted(isAble);
        }
    }

    /**
     * 音频是否启用
     * @return
     */
    public boolean isLiveAudioEnabled(){
        if (liveStreamManager!=null){
            return liveStreamManager.isLiveAudioEnabled();
        }else {
            return false;
        }
    }

    /**
     * 音频是否被静音，则返回true，反之false
     */
    public boolean isAudioMuted(){
        if (liveStreamManager!=null){
            return liveStreamManager.isAudioMuted();
        }else {
            return false;
        }
    }

    /**
     * 为直播流启用或者关闭强制视频编码
     * @param isAble
     */
    public void setVideoEncodingEnabled(boolean isAble){
        if (liveStreamManager!=null){
            liveStreamManager.setVideoEncodingEnabled(isAble);
        }
    }

    /**
     * 如果启用了视频编码，则返回true，反之flase
     * @return
     */
    public boolean isVideoEncodingEnabled(){
        if (liveStreamManager!=null){
            return liveStreamManager.isVideoEncodingEnabled();
        }else {
            return false;
        }
    }

    /**
     * 返回实时实时视频fps。
     * @return
     */
    public float getLiveVideoFps(){
        if (liveStreamManager!=null){
            return liveStreamManager.getLiveVideoFps();
        }else {
            return 0.0f;
        }
    }

    /**
     * 返回以kpbs为单位的实时视频流比特率。
     * @return
     */
    public int getLiveVideoBitRate(){
        if (liveStreamManager!=null){
            return liveStreamManager.getLiveVideoBitRate();
        }else {
            return 0;
        }
    }

    /**
     * 返回以kpbs为单位的实时音频流比特率。
     * @return
     */
    public int getLiveAudioBitRate(){
        if (liveStreamManager!=null){
            return  liveStreamManager.getLiveVideoBitRate();
        }else {
            return  0;
        }
    }

    /**
     * 回实时视频缓存列表大小，单位：帧。
     * @return
     */
    public int getLiveVideoCacheSize(){
        if (liveStreamManager!=null){
            return  liveStreamManager.getLiveVideoCacheSize();
        }else {
            return 0;
        }
    }

    public void setSource(){
        if (liveStreamManager!=null)
//        currentVideoSource = (currentVideoSource == LiveStreamManager.LiveStreamVideoSource.Primary) ?
//                LiveStreamManager.LiveStreamVideoSource.Secoundary :
//                LiveStreamManager.LiveStreamVideoSource.Primary;
        liveStreamManager.setVideoSource(currentVideoSource);
    }

    public void addListener(){
        if (liveStreamManager!=null){
            liveStreamManager.registerListener(listener);
        }
    }

    public void removeListener(){
        if (liveStreamManager!=null){
            liveStreamManager.unregisterListener(listener);
        }
    }
}
