package com.exalt.xmlfiles;

import com.exalt.xmlfiles.model.DeviceProfileJson;
import com.exalt.xmlfiles.model.JsonObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    private final TheService service;

    public Controller(TheService service) {
        this.service = service;
    }

//    @GetMapping(value = "/{id}", produces = {"application/json"})
//    public JsonObject getDeviceFeatures(@PathVariable String id) {
//        return service.getDeviceFeatures(id);
//    }

    @GetMapping(value = "/{artifactId}", produces = {"application/json"})
    public DeviceProfileJson getDevice(@PathVariable String artifactId) {
        return service.getDevice(artifactId);
    }
}
