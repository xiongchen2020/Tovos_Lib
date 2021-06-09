package com.example.djilib.dji.healthinfomation;

import android.content.Context;
import android.util.Log;

import com.example.djilib.dji.util.Helper;

import java.util.ArrayList;
import java.util.List;

import dji.sdk.base.DJIDiagnostics;
import dji.sdk.sdkmanager.DJISDKManager;

public class CustomHealthInfomation implements DJIDiagnostics.DiagnosticsInformationCallback{

    private Context context;
    private boolean isSupportHMS;
    private CustomHealthInfomationListener listener;
    private List<HealthInfo> allHealthInfolist = new ArrayList<>();

    public CustomHealthInfomation(Context context,CustomHealthInfomationListener listener) {
        this.context = context;
        this.listener = listener;

    }

    public void setSupportHMS(boolean isSupportHMS){
        this.isSupportHMS = isSupportHMS;
    }

    public void addListener(){
        if (DJISDKManager.getInstance().getProduct() != null) {
            DJISDKManager.getInstance().getProduct().setDiagnosticsInformationCallback(this);
        }
    }

    public void removeListener(){
        allHealthInfolist.clear();
        if (DJISDKManager.getInstance().getProduct() != null) {
            DJISDKManager.getInstance().getProduct().setDiagnosticsInformationCallback(null);
        }
    }

    @Override
    public void onUpdate(List<DJIDiagnostics> list) {
        if (list == null || list.isEmpty()||list.size()<1) {
            return;
        }
        List<HealthInfo> healthList = new ArrayList<>();

        for (DJIDiagnostics diagnostics : list) {

            if (diagnostics.getType() == DJIDiagnostics.DJIDiagnosticsType.DEVICE_HEALTH_INFORMATION) {
                handleHMS(diagnostics,healthList);
            } else {
                handleOther(diagnostics,healthList);
            }
        }
        getNewAllInfo(healthList);
        listener.upDateHealthInfoMatioList(allHealthInfolist);
       // updateLog(healthInfos);
    }

    private void getNewAllInfo(List<HealthInfo> mylist){

        if (mylist.size()>0){
            if (allHealthInfolist.size() == 0){
                allHealthInfolist.addAll(mylist);
            }else {
                List<String> ids = new ArrayList<>();

                for (int i =0;i<allHealthInfolist.size();i++){
                    String itemid = allHealthInfolist.get(i).getAlarmId();
                    boolean ischecked = false;
                    Log.i("CustomHealthInfomation","当前已有的信息 id:"+itemid+" /name:"+allHealthInfolist.get(i).getTipCn());
                    for (int j=0;j<mylist.size();j++){
                        if (mylist.get(j).getAlarmId().equals(itemid)){
                            mylist.remove(j);
                            ischecked = true;
                        }
                    }
                    if (!ischecked){
                        Log.i("CustomHealthInfomation","该问题已解决");
                        ids.add(itemid);
                    }
                }

                for (int i=0;i<ids.size();i++){
                    //allHealthInfolist.remove(positions.get(i));
                    for (int j=0;j<allHealthInfolist.size();j++){
                        if (ids.get(i).equals(allHealthInfolist.get(j))){
                            allHealthInfolist.remove(j);
                        }
                    }
                }

                allHealthInfolist.addAll(mylist);
            }

        }else {
            allHealthInfolist.clear();
        }
    }

    private void handleHMS(DJIDiagnostics diagnostic,List<HealthInfo> healthList) {
        // Get the tip from json
        List<HealthInfo> healthInfos = Helper.getHmsInfo(context.getApplicationContext());
        if (healthInfos != null){
            int index = healthInfos.indexOf(new HealthInfo("0x" + Long.toHexString(diagnostic.getHealthInformation().informationId()).toUpperCase() + ""));
            if (index >= 0) {
                HealthInfo info = healthInfos.get(index);
                if (healthInfos.contains(info)) {
                    info.setTipEn("");
                    healthList.add(info);
                }
            }
        }
    }

    private void handleOther(DJIDiagnostics diagnostics,List<HealthInfo> healthList) {

        if (filter(diagnostics)) {
            return;
        }
        HealthInfo healthInfo = new HealthInfo();
        healthInfo.setAlarmId(diagnostics.getCode()+"");
        healthInfo.setTipEn("");
        healthInfo.setTipCn(diagnostics.getReason()+":"+diagnostics.getSolution());

        healthList.add(healthInfo);
    }

    private boolean filter(DJIDiagnostics d) {
        if (!isSupportHMS) {
            return false;
        }
        List<HealthInfoMatchSDKError> errors = Helper.getHmsMatchSDKError(context.getApplicationContext());

        if (errors!= null){
            for (HealthInfoMatchSDKError e : errors) {
                if (e.getSdkErrorCode().equals(d.getCode())) {
                    // this info will also be pushed by `type == DJIDiagnostics.DJIDiagnosticsType.DEVICE_HEALTH_INFORMATION`. so need filter.
                    return true;
                }
            }
        }

        return false;
    }

}
