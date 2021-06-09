package com.example.djilib.dji.djierror;

import dji.common.flightcontroller.rtk.NetworkServiceChannelState;

public enum CustomNetworkServiceChannelState {
    DISABLED(NetworkServiceChannelState.DISABLED.toString(),"网络服务未启动。"),
    ACCOUNT_EXPIRED(NetworkServiceChannelState.ACCOUNT_EXPIRED.toString(),"用户帐户已过期。"),
    //NETWORK_NOT_REACHABLE("NETWORK_NOT_REACHABLE","RTK账户计划已经过期，请激活！"),
    AIRCRAFT_DISCONNECTED(NetworkServiceChannelState.AIRCRAFT_DISCONNECTED.toString(),"未连接飞机！"),
    LOGIN_FAILURE(NetworkServiceChannelState.LOGIN_FAILURE.toString(),"无法使用提供的用户名和密码登录。"),
    TRANSMITTING(NetworkServiceChannelState.TRANSMITTING.toString(),"RTK连接成功"),
    DISCONNECTED(NetworkServiceChannelState.DISCONNECTED.toString(),"RTK连接已断开"),
    ACCOUNT_ERROR(NetworkServiceChannelState.ACCOUNT_ERROR.toString(),"用户账户错误"),
    SERVER_NOT_REACHABLE(NetworkServiceChannelState.SERVER_NOT_REACHABLE.toString(),"无法连接服务器"),
    CONNECTING(NetworkServiceChannelState.CONNECTING.toString(),"正在连接..."),
    NETWORK_NOT_REACHABLE(NetworkServiceChannelState.NETWORK_NOT_REACHABLE.toString(),"无法从移动设备访问网络。"),
    SERVICE_SUSPENSION(NetworkServiceChannelState.SERVICE_SUSPENSION.toString(),"网络RTK计划A的帐户已过期，请激活计划B"),
    UNKNOWN(NetworkServiceChannelState.UNKNOWN.toString(),"未知异常"),
    INVALID_REQUEST(NetworkServiceChannelState.INVALID_REQUEST.toString(),"无效请求");

    String code;
    String error;

    CustomNetworkServiceChannelState(String code, String error) {
        this.code = code;
        this.error = error;
    }
    public static String getError(String mcode) {

        for (CustomNetworkServiceChannelState e : CustomNetworkServiceChannelState.values()) {
            if (e.code == mcode) {
                return e.error;
            }
        }
        return "RTK未连接";

    }

}
