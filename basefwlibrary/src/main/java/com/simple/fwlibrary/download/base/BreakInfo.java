package com.simple.fwlibrary.download.base;

import java.util.List;

/**
 * Created by e2670 on 2017/9/13.
 * BreakInfo 断点信息
 */

public class BreakInfo {
    private String filePath;
    private long costTime;
    private int threadNum;
    private List<Long> listBreak;

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public long getCostTime() {
        return costTime;
    }

    public void setCostTime(long costTime) {
        this.costTime = costTime;
    }

    public int getThreadNum() {
        return threadNum;
    }

    public void setThreadNum(int threadNum) {
        this.threadNum = threadNum;
    }

    public List<Long> getListBreak() {
        return listBreak;
    }

    public void setListBreak(List<Long> listBreak) {
        this.listBreak = listBreak;
    }

    @Override
    public String toString() {
        String listStr = "";
        for (int i = 0; i < listBreak.size(); i++) {
            listStr += listBreak.get(i) + " ";
        }
        return "threadNum=" + threadNum +
                ",costTime=" + costTime +
                ",listBreak=" + listStr;
    }
}
