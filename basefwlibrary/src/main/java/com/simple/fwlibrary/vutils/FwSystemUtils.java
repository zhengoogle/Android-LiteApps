package com.simple.fwlibrary.vutils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.telephony.TelephonyManager;
import android.text.format.Formatter;

import com.simple.fwlibrary.BuildConfig;
import com.simple.fwlibrary.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;


/**
 * Created by e2670 on 2017/5/2.
 * SystemInfo
 * ref:http://blog.csdn.net/shenyuanqing/article/details/47445959
 */

public class FwSystemUtils {
    /**
     * getVersionInfo by build config
     *
     * @return
     */
    public static String getVersionNameWithSvn(Context context) {
        return "v" + BuildConfig.VERSION_NAME + "(" + BuildConfig.VERSION_CODE + ")";
    }

    public static String getVersionSvn() {
        return BuildConfig.VERSION_CODE + "";
    }

    /**
     * getVersionInfo by build config
     *
     * @return
     */
    public static String getVersionInfoByConfig() {
        return "v" + BuildConfig.VERSION_NAME + "(" + BuildConfig.VERSION_CODE + ")";
    }

    /**
     * getVersionInfo
     *
     * @param context
     * @return
     */
    public static String getVersionInfo(Context context) {
        return "v" + getVersionName(context) + "(" + getVersionCode(context) + ")";
    }

    /**
     * get App versionCode
     *
     * @param context
     * @return
     */
    public static String getVersionCode(Context context) {
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageInfo;
        String versionCode = "";
        try {
            packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            versionCode = packageInfo.versionCode + "";
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    public static int getVersionCode() {
        return BuildConfig.VERSION_CODE;
    }

    /**
     * get App versionName
     *
     * @param context
     * @return
     */
    public static String getVersionName(Context context) {
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageInfo;
        String versionName = "";
        try {
            packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            versionName = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }

    public static String getVersionName() {
        return BuildConfig.VERSION_NAME;
    }

    /**
     * 判断当前 App 是否为 系统 APP
     * http://www.bkjia.com/Androidjc/880029.html
     *
     * @param context
     * @return
     */
    public static boolean isSystemApp(Context context) {
        boolean isSystem = false;
        if (context != null) {
            String packageName = context.getPackageName();
            try {
                PackageInfo packageInfo = context.getPackageManager().getPackageInfo(packageName, 0);
                if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0) {
                    isSystem = false;
                } else {
                    isSystem = true;
                }
            } catch (PackageManager.NameNotFoundException e) {
                isSystem = false;
            }
        }
        return isSystem;
    }

    /**
     * 获取设备唯一ID标识
     * http://www.cnblogs.com/lvcha/p/3721091.html
     *
     * @param context
     * @return
     */
    public static String getDeviceId(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String deviceId = tm.getDeviceId();
        if (deviceId == null) {
            deviceId = getWifiMacAddress();
        }
        return deviceId;
    }

    /**
     * 获取WIFI MAC地址
     *
     * @return
     */
    public static String getWifiMacAddress() {
        String macSerial = null;
        String str = "";
        try {
            Process pp = Runtime.getRuntime().exec(
                    "cat /sys/class/net/wlan0/address ");
            InputStreamReader ir = new InputStreamReader(pp.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);
            for (; null != str; ) {
                str = input.readLine();
                if (str != null) {
                    macSerial = str.trim();// 去空格
                    break;
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return macSerial;
    }

    /**
     * 获取RAM大小
     *
     * @param paramContext
     * @return
     */
    public static String getTotalRam(Context paramContext) {
        String path = "/proc/meminfo";
        String firstLine = null;
        float totalRam = 0;
        try {
            FileReader fileReader = new FileReader(path);
            BufferedReader br = new BufferedReader(fileReader, 8192);
            firstLine = br.readLine().split("\\s+")[1];
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (firstLine != null) {
            totalRam = (float) Math.ceil((new Float(Float.valueOf(firstLine) / (1024 * 1024)).doubleValue()));
        }
        return totalRam + "GB";
    }

    /**
     * 获取可用RAM大小
     *
     * @param paramContext
     * @return
     */
    public static String getTotalRamValid(Context paramContext) {
        String path = "/proc/meminfo";
        String firstLine = null;
        float totalRam = 0;
        try {
            FileReader fileReader = new FileReader(path);
            BufferedReader br = new BufferedReader(fileReader, 8192);
            firstLine = br.readLine().split("\\s+")[1];
            firstLine = br.readLine().split("\\s+")[1];
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (firstLine != null) {
            totalRam = (float) Math.ceil((new Float(Float.valueOf(firstLine) / (1024 * 1024)).doubleValue()));
        }
        return totalRam + "GB";
    }

    /**
     * 获取ROM大小
     *  http://blog.csdn.net/hanmengaidudu/article/details/18628399
     * @param context
     * @return
     */
    public static String getTotalInternalMemorySize(Context context) {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockCount = stat.getBlockCount();
        long blockSize = stat.getBlockSize();

        String totalSize = Formatter.formatFileSize(context, blockCount * blockSize);
        return totalSize;
    }

    /**
     * 获取可用ROM大小
     *  http://blog.csdn.net/loongggdroid/article/details/12304695
     *
     * @param context
     * @return
     */
    public static String getRomAvailableSize(Context context) {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        return Formatter.formatFileSize(context, blockSize * availableBlocks);
    }

    public static String getSysBrand() {
        return Build.BRAND;
    }

    public static String getSysDevice() {
        return Build.DEVICE;
    }

    public static String getSysHardware() {
        return Build.HARDWARE;
    }

    public static String getSysModel() {
        return Build.MODEL;
    }

    public static String getSysProduct() {
        return Build.PRODUCT;
    }

    public static String getSysSdkVersion() {
        return Build.VERSION.SDK_INT + "";
    }

    public static String getSysVersion() {
        return Build.VERSION.RELEASE;
    }
}
