package com.simple.liteapps.base.http.http;




import com.simple.liteapps.base.http.DLThread;
import com.simple.liteapps.base.http.base.DLLog;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by e2670 on 2017/9/10.
 * FtpDLThread 完成每个独立线程的下载任务
 */
public class HttpDLThread extends DLThread {

    /**
     * @param downloadUrl :文件下载地址
     * @param file        :文件保存路径
     * @param blockSize   :下载数据长度
     * @param threadId    :线程ID
     */
    public HttpDLThread(URL downloadUrl, File file, long blockSize , long loadedSize, int threadId) {
        super(downloadUrl, file, blockSize , loadedSize, threadId);
    }

    @Override
    public void run() {
        BufferedInputStream bis = null;
        RandomAccessFile raf = null;

        try {
            URLConnection conn = downloadUrl.openConnection();
            conn.setAllowUserInteraction(true);

            long startPos = blockSize * (threadId - 1) + loadSize; //开始位置
            long endPos = blockSize * threadId - 1; //结束位置
            //设置当前线程下载的起点、终点
            conn.setRequestProperty("Range", "bytes=" + startPos + "-" + endPos);
            DLLog.d(Thread.currentThread().getName() + "  bytes=" + startPos + "-" + endPos);

            byte[] buffer = new byte[cacheSize];
            bis = new BufferedInputStream(conn.getInputStream());

            raf = new RandomAccessFile(file, "rwd");
            raf.seek(startPos);
            int len;
            while ((len = bis.read(buffer, 0, cacheSize)) != -1  && !isCancel) {
                raf.write(buffer, 0, len);
                downloadLength += len;
            }
            isCompleted = true;
            DLLog.d(TAG, "current thread task has finished,all size:" + downloadLength);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (raf != null) {
                try {
                    raf.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
