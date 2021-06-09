package com.example.djilib.dji.component.aircraft.rtk;

import com.example.commonlib.utils.LogUtil;
import com.example.commonlib.utils.ToastUtils;
import com.example.djilib.dji.DJIContext;
import com.example.djilib.dji.djierror.RTKNetServiceError;

import dji.common.error.DJIError;
import dji.common.flightcontroller.RTKState;
import dji.common.flightcontroller.rtk.RTKBaseStationInformation;
import dji.common.flightcontroller.rtk.RTKConnectionStateWithBaseStationReferenceSource;
import dji.common.flightcontroller.rtk.ReferenceStationSource;
import dji.common.util.CommonCallbacks;
import dji.keysdk.DJIKey;
import dji.keysdk.FlightControllerKey;
import dji.keysdk.KeyManager;
import dji.keysdk.callback.KeyListener;
import dji.sdk.flightcontroller.RTK;

public class RtkManager implements ReferenceStationSource.Callback,RTKState.Callback{
    private RTK mRtk;
    private RTKListener myAircraftInterface;
    private boolean isChecked = true;
    private ReferenceStationSource[] singal_type = {ReferenceStationSource.NETWORK_RTK,ReferenceStationSource.CUSTOM_NETWORK_SERVICE,ReferenceStationSource.BASE_STATION};


    public RtkManager(RTKListener myAircraftInterface) {
        this.myAircraftInterface = myAircraftInterface;
        LogUtil.d("RTK网络服务","初始化RTK");
        getRtk();
        LogUtil.d("RTK网络服务","添加RTK监听");
        setRtkListener();
        initDjiKey();
        openRtk();

        getNowSignal();
        //customRTKNetworkService = new CustomRTKNetworkService(myAircraftInterface);
        //setSignal(1);

    }

    public void removeListener(){
        if (getRtk()==null)
            return;

        getRtk().removeReferenceStationSourceCallback(this);
        //getRtk().setRtkBaseStationListCallback(null);
    }

    public RTK getRtk(){
        if (DJIContext.getAircraftInstance()!=null){
            if (DJIContext.getAircraftInstance().getFlightController()!=null){
                if (mRtk == null) {
                    mRtk = DJIContext.getAircraftInstance().getFlightController().getRTK();
                }
                return mRtk;
            }
        }
        mRtk = null;
        return mRtk;
    }

    public boolean isRtkAble(){
        if (DJIContext.getAircraftInstance()!=null){
            if (DJIContext.getAircraftInstance().getFlightController()!=null){
                return DJIContext.getAircraftInstance().getFlightController().isRTKSupported();
            }
        }
        return false;
    }

    public void openRtk(){
        if (getRtk() == null){
            return;
        }
        getRtk().setRtkEnabled(isChecked, new CommonCallbacks.CompletionCallback() {
            @Override
            public void onResult(DJIError djiError) {
                LogUtil.d("RTK网络服务","打开RTK");
                if (djiError != null) {
                    int code = djiError.getErrorCode();
                    LogUtil.d("RTK网络服务","打开RTK错误:"+RTKNetServiceError.getError(code));
                    ToastUtils.setResultToToast(RTKNetServiceError.getError(code));
                } else {
                   setSignal(1);
                }
                myAircraftInterface.openRtkResult(djiError);
            }
        });
    }


    public void getRtkStatues(){
        if (getRtk() == null){
            return;
        }
        getRtk().getRtkEnabled(new CommonCallbacks.CompletionCallbackWith<Boolean>() {

            @Override
            public void onSuccess(final Boolean aBoolean) {
                //这个东西用来判断是否成功开启RTK
                myAircraftInterface.getRtkStatues(aBoolean);
            }

            @Override
            public void onFailure(DJIError djiError) {
                    if (djiError!=null){
                        int code = djiError.getErrorCode();
                        ToastUtils.setResultToToast(RTKNetServiceError.getError(code));
                    }

                myAircraftInterface.getRtkStatues(false);
            }
        });
    }

    public void setSignal(final int position){
        if (getRtk() == null){
            return;
        }
        //设置信号源  移动站
        getRtk().setReferenceStationSource(singal_type[position], new CommonCallbacks.CompletionCallback() {
            @Override
            public void onResult(DJIError djiError) {
                LogUtil.d("RTK网络服务","设置RTK信号源:"+singal_type[position]);

                if(djiError!=null){
                    int code = djiError.getErrorCode();
                    LogUtil.d("RTK网络服务","设置RTK信号源失败:"+RTKNetServiceError.getError(code));
                    ToastUtils.setResultToToast(RTKNetServiceError.getError(code));
                }
                myAircraftInterface.setSignalResult(djiError);
            }
        });

    }

    public void getNowSignal(){
        if (getRtk() == null){
            return;
        }
        getRtk().addReferenceStationSourceCallback(this);
    }

