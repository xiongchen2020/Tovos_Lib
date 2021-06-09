package com.example.djilib.dji.component.aircraft.flightAssistant;

import com.example.commonlib.utils.ToastUtils;
import com.example.djilib.dji.DJIContext;

import dji.common.error.DJIError;
import dji.common.flightcontroller.flightassistant.ObstacleAvoidanceSensorState;
import dji.common.flightcontroller.flightassistant.PerceptionInformation;
import dji.common.product.Model;
import dji.common.util.CommonCallbacks;
import dji.sdk.flightcontroller.FlightAssistant;
import dji.sdk.sdkmanager.DJISDKManager;

public class CustomFlightAssistant {
    FlightAssistant flightAssistant;
    CustomFlightAssistantListener  myAircraftInterface;
    public  boolean collisionAvoidanceEnabled;
    public  boolean visionAssistedPositioningEnabled;
    public  boolean rthObstacleAvoidanceEnabled;
    public  boolean precisionLandingEnabled;
    public  boolean isSbz = false;
    public  boolean isXbz = false;//水平避障
    public  boolean isLbz = false;//下避障
    public  boolean isArroundBz = false;
    public CustomFlightAssistant(CustomFlightAssistantListener myAircraftInterface){
        this.myAircraftInterface = myAircraftInterface;
        getFlightAssistant();
        setObstacleAvoidanceSensorStateListener();
    }

    /**
     * 获取飞行助理
     * @return
     */
    public FlightAssistant getFlightAssistant(){
        flightAssistant = null;

        if (DJIContext.getAircraftInstance() != null) {
            if (DJIContext.getAircraftInstance().getFlightController() != null) {

                flightAssistant = DJIContext.getAircraftInstance().getFlightController().getFlightAssistant();

            }
        }
        return flightAssistant;
    }


    /**
     * 获得避碰状态
     */
    public void  getCollisionAvoidanceEnable(){
        if (flightAssistant != null){
            flightAssistant.getCollisionAvoidanceEnabled(new CommonCallbacks.CompletionCallbackWith<Boolean>() {
                @Override
                public void onSuccess(Boolean aBoolean) {
                    collisionAvoidanceEnabled = aBoolean;
                    if (DJISDKManager.getInstance().getProduct().getModel() == Model.MATRICE_300_RTK){
                        myAircraftInterface.setActiveObstacleAvoidanceEnabled(aBoolean);
                    }else {
                        myAircraftInterface.getCollisionAvoidanceEnabled(aBoolean);
                    }


                }

                @Override
                public void onFailure(DJIError djiError) {
                    collisionAvoidanceEnabled = false;
                    if (DJISDKManager.getInstance().getProduct().getModel() == Model.MATRICE_300_RTK){
                        myAircraftInterface.setActiveObstacleAvoidanceEnabled(false);
                    }else {
                        myAircraftInterface.getCollisionAvoidanceEnabled(false);
                    }
                    myAircraftInterface.getFlightAssistantFailure(djiError);

                }
            });
        }
    }

    /**
     * 设置避障状态
     * @param enabled
     */
    public void setCollisionAvoidanceEnabled(final Boolean enabled){
        if (flightAssistant != null){
            flightAssistant.setCollisionAvoidanceEnabled(enabled, new CommonCallbacks.CompletionCallback() {
                @Override
                public void onResult(DJIError djiError) {
                    if (djiError != null) {
                        ToastUtils.setResultToToast("避障置错误:"+djiError);
                    }else {
                        collisionAvoidanceEnabled = enabled;
                        getCollisionAvoidanceEnable();
                    }
                    myAircraftInterface.setCollisionAvoidanceEnabled(djiError);
                }
            });
        }
    }


