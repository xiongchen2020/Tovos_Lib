package com.example.commonlib.utils;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;




/**
 * AlertDialog封装
 */

public class DialogManager {

    private DialogManager() {

    }

    /**
     * Show dialog with single button.
     *
     */
    public static AlertDialog showSingleButton(Context context,int iconId, String title,String message,
                                               String sure, boolean cancelable, DialogInterface.OnClickListener click) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setIcon(iconId);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(sure, click);
        AlertDialog dialog = builder.create();
        dialog.setCancelable(cancelable);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        return dialog;
    }




    /**
     * Show dialog with two buttons.
     */
    public static AlertDialog showSelectDialog(Context context, String title,
                                               String msg, String ok, String cancel, boolean cancelable,
                                               DialogInterface.OnClickListener okClick,DialogInterface.OnClickListener caancelClick) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setPositiveButton(ok, okClick);
        builder.setNegativeButton(cancel, caancelClick);
        AlertDialog dialog = builder.create();
        dialog.setCancelable(cancelable);
        dialog.setCanceledOnTouchOutside(cancelable);
        dialog.show();
        return dialog;
    }


    public static ProgressDialog showProgressDialog(Context context,
                                                     String msg, boolean isCancelable,String cancel,
                                                    DialogInterface.OnClickListener okClick) {
        ProgressDialog progressDialog = new ProgressDialog(context);

      //  progressDialog.setTitle(title);

        progressDialog.setMessage(msg);
        progressDialog.setCancelable(isCancelable);
      //  progressDialog.setOnCancelListener(cancelListener);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setButton(ProgressDialog.BUTTON_NEGATIVE, cancel, okClick);


        progressDialog.show();

        return progressDialog;
    }












}
