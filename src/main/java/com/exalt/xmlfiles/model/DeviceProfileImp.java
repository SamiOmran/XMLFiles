package com.exalt.xmlfiles.model;

import java.util.ArrayList;

public class DeviceProfileImp implements DeviceProfile{
    private final String id;
    private final String name;
    private final String parentName;
    private Device deviceParent;
    private final ArrayList<String> features;

    public DeviceProfileImp(String id, String name, String parentName, ArrayList<String> features) {
        this.id = id;
        this.name = name;
        this.parentName = parentName;
        this.features = features;
    }
    @Override
    public ArrayList<String> getFeatures() {
        return features;
    }
}
