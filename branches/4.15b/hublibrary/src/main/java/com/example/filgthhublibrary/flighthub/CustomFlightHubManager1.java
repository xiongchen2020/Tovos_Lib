package com.example.filgthhublibrary.flighthub;

import com.example.filgthhublibrary.network.FlightHubModel;

import dji.sdk.flighthub.FlightHubManager;
import dji.sdk.sdkmanager.DJISDKManager;

public class CustomFlightHubManager1 {
    private static CustomFlightHubManager1 customFlightHubManager;
    private FlightHubManager flightHubManager;

    private CustomFlightHubManager1() {
       if (flightHubManager==null){
           if (DJISDKManager.getInstance()!=null){
               flightHubManager = DJISDKManager.getInstance().getFlightHubManager();
           }
       }
    }

    public static CustomFlightHubManager1 getInstance() {
        if (customFlightHubManager == null) {
            customFlightHubManager = new CustomFlightHubManager1();
        }
        return customFlightHubManager;
    }



}
