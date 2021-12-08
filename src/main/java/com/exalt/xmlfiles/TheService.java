package com.exalt.xmlfiles;

import com.exalt.xmlfiles.model.Device;
import com.exalt.xmlfiles.model.DeviceProfile;
import com.exalt.xmlfiles.model.DeviceProfileJson;
import com.exalt.xmlfiles.model.JsonObject;
import org.springframework.stereotype.Service;
//import static com.exalt.xmlfiles.Bootstrap.allDevices;
import static com.exalt.xmlfiles.Bootstrap2.allDevices;

@Service
public class TheService {

//    public JsonObject getDeviceFeatures(String id) {
//        for (Device tempDevice : allDevices) {
//            if (tempDevice.getId().equals(id)) {
//                return new JsonObject("Success", tempDevice);
//            }
//        }
//
//        return new JsonObject("Fail", null);
//    }

    public DeviceProfileJson getDevice(String artifactId) {
        for (DeviceProfile deviceProfile : allDevices) {
            if (deviceProfile.getArtifactId().equalsIgnoreCase(artifactId) || deviceProfile.getParentVersion().equalsIgnoreCase(artifactId))
                return new DeviceProfileJson("Success", deviceProfile);
        }
        return new DeviceProfileJson("Fail", null);
    }
}
