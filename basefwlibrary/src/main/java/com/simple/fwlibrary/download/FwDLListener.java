package com.simple.fwlibrary.download;

/**
 * Created by e2670 on 2017/9/12.
 * FwDLListener
 */

public interface FwDLListener {
    void onDLStart();

    void onDLFailed(String msg);

    void onDLFinished(long costTime, long dlSize,String filePath);

    void onProgressChange(long fileSize,long loadSize, long changeSize);
}
