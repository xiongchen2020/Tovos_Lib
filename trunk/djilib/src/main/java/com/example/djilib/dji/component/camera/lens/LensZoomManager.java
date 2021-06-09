package com.example.djilib.dji.component.camera.lens;

import android.graphics.PointF;

import com.example.commonlib.utils.ToastUtils;

import dji.common.camera.SettingsDefinitions;
import dji.common.error.DJIError;
import dji.common.util.CommonCallbacks;
import dji.sdk.camera.Lens;

public class LensZoomManager {

    private Lens camera;

    public LensZoomManager(Lens camera) {
        this.camera = camera;
    }

    /**
     * 检查镜头是否支持变焦，H20T和H20除外
     * @return
     */
    public boolean isDigitalZoomSupported(){
        if (camera!=null){
            return camera.isDigitalZoomSupported();
        }else {
            return false;
        }
    }

    /**
     * 调整数码变焦  H20 H20T用lens
     * @param var  1.0 - 2.0
     */
    public void setDigitalZoomFactor(float var){
        if (camera!=null){

            if (!isDigitalZoomSupported()){
                ToastUtils.setResultToToast("不支持变焦");
                return;
            }

            camera.setDigitalZoomFactor(var, new CommonCallbacks.CompletionCallback() {
                @Override
                public void onResult(DJIError djiError) {

                }
            });
        }
    }

    /**
     * 获取调焦参数
     */
    public void getDigitalZoomFactor(){
        if (camera!=null){
            camera.getDigitalZoomFactor(new CommonCallbacks.CompletionCallbackWith<Float>() {
                @Override
                public void onSuccess(Float aFloat) {

                }

                @Override
                public void onFailure(DJIError djiError) {

                }
            });
        }
    }

    /**
     * 镜头是否支持光学变焦
     * @return
     */
    public boolean isOpticalZoomSupported(){
        if (camera!=null){
            return camera.isOpticalZoomSupported();
        }else {
            return false;
        }
    }

    /**
     * 获取变焦镜头规格
     */
    public void getOpticalZoomSpec(){
        if (camera!=null){
            camera.getOpticalZoomSpec(new CommonCallbacks.CompletionCallbackWith<SettingsDefinitions.OpticalZoomSpec>() {
                @Override
                public void onSuccess(SettingsDefinitions.OpticalZoomSpec opticalZoomSpec) {

                }

                @Override
                public void onFailure(DJIError djiError) {

                }
            });
        }
    }

    /**
     * 设置光学变焦
     * @param num
     */
    public void setOpticalZoomFocalLength(int num){
        if (camera!=null){
            if (!isOpticalZoomSupported()){
                ToastUtils.setResultToToast("不支持光学变焦!");
                return;
            }

            camera.setOpticalZoomFocalLength(num, new CommonCallbacks.CompletionCallback() {
                @Override
                public void onResult(DJIError djiError) {

                }
            });
        }
    }

    /**
     * 获取光学变焦参数
     */
    public void getOpticalZoomFocalLength(){
        if (camera!=null){
            if (!isOpticalZoomSupported()){
                ToastUtils.setResultToToast("不支持光学变焦!");
                return;
            }

            camera.getOpticalZoomFocalLength(new CommonCallbacks.CompletionCallbackWith<Integer>() {
                @Override
                public void onSuccess(Integer integer) {

                }

                @Override
                public void onFailure(DJIError djiError) {

                }
            });
        }
    }


    /**
     * 在指定的焦距方向变焦
     * @param var
     * @param zoomSpeed
     */
    public void startContinuousOpticalZoom(SettingsDefinitions.ZoomDirection var,SettingsDefinitions.ZoomSpeed zoomSpeed){
        if (camera!=null){
            camera.startContinuousOpticalZoom(var, zoomSpeed, new CommonCallbacks.CompletionCallback() {
                @Override
                public void onResult(DJIError djiError) {

                }
            });
        }
    }

    /**
     * 停止变焦
     */
    public void stopContinuousOpticalZoom(){
        if (camera!=null){
            camera.stopContinuousOpticalZoom(new CommonCallbacks.CompletionCallback() {
                @Override
                public void onResult(DJIError djiError) {

                }
            });
        }
    }

