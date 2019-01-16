package com.simple.liteapps.base.http;

/**
 * Created by e2670 on 2017/9/12.
 * FwDLListener
 */

public interface DLListener {
    void onDLStart();

    void onDLFailed(String msg);

    void onDLFinished(long costTime, long dlSize, String filePath);

    void onProgressChange(long fileSize, long loadSize, long changeSize);
}
