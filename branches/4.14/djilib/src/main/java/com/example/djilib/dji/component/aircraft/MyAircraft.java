package com.example.djilib.dji.component.aircraft;

import android.content.SharedPreferences;
import android.util.Log;

import com.example.commonlib.utils.ToastUtils;
import com.example.djilib.dji.DJIContext;
import com.example.djilib.dji.component.aircraft.compass.CustomCompass;
import com.example.djilib.dji.component.aircraft.database.CustomIMUData;
import com.example.djilib.dji.component.aircraft.flightAssistant.CustomFlightAssistant;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import dji.common.error.DJIError;
import dji.common.flightcontroller.ConnectionFailSafeBehavior;
import dji.common.flightcontroller.FlightControllerState;
import dji.common.flightcontroller.imu.CalibrationState;
import dji.common.flightcontroller.imu.IMUState;
import dji.common.model.LocationCoordinate2D;
import dji.common.product.Model;
import dji.common.util.CommonCallbacks;
import dji.keysdk.DJIKey;
import dji.keysdk.FlightControllerKey;
import dji.keysdk.KeyManager;
import dji.keysdk.ProductKey;
import dji.keysdk.callback.KeyListener;

public class MyAircraft {
   // public RtkManager rtkManager;
    private static String TAG = "MyAircraft";
    private MyAircraftInterface myAircraftInterface = null;
    public int imuCount = 0;
    public CustomCompass customCompass;
    private SharedPreferences shared;
    public boolean isAllowLimit = false;
    public boolean isAllowChangeModel = false;
    public boolean isGoHomeEnable = false;
    public CustomFlightAssistant customFlightAssistant;
    private double flightHeight = 0;
    private SmartListener smartListener;
    public MyAircraft(){

    }
    public MyAircraft(SmartListener smartListener){
        this.smartListener = smartListener;
    }

    public MyAircraft( MyAircraftInterface myAircraftInterface) {

        //setMyAircraftListener(activity);
        this.myAircraftInterface = myAircraftInterface;
        imuCount = getIMUCount();
        setMyAircraftListener();
        getGoHomeModelMaxFlightRadius();
        getGoHomeModel();
        setFlightRemainingFLightTimeListener();
        getConnectionFailSafeBehavior();
        getAllowSwitchFlightModel();
        getAllowFlightRadius();
        if (DJIContext.getAircraftInstance() != null) {
            if (DJIContext.getAircraftInstance().getFlightController() != null) {
                setIMUStateCallback();
                customCompass = new CustomCompass(myAircraftInterface);
                //setRtk();

            }
        }
    }

    public String getMyModel() {

        if (KeyManager.getInstance() == null){
            return "";
        }
        DJIKey key = ProductKey.create(ProductKey.MODEL_NAME);
        Object model = KeyManager.getInstance().getValue(key);
        String name = "";
        if (model instanceof Model && model != null) {
            name = ((Model) model).getDisplayName();
        }
        return name;
    }

    public void getSN() {
        if (DJIContext.getAircraftInstance() != null) {
            if (DJIContext.getAircraftInstance().getFlightController() != null) {
                DJIContext.getAircraftInstance().getFlightController().getSerialNumber(new CommonCallbacks.CompletionCallbackWith<String>() {
                    @Override
                    public void onSuccess(String s) {
                        if (myAircraftInterface!=null){
                            myAircraftInterface.getSerialNumber(s);
                        }

                    }

                    @Override
                    public void onFailure(DJIError djiError) {

                    }
                });
            }
        }
    }

    //??????????????????
    public void setGoHomeModel(int height) {
        if (DJIContext.getAircraftInstance() != null) {
            if (DJIContext.getAircraftInstance().getFlightController() != null) {

                //????????????
                DJIContext.getAircraftInstance().getFlightController().setGoHomeHeightInMeters(height, new CommonCallbacks.CompletionCallback() {
                    @Override
                    public void onResult(DJIError djiError) {

                        if (djiError == null) {
                            ToastUtils.setResultToToast("????????????????????????");
                        } else {
                            ToastUtils.setResultToToast("????????????????????????:" + djiError.getDescription());
                        }

                        if (myAircraftInterface != null) {
                            myAircraftInterface.getSetGoHomeHeightResult(djiError);
                        }
                    }
                });
            }
        }
    }

