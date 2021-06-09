package com.example.filgthhublibrary.util;

import android.app.ProgressDialog;
import android.content.Context;

public class DialogUtils {

    public static ProgressDialog waitingDialog;
    public static void showWaitingDialog(Context context,String str) {
        /* 等待Dialog具有屏蔽其他控件的交互能力
         * @setCancelable 为使屏幕不可点击，设置为不可取消(false)
         * 下载等事件完成后，主动调用函数关闭该Dialog
         */
        waitingDialog= new ProgressDialog(context);
        waitingDialog.setTitle("提示");
        waitingDialog.setMessage(str);
        waitingDialog.setIndeterminate(true);
        waitingDialog.setCancelable(false);
        waitingDialog.show();
    }

    public static void dismissWaitingDialog(){
        if (waitingDialog!=null){
            waitingDialog.dismiss();
        }

    }
    public static boolean isWaitingDialogShow(){
        if(waitingDialog!=null){
            return waitingDialog.isShowing();
        }
        return false;
    }

}
