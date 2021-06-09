package com.example.filgthhublibrary.listener;

import com.example.filgthhublibrary.network.bean.ResRecordDetail;

public interface HubRecordByIdListener {

    void getFlightRecordsById(ResRecordDetail reqRecordDetail);

    void getFlightRecordsByIdThrowable(Throwable throwable);

}