    //??????????????????
    public void getGoHomeModel() {
        if (DJIContext.getAircraftInstance() != null) {
            if (DJIContext.getAircraftInstance().getFlightController() != null) {

                //????????????
                DJIContext.getAircraftInstance().getFlightController().getGoHomeHeightInMeters(new CommonCallbacks.CompletionCallbackWith<Integer>() {
                    @Override
                    public void onSuccess(Integer integer) {
                        if (myAircraftInterface!=null){
                            myAircraftInterface.getFlightGoHomeHieght(integer);
                        }
                    }

                    @Override
                    public void onFailure(DJIError djiError) {

                    }
                });
            }
        }
    }

    //??????????????????
    public void getMaxHeight() {
        if (DJIContext.getAircraftInstance() != null) {
            if (DJIContext.getAircraftInstance().getFlightController() != null) {


                //????????????
                DJIContext.getAircraftInstance().getFlightController().getMaxFlightHeight(new CommonCallbacks.CompletionCallbackWith<Integer>() {
                    @Override
                    public void onSuccess(Integer integer) {
                        if (myAircraftInterface !=null){
                            myAircraftInterface.getFlightMaxHeight(integer);
                        }

                    }

                    @Override
                    public void onFailure(DJIError djiError) {

                    }
                });
            }
        }
    }

    //????????????????????????
    public void setMaxFlightRadius(int height) {
        if (DJIContext.getAircraftInstance() != null) {
            //????????????
            if (DJIContext.getAircraftInstance().getFlightController() != null)
                DJIContext.getAircraftInstance().getFlightController().setMaxFlightRadius(height, new CommonCallbacks.CompletionCallback() {
                    @Override
                    public void onResult(DJIError djiError) {
                        if (myAircraftInterface != null) {
                            myAircraftInterface.getSetGoHomeHeightResult(djiError);
                        }
                    }
                });
        }
    }

    //????????????????????????
    public void getGoHomeModelMaxFlightRadius() {
        if (DJIContext.getAircraftInstance() != null) {
            if (DJIContext.getAircraftInstance().getFlightController() != null) {

                //????????????
                DJIContext.getAircraftInstance().getFlightController().getMaxFlightRadius(new CommonCallbacks.CompletionCallbackWith<Integer>() {
                    @Override
                    public void onSuccess(Integer integer) {
                        if (myAircraftInterface!=null) {
                            myAircraftInterface.getFlightMaxRadius(integer);
                        }
                    }

                    @Override
                    public void onFailure(DJIError djiError) {

                    }
                });
            }
        }
    }


    public void setMyAircraftListener() {

        if (DJIContext.getAircraftInstance() != null) {
            if (DJIContext.getAircraftInstance().getFlightController() != null) {

                DJIContext.getAircraftInstance().getFlightController().setStateCallback(new FlightControllerState.Callback() {
                    @Override
                    public void onUpdate(@NonNull final FlightControllerState flightControllerState) {
                        if (flightControllerState.getAircraftLocation() != null) {
                            flightHeight = flightControllerState.getAircraftLocation().getAltitude();
                        }
                        if (myAircraftInterface!=null){
                            myAircraftInterface.receiver(flightControllerState);
                        }
                    }
                });
            }
        }
    }

    public void getHomeLocation() {
        if (DJIContext.getAircraftInstance() != null) {
            if (DJIContext.getAircraftInstance().getFlightController() != null) {
                DJIContext.getAircraftInstance().getFlightController().getHomeLocation(new CommonCallbacks.CompletionCallbackWith<LocationCoordinate2D>() {
                    @Override
                    public void onSuccess(LocationCoordinate2D locationCoordinate2D) {

                        if (myAircraftInterface!=null) {
                            myAircraftInterface.getHomeLocation(locationCoordinate2D);
                        }
                    }

                    @Override
                    public void onFailure(DJIError djiError) {

                    }
                });
            }
        }
    }


