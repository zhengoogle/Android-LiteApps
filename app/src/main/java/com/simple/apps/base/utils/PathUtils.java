package com.simple.apps.base.utils;

import android.os.Environment;

import java.io.File;

/**
 * Created by e2670 on 2017/4/23.
 * PathUtils
 */

public class PathUtils {

    /**
     * root path
     * /sdcard/
     *
     * @return
     */
    private static String getBasePath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath()
                + File.separator;
    }

    public static String getEnvPath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath()
                + File.separator;
    }

    /**
     * app path
     * /sdcard/--BaseFw/
     *
     * @return
     */
    private static String getGlobalPath() {
        return getBasePath() + "--BaseFw" + File.separator;
    }

    /**
     * app target path
     * /sdcard/--BaseFw/name/
     *
     * @return
     */
    public static String getTargetPath(String name) {
        return getGlobalPath() + name + File.separator;
    }

    /**
     * app path cache
     * /sdcard/--BaseFw/cache/
     *
     * @return
     */
    public static String getCachePath() {
        String path = getTargetPath("cache");
        File file = new File(path);
        if (!file.exists()) {
            if (!file.mkdirs()) {
                return null;
            }
        }
        return path;
    }

    /**
     * app path cache
     * /sdcard/--BaseFw/logs/
     *
     * @return
     */
    public static String getLogPath() {
        String path = getTargetPath("logs");
        File file = new File(path);
        if (!file.exists()) {
            if (!file.mkdirs()) {
                return null;
            }
        }
        return path;
    }

    /**
     * app path video
     * /sdcard/--BaseFw/video/
     *
     * @return
     */
    public static String getVideoPath() {
        String videoPath = getTargetPath("video");
        File file = new File(videoPath);
        if (!file.exists()) {
            if (!file.mkdirs()) {
                return null;
            }
        }
        return videoPath;
    }

    /**
     * app path image
     * /sdcard/--BaseFw/image/
     *
     * @return
     */
    public static String getImagePath() {
        String path = getTargetPath("image");
        File file = new File(path);
        if (!file.exists()) {
            if (!file.mkdirs()) {
                return null;
            }
        }
        return path;
    }

    /**
     * 获取ImageLoader缓存路径
     * /sdcard/--BaseFw/ImageLoader-cache/
     */
    public static String getImageLoaderCachePath() {
        String path = getTargetPath("ImageLoader-cache");
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        return path;
    }

    /**
     * app path
     * /sdcard/--BaseFw/crash-log/
     *
     * @return
     */
    public static String getCrashLogPath() {
        return getGlobalPath() + "crash-log" + File.separator;
    }
}
