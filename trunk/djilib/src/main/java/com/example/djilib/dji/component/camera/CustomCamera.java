package com.example.djilib.dji.component.camera;

import android.graphics.PointF;
import android.os.Handler;
import android.os.Looper;

import com.example.commonlib.utils.ToastUtils;
import com.example.djilib.dji.component.camera.lens.CustomLens;
import com.example.djilib.dji.component.camera.lens.LensListener;

import java.util.ArrayList;
import java.util.List;

import dji.common.camera.CameraVideoStreamSource;
import dji.common.camera.SettingsDefinitions;
import dji.common.error.DJIError;
import dji.common.util.CommonCallbacks;
import dji.keysdk.CameraKey;
import dji.keysdk.DJIKey;
import dji.keysdk.KeyManager;
import dji.keysdk.callback.SetCallback;
import dji.sdk.camera.Camera;
import dji.sdk.camera.Lens;

public class CustomCamera {

    Camera camera;
    private List<Lens> lensList = new ArrayList<>();
    public ZoomManager zoomManager;
    private Handler handler = new Handler(Looper.getMainLooper());
    public int getCameraindex() {
        return camera.getIndex();
    }
    CustomCameraListener customCameraListener;
    public CustomCamera(Camera camera){
        this.camera = camera;
        zoomManager = new ZoomManager(camera);
    }

    public void setCustomCameraListener(CustomCameraListener customCameraListener){
        this.customCameraListener = customCameraListener;
    }

    /**
     * 获取相机名称
     * @return
     */
    public String getDisplayName(){
        if (camera!=null){
           return camera.getDisplayName();
        }else {
            return "";
        }
    }

    /**
     * 相机为多镜头相机时返回nil。相反，用户可以通过lens对象访问lens的功能。
     */
    public void getCapabilities(){

        if (camera!=null){
            camera.getCapabilities();
        }
    }

    public void getCameraSide(){
        if (camera!=null){
           // VideoFeeder.getInstance().
        }
    }

    public void getLenses(){
        if (camera!=null){
            if (camera.getLenses()!=null) {
                lensList = camera.getLenses();
            }
        }
    }

    public Lens getLens(int index){
        if (camera != null){
           return camera.getLens(index);
        }else {
            return null;
        }
    }

    public boolean isInterchangeableLensSupported(){
        if (camera!=null){
            return camera.isInterchangeableLensSupported();
        }else {
            return false;
        }
    }

    public void getLensInformation(){
        if (camera!=null){

            if (!isInterchangeableLensSupported()){
                ToastUtils.setResultToToast("不支持可拆装的镜头");
                return;
            }

            camera.getLensInformation(new CommonCallbacks.CompletionCallbackWith<String>() {
                @Override
                public void onSuccess(String s) {

                }

                @Override
                public void onFailure(DJIError djiError) {

                }
            });
        }
    }


    /**
     * 设置聚焦模式
     * @param var
     */
    public void setFocusMode(SettingsDefinitions.FocusMode var){
        if (camera!=null){
            camera.setFocusMode(var, new CommonCallbacks.CompletionCallback() {
                @Override
                public void onResult(DJIError djiError) {

                }
            });
        }
    }

    public void getFocusMode(){
        if (camera!=null){
            camera.getFocusMode(new CommonCallbacks.CompletionCallbackWith<SettingsDefinitions.FocusMode>() {
                @Override
                public void onSuccess(SettingsDefinitions.FocusMode focusMode) {

                }

                @Override
                public void onFailure(DJIError djiError) {

                }
            });
        }
    }

    public void setFocusTarget(PointF var){
        if (camera!=null){
            camera.setFocusTarget(var, new CommonCallbacks.CompletionCallback() {
                @Override
                public void onResult(DJIError djiError) {

                }
            });
        }
    }

    public void getFocusTarget(){
        if (camera!=null){
            camera.getFocusMode(new CommonCallbacks.CompletionCallbackWith<SettingsDefinitions.FocusMode>() {
                @Override
                public void onSuccess(SettingsDefinitions.FocusMode focusMode) {

                }

                @Override
                public void onFailure(DJIError djiError) {

                }
            });
        }
    }

    public CustomLens getLensByIndex(int index, LensListener lensListener){
        CustomLens lens = null;
        for (Lens item:lensList){
            if (index == item.getIndex()){
                lens = new CustomLens(item,lensListener);
                return lens;
            }
        }

        return null;
    }


    public CustomLens getLensByType( SettingsDefinitions.LensType type,LensListener lensListener){
        CustomLens lens = null;
        for (Lens item:lensList){
            if (type == item.getType()){
                lens = new CustomLens(item,lensListener);
                return lens;
            }
        }

        return null;
    }

    public List<Lens> getLensList() {
        return lensList;
    }

    private ArrayList<CameraVideoStreamSource>  list = new ArrayList<>();
    private ArrayList<CameraVideoStreamSource> updateCameraVideoStreamSourceRange() {

        DJIKey key = CameraKey.create(CameraKey.CAMERA_VIDEO_STREAM_SOURCE_RANGE, 0);
        if (KeyManager.getInstance().getValue(key) != null) {

            list = (ArrayList<CameraVideoStreamSource>)KeyManager.getInstance().getValue(key);
        }
        return list;
    }

    public void setCameraSource(final CameraVideoStreamSource videoStreamSource){
        Runnable r = new Runnable() {
            @Override
            public void run() {
                setVideoStreamSource(videoStreamSource);
            }
        };
        handler.post(r);
    }

    public CameraVideoStreamSource getVideoStreamSource(){
        DJIKey key = CameraKey.create(CameraKey.CAMERA_VIDEO_STREAM_SOURCE, 0);
        CameraVideoStreamSource videoStreamSource =(CameraVideoStreamSource) KeyManager.getInstance().getValue(key);
        return videoStreamSource;
    }

    public void getCameraViedoStreamSource(){
        camera.getCameraVideoStreamSource(new CommonCallbacks.CompletionCallbackWith<CameraVideoStreamSource>() {
            @Override
            public void onSuccess(CameraVideoStreamSource cameraVideoStreamSource) {
                customCameraListener.getCameraViedoStreamSource(cameraVideoStreamSource);
            }

            @Override
            public void onFailure(DJIError djiError) {

                ToastUtils.setResultToToast("获取当前图像资源出错："+djiError.getDescription());
            }
        });
    }

    public void setCameraViedoStreamSource(CameraVideoStreamSource viedoStreamSource){
        camera.setCameraVideoStreamSource(viedoStreamSource, new CommonCallbacks.CompletionCallback() {
            @Override
            public void onResult(DJIError djiError) {
                if (djiError !=null){
                    ToastUtils.setResultToToast("设置图像资源出错："+djiError.getDescription());
                }
            }
        });
    }

    private void setVideoStreamSource(CameraVideoStreamSource videoStreamSource) {
        DJIKey key = CameraKey.create(CameraKey.CAMERA_VIDEO_STREAM_SOURCE, 0);
        KeyManager.getInstance().setValue(key, videoStreamSource, new SetCallback(){
            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailure(DJIError djiError) {

            }

        });
    }
}
