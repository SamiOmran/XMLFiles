package com.exalt.xmlfiles;

import org.springframework.stereotype.Service;
import static com.exalt.xmlfiles.Bootstrap.allDevices;

@Service
public class TheService {

    public JsonObject getDeviceFeatures(String id) {
        for (Device tempDevice : allDevices) {
            if (tempDevice.getId().equals(id)) {
                return new JsonObject("Success", tempDevice);
            }
        }

        return new JsonObject("Fail", null);
    }
}