    private DJIKey REMAINING_FLIGHT_TIME_KEY, maxFlightRadiusEnabledKey;

    public void setFlightRemainingFLightTimeListener() {
        if (KeyManager.getInstance() == null)
            return;
        REMAINING_FLIGHT_TIME_KEY = FlightControllerKey.create(FlightControllerKey.REMAINING_FLIGHT_TIME);
        KeyManager.getInstance().addListener(REMAINING_FLIGHT_TIME_KEY, getFlightRealTimeListener);
        KeyManager.getInstance().addListener(maxFlightRadiusEnabledKey, getAllowFlightRadiusListener);
    }

    public KeyListener getFlightRealTimeListener = new KeyListener() {
        @Override
        public void onValueChange(Object o, Object o1) {
            String str = "";
            if (o1 != null) {
                str = o1.toString();
            }
        }
    };

    /**
     * ??????????????????????????????
     *
     * @param isAllow
     */
    public void setAllowSwitchFlightModel(boolean isAllow) {
        if (DJIContext.getAircraftInstance() != null) {
            if (DJIContext.getAircraftInstance().getFlightController() != null) {
                DJIContext.getAircraftInstance().getFlightController().setMultipleFlightModeEnabled(isAllow, new CommonCallbacks.CompletionCallback() {

                    @Override
                    public void onResult(DJIError djiError) {
                        if (myAircraftInterface!=null) {
                            myAircraftInterface.setIsAllowSwitchFlightModel(djiError);
                        }
                    }
                });
            }
        }
    }

    /**
     * ??????????????????????????????????????????
     */
    public void getAllowSwitchFlightModel() {
        if (DJIContext.getAircraftInstance() != null) {
            if (DJIContext.getAircraftInstance().getFlightController() != null) {
                DJIContext.getAircraftInstance().getFlightController().getMultipleFlightModeEnabled(new CommonCallbacks.CompletionCallbackWith<Boolean>() {
                    @Override
                    public void onSuccess(Boolean aBoolean) {
                        isAllowChangeModel = aBoolean;
                        if (myAircraftInterface!=null) {
                            myAircraftInterface.getIsAllowSwitchFlightModel(aBoolean);
                        }
                    }

                    @Override
                    public void onFailure(DJIError djiError) {
                        isAllowChangeModel = false;
                        if (myAircraftInterface!=null) {
                            myAircraftInterface.getIsAllowSwitchFlightModel(false);
                        }

                    }
                });
            }
        }
    }

    /**
     * ??????????????????????????????????????????????????????
     *
     * @param isAllow
     */
    public void setAllowFlightRadius(boolean isAllow) {
        if (DJIContext.getAircraftInstance() != null) {
            if (DJIContext.getAircraftInstance().getFlightController() != null) {
                DJIContext.getAircraftInstance().getFlightController().setMaxFlightRadiusLimitationEnabled(isAllow, new CommonCallbacks.CompletionCallback() {
                    @Override
                    public void onResult(DJIError djiError) {
                        if (myAircraftInterface!=null) {
                            myAircraftInterface.setIsAllowLimtFlightMaxRadius(djiError);
                        }
                    }
                });
            }
        }
    }

    public void getAllowFlightRadius() {
        if (DJIContext.getAircraftInstance() != null) {
            if (DJIContext.getAircraftInstance().getFlightController() != null) {
                DJIContext.getAircraftInstance().getFlightController().getMaxFlightRadiusLimitationEnabled(new CommonCallbacks.CompletionCallbackWith<Boolean>() {
                    @Override
                    public void onSuccess(Boolean aBoolean) {
                        isAllowLimit = aBoolean;
                        if (myAircraftInterface!=null) {
                            myAircraftInterface.getIsAllowLimtFlightMaxRadius(aBoolean);
                        }
                    }

                    @Override
                    public void onFailure(DJIError djiError) {
                        isAllowLimit = false;
                        if (myAircraftInterface!=null) {
                            myAircraftInterface.getIsAllowLimtFlightMaxRadius(false);
                        }
                    }
                });
            }
        }
    }

