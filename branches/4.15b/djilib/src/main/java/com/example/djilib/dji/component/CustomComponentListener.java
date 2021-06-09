package com.example.djilib.dji.component;

public interface CustomComponentListener {

    void componentConnect();

    void componentDissConnect();

    void aircraftConnect();

    void aircraftDissConnect();

    void batteryConnect();

    void batteryDissConnect();

    void cameraConnect();

    void cameraDissConnect();

    void gimbalConnect();

    void gimbalDissConnect();

    void payLoadConnect();

    void payLoadDissConnect();

    void remoteControllerConnect();

    void remoteControllerDissConnect();
}
