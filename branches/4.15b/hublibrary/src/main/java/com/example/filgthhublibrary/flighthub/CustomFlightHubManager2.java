package com.example.filgthhublibrary.flighthub;


import android.util.Log;

import com.example.commonlib.utils.CustomTimeUtils;
import com.example.commonlib.utils.LogUtil;
import com.example.commonlib.utils.SPUtils;
import com.example.commonlib.utils.ToastUtils;
import com.example.filgthhublibrary.listener.CustomFlightHubListener;
import com.example.filgthhublibrary.listener.DroneListener;
import com.example.filgthhublibrary.listener.DroneOnlineListener;
import com.example.filgthhublibrary.listener.HubLoginListener;
import com.example.filgthhublibrary.listener.HubRecordByIdListener;
import com.example.filgthhublibrary.listener.HubRecordsListener;
import com.example.filgthhublibrary.listener.HubStreamListener;
import com.example.filgthhublibrary.listener.HubTeamListener;
import com.example.filgthhublibrary.listener.HubVideoListener;
import com.example.filgthhublibrary.listener.StreanDronesListener;
import com.example.filgthhublibrary.manager.CustomLiveStreamManager;


import com.example.filgthhublibrary.manager.DjiComponentManager;
import com.example.filgthhublibrary.network.bean.ResDrones;
import com.example.filgthhublibrary.network.bean.ResFlightRecords;
import com.example.filgthhublibrary.network.bean.ResMediaList;
import com.example.filgthhublibrary.network.bean.ResOnLineDrone;
import com.example.filgthhublibrary.network.bean.ResPlay;
import com.example.filgthhublibrary.network.bean.ResRecordDetail;
import com.example.filgthhublibrary.network.bean.ResStatisticsRecords;
import com.example.filgthhublibrary.network.bean.ResTeamMember;
import com.example.filgthhublibrary.network.bean.ResUserInfo;
import com.example.filgthhublibrary.util.MapToBeanUtil;

import com.google.gson.internal.LinkedTreeMap;

import com.example.filgthhublibrary.network.FlightHubModel;
import com.example.filgthhublibrary.network.bean.BaseResponse;

import com.example.filgthhublibrary.network.bean.ReqFlightevents;
import com.example.filgthhublibrary.network.bean.ResLogin;
import com.example.filgthhublibrary.network.bean.ResPublish;

import com.example.filgthhublibrary.network.bean.TeamGetModel;


import org.greenrobot.eventbus.EventBus;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;


import dji.common.error.DJIError;
import dji.common.flightcontroller.FlightControllerState;
import dji.common.flightcontroller.LocationCoordinate3D;
import dji.common.gimbal.Attitude;
import dji.common.gimbal.GimbalState;
import dji.common.mission.waypointv2.WaypointV2MissionState;
import dji.common.model.LocationCoordinate2D;
import dji.common.product.Model;
import dji.common.util.CommonCallbacks;
import dji.keysdk.BatteryKey;
import dji.keysdk.DJIKey;
import dji.keysdk.KeyManager;
import dji.keysdk.ProductKey;
import dji.sdk.camera.Camera;
import dji.sdk.mission.MissionControl;
import dji.sdk.mission.waypoint.WaypointMissionOperator;
import dji.sdk.mission.waypoint.WaypointV2MissionOperator;
import dji.sdk.products.Aircraft;
import dji.sdk.sdkmanager.DJISDKManager;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

import static com.example.filgthhublibrary.util.ToastUtils.setResultToToast;
import static dji.common.mission.waypoint.WaypointMissionState.EXECUTING;
import static dji.common.mission.waypoint.WaypointMissionState.EXECUTION_PAUSED;


public class CustomFlightHubManager2 {
    private static CustomFlightHubManager2 customFlightHubManager;
    private FlightHubModel model;
    public DjiComponentManager djiComponentManager;
    private String sn = "";
    private int bindTeamId = 0;
    private int taskId = 0;
    private int liveStatus = 0;
    private String publishUrl = "";

    public ReqFlightevents.FlyReport flyReport;
    public CustomLiveStreamManager customLiveStreamManager;

    public List<TeamGetModel> teamGetModels = new ArrayList<TeamGetModel>();
    public CustomFlightHubListener customFlightHubListener;
    public HubLoginListener hubLoginListener;
    public HubStreamListener hubStreamListener;
    public HubTeamListener hubTeamListener;
    public HubRecordsListener hubRecordsListener;
    public DroneListener droneListener;
    public DroneOnlineListener droneOnlineListener;
    public StreanDronesListener streanDronesListener;
    private HubRecordByIdListener hubRecordByIdListener;
    public HubVideoListener hubVideoListener;

