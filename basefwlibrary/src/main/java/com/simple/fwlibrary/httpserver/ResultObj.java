package com.simple.fwlibrary.httpserver;

/**
 * Created by e2670 on 2017/9/19.
 * ResultObj
 */

public class ResultObj {
    private int resultCode;
    private String resultMsg;
    private Object data;

    public ResultObj(int resultCode, String resultMsg, Object data) {
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
        this.data = data;
    }

    public static ResultObj getFailedObj(String errMsg){
        return new ResultObj(103,errMsg,null);
    }

    public static ResultObj getSucObj(Object data){
        return new ResultObj(100,"success",data);
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
