package com.simple.fwlibrary.vutils;

import android.content.Context;
import android.widget.Toast;

import static com.simple.fwlibrary.vutils.FwReflectUtils.getApplicationContext;

/**
 * Created by e2670 on 2017/4/23.
 * FwToast api-24+ 需要activist context
 * ref:
 *  http://blog.csdn.net/u012659709/article/details/51360898
 *  http://www.jianshu.com/p/320d61a22ed7
 */

public class FwToast {

    /**
     * toast show short
     * @param info
     */
    public static void show(String info){
        Toast.makeText(getApplicationContext(),info, Toast.LENGTH_SHORT).show();
        FwLog.d(info);
    }

    public static void show(Context context, String info){
        Toast.makeText(context,info, Toast.LENGTH_SHORT).show();
        FwLog.d(info);
    }

    /**
     * toast show short
     * @param info
     */
    public static void showS(String info){
        Toast.makeText(getApplicationContext(),info, Toast.LENGTH_SHORT).show();
        FwLog.d(info);
    }

    public static void showS(Context context, String info){
        Toast.makeText(context,info, Toast.LENGTH_SHORT).show();
        FwLog.d(info);
    }

    /**
     * toast show long
     * @param info
     */
    public static void showL(String info){
        Toast.makeText(getApplicationContext(),info, Toast.LENGTH_LONG).show();
        FwLog.d(info);
    }

    public static void showL(Context context, String info){
        Toast.makeText(context,info, Toast.LENGTH_LONG).show();
        FwLog.d(info);
    }
}
