package com.exalt.xmlfiles.model;

import javafx.util.Pair;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public class Device {
    private String id;
    private String name;
    private String parentName;
    private Device deviceParent;
    //private ArrayList<String> features;
    private ArrayList<Pair<String, Boolean>> features;

    public Device(String id, String name, String parentName, ArrayList<Pair<String, Boolean>>  features) {
        this.id = id;
        this.name = name;
        this.parentName = parentName;
        this.features = features;
    }

    public void setFeatures(ArrayList<Pair<String, Boolean>>  features) {
        this.features = features;
                //(ArrayList<Pair<String, Boolean>> ) features.stream().distinct().collect(Collectors.toList());
    }

    public void setDeviceParent(Device deviceParent) {
        this.deviceParent = deviceParent;
    }
}
