package com.simple.liteapps.view;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

import com.simple.liteapps.base.utils.MLog;

public class BridgeWebView extends WebView {
    public BridgeWebView(Context context) {
        super(context);
    }

    public BridgeWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BridgeWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void execWebCallback(final String id, final String res) {
        post(new Runnable() {
            @Override
            public void run() {
                String runRes = "javascript:window.onNativeCallback(" + id + ",\"" + res + "\")";
                MLog.i(runRes);
                loadUrl(runRes);
//                loadUrl("javascript:window.onNativeCallback(" + id + ")");
            }
        });
    }
}
