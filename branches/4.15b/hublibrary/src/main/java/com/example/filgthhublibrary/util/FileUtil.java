package com.example.filgthhublibrary.util;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FileUtil {
    public static byte[] path2byte(String path) {
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


        } catch (FileNotFoundException e) {
            // DialogUtils.dismissWaitingDialog();
            ToastUtils.setResultToToast("上传视频失败: " + e.getMessage());
        } catch (IOException e) {
            // DialogUtils.dismissWaitingDialog();
            ToastUtils.setResultToToast("上传视频失败: " + e.getMessage());
        }
        return buffer;
    }
}