    public KeyListener getAllowFlightRadiusListener = new KeyListener() {
        @Override
        public void onValueChange(Object o, Object o1) {

            if (o != null) {
                boolean isAllow = (Boolean) o;
                if (myAircraftInterface!=null) {
                    myAircraftInterface.getIsAllowLimtFlightMaxRadius(isAllow);
                }
            }
        }
    };

    /**
     * ????????????????????????
     *
     * @param connectionFailSafeBehavior
     */
    public void setConnectionFailSafeBehavior(ConnectionFailSafeBehavior connectionFailSafeBehavior) {
        if (DJIContext.getAircraftInstance() != null) {
            if (DJIContext.getAircraftInstance().getFlightController() != null) {
                DJIContext.getAircraftInstance().getFlightController().setConnectionFailSafeBehavior(connectionFailSafeBehavior, new CommonCallbacks.CompletionCallback() {
                    @Override
                    public void onResult(DJIError djiError) {
                        if (myAircraftInterface!=null) {
                            myAircraftInterface.setFailSafeBehavior(djiError);
                        }
                    }
                });
            }
        }
    }

    /**
     * ????????????????????????
     */
    public void getConnectionFailSafeBehavior() {
        if (DJIContext.getAircraftInstance() != null) {
            if (DJIContext.getAircraftInstance().getFlightController() != null) {
                DJIContext.getAircraftInstance().getFlightController().getConnectionFailSafeBehavior(new CommonCallbacks.CompletionCallbackWith<ConnectionFailSafeBehavior>() {
                    @Override
                    public void onSuccess(ConnectionFailSafeBehavior connectionFailSafeBehavior) {
                        if (myAircraftInterface!=null) {
                            myAircraftInterface.getFailSafeBehavior(connectionFailSafeBehavior);
                        }
                    }

                    @Override
                    public void onFailure(DJIError djiError) {

                        if (myAircraftInterface!=null) {
                            myAircraftInterface.getFailSafeBehavior(null);
                        }
                    }
                });
            }
        }
    }

    /**
     * ????????????????????????
     */
    public void startGravityCenterCalibration() {
        if (DJIContext.getAircraftInstance() != null) {
            if (DJIContext.getAircraftInstance().getFlightController() != null) {
                DJIContext.getAircraftInstance().getFlightController().startGravityCenterCalibration(new CommonCallbacks.CompletionCallback() {
                    @Override
                    public void onResult(DJIError djiError) {


                    }
                });
            }
        }
    }

    /**
     * ????????????????????????
     */
    public void stopGravityCenterCalibration() {
        if (DJIContext.getAircraftInstance() != null) {
            if (DJIContext.getAircraftInstance().getFlightController() != null) {
                DJIContext.getAircraftInstance().getFlightController().stopGravityCenterCalibration(new CommonCallbacks.CompletionCallback() {
                    @Override
                    public void onResult(DJIError djiError) {

                    }
                });
            }
        }
    }

    public int getIMUCount() {
        int nums = 0;
        if (DJIContext.getAircraftInstance() != null) {
            if (DJIContext.getAircraftInstance().getFlightController() != null) {
                nums = DJIContext.getAircraftInstance().getFlightController().getIMUCount();
            }
        }
        return nums;
    }

    public void startIMUCalibration() {
        if (DJIContext.getAircraftInstance() != null) {
            if (DJIContext.getAircraftInstance().getFlightController() != null) {
                DJIContext.getAircraftInstance().getFlightController().startIMUCalibration(new CommonCallbacks.CompletionCallback() {
                    @Override
                    public void onResult(DJIError djiError) {

                    }
                });
            }
        }
    }

    public List<CustomIMUData> customIMUDatalist = new ArrayList<>();

