package com.example.djilib.dji.manager;

import com.dji.mapkit.core.models.DJILatLng;
import com.example.commonlib.utils.ToastUtils;

import java.util.ArrayList;

import dji.common.error.DJIError;
import dji.common.flightcontroller.flyzone.FlyZoneInformation;
import dji.common.util.CommonCallbacks;
import dji.sdk.flightcontroller.FlyZoneManager;
import dji.sdk.sdkmanager.DJISDKManager;

public class CustomFlyZoneManger {

    private FlyZoneManager flyZoneManager;

    public CustomFlyZoneManger(){
        initFlyZoneManager();
        getFlyZoneData();
    }

    public void initFlyZoneManager(){
        if (DJISDKManager.getInstance() !=null){
            if (DJISDKManager.getInstance().getFlyZoneManager()!=null){
                flyZoneManager = DJISDKManager.getInstance().getFlyZoneManager();
            }
        }
    }

    public void getFlyZoneData(){
        if (flyZoneManager!=null){
            flyZoneManager.getFlyZonesInSurroundingArea(new CommonCallbacks.CompletionCallbackWith<ArrayList<FlyZoneInformation>>() {
                @Override
                public void onSuccess(ArrayList<FlyZoneInformation> flyZoneInformations) {
                    for (FlyZoneInformation flyZoneInformation:flyZoneInformations){
                        //ToastUtils.setResultToToast("item info:"+flyZoneInformation.getFlyZoneType());
                    }
                }

                @Override
                public void onFailure(DJIError djiError) {

                }
            });
        }
    }

    public void getFlyZoneDataByLocation(DJILatLng djiLatLng){
        if (flyZoneManager!=null){
            flyZoneManager.getFlyZonesInSurroundingArea(djiLatLng.altitude, djiLatLng.longitude, new CommonCallbacks.CompletionCallbackWith<ArrayList<FlyZoneInformation>>() {
                @Override
                public void onSuccess(ArrayList<FlyZoneInformation> flyZoneInformations) {

                }

                @Override
                public void onFailure(DJIError djiError) {

                }
            });
        }
    }
}
