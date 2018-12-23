package com.simple.liteapps.bridge;

public interface IBridge {
    String sync(String params);
    String async(String params);
    String syncBridge(String params);
    String asyncBridge(String params);
}
