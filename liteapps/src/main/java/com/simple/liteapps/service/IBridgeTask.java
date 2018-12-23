package com.simple.liteapps.service;


import com.simple.liteapps.bridge.JsParams;

public interface IBridgeTask {
    String runSyncTasks(JsParams jsParams);

    String runAsyncTasks(JsParams jsParams);
}
