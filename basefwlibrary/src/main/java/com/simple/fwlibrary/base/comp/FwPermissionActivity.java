package com.simple.fwlibrary.base.comp;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.simple.fwlibrary.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by e2670 on 2017/10/26.
 * BasePermissionActivity
 *  先取得系统相关访问权限再进入APP
 */

public abstract class FwPermissionActivity extends Activity{
    public Context mContext;
    public static boolean isKeepActionBar = false;
    public List<TaskCallbackInf> listTask = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!isKeepActionBar){  // set no title
            requestWindowFeature(Window.FEATURE_NO_TITLE);
        }
        if(getMainView() != 0) {    // set layout
            setContentView(getMainView());
        }
        setStatusBarColor();
        mContext = this;
        getPermission();
    }

    public abstract int getMainView();
    public abstract void initDatas();
    public abstract void initViews();
    public abstract void initEvents();
    public abstract void loadDatas();

    /**
     * 获取权限
     */
    public void getPermission() {
        // start request permission
        for (int i = 0; i < listTask.size(); i++) {
            listTask.get(i).onStart();
        }
        hasPermission = hasPermission();
        if (!hasPermission) {
            if (shouldShowRequestPermissionRationale()) {
                showPermissionRequestDialog(false);
            } else {
                requestPermission();
            }
        } else {
            for (int i = 0; i < listTask.size(); i++) {
                listTask.get(i).onFinished(true);
            }
            initDatas();
            initViews();
            initEvents();
            loadDatas();
        }
    }

    /**
     * xLog动态权限申请存贮权限：
     * https://github.com/elvishew/xLog
     */
    private static final int PERMISSIONS_REQUEST_EXTERNAL_STORAGE = 1;
    private boolean hasPermission;

    private boolean hasPermission() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED;
    }

    private boolean shouldShowRequestPermissionRationale() {
        return ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                PERMISSIONS_REQUEST_EXTERNAL_STORAGE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                hasPermission = grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED;
                if (!hasPermission) {
                    if (shouldShowRequestPermissionRationale()) {
                        showPermissionRequestDialog(false);
                    } else {
                        showPermissionRequestDialog(true);
                    }
                }else {
                    for (int i = 0; i < listTask.size(); i++) {
                        listTask.get(i).onFinished(true);
                    }
                    initDatas();
                    initViews();
                    initEvents();
                    loadDatas();
                }
            }
        }
    }

    /**
     * Show a dialog for user to explain about the permission.
     */
    private void showPermissionRequestDialog(final boolean gotoSettings) {
        new AlertDialog.Builder(this)
                .setTitle(R.string.permission_request)
                .setMessage(R.string.permission_explanation)
                .setNegativeButton(android.R.string.cancel, null)
                .setPositiveButton(gotoSettings ? R.string.go_to_settings : R.string.allow,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (gotoSettings) {
                                    startAppSettings();
                                } else {
                                    requestPermission();
                                }
                            }
                        })
                .show();
    }

    private void startAppSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivity(intent);
    }

    public interface TaskCallbackInf {
        void onStart();

        void onFinished(boolean isSuccess);
    }

    /**
     * Set status bar color
     */
    private void setStatusBarColor(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            ViewGroup decorViewGroup = (ViewGroup) window.getDecorView();
            View statusBarView = new View(window.getContext());
            int statusBarHeight = getStatusBarHeight(window.getContext());
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, statusBarHeight);
            params.gravity = Gravity.TOP;
            statusBarView.setLayoutParams(params);
            int color = ContextCompat.getColor(this, R.color.colorPrimaryDark);
            statusBarView.setBackgroundColor(color);
            decorViewGroup.addView(statusBarView);

            ViewGroup mContentView = (ViewGroup) window.findViewById(Window.ID_ANDROID_CONTENT);
            View mChildView = mContentView.getChildAt(0);
            if (mChildView != null) {
                //注意不是设置 ContentView 的 FitsSystemWindows, 而是设置 ContentView 的第一个子 View .
                // 预留出系统 View 的空间.
                mChildView.setFitsSystemWindows(true);
            }
        }
    }

    /**
     * GET status bar height
     * @param context
     * @return
     */
    private static int getStatusBarHeight(Context context) {
        int statusBarHeight = 0;
        Resources res = context.getResources();
        int resourceId = res.getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = res.getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }
}
