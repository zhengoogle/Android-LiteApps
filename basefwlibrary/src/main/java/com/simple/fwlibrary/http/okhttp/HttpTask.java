package com.simple.fwlibrary.http.okhttp;


import com.simple.fwlibrary.vutils.FwLog;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by e2670 on 2017/6/2.
 * HttpTask
 * 实现框架层与业务层代码隔离
 */

public class HttpTask {
    private static final MediaType DATA_TYPE = MediaType.parse("application/json; charset=utf-8");

    /**
     * POST请求提交json数据
     * @param iData
     * @param url
     * @param json
     */
    public void postJsonData(final IDataCallback iData, String url, String json) {
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(DATA_TYPE, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                FwLog.e("onFailure:" + e.getMessage());
                e.printStackTrace();
                iData.onError(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                ResponseBody rBody = response.body();
                String resStr = (rBody != null) ? rBody.string() : null;
                FwLog.d("onResponse:" + resStr);
                iData.onSuccess(resStr);
            }
        });
    }

    /**
     * POST请求提交json数据
     * @param iData
     * @param url
     */
    public void getJsonData(final IDataCallback iData, String url) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                FwLog.e("onFailure:" + e.getMessage());
                e.printStackTrace();
                iData.onError(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                ResponseBody rBody = response.body();
                String resStr = (rBody != null) ? rBody.string() : null;
                FwLog.d("onResponse:" + resStr);
                iData.onSuccess(resStr);
            }
        });
    }

    /**
     * 数据回调接口
     */
    public interface IDataCallback {
        /**
         * 成功回调接口
         *
         * @param object
         */
        void onSuccess(Object object);

        /**
         * 失败回调接口
         *
         * @param object
         */
        void onError(Object object);
    }
}
