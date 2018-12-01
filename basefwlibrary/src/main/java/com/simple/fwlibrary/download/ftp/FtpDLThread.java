package com.simple.fwlibrary.download.ftp;

import com.simple.fwlibrary.vutils.FwLog;
import com.simple.fwlibrary.download.DLThread;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

/**
 * Created by e2670 on 2017/9/10.
 * FtpDLThread 完成每个独立线程的下载任务
 */
public class FtpDLThread extends DLThread {
    private FtpLogin ftpLogin;

    /**
     * @param file      :文件保存路径
     * @param blockSize :下载数据长度
     * @param threadId  :线程ID
     */
    public FtpDLThread(FtpLogin ftpLogin, File file, long blockSize,long loadedSize, int threadId) {
        super(null, file, blockSize, loadedSize, threadId);
        this.ftpLogin = ftpLogin;
    }


    @Override
    public void run() {
        BufferedInputStream bis = null;
        RandomAccessFile raf = null;

        try {
            FTPClient client = new FTPClient();
            client.connect(ftpLogin.ipAddr, ftpLogin.port);
            if (ftpLogin.userName != null) {
                client.login(ftpLogin.userName, ftpLogin.password);
            }

            client.enterLocalPassiveMode();     //设置被动模式
            client.setFileType(FTP.BINARY_FILE_TYPE);  //设置文件传输模式
            long startPos = blockSize * (threadId - 1) + loadSize; //开始位置
            long endPos = blockSize * threadId - 1;     //结束位置
            client.setRestartOffset(startPos);   //设置恢复下载的位置
            FwLog.d(Thread.currentThread().getName() + "  bytes=" + startPos + "-" + endPos);

            String charSet = "UTF-8";
            if (!FTPReply.isPositiveCompletion(client.sendCommand("OPTS UTF8", "ON"))) {
                charSet = "GBK";
            }
            client.allocate(cacheSize);
            InputStream is = client.retrieveFileStream(new String(ftpLogin.path.getBytes(charSet), "ISO-8859-1"));

            int reply = client.getReplyCode();
            if (!FTPReply.isPositivePreliminary(reply)) {
                client.disconnect();
                FwLog.e("获取文件信息错误，错误码为：" + reply, null);
                return;
            }

            byte[] buffer = new byte[cacheSize];
            bis = new BufferedInputStream(is);

            raf = new RandomAccessFile(file, "rwd");
            raf.seek(startPos);
            int len;
            while ((len = bis.read(buffer, 0, cacheSize)) != -1 && !isCancel) {
                raf.write(buffer, 0, len);
                downloadLength += len;
                if (downloadLength >= endPos - startPos) {
                    break;
                }
            }
            isCompleted = true;
            FwLog.d(TAG, "current thread task has finished,all size:" + downloadLength);
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
