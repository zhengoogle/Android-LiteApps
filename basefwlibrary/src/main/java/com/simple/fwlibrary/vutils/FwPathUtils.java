package com.simple.fwlibrary.vutils;

import android.os.Environment;

import java.io.File;

/**
 * Created by e2670 on 2017/4/23.
 * PathUtils
 */

public class FwPathUtils {
    public static String FILE_PATH = "FwLibrary";

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
     * /sdcard/--FwLibrary/
     *
     * @return
     */
    private static String getGlobalPath() {
        return getBasePath() + "--" + FILE_PATH + File.separator;
    }

    /**
     * app target path
     * /sdcard/--FwLibrary/name/
     *
     * @return
     */
    public static String getTargetPath(String name) {
        return getGlobalPath() + name + File.separator;
    }

    /**
     * app path cache
     * /sdcard/--FwLibrary/cache/
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
     * app path video
     * /sdcard/--FwLibrary/video/
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
     * /sdcard/--FwLibrary/image/
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
     * app path cache
     * /sdcard/--FwLibrary/logs/
     *
     * @return
     */
    public static String getLogPath() {
        String path = getTargetPath("logs");
        File file = new File(path);
        if (!file.exists()) {
            if (!file.mkdirs()) {
                return path;
            }
        }
        return path;
    }

    /**
     * 获取ImageLoader缓存路径
     * /sdcard/--FwLibrary/ImageLoader-cache/
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
     * /sdcard/--FwLibrary/crash-log/
     *
     * @return
     */
    public static String getCrashLogPath() {
        return getGlobalPath() + "crash-log" + File.separator;
    }
}
