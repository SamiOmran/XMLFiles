package com.exalt.xmlfiles.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DeviceProfileJson {
    private String message;
    private DeviceProfile deviceProfile;

    public DeviceProfileJson(String message, DeviceProfile deviceProfile) {
        this.message = message;
        this.deviceProfile = deviceProfile;
    }
}
