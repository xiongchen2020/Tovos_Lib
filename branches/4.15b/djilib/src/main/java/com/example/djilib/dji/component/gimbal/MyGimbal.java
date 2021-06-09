package com.example.djilib.dji.component.gimbal;

import android.util.Log;

import com.example.commonlib.utils.ToastUtils;
import com.example.djilib.dji.DJIContext;
import com.example.djilib.dji.util.CallbackHandlers;

import java.util.ArrayList;
import java.util.List;

import dji.common.error.DJIError;
import dji.common.gimbal.Axis;
import dji.common.gimbal.CapabilityKey;
import dji.common.gimbal.GimbalMode;
import dji.common.gimbal.GimbalState;
import dji.common.gimbal.Rotation;
import dji.common.gimbal.RotationMode;
import dji.common.util.CommonCallbacks;
import dji.common.util.DJIParamCapability;
import dji.keysdk.DJIKey;
import dji.keysdk.GimbalKey;
import dji.keysdk.KeyManager;
import dji.sdk.base.BaseProduct;
import dji.sdk.gimbal.Gimbal;
import dji.sdk.products.Aircraft;
import dji.sdk.sdkmanager.DJISDKManager;



/**
 * 云台操作类
 */
public class MyGimbal {

    private static String TAG= "MyGimbal";
    private Gimbal gimbal = null;
    GimbalListener listener;
    //private int currentGimbalId = 0;
    public  boolean pitchRangeExtensionEnabled = false;
    public MyGimbal(GimbalListener listener){
        //enablePitchExtensionIfPossible();
        this.listener = listener;
    }

    /**
     * 获取云台数量
     * @return  云台list
     */
    public List<Gimbal> getLimits(){
        List<Gimbal> limits = new ArrayList<>();
//        if (getGimbalInstance() == null){
//            return limits;
//        }

        if (DJIContext.getAircraftInstance().getGimbals()!=null){
            limits = DJIContext.getAircraftInstance().getGimbals();

        }else if(DJIContext.getAircraftInstance().getGimbal()!=null){
            limits.add(DJIContext.getAircraftInstance().getGimbal());
        }

        return limits;
    }
    /**
     * 初始化云台对象
     */
    public Gimbal getGimbalInstance(int currentGimbalId) {
        initGimbal(currentGimbalId);
        return gimbal;
    }

//    public int getCurrentGimbalId() {
//        return currentGimbalId;
//    }
//
//    public void setCurrentGimbalId(int currentGimbalId) {
//        this.currentGimbalId = currentGimbalId;
//    }

    public Gimbal getGimbal() {
        return gimbal;
    }

    public void setGimbal(Gimbal gimbal) {
        this.gimbal = gimbal;
    }

    /**
     * 初始化云台
     */
    public void initGimbal(int currentGimbalId) {
        if (DJISDKManager.getInstance() != null) {
            BaseProduct product = DJISDKManager.getInstance().getProduct();
            if (product != null&&isMyGimbalConnected()) {
                //ToastUtils.setResultToToast(TAG+"isMyGimbalConnected："+isMyGimbalConnected());
                gimbal = null;
                if (product instanceof Aircraft) {
                    List<Gimbal> gimbals = ((Aircraft) product).getGimbals();
                    if (gimbals != null) {
                        gimbal = ((Aircraft) product).getGimbals().get(currentGimbalId);
                    }
                } else {
                    gimbal = product.getGimbal();
                }
            }else{
              //  ToastUtils.setResultToToast("云台未连接");
            }
        }
    }
    public boolean isMyGimbalConnected(){
        if (KeyManager.getInstance() == null)
            return false;
        KeyManager keyManager = KeyManager.getInstance();
        DJIKey gimbalKeyConnection = GimbalKey.create(GimbalKey.CONNECTION);
        Object gimbalConnection = keyManager.getValue(gimbalKeyConnection);
        return gimbalConnection instanceof Boolean && (Boolean) gimbalConnection;
    };
    public boolean isFeatureSupported(CapabilityKey key) {

   //     Gimbal gimbal = getGimbalInstance();
        if (gimbal == null) {
            return false;
        }

        DJIParamCapability capability = null;
        if (gimbal.getCapabilities() != null) {
            capability = gimbal.getCapabilities().get(key);
        }

        if (capability != null) {
            return capability.isSupported();
        }
        return false;
    }
    /**
     * 云台俯仰限位扩展
     */
    public void getPitchExtensionIfPossible() {

        // Gimbal gimbal = getGimbalInstance();
        if (gimbal == null) {
            return;
        }
        boolean ifPossible = isFeatureSupported(CapabilityKey.PITCH_RANGE_EXTENSION);
        if (ifPossible) {
            gimbal.getPitchRangeExtensionEnabled(new CommonCallbacks.CompletionCallbackWith<Boolean>() {
                @Override
                public void onSuccess(Boolean aBoolean) {
                    pitchRangeExtensionEnabled = aBoolean;
                    listener.getPitchRangeExtensionEnabled(aBoolean);

                }

                @Override
                public void onFailure(DJIError djiError) {
                    pitchRangeExtensionEnabled = false;
                    listener.getPitchRangeExtensionEnabledFailure(djiError);

                }
            });
        }
    }
    /**
     * 云台俯仰限位扩展
     */
    public void setPitchExtensionIfPossible(final boolean b) {

       // Gimbal gimbal = getGimbalInstance();
        if (gimbal == null) {
            return;
        }
        boolean ifPossible = isFeatureSupported(CapabilityKey.PITCH_RANGE_EXTENSION);
        if (ifPossible) {
            gimbal.setPitchRangeExtensionEnabled(b, new CommonCallbacks.CompletionCallback() {
                @Override
                public void onResult(DJIError djiError) {
                    pitchRangeExtensionEnabled = b;
                    listener.setPitchRangeExtensionEnabledResult(djiError);
                }
            });
        }
    }
    /**
     * 校准云台
     */
    public void startCalibrationGimbal(){
     //   Gimbal gimbal = getGimbalInstance();
        if (gimbal == null) {
            return;
        }
        gimbal.startCalibration(new CommonCallbacks.CompletionCallback() {
            @Override
            public void onResult(DJIError djiError) {

            }
        });
    }
    /**
     *以上是初始化Gimmbal的操作
     */

