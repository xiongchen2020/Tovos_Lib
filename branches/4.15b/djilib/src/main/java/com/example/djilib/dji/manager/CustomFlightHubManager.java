package com.example.djilib.dji.manager;

import dji.sdk.flighthub.FlightHubManager;
import dji.sdk.sdkmanager.DJISDKManager;

public class CustomFlightHubManager {

    private FlightHubManager flightHubManager;

    public void initFlighthubManager(){
        if (DJISDKManager.getInstance()!=null){
            flightHubManager = DJISDKManager.getInstance().getFlightHubManager();
        }
    }

    public void init(){
    }
//    public void getHistoryFlight(){
//        if (flightHubManager!=null){
//            flightHubManager.getHistoricalFlightPath();//.getFlightStatistics();
//            //flightHubManager.g
//        }
//    }
}
