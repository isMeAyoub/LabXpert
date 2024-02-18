package com.simplon.labxpert.util;

import org.springframework.stereotype.Component;

import java.util.UUID;
@Component
public class UtilMethods {
    public static String generateSerialNumber() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 12);
    }
}