    /**
     * 获取视觉定位状态
     */
    public void getVisionAssistedPositioningEnable(){

        if (flightAssistant != null){

            flightAssistant.getVisionAssistedPositioningEnabled(new CommonCallbacks.CompletionCallbackWith<Boolean>() {
                @Override
                public void onSuccess(Boolean aBoolean) {
                    myAircraftInterface.getVisionAssistedPositioningEnabled(aBoolean);
                    visionAssistedPositioningEnabled = aBoolean;

                }

                @Override
                public void onFailure(DJIError djiError) {
                    myAircraftInterface.getFlightAssistantFailure(djiError);
                    visionAssistedPositioningEnabled = false;

                }
            });
        }
    }

    public void setVisionAssistedPositioningEnable(final Boolean enable){
        if (flightAssistant != null) {
            flightAssistant.setVisionAssistedPositioningEnabled(enable, new CommonCallbacks.CompletionCallback() {
                @Override
                public void onResult(DJIError djiError) {
                    visionAssistedPositioningEnabled = enable;
                    myAircraftInterface.setVisionAssistedPositioningEnabled(djiError);

                }
            });
        }
    }


    /**
     * 确定在RTH期间是否启用了避障
     */
    public void getRTHObstraleAvoidanceEnable(){


        if (flightAssistant != null){
            flightAssistant.getRTHObstacleAvoidanceEnabled(new CommonCallbacks.CompletionCallbackWith<Boolean>() {
                @Override
                public void onSuccess(Boolean aBoolean) {
                    myAircraftInterface.getRTHObstacleAvoidanceEnabled(aBoolean);
                    rthObstacleAvoidanceEnabled = aBoolean;

                }

                @Override
                public void onFailure(DJIError djiError) {
                    myAircraftInterface.getFlightAssistantFailure(djiError);
                    rthObstacleAvoidanceEnabled = false;

                }
            });
        }
    }

    public void setRTHObstacleAvoidanceEnabled(final Boolean enabled){
        if (flightAssistant != null){
            flightAssistant.setRTHObstacleAvoidanceEnabled(enabled, new CommonCallbacks.CompletionCallback() {
                @Override
                public void onResult(DJIError djiError) {
                    rthObstacleAvoidanceEnabled = enabled;
                    myAircraftInterface.setRTHObstacleAvoidanceEnabled(djiError);
                }
            });
        }
    }

    /**
     * 开启关闭水平避障状态
     * @param isable
     */
    public void setHorizontalVisionObstacleAvoidanceEnabled(boolean isable){
        if (flightAssistant!=null){
            flightAssistant.setHorizontalVisionObstacleAvoidanceEnabled(isable, new CommonCallbacks.CompletionCallback() {
                @Override
                public void onResult(DJIError djiError) {
                    if (djiError != null) {

                        ToastUtils.setResultToToast("水平避障设置错误:"+djiError);
                    }else {
                        getHorizontalVisionObstacleAvoidanceEnabled();
                    }
                }
            });
        }
    }

    /**
     * 获取水平避障状态
     */
    public void getHorizontalVisionObstacleAvoidanceEnabled(){
        if (flightAssistant!=null){

            flightAssistant.getHorizontalVisionObstacleAvoidanceEnabled(new CommonCallbacks.CompletionCallbackWith<Boolean>() {
                @Override
                public void onSuccess(Boolean aBoolean) {

                    isXbz = aBoolean;
                    myAircraftInterface.setDownwardAvoidanceEnabled(aBoolean);
                }

                @Override
                public void onFailure(DJIError djiError) {
                    isXbz = false;
                    ToastUtils.setResultToToast("水平避障失败:"+djiError);
                    myAircraftInterface.setDownwardAvoidanceEnabled(false);
                }
            });
        }
    }

    /**
     * 开启关闭上避障
     * @param isable
     */
    public void setUpwardVisionObstacleAvoidanceEnabled(boolean isable){
        if (flightAssistant!=null){
            flightAssistant.setUpwardVisionObstacleAvoidanceEnabled(isable, new CommonCallbacks.CompletionCallback() {
                @Override
                public void onResult(DJIError djiError) {
                    if (djiError != null) {
                        ToastUtils.setResultToToast("上避障设置错误:"+djiError);
                    }else {
                        getUpwardVisionObstacleAvoidanceEnabled();
                    }
                }
            });
        }
    }

