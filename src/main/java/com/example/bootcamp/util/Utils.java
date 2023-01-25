package com.example.bootcamp.util;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Utils {
    public static void loggerInfo(String operation, String layer, Object ... propertyToLog) {
        log.info("Operation = {} - Layer = {} - Input = {}", operation, layer, propertyToLog);
    }   
}
