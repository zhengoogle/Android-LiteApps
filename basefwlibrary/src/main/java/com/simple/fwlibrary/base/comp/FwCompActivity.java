package com.simple.fwlibrary.base.comp;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.simple.fwlibrary.R;

/**
 * Activity base class
 */
public abstract class FwCompActivity extends AppCompatActivity {
    public Context mContext;
    public static boolean isKeepActionBar = false;
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
        initDatas();
        initViews();
        initEvents();
        loadDatas();
    }

    public abstract int getMainView();
    public abstract void initDatas();
    public abstract void initViews();
    public abstract void initEvents();
    public abstract void loadDatas();

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
