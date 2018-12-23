package com.simple.liteapps.service.hardware;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class SocInfoService {
    public static Map<String, String> getCPUInfoMap() {
        Map<String, String> map = new HashMap<>();
        try {
            Scanner scanner = new Scanner(new File("/proc/cpuinfo"));
            while (scanner.hasNextLine()) {
                String[] vals = scanner.nextLine().split(": ");
                if (vals.length > 1) map.put(vals[0].trim(), vals[1].trim());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static String getCPUModel() {
        String hardware = getCPUInfoMap().get("Hardware");
        if (hardware == null || hardware.length() ==0){
            hardware = SystemInfoService.getSysHardware();
        }
        return hardware;
    }

    /**
     * CPU cores
     * @return
     */
    public static int getCPUCores() {
        return Runtime.getRuntime().availableProcessors();
    }
}
