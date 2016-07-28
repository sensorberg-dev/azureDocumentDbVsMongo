package com.sensorberg.backend.util;

import java.util.UUID;

public class UuidUtil {
    public static String newUuid() {
        return UUID.randomUUID().toString();
    }
}
