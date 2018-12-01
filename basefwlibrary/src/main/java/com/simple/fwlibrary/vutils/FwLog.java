package com.simple.fwlibrary.vutils;

import android.text.TextUtils;
import android.util.Log;

/**
 * Created by e2670 on 2017/4/22.
 * MLog
 * 17.04.22 增加对象所在类TAG标识打印
 */

public class FwLog {
    private static final int LOG_LEVEL_VERBOSE = 2;     // 提示级别
    private static final int LOG_LEVEL_DEBUG = 3;       // 调试级别
    private static final int LOG_LEVEL_INFO = 4;        //
    private static final int LOG_LEVEL_WARN = 5;        // 提醒级别
    private static final int LOG_LEVEL_ERROR = 6;       // 错误级别
    private static final String LOG_TAG = "==FwLog==> ";     // Log标示
    // 定义Log级别控制
    private static int LOG_LEVEL = true ? LOG_LEVEL_VERBOSE : LOG_LEVEL_WARN;

    private static String customTagPrefix = "";

    /**
     * 生成LOG tag，所在类-方法与行，参考xUtils框架
     * http://blog.csdn.net/androidresearch/article/details/45605325
     * http://www.cnblogs.com/tc310/p/5345476.html
     *
     * @return
     */
    private static String generateTag() {
        StackTraceElement caller = Thread.currentThread().getStackTrace()[4];
        String tag = "%s.%s(L:%d)";
        String callerClazzName = caller.getClassName();
        callerClazzName = callerClazzName.substring(callerClazzName.lastIndexOf(".") + 1);
        tag = String.format(tag, callerClazzName, caller.getMethodName(), caller.getLineNumber());
        tag = TextUtils.isEmpty(customTagPrefix) ? tag : customTagPrefix + ":" + tag;
        return tag;
    }

    /**
     * 提示日志
     *
     * @param tag
     * @param info
     */
    public static void v(String tag, String info) {
        if (LOG_LEVEL_VERBOSE >= LOG_LEVEL) {
            tag = LOG_TAG + tag;
            Log.v(tag, info + "");
        }
    }

    /**
     * 调试日志
     *
     * @param tag
     * @param info
     */
    public static void d(String tag, String info) {
        if (LOG_LEVEL_DEBUG >= LOG_LEVEL) {
            tag = LOG_TAG + tag;
            Log.i(tag, info + "");
        }
    }

    /**
     * TAG为object对象说在类
     *
     * @param object
     * @param info
     */
    public static void d(Object object, String info) {
        if (LOG_LEVEL_DEBUG >= LOG_LEVEL) {
            String tag = LOG_TAG + object.getClass().getSimpleName();
            Log.i(tag, info + "");
        }
    }

    /**
     * TAG为打印该信息所在类-方法-所在行
     *
     * @param info
     */
    public static void d(String info) {
        if (LOG_LEVEL_DEBUG >= LOG_LEVEL) {
            String tag = generateTag();
            Log.i(LOG_TAG + tag, info + "");
        }
    }

    /**
     * 提示日志
     *
     * @param tag
     * @param info
     */
    public static void i(String tag, String info) {
        if (LOG_LEVEL_INFO >= LOG_LEVEL) {
            tag = LOG_TAG + tag;
            Log.i(tag, info + "");
        }
    }

    public static void i(Object object, String info) {
        if (LOG_LEVEL_INFO >= LOG_LEVEL) {
            String tag = LOG_TAG + object.getClass().getSimpleName();
            Log.i(tag, info + "");
        }
    }

    public static void i(String info) {
        if (LOG_LEVEL_INFO >= LOG_LEVEL) {
            String tag = generateTag();
            Log.i(LOG_TAG + tag, info + "");
        }
    }

    /**
     * 提醒日志
     *
     * @param tag
     * @param info
     */
    public static void w(String tag, String info) {
        if (LOG_LEVEL_WARN >= LOG_LEVEL) {
            tag = LOG_TAG + tag;
            Log.w(tag, info + "");
        }
    }

    /**
     * 错误日志
     *
     * @param tag
     * @param info
     */
    public static void e(String tag, String info) {
        if (LOG_LEVEL_ERROR >= LOG_LEVEL) {
            tag = LOG_TAG + tag;
            Log.e(tag, info + "");
        }
    }

    public static void e(Object object, String info) {
        if (LOG_LEVEL_ERROR >= LOG_LEVEL) {
            String tag = LOG_TAG + object.getClass().getSimpleName();
            Log.e(tag, info + "");
        }
    }

    public static void e(String info) {
        if (LOG_LEVEL_DEBUG >= LOG_LEVEL) {
            String tag = generateTag();
            Log.e(LOG_TAG + tag, info + "");
        }
    }
}
