package com.simple.liteapps.service.hardware;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.text.format.Formatter;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import com.simple.liteapps.bridge.JsParams;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DeviceInfoService {

    /**
     * device model
     *
     * @return
     */
    public static String getDeviceModel(JsParams jsParams) {
        return Build.MODEL;
    }

    /**
     * device manufacturer
     *
     * @return
     */
    public static String getDeviceManufacturer(JsParams jsParams) {
        return Build.MANUFACTURER;
    }

    /**
     * device brand
     *
     * @return
     */
    public static String getDeviceBrand(JsParams jsParams) {
        return Build.BRAND;
    }

    /**
     * device brand
     *
     * @return
     */
    public static String getDeviceBoard(JsParams jsParams) {
        return Build.BOARD;
    }

    /**
     * device hardware
     *
     * @return
     */
    public static String getDeviceHardware(JsParams jsParams) {
        return Build.HARDWARE;
    }

    /**
     * device screen size
     *
     * @param jsParams
     * @return
     */
    public static String getDeviceScreenSize(JsParams jsParams) {
        int widthPixels = 0;
        int heightPixels = 0;
        WindowManager windowManager = ((Activity) jsParams.getaContext()).getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        display.getMetrics(displayMetrics);
        try {
            Point realSize = new Point();
            Display.class.getMethod("getRealSize", Point.class).invoke(display, realSize);
            widthPixels = realSize.x;
            heightPixels = realSize.y;
        } catch (Exception e) {
            e.printStackTrace();
        }
        double x = Math.pow(widthPixels / displayMetrics.xdpi, 2);
        double y = Math.pow(heightPixels / displayMetrics.ydpi, 2);
        double screenSize = Math.sqrt(x + y);
        return String.format(Locale.ENGLISH, "%.2f", screenSize) + " inches";
    }

    /**
     * device resolution
     *
     * @param jsParams
     * @return
     */
    public static String getDeviceResolution(JsParams jsParams) {
        Display display = ((Activity) jsParams.getaContext()).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getRealSize(size);
        int screenWidth = size.x;
        int screenHeight = size.y;
        return String.valueOf(screenWidth + "x" + screenHeight);
    }

    /**
     * device DPI
     *
     * @param jsParams
     * @return
     */
    public static String getDeviceDPI(JsParams jsParams) {
        DisplayMetrics metrics = ((Activity) jsParams.getaContext()).getResources().getDisplayMetrics();
        int densityDPI = (int) (metrics.density * 160f);
        return String.valueOf(densityDPI) + " dpi";
    }

    /**
     * device RAM
     *
     * @return
     */
    public static String getDeviceRAM(JsParams jsParams) {
        RandomAccessFile randomAccessFile = null;
        String load = null;

        double totRAM = 0;
        String lastValue = "";
        try {
            randomAccessFile = new RandomAccessFile("/proc/meminfo", "r");
            load = randomAccessFile.readLine();

            Pattern pattern = Pattern.compile("(\\d+)");
            Matcher matcher = pattern.matcher(load);
            String value = "";
            while (matcher.find()) {
                value = matcher.group(1);
            }
            randomAccessFile.close();

            totRAM = Double.parseDouble(value);

            lastValue = bytesConvertToGB(totRAM);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return lastValue;
    }

    /**
     * Valid RAM
     * @param jsParams
     * @return
     */
    public static String getDeviceValidRAM(JsParams jsParams) {
        ActivityManager activityManager = (ActivityManager) ((Context)jsParams.getaContext()).getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        if (activityManager != null) {
            activityManager.getMemoryInfo(memoryInfo);
        }
        return Formatter.formatFileSize((Context)jsParams.getaContext(), memoryInfo.availMem);
    }

    private static String bytesConvertToGB(double totRAM) {
        String lastValue = "";
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        double mb = totRAM / 1024.0;
        double gb = totRAM / 1048576.0;
        double tb = totRAM / 1073741824.0;
        if (tb > 1) {
            lastValue = decimalFormat.format(tb).concat(" TB");
        } else if (gb > 1) {
            lastValue = decimalFormat.format(gb).concat(" GB");
        } else if (mb > 1) {
            lastValue = decimalFormat.format(mb).concat(" MB");
        } else {
            lastValue = decimalFormat.format(totRAM).concat(" KB");
        }
        return lastValue;
    }

    /**
     * device internal storage(ROM)
     *
     * @return
     */
    public static String getDeviceInternalStorage(JsParams jsParams) {
        StatFs statFs = new StatFs(Environment.getDataDirectory().getPath());
        long blockSize = statFs.getBlockSize();
        long totalSize = statFs.getBlockCount() * blockSize;
        return bytesConvert2GB(totalSize);
    }

    /**
     * device valid internal storage(ROM)
     *
     * @return
     */
    public static String getDeviceValidInternalStorage(JsParams jsParams) {
        StatFs statFs = new StatFs(Environment.getDataDirectory().getPath());
        long blockSize = statFs.getBlockSize();
        long totalSize = statFs.getFreeBlocks() * blockSize;
        return bytesConvert2GB(totalSize);
    }

    /**
     * @param totalSize
     * @return
     */
    private static String bytesConvert2GB(long totalSize) {
        String lastValue = "";
        DecimalFormat decimalFormat = new DecimalFormat("#.##");

        double kb = totalSize / 1024.0;
        double mb = totalSize / 1048576.0;
        double gb = totalSize / 1073741824.0;

        if (gb > 1) {
            lastValue = decimalFormat.format(gb).concat(" GB");
        } else if (mb > 1) {
            lastValue = decimalFormat.format(mb).concat(" MB");
        } else if (kb > 1) {
            lastValue = decimalFormat.format(kb).concat(" KB");
        } else {
            lastValue = decimalFormat.format(totalSize).concat(" bytes");
        }
        return String.valueOf(lastValue);
    }
}
