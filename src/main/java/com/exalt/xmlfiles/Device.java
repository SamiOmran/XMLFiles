package com.exalt.xmlfiles;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@RequiredArgsConstructor
public class Device {
    private String id;
    private String name;
    private String parent;
    private ArrayList<String> features;

    public Device(String id, String name, String parent, ArrayList<String> features) {
        this.id = id;
        this.name = name;
        this.parent = parent;
        this.features = features;
    }
}
