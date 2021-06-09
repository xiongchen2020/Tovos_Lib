package com.example.commonlib;

import android.content.Context;

public  class CommonContext {

    private static Context context;

    public static  void setContext(Context appContext){
        if (appContext!=null){
            context = appContext;
        }

    }

    public static Context getContext() {
        return context;
    }
}
