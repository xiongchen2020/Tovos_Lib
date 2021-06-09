package com.example.djilib.dji.component.battery;

import com.example.djilib.dji.DJIContext;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dji.common.battery.AggregationState;
import dji.common.battery.PairingState;
import dji.keysdk.BatteryKey;
import dji.keysdk.DJIKey;
import dji.keysdk.KeyManager;
import dji.keysdk.callback.KeyListener;
import dji.sdk.battery.Battery;

public class CustomBattery {

    private List<Battery> batteryKeyLis = new ArrayList<>();
    private int customBatteryNums = 0;
    private DJIKey battery1;
    private BatteryKey pairingStateKey;
    private PairingState pairingState;
    private int CHARGE_REMAINING_IN_PERCENT = 0;
    public int  remainingInPercent = 0;
    public CustomBatteryListener listener;

    public CustomBattery(CustomBatteryListener listener){
        this.listener = listener;
        updateCustomBatteryInfo();
    }

    public void updateCustomBatteryInfo(){
        setBatteryList();
        this.pairingStateKey = BatteryKey.create(BatteryKey.PAIRING_STATE);
        getBatteryListener();

    }

    public void setBatteryList(){
      //  batteryKeyLis.clear();
        if (DJIContext.getAircraftInstance()!=null){
            if (DJIContext.getAircraftInstance().getBatteries() != null) {
               batteryKeyLis = DJIContext.getAircraftInstance().getBatteries();
            }else{
//                if (MApplication.getAircraftInstance().getBattery() != null) {
//                    batteryKeyLis.add(MApplication.getAircraftInstance().getBattery());
//                }
            }
        }
    }

//    public void setStateCallback(Battery battery) {
//
//            battery.setStateCallback(new BatteryState.Callback() {
//                @Override
//                public void onUpdate(BatteryState batteryState) {
//                   // BatteryInfo batteryInfo = new BatteryInfo()
//                    listener.setStateCallback(batteryState);
//                }
//
//            });
//
//
//    }


    public void setAggregationStateCallback(){
       // setBatteryList();
        if (batteryKeyLis.size()>0){
            Battery battery = batteryKeyLis.get(0);
            battery.setAggregationStateCallback(new AggregationState.Callback() {
                @Override
                public void onUpdate(AggregationState aggregationState) {
                    remainingInPercent = aggregationState.getChargeRemainingInPercent();
                    listener.setAggregationStateCallback(aggregationState);
                }
            });
        }
    }
    public void getBatteryListener(){

        if (KeyManager.getInstance() == null)
            return;
        battery1 = BatteryKey.create(BatteryKey.CHARGE_REMAINING_IN_PERCENT);
        KeyManager.getInstance().addListener(battery1, listener1);

    }

    private KeyListener listener1 = new KeyListener() {
        @Override
        public void onValueChange(Object o, Object o1) {
            String str = "";
            if (o1 != null){
                str = o1.toString();
            }
            if(isNumeric(str)) {//dlbfbList.set(i,str);
                CHARGE_REMAINING_IN_PERCENT = Integer.valueOf(str);
            }
            //customBatteryListener.getCustomBatter1ChargeReminingInPercent(str);
        }
    };

    private boolean isDoubleBattery() {
        if (KeyManager.getInstance() == null)
            return false;
        Object var1;
        if ((var1 = KeyManager.getInstance().getValue(this.pairingStateKey)) != null) {
            this.pairingState = (PairingState)var1;
            if (this.pairingState != PairingState.UNKNOWN) {
                return true;
            }
        }

        return false;
    }

    public boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() ){
            return false;
        }
        return true;
    }

    public List<Battery> getBatteryKeyLis() {
        return batteryKeyLis;
    }
     public int  getNum(){

         if (KeyManager.getInstance() == null)
             return 0;

        DJIKey key = BatteryKey.create(BatteryKey.NUMBER_OF_CELLS);
        Object  object = KeyManager.getInstance().getValue(key);
        if (object instanceof Integer){
            return (Integer)object;
        }
        return 0;

     }
    public int getCHARGE_REMAINING_IN_PERCENT() {
        return CHARGE_REMAINING_IN_PERCENT;
    }
}
