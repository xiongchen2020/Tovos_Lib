package com.example.filgthhublibrary.view;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.example.commonlib.utils.LogUtil;
import com.example.commonlib.utils.SPUtils;
import com.example.commonlib.utils.ToastUtils;
import com.example.filgthhublibrary.listener.CustomFlightHubListener;

import com.example.filgthhublibrary.flighthub.CustomFlightHubManager2;
import com.example.filgthhublibrary.R;
import com.example.filgthhublibrary.listener.HubLoginListener;
import com.example.filgthhublibrary.listener.HubStreamListener;
import com.example.filgthhublibrary.listener.HubTeamListener;
import com.example.filgthhublibrary.network.bean.ResPlay;
import com.example.filgthhublibrary.network.bean.ResTeamMember;
import com.example.filgthhublibrary.network.bean.ResUserInfo;
import com.example.filgthhublibrary.network.bean.TeamGetModel;

import com.example.filgthhublibrary.view.adapter.TeamAdapter;
import com.example.filgthhublibrary.view.listener.OnTeamItemClickListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.TreeMap;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import dji.common.flightcontroller.FlightControllerState;
import dji.common.gimbal.GimbalState;
import dji.common.product.Model;
import dji.keysdk.DJIKey;
import dji.keysdk.GimbalKey;
import dji.keysdk.KeyManager;
import dji.keysdk.ProductKey;
import dji.sdk.base.BaseProduct;
import dji.sdk.gimbal.Gimbal;
import dji.sdk.products.Aircraft;
import dji.sdk.sdkmanager.DJISDKManager;

public class FlightHubView extends LinearLayout implements CustomFlightHubListener, HubTeamListener, HubStreamListener, HubLoginListener {
    private Context context;

//    public MyAircraft myAircraft;
//    public MyGimbal myGimbal;

    DJIKey productConnectionKey;
    boolean isProductConn = false;

    public RecyclerView teamList;
    public RelativeLayout flightRl, bindRl;
    public ImageView ivSelector;
    private LinearLayout hubSet, hubLogin, llSetVideo, llHub;
    public CustomFlightHubManager2 customFlightHubManager;
    private TextView tvBindTeam, tvVideofps, tvVideobitrate, tvAudiobitrate, tvVideocachesize, tvVideoStatus, tvLogin, tvUnLogin, tvUserName;
    private Switch swithStartLive, swithAudioStream, swithAudioMuted, swithVideoEncoding;
    private Button buttonPublishurl;
    private EditText etUser, etPass;
    private ProgressBar pbHub;
    String account = "";
    String password = "";
    boolean isLogin = false;
    Activity activity;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public FlightHubView(Context context) {
        this(context, null);

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public FlightHubView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);


    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public FlightHubView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public FlightHubView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context = context;
        activity = (Activity) context;
        customFlightHubManager = CustomFlightHubManager2.getInstance();
        customFlightHubManager.setListener(this, this, this, this, null);

