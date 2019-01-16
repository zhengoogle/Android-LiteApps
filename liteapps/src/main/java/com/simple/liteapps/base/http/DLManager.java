package com.simple.liteapps.base.http;


import com.simple.liteapps.base.http.ftp.FtpDLTask;
import com.simple.liteapps.base.http.http.HttpDLTask;

/**
 * Created by e2670 on 2017/9/12.
 * DLManager
 *  支持FTP/HTTP下载
 *  支持断点续传
 *  支持文件缓存
 *  支持多线程下载
 *  支持指定文件名/默认文件名，根据传入路径适配
 *  todo:FTP/HTTP文件上传
 * 依赖：
 *  commons-net-3.6.jar // FTP client
 *  tools.jar   // MD5
 */

public class DLManager {
    private DLTaskImp fwDLTaskImp;
    /**
     * 执行下载任务
     * @param downloadUrl
     * @param filePath
     * @param threadNum
     * @param dlListener
     */
    public void download(String downloadUrl, String filePath, int threadNum, DLListener dlListener){
        if (downloadUrl !=null && downloadUrl.length() > 6){
            boolean isFtp = downloadUrl.substring(0, 5).toLowerCase().contains("ftp");
            if(isFtp){
                fwDLTaskImp = new FtpDLTask(downloadUrl,  filePath,  threadNum,  dlListener);
            }else {
                fwDLTaskImp = new HttpDLTask(downloadUrl,  filePath,  threadNum,  dlListener);
            }
            fwDLTaskImp.start();
        }else {
            dlListener.onDLFailed("url error");
        }
    }

    /**
     * 取消下载任务
     */
    public void cancel(){
        fwDLTaskImp.cancel();
    }
}
