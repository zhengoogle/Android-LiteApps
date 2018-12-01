package com.simple.fwlibrary.vutils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by envy15 on 2016/10/23.
 * 基于Gson封装的JSON解析/反解析工具类
 * 参考：
 *  http://blog.csdn.net/chenliguan/article/details/48623283
 *  http://www.cnblogs.com/top5/archive/2012/02/03/2336493.html
 */
public class FwJsonUtils {
    private static Gson gson;

    static {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("yyyy-MM-dd HH:mm:ss");
        gson = gsonBuilder.create();
    }

    /***
     * 将List对象序列化为JSON文本
     */
    public static <T> String toJSONString(List<T> list)
    {
        return gson.toJson(list);
    }

    /***
     * 将对象序列化为JSON文本
     * @param object
     * @return
     */
    public static String toJSONString(Object object)
    {
        return gson.toJson(object);
    }

    /***
     * Json对象解析
     * @return
     */
    public static <T> T  toBean(String str, Class<T> classOfT) {

        return new Gson().fromJson(str, classOfT);
    }

    /***
     * Json List对象解析
     * @return
     */
    public static <T> List<T> toBeanList(String str, Type listType) {
//        Type listType = new TypeToken<ArrayList<T>>() {}.getType();
        return new Gson().fromJson(str, listType);
    }

    /**
     * 转成list
     * 解决泛型问题
     * @param json
     * @param cls
     * @param <T>
     * @return
     */
    public <T> List<T> toList(String json, Class<T> cls) {
        Gson gson = new Gson();
        List<T> list = new ArrayList<T>();
        JsonArray array = new JsonParser().parse(json).getAsJsonArray();
        for(final JsonElement elem : array){
            list.add(gson.fromJson(elem, cls));
        }
        return list;
    }
}
