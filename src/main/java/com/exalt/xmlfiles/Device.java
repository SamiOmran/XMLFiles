package com.exalt.xmlfiles;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public class Device {
    private String id;
    private String name;
    private String parentName;
    private Device deviceParent;
    private ArrayList<String> features;

    public Device(String id, String name, String parentName, ArrayList<String> features) {
        this.id = id;
        this.name = name;
        this.parentName = parentName;
        this.features = features;
    }

    public void setFeatures(ArrayList<String> features) {
        this.features = (ArrayList<String>) features.stream().distinct().collect(Collectors.toList());
    }

    public void setDeviceParent(Device deviceParent) {
        this.deviceParent = deviceParent;
    }
}
