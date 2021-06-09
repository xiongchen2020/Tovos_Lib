package com.example.djilib.dji.component.airlink;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.example.commonlib.utils.ToastUtils;
import com.example.djilib.dji.DJIContext;
import androidx.annotation.NonNull;
import dji.common.airlink.PhysicalSource;
import dji.common.error.DJIError;
import dji.common.product.Model;
import dji.common.util.CommonCallbacks;
import dji.keysdk.AirLinkKey;
import dji.keysdk.KeyManager;
import dji.keysdk.callback.ActionCallback;
import dji.keysdk.callback.SetCallback;
import dji.sdk.airlink.AirLink;
import dji.sdk.airlink.OcuSyncLink;
import dji.sdk.base.BaseProduct;
import dji.sdk.camera.VideoFeeder;
import dji.sdk.sdkmanager.DJISDKManager;
import dji.ux.widget.FPVWidget;

public class CustomAirLink {

    private CustomAirLinkListener customAirLinkListener;
    private AirLink airLink;
    private AirLinkKey extEnabledKey;
    private AirLinkKey lbBandwidthKey;
    private AirLinkKey hdmiBandwidthKey;
    private AirLinkKey mainCameraBandwidthKey;
    private AirLinkKey assignSourceToPrimaryChannelKey;
    private AirLinkKey primaryVideoBandwidthKey;
    private VideoFeeder.PhysicalSourceListener sourceListener;
    private FPVWidget main,second;
    private SetCallback setBandwidthCallback;
    private SetCallback setExtEnableCallback;
    private ActionCallback allocSourceCallback;

    private Handler handler = new Handler(Looper.getMainLooper());

    final PhysicalSource[] videoIndex = new PhysicalSource[]{
            PhysicalSource.LEFT_CAM,
            PhysicalSource.RIGHT_CAM,
            PhysicalSource.TOP_CAM,
            PhysicalSource.FPV_CAM
    };

    public CustomAirLink(CustomAirLinkListener customAirLinkListener) {
        this.customAirLinkListener = customAirLinkListener;
        initAirLink();
        initAllKeys();
        initCallbacks();
        setUpListeners();
    }

    private void initAirLink() {
        BaseProduct baseProduct = DJISDKManager.getInstance().getProduct();
        if (null != baseProduct && null != baseProduct.getAirLink()) {
            airLink = baseProduct.getAirLink();
        }
    }

    private void initCallbacks() {
        setBandwidthCallback = new SetCallback() {
            @Override
            public void onSuccess() {
                Log.e(this.getClass().getSimpleName(),"Set key value successfully");
//                if (fpvVideoFeed != null) {
//                    fpvVideoFeed.changeSourceResetKeyFrame();
//                }
//                if (primaryVideoFeed != null) {
//                    primaryVideoFeed.changeSourceResetKeyFrame();
//                }
            }

            @Override
            public void onFailure(@NonNull DJIError error) {
                Log.e(this.getClass().getSimpleName(),"Failed to set: " + error.getDescription());
            }
        };


        allocSourceCallback = new ActionCallback() {
            @Override
            public void onSuccess() {
                Log.e(this.getClass().getSimpleName(),"Perform action successfully");
            }

            @Override
            public void onFailure(@NonNull DJIError error) {
                Log.e(this.getClass().getSimpleName(),"Failed to action: " + error.getDescription());
            }
        };
    }


    private void initAllKeys() {
        extEnabledKey = AirLinkKey.createLightbridgeLinkKey(AirLinkKey.IS_EXT_VIDEO_INPUT_PORT_ENABLED);
        lbBandwidthKey = AirLinkKey.createLightbridgeLinkKey(AirLinkKey.BANDWIDTH_ALLOCATION_FOR_LB_VIDEO_INPUT_PORT);
        hdmiBandwidthKey =
                AirLinkKey.createLightbridgeLinkKey(AirLinkKey.BANDWIDTH_ALLOCATION_FOR_HDMI_VIDEO_INPUT_PORT);
        mainCameraBandwidthKey = AirLinkKey.createLightbridgeLinkKey(AirLinkKey.BANDWIDTH_ALLOCATION_FOR_LEFT_CAMERA);
        assignSourceToPrimaryChannelKey = AirLinkKey.createOcuSyncLinkKey(AirLinkKey.ASSIGN_SOURCE_TO_PRIMARY_CHANNEL);
        primaryVideoBandwidthKey = AirLinkKey.createOcuSyncLinkKey(AirLinkKey.BANDWIDTH_ALLOCATION_FOR_PRIMARY_VIDEO);
    }

