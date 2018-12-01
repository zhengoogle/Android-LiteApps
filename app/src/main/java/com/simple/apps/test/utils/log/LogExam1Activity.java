package com.simple.apps.test.utils.log;

import android.os.Bundle;

import com.simple.apps.R;
import com.simple.fwlibrary.base.comp.FwCompActivity;
import com.simple.fwlibrary.vutils.FwLog;

public class LogExam1Activity extends FwCompActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getMainView() {
        return R.layout.activity_log_exam1;
    }

    @Override
    public void initDatas() {

    }

    @Override
    public void initViews() {

    }

    @Override
    public void initEvents() {

    }

    @Override
    public void loadDatas() {
        testSimpleLog();
    }

    private void testSimpleLog(){
        FwLog.i("AAA");
    }
}
