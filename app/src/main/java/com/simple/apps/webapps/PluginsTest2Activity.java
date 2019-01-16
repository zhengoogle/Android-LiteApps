package com.simple.apps.webapps;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.simple.apps.R;
import com.simple.fwlibrary.base.comp.FwCompActivity;
import com.simple.fwlibrary.log.xlog.XLog;
import com.simple.liteapps.plugins.manager.StartupManager;

public class PluginsTest2Activity extends FwCompActivity {

    private WebView wvElm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getMainView() {
        return R.layout.activity_plugins_test2;
    }

    @Override
    public void initDatas() {

    }

    @Override
    public void initViews() {
        StartupManager.startLiteApps(this);
        webViewTest();
    }

    /**
     * js原生交互测试
     */
    private void webViewTest() {
        XLog.i(Thread.currentThread().getName());
        wvElm = (WebView) findViewById(R.id.wv_elm);
        // 支持调试
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            wvElm.setWebContentsDebuggingEnabled(true);
        }
        // 如果不设置WebViewClient，请求会跳转系统浏览器
        wvElm.setWebViewClient(new WebViewClient());
        // 支持js执行
        wvElm.getSettings().setJavaScriptEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            wvElm.getSettings().setAllowUniversalAccessFromFileURLs(true);
        }
//        wvElm.loadUrl("file:///android_asset/jsBridge/JsBridgePage01.html");
//        wvElm.loadUrl("http://192.168.31.153:8801/#/");
        wvElm.loadUrl("http://192.168.31.153:10118/#/");
//        wvElm.loadUrl("file:///storage/emulated/0/Download/H5Debug/index.html");


    }

    @Override
    public void initEvents() {

    }

    @Override
    public void loadDatas() {

    }

    /**
     * 返回事件处理
     */
    @Override
    public void onBackPressed() {
        if(wvElm.canGoBack()) {
            wvElm.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
