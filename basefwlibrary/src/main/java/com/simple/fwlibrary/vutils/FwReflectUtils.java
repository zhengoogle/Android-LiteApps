package com.simple.fwlibrary.vutils;

import android.content.Context;

import java.lang.reflect.Method;

/**
 * Created by e2670 on 2017/9/13.
 * FwReflectUtils
 */

public class FwReflectUtils {
    /**
     * 获取上下文
     * @return
     */
    public static Context getApplicationContext() {
        Context var0 = null;

        try {
            Class var1 = Class.forName("android.app.ActivityThread");
            Method var2 = var1.getDeclaredMethod("currentApplication", new Class[0]);
            var0 = (Context)var2.invoke((Object)null, new Object[0]);
        } catch (Exception var3) {
            var3.printStackTrace();
        }

        return var0;
    }
}
