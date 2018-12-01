package com.simple.fwlibrary.view.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.simple.fwlibrary.R;


/**
 * Created by e2670 on 2017/7/9.
 * FwDialog 原生对话框工具类
 */

public class FwDialog {
    private static FwDialog FwDialog;

    public static FwDialog getInstance() {
        if (FwDialog == null) {
            FwDialog = new FwDialog();
        }
        return FwDialog;
    }

    /**
     * 普通Dialog
     * 选择继续或取消
     * builder.create()创建AlertDialog实例
     *
     * @param context
     */
    public void showContinueDialog(Context context, String title, String msg, DialogInterface.OnClickListener listener) {
        showHintDialog(context, title, msg, 0, listener);
    }

    /**
     * 普通Dialog
     * 选择确认或取消
     * @param context
     * @param title
     * @param msg
     * @param listener
     */
    public void showEnterDialog(Context context, String title, String msg, DialogInterface.OnClickListener listener) {
        showHintDialog(context, title, msg, 1, listener);
    }
    // for MainActivity
    public void showEnterDialog1(Context context, String title, String msg, DialogInterface.OnClickListener listener) {
        showHintDialog(context, title, msg, 10, listener);
    }

    /**
     * 普通Dialog
     * 选择更新或取消
     * @param context
     * @param title
     * @param msg
     * @param listener
     */
    public void showUpdateDialog(Context context, String title, String msg, DialogInterface.OnClickListener listener) {
        showHintDialog(context, title, msg, 2, listener);
    }

    /**
     * 提示对话框
     * @param context
     * @param title
     * @param msg
     * @param type  类型0-continue/1-enter/2-update
     * @param listener
     */
    private void showHintDialog(final Context context, String title, String msg, final int type, DialogInterface.OnClickListener listener) {
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(context);
        normalDialog.setIcon(R.drawable.ic_launcher);  // LOGO
        normalDialog.setTitle(title);   // title
        normalDialog.setMessage(msg);   // Msg
        switch (type) {
            case 0:
                normalDialog.setPositiveButton("继续", listener);
                break;
            case 1:
            case 10:
                normalDialog.setPositiveButton("确认", listener);
                break;
            case 2:
                normalDialog.setPositiveButton("更新", listener);
                break;
        }
        normalDialog.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                        if(type == 10){
                            ((Activity)context).finish();
                        }
                    }
                });
        normalDialog.show();
    }

    /**
     * 列表Dialog
     *
     * @param context
     */
    public void showListDialog(final Context context, String title, String[] items,
                               DialogInterface.OnClickListener listener) {
        AlertDialog.Builder listDialog =
                new AlertDialog.Builder(context);
        listDialog.setTitle(title);
        listDialog.setItems(items, listener);
        listDialog.show();
    }

    /**
     * 进度条Dialog
     *
     * @param context
     */
    public ProgressDialog getProgressDialog(Context context, String title) {
        final int MAX_PROGRESS = 100;
        ProgressDialog progressDialog =
                new ProgressDialog(context);
        progressDialog.setProgress(0);
        progressDialog.setTitle(title);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setMax(MAX_PROGRESS);
        progressDialog.show();
        return progressDialog;
    }
}
