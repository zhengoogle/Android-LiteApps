package com.simple.liteapps.view;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.webkit.WebViewClient;

import com.simple.liteapps.R;
import com.simple.liteapps.bridge.JsBridge;

public class HybridApps extends AppCompatActivity {
    private final String injectObj = "$jsBridge";   // inject to WebView bridge object
    private BridgeWebView wvElm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hybrid);

        initLiteApps();
    }

    private void initLiteApps(){
        wvElm = (BridgeWebView) findViewById(R.id.wv_elm);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            wvElm.setWebContentsDebuggingEnabled(true);
        }
        wvElm.setWebViewClient(new WebViewClient());
        wvElm.getSettings().setJavaScriptEnabled(true);
        wvElm.getSettings().setAllowUniversalAccessFromFileURLs(true);
        wvElm.addJavascriptInterface(new JsBridge(this, wvElm), injectObj);
        wvElm.loadUrl("http://192.168.31.153:10118/#/");
    }

    @Override
    protected void onDestroy() {
        wvElm.removeJavascriptInterface(injectObj);
        super.onDestroy();
    }

    /**
     * 返回事件处理
     */
    @Override
    public void onBackPressed() {
        if (wvElm.canGoBack()) {
            wvElm.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
