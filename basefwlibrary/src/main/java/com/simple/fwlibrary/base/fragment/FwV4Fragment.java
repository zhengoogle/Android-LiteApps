package com.simple.fwlibrary.base.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.simple.fwlibrary.vutils.FwLog;


/**
 * Fragment基类
 */
public abstract class FwV4Fragment extends Fragment {
    public Activity mActivity;

    /**
     * 子类的根布局
     */
    protected View rootView;

    /**
     * 子类需实现getLayoutId()并传入布局id
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        FwLog.d(this,"onCreateView...");

        rootView = inflater.inflate(getLayoutId(), null);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        FwLog.d(this,"onActivityCreated...");

        initDatas();
        initViews();
        initEvents();
        loadDatas();
    }

    /**
     * 静态的获取上下文的方法
     * @return TypeFragment.getmActivity()
     */
    public Activity getmActivity(){
        return mActivity;
    }

    /**
     * Fragment getActivity空指针问题简化处理方式
     * 当获取失败最好的解决方式直接让当前代码块结束
     * @param context 上下文
     */
    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        FwLog.d(this,"onAttach....");

        //this.mActivity = (Activity) context;
        this.mActivity = getActivity();
    }

    /**
     * 从子类获取布局id
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * 视图初始化
     */
    protected abstract void initViews();

    /**
     * 事件初始化
     */
    protected abstract void initEvents();

    /**
     * 数据初始化
     */
    protected abstract void initDatas();

    /**
     * 加载数据
     */
    protected abstract void loadDatas();

    /* 生命周期打印测试程序 */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FwLog.d(this,"onCreate....");
    }

    @Override
    public void onStart() {
        super.onStart();
        FwLog.d(this,"onStart....");
    }

    @Override
    public void onResume() {
        super.onResume();
        FwLog.d(this,"onResume....");
    }

    @Override
    public void onPause() {
        super.onPause();
        FwLog.d(this,"onPause....");
    }

    @Override
    public void onStop() {
        super.onStop();
        FwLog.d(this,"onStop....");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        FwLog.d(this,"onDetach....");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        FwLog.d(this,"onDestroyView....");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        FwLog.d(this,"onDestroy....");
    }
}
