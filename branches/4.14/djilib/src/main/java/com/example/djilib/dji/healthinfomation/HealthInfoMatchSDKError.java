package com.example.djilib.dji.healthinfomation;

public class HealthInfoMatchSDKError {
    private String alarmId;
    private String sdkErrorCode;
    private String sdkErrorEnumName;

    public String getAlarmId() {
        return alarmId;
    }

    public void setAlarmId(String alarmId) {
        this.alarmId = alarmId;
    }

    public String getSdkErrorCode() {
        return sdkErrorCode;
    }

    public void setSdkErrorCode(String sdkErrorCode) {
        this.sdkErrorCode = sdkErrorCode;
    }

    public String getSdkErrorEnumName() {
        return sdkErrorEnumName;
    }

    public void setSdkErrorEnumName(String sdkErrorEnumName) {
        this.sdkErrorEnumName = sdkErrorEnumName;
    }
}
