package com.example.djilib.dji.component.battery;

import dji.common.battery.AggregationState;

public interface CustomBatteryListener {

//    void getCustomBatter1ChargeReminingInPercent(String var);
//
//    void getCustomBatter2ChargeReminingInPercent(String var);
//
//    void getCustonBatter1CellVoltages(String var);
//
//    void getCustonBatter2CellVoltages(String var);

    void setAggregationStateCallback(AggregationState aggregationState);

  //  void setStateCallback(BatteryState batteryState);
}
