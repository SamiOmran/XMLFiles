package com.exalt.xmlfiles;

import com.exalt.xmlfiles.model.Device;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class Controller {
    private TheService service;

    public Controller(TheService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public List<Device> getDeviceFeatures(@PathVariable long id) {
        return service.getDeviceFeatures(id);
    }
}