    public void getUpwardVisionObstacleAvoidanceEnabled(){
        if (flightAssistant!=null){
            flightAssistant.getUpwardVisionObstacleAvoidanceEnabled(new CommonCallbacks.CompletionCallbackWith<Boolean>() {
                @Override
                public void onSuccess(Boolean aBoolean) {
                    isSbz = aBoolean;
                    myAircraftInterface.setUpwardAvoidanceEnabled(aBoolean);
                }

                @Override
                public void onFailure(DJIError djiError) {
                    isSbz = false;
                    ToastUtils.setResultToToast("上避障设置错误:"+djiError);
                    myAircraftInterface.setUpwardAvoidanceEnabled(false);
                }
            });
        }
    }
    /**
     * 开启关闭下避障/保护着陆
     * @param isable
     */
    public void setLandingProtectionEnabled(boolean isable){
        if (flightAssistant!=null){
            flightAssistant.setLandingProtectionEnabled(isable, new CommonCallbacks.CompletionCallback() {
                @Override
                public void onResult(DJIError djiError) {
                    if (djiError != null) {
                        ToastUtils.setResultToToast("下避障设置错误:"+djiError);
                    }else {
                        getLandingProtectionEnabled();
                    }
                }
            });
        }
    }

    public void getLandingProtectionEnabled(){
        if (flightAssistant!=null){
            flightAssistant.getLandingProtectionEnabled(new CommonCallbacks.CompletionCallbackWith<Boolean>() {
                @Override
                public void onSuccess(Boolean aBoolean) {
                    isLbz = aBoolean;
                    myAircraftInterface.setLandingProtectionEnabled(aBoolean);
                }

                @Override
                public void onFailure(DJIError djiError) {
                    isLbz = false;
                    ToastUtils.setResultToToast("下避障设置错误:"+djiError);
                    myAircraftInterface.setLandingProtectionEnabled(false);
                }
            });
        }
    }

    /**
     * 主动避障
     * @param isable
     */
    public void setActiveObstacleAvoidanceEnabled(boolean isable){
        if (flightAssistant!=null){
            flightAssistant.setActiveObstacleAvoidanceEnabled(isable, new CommonCallbacks.CompletionCallback() {
                @Override
                public void onResult(DJIError djiError) {
                    if (djiError != null) {
                        ToastUtils.setResultToToast("主动避障置错误:"+djiError);
                    }else {
                        getActiveObstacleAvoidanceEnabled();
                    }
                }
            });
        }
    }

    /**
     * 获取主动避障状态
     */
    public void getActiveObstacleAvoidanceEnabled(){
        if (flightAssistant!=null){
            flightAssistant.getActiveObstacleAvoidanceEnabled(new CommonCallbacks.CompletionCallbackWith<Boolean>() {
                @Override
                public void onSuccess(Boolean aBoolean) {

                    isArroundBz = aBoolean;
                    myAircraftInterface.setActiveObstacleAvoidanceEnabled(aBoolean);
                }

                @Override
                public void onFailure(DJIError djiError) {
                    isArroundBz = false;
                    ToastUtils.setResultToToast("主动避障置错误:"+djiError);
                    myAircraftInterface.setActiveObstacleAvoidanceEnabled(false);
                }
            });
        }
    }

    /**
     * 设置警告距离
     * @param var
     * @param flightAssistantObstacleSensingDirection
     */
    public void setMaxPerceptionDistance(float var, final PerceptionInformation.DJIFlightAssistantObstacleSensingDirection flightAssistantObstacleSensingDirection){
        if (flightAssistant!=null){
            flightAssistant.setMaxPerceptionDistance(var, flightAssistantObstacleSensingDirection, new CommonCallbacks.CompletionCallback() {
                @Override
                public void onResult(DJIError djiError) {
                    if (djiError != null) {
                        ToastUtils.setResultToToast("警告距离设置错误:"+djiError);
                    }else {
                        getMaxPerceptionDistance(flightAssistantObstacleSensingDirection);
                    }
                }
            });
        }
    }

