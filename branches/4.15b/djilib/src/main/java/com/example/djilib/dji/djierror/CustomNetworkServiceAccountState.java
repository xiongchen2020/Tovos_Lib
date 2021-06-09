package com.example.djilib.dji.djierror;

import dji.common.flightcontroller.rtk.NetworkServiceAccountState;

public enum  CustomNetworkServiceAccountState {
    NOT_PURCHASED(NetworkServiceAccountState.NOT_PURCHASED,"未购买网络RTK服务"),
    UNBOUND(NetworkServiceAccountState.UNBOUND,"服务已经购买，但未绑定设备！"),
    BOUND(NetworkServiceAccountState.BOUND,"已绑定内置账号"),
    UNKNOWN(NetworkServiceAccountState.NOT_PURCHASED,"网络RTK服务帐户状态未知");

    NetworkServiceAccountState code;
    String error;

    CustomNetworkServiceAccountState(NetworkServiceAccountState code, String error) {
        this.code = code;
        this.error = error;
    }
    public static  String getError(NetworkServiceAccountState code) {

        for (CustomNetworkServiceAccountState e : CustomNetworkServiceAccountState.values()) {
            if (e.code == code) {
                return e.error;
            }
        }
        return "RTK未连接";

    }
}