    List<ReqFlightevents.FlyReport.Gimbal> gimbalList = new ArrayList<>();
    List<ReqFlightevents.FlyReport.Camera> cameraList = new ArrayList<>();
    List<ReqFlightevents.FlyReport> flyReportList = new ArrayList<>();
    ReqFlightevents reqFlightevents;
    private boolean flightIsMotorsOn = false;
    private long startFlightTime = 0L;

    private CustomFlightHubManager2() {
        if (model == null) {
            model = new FlightHubModel();
        }
        if (djiComponentManager == null) {
            djiComponentManager = new DjiComponentManager();
        }
    }

    public static CustomFlightHubManager2 getInstance() {
        if (customFlightHubManager == null) {
            customFlightHubManager = new CustomFlightHubManager2();
        }
        return customFlightHubManager;
    }

    public void setListener(CustomFlightHubListener customFlightHubListener, HubLoginListener hubLoginListener, HubStreamListener hubStreamListener, HubTeamListener hubTeamListener, HubRecordsListener hubRecordsListener) {
        this.customFlightHubListener = customFlightHubListener;
        this.hubLoginListener = hubLoginListener;
        this.hubStreamListener = hubStreamListener;
        this.hubTeamListener = hubTeamListener;
        this.hubRecordsListener = hubRecordsListener;
    }

    public void setHubRecordByIdListener(HubRecordByIdListener hubRecordByIdListener) {
        this.hubRecordByIdListener = hubRecordByIdListener;
    }

    public void setHubLoginListener(HubLoginListener hubLoginListener) {
        this.hubLoginListener = hubLoginListener;
    }

    public void setHubStreamListener(HubStreamListener hubStreamListener) {
        this.hubStreamListener = hubStreamListener;
    }

    public void setDroneListener(DroneListener droneListener) {
        this.droneListener = droneListener;
    }

    public void setDroneOnlineListener(DroneOnlineListener droneOnlineListener) {
        this.droneOnlineListener = droneOnlineListener;
    }

    public void setStreanDronesListener(StreanDronesListener streanDronesListener) {
        this.streanDronesListener = streanDronesListener;
    }

    public void setCustomFlightHubListener(CustomFlightHubListener customFlightHubListener) {
        this.customFlightHubListener = customFlightHubListener;
    }

    public void setLoginListener(HubLoginListener hubLoginListener) {
        this.hubLoginListener = hubLoginListener;
    }

    public void setStreamListener(HubStreamListener hubStreamListener) {
        this.hubStreamListener = hubStreamListener;
    }

    public void setHubTeamListener(HubTeamListener hubTeamListener) {
        this.hubTeamListener = hubTeamListener;
    }

    public void setHubRecordsListener(HubRecordsListener hubRecordsListener) {
        this.hubRecordsListener = hubRecordsListener;
    }

    public void setHubVideoListener(HubVideoListener hubVideoListener) {
        this.hubVideoListener = hubVideoListener;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public int getBindTeamId() {
        return bindTeamId;
    }

    public void setBindTeamId(int bindTeamId) {
        this.bindTeamId = bindTeamId;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public void setLiveStatus(int liveStatus) {
        this.liveStatus = liveStatus;
    }

    public String getPublishUrl() {
        return publishUrl;
    }

    public void initCustomLiveStreamManager() {
        if (customLiveStreamManager == null) {
            customLiveStreamManager = new CustomLiveStreamManager();
            customLiveStreamManager.addListener();
        }
    }

    public void Login(String account, String password) {
        if (model != null) {
            Observable<BaseResponse<ResLogin>> observable = model.Login(account, password);
            if (observable == null) {
                return;
            }
            model.addSubscription(observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<BaseResponse<ResLogin>>() {
                        @Override
                        public void accept(BaseResponse<ResLogin> baseResponse) throws Exception {
                            setResultToToast("登录接口成功：" + baseResponse.getCode() + "/" + baseResponse.getMessage());
                            if (baseResponse.getCode() == 0) {
                                ResLogin resLogin = (ResLogin) baseResponse.getData();
                                model.setToken(resLogin.getToken());
                                model.setSignKey(resLogin.getSignkey());
                                SPUtils.put("account", resLogin.getAccount());
                                Log.i("zsj", "account:" + resLogin.getAccount());
                                teams();
                                SPUtils.put("hub_account", resLogin.getAccount());

                                getLiveUrl();
                                if (hubLoginListener != null) {
                                    hubLoginListener.loginSuccess();
                                }
                            } else {
                                if (hubLoginListener != null) {
                                    hubLoginListener.loginFail();
                                }
                            }
                        }

                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            setResultToToast("登录接口返回错误" + throwable.toString());
                            if (hubLoginListener != null) {
                                hubLoginListener.loginFail();
                            }
                        }
                    }));
        }
    }

