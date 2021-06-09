package com.example.commonlib.utils;

public class FastClickUtils {
    // 两次点击按钮之间的点击间隔不能少于1000毫秒
   // private static final int MIN_CLICK_DELAY_TIME = 1000;
    private static long lastClickTime;

    public static boolean isFastClick(int  milliSecond) {

        long curClickTime = System.currentTimeMillis();
        if ((curClickTime - lastClickTime) <= milliSecond) {
            return true;
        }
        lastClickTime = curClickTime;
        return false;
    }
}
