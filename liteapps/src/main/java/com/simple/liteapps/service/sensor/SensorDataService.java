package com.simple.liteapps.service.sensor;

import android.hardware.Sensor;

import com.simple.liteapps.bridge.JsParams;

import java.util.HashMap;
import java.util.Map;

/**
 * SensorDataService
 */
public class SensorDataService {
    public static Map sensorMap = new HashMap<String,Sensor>();
    public static Map sensorData = new HashMap<String,Object>();

    /**
     * get sensor data
     * @param jsParams
     * @return
     */
    public static String getSensorData(JsParams jsParams){
        return sensorData.get(jsParams.getType()).toString();
    }

    /**
     * register sensor
     * @param jsParams
     * @return
     */
    public boolean registerSensor(JsParams jsParams){
        return true;
    }

    /**
     * unregister sensor
     * @param jsParams
     * @return
     */
    public boolean unregisterSensor(JsParams jsParams){
        return true;
    }
}
