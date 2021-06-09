package com.example.djilib.dji.component.camera;

import android.app.Activity;
import android.util.Log;

import com.example.commonlib.utils.CustomTimeUtils;
import com.example.commonlib.utils.FileManager;
import com.example.commonlib.utils.ToastUtils;
import com.example.djilib.dji.DJIContext;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


import dji.common.airlink.PhysicalSource;
import dji.common.camera.CameraStreamSettings;
import dji.common.camera.SettingsDefinitions;
import dji.common.error.DJICameraError;
import dji.common.error.DJIError;
import dji.common.util.CommonCallbacks;
import dji.sdk.camera.Camera;
import dji.sdk.camera.VideoFeeder;
import dji.sdk.gimbal.Gimbal;
import dji.sdk.media.DownloadListener;
import dji.sdk.media.MediaFile;
import dji.ux.beta.core.util.SettingDefinitions;
import dji.ux.beta.core.widget.fpv.FPVWidget;

/**
 * 这是一个相机管理类，通过这个类去实现相机拍照信令
 */
public class CustomCameraManager {

    private List<Camera> Cameras = new ArrayList<>();
    private static String TAG = "PanTiltControl";
    private MyCameraInterface myCameraInterface;
    private Activity activity;
    private int index = 0;
    private ArrayList<MediaFile> mediaFiles = new ArrayList<>();//多媒体文件列表
    private List<CustomCamera> myCameras = new ArrayList<>();
    public boolean isSupportDowaload = false;
    public Camera camera;

    public CustomCameraManager(Activity activity) {

        this.activity = activity;
        setShootModel();

    }
    public CustomCameraManager(Activity activity, MyCameraInterface myCameraInterface) {
        this.myCameraInterface = myCameraInterface;
        this.activity = activity;
        setShootModel();

    }


    public CustomCamera getYu2Camera(){
        CustomCamera customCamera = new CustomCamera(DJIContext.getProductInstance().getCameras().get(1));
        return customCamera;
    }

    public void setMyCameraInterface(MyCameraInterface myCameraInterface) {
        this.myCameraInterface = myCameraInterface;
    }

    public List<Camera> getCamera(){

        List<Camera> camera = new ArrayList<>();
        if (DJIContext.getAircraftInstance() == null)
            return camera;

        if (DJIContext.getProductInstance().getCameras() != null){
            camera = DJIContext.getProductInstance().getCameras();
        }

        for (int i=0;i<camera.size();i++){
            Camera item  = camera.get(i);
        }
        Cameras.clear();
        myCameras.clear();
        Cameras.addAll(camera);
        Log.e(this.getClass().getSimpleName(),"Cameras:"+Cameras.size());
        for (int i=0;i<Cameras.size();i++){
            Log.e(this.getClass().getSimpleName(),"相机名"+Cameras.get(i).getDisplayName());
            Log.e(this.getClass().getSimpleName(),"云台"+Cameras.get(i).getIndex());
            CustomCamera customCamera = new CustomCamera(Cameras.get(i));
            myCameras.add(customCamera);
        }
        //Log.e(this.getClass().getSimpleName(),"Cameras:"+C)
        return camera;
    }
    /**
     * 初始化相机对象
     */
    public Camera getCameraInstance(int currentCameraId) {
        camera = null;
        if (Cameras!=null&&currentCameraId<Cameras.size()){
            camera =  Cameras.get(currentCameraId);
        }
        return camera;
    }
    public boolean isDouble(){
        boolean isDouble = false;
        if (Cameras.size()>1){
            isDouble = true;
        }else {
            if (Cameras.size() == 1){
                //ToastUtils.setResultToToast("是否支持多相机："+Cameras.get(0).getLenses().size());
                if (Cameras.get(0).getLenses()!=null){
                    isDouble = true;
                }
            }
        }
        return isDouble;
    }


    /**
     * 获取镜头数量
     */
    public int getCameraNums (){
        int nums = 0;
        if (isModuleAvailable()){
            if (DJIContext.getProductInstance().getCamera()!=null){
                nums = 1;
            }else {
                nums =  DJIContext.getProductInstance().getCameras().size();
            }
        }
        return nums;
    }

    /**
     * 判断相机模式是否可用
     * @return
     */
    public boolean isModuleAvailable() {
        return (null != DJIContext.getProductInstance()) && (null != camera);
    }

    /***
     * 设置拍照模式
     */
    public void setShootModel(){

        if (isModuleAvailable()) {
            camera.setMode(SettingsDefinitions.CameraMode.SHOOT_PHOTO,
                            new CommonCallbacks.CompletionCallback() {
                                @Override
                                public void onResult(DJIError djiError) {

                                }
                            });
        }
    }



