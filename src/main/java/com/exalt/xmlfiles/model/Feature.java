package com.exalt.xmlfiles.model;

import lombok.Getter;

@Getter
public class Feature {
    private final String groupId;
    private final String artifactId;
    private final String version;

    public Feature(String groupId, String artifactId, String version) {
        this.groupId = groupId;
        this.artifactId = artifactId;
        this.version = version;
    }
}
