package com.example.djilib.dji.djierror;

public enum  RtkNewWorkInfo {
    UNKNOWN("UNKNOWN","未知错误！"),
    INVALID_REQUEST("INVALID_REQUEST","服务器拒绝了无效请求。"),
    SERVICE_SUSPENSION("SERVICE_SUSPENSION","RTK账户计划已经过期，请激活！"),
    CONNECTING("CONNECTING","正在连接服务"),
    SERVER_NOT_REACHABLE("SERVER_NOT_REACHABLE","无法连接服务"),
    ACCOUNT_ERROR("ACCOUNT_ERROR","RTK账户错误"),
    DISCONNECTED("DISCONNECTED","RTK连接断开"),
    TRANSMITTING("TRANSMITTING","RTK连接成功"),
    LOGIN_FAILURE("LOGIN_FAILURE","RTK无法获取登录账号和密码"),
    AIRCRAFT_DISCONNECTED("AIRCRAFT_DISCONNECTED","飞机断开连接。"),
    NETWORK_NOT_REACHABLE("NETWORK_NOT_REACHABLE","无法从移动设备访问网络。"),
    DISABLED("DISABLED","未连接");

    String code;
    String error;

    RtkNewWorkInfo(String code, String error) {
        this.code = code;
        this.error = error;
    }
    public static  String getError(String mcode) {

        for (RtkNewWorkInfo e : RtkNewWorkInfo.values()) {
            if (e.code == mcode) {
                return e.error;
            }
        }
        return "RTK未连接";

    }

}
