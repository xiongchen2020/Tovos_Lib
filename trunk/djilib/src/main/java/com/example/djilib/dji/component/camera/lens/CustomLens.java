package com.example.djilib.dji.component.camera.lens;

import android.util.Log;

import com.example.commonlib.utils.ToastUtils;

import dji.common.camera.SettingsDefinitions;
import dji.common.error.DJIError;
import dji.common.util.CommonCallbacks;
import dji.internal.logics.CommonUtil;
import dji.keysdk.CameraKey;
import dji.keysdk.DJIKey;
import dji.keysdk.KeyManager;
import dji.keysdk.callback.ActionCallback;
import dji.sdk.camera.Lens;

public class CustomLens {

    private Lens lens;
    public LensZoomManager zoomManager;
    private LensListener lensListener;
    public CustomLens(Lens lens,LensListener lensListener) {
        this.lens = lens;
        this.lensListener = lensListener;
        zoomManager = new LensZoomManager(lens);
    }

    public SettingsDefinitions.LensType getLensType(){
        return lens.getType();
    }

    public int getCameraIndex(){
        return lens.getCameraIndex();
    }

    public int getIndex(){
        return lens.getIndex();
    }

    public String getDisplayName(){
        return lens.getDisplayName();
    }

    public void getCapabilities(){
        lens.getCapabilities();
    }


    /**
     * 设置测温兴趣区域
     * @param var1
     */
    public void setThermalROI(SettingsDefinitions.ThermalROI var1){
        lens.setThermalROI(var1, new CommonCallbacks.CompletionCallback() {
            @Override
            public void onResult(DJIError djiError) {

            }
        });
    }

    /**
     * 获取测温兴趣区域
     */
    public void getThermalROI(){
        lens.getThermalROI(new CommonCallbacks.CompletionCallbackWith<SettingsDefinitions.ThermalROI>() {
            @Override
            public void onSuccess(SettingsDefinitions.ThermalROI thermalROI) {

            }

            @Override
            public void onFailure(DJIError djiError) {

            }
        });
    }

    /**
     * 设置温度区域调色板
     */
    public void setThermalPalette(SettingsDefinitions.ThermalPalette var1){
        lens.setThermalPalette(var1, new CommonCallbacks.CompletionCallback() {
            @Override
            public void onResult(DJIError djiError) {

            }
        });
    }

    /**
     * 获取温度区域调色板
     */
    public void getThermalPalette(){
        lens.getThermalPalette(new CommonCallbacks.CompletionCallbackWith<SettingsDefinitions.ThermalPalette>() {
            @Override
            public void onSuccess(SettingsDefinitions.ThermalPalette thermalPalette) {

            }

            @Override
            public void onFailure(DJIError djiError) {

            }
        });
    }

    /**
     * 设置场景以立即增强图像
     * @param var1
     */
    public void setThermalScene(SettingsDefinitions.ThermalScene var1){
        lens.setThermalScene(var1, new CommonCallbacks.CompletionCallback() {
            @Override
            public void onResult(DJIError djiError) {

            }
        });
    }

    public void getThermalScene(){
        lens.getThermalPalette(new CommonCallbacks.CompletionCallbackWith<SettingsDefinitions.ThermalPalette>() {
            @Override
            public void onSuccess(SettingsDefinitions.ThermalPalette thermalPalette) {

            }

            @Override
            public void onFailure(DJIError djiError) {

            }
        });
    }

    public void setDisplayMode(SettingsDefinitions.DisplayMode var1){
        lens.setDisplayMode(var1, new CommonCallbacks.CompletionCallback() {
            @Override
            public void onResult(DJIError djiError) {

            }
        });
    }

    public void getDisplayMode(){
        lens.getDisplayMode(new CommonCallbacks.CompletionCallbackWith<SettingsDefinitions.DisplayMode>() {
            @Override
            public void onSuccess(SettingsDefinitions.DisplayMode displayMode) {
                lensListener.setThermal(displayMode);
            }

            @Override
            public void onFailure(DJIError djiError) {

                ToastUtils.setResultToToast("设置红外镜头模式失败："+djiError.getDescription());
            }
        });
    }

    public void setThermal(){
        int lensindex =  CommonUtil.getLensIndex(0, SettingsDefinitions.LensType.INFRARED_THERMAL);
        DJIKey key = CameraKey.createLensKey(CameraKey.INFRARED_THERMAL_CAMERA_SHUTTER_ENABLED, 0, lensindex);
        KeyManager.getInstance().performAction(key, new ActionCallback() {
            @Override
            public void onSuccess() {

                Log.e(this.getClass().getSimpleName(),"红外镜头切换成功!");
                getDisplayMode();
            }

            @Override
            public void onFailure(DJIError djiError) {
                Log.e(this.getClass().getSimpleName(),"红外镜头切换失败："+djiError.getDescription());
            }
        });
    }

    public void setZoom(){
        int i =  CommonUtil.getLensIndex(0, SettingsDefinitions.LensType.ZOOM);
        DJIKey key = CameraKey.createLensKey(CameraKey.HYBRID_ZOOM_SPEC, 0, i);
        KeyManager.getInstance().performAction(key, new ActionCallback() {
            @Override
            public void onSuccess() {

                Log.e(this.getClass().getSimpleName(),"变焦镜头切换成功!");
            }

            @Override
            public void onFailure(DJIError djiError) {
                Log.e(this.getClass().getSimpleName(),"变焦镜头切换失败："+djiError.getDescription());
            }
        });
    }

}
