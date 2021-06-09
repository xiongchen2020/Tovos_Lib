package com.example.filgthhublibrary.flighthub;

import android.app.Activity;

public class FlightHubManagerFactory {
    public static CustomFlightHubManger create(){
        CustomFlightHubManger customFlightHubManger = null;
        switch ("type") {

            default:
               // customFlightHubManger = CustomFlightHubManager2.getInstance(null);
                break;
        }
        return customFlightHubManger;
    }
}
