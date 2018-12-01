package com.simple.fwlibrary.download;

import com.se7en.endecryption.MD5;
import com.simple.fwlibrary.vutils.FwStringUtils;
import com.simple.fwlibrary.download.base.BreakInfo;
import com.simple.fwlibrary.download.base.FwdlSPUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by e2670 on 2017/9/13.
 * FwDLTaskImp 实现FTP/HTTP下载公共部分
 */

public abstract class FwDLTaskImp extends Thread implements FwDLTaskInf {
    protected String downloadUrl;  // 下载地址
    protected int threadNum;       //下载线程数
    protected String filePath;     // 下载文件路径
    private FwDLListener dlListener; // 下载进度监听

    protected long fileSize;      // 文件大小
    private int oldSize = 0;    // 上一次下载总量
    private int loadedAllSize = 0;  // 当前下载总量

    private boolean isFinished = false;

    protected DLThread[] threads;
    private Timer timer;

    public FwDLTaskImp(String downloadUrl, String filePath, int threadNum, FwDLListener dlListener) {
        this.downloadUrl = downloadUrl;
        this.threadNum = threadNum;
        this.filePath = filePath;
        this.dlListener = dlListener;
    }

    protected BreakInfo breakInfo;

    /**
     * 每秒统计一次已下载文件大小
     */
    public void startTimer() {
        final List<Long> listBreak = breakInfo.getListBreak();
        dlListener.onDLStart();
        final long startTime = System.currentTimeMillis();
        timer = new Timer();
        final long oldCostTime = breakInfo.getCostTime();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                isFinished = true;
                loadedAllSize = 0;
                for (int i = 0; i < threads.length; i++) {
                    long threadLoadSize = threads[i].getDownloadLength() + threads[i].loadSize;
                    loadedAllSize += threadLoadSize;
                    listBreak.set(i, threadLoadSize);
                    if (!threads[i].isCompleted()) {
                        isFinished = false;
                    }
                }

                dlListener.onProgressChange(fileSize, loadedAllSize, loadedAllSize - oldSize);
                oldSize = loadedAllSize;

                if (isFinished) {
                    FwdlSPUtils.remove(breakInfo.getFilePath());
                    timer.cancel();
                    dlListener.onDLFinished(System.currentTimeMillis() - startTime + oldCostTime,
                            loadedAllSize, filePath);
                } else {
                    breakInfo.setCostTime(System.currentTimeMillis() - startTime + oldCostTime);
                    FwdlSPUtils.put(breakInfo.getFilePath(), breakInfo.toString());
                }
            }
        };
        timer.schedule(timerTask, 1000, 1000);
    }

    /**
     * 获取断点信息
     */
    protected boolean getBreakInfo() {
        breakInfo = new BreakInfo();
        String pathKey = MD5.md5(filePath);
        breakInfo.setFilePath(pathKey);
        breakInfo.setThreadNum(threadNum);
        List<Long> list = new ArrayList<>();
        for (int i = 0; i < threadNum; i++) {
            list.add(i, 0L);
        }
        breakInfo.setListBreak(list);
        File file = new File(filePath);
        if (!file.exists()) { // 缓存不存在
            return false;
        }
        try {
            String pathValue = (String) FwdlSPUtils.get(pathKey, " ");
            if (!FwStringUtils.isEmpty(pathValue)) {
                String[] splitPair = pathValue.split(",");  // 分离键值对
                for (String pair : splitPair) {
                    String[] onePair = pair.split("=");
                    if (onePair[0].trim().equals("threadNum")) {  // 解析出线程数
                        threadNum = Integer.parseInt(onePair[1].trim());
                        breakInfo.setThreadNum(threadNum);
                    } else if (onePair[0].trim().equals("listBreak")) {    // 解析出断点位置
                        list = new ArrayList<>();
                        String[] split = onePair[1].trim().split(" ");
                        for (int i = 0; i < split.length; i++) {
                            list.add(i, Long.parseLong(split[i]));
                        }
                        breakInfo.setListBreak(list);
                    }else if(onePair[0].trim().equals("costTime")){
                        breakInfo.setCostTime(Long.parseLong(onePair[1].trim()));
                    }
                }
            }else if(fileSize == file.length()){
                // 文件已下载
                dlListener.onDLFinished(0,fileSize,filePath);
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void cancel() {
        if (timer != null) {
            timer.cancel();
        }
        if (threads != null) {
            for (DLThread thread : threads) {
                thread.isCancel = true;
            }
        }
        dlListener.onDLFailed("download task cancel");
    }
}
