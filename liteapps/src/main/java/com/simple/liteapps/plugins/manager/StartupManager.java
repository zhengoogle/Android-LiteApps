package com.simple.liteapps.plugins.manager;

import android.content.Context;

import com.simple.liteapps.base.utils.JsonUtils;
import com.simple.liteapps.base.utils.SPUtils;
import com.simple.liteapps.plugins.info.Version;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class StartupManager {
    private static final String KEY_APP = "lite_apps_version";
    public static void startLiteApps(Context context) {
        String res = (String) SPUtils.get(KEY_APP, "");
        if (res != null && res.length() != 0){
        }else {
//            context.getResources().getAssets().open()
            InputStream inputStream = context.getClass().getResourceAsStream("/assets/liteapps/config.json");
            res = readStream(inputStream);
        }
        Version version = JsonUtils.toBean(res, Version.class);
    }

    /**
     * 读取 InputStream 到 String字符串中
     * https://www.cnblogs.com/-Tiger/p/7146567.html
     */
    public static String readStream(InputStream in) {
        try {
            //<1>创建字节数组输出流，用来输出读取到的内容
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            //<2>创建缓存大小
            byte[] buffer = new byte[1024]; // 1KB
            //每次读取到内容的长度
            int len = -1;
            //<3>开始读取输入流中的内容
            while ((len = in.read(buffer)) != -1) { //当等于-1说明没有数据可以读取了
                baos.write(buffer, 0, len);   //把读取到的内容写到输出流中
            }
            //<4> 把字节数组转换为字符串
            String content = baos.toString();
            //<5>关闭输入流和输出流
            in.close();
            baos.close();
            //<6>返回字符串结果
            return content;
        } catch (Exception e) {
            e.printStackTrace();
            return  e.getMessage();
        }
    }
}
