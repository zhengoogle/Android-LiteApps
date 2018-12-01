package com.simple.fwlibrary.httpserver;

import com.simple.fwlibrary.vutils.FwJsonUtils;

import java.util.HashMap;
import java.util.Map;

import fi.iki.elonen.NanoHTTPD;


/**
 * Created by e2670 on 2017/9/18.
 * FwMiniHttpServer
 *  支持GET/POST/PUT请求
 *  TODO:DELETE请求
 */

public class FwMiniHttpServer extends NanoHTTPD{

    public FwMiniHttpServer(int port) {
        super(port);
    }

    public FwMiniHttpServer(String hostname, int port) {
        super(hostname, port);
    }

    /**
     * 获取miniHttpServer实例
     * @param port
     * @return
     */
    public static FwMiniHttpServer getInstance(int port){
        return new FwMiniHttpServer(port);
    }

    /**
     * http://192.168.31.220:8086/root/login/commit
     * @param session
     * @return
     */
    @Override
    public Response serve(IHTTPSession session) {
        String resultMsg = null;
        String[] splitPath = session.getUri().split("/");

        // 获取请求方法
        Method method = session.getMethod();
        switch (method){
            case GET:
                Map<String, String> parmsMap = session.getParms();
                if("root".equals(splitPath[1])){
                    switch (splitPath[2]){
                        case "login":
                            switch (splitPath[3]){
                                // http://192.168.31.220:8080/root/login/commit?username=AAA
                                case "commit":
                                    parmsMap.get("username");
                                    break;
                                case "register":
                                    break;
                            }
                            break;
                    }
                }

                break;
            case PUT:
            case POST:
                // 获取json
                // http://192.168.31.220:8080/root/login/register
                // {"userName":"AAA","pwd":"AAA"}
                try {
                    Map<String, String> filesMap = new HashMap<>();
                    session.parseBody(filesMap);
                    String postData = filesMap.get("postData");
                    if("root".equals(splitPath[1])){
                        switch (splitPath[2]){
                            case "login":
                                switch (splitPath[3]){
                                    // http://192.168.31.220:8080/root/login/commit?username=AAA
                                    case "commit":

                                        break;
                                    case "register":
                                        resultMsg = FwJsonUtils.toJSONString(ResultObj.getSucObj(""));
                                        break;
                                }
                                break;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }

        return newFixedLengthResponse(resultMsg);
    }
}
