package com.exalt.xmlfiles;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class JsonObject {
    private String message;
    private Device device;

    public JsonObject(String message, Device device) {
        this.message = message;
        this.device = device;
    }
}
