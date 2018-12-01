package com.simple.fwlibrary.download.ftp;

import com.simple.fwlibrary.vutils.FwLog;
import com.simple.fwlibrary.download.FwDLListener;
import com.simple.fwlibrary.download.FwDLTaskImp;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.File;

/**
 * Created by e2670 on 2017/9/10.
 * FtpDLTask 多线程下载任务分配
 */

public class FtpDLTask extends FwDLTaskImp {

    public FtpDLTask(String downloadUrl, String filePath, int threadNum, FwDLListener dlListener) {
        super(downloadUrl, filePath, threadNum, dlListener);
    }


    @Override
    public void run() {
        try {
            FwLog.d(downloadUrl);
            FTPClient client = new FTPClient();
            FtpLogin ftpLogin;
            try {
                ftpLogin = parseFtpUrl(downloadUrl);
            } catch (Exception e) {
                e.printStackTrace();
                FwLog.e("ftp url解析错误");
                return;
            }
            client.connect(ftpLogin.ipAddr, ftpLogin.port);
            if (ftpLogin.userName != null) {
                client.login(ftpLogin.userName, ftpLogin.password);
            }

            int reply = client.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                client.disconnect();
                FwLog.e("无法连接到ftp服务器，错误码为：" + reply);
                return;
            }
            // 读取下载文件总大小
            String charSet = "UTF-8";
            if (!FTPReply.isPositiveCompletion(client.sendCommand("OPTS UTF8", "ON"))) {
                charSet = "GBK";
            }
            FTPFile[] files = client.listFiles(new String(ftpLogin.path.getBytes(charSet), "ISO-8859-1"));
            if (files.length > 0) {
                fileSize = files[0].getSize();
            }

            if (fileSize <= 0) {
                FwLog.e("读取文件失败:文件大小" + fileSize);
                return;
            }
            if(File.separator.equals(filePath.substring(filePath.length() -1))){
                String fileName = files[0].getName();
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
            threads = new FtpDLThread[threadNum];

            // 计算每条线程下载的数据长度
            long blockSize = (fileSize % threadNum) == 0 ? fileSize / threadNum
                    : fileSize / threadNum + 1;
            FwLog.d("threadNum:" + threadNum + "  fileSize:" + fileSize + "  blockSize:" + blockSize);

            File file = new File(filePath);
            for (int i = 0; i < threads.length; i++) {
                // 启动线程，分别下载每个线程需要下载的部分
                threads[i] = new FtpDLThread(ftpLogin, file, blockSize, breakInfo.getListBreak().get(i), (i + 1));
                threads[i].setName("FtpDLThread:" + i);
                threads[i].start();
            }
            startTimer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * FTP url解析
     *
     * @param fullUrl
     * @return
     */
    private FtpLogin parseFtpUrl(String fullUrl) throws Exception {
        FtpLogin ftpLogin = new FtpLogin();
        fullUrl = fullUrl.trim();
        String res;
        int beginIndex = "ftp://".length();
        int endIndex = fullUrl.indexOf('/', beginIndex);
        if (endIndex != -1) {
            res = fullUrl.substring(beginIndex, endIndex);
            String[] splitRes = res.split("@");
            if (splitRes.length > 1) {    // IP+Login
                String[] splitIp = splitRes[1].split(":");
                ftpLogin.ipAddr = splitIp[0];
                if (splitIp.length > 1) {
                    ftpLogin.port = Integer.parseInt(splitIp[1]);
                } else {
                    ftpLogin.port = 21;
                }
                String[] splitUser = splitRes[0].split(":");
                ftpLogin.userName = splitUser[0];
                if (splitUser.length > 1) {
                    ftpLogin.password = splitUser[1];
                } else {
                    ftpLogin.password = "";
                }
            } else { // IP
                String[] splitIp = splitRes[0].split(":");
                ftpLogin.ipAddr = splitIp[0];
                if (splitIp.length > 1) {
                    ftpLogin.port = Integer.parseInt(splitIp[1]);
                } else {
                    ftpLogin.port = 21;
                }
            }
            ftpLogin.path = fullUrl.substring(endIndex);
        } else {
            throw new Exception("URL解析错误");
        }
        return ftpLogin;
    }

    @Override
    public void cancel() {
        super.cancel();
    }
}
