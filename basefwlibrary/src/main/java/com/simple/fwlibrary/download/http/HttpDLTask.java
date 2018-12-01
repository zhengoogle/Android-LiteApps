package com.simple.fwlibrary.download.http;

import com.simple.fwlibrary.vutils.FwLog;
import com.simple.fwlibrary.download.FwDLListener;
import com.simple.fwlibrary.download.FwDLTaskImp;

import java.io.File;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by e2670 on 2017/9/10.
 * FtpDLTask 多线程下载任务分配
 */

public class HttpDLTask extends FwDLTaskImp {

    public HttpDLTask(String downloadUrl, String filePath, int threadNum, FwDLListener dlListener) {
        super(downloadUrl, filePath, threadNum, dlListener);
    }

    @Override
    public void run() {
        try {
            URL url = new URL(downloadUrl);
            FwLog.d(downloadUrl);
            URLConnection conn = url.openConnection();
            // 读取下载文件总大小
            fileSize = conn.getContentLength();
            if (fileSize == -1) { // nginx文件服务器
                fileSize = Long.valueOf(conn.getHeaderField("Content-Length"));
            }
            if (fileSize <= 0) {
                FwLog.e("读取文件失败:文件大小" + fileSize);
                return;
            }
            // 最后一个字符是/使用默认文件名，否则使用传入文件名
            if(File.separator.equals(filePath.substring(filePath.length() -1))) {
                String[] split = url.getPath().split(File.separator);
                String fileName = split[split.length - 1];
                filePath += fileName;
            }

            // 恢复断点信息
            try {
                if(getBreakInfo()){
                    return;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            threads = new HttpDLThread[threadNum];

            // 计算每条线程下载的数据长度
            long blockSize = (fileSize % threadNum) == 0 ? fileSize / threadNum
                    : fileSize / threadNum + 1;
            FwLog.d("threadNum:" + threadNum + "  fileSize:" + fileSize + "  blockSize:" + blockSize);

            File file = new File(filePath);
            for (int i = 0; i < threads.length; i++) {
                // 启动线程，分别下载每个线程需要下载的部分
                threads[i] = new HttpDLThread(url, file, blockSize, breakInfo.getListBreak().get(i), (i + 1));
                threads[i].setName("FtpDLThread:" + i);
                threads[i].start();
            }
            startTimer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void cancel() {
        super.cancel();
    }
}
