package com.example.djilib.dji.component.CustomVideoFeeder;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import dji.common.airlink.PhysicalSource;
import dji.sdk.camera.Camera;
import dji.sdk.camera.VideoFeeder;

public class CustomVideoFeeder extends Camera {

    private VideoFeeder videoFeeder;
    private List<PhysicalSource> list = new ArrayList<>();

    public CustomVideoFeeder (){
        initCustomVideoFeeder();
        addPhysicalSourceListener();
        showTost();
    }

    public void showTost(){
        Log.e(this.getClass().getSimpleName(),"第一视频源:"+provideTranscodedVideoFeed().getVideoSource()+"第二视频源:"+getSecondaryVideoFeed().getVideoSource());
    }

    public void initCustomVideoFeeder(){
        if (VideoFeeder.getInstance()!=null){
            videoFeeder = VideoFeeder.getInstance();
        }
    }

    public void destoryVideoFeeder(){
        if (videoFeeder!=null){
            removePhysicalSourceListener();
            videoFeeder.destroy();
        }
    }

    public void addPhysicalSourceListener(){
        if (videoFeeder!=null){
            videoFeeder.addPhysicalSourceListener(new VideoFeeder.PhysicalSourceListener() {
                @Override
                public void onChange(VideoFeeder.VideoFeed videoFeed, PhysicalSource physicalSource) {

//                    if (list.size() == 0){
//                        list.add(physicalSource);
//                    }else {
//                        for (int i=0;i<list.size();i++){
//                            if (physicalSource.value() == list.get(i).value()){
//                                list.add(PhysicalSource.FPV_CAM);
//                            }
//                        }
//                    }
                    //ToastUtils.setResultToToast("videoFeed:"+videoFeed.getVideoSource()+"/physicalSource:"+physicalSource);
                }
            });
        }
    }

    public void removePhysicalSourceListener(){
        if (videoFeeder!=null){
            videoFeeder.removePhysicalSourceListener(new VideoFeeder.PhysicalSourceListener() {
                @Override
                public void onChange(VideoFeeder.VideoFeed videoFeed, PhysicalSource physicalSource) {

                }
            });
        }
    }

    public VideoFeeder.VideoFeed getPrimaryVideoFeed(){
        if (videoFeeder!=null){
            return videoFeeder.getPrimaryVideoFeed();
        }else {
            return null;
        }
   }

   public VideoFeeder.VideoFeed getSecondaryVideoFeed(){
        if (videoFeeder!=null){
            return videoFeeder.getSecondaryVideoFeed();
        }else {
            return null;
        }
   }

   public VideoFeeder.VideoFeed provideTranscodedVideoFeed(){
        if (videoFeeder!=null){
            return videoFeeder.provideTranscodedVideoFeed();
        }else {
            return null;
        }
   }

   public void setTranscodingDataRate(float var){
        if (videoFeeder!=null){
            videoFeeder.setTranscodingDataRate(var);
        }
   }

   public float getTranscodingDataRate(){
        if (videoFeeder!=null){
            return videoFeeder.getTranscodingDataRate();
        }else {
            return 0.0f;
        }
   }

   public boolean isLensDistortionCalibrationNeeded(){
        if (videoFeeder!=null){
            return videoFeeder.isLensDistortionCalibrationNeeded();
        }else {
            return false;
        }
   }

   public boolean isFetchKeyFrameNeeded(){
        if (videoFeeder!=null){
            return videoFeeder.isFetchKeyFrameNeeded();
        }else {
            return false;
        }
   }
}
