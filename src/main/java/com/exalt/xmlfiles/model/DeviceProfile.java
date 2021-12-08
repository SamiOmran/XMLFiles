package com.exalt.xmlfiles.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;

@Getter
@Setter
@NoArgsConstructor
public class DeviceProfile {
    private String groupId;
    private String artifactId;
    private String parentVersion;
    private Device deviceParent;
    private HashSet<Feature> dependencies;

    public DeviceProfile(String parentVersion, String groupId, String artifactId, HashSet<Feature> dependencies) {
        this.parentVersion = parentVersion;
        this.groupId = groupId;
        this.artifactId = artifactId;
        this.dependencies = dependencies;
    }
}