    public void setIMUStateCallback() {
        if (DJIContext.getAircraftInstance() != null) {
            if (DJIContext.getAircraftInstance().getFlightController() != null) {
                DJIContext.getAircraftInstance().getFlightController().setIMUStateCallback(new IMUState.Callback() {
                    @Override
                    public void onUpdate(IMUState imuState) {

                        //imuState.
                        if (imuState.getCalibrationState() == CalibrationState.CALIBRATING){
                            Log.i("MyAircraft","IMU?????????  ??????:"+imuState.getCalibrationProgress()+"/"+100);
                            //Log.i("MyAircraft","IMU?????????  getCurrentSideStatus:"+imuState.getMultipleOrientationCalibrationHint().);
                        }else if (imuState.getCalibrationState() == CalibrationState.SUCCESSFUL){
                            Log.i("MyAircraft","IMU????????????");
                        }else if (imuState.getCalibrationState() == CalibrationState.NONE){
                            Log.i("MyAircraft","IMU?????????");
                        }else if (imuState.getCalibrationState() == CalibrationState.FAILED){
                            Log.i("MyAircraft","IMU????????????");
                        }else if (imuState.getCalibrationState() == CalibrationState.UNKNOWN){
                            Log.i("MyAircraft","IMU??????????????????");
                        }

                        if (imuState.getCalibrationProgress() >= 0 && imuState.getCalibrationProgress() <= 100) {
                            // imuState.getMultipleOrientationCalibrationHint().getState() = CalibrationOrientation.NOSE_DOWN;
                        }

                        CustomIMUData customIMUData = new CustomIMUData();
                        customIMUData.setIndex(imuState.getIndex());
                        customIMUData.setAccelerometerValue(imuState.getAccelerometerValue());
                        customIMUData.setGyroscopeValue(imuState.getGyroscopeValue());
                        boolean isadd = true;

//                        imuState.isMultiSideCalibrationType();

                        if (customIMUDatalist.size() > 0) {
                            for (int i = 0; i < customIMUDatalist.size(); i++) {
                                if (customIMUData.getIndex() == customIMUDatalist.get(i).getIndex()) {
                                    customIMUDatalist.set(i, customIMUData);
                                    isadd = false;
                                }
                            }
                        }

                        if (isadd) {
                            if (imuState.getIndex() >= 0) {
                                customIMUDatalist.add(customIMUData);
                            }
                        }
                        if (myAircraftInterface!=null)
                        myAircraftInterface.getIMUList();
                    }
                });
            }
        }
    }


    /**
     * ????????????????????????????????????(RTH)??????
     */
    public void getSmartReturToHomeEnable() {

        if (DJIContext.getAircraftInstance() != null) {
            if (DJIContext.getAircraftInstance().getFlightController() != null) {
                DJIContext.getAircraftInstance().getFlightController().getSmartReturnToHomeEnabled(new CommonCallbacks.CompletionCallbackWith<Boolean>() {
                    @Override
                    public void onSuccess(Boolean aBoolean) {
                        isGoHomeEnable = aBoolean;
                        if (smartListener!=null) {
                            smartListener.getSmartReturnToHomeEnabled(aBoolean);
                        }
                    }

                    @Override
                    public void onFailure(DJIError djiError) {
                        isGoHomeEnable = false;
                        if (smartListener!=null) {
                            smartListener.getSmartReturnToHomeEnabledError(djiError);
                        }
                    }
                });
            }
        }
    }

    /**
     * ??????????????????????????????  true ??????
     *
     * @param enable
     */
    public void setSmartReturToHomeEnable(final Boolean enable) {
        if (DJIContext.getAircraftInstance() != null) {
            if (DJIContext.getAircraftInstance().getFlightController() != null) {
                DJIContext.getAircraftInstance().getFlightController().setSmartReturnToHomeEnabled(enable, new CommonCallbacks.CompletionCallback() {
                    @Override
                    public void onResult(DJIError djiError) {
                        if (smartListener!=null) {
                            smartListener.setSmartReturToHomeEnable(enable, djiError);
                        }
                    }
                });
            }
        }
    }

