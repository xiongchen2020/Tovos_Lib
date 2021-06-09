package com.example.djilib.dji.component.remote;

import androidx.annotation.NonNull;

import com.example.commonlib.utils.ToastUtils;
import com.example.djilib.dji.DJIContext;

import dji.common.error.DJIError;
import dji.common.remotecontroller.AircraftMappingStyle;
import dji.common.remotecontroller.GPSData;
import dji.common.remotecontroller.PairingDevice;
import dji.common.remotecontroller.PairingState;
import dji.common.remotecontroller.RCMode;
import dji.common.util.CommonCallbacks;
import dji.keysdk.BatteryKey;
import dji.keysdk.DJIKey;
import dji.keysdk.KeyManager;
import dji.keysdk.RemoteControllerKey;
import dji.sdk.products.Aircraft;
import dji.sdk.remotecontroller.RemoteController;

public class RemoteManger {

    private RemoteController remoteController;
    public RemoteListener remoteListener;
    public RemoteManger(RemoteListener remoteListener){
        this.remoteListener = remoteListener;
        initRemoteManger();
        getMasterSlaveMode();
        getPairingState();
    }

    private void initRemoteManger(){

        if (DJIContext.getProductInstance() != null) {
            remoteController = ((Aircraft) DJIContext.getProductInstance()).getRemoteController();
        }

    }

    public RemoteController getRemoteController() {
        return remoteController;
    }
    /**
     * 获取摇杆模式
     */
    public void getAircraftMappingStyle(){
        if (remoteController != null){
            remoteController.getAircraftMappingStyle(new CommonCallbacks.CompletionCallbackWith<AircraftMappingStyle>() {
                @Override
                public void onSuccess(AircraftMappingStyle aircraftMappingStyle) {
                    remoteListener.getMappingStyle(aircraftMappingStyle);
                }

                @Override
                public void onFailure(DJIError djiError) {
                    remoteListener.setMappingStyle(djiError);
                }
            });
        }
    }
    /**
     * 设置摇杆模式
     * @param style
     */
    public void setAircraftMappingStyle(AircraftMappingStyle style){
       // initRemoteManger();
        if (remoteController != null){


            remoteController.setAircraftMappingStyle(style, new CommonCallbacks.CompletionCallback() {
                @Override
                public void onResult(DJIError djiError) {
                    remoteListener.setMappingStyle(djiError);
                }
            });
        }
    }

    public boolean isMasterSlaveModeSupported(){
        if (remoteController!=null){
            return remoteController.isMasterSlaveModeSupported();
        }else {
            return false;
        }
    }


    public void getMasterSlaveMode(){
        if (remoteController!=null){
            remoteController.getMode(new CommonCallbacks.CompletionCallbackWith<RCMode>() {
                @Override
                public void onSuccess(RCMode rcMode) {


                    remoteListener.getMasterSlaveMode(rcMode);
                }

                @Override
                public void onFailure(DJIError djiError) {

                }
            });
        }
    }

    public void setMasterSlaveMode(RCMode rcMode){
        if (remoteController!=null){
            remoteController.setMode(rcMode, new CommonCallbacks.CompletionCallback() {
                @Override
                public void onResult(DJIError djiError) {
                    remoteListener.setMasterSlaveMode(djiError);
                }
            });
        }
    }

    /**
     * 遥控器对频
     */
    public void startPairingRemoteControl(){
        if (remoteController!=null){
            remoteController.startPairing(new CommonCallbacks.CompletionCallback() {
                @Override
                public void onResult(DJIError djiError) {

                    if (djiError == null){
                        remoteListener.startPairing();
                    }else {
                        ToastUtils.setResultToToast("对频失败:"+djiError.getDescription());
                    }
                }
            });

        }
    }

    public void getPairingState(){
        if (remoteController!=null){
            remoteController.getPairingState(new CommonCallbacks.CompletionCallbackWith<PairingState>() {
                @Override
                public void onSuccess(PairingState pairingState) {
                    //ToastUtils.setResultToToast("对频值:"+pairingState.value());
                    if (pairingState == PairingState.UNPAIRED){
                    }else if (pairingState == PairingState.PAIRING){
                    }else if (pairingState == PairingState.PAIRED){

                    }else if (pairingState == PairingState.UNKNOWN){

                    }
                }

                @Override
                public void onFailure(DJIError djiError) {
                    ToastUtils.setResultToToast("对频失败:"+djiError.getDescription());
                }
            });
        }
    }

    public void stopPairingRemoteControl(){
        if (remoteController!=null){
            remoteController.stopPairing(new CommonCallbacks.CompletionCallback() {
                @Override
                public void onResult(DJIError djiError) {

                    remoteListener.stopParing();
                }
            });
        }
    }

    public boolean isMultiDevicePairingSupported(){
        boolean isMultiDevicePairingSupported = false;
        if (remoteController!=null){
            isMultiDevicePairingSupported =  remoteController.isMultiDevicePairingSupported();
        }
        return isMultiDevicePairingSupported;
    }

    public void startMultiDevicePairing(PairingDevice device){
        if (remoteController!=null){
            remoteController.startMultiDevicePairing(device, new CommonCallbacks.CompletionCallback() {
                @Override
                public void onResult(DJIError djiError) {
                    remoteListener.startMultiDevicePairing(djiError);
                }
            });
        }
    }
    public void stopMultiDevicePairing(){
        if (remoteController!=null){
            remoteController.stopMultiDevicePairing( new CommonCallbacks.CompletionCallback() {
                @Override
                public void onResult(DJIError djiError) {
                    remoteListener.stopMultiDevicePairing(djiError);
                }
            });
        }
    }
    public void setRTKChannelEnabled(boolean b){
        if (remoteController!=null){
            remoteController.setRTKChannelEnabled(b, new CommonCallbacks.CompletionCallback() {
                @Override
                public void onResult(DJIError djiError) {
                    remoteListener.stopMultiDevicePairing(djiError);
                }
            });
        }
    }

    public void getGPSData(){


        if (remoteController!=null) {
            remoteController.setGPSDataCallback(new GPSData.Callback() {
                @Override
                public void onUpdate(@NonNull GPSData gpsData) {
                    remoteListener.setGPSData(gpsData);
                }
            });
        }
    }
}
