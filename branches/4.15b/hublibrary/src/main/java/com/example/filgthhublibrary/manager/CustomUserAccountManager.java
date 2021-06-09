package com.example.filgthhublibrary.manager;

import android.content.Context;

import com.example.filgthhublibrary.manager.listener.CustomUserAccountListener;


import dji.common.error.DJIError;
import dji.common.useraccount.UserAccountState;
import dji.common.util.CommonCallbacks;
import dji.sdk.useraccount.UserAccountInformation;
import dji.sdk.useraccount.UserAccountManager;

public class CustomUserAccountManager implements UserAccountManager.UserAccountStateChangeListener {

    private UserAccountManager userAccountManager;
    private CustomUserAccountListener customUserAccountListener;
    public CustomUserAccountManager() {
        getUserAccountManager();
    }

    private UserAccountManager getUserAccountManager(){
        userAccountManager = UserAccountManager.getInstance();
        return userAccountManager;
    }

    public void setCustomUserAccountListener(CustomUserAccountListener customUserAccountListener) {
        this.customUserAccountListener = customUserAccountListener;
    }

    /**
     * 获取当前登录用户的账户名
     */
    public void getLoggedInDJIUserAccountName(){
        if (userAccountManager!=null){
            userAccountManager.getLoggedInDJIUserAccountName(new CommonCallbacks.CompletionCallbackWith<String>() {
                @Override
                public void onSuccess(String s) {
                   // ToastUtils.setResultToToast("DJI用户名："+s);
                    customUserAccountListener.getUserAccountName(s);
                }

                @Override
                public void onFailure(DJIError djiError) {
                   // ToastUtils.setResultToToast("DJI用户名错误："+djiError.getDescription());
                    customUserAccountListener.getUserAccountNameFailure(djiError);
                }
            });
        }
    }

    /**
     *
     NOT_LOGGED_IN	未登录
     NOT_AUTHORIZED	未授权
     AUTHORIZED	    已授权
     TOKEN_OUT_OF_DATE	已过期账户
     UNKNOWN	未知.
     * @return
     */
    public UserAccountState getUserAccountState(){
        if (userAccountManager!=null){
            return userAccountManager.getUserAccountState();
        }else {
            return null;
        }
    }

    public void logIntoDJIUserAccount(Context context){
        if (userAccountManager!=null){
            userAccountManager.logIntoDJIUserAccount(context, new CommonCallbacks.CompletionCallbackWith<UserAccountState>() {
                @Override
                public void onSuccess(UserAccountState userAccountState) {

                }

                @Override
                public void onFailure(DJIError djiError) {

                }
            });
        }
    }

    public void logoutOfDJIUserAccount(){
        if (userAccountManager!=null){
            userAccountManager.logoutOfDJIUserAccount(new CommonCallbacks.CompletionCallback() {
                @Override
                public void onResult(DJIError djiError) {

                }
            });
        }
    }

    public void addUserAccountStateChangeListener (){
        if (userAccountManager!=null){
            userAccountManager.addUserAccountStateChangeListener(this);
        }
    }

    public void removeUserAccountStateChangeListener (){
        if (userAccountManager!=null){
            userAccountManager.removeUserAccountStateChangeListener(this);
        }
    }

    @Override
    public void onUserAccountStateChanged(UserAccountState userAccountState, UserAccountInformation userAccountInformation) {

    }
}
