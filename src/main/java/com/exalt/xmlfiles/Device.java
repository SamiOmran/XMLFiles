package com.exalt.xmlfiles;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public class Device {
    private String id;
    private String name;
    private String parent;
    private ArrayList<String> features;
    private Node xmlNode;

    public Device(String id, String name, String parent, ArrayList<String> features, Node xmlNode) {
        this.id = id;
        this.name = name;
        this.parent = parent;
        this.features = features;
        this.xmlNode = xmlNode;
    }

    public void setFeatures(ArrayList<String> features) {
        this.features = (ArrayList<String>) features.stream().distinct().collect(Collectors.toList());
    }
}
