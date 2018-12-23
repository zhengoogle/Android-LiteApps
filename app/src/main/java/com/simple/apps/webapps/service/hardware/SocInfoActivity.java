package com.simple.apps.webapps.service.hardware;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.simple.apps.R;
import com.simple.fwlibrary.log.xlog.XLog;
import com.simple.liteapps.service.hardware.SocInfoService;

/**
 * SocInfo test
 */
public class SocInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soc_info);

        XLog.w(SocInfoService.getCPUModel());
    }
}
