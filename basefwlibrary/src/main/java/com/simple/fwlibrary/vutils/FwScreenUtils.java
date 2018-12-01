package com.simple.fwlibrary.vutils;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Point;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import static com.simple.fwlibrary.vutils.FwReflectUtils.getApplicationContext;


/**
 * Created by e2670 on 2017/5/26.
 * ScreenUtils
 */

public class FwScreenUtils {
    /**
     * 获取设备高度(不包括状态栏)
     *
     * @param paramContext
     * @return
     */
    public static int deviceHeight(Context paramContext) {
        return paramContext.getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * 获取设备宽度
     *
     * @param paramContext
     * @return
     */
    public static int deviceWidth(Context paramContext) {
        return paramContext.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 获取屏幕方向
     *
     * @param paramContext
     * @return
     */
    public static String getOrientation(Context paramContext) {
        if (isLand(paramContext)) {
            return "landscape";
        } else {
            return "portrait";
        }
    }

    /**
     * 获取当前屏幕方向
     *
     * @param context
     * @return
     */
    public static boolean isLand(Context context) {
        return context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    /**
     * 获取屏幕density
     *
     * @param context
     * @return
     */
    public static float getScreenDensity(Context context) {
        Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            display.getRealMetrics(metrics);
            return metrics.density;
        } else {
            return 1;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static int getScreenHeight() {
        return getScreenHeight(getApplicationContext());
    }

    /**
     * 获取屏幕高度
     *
     * @param context
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static int getScreenHeight(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics localDisplayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getRealMetrics(localDisplayMetrics);
        return localDisplayMetrics.heightPixels;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static int getScreenWidth() {
        return getScreenWidth(getApplicationContext());
    }

    /**
     * 获取屏幕宽度
     *
     * @param context
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static int getScreenWidth(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics localDisplayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getRealMetrics(localDisplayMetrics);
        return localDisplayMetrics.widthPixels;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static String getScreenSizeOfDevice(Context context) {
        WindowManager localWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        localWindowManager.getDefaultDisplay().getRealMetrics(displayMetrics);
        int j = displayMetrics.widthPixels;
        int i = displayMetrics.heightPixels;
        double d = Math.sqrt(Math.pow(j, 2.0D) + Math.pow(i, 2.0D)) / displayMetrics.densityDpi;
        FwLog.d("The screenInches " + d);
        return d + "";
    }

    /**
     * 获取屏幕大小
     * @param paramContext
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static String getScreenSizeOfDevice2(Context paramContext) {
        Point localPoint = new Point();
        WindowManager localWindowManager = (WindowManager) paramContext.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        localWindowManager.getDefaultDisplay().getRealMetrics(displayMetrics);
        localWindowManager.getDefaultDisplay().getRealSize(localPoint);
        float f = (float) Math.sqrt(Math.pow(localPoint.x / displayMetrics.xdpi, 2.0D) +
                Math.pow(localPoint.y / displayMetrics.ydpi, 2.0D));
        FwLog.d("Screen inches : " + f);
        return f + "";
    }

    /**
     * 获得状态栏的高度
     *
     * @param context
     * @return
     */
    public static int getStatusHeight(Context context) {

        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