        LayoutInflater.from(context).inflate(R.layout.hub_view_layout, this);
        EventBus.getDefault().register(this);
        initView();
    }


    private void initView() {
        hubLogin = findViewById(R.id.hub_login);
        hubSet = findViewById(R.id.hub_set);
        //登录
        llHub = findViewById(R.id.ll_hub);
        pbHub = findViewById(R.id.pb_hub);
        etUser = findViewById(R.id.et_user);
        etPass = findViewById(R.id.et_pass);
        tvLogin = findViewById(R.id.tv_login);

        //设置
        flightRl = (RelativeLayout) findViewById(R.id.flight_rl);
        tvUserName = findViewById(R.id.tv_user_name);
        ivSelector = (ImageView) findViewById(R.id.iv_selector);
        teamList = (RecyclerView) findViewById(R.id.team_list);
        bindRl = findViewById(R.id.bind_rl);
        tvBindTeam = findViewById(R.id.tv_bind_team);
        // buttonPublishurl = findViewById(R.id.button_publishurl);
        tvVideoStatus = findViewById(R.id.tv_video_status);
        swithStartLive = findViewById(R.id.swith_start_live);
        llSetVideo = findViewById(R.id.ll_set_video);
        swithAudioStream = findViewById(R.id.swith_audio_stream);

        swithAudioMuted = findViewById(R.id.swith_audio_muted);
        swithVideoEncoding = findViewById(R.id.swith_video_encoding);

        tvVideofps = findViewById(R.id.tv_videofps);
        tvVideobitrate = findViewById(R.id.tv_videobitrate);
        tvAudiobitrate = findViewById(R.id.tv_audiobitrate);
        tvVideocachesize = findViewById(R.id.tv_videocachesize);
        tvUnLogin = findViewById(R.id.tv_unlogin);


        hubSet.setVisibility(GONE);
        hubLogin.setVisibility(VISIBLE);


        initSetView();

        initLoginView();


    }


    public void setView() {
        account = (String) SPUtils.get("hub_account", "");
        password = (String) SPUtils.get("hub_password", "");
        isLogin = (boolean) SPUtils.get("is_login", false);
        etUser.setText(account);
        etPass.setText(password);
        tvUserName.setText(account);
        if (isLogin && account != "" && password != "") {
            //  hubSet.setVisibility(VISIBLE);
            etUser.setText(account);
            etPass.setText(password);
            tvUserName.setText(account);
            if (customFlightHubManager != null) {
                customFlightHubManager.Login(account, password);
            }

        } else {
            hubLogin.setVisibility(VISIBLE);
            //  initLoginView();
        }
    }

    public void initLoginView() {
        tvLogin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                account = etUser.getText().toString();
                password = etPass.getText().toString();
                SPUtils.put("hub_account", account);
                SPUtils.put("hub_password", password);
                if ("".equals(account)) {
                    ToastUtils.setResultToToast("用户名不能为空");
                    return;
                }
                if ("".equals(password)) {
                    ToastUtils.setResultToToast("密码不能为空");
                    return;
                }
                if (customFlightHubManager != null) {
                    llHub.setVisibility(GONE);
                    pbHub.setVisibility(VISIBLE);
                    customFlightHubManager.Login(account, password);
                }
            }
        });

    }

    public void initSetView() {
        customFlightHubManager.setLiveStatus(0);
        tvVideoStatus.setText("未开启");

        llSetVideo.setVisibility(View.GONE);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        teamList.setLayoutManager(linearLayoutManager);

        swithStartLive.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isProductConn) {
                    if (customFlightHubManager != null) {

                        if (customFlightHubManager.customLiveStreamManager == null) {
                            ToastUtils.setResultToToast("该飞机不能开启直播");
                            swithStartLive.setChecked(!isChecked);
                            return;
                        }
                        if (customFlightHubManager.getPublishUrl() == "") {
                            customFlightHubManager.getLiveUrl();
                        } else {
                            startLive(isChecked);
                        }

                    }
                } else {
                    ToastUtils.setResultToToast("请先连飞机！！！");
                }
            }
        });

        swithAudioStream.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                customFlightHubManager.customLiveStreamManager.setAudioStreamingEnabled(isChecked);

            }
        });
        swithAudioMuted.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                customFlightHubManager.customLiveStreamManager.setAudioMuted(isChecked);

            }
        });
        swithVideoEncoding.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                customFlightHubManager.customLiveStreamManager.setVideoEncodingEnabled(isChecked);


            }
        });

        final TeamAdapter teamAdapter = new TeamAdapter(context, customFlightHubManager.teamGetModels, new OnTeamItemClickListener() {
            @Override
            public void onTaskItemClick(int Position) {

                TeamGetModel model = customFlightHubManager.teamGetModels.get(Position);
                customFlightHubManager.setBindTeamId(model.getId());
                //  ToastUtils.setResultToToast("点击了item"+ model.getId());
                if (isProductConn) {

                    //  customFlightHubManager.bindDrone(getMyModel());
                } else {
                    ToastUtils.setResultToToast("请先连飞机");
                }

            }

            @Override
            public void onTaskItemLongClick(int position) {

            }
        });
        teamList.setAdapter(teamAdapter);
        flightRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (teamList.getVisibility() == View.GONE) {
                    ivSelector.setSelected(false);
                    teamList.setVisibility(View.GONE);
                } else {
                    ivSelector.setSelected(true);
                    teamList.setVisibility(View.GONE);


                }
                teamAdapter.notifyDataSetChanged();
            }
        });
        tvUnLogin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                hubSet.setVisibility(GONE);
                hubLogin.setVisibility(VISIBLE);
                SPUtils.put("is_login", false);
                customFlightHubManager.removeLoginInfo();

                //  initLoginView();
            }
        });
    }

    public void setHubView() {
        if (customFlightHubManager != null) {
            String bindTeamName = (String) SPUtils.get("bindTeamName", "");
            tvBindTeam.setText(bindTeamName);
            teamList.setVisibility(View.GONE);
            ivSelector.setSelected(false);
            if ("".equals(bindTeamName)) {
                flightRl.setVisibility(View.GONE);
            } else {
                flightRl.setVisibility(View.GONE);


            }


        }
    }

    public void connHubView() {
        if (customFlightHubManager != null) {
            isProductConn = true;
            swithStartLive.setEnabled(true);
            customFlightHubManager.getSN();

            customFlightHubManager.setTaskId(new Random().nextInt(Integer.MAX_VALUE));
            //  customFlightHubManager.getBatteyLevel();
            List<Gimbal> gimbalList = getLimits();
            if (gimbalList != null && gimbalList.size() > 0) {
                for (int i = 0; i < gimbalList.size(); i++) {
                    final int finalI = i;
                    gimbalList.get(i).setStateCallback(new GimbalState.Callback() {
                        @Override
                        public void onUpdate(GimbalState gimbalState) {
                            // djiComponentManaer.setGimbalState();
                            customFlightHubManager.setGimbalState(finalI, gimbalState);
                        }
                    });
                }
            }
        }
    }


    public void resetHubView() {
        isProductConn = false;
        swithStartLive.setEnabled(false);
        swithStartLive.setChecked(false);
        // swithStartLive.setEnabled(false);
        llSetVideo.setVisibility(View.GONE);
        customFlightHubManager.setLiveStatus(0);
        tvVideoStatus.setText("未开启");
        if (customFlightHubManager != null) {
            customFlightHubManager.disconnectDetory();
        }
    }


    @Override
    public void loginSuccess() {
        hubLogin.setVisibility(GONE);
        llHub.setVisibility(VISIBLE);
        pbHub.setVisibility(GONE);
        hubSet.setVisibility(VISIBLE);

        SPUtils.put("is_login", true);

//        String account = (String) SPUtils.get("hub_account", "");
//        String password = (String) SPUtils.get("hub_password", "");

        etUser.setText(account);
        etPass.setText(password);
        tvUserName.setText(account);

        //  initSetView();
    }

    @Override
    public void loginFail() {
        hubSet.setVisibility(GONE);
        hubLogin.setVisibility(VISIBLE);
        llHub.setVisibility(VISIBLE);
        pbHub.setVisibility(GONE);
        SPUtils.put("is_login", false);
        //  initSetView();
        ToastUtils.setResultToToast("司空登录失败");
    }

    @Override
    public void setAllUser(List<ResUserInfo> resUserInfoList) {

    }

    @Override
    public void setAllUserThrowable(Throwable throwable) {

    }

    @Override
    public void getTeamMember(List<ResTeamMember> resTeamMemberList) {

    }

    @Override
    public void getTeamMembersThrowable(Throwable throwable) {

    }

    @Override
    public void updateFlightHubView() {
        setHubView();
    }

    @Override
    public void getPublishUrlSuccess(String publishUrl) {
        startLive(swithStartLive.isChecked());
    }

    @Override
    public void getPublishUrlFail() {
        tvVideoStatus.setText("未开启");
        swithStartLive.setChecked(false);
    }

    @Override
    public void updateVideoInfo() {


        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvVideoStatus.setText("已开启");
                tvVideofps.setText(customFlightHubManager.customLiveStreamManager.getLiveVideoFps() + " fps");
                tvVideobitrate.setText(customFlightHubManager.customLiveStreamManager.getLiveVideoBitRate() + " kpbs");
                tvAudiobitrate.setText(customFlightHubManager.customLiveStreamManager.getLiveAudioBitRate() + " kpbs");
                tvVideocachesize.setText(customFlightHubManager.customLiveStreamManager.getLiveVideoCacheSize() + " fps");
            }
        });
    }


    @Override
    public void playUrl(ResPlay publish) {

    }

    @Override
    public void getTeams(List<TeamGetModel> teamGetModels) {

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

    private void startLive(boolean isChecked) {
        if (isChecked&&swithStartLive.isChecked()) {
            tvVideoStatus.setText("开启中。。。");
            new Thread() {
                @Override
                public void run() {

                    if (customFlightHubManager.getPublishUrl() != "") {
                        customFlightHubManager.customLiveStreamManager.setLiveUrl(customFlightHubManager.getPublishUrl());
                        customFlightHubManager.customLiveStreamManager.setSource();
                        customFlightHubManager.customLiveStreamManager.startStream();
                        //  MApplication.customFlightHubManager.setAllowLive(true);
                        customFlightHubManager.customLiveStreamManager.setStartTime();
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                llSetVideo.setVisibility(View.VISIBLE);
                                swithAudioStream.setChecked(customFlightHubManager.customLiveStreamManager.isLiveAudioEnabled());
                                swithAudioMuted.setChecked(customFlightHubManager.customLiveStreamManager.isAudioMuted());
                                swithVideoEncoding.setChecked(customFlightHubManager.customLiveStreamManager.isVideoEncodingEnabled());
                            }
                        });

                    } else {
                        llSetVideo.setVisibility(View.GONE);
                        customFlightHubManager.setLiveStatus(0);
                        tvVideoStatus.setText("未开启");
                    }

                }

            }.start();

        } else {
            if (customFlightHubManager.customLiveStreamManager.isStreaming()) {
                customFlightHubManager.customLiveStreamManager.stopStream();
            }
            tvVideoStatus.setText("未开启");
            customFlightHubManager.setLiveStatus(0);
            llSetVideo.setVisibility(View.GONE);
        }


    }

    public List<Gimbal> getLimits() {
        List<Gimbal> gimbals = new ArrayList<>();
        if (KeyManager.getInstance() != null) {

            KeyManager keyManager = KeyManager.getInstance();
            DJIKey gimbalKeyConnection = GimbalKey.create(GimbalKey.CONNECTION);
            Object gimbalConnection = keyManager.getValue(gimbalKeyConnection);
            boolean isgimbalConn = gimbalConnection instanceof Boolean && (Boolean) gimbalConnection;

            if (DJISDKManager.getInstance() != null) {
                BaseProduct product = DJISDKManager.getInstance().getProduct();
                if (product != null && isgimbalConn) {
                    //ToastUtils.setResultToToast(TAG+"isMyGimbalConnected："+isMyGimbalConnected());
                    if (product instanceof Aircraft) {
                        gimbals = ((Aircraft) product).getGimbals();

                    } else {

                    }
                } else {
                    //  ToastUtils.setResultToToast("云台未连接");
                }
            }
        }

        return gimbals;

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getFlightHubData(TreeMap<String, Object> event) {


        String message = (String) event.get("message");
        //   LogUtil.i(this.getClass().getSimpleName(),"event:"+message);
        if ("flightControllerState".equals(event.get("message"))) {
            customFlightHubManager.getFlightSate((FlightControllerState) event.get("flightControllerState"));
        } else {

            LogUtil.i(this.getClass().getSimpleName(), "event:" + message);
            /* Do something */

            if ("detory".equals(message)) {
                customFlightHubManager.modelDetory();
                customFlightHubManager.detoryLiveSteam();
                EventBus.getDefault().unregister(this);
            } else if ("flightConn".equals(message)) {
                connHubView();

            } else if ("flightDisConn".equals(message)) {
                customFlightHubManager.detoryLiveSteam();
                // LogUtil.i(this.getClass().getSimpleName(),"event:"+message);
                resetHubView();

            } else if ("initCustomLiveStream".equals(message)) {
                customFlightHubManager.initCustomLiveStreamManager();
                customFlightHubManager.cameraConnData(customFlightHubManager.getCamera());
            } else if ("detoryLiveSteam".equals(message)) {
                customFlightHubManager.detoryLiveSteam();
                resetHubView();
            } else if ("uploadImage".equals(message) || "uploadVideo".equals(message)) {
                customFlightHubManager.uploadObject((String) event.get("medailtype"), (String) event.get("type"),
                        getPic2Byte((String) event.get("path")), (String) event.get("name"));
            }
        }


    }

    private byte[] getPic2Byte(String path) {
        byte[] buffer = null;

        try {
            FileInputStream fis = new FileInputStream(path);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
            //EventBus.getDefault().postSticky(new CustomMessageEvent("uploadVideo",finalVideoType,buffer,item.getFileName()));
        } catch (FileNotFoundException e) {
            //DialogUtils.dismissWaitingDialog();
            ToastUtils.setResultToToast(e.getMessage());
        } catch (IOException e) {
            // DialogUtils.dismissWaitingDialog();
            ToastUtils.setResultToToast(e.getMessage());
        }
        return buffer;
    }


}
