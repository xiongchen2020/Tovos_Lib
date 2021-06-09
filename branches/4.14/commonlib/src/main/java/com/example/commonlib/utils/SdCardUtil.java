package com.example.commonlib.utils;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.os.storage.StorageManager;
import android.os.storage.StorageVolume;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Travis1022 on 2017/8/8.
 */

public class SdCardUtil {
    /**
     * 获取内置SD卡路径
     *
     * @return
     */
    public static String getInnerSDCardPath() {
        return Environment.getExternalStorageDirectory().getPath();
    }


    public static List<String> getSDCardPath(){
        //String[] sdCardPath=new String[2];
        List<String> sdCardPath = new ArrayList<>();
        File sdFile=Environment.getExternalStorageDirectory();
        File[] files=sdFile.getParentFile().listFiles();
        for(File file:files){
            if(file.getAbsolutePath().equals(sdFile.getAbsolutePath())){//外置
                //sdCardPath[1]=sdFile.getAbsolutePath();
                sdCardPath.add(sdFile.getAbsolutePath());
            }else if(file.getAbsolutePath().contains("sdcard")){//得到内置sdcard
                //sdCardPath[0]=file.getAbsolutePath();
            }
        }
        return sdCardPath;
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public static List getAllExternalSdcardPath(Context context) {
        List uDisks = new ArrayList<>();
        String systemPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        StorageManager mStorageManager;
        mStorageManager = (StorageManager) context.getSystemService(Context.STORAGE_SERVICE);
        //获取所有挂载的设备（内部sd卡、外部sd卡、挂载的U盘）
        if (mStorageManager != null) {
            List<StorageVolume> volumes = mStorageManager.getStorageVolumes();//etStorageVolumes();
            Log.e("MainActivity", "size:" + volumes.size());
            if (volumes.size() > 0) {
                try {
                    Class storageVolumeClazz = Class
                            .forName("android.os.storage.StorageVolume");
                    //通过反射调用系统hide的方法
                    Method getPath = storageVolumeClazz.getMethod("getPath");
                    Method isRemovable = storageVolumeClazz.getMethod("isRemovable");
                    for (int i = 0; i < volumes.size(); i++) {
                        StorageVolume storageVolume = volumes.get(i);//获取每个挂载的StorageVolume
                        //通过反射调用getPath、isRemovable
                        String storagePath = (String) getPath.invoke(storageVolume);//获取路径
                        if (!storagePath.equalsIgnoreCase(systemPath)) {
                            uDisks.add(storagePath);
                        }
                        boolean isRemovableResult = (boolean) isRemovable.invoke(storageVolume);//是否可移除
                        String description = storageVolume.getDescription(context);
                        Log.e("MainActivity", " i=" + i + " ,storagePath=" + storagePath
                                + " ,isRemovableResult=" + isRemovableResult + " ,description=" + description);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return uDisks;
    }

    /**
     * 获取存储路径
     * @return 所有可用于存储的不同的卡的位置，用一个List来保存
     */
    public static List<String> getExtSDCardPathList() {
        List<String> paths = new ArrayList<String>();
        String extFileStatus = Environment.getExternalStorageState();
        File extFile = Environment.getExternalStorageDirectory();
        //首先判断一下外置SD卡的状态，处于挂载状态才能获取的到
        if (extFileStatus.equals(Environment.MEDIA_MOUNTED) && extFile.exists() && extFile.isDirectory() && extFile.canWrite()) {
            //外置SD卡的路径
            paths.add(extFile.getAbsolutePath());
        }
        try {
            Runtime runtime = Runtime.getRuntime();
            Process process = runtime.exec("mount");
            InputStream is = process.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line = null;
            int mountPathIndex = 1;
            while ((line = br.readLine()) != null) {
                // format of sdcard file system: vfat/fuse
                if ((!line.contains("fat") && !line.contains("fuse") && !line
                        .contains("storage"))
                        || line.contains("secure")
                        || line.contains("asec")
                        || line.contains("firmware")
                        || line.contains("shell")
                        || line.contains("obb")
                        || line.contains("legacy") || line.contains("data")) {
                    continue;
                }
                String[] parts = line.split(" ");
                int length = parts.length;
                if (mountPathIndex >= length) {
                    continue;
                }
                String mountPath = parts[mountPathIndex];
                if (!mountPath.contains("/") || mountPath.contains("data")
                        || mountPath.contains("Data")) {
                    continue;
                }
                File mountRoot = new File(mountPath);
                if (!mountRoot.exists() || !mountRoot.isDirectory()
                        || !mountRoot.canWrite()) {
                    continue;
                }
                boolean equalsToPrimarySD = mountPath.equals(extFile
                        .getAbsolutePath());
                if (equalsToPrimarySD) {
                    continue;
                }
                //扩展存储卡即TF卡或者SD卡路径
                paths.add(mountPath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return paths;
    }

}