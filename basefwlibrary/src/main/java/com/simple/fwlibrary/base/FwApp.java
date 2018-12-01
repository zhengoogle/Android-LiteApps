package com.simple.fwlibrary.base;

import android.app.Application;
import android.content.Context;

import com.simple.fwlibrary.log.xlog.XLog;
import com.simple.fwlibrary.vutils.FwPathUtils;

/**
 * Created by x5670 on 2018/1/4.
 * FwApp
 */

public class FwApp extends Application{
    private static FwApp mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        // 设置文件保存路径
        FwPathUtils.FILE_PATH = getPackageName();
        FwInit.initFw(this);
        XLog.i("Log path: "+ FwPathUtils.getLogPath());
    }

    public static Context getContext(){
        return mContext;
    }
}
