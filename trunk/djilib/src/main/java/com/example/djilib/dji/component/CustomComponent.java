package com.example.djilib.dji.component;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.commonlib.utils.ToastUtils;
import com.example.djilib.dji.DJIContext;
import com.example.djilib.dji.bea.MessageEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicBoolean;

import dji.common.error.DJIError;
import dji.common.error.DJISDKError;
import dji.common.useraccount.UserAccountState;
import dji.common.util.CommonCallbacks;
import dji.sdk.base.BaseComponent;
import dji.sdk.base.BaseProduct;
import dji.sdk.sdkmanager.DJISDKInitEvent;
import dji.sdk.sdkmanager.DJISDKManager;
import dji.sdk.useraccount.UserAccountManager;

public class CustomComponent implements DJISDKManager.SDKManagerCallback{

    private AtomicBoolean isRegistrationInProgress = new AtomicBoolean(false);
    private Context context;
    private CustomComponentListener listener;
    public CustomComponent(Context context) {
        this.context = context;
    }
    public CustomComponent(Context context, CustomComponentListener listener) {
        this.context = context;
        this.listener = listener;
    }

    private void loginAccount() {
        TreeMap<String,Object> event = new TreeMap<>();

        UserAccountManager.getInstance().logIntoDJIUserAccount(context,
                new CommonCallbacks.CompletionCallbackWith<UserAccountState>() {
                    @Override
                    public void onSuccess(final UserAccountState userAccountState) {
                       Log.i("zsj","用户登录"+userAccountState.name());
                       switch (userAccountState){
                           case NOT_LOGGED_IN:
                               EventBus.getDefault().post(new MessageEvent("notlogin"));
                               break;
                           case AUTHORIZED:
                               EventBus.getDefault().post(new MessageEvent("authorized"));
                               break;
                           case TOKEN_OUT_OF_DATE:
                               ToastUtils.setResultToToast("账户已过期");
                               break;

                       }
                    }

                    @Override
                    public void onFailure(DJIError error) {
                       // Log.i("zsj","用户登录失败"+error.getDescription());
                        EventBus.getDefault().post(new MessageEvent("loginError"));
                    }
                });

    }


    public void startSDKRegistration(Context context) {
        if (isRegistrationInProgress.compareAndSet(false, true)) {
            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    Log.i("zsj","注册状态："+DJISDKManager.getInstance().hasSDKRegistered());
                    Log.i("zsj","getContext："+DJIContext.getContext());
                    DJISDKManager.getInstance().registerApp(DJIContext.getContext(), CustomComponent.this);
                }
            });
        }

    }

    @Override
    public void onRegister(DJIError djiError) {
        Log.i("zsj","onRegister");
        isRegistrationInProgress.set(false);
        if (djiError == DJISDKError.REGISTRATION_SUCCESS) {
            Log.i("zsj","注册状态："+DJISDKManager.getInstance().hasSDKRegistered());
            loginAccount();
            DJISDKManager.getInstance().startConnectionToProduct();

        } else {
            Log.i("zsj","注册状态："+djiError.getErrorCode()+djiError.getDescription());
            if (255==djiError.getErrorCode()){//没有网络
                EventBus.getDefault().post(new MessageEvent("notnetwork"));
            }else{
                EventBus.getDefault().post(new MessageEvent("sdkRegisterError"));
            }


        }
    }
    @Override
    public void onProductDisconnect() {

        EventBus.getDefault().post(new MessageEvent("productDisconnect"));
      //  listener.componentDissConnect();
    }

    @Override
    public void onProductConnect(BaseProduct product) {
      //  Log.i(this.getClass().getSimpleName(), "产品第一次连接");

        EventBus.getDefault().post(new MessageEvent("productConnect"));
    }

    @Override
    public void onProductChanged(BaseProduct baseProduct) {

    }

    @Override
    public void onComponentChange(BaseProduct.ComponentKey key,
                                  BaseComponent oldComponent,
                                  BaseComponent newComponent) {
        if (newComponent != null){
            String type = "";
            switch ( key){
                case FLIGHT_CONTROLLER:
                    type = "aircraftConnect";
                    setComponentListener(type,newComponent);
                    break;
                case BATTERY:
                    type = "batteryConnect";
                    setComponentListener(type,newComponent);
                    break;
                case CAMERA:
                    type = "cameraConnect";
                    setComponentListener(type,newComponent);
                    break;
                case REMOTE_CONTROLLER:
                    type = "remoteControllerConnect";
                    setComponentListener(type,newComponent);
                    break;
                case GIMBAL:
                    type = "gimbalConnect";
                    setComponentListener(type,newComponent);
                    break;
                case PAYLOAD:
                    type = "paylodConnect";
                    setComponentListener(type,newComponent);
                    break;
            }
        }
    }

    private void setComponentListener(final String type, BaseComponent newComponent){
        if (isProductIsConnected()){

            EventBus.getDefault().post(new MessageEvent(type));
        }
        newComponent.setComponentListener(new BaseComponent.ComponentListener() {
            @Override
            public void onConnectivityChange(boolean b) {
                if (!b){
                    EventBus.getDefault().post(new MessageEvent("diss"+type));
                   // listener.payLoadDissConnect();
                }else {

                    EventBus.getDefault().post(new MessageEvent(type));
                    //listener.payLoadConnect();
                }

            }
        });
    }


    @Override
    public void onInitProcess(DJISDKInitEvent event, int totalProcess) {
      //  Log.i("zsj","totalProcess:"+totalProcess+"/event:"+event.getInitializationState().name());
    }

    @Override
    public void onDatabaseDownloadProgress(long current, long total) {

    }

    public boolean isProductIsConnected(){
        boolean isConnected = false;
        if (DJISDKManager.getInstance().getProduct() !=null){
            if (DJISDKManager.getInstance().getProduct().isConnected()){
                isConnected = true;
            }
        }
        return isConnected;
    }

}
