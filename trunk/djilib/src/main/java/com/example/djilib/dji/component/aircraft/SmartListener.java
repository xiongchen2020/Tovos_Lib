package com.example.djilib.dji.component.aircraft;

import dji.common.error.DJIError;

public interface SmartListener {

    void getLowBatteryWarningThreshold(Integer integer);

    void getLowBatteryWarningThresholdError(DJIError djiError);

    void setLowBatteryWarningThreshold(DJIError djiError);

    void getSeriousLowBatteryWarningThreshold(Integer integer);

    void getSeriousLowBatteryWarningThresholdError(DJIError djiError);

    void setSeriousLowBatteryWarningThreshold(DJIError djiError);

    void confirmSmartReturnToHomeRequest(DJIError djiError);

    void getSmartReturnToHomeEnabled(Boolean aBoolean);

    void getSmartReturnToHomeEnabledError(DJIError djiError);

    void setSmartReturToHomeEnable(Boolean enable, DJIError djiError);
}
