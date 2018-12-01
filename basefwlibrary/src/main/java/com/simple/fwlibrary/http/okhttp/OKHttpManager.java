package com.simple.fwlibrary.http.okhttp;

import com.simple.fwlibrary.vutils.FwLog;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * OKHttpManager
 */

public class OKHttpManager {
    private static OKHttpManager oKhttpManager;

    private static OkHttpClient client;

    public static OKHttpManager getManager() {
        if (oKhttpManager == null) {
            oKhttpManager = new OKHttpManager();
            client = new OkHttpClient.Builder()
                    .connectTimeout(5, TimeUnit.SECONDS)
                    .readTimeout(15, TimeUnit.SECONDS)
                    .build();
        }
        return oKhttpManager;
    }

    /**
     * 同步获取HTTP请求结果
     * @param url
     * @return
     */
    public String requestDataSync(String url) {
        FwLog.d(url);
        String result = null;
        try {
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            Response response = client.newCall(request).execute();
            ResponseBody body = response.body();
            if (body != null) {
                result = body.string();
                FwLog.d(result);
            }
        } catch (IOException e) {
            e.printStackTrace();
            FwLog.e(e.getMessage());
        }
        return result;
    }

    /**
     * 异步获取HTTP请求结果
     * @param url
     * @param callback
     */
    public void requestDataAsycn(String url, final HttpCallback callback) {
        FwLog.d(url);
        Request request;
        try {
            request = new Request.Builder()
                    .url(url)
                    .build();
        }catch (Exception e){
            e.printStackTrace();
            FwLog.d(e.getMessage());
            return;
        }

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                callback.onFail("failed");
            }

            @Override
            public void onResponse(Call call, Response response) {
                if (response.isSuccessful()) {
                    try {
                        if(response.body() != null){
                            callback.onSuccess(response.body().string());
                            return;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        FwLog.e(e.getMessage());
                    }
                    callback.onFail("error");
                }
            }
        });
    }

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    /**
     * 同步提交数据
     * @param url
     * @param json
     * @return
     */
    public String postDataSync(String url,String json) {
        FwLog.d(url);
        String result = null;
        try {
            RequestBody body = RequestBody.create(JSON, json);
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();
            Response response = client.newCall(request).execute();
            ResponseBody responseBody = response.body();
            if (responseBody != null) {
                result = responseBody.string();
                FwLog.d(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
            FwLog.e(e.getMessage());
        }
        return result;
    }

    /**
     * 异步提交数据
     * @param url
     * @param json
     * @param callback
     */
    public void postDataAsync(String url,String json ,final HttpCallback callback) {
        FwLog.d(url);
        RequestBody body = RequestBody.create(JSON, json);
        Request postRequest;
        try {
             postRequest = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();
        }catch (Exception e){
            e.printStackTrace();
            FwLog.e(e.getMessage());
            return;
        }

        client.newCall(postRequest).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                callback.onFail("failed");
            }

            @Override
            public void onResponse(Call call, Response response) {
                if (response.isSuccessful()) {
                    try {
                        if(response.body() != null){
                            callback.onSuccess(response.body().string());
                            return;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        FwLog.d(e.getMessage());
                    }
                    callback.onFail("error");
                }
            }
        });
    }

    interface HttpCallback {
        void onSuccess(String result);
        void onFail(String msg);
    }
}
