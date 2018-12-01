package com.simple.fwlibrary.vutils;

import android.content.Context;
import android.content.Intent;

/**
 * Created by x5670 on 2018/1/4.
 * FwJumpUtils
 */

public class FwJumpUtils {
    /**
     * 跳转到目标页面
     * @param context
     * @param cls
     */
    public static void jumpToTarget(Context context,Class<?> cls){
        Intent intent = new Intent(context,cls);
        context.startActivity(intent);
    }
}
