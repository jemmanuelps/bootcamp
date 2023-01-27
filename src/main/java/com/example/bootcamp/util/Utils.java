package com.example.bootcamp.util;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Objects;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Utils {
    public static void loggerInfo(String operation, String layer, Object ... propertyToLog) {
        log.info("Operation = {} - Layer = {} - Input = {}", operation, layer, propertyToLog);
    }

    public static <T> boolean validate(T input) { 
        var mappedInput = new ObjectMapper().convertValue(input, Map.class);
        var keys = input.getClass().getDeclaredFields();
        for (Field key : keys) {
            if (Objects.isNull(mappedInput.get(key.getName()))) {
                return false;
            }
        }
        return true;
    }
}