    public void myPublish() {
        Observable<BaseResponse<ResPublish>> observable = model.publish(sn);
        if (observable == null) {
            return;
        }
        model.addSubscription(observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BaseResponse<ResPublish>>() {
                    @Override
                    public void accept(BaseResponse<ResPublish> baseResponse) throws Exception {
                        if (baseResponse.getCode() == 0) {

                            ResPublish publish = (ResPublish) baseResponse.getData();
                            if (publish.getRtmp() != null && publish.getRtmp().getPublishUrl() != null) {
                                publishUrl = publish.getRtmp().getPublishUrl();

                                if (hubStreamListener != null) {
                                    hubStreamListener.getPublishUrlSuccess(publishUrl);
                                }

                            }

                        } else {
                            liveStatus = 0;
                            ToastUtils.setResultToToast("获取直播地址错误:" + baseResponse.getMessage());
                            if (hubStreamListener != null) {
                                hubStreamListener.getPublishUrlFail();
                            }

                        }

                    }

                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        ToastUtils.setResultToToast("获取直播地址错误:" + throwable.toString());
                        liveStatus = 0;
                        if (hubStreamListener != null) {
                            hubStreamListener.getPublishUrlFail();
                        }


                    }
                }));

    }

    public void drones() {
        Observable<BaseResponse> observable = model.drones();
        if (observable == null) {
            return;
        }
        model.addSubscription(observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BaseResponse>() {
                    @Override
                    public void accept(BaseResponse baseResponse) throws Exception {
                        //  baseResponse.getData();
                        if (baseResponse.getCode() == 0) {
                            ResDrones resDrones = new ResDrones();
                            MapToBeanUtil.toDrones(resDrones, baseResponse.getData());
                            if (droneListener != null) {
                                droneListener.getDroneList(resDrones);
                            }
                        }
                        // ToastUtils.setResultToToast("获取飞机："+baseResponse.getCode()+"/"+baseResponse.getMessage());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        setResultToToast("获取飞机失败：" + throwable.getMessage());
                    }
                }));

    }

    public void onLineDrones() {
        Observable<BaseResponse> observable = model.onLineDrones();
        if (observable == null) {
            return;
        }
        model.addSubscription(observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BaseResponse>() {
                    @Override
                    public void accept(BaseResponse baseResponse) throws Exception {
                        if (baseResponse.getCode() == 0) {
                            ResOnLineDrone onLineDrone = new ResOnLineDrone();
                            MapToBeanUtil.toOnLineDrone(onLineDrone, baseResponse.getData());
                            LogUtil.e(onLineDrone + "");
                            if (droneOnlineListener != null) {
                                droneOnlineListener.getOnLineDroneList(onLineDrone);
                            }
                        }
                        //  ToastUtils.setResultToToast("绑定飞机失败："+baseResponse.getCode());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (droneOnlineListener != null) {
                            droneOnlineListener.getOnLineDroneListThrowable(throwable);
                        }
                        // ToastUtils.setResultToToast("绑定飞机失败："+throwable.getMessage());
                    }
                }));
    }

    public void onStreamDrones() {
        Observable<BaseResponse> observable = model.streams();
        if (observable == null) {
            return;
        }
        model.addSubscription(observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BaseResponse>() {
                    @Override
                    public void accept(BaseResponse baseResponse) throws Exception {
                        //  baseResponse.getData();
                        if (baseResponse.getCode() == 0) {
                            ResDrones drones = new ResDrones();
                            MapToBeanUtil.toDrones(drones, baseResponse.getData());
                            LogUtil.e(drones + "");
                            if (streanDronesListener != null) {
                                streanDronesListener.getLiveDroneList(drones);
                            }
                        }
                        LogUtil.e("获取直播飞机", "获取直播飞机：" + baseResponse.getCode() + "/" + baseResponse.getMessage());
                        LogUtil.e("获取直播飞机", "data：" + baseResponse.getData());
                    }

                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        LogUtil.e("获取直播飞机", "获取直播飞机失败：" + throwable.getMessage());
                    }
                }));
    }

    public void bindDrone(int teamId, String aircraftName) {
        Observable<BaseResponse> observable = model.bindDrone(teamId, aircraftName, sn);
        if (observable == null) {
            return;
        }
        model.addSubscription(observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BaseResponse>() {
                    @Override
                    public void accept(BaseResponse baseResponse) throws Exception {
                        if (baseResponse.getCode() == 0 || baseResponse.getCode() == 3001) {
                            bindTeamId = teamId;
                            if (customFlightHubListener != null) {
                                customFlightHubListener.updateFlightHubView();
                            }
                        }
                        LogUtil.i("zsj_绑定飞机", baseResponse.getCode() + "/" + baseResponse.getMessage());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        LogUtil.i("zsj", "绑定飞机接口返回错误" + throwable.toString());
                    }
                }));
    }

    public void bindDroneforlist(final int posotion) {
        Log.i("zsj", "posotion:" + posotion + "/ size:" + teamGetModels.size());
        if (posotion < teamGetModels.size()) {
            Log.i("zsj", "飞机名字：" + getMyModel());
            Observable<BaseResponse> observable = model.bindDroneByIndex(teamGetModels.get(posotion).getId(), getMyModel(), sn, posotion);
            if (observable == null) {
                return;
            }
            model.addSubscription(observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<BaseResponse>() {
                        @Override
                        public void accept(BaseResponse baseResponse) throws Exception {
                            if (baseResponse.getCode() == 0 || baseResponse.getCode() == 3001) {
                                bindTeamId = teamGetModels.get(posotion).getId();
                                if (customFlightHubListener != null) {
                                    customFlightHubListener.updateFlightHubView();
                                }
                                //ToastUtils.setResultToToast("飞机绑定成功");
                            }
                            Log.e("zsj_绑定飞机", baseResponse.getCode() + "/" + baseResponse.getMessage());
                            if (baseResponse.getCode() != 0 && baseResponse.getCode() != 3001) {
                                int index = posotion + 1;
                                bindDroneforlist(index);
                            }
                            // ToastUtils.setResultToToast("绑定飞机接口:"+baseResponse.getCode()+"/"+baseResponse.getMessage());
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            int index = posotion + 1;
                            bindDroneforlist(index);
                            LogUtil.i("zsj", "绑定飞机接口返回错误" + throwable.toString());
                        }
                    }));

        }
    }

    public void getRecordById(String id) {
        Observable<BaseResponse> observable = model.getFlightRecordById(id);
        if (observable == null) {
            return;
        }
        model.addSubscription(observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BaseResponse>() {
                    @Override
                    public void accept(BaseResponse baseResponse) throws Exception {
                        if (baseResponse.getCode() == 0) {
                            ResRecordDetail resRecordDetail = new ResRecordDetail();
                            MapToBeanUtil.toRecordDetail(resRecordDetail, baseResponse.getData());
                            if (hubRecordByIdListener != null) {
                                ;
                                LogUtil.d("getFlightRecordsById data:" + baseResponse.getData());
                                //JSONObject jsonObject = JSONObject.fromObject(baseResponse.getData());
                                hubRecordByIdListener.getFlightRecordsById(resRecordDetail);
                            }
                        }
                        // LogUtil.d("获取飞行记录详情:"+baseResponse.getData());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (hubRecordByIdListener != null) {
                            hubRecordByIdListener.getFlightRecordsByIdThrowable(throwable);
                        }
                        LogUtil.d("获取飞行记录详情失败：" + throwable.toString());
                    }
                }));


    }

    public void play(String sn) {
        Observable<BaseResponse<ResPlay>> observable = model.play(sn);
        if (observable == null) {
            return;
        }
        model.addSubscription(observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BaseResponse<ResPlay>>() {
                    @Override
                    public void accept(BaseResponse<ResPlay> baseResponse) throws Exception {
                        if (baseResponse.getCode() == 0) {
                            ResPlay publish = (ResPlay) baseResponse.getData();
                            if (hubStreamListener != null) {
                                hubStreamListener.playUrl(publish);
                            }
                            // FileManager.writerTxtFile("url", publish.getRtmp().getPlayUrl(), publish.getHlv().getPlayUrl());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        // ToastUtils.setResultToToast("播放地址接口返回错误"+throwable.toString());
                    }
                }));

    }

    public void teams() {
        Observable<BaseResponse> observable = model.teams();
        if (observable == null) {
            return;
        }
        model.addSubscription(observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BaseResponse>() {
                    @Override
                    public void accept(BaseResponse baseResponse) throws Exception {
                        Log.i("FlightHub_zsj", "司空获取团队：" + baseResponse.getCode() + "/" + baseResponse.getMessage());
                        if (baseResponse.getCode() == 0) {
                            LinkedTreeMap<String, ArrayList<LinkedTreeMap<String, Object>>> map = (LinkedTreeMap<String, ArrayList<LinkedTreeMap<String, Object>>>) baseResponse.getData();
                            ArrayList<LinkedTreeMap<String, Object>> linkedTreeMaps = map.get("list");
                            teamGetModels.clear();
                            teamGetModels = getSubTeam(linkedTreeMaps);
                            Log.e("zsj", "获取团队结束");
                            //isGetTeams = true;
                            if (teamGetModels != null && teamGetModels.size() > 0) {
                                if (!"".equals(sn)) {
                                    bindDroneforlist(0);
                                }
                            }
                            if (hubTeamListener != null) {
                                hubTeamListener.getTeams(teamGetModels);
                            }
                            //  model.drones();
                        }
                        //  ToastUtils.setResultToToast("获取团队："+baseResponse.getCode()+"/"+baseResponse.getMessage());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        // ToastUtils.setResultToToast("获取团队失败："+throwable.getMessage());
                    }
                }));
    }

    public void flightevents(ReqFlightevents reqFlightevents) {
        Observable<BaseResponse> observable = model.flightevents(reqFlightevents);
        if (observable == null) {
            return;
        }
        model.addSubscription(observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BaseResponse>() {
                    @Override
                    public void accept(BaseResponse baseResponse) throws Exception {
                        Log.i("zsj", "飞行记录：" + "/" + baseResponse.getCode() + "/" + baseResponse.getMessage());
                    }

                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.i("zsj", "飞行记录错误：" + throwable.getMessage());
                    }
                }));
    }

    public void teamMembers(int teamId) {
        Observable<BaseResponse> observable = model.teamMembers(teamId);
        if (observable == null) {
            return;
        }
        model.addSubscription(observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BaseResponse>() {
                    @Override
                    public void accept(BaseResponse baseResponse) throws Exception {
                        if (baseResponse.getCode() == 0) {
                            List<ResTeamMember> resTeamMemberList = new ArrayList<>();
                            MapToBeanUtil.toTeamMember(resTeamMemberList, baseResponse.getData());
                            if (hubLoginListener != null) {
                                hubLoginListener.getTeamMember(resTeamMemberList);
                            }
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (hubLoginListener != null) {
                            hubLoginListener.getTeamMembersThrowable(throwable);
                        }
                    }
                }));
    }

    public void getStatisticsRecords(int teamId, int userId, String droneSn, long startTs, long endTs) {
        Observable<BaseResponse> observable = model.getStatisticsRecords(teamId, userId, droneSn, startTs, endTs);
        if (observable == null) {
            return;
        }
        model.addSubscription(observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BaseResponse>() {
                    @Override
                    public void accept(BaseResponse baseResponse) throws Exception {
                        if (baseResponse.getCode() == 0) {
                            ResStatisticsRecords resStatisticsRecords = new ResStatisticsRecords();
                            MapToBeanUtil.toStatisticsRecords(resStatisticsRecords, baseResponse.getData());
                            if (hubRecordsListener != null) {
                                hubRecordsListener.setStatisticsRecords(resStatisticsRecords);
                            }
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (hubRecordsListener != null) {
                            hubRecordsListener.setStatisticsRecordsThrowable(throwable);
                        }
                    }
                }));

    }

    public void getFlightRecords(int teamId, int userId, String droneSn, long startTs, long endTs, int offset, int limit) {
        Observable<BaseResponse> observable = model.getFlightRecords(teamId, userId, droneSn, startTs, endTs, offset, limit);
        if (observable == null) {
            return;
        }
        model.addSubscription(observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BaseResponse>() {
                    @Override
                    public void accept(BaseResponse baseResponse) throws Exception {
                        if (baseResponse.getCode() == 0) {
                            ResFlightRecords resFlightRecords = new ResFlightRecords();
                            MapToBeanUtil.toFlightRecorda(resFlightRecords, baseResponse.getData());
                            if (hubRecordsListener != null) {
                                hubRecordsListener.setFlightRecords(resFlightRecords);
                            }
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (hubRecordsListener != null) {
                            hubRecordsListener.setFlightRecordsThrowable(throwable);
                        }
                    }
                }));

    }

    public void getMediaList(String category, int teamId, String account, String droeSn, long startTs, long endTs, int offset, int limit) {

        Observable<BaseResponse> observable = model.getMediaList(category, teamId, account, droeSn, startTs, endTs, offset, limit);
        if (observable == null) {
            return;
        }
        model.addSubscription(observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BaseResponse>() {
                    @Override
                    public void accept(BaseResponse baseResponse) throws Exception {
                        ResMediaList resMediaList = new ResMediaList();
                        if (baseResponse.getCode() == 0) {
                            MapToBeanUtil.toMediaList(resMediaList, baseResponse.getData());
                        }
                        if (hubRecordsListener != null) {
                            hubRecordsListener.setMediaList(baseResponse.getCode(), resMediaList);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (hubRecordsListener != null) {
                            hubRecordsListener.setMediaListThrowable(throwable);
                        }
                    }
                }));

    }

    public void downloadMedia(String category, String id) {
        Observable<ResponseBody> observable = model.downloadMedia(category, id);
        if (observable == null) {
            return;
        }
        model.addSubscription(observable.subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())//需要
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody baseResponse) throws Exception {
                        if (hubVideoListener != null) {
                            hubVideoListener.getVideoUrl(baseResponse);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                }));
    }

    public void uploadObject(String category, String contentType, byte[] file, String filename) {
        Observable<BaseResponse> observable = model.uploadObject(category, contentType, file, filename, taskId + "", bindTeamId);
        if (observable == null) {
            return;
        }
        model.addSubscription(observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BaseResponse>() {
                    @Override
                    public void accept(BaseResponse baseResponse) throws Exception {
                        if (baseResponse.getCode() == 0) {
                            ToastUtils.setResultToToast("媒体文件上传成功");
                        } else {
                            ToastUtils.setResultToToast("媒体文件上传失败：" + baseResponse.getMessage());
                        }

                        EventBus.getDefault().postSticky("");
                        //  ToastUtils.setResultToToast("上传媒体文件："+baseResponse.getCode());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        ToastUtils.setResultToToast("媒体文件上传失败：" + throwable.getMessage());
                        EventBus.getDefault().postSticky("");
                        // ToastUtils.setResultToToast("上传媒体文件失败："+throwable.getMessage());
                    }
                }));
    }

    public int getBatteyLevel() {
        int level = 0;
        if (KeyManager.getInstance() != null) {
            DJIKey key2 = BatteryKey.createBatteryAggregationKey(BatteryKey.CHARGE_REMAINING_IN_PERCENT);
            Object o = KeyManager.getInstance().getValue(key2);
            if (o instanceof Integer) {
                level = (int) o;
            } else {
                DJIKey key1 = BatteryKey.create(BatteryKey.CHARGE_REMAINING_IN_PERCENT);
                Object o1 = KeyManager.getInstance().getValue(key1);
                if (o1 instanceof Integer) {
                    level = (int) o1;
                }
            }
        }
        return level;
    }


    public void getFlightSate(final FlightControllerState flightControllerState) {
        getReqFlightevents();
        LocationCoordinate3D locationCoordinate3D = flightControllerState.getAircraftLocation();//飞机的3D位置
        LocationCoordinate2D locationCoordinate2D = flightControllerState.getHomeLocation();
        if (locationCoordinate3D != null  && locationCoordinate3D.getAltitude() != Double.NaN && !Double.isNaN(locationCoordinate3D.getLatitude()) && !Double.isNaN(locationCoordinate3D.getLongitude())&& !Double.isNaN(flightControllerState.getAttitude().yaw)) {
            flyReportList.clear();
            if (flightControllerState.areMotorsOn() && !flightIsMotorsOn) {
                startFlightTime = System.currentTimeMillis();
                // startDate = Integer.valueOf(String.valueOf(new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss").format(new Date(System.currentTimeMillis()))));
            }
            flightIsMotorsOn = flightControllerState.areMotorsOn();
            //   Log.i("zsj","进入司空数据");
            flyReport.setAltitude(locationCoordinate3D.getAltitude());
            flyReport.setLatitude((Double.valueOf(locationCoordinate3D.getLatitude()).floatValue()));
            flyReport.setLongitude(Double.valueOf(locationCoordinate3D.getLongitude()).floatValue());
            if (locationCoordinate2D != null && !Double.isNaN(locationCoordinate2D.getLongitude()) && !Double.isNaN(locationCoordinate2D.getLatitude()) ){
                flyReport.setHomeLatitude((Double.valueOf(locationCoordinate2D.getLatitude()).floatValue()));
                flyReport.setHomeLongitudel((Double.valueOf(locationCoordinate2D.getLongitude()).floatValue()));
            }

            flyReport.setBatteryLevel(getBatteyLevel());
            flyReport.setLiveStatus(liveStatus);
            flyReport.setSpeed(Math.abs(flightControllerState.getVelocityX()));
            flyReport.setSpeedH(Math.abs(flightControllerState.getVelocityY()));
            flyReport.setSpeedV(Math.abs(flightControllerState.getVelocityZ()));
            flyReport.setFlightMode(flightControllerState.getFlightMode().value());
            flyReport.setMoteorUp(flightControllerState.areMotorsOn());
            flyReport.setFlying(flightControllerState.isFlying());

            if (flightControllerState.isFlying()) {
                long flightTime = System.currentTimeMillis() - startFlightTime;
                LogUtil.d("飞行状态  上报飞行时间SDK：" + flightControllerState.getFlightTimeInSeconds());
                LogUtil.d("飞行状态  飞行时间转化前:" + flightTime + "/转化后：" + flightTime / 60 + "分钟" + flightTime % 60 + "秒");
                try {
                    LogUtil.d("飞行状态  电机启动时间:" + CustomTimeUtils.longToString(startFlightTime, "yyyy-mm-dd hh:mm:ss"));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                flyReport.setFlightTime(flightTime / 100);
            } else {
                flyReport.setRecordId(UUID.randomUUID().toString());
                flyReport.setFlightTime(0);
            }

            Integer date = Integer.valueOf(String.valueOf(new SimpleDateFormat("yyyyMMdd").format(new Date())));
            Log.i("zsj", flightControllerState.getFlightMode().value() + "");
            flyReport.setYaw(Double.valueOf(flightControllerState.getAttitude().yaw).floatValue());
            flyReport.setRcSignal(100);
            flyReport.setWaypointMode(getWayPointState());
            flyReport.setDate(System.currentTimeMillis());
            flyReport.setSn(sn);
            flyReport.setAllowLive(false);
            flyReport.setCamera(cameraList);
            flyReport.setGimbal(gimbalList);
            flyReport.setTeamId(bindTeamId);
            flyReportList.add(flyReport);
            Log.i("zsj", flyReport.toString() + "");
            reqFlightevents = new ReqFlightevents();
            reqFlightevents.setRecordList(flyReportList);
            flightevents(reqFlightevents);
        }

        if (customLiveStreamManager != null && customLiveStreamManager.isStreaming() && hubStreamListener != null) {
            liveStatus = 1;
            hubStreamListener.updateVideoInfo();
        }
    }

    public void getLiveUrl() {
        if (!"".equals(sn) && !"".equals(model.getSignKey())&&"".equals(publishUrl)) {
            customFlightHubManager.myPublish();
        }

    }

    public void setGimbalState(int index, GimbalState gimbalState) {
        Attitude attitude = gimbalState.getAttitudeInDegrees();
        ReqFlightevents.FlyReport.Gimbal gimbal = new ReqFlightevents.FlyReport.Gimbal();
        gimbal.setId(index);
        gimbal.setPitch(attitude.getPitch());
        gimbal.setRoll(attitude.getRoll());
        gimbal.setYaw(attitude.getYaw());
        if (gimbalList.size() > index) {
            gimbalList.set(index, gimbal);
        } else {
            gimbalList.add(gimbal);
        }
    }

    public void cameraConnData(List<Camera> cameras) {
        for (int i = 0; i < cameras.size(); i++) {
            ReqFlightevents.FlyReport.Camera camera = new ReqFlightevents.FlyReport.Camera();
            camera.setId(i);
            camera.setStatus(0);
            camera.setModel(cameras.get(i).getDisplayName());
            cameraList.add(camera);
        }
    }

    public void getReqFlightevents() {
        if (flyReport == null) {
            flyReport = new ReqFlightevents.FlyReport();
            flyReport.setAltitude(50.0f);
            flyReport.setLatitude(30.34f);
            flyReport.setLongitude(130.34f);
            flyReport.setBatteryLevel(0);
            flyReport.setDroneType(getMyModel());
            flyReport.setRecordId(UUID.randomUUID().toString());
            flyReport.setSn(sn);
        }
    }

    public String getMyModel() {
        if (KeyManager.getInstance() == null) {
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


    public List<TeamGetModel> getSubTeam(ArrayList<LinkedTreeMap<String, Object>> linkedTreeMaps) {
        List<TeamGetModel> list = new ArrayList<>();
        for (int i = 0; i < linkedTreeMaps.size(); i++) {
            TeamGetModel model = new TeamGetModel();
            LinkedTreeMap<String, Object> linkedTreeMap = linkedTreeMaps.get(i);
            Double id = (Double) linkedTreeMap.get("id");
            Double groupId = (Double) linkedTreeMap.get("groupId");
            String teamName = (String) linkedTreeMap.get("teamName");
            Double role = (Double) linkedTreeMap.get("role");
            Double memberCount = (Double) linkedTreeMap.get("memberCount");
            Double deviceCount = (Double) linkedTreeMap.get("deviceCount");
            model.setId((Double.valueOf(id)).intValue());
            if (groupId != null && groupId != Double.NaN && groupId > 0) {
                model.setGroupId((Double.valueOf(groupId)).intValue());
            }
            model.setTeamName(teamName);
            if (role != Double.NaN && role > 0) {
                model.setRole((Double.valueOf(role)).intValue());
            }
            if (deviceCount != Double.NaN && deviceCount > 0) {
                model.setDeviceCount((Double.valueOf(deviceCount)).intValue());
            }
            if (memberCount != Double.NaN && memberCount > 0) {
                model.setMemberCount((Double.valueOf(memberCount)).intValue());
            }
            ArrayList<LinkedTreeMap<String, Object>> subTeam = (ArrayList<LinkedTreeMap<String, Object>>) linkedTreeMap.get("subTeams");

            LogUtil.d("DJI Hub subTeam:" + subTeam);
            if (subTeam != null) {
                addTeam(model, subTeam);
            }

            list.add(model);
        }
        return list;
    }

    private void addTeam(TeamGetModel teamGetModel, ArrayList<LinkedTreeMap<String, Object>> linkedTreeMaps) {
        teamGetModel.setSubTeams(getSubTeam(linkedTreeMaps));
    }

    public void removeLoginInfo() {
        model.removeToken();
        model.removeSignKey();
    }

    public void modelDetory() {
        model.onUnsubscribe();
    }

    public void disconnectDetory() {
        gimbalList.clear();
        cameraList.clear();
    }

    public void detoryLiveSteam() {
        if (customLiveStreamManager != null) {
            if (customLiveStreamManager.isStreaming()) {
                customLiveStreamManager.stopStream();
            }
            customLiveStreamManager.removeListener();
            customLiveStreamManager = null;
        }
    }


    public List<Camera> getCamera() {
        List<Camera> camera = new ArrayList<>();
        if (DJISDKManager.getInstance() == null || DJISDKManager.getInstance().getProduct() == null)
            return camera;

        if (DJISDKManager.getInstance().getProduct().getCameras() != null) {
            camera = DJISDKManager.getInstance().getProduct().getCameras();
        }
        return camera;
    }

    public void getSN() {
        if (DJISDKManager.getInstance() != null && (Aircraft) DJISDKManager.getInstance().getProduct() != null) {
            Aircraft aircraft = (Aircraft) DJISDKManager.getInstance().getProduct();
            if (aircraft.getFlightController() != null) {
                aircraft.getFlightController().getSerialNumber(new CommonCallbacks.CompletionCallbackWith<String>() {
                    @Override
                    public void onSuccess(String s) {
                        sn = s;
                        getLiveUrl();
                        String sn = (String) SPUtils.get("sn", "");
                        if (!s.equals(sn)) {
                            // isbindDrone = false;
                            SPUtils.put("sn", s);
                            SPUtils.put("bindTeamName", "");
                            SPUtils.put("bindTeamId", 0);

                        }

                        if (teamGetModels != null && teamGetModels.size() > 0) {
                            if ("".equals(sn)) {
                                bindDroneforlist(0);
                            }
                        }
                    }

                    @Override
                    public void onFailure(DJIError djiError) {
                        sn = "";
                    }
                });
            }
        }
    }

    public int getWayPointState() {
        int waypointModel = -1;
        if (DJISDKManager.getInstance() != null) {

            MissionControl missionControl = DJISDKManager.getInstance().getMissionControl();
            if (missionControl != null) {
                WaypointV2MissionOperator waypointV2MissionOperator = missionControl.getWaypointMissionV2Operator();

                if (waypointV2MissionOperator == null) {
                    WaypointMissionOperator waypointMissionOperator = missionControl.getWaypointMissionOperator();
                    if (EXECUTING.equals(waypointMissionOperator.getCurrentState())) {
                        waypointModel = 1;
                    } else if (EXECUTION_PAUSED.equals(waypointMissionOperator.getCurrentState())) {
                        waypointModel = 0;
                    }
                } else {
                    if (WaypointV2MissionState.EXECUTING.equals(waypointV2MissionOperator.getCurrentState())) {
                        waypointModel = 1;
                    } else if (WaypointV2MissionState.EXECUTING.equals(waypointV2MissionOperator.getCurrentState())) {
                        waypointModel = 0;
                    }
                }
            }
        }
        return waypointModel;
    }

}
