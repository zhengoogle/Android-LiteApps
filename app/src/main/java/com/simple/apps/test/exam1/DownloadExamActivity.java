package com.simple.apps.test.exam1;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.simple.apps.R;
import com.simple.apps.base.utils.PathUtils;
import com.simple.fwlibrary.base.comp.FwActivity;
import com.simple.fwlibrary.download.FwDLListener;
import com.simple.fwlibrary.download.FwDLManager;

import java.text.DecimalFormat;

public class DownloadExamActivity extends FwActivity {
    private Button btnStart;
    private TextView tvDlInfo;
    private TextView tvDlAverage;
    private FwDLManager dlManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getMainView() {
        return R.layout.activity_download_exam;
    }

    @Override
    public void initDatas() {

    }

    @Override
    public void initViews() {
        btnStart = (Button) findViewById(R.id.ftp_btn_start);
        tvDlInfo = (TextView) findViewById(R.id.ftp_tv_dl_info);
        tvDlAverage = (TextView) findViewById(R.id.ftp_tv_dl_average);
    }

    boolean isStart = false;
    @Override
    public void initEvents() {
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isStart){
                    btnStart.setText("停止下载");
                    startMutitaskDownload();
                }else {
                    if(dlManager != null){
                        dlManager.cancel();
                        btnStart.setText("开始下载");
                    }
                }
                isStart = !isStart;
            }
        });
    }

    @Override
    public void loadDatas() {

    }

//    String downloadUrl = "http://192.168.31.162/FileServer/SoftApk/UC-11.5.5.943.apk";
    String downloadUrl = "ftp://root@192.168.31.162/SoftApk/UC-11.5.5.943.apk";
//    String filepath = PathUtils.getCachePath() + "UC-11.5.5.943.apk";
    String filepath = PathUtils.getCachePath();
    /**
     * 多线程下载
     *  基于：http://blog.csdn.net/qwe511455842/article/details/76603675
     *  Apache Commons Net 3.6
     *      http://commons.apache.org/proper/commons-net/download_net.cgi
     *
     */
    private void startMutitaskDownload(){
        dlManager = new FwDLManager();
        dlManager.download(downloadUrl, filepath, 5, new FwDLListener() {
            @Override
            public void onDLStart() {

            }

            @Override
            public void onDLFailed(String msg) {

            }

            @Override
            public void onDLFinished(final long costTime, final long dlSize, String filePath) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(costTime != 0){
                            DecimalFormat dFormat   =   new   DecimalFormat("##0.00");
                            Double res = dlSize * 1.0 / 1024 / costTime;
                            String speed = dFormat.format(res);
                            tvDlAverage.setText("平均下载速度：" + speed + "Mb/s ");
                        }
                    }
                });
            }

            @Override
            public void onProgressChange(final long fileSize,final long loadedSize, final long changeSize) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvDlInfo.setText("下载速度：" + changeSize / 1024 / 1024 + "Mb/s " +
                                "下载进度：" + loadedSize / (fileSize / 100) + "%");
                    }
                });
            }
        });
    }
}
