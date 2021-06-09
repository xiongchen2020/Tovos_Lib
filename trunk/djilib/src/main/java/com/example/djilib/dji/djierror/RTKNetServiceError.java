package com.example.djilib.dji.djierror;

import dji.common.error.DJIRTKNetworkServiceError;

public enum RTKNetServiceError {
    AUTHENTICATION_FAILURE(DJIRTKNetworkServiceError.AUTHENTICATION_FAILURE.getErrCode(),"尝试访问服务器时身份验证失败"),
    INVALID_SETTINGS(DJIRTKNetworkServiceError.INVALID_SETTINGS.getErrCode(),"网络设置尚未配置或无效"),
    ALREADY_STARTED(DJIRTKNetworkServiceError.ALREADY_STARTED.getErrCode(),"该服务已经启动。首先停止它以重新启动服务。"),
    INVALID_GPS_DATA(DJIRTKNetworkServiceError.INVALID_GPS_DATA.getErrCode(),"飞机的GPS位置无效。RTK网络服务需要空中系统的位置。"),
    INCORRECT_REFERENCE_STATION_SOURCE(DJIRTKNetworkServiceError.INCORRECT_REFERENCE_STATION_SOURCE.getErrCode(),"请选择正确的基准站位。"),
    ACCOUNT_NOT_LOGGED_IN_OR_EXPIRED(DJIRTKNetworkServiceError.ACCOUNT_NOT_LOGGED_IN_OR_EXPIRED.getErrCode(),"没有登录帐户或登录会话已过期。"),
    ACCOUNT_UNACTIVATED(DJIRTKNetworkServiceError.ACCOUNT_UNACTIVATED.getErrCode(),"帐号未激活，请联系技术支持。"),
    INVALID_REQUEST(DJIRTKNetworkServiceError.INVALID_REQUEST.getErrCode(),"对RTK服务的请求无效。请检查是否购买或激活了网络RTK服务。"),
    SERVER_NOT_REACHABLE(DJIRTKNetworkServiceError.SERVER_NOT_REACHABLE.getErrCode(),"无法连接到服务器。"),
    ACCOUNT_ERROR(DJIRTKNetworkServiceError.ACCOUNT_ERROR.getErrCode(),"未知帐户错误，请联系技术支持。");


    int code;
    String error;

    RTKNetServiceError(int code, String error) {
        this.code = code;
        this.error = error;
    }
    public static  String getError(int code) {

        for (RTKNetServiceError e : RTKNetServiceError.values()) {
            if (e.code == code) {
                return e.error;
            }
        }
        return "RTK未连接";

    }


}
