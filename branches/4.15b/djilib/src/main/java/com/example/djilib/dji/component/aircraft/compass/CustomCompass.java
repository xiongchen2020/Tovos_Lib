package com.example.djilib.dji.component.aircraft.compass;

import com.example.djilib.dji.DJIContext;
import com.example.djilib.dji.component.aircraft.MyAircraftInterface;
import com.example.djilib.dji.component.aircraft.database.CustomCompassData;

import java.util.ArrayList;
import java.util.List;

import dji.common.error.DJIError;
import dji.common.flightcontroller.CompassCalibrationState;
import dji.common.flightcontroller.CompassState;
import dji.common.util.CommonCallbacks;
import dji.sdk.flightcontroller.Compass;

public class CustomCompass {
    private MyAircraftInterface myAircraftInterface = null;
    public int compassCount = 0;
    public Compass compass;
    public List<CustomCompassData> compassDataList = new ArrayList<>();

    public CustomCompass(MyAircraftInterface myAircraftInterface) {
        this.myAircraftInterface = myAircraftInterface;
        initCompass ();
        setCompassStateCallback();
        compassCount = getCompassCount();
    }

    public void initCompass (){
        if (DJIContext.getAircraftInstance()!=null) {
            if (DJIContext.getAircraftInstance().getFlightController() != null) {
                compass = DJIContext.getAircraftInstance().getFlightController().getCompass();
            }
        }
    }

    /**
     * 指南针数量
     * @return
     */
    public int getCompassCount(){
        int nums = 0;
        if (DJIContext.getAircraftInstance()!=null) {
            if (DJIContext.getAircraftInstance().getFlightController() != null) {
                nums = DJIContext.getAircraftInstance().getFlightController().getCompassCount();
            }
        }
        return nums;
    }

    /**
     * 指南针是否正在校准
     * @return
     */
    public boolean isCalibrating(){
        boolean isCalibrating = false;
        if (compass!=null) {
            isCalibrating = compass.isCalibrating();
        }
        return  isCalibrating;
    }

    /**
     * 开始校准指罗盘
     */
    public void startCalibration(){
        if (compass!=null){
            compass.startCalibration(new CommonCallbacks.CompletionCallback() {
                @Override
                public void onResult(DJIError djiError) {

                }
            });
        }
    }

    /**
     * 停止校准罗盘
     */
    public void stopCalibration(){
        if (compass!=null){
            compass.stopCalibration(new CommonCallbacks.CompletionCallback() {
                @Override
                public void onResult(DJIError djiError) {

                }
            });
        }
    }

    /**
     * 指南针校准回调
     */
    public void setCalibrationStateCallback(){
        if (compass!=null){
            compass.setCalibrationStateCallback(new CompassCalibrationState.Callback() {
                @Override
                public void onUpdate(CompassCalibrationState compassCalibrationState) {

                }
            });
        }
    }


    public void setCompassStateCallback(){
        if (compass!=null){
            compass.setCompassStateCallback(new CompassState.Callback() {
                @Override
                public void onUpdate(CompassState compassState) {
                    CustomCompassData customCompassData = new CustomCompassData();
                    customCompassData.setIndex(compassState.getIndex());
                    customCompassData.setSensorValue(compassState.getSensorValue());
                    boolean isadd = true;
                    if (compassDataList.size() > 0){
                        for (int i=0;i<compassDataList.size();i++){
                            if (customCompassData.getIndex() == compassDataList.get(i).getIndex()){
                                compassDataList.set(i,customCompassData);
                                isadd = false;
                            }
                        }
                    }

                    if (isadd){
                        if (compassState.getIndex()>=0){
                            compassDataList.add(customCompassData);
                        }

                    }

                    myAircraftInterface.getCompassList();
                }
            });
        }
    }

}