    /**
     *
     */
    public void getGimbalState(){
        // Gimbal gimbal = getGimbalInstance();

        gimbal.setStateCallback(new GimbalState.Callback() {
            @Override
            public void onUpdate(GimbalState gimbalState) {
                listener.updateState(gimbalState);
            }
        });
    }
    /**
     * 获取云台工作模式
     */
    public GimbalMode getGimbalMode(){
        GimbalMode gimbalMode = null;
        // Gimbal gimbal = getGimbalInstance();
        if (KeyManager.getInstance()!=null){
            DJIKey key = GimbalKey.create(GimbalKey.MODE);
            Object o = KeyManager.getInstance().getValue(key);
            if (o instanceof GimbalMode){
                return (GimbalMode)o;
            }
        }
        return gimbalMode;

    }


    /**
     * 设置云台工作模式
     */
    public void setGimbalMode(GimbalMode gimbalMode){
       // Gimbal gimbal = getGimbalInstance();
        gimbal.setMode(gimbalMode, new CommonCallbacks.CompletionCallback() {
            @Override
            public void onResult(DJIError djiError) {
                listener.setGimbalMode(djiError);
            }
        });
    }
    /**
     * 配置云台操作参数
     * @param rotation
     */
    private void sendRotateGimbalCommand(Rotation rotation) {

       // Gimbal gimbal = getGimbalInstance();
        if (gimbal == null) {
            return;
        }

        gimbal.rotate(rotation, new CallbackHandlers.CallbackToastHandler());
    }


    /**
     * 设置云台控制
     * @param picth  倾斜度  仅由Spark和Mavic 2系列支持。
     * @param yaw  偏航    仅由Spark和Mavic 2系列支持。
     * @param roll  上下倾斜角度
     */
    public void rotateGimbalSet(float picth,float yaw,float roll) {

      //  Gimbal gimbal = getGimbalInstance();
        if (gimbal == null) {
            return;
        }

        Rotation.Builder builder = new Rotation.Builder().mode(RotationMode.ABSOLUTE_ANGLE).time(2);
        builder.pitch(picth);
        builder.yaw(yaw);
        builder.roll(roll);
        sendRotateGimbalCommand(builder.build());
    }

    /**
     * 获取云台缓启/停，获取轴上的物理控制器平滑值
     */
    public void getControllerSmoothingFactor(final Axis axis){
       // Gimbal gimbal = getGimbalInstance();
        if (gimbal == null) {
            return;
        }
        gimbal.getControllerSmoothingFactor(axis, new CommonCallbacks.CompletionCallbackWith<Integer>() {
            @Override
            public void onSuccess(Integer integer) {
                listener.getControllerSmoothingFactor(axis,integer);
            }

            @Override
            public void onFailure(DJIError djiError) {
                listener.getControllerSmoothingFactorFailure(axis,djiError);
            }
        });
    }
    /**
     * 设置云台缓启/停，设置轴上的物理控制器平滑值
     */
    public void setControllerSmoothingFactor(final Axis axis, int i){
      //  Gimbal gimbal = getGimbalInstance();
        if (gimbal == null) {
            return;
        }
        gimbal.setControllerSmoothingFactor(axis, i, new CommonCallbacks.CompletionCallback() {
            @Override
            public void onResult(DJIError djiError) {
                listener.setControllerSmoothingFactor(axis,djiError);
            }
        });
    }

    /**
     * 获取最大速度
     */
    public void getControllerMaxSpeed(final Axis axis){
       // Gimbal gimbal = getGimbalInstance();
        if (gimbal == null) {
            return;
        }
        gimbal.getControllerMaxSpeed(axis, new CommonCallbacks.CompletionCallbackWith<Integer>() {
            @Override
            public void onSuccess(Integer integer) {
                listener.getControllerMaxSpeed(axis,integer);
            }

            @Override
            public void onFailure(DJIError djiError) {
                listener.getControllerMaxSpeedFailure(axis,djiError);
            }
        });
    }
    /**
     * 设置最大速度
     */
    public void setControllerMaxSpeed(final Axis axis, int i) {
      //  Gimbal gimbal = getGimbalInstance();
        if (gimbal == null) {
            return;
        }
        gimbal.setControllerMaxSpeed(axis, i, new CommonCallbacks.CompletionCallback() {
            @Override
            public void onResult(DJIError djiError) {
                listener.setControllerMaxSpeed(axis,djiError);
            }
        });
    }
    //+++++++++++++++++++以上是设置云台操作信息++++++++++++++++++++++//

    /**
     * 重置云台位置
     */
    public void resetGimbal(){
      //  Gimbal gimbal = getGimbalInstance();
        if (gimbal != null) {
            gimbal.reset(null);
        } else {
            ToastUtils.setResultToToast("The gimbal is disconnected.");
        }
    }
    /**
     * 重置云台设置
     */
    public void restoreFactorySettings(){
       // Gimbal gimbal = getGimbalInstance();
        if (gimbal != null) {
            gimbal.restoreFactorySettings(null);
        } else {
            ToastUtils.setResultToToast("The gimbal is disconnected.");
        }
    }
}
