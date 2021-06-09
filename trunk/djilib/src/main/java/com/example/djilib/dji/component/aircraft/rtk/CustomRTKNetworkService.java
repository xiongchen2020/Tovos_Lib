package com.example.djilib.dji.component.aircraft.rtk;

import android.content.SharedPreferences;
import android.util.Log;

import com.example.commonlib.utils.ToastUtils;
import com.example.djilib.dji.DJIContext;
import com.example.djilib.dji.djierror.CustomNetworkServiceAccountState;
import com.example.djilib.dji.djierror.RTKNetServiceError;
import com.example.djilib.dji.util.ModuleVerificationUtil;

import dji.common.error.DJIError;
import dji.common.flightcontroller.rtk.CoordinateSystem;
import dji.common.flightcontroller.rtk.NetworkServicePlan;
import dji.common.flightcontroller.rtk.NetworkServicePlanType;
import dji.common.flightcontroller.rtk.NetworkServicePlansState;
import dji.common.flightcontroller.rtk.NetworkServiceSettings;
import dji.common.flightcontroller.rtk.NetworkServiceState;
import dji.common.util.CommonCallbacks;
import dji.sdk.network.RTKNetworkServiceProvider;
import dji.sdk.sdkmanager.DJISDKManager;

import static android.content.Context.MODE_PRIVATE;

public class CustomRTKNetworkService {

    private RTKNetworkServiceProvider mRTKNetworkServiceProvider;
    private CustomRTKNetworkListener myAircraftInterface;
    public boolean isFristAddListener = true;
    private NetworkServicePlanType networkServicePlanType = NetworkServicePlanType.UNKNOWN;
    private CoordinateSystem mCoordinateSystem = CoordinateSystem.UNKNOWN;
    private NetworkServicePlan selectednetworkServicePlan = null;

    public NetworkServicePlan getSelectednetworkServicePlan() {
        return selectednetworkServicePlan;
    }

    public void setSelectednetworkServicePlan(NetworkServicePlan selectednetworkServicePlan) {
        this.selectednetworkServicePlan = selectednetworkServicePlan;
    }

    public CoordinateSystem getmCoordinateSystem() {
        return mCoordinateSystem;
    }

    public void setmCoordinateSystem(CoordinateSystem mCoordinateSystem) {
        this.mCoordinateSystem = mCoordinateSystem;
    }

    public CustomRTKNetworkService(CustomRTKNetworkListener myAircraftInterface) {
        this.myAircraftInterface = myAircraftInterface;
        getRTKNetworkServiceInit();
        addListener();
//        getNetworkServiceOrderPlans();
//        getNetworkServiceCoordinateSystem();
    }

    public RTKNetworkServiceProvider getRTKNetworkServiceInit(){
        if (ModuleVerificationUtil.isNetRtkAvailable()){
            if (DJISDKManager.getInstance()!=null){
                if (mRTKNetworkServiceProvider == null) {
                    mRTKNetworkServiceProvider = DJISDKManager.getInstance().getRTKNetworkServiceProvider();
                }

                return mRTKNetworkServiceProvider;
            }
        }

        mRTKNetworkServiceProvider = null;
        return mRTKNetworkServiceProvider;
    }

    public void setNetWorkSeriverProvider(String ip,String user,String pwd,String post,String mp){
        saveRtkInfo(ip,user,pwd,post,mp);
        if (getRTKNetworkServiceInit()!=null) {
            if (post.equals("")) {
                post = "0";
            }

            mRTKNetworkServiceProvider.setCustomNetworkSettings(new NetworkServiceSettings.Builder()
                    .userName(user).password(pwd)
                    .ip(ip).port(Integer.valueOf(post)).mountPoint(mp).build()); //设置千寻网络地址

            String description4 = "AD=网络服务的设置IP: "
                    + mRTKNetworkServiceProvider.getCustomNetworkSettings().getServerAddress()
                    + "，端口号：" + mRTKNetworkServiceProvider.getCustomNetworkSettings().getPort()
                    + "，用户名：" + mRTKNetworkServiceProvider.getCustomNetworkSettings().getUserName()
                    + "，密码：" + mRTKNetworkServiceProvider.getCustomNetworkSettings().getPassword()
                    + "，域名：" + mRTKNetworkServiceProvider.getCustomNetworkSettings().getMountPoint();
            // NettyClient.getInstance().sendMsgToServer(description4);
        }
    }


    public static void saveRtkInfo(String ip,String user,String pwd,String post,String gzd){
        SharedPreferences shared = DJIContext.getContext().getSharedPreferences("rtk_data", MODE_PRIVATE);
        //获取  SharedPreferences.Editor 对象
        SharedPreferences.Editor editor = shared.edit();
        //以键值对方式传入数据
        editor.putString("ip", ip);
        editor.putString("user", user);
        editor.putString("pwd", pwd);
        editor.putString("gzd", gzd);
        editor.putString("post", post);
        //提交数据
        editor.commit();
    }




