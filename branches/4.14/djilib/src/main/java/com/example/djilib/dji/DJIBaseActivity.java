package com.example.djilib.dji;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.view.View;

public class DJIBaseActivity  extends Activity {

    private static boolean isAppStarted = false;

    public static boolean isStarted() {
        return isAppStarted;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isAppStarted = true;
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        isAppStarted = false;
    }
}