    /**
     * 获取避障距离
     * @param flightAssistantObstacleSensingDirection
     */
    public void getMaxPerceptionDistance(final PerceptionInformation.DJIFlightAssistantObstacleSensingDirection flightAssistantObstacleSensingDirection){
        if (flightAssistant!=null){
            flightAssistant.getMaxPerceptionDistance(flightAssistantObstacleSensingDirection, new CommonCallbacks.CompletionCallbackWith<Float>() {
                @Override
                public void onSuccess(Float aFloat) {

                    myAircraftInterface.setMaxPerceptionDistance(aFloat,flightAssistantObstacleSensingDirection);
                }

                @Override
                public void onFailure(DJIError djiError) {
                    ToastUtils.setResultToToast("警告距离获取错误:"+djiError);
                    myAircraftInterface.setMaxPerceptionDistance(0.0f,flightAssistantObstacleSensingDirection);
                }
            });
        }
    }

    public void setObstaclesAvoidanceDistance(float var1, final PerceptionInformation.DJIFlightAssistantObstacleSensingDirection var2){
        if (flightAssistant != null){
            flightAssistant.setVisualObstaclesAvoidanceDistance(var1, var2, new CommonCallbacks.CompletionCallback() {
                @Override
                public void onResult(DJIError djiError) {

                    if (djiError != null){
                        ToastUtils.setResultToToast("设置避障距离错误:"+djiError.getDescription());
                    }else {
                        getObstaclesAvoidanceDistance(var2);
                    }
                }
            });
        }
    }

    public void getObstaclesAvoidanceDistance(final PerceptionInformation.DJIFlightAssistantObstacleSensingDirection var1){
        if (flightAssistant!=null){
            flightAssistant.getVisualObstaclesAvoidanceDistance(var1, new CommonCallbacks.CompletionCallbackWith<Float>() {
                @Override
                public void onSuccess(Float aFloat) {
                    myAircraftInterface.getObstaclesAvoidanceDistance(aFloat,var1);
                }

                @Override
                public void onFailure(DJIError djiError) {
                    ToastUtils.setResultToToast("error:"+djiError.getDescription());
                    myAircraftInterface.getObstaclesAvoidanceDistance(1.0f,var1);
                }
            });
        }
    }


    public void setObstacleAvoidanceSensorStateListener(){

        if (flightAssistant!=null){

            flightAssistant.setObstacleAvoidanceSensorStateListener(new CommonCallbacks.CompletionCallbackWith<ObstacleAvoidanceSensorState>() {
                @Override
                public void onSuccess(ObstacleAvoidanceSensorState obstacleAvoidanceSensorState) {
                    myAircraftInterface.setObstacleAvoidanceSensorState(obstacleAvoidanceSensorState);
                }

                @Override
                public void onFailure(DJIError djiError) {

                }
            });
        }
    }

    /**
     * 精准着陆
     */
    public void getPrecisionLandingEnabled(){
        if (flightAssistant!=null){
            flightAssistant.getPrecisionLandingEnabled(new CommonCallbacks.CompletionCallbackWith<Boolean>() {
                @Override
                public void onSuccess(Boolean aBoolean) {
                    precisionLandingEnabled = aBoolean;
                    myAircraftInterface.getPrecisionLandingEnabled(aBoolean);
                }

                @Override
                public void onFailure(DJIError djiError) {
                    precisionLandingEnabled = false;
                    myAircraftInterface.getPrecisionLandingEnabledFailure(djiError);
                }
            });
        }
    }
    public void setPrecisionLandingEnabled(final boolean b){
        if (flightAssistant!=null){
            flightAssistant.setPrecisionLandingEnabled(b, new CommonCallbacks.CompletionCallback() {
                @Override
                public void onResult(DJIError djiError) {
                    precisionLandingEnabled = b;
                    myAircraftInterface.setPrecisionLandingEnabled(djiError);
                }
            });
        }
    }


}