    /**
     * 是否支持触摸放缩
     * @return
     */
    public boolean isTapZoomSupported(){
        if (camera!=null){
            return camera.isTapZoomSupported();
        }else {
            return false;
        }
    }

    /**
     * 启用关闭Tapzoom
     * @param isAble
     */
    public void setTapZoomEnabled(boolean isAble){

        if (camera!=null){

            if (!isTapZoomSupported())
                return;

            camera.setTapZoomEnabled(isAble, new CommonCallbacks.CompletionCallback() {
                @Override
                public void onResult(DJIError djiError) {

                }
            });
        }
    }

    /**
     * 获取TapZoom状态
     */
    public void getTapZoomEnabled(){


        if (camera!=null){

            if (!isTapZoomSupported())
                return;

            camera.getTapZoomEnabled(new CommonCallbacks.CompletionCallbackWith<Boolean>() {
                @Override
                public void onSuccess(Boolean aBoolean) {

                }

                @Override
                public void onFailure(DJIError djiError) {

                }
            });
        }
    }

    /**
     * 设置tapZoom放缩等级  1-5
     * @param var
     */
    public void setTapZoomMultiplier(int var){
        if (camera!=null){

            if (!isTapZoomSupported())
                return;

            camera.setTapZoomMultiplier(var, new CommonCallbacks.CompletionCallback() {
                @Override
                public void onResult(DJIError djiError) {

                }
            });
        }
    }

    /**
     * 获取相机当前TapZoom参数
     */
    public void getTapZoomMultiplier(){
        if (camera!=null){

            if (!isTapZoomSupported())
                return;

            camera.getTapZoomMultiplier(new CommonCallbacks.CompletionCallbackWith<Integer>() {
                @Override
                public void onSuccess(Integer integer) {

                }

                @Override
                public void onFailure(DJIError djiError) {

                }
            });
        }
    }

    public void tapZoomAtTarget(PointF var1){
        if (camera!=null){

            if (!isTapZoomSupported())
                return;

            camera.tapZoomAtTarget(var1, new CommonCallbacks.CompletionCallback() {
                @Override
                public void onResult(DJIError djiError) {

                }
            });
        }
    }

    public void setTapZoomStateCallback(){
        if (camera!=null){
            if (!isTapZoomSupported())
                return;

            camera.setTapZoomStateCallback(new Lens.TapZoomStateCallback(){
                @Override
                public void onUpdate(SettingsDefinitions.TapZoomState tapZoomState) {

                }
            });
        }
    }

    /**
     * 是否支持混合变焦
     * @return
     */
    public boolean isHybridZoomSupported(){
        if (camera!=null){
            return camera.isHybridZoomSupported();
        }else {
            return false;
        }
    }

    /**
     * 获取混合变焦参数
     */
    public void getHybridZoomSpec(){
        if (camera!=null){
            if (! isHybridZoomSupported())
                return;

            camera.getHybridZoomSpec(new CommonCallbacks.CompletionCallbackWith<SettingsDefinitions.HybridZoomSpec>() {
                @Override
                public void onSuccess(SettingsDefinitions.HybridZoomSpec hybridZoomSpec) {

                }

                @Override
                public void onFailure(DJIError djiError) {

                }
            });
        }
    }

    /**
     * 设置混合变焦值
     * @param var
     */
    public void setHybridZoomFocalLength(int var){
        if (camera!=null){
            if (!isHybridZoomSupported())
                return;

            camera.setHybridZoomFocalLength(var, new CommonCallbacks.CompletionCallback() {
                @Override
                public void onResult(DJIError djiError) {

                }
            });
        }
    }

    /**
     * 获取光学变焦值
     */
    public void getHybridZoomFocalLength(){
        if (camera!=null){
            if (! isHybridZoomSupported())
                return;

            camera.getHybridZoomFocalLength(new CommonCallbacks.CompletionCallbackWith<Integer>() {
                @Override
                public void onSuccess(Integer integer) {

                }

                @Override
                public void onFailure(DJIError djiError) {

                }
            });
        }
    }



}
