package com.simple.fwlibrary.base;

import android.content.Context;

import com.simple.fwlibrary.base.crash.CrashHandler;
import com.simple.fwlibrary.log.xlog.LogConfiguration;
import com.simple.fwlibrary.log.xlog.LogLevel;
import com.simple.fwlibrary.log.xlog.XLog;
import com.simple.fwlibrary.log.xlog.flattener.ClassicFlattener;
import com.simple.fwlibrary.log.xlog.interceptor.BlacklistTagsFilterInterceptor;
import com.simple.fwlibrary.log.xlog.printer.AndroidPrinter;
import com.simple.fwlibrary.log.xlog.printer.Printer;
import com.simple.fwlibrary.log.xlog.printer.file.FilePrinter;
import com.simple.fwlibrary.log.xlog.printer.file.naming.DateFileNameGenerator;
import com.simple.fwlibrary.vutils.FwPathUtils;

/**
 * Created by x5670 on 2018/1/4.
 * FwInit 框架初始化调用
 */

public class FwInit {
    /**
     * initFw
     *  框架初始化
     */
    public static void initFw(Context context){
        CrashHandler.getInstance().init(context);
        initXLog();
    }

    /**
     * Initialize XLog.
     */
    private static void initXLog() {
        LogConfiguration config = new LogConfiguration.Builder()
//                .logLevel(BuildConfig.DEBUG ? LogLevel.ALL : LogLevel.NONE) // Specify log level, logs below this level won't be printed, default: LogLevel.ALL
                .logLevel(LogLevel.ALL)
                .tag("FXLog")                                       // Specify TAG, default: "X-LOG"
//                .t()                                                // Enable thread info, disabled by default
                .st(1)                                              // Enable stack trace info with depth 2, disabled by default
                .b()                                                // Enable border, disabled by default
                .addInterceptor(new BlacklistTagsFilterInterceptor(    // Add blacklist tags filter
                        "blacklist1", "blacklist2", "blacklist3"))
                .build();

        Printer androidPrinter = new AndroidPrinter();             // Printer that print the log using android.util.Log
        Printer filePrinter = new FilePrinter                      // Printer that print the log to the file system
                .Builder(FwPathUtils.getLogPath())       // Specify the path to save log file
                .fileNameGenerator(new DateFileNameGenerator())        // Default: ChangelessFileNameGenerator("log")
                // .backupStrategy(new MyBackupStrategy())             // Default: FileSizeBackupStrategy(1024 * 1024)
                .logFlattener(new ClassicFlattener())                  // Default: DefaultFlattener
                .build();

        XLog.init(                                                  // Initialize XLog
                config,                                                // Specify the log configuration, if not specified, will use new LogConfiguration.Builder().build()
                androidPrinter,                                        // Specify printers, if no printer is specified, AndroidPrinter(for Android)/ConsolePrinter(for java) will be used.
                filePrinter);
    }
}
