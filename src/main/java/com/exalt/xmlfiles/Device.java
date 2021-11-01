package com.exalt.xmlfiles;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.Id;
import java.util.ArrayList;

@Getter
@Setter
@RequiredArgsConstructor
public class Device {
    @Id private long id;
    private String name;
    private ArrayList<String> features;
}