    /**
     * ???????????????????????????????????????????????????
     */
    public void getLowBatteryWarningThreshold() {
        if (DJIContext.getAircraftInstance() != null) {
            if (DJIContext.getAircraftInstance().getFlightController() != null) {
                DJIContext.getAircraftInstance().getFlightController().getLowBatteryWarningThreshold(new CommonCallbacks.CompletionCallbackWith<Integer>() {
                    @Override
                    public void onSuccess(Integer integer) {
                        if (smartListener!=null) {
                            smartListener.getLowBatteryWarningThreshold(integer);
                        }
                    }

                    @Override
                    public void onFailure(DJIError djiError) {

                        if (smartListener!=null) {
                            smartListener.getLowBatteryWarningThresholdError(djiError);
                        }
                    }
                });
            }
        }
    }

    /**
     * ??????????????????????????????????????????
     *
     * @param percent ????????? [15???50]
     */
    public void setLowBatteryWarningThreshold(int percent) {
        if (DJIContext.getAircraftInstance() != null) {
            if (DJIContext.getAircraftInstance().getFlightController() != null) {
                DJIContext.getAircraftInstance().getFlightController().setLowBatteryWarningThreshold(percent, new CommonCallbacks.CompletionCallback() {
                    @Override
                    public void onResult(DJIError djiError) {
                        if (smartListener!=null) {
                            smartListener.setLowBatteryWarningThreshold(djiError);
                        }
                    }
                });
            }
        }
    }

    /**
     * ????????????????????????????????????(?????????)???
     */
    public void getSeriousLowBatteryWarningThreshold() {
        if (DJIContext.getAircraftInstance() != null) {
            if (DJIContext.getAircraftInstance().getFlightController() != null) {
                DJIContext.getAircraftInstance().getFlightController().getSeriousLowBatteryWarningThreshold(new CommonCallbacks.CompletionCallbackWith<Integer>() {
                    @Override
                    public void onSuccess(Integer integer) {
                        if (smartListener!=null) {
                            smartListener.getSeriousLowBatteryWarningThreshold(integer);
                        }
                    }

                    @Override
                    public void onFailure(DJIError djiError) {
                        if (smartListener!=null) {
                            smartListener.getSeriousLowBatteryWarningThresholdError(djiError);
                        }
                    }
                });
            }
        }
    }

    /**
     * ???????????????????????????????????????????????????   [10???getLowBatteryWarningThreshold???5]
     *
     * @param percent
     */
    public void setSeriousLowBatteryWarningThresHolding(int percent) {
        if (DJIContext.getAircraftInstance() != null) {
            if (DJIContext.getAircraftInstance().getFlightController() != null) {
                DJIContext.getAircraftInstance().getFlightController().setSeriousLowBatteryWarningThreshold(percent, new CommonCallbacks.CompletionCallback() {
                    @Override
                    public void onResult(DJIError djiError) {
                        if (smartListener!=null) {
                            smartListener.setSeriousLowBatteryWarningThreshold(djiError);
                        }
                    }
                });
            }
        }
    }

    public int getFlightTime() {
        if (DJIContext.getAircraftInstance() != null) {
            if (DJIContext.getAircraftInstance().getFlightController() != null) {
                FlightControllerState flightControllerState = DJIContext.getAircraftInstance().getFlightController().getState();
                return flightControllerState.getFlightTimeInSeconds();
            }
        }
        return 0;
    }
   public void setGoHomeLocation( LocationCoordinate2D homeLocation){
       if (DJIContext.getAircraftInstance() != null) {
           if (DJIContext.getAircraftInstance().getFlightController() != null) {
               DJIContext.getAircraftInstance().getFlightController().setHomeLocation(homeLocation, new CommonCallbacks.CompletionCallback() {
                   @Override
                   public void onResult(DJIError djiError) {
                       myAircraftInterface.setGoHomeLocation(djiError);
                   }
               });
           }
       }
    }

    public CustomFlightAssistant getCustomFlightAssistant() {
        return customFlightAssistant;
    }

    public double getFlightHeight() {
        return flightHeight;
    }
}