    public void setRtkListener(){
        if (getRtk() == null){
            return;
        }
        getRtk().setStateCallback(this);
    }

    private DJIKey rtkStateKey;
    private DJIKey rtkIsConnectedKey;
    public boolean isRtkConnected = false;
    public boolean isRtkConnectFinish = false;

    public void initDjiKey(){

        if (KeyManager.getInstance() == null)
            return;
        this.rtkStateKey = FlightControllerKey.createRTKKey(FlightControllerKey.RTK_STATE);
        KeyManager.getInstance().addListener(rtkStateKey, getRtkListener);
        rtkIsConnectedKey= FlightControllerKey.createRTKKey(FlightControllerKey.IS_RTK_CONNECTED);
        KeyManager.getInstance().addListener(rtkIsConnectedKey, getRtkAbleListener);
    }

    public KeyListener getRtkListener =  new KeyListener() {
        @Override
        public void onValueChange(Object o, Object o1) {
            if (isRtkConnected){
                if (o!=null){
                    RTKState rtkState = (RTKState)o;
                    //rtkListener.setHomeAltitude( rtkState.getMobileStationAltitude());
                    myAircraftInterface.setHomeAltitude(rtkState.getMobileStationAltitude());
                }
            }
        }
    };

    public KeyListener getRtkAbleListener = new KeyListener() {
        @Override
        public void onValueChange(Object o, Object o1) {
            if (o != null){
                //isRtkConnected = (Boolean)o;
            }
        }
    };

    /**
     * 开始搜索基站
     */
    public void startSearchBaseStation(){
       // setRtkConnectionStateWithBaseStationCallback();
        setRtkBaseStationListCallback();
       getRtk().startSearchBaseStation(new CommonCallbacks.CompletionCallback() {
           @Override
           public void onResult(DJIError djiError) {
               if (djiError==null){

               }else{
                   ToastUtils.setResultToToast("搜索基站失败"+djiError.getDescription());
               }
           }
       });
    }

    /**
     * 停止搜索基站
     */
    public void stopSearchBaseStation(){
        getRtk().stopSearchBaseStation(new CommonCallbacks.CompletionCallback() {
            @Override
            public void onResult(DJIError djiError) {


            }
        });
    }

    /**
     * 连接基站
     */
    public void connectToBaseStation(long id){

        getRtk().connectToBaseStation(id, new CommonCallbacks.CompletionCallback() {
            @Override
            public void onResult(DJIError djiError) {
                if (djiError==null){

                }else{
                    ToastUtils.setResultToToast("连接基站失败");
                }
            }
        });
    }

    /**
     * 设置RTK基站扫描信息更新
     */
    public  void setRtkBaseStationListCallback(){
        getRtk().setRtkBaseStationListCallback(new RTK.RTKBaseStationListCallback() {
            @Override
            public void onUpdate(RTKBaseStationInformation[] rtkBaseStationInformations) {
                 myAircraftInterface.getRTKBaseStationInformationList(rtkBaseStationInformations);
            }
        });
    }

    /**
     * 设置RTK基站连接状态和信息更新
     */
    public void setRtkConnectionStateWithBaseStationCallback(){
        getRtk().setRtkConnectionStateWithBaseStationCallback(new RTK.RTKConnectionStateWithBaseStationReferenceSourceCallback() {
            @Override
            public void onUpdate(RTKConnectionStateWithBaseStationReferenceSource rtkConnectionStateWithBaseStationReferenceSource, RTKBaseStationInformation rtkBaseStationInformation) {
                myAircraftInterface.getRTKConnectionStateWithBaseStationState(rtkConnectionStateWithBaseStationReferenceSource,rtkBaseStationInformation);
            }
        });
    }
    @Override
    public void onReferenceStationSourceUpdate(ReferenceStationSource referenceStationSource) {
        LogUtil.d("RTK网络服务","获取当前信号源:"+referenceStationSource);
        myAircraftInterface.getNowSignalResult(referenceStationSource);
    }