    public void openNetWorkService(){

        if (getRTKNetworkServiceInit()!=null) {
            mRTKNetworkServiceProvider.startNetworkService(new CommonCallbacks.CompletionCallback() {
                @Override
                public void onResult(DJIError djiError) {
                    if (djiError == null){
                        if (isFristAddListener){

                        }
                    }
                    myAircraftInterface.openNetWorkResult(djiError);
                }
            });
        }
    }

    public void reOpenNetWorkService(){
        if (getRTKNetworkServiceInit()!=null){
            mRTKNetworkServiceProvider.stopNetworkService(new CommonCallbacks.CompletionCallback() {
                @Override
                public void onResult(DJIError djiError) {
                    if (djiError != null) {
                        int code = djiError.getErrorCode();
                        ToastUtils.setResultToToast(RTKNetServiceError.getError(code));
                    }
                    openNetWorkService();
                }
            });
        }
    }


    public void addListener(){
        if (getRTKNetworkServiceInit()!=null) {
            mRTKNetworkServiceProvider.addNetworkServiceStateCallback(new NetworkServiceState.Callback() {
                @Override
                public void onNetworkServiceStateUpdate(NetworkServiceState networkServiceState) {
//                    isFristAddListener = false;
                    myAircraftInterface.getRtkLinkStatues(networkServiceState);
                }
            });
        }
    }

    public void removeRtkLinkListener(){
        if (getRTKNetworkServiceInit()!=null) {
            mRTKNetworkServiceProvider.removeNetworkServiceStateCallback(new NetworkServiceState.Callback() {
                @Override
                public void onNetworkServiceStateUpdate(NetworkServiceState networkServiceState) {

                    //reOpenNetWorkService();
                }
            });
            selectednetworkServicePlan = null;
            mCoordinateSystem = CoordinateSystem.UNKNOWN;
        }
    }


    public void activateNetworkService(NetworkServicePlanType networkServicePlanType){
        if (getRTKNetworkServiceInit()!=null){
            mRTKNetworkServiceProvider.activateNetworkService(networkServicePlanType, new CommonCallbacks.CompletionCallback() {
                @Override
                public void onResult(DJIError djiError) {
                    if (djiError == null){
                        ToastUtils.setResultToToast("内置RTK账号激活成功");
                        getNetworkServiceOrderPlans();
                    }else {
                        ToastUtils.setResultToToast("内置RTK账号激活失败:"+djiError.getDescription());
                    }
                }
            });
        }
    }

    public void getNetworkServiceOrderPlans(){
        if (getRTKNetworkServiceInit()!=null){
            mRTKNetworkServiceProvider.getNetworkServiceOrderPlans(new CommonCallbacks.CompletionCallbackWith<NetworkServicePlansState>() {
                @Override
                public void onSuccess(NetworkServicePlansState networkServicePlansState) {
                    Log.i("RTK网络服务","networkServicePlansState:"+networkServicePlansState.getState());
                    Log.i("RTK网络服务","networkServicePlansState:"+networkServicePlansState.getPlans().size());
                    //ToastUtils.setResultToToast(CustomNetworkServiceAccountState.getError(networkServicePlansState.getState()));
                    myAircraftInterface.getNetworkServiceOrderPlans(networkServicePlansState);
                }

                @Override
                public void onFailure(DJIError djiError) {
                    Log.i("RTK网络服务","获取状态 error:"+djiError.getDescription());
                    ToastUtils.setResultToToast(djiError.getDescription());
                }
            });
        }
    }

    public void setNetworkServiceCoordinateSystem(final CoordinateSystem coordinateSystem){
        if (getRTKNetworkServiceInit()!=null){
            mRTKNetworkServiceProvider.setNetworkServiceCoordinateSystem(coordinateSystem, new CommonCallbacks.CompletionCallback() {
                @Override
                public void onResult(DJIError djiError) {
                    if (djiError == null) {
                        mCoordinateSystem = coordinateSystem;
                    }else {
                        ToastUtils.setResultToToast(djiError.getDescription());
                    }

                }
            });
        }
    }

    public void getNetworkServiceCoordinateSystem(){
        if (getRTKNetworkServiceInit()!=null){
            mRTKNetworkServiceProvider.getNetworkServiceCoordinateSystem(new CommonCallbacks.CompletionCallbackWith<CoordinateSystem>() {
                @Override
                public void onSuccess(CoordinateSystem coordinateSystem) {
                    mCoordinateSystem = coordinateSystem;
                    myAircraftInterface.getNetworkServiceCoordinateSystem(coordinateSystem);
                }

                @Override
                public void onFailure(DJIError djiError) {
                    Log.i("RTK网络服务","获取坐标系 error copy:"+djiError.copy());
                    Log.i("RTK网络服务","获取坐标系 error code:"+djiError.getErrorCode());
                    Log.i("RTK网络服务","获取坐标系 error:"+djiError.getDescription());
                    ToastUtils.setResultToToast(djiError.getDescription());
                }
            });
        }
    }
}
