package com.simple.liteapps.bridge;

import android.app.Activity;
import android.content.Context;
import android.webkit.JavascriptInterface;


import com.simple.liteapps.base.utils.JsonUtils;
import com.simple.liteapps.base.utils.MLog;
import com.simple.liteapps.service.NativeTest;
import com.simple.liteapps.view.BridgeWebView;

import java.lang.reflect.Method;

public class JsBridge implements IBridge{
    private Activity aContext;
    private BridgeWebView bridgeWebView;

    public JsBridge(Activity context,BridgeWebView bridgeWebView) {
        this.aContext = context;
        this.bridgeWebView = bridgeWebView;
    }

    /**
     * sync direct invoke
     * @param params
     * @return
     */
    @Override
    @JavascriptInterface
    public String sync(String params) {
        MLog.i(params);
        JsParams jsParams = JsonUtils.toBean(params, JsParams.class);
        jsParams.setaContext(aContext);
        jsParams.setBridgeWebView(bridgeWebView);
        return new NativeTest().runSyncTasks(jsParams);
    }

    /**
     * async direct invoke
     * @param params
     * @return
     */
    @Override
    @JavascriptInterface
    public String async(String params) {
        MLog.i(params);
        JsParams jsParams = JsonUtils.toBean(params, JsParams.class);
        jsParams.setaContext(aContext);
        jsParams.setBridgeWebView(bridgeWebView);
        return new NativeTest().runAsyncTasks(jsParams);
    }

    /**
     * Js native syncBridge reflect
     * @param params
     * @return
     */
    @Override
    @JavascriptInterface
    public String syncBridge(String params) {
        MLog.i(params);
        JsParams jsParams = JsonUtils.toBean(params, JsParams.class);
        jsParams.setaContext(aContext);
        jsParams.setBridgeWebView(bridgeWebView);
        Class<?> clazz = null;
        try {
            clazz = Class.forName(jsParams.getPkg() + "." + jsParams.getService());
            Method method = clazz.getMethod(jsParams.getMethod(), JsParams.class);
            return method.invoke(clazz.newInstance(),jsParams).toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * Js native asyncBridge reflect
     * @param params
     * @return
     */
    @Override
    @JavascriptInterface
    public String asyncBridge(String params) {
        MLog.i(params);
        JsParams jsParams = JsonUtils.toBean(params, JsParams.class);
        jsParams.setaContext(aContext);
        jsParams.setBridgeWebView(bridgeWebView);
        Class<?> clazz = null;
        try {
            clazz = Class.forName(jsParams.getPkg() + "." + jsParams.getService());
            Method method = clazz.getMethod(jsParams.getMethod(), JsParams.class);
            return method.invoke(clazz.newInstance(),jsParams) + "";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