    /**
     * 调用拍照信令
     */
    public void shootPhoto(int p){
        this.index = p;
        if (isModuleAvailable()) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (myCameraInterface!=null){
                        index = 0;
                        myCameraInterface.setStartShootState();
                    }
                }
            });
            camera.startShootPhoto(new CommonCallbacks.CompletionCallback() {
                        @Override
                        public void onResult(DJIError djiError) {
                            if (null != djiError) {
                                if (myCameraInterface!=null){
                                    index = index -1;
                                    myCameraInterface.setEndShootState(index);
                                }
                            }
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                if (myCameraInterface!=null){
                                    index = index -1;
                                    myCameraInterface.setEndShootState(index);
                                }

                                }
                            });
                        }
                    });
        }
    }


    /**
     * 设置相机模式
     * @param cameraModel  CameraMode.MEDIA_DOWNLOAD是阅览下载模式，CameraMode.SHOOT_PHOTO拍照模式
     */
    public void setCameraModel(SettingsDefinitions.CameraMode cameraModel){
        if (isModuleAvailable()) {
            camera.setMode(cameraModel, new CommonCallbacks.CompletionCallback() {
                @Override
                public void onResult(DJIError djiError) {
                    myCameraInterface.setCameraMode(djiError);
                }
            });
        }
    }

    public void isMediaDownloadModeSupported(){
        if (isModuleAvailable()) {
           isSupportDowaload =  camera.isMultiLensCameraSupported();
        }
    }
    /**
     * 获取相机名称
     */
    public String getDisplayName(){
        String cameraName = "";
        if (isModuleAvailable()) {
            cameraName = camera.getDisplayName();
        }
        return cameraName;
    }

    /**
     * 相机为h20系列，选择镜头拍照
     * @param streamSettings
     */
    public void setCaptureCameraStreamSettings(CameraStreamSettings streamSettings){
        if (isModuleAvailable()) {
            camera.setCaptureCameraStreamSettings(streamSettings, new CommonCallbacks.CompletionCallback() {
                @Override
                public void onResult(DJIError djiError) {

                }
            });
        }
    }

    public void getMediaPath(final String starttime, final String endtime){
        if (isModuleAvailable()) {
            mediaFiles.clear();
            camera.getStorageLocation(new CommonCallbacks.CompletionCallbackWith<SettingsDefinitions.StorageLocation>() {
                @Override
                public void onSuccess(SettingsDefinitions.StorageLocation storageLocation) {

                    getMediaFile(storageLocation,starttime,endtime);

                }

                @Override
                public void onFailure(DJIError djiError) {
                    ToastUtils.setResultToToast("获取多媒体路径失败");
                    myCameraInterface.getPicturesInfoFaild();
                }
            });
        }

    }


    private void getMediaFile(SettingsDefinitions.StorageLocation storageLocation, final String starttime, final String endtime){
        if (isModuleAvailable()) {
            camera.getMediaManager().refreshFileListOfStorageLocation(storageLocation, new CommonCallbacks.CompletionCallback() {
                @Override
                public void onResult(DJIError djiError) {
                    if (djiError == null){
                        List<MediaFile>  list = new ArrayList<>();
                        if (camera.getMediaManager().getSDCardFileListSnapshot().size() == 0){
                            list = camera.getMediaManager().getInternalStorageFileListSnapshot();
                        }else {
                            list = camera.getMediaManager().getSDCardFileListSnapshot();
                        }
                        if (list!=null){
                            mediaFiles.clear();
                            for (int i = 0;i<list.size();i++){
                                String timefort ="yyyy-MM-dd HH:mm:ss";
                                String pic_time = list.get(i).getDateCreated();
                                if (CustomTimeUtils.timeCompare(starttime,pic_time,timefort)==3 && CustomTimeUtils.timeCompare(pic_time,endtime,timefort)==3){
                                    mediaFiles.add(list.get(i));
                                }
                            }
                        }

                        myCameraInterface.getPicturesInfoSuccess();

                    }else {
                        ToastUtils.setResultToToast("获取多媒体文件失败");
                        myCameraInterface.getPicturesInfoFaild();
                    }
                }
            });
        }else {
            myCameraInterface.getPicturesInfoFaild();
        }
    }

    /**
     * 获取缩略图
     * @param mediaFile
     */
    public static void getFetchThumbnail (MediaFile mediaFile){

        mediaFile.fetchThumbnail(new CommonCallbacks.CompletionCallback() {
            @Override
            public void onResult(DJIError djiError) {

            }
        });
    }

    /**
     * 预览图
     * @param mediaFile
     */
    public static void getFetchPreview(MediaFile mediaFile){

        mediaFile.fetchPreview(new CommonCallbacks.CompletionCallback() {
            @Override
            public void onResult(DJIError djiError) {

            }
        });
    }

    /**
     * 自定义信息
     */
    public static void fetchCustomInformation (final MediaFile mediaFile){
        mediaFile.fetchCustomInformation(new CommonCallbacks.CompletionCallback() {
            @Override
            public void onResult(DJIError djiError) {

            }
        });
    }

    /**
     * // 原始数据
     */
    public void fetchData (MediaFile mediaFile,String pic,String fileName){

        FileManager fileManager = new FileManager();
        File file = fileManager.createDirectory(pic);
        //File pic = FileManager.createFile(DJIContext.path+taskName,fileName+".jpg");
        mediaFile.fetchFileData(file, fileName, new DownloadListener<String>() {
            @Override
            public void onStart() {

                Log.i(this.getClass().getSimpleName(),"onStart");
            }

            @Override
            public void onRateUpdate(long l, long l1, long l2) {
                Log.i(this.getClass().getSimpleName(),"onRateUpdate:"+l+"/"+l1+"/"+l2);
            }

            @Override
            public void onRealtimeDataUpdate(byte[] bytes, long l, boolean b) {

            }

            @Override
            public void onProgress(long l, long l1) {
                Log.i(this.getClass().getSimpleName(),"onProgress:"+l+"/"+l1+"/");
            }

            @Override
            public void onSuccess(String s) {
                Log.i(this.getClass().getSimpleName(),"onSuccess");
            }

            @Override
            public void onFailure(DJIError djiError) {
                Log.i(this.getClass().getSimpleName(),"onFailure:"+djiError.getDescription());
                if (djiError.getDescription().equals("Not supported")){
                    ToastUtils.setResultToToast("该设备不支持下载照片");
                }else {
                    ToastUtils.setResultToToast("下载失败："+djiError.getDescription());
                }
            }
        });
    }


    /**
     * 停止下载多媒体文件
     * @param mediaFile
     */

    public void stopLoadPic(MediaFile mediaFile){
        mediaFile.stopFetchingFileData(new CommonCallbacks.CompletionCallback() {
            @Override
            public void onResult(DJIError djiError) {

            }
        });
    }


    /**
     * 删除照片
     * @param mediaFile
     */
    public void delePic(List<MediaFile> mediaFile){
        if (isModuleAvailable()) {
            camera.getMediaManager().deleteFiles(mediaFile, new CommonCallbacks.CompletionCallbackWithTwoParam<List<MediaFile>, DJICameraError>() {
                @Override
                public void onSuccess(List<MediaFile> mediaFiles, DJICameraError djiCameraError) {

                }

                @Override
                public void onFailure(DJIError djiError) {

                }
            });
        }

    }

    public ArrayList<MediaFile> getMediaFiles() {
        return mediaFiles;
    }

    public void setMediaFiles(ArrayList<MediaFile> mediaFiles) {
        this.mediaFiles = mediaFiles;
    }

    public List<CustomCamera> getMyCameras() {
        return myCameras;
    }

    public void setMyCameras(List<CustomCamera> myCameras) {
        this.myCameras = myCameras;
    }

    public CustomCamera getNowCamera(FPVWidget fpvWidget){
        CustomCamera customCamera = null;
        PhysicalSource main = PhysicalSource.UNKNOWN;
        Log.e(this.getClass().getSimpleName(),"主视频源:"+fpvWidget.getVideoSource().name());
        if (fpvWidget.getVideoSource() == SettingDefinitions.VideoSource.PRIMARY){
            main = VideoFeeder.getInstance().getPrimaryVideoFeed().getVideoSource();
        }else {
            main = VideoFeeder.getInstance().getSecondaryVideoFeed().getVideoSource();
        }
        Log.e(this.getClass().getSimpleName(),"main:"+main);
        switch (main){
            case LEFT_CAM:
                customCamera = getCameraByIndex(0);
                break;
            case RIGHT_CAM:
                customCamera = getCameraByIndex(1);
                break;
            case TOP_CAM:
                customCamera = getCameraByIndex(4);
                break;
            case FPV_CAM:
                customCamera = null;
                break;
            case MAIN_CAM:
                customCamera =getCameraByIndex(0);
                break;
        }

        if (customCamera != null){
            Log.e(this.getClass().getSimpleName(),"camera:"+customCamera.getDisplayName());
        }else {
            Log.e(this.getClass().getSimpleName(),"camera:null");
        }

        return customCamera;
    }

    public CustomCamera getCameraByIndex(int index){
        for (int i=0;i<myCameras.size();i++){
            CustomCamera customCamera = myCameras.get(i);
            Log.e(this.getClass().getSimpleName(),"name:"+customCamera.getDisplayName()+"index:"+customCamera.getCameraindex());
            if (customCamera.getCameraindex() == index){
                return customCamera;
            }
        }
        return null;
    }

}