    public void setUpListeners() {
        sourceListener = new VideoFeeder.PhysicalSourceListener() {
            @Override
            public void onChange(VideoFeeder.VideoFeed videoFeed, PhysicalSource newPhysicalSource) {
                if (videoFeed == VideoFeeder.getInstance().getPrimaryVideoFeed()) {
                    String newText = "Primary Source: " + newPhysicalSource.toString();
                }
                if (videoFeed == VideoFeeder.getInstance().getSecondaryVideoFeed()) {


                }
            }
        };

        if (DJIContext.getAircraftInstance() != null) {
            VideoFeeder.getInstance().addPhysicalSourceListener(sourceListener);
        } else {
            VideoFeeder.getInstance().removePhysicalSourceListener(sourceListener);
        }
    }

    public void setMainAndSer(PhysicalSource main,PhysicalSource sec){
        if (DJISDKManager.getInstance().getProduct() == null) {
            return ;
        }

        if (!isM300Product()){
            if (sec != PhysicalSource.FPV_CAM){
                setFp(main);
            }else {
                setLeftAndRight(main,sec);
            }
        }
    }

    public void setFp(PhysicalSource main) {
        if (airLink != null) {
            if (airLink.isOcuSyncLinkSupported()) {
                KeyManager.getInstance().performAction(assignSourceToPrimaryChannelKey, allocSourceCallback, main, PhysicalSource.FPV_CAM);
                KeyManager.getInstance().setValue(primaryVideoBandwidthKey, 1.0f, setBandwidthCallback);

            } else {
                KeyManager.getInstance().setValue(lbBandwidthKey, 0.8f, null);
                KeyManager.getInstance().setValue(mainCameraBandwidthKey, 1.0f, setBandwidthCallback);
            }
        }
    }


    public void setLeftAndRight(PhysicalSource main,PhysicalSource sec) {
        if (airLink != null) {
            if (airLink.isOcuSyncLinkSupported()) {
                KeyManager.getInstance().performAction(assignSourceToPrimaryChannelKey, allocSourceCallback, main, sec);
                KeyManager.getInstance().setValue(primaryVideoBandwidthKey, 0.5f, setBandwidthCallback);

            } else {
                KeyManager.getInstance().setValue(lbBandwidthKey, 1.0f, null);
                KeyManager.getInstance().setValue(mainCameraBandwidthKey, 0.5f, setBandwidthCallback);
            }
        }
    }

    public boolean isMultiStreamPlatform() {
        if (DJISDKManager.getInstance() == null){
            return false;
        }else if (DJISDKManager.getInstance().getProduct() == null){
            return false;
        }else {
            Model model = DJISDKManager.getInstance().getProduct().getModel();
            return model != null && (model == Model.INSPIRE_2
                    || model == Model.MATRICE_200
                    || model == Model.MATRICE_210
                    || model == Model.MATRICE_210_RTK
                    || model == Model.MATRICE_200_V2
                    || model == Model.MATRICE_210_V2
                    || model == Model.MATRICE_210_RTK_V2
                    || model == Model.MATRICE_300_RTK
                    || model == Model.MATRICE_600
                    || model == Model.MATRICE_600_PRO
                    || model == Model.A3
                    || model == Model.N3);
        }

    }

    public boolean isM300Product() {
        if (DJISDKManager.getInstance().getProduct() == null) {
            return false;
        }
        Model model = DJISDKManager.getInstance().getProduct().getModel();
        return model == Model.MATRICE_300_RTK;
    }

    public void setM300(final int position, final PhysicalSource sec, final String type){
        PhysicalSource mainPhysicalSource = VideoFeeder.getInstance().getPrimaryVideoFeed().getVideoSource();
        PhysicalSource secPhysicalSource= VideoFeeder.getInstance().getSecondaryVideoFeed().getVideoSource();

        if (videoIndex[position] == mainPhysicalSource || videoIndex[position] == secPhysicalSource){
            ToastUtils.setResultToToast("选中视频源当前主视频源相同，请重新选择！");
            return;
        }

        if (airLink== null)
            return;

        final OcuSyncLink ocuSyncLink = airLink.getOcuSyncLink();
        if (ocuSyncLink!=null){
            Log.e(this.getClass().getSimpleName(),"11111");
            Runnable r = new Runnable() {
                @Override
                public void run() {
                    PhysicalSource main = sec;
                    PhysicalSource secss = videoIndex[position];

                    if (type.equals("main")){
                        main= videoIndex[position];
                        secss = sec;
                    }
                    ocuSyncLink.assignSourceToPrimaryChannel(main,secss, new CommonCallbacks.CompletionCallback() {
                        @Override
                        public void onResult(DJIError error) {
                            if (error!=null){
                                ToastUtils.setResultToToast("视频源错误: " +  error.getDescription());
                            }else {
                                customAirLinkListener.refreshFpv();
                            }
                        }
                    });
                }
            };
            handler.post(r);
        }
    }
}
