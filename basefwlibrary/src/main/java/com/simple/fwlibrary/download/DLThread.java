package com.simple.fwlibrary.download;

import com.simple.fwlibrary.download.http.HttpDLThread;

import java.io.File;
import java.net.URL;

/**
 * Created by e2670 on 2017/9/13.
 * DLThread
 */

public class DLThread extends Thread {

    protected static final String TAG = HttpDLThread.class.getSimpleName();

    /**
     * 当前下载是否完成
     */
    protected boolean isCompleted = false;
    /**
     * 当前下载文件长度
     */
    protected long downloadLength = 0;
    /**
     * 文件保存路径
     */
    public File file;
    /**
     * 文件下载路径
     */
    protected URL downloadUrl;
    /**
     * 当前下载线程ID
     */
    protected int threadId;
    /**
     * 线程下载数据长度
     */
    protected long blockSize;

    protected int cacheSize = 100 * 1024;

    protected boolean isCancel = false;

    protected long loadSize = 0;


    /**
     * @param downloadUrl:文件下载地址
     * @param file:文件保存路径
     * @param blockSize:下载数据长度
     * @param loadSize:已下载数据长度
     * @param threadId:线程ID
     */
    public DLThread(URL downloadUrl, File file, long blockSize, long loadSize,
                    int threadId) {
        this.downloadUrl = downloadUrl;
        this.file = file;
        this.threadId = threadId;
        this.blockSize = blockSize;
        this.loadSize = loadSize;
    }

    /**
     * 线程文件是否下载完毕
     */
     boolean isCompleted() {
        return isCompleted;
    }

    /**
     * 线程下载文件长度
     */
     long getDownloadLength() {
        return downloadLength;
    }
}
