package com.simple.liteapps.service;


import com.simple.liteapps.bridge.JsParams;
import com.simple.liteapps.view.BridgeWebView;

public class NativeTest implements IBridgeTask{
    @Override
    public String runSyncTasks(JsParams jsParams) {
        return "runSyncTasks...";
    }

    @Override
    public String runAsyncTasks(JsParams jsParams) {
        ((BridgeWebView)jsParams.getObject()).execWebCallback(jsParams.getTaskId(),"res from native...");
        return "";
    }
}
