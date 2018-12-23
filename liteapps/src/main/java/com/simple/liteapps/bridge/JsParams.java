package com.simple.liteapps.bridge;

import android.app.Activity;

import com.simple.liteapps.view.BridgeWebView;

public class JsParams {
    private String taskId;
    private String pkg;
    private String service;
    private String method;
    private String url;
    private String body;
    private Object aContext;
    private Object bridgeWebView;
    private Object object;

    public JsParams() {
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getPkg() {
        return pkg;
    }

    public void setPkg(String pkg) {
        this.pkg = pkg;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Object getaContext() {
        return aContext;
    }

    public void setaContext(Activity aContext) {
        this.aContext = aContext;
    }

    public Object getBridgeWebView() {
        return bridgeWebView;
    }

    public void setBridgeWebView(BridgeWebView bridgeWebView) {
        this.bridgeWebView = bridgeWebView;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    @Override
    public String toString() {
        return "JsParams{" +
                "taskId='" + taskId + '\'' +
                ", pkg='" + pkg + '\'' +
                ", service='" + service + '\'' +
                ", method='" + method + '\'' +
                ", url='" + url + '\'' +
                ", body='" + body + '\'' +
                ", aContext=" + aContext +
                ", bridgeWebView=" + bridgeWebView +
                ", object=" + object +
                '}';
    }
}
