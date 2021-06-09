package com.example.filgthhublibrary.listener;



import com.example.filgthhublibrary.network.bean.ResFlightRecords;
import com.example.filgthhublibrary.network.bean.ResMediaList;
import com.example.filgthhublibrary.network.bean.ResStatisticsRecords;

import java.util.List;

public interface HubRecordsListener {

   void  setStatisticsRecords(ResStatisticsRecords resStatisticsRecords);

   void setStatisticsRecordsThrowable(Throwable throwable);

   void setMediaList(int code,ResMediaList resMediaList);

   void setMediaListThrowable(Throwable throwable);

   void setFlightRecords(ResFlightRecords resFlightRecords);

   void setFlightRecordsThrowable(Throwable throwable);


}
