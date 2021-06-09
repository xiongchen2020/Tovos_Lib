package com.example.djilib.dji.component.gimbal;

import dji.common.error.DJIError;
import dji.common.gimbal.Axis;
import dji.common.gimbal.GimbalState;

public interface GimbalListener {

    void getPitchRangeExtensionEnabled(boolean aBoolean);
    void getPitchRangeExtensionEnabledFailure(DJIError djiError);
    void setPitchRangeExtensionEnabledResult(DJIError djiError);

    void updateState(GimbalState gimbalState);
    void setGimbalMode(DJIError djiError);

    void getControllerSmoothingFactor(Axis axis, Integer integer);
    void getControllerSmoothingFactorFailure(Axis axis,DJIError djiError);
    void setControllerSmoothingFactor(Axis axis,DJIError djiError);

    void getControllerMaxSpeed(Axis axis, Integer integer);
    void getControllerMaxSpeedFailure(Axis axis,DJIError djiError);
    void setControllerMaxSpeed(Axis axis,DJIError djiError);
}