    @Override
    public void onUpdate(RTKState rtkState) {
        myAircraftInterface.setHomeAltitude( rtkState.getMobileStationAltitude());
      //  LogUtil.d("rtk:"+rtkState.toString());
        String description =
                "A=RTKBeingUsed是否正在使用rtk: " + rtkState.isRTKBeingUsed() + "\n"
                        + "B=distanceToHomePointDataSource到本站数据源的距离: " + rtkState.getDistanceToHomePointDataSource() + "\n"
                        + "C=TakeoffAltitudeRecorded，true表示飞机起飞时飞行控制器记录了高度\n: " + rtkState.isTakeoffAltitudeRecorded() + "\n" +

                        "DA=DistanceToHomePointDataSource起始点数据源: " + rtkState.getDistanceToHomePointDataSource() + "\n" +

                        "DA=DistanceToHomePointDataSource起始点数据源Two: " + rtkState.getHomePointDataSource() +
                        "DB=HomePointLocation起始点位置: " + rtkState.getHomePointLocation() + "\n" +
                        "DC=SatelliteCount,GPS或RTK卫星计数: " + rtkState.getSatelliteCount() + "\n"
                        + "E=TakeOffAltitude起飞高度: " + rtkState.getTakeOffAltitude() + "\n"
                        + "F=DistanceToHomePoint离家点的距离: " + rtkState.getDistanceToHomePoint() + "\n" +

                        "G=PositioningSolution描述了用于确定定位的方法: " + rtkState.getPositioningSolution() + "\n"
                        + "H=Error错误信息: " + rtkState.getError() + "\n"
                        + "I=HeadingValid航向有效性: " + rtkState.isHeadingValid() + "\n" +

                        "J=Heading移动站度数: " + rtkState.getHeading() + "\n"
                        + "K=HeadingSolution确定精度的方法: " + rtkState.getHeadingSolution() + "\n"
                        + "L=MobileStationLocation指示RTK位置数据: " + rtkState.getMobileStationLocation() + "\n" +

                        "M=MobileStationAltitude移动台接收器相对于地面系统位置的高度: " + rtkState.getMobileStationAltitude() + "\n"
                        + "N=MobileStationStandardDeviation以米为单位的定位精度的标准偏差: " + rtkState.getMobileStationStandardDeviation().getStdLatitude()
                        + "," + rtkState.getMobileStationStandardDeviation().getStdLongitude() + "," + rtkState.getMobileStationStandardDeviation().getStdAltitude() + "\n"
                        + "O=FusionMobileStationLocation移动台的融合位置: " + rtkState.getFusionMobileStationLocation() + "\n" +

                        "P=FusionMobileStationAltitude移动台的融合高度: " + rtkState.getFusionMobileStationAltitude() + "\n"
                        + "Q=FusionHeading移动台的融合航向: " + rtkState.getFusionHeading() + "\n"
                        + "R=BaseStationLocation基站的位置坐标: " + rtkState.getBaseStationLocation() + "\n" +

                        "S=BaseStationAltitude基站在海平面以上的高度: " + rtkState.getBaseStationAltitude() + "\n"
                        + "T=MobileStationReceiver1GPSInfo单个RTK接收器GPS信息,卫星计数: " + rtkState.getMobileStationReceiver1GPSInfo().getSatelliteCount() + "\n"
                        + "U=MobileStationReceiver1BeiDouInfo单个RTK接收器北斗信息,卫星计数: " + rtkState.getMobileStationReceiver1BeiDouInfo().getSatelliteCount() + "\n" +

                        "V=MobileStationReceiver1GLONASSInfo 每个接收器连接到单个天线: " + rtkState.getMobileStationReceiver1GLONASSInfo().getSatelliteCount() + "\n"
                        + "W=MobileStationReceiver1GalileoInfo每个接收器连接到单个天线: " + rtkState.getMobileStationReceiver1GalileoInfo().getSatelliteCount() + "\n"
                        + "X=MobileStationReceiver2GPSInfo移动台2GPS信息: " + rtkState.getMobileStationReceiver2GPSInfo().getSatelliteCount() + "\n" +

                        "Y=MobileStationReceiver2BeiDouInfo移动台2北斗信息: " + rtkState.getMobileStationReceiver2BeiDouInfo().getSatelliteCount() + "\n"
                        + "Z=MobileStationReceiver2GLONASSInfo移动台信息: " + rtkState.getMobileStationReceiver2GLONASSInfo().getSatelliteCount() + "\n"
                        + "ZA=MobileStationReceiver2GalileoInfo移动台信息: " + rtkState.getMobileStationReceiver2GalileoInfo().getSatelliteCount() + "\n" +

                        "ZB=BaseStationReceiverGPSInfo: " + rtkState.getBaseStationReceiverGPSInfo().getSatelliteCount() + "\n"
                        + "ZC=BaseStationReceiverBeiDouInfo: " + rtkState.getBaseStationReceiverBeiDouInfo().getSatelliteCount() + "\n"
                        + "ZD=BaseStationReceiverGLONASSInfo: " + rtkState.getBaseStationReceiverGLONASSInfo().getSatelliteCount() + "\n" +
                        "ZE=BaseStationReceiverGalileoInfo: " + rtkState.getBaseStationReceiverGalileoInfo().getSatelliteCount() + "\n"
                        + "ZF=MobileStationReceiver1GPSInfo: " + rtkState.getMobileStationReceiver1GPSInfo().getSatelliteCount() + "\n"
                        + "ZG=MobileStationReceiver1BeiDouInfo: " + rtkState.getMobileStationReceiver1BeiDouInfo().getSatelliteCount() + "\n";

    }


}
