package com.exalt.xmlfiles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.io.File;

@Configuration
public class Bootstrap {
    private final String directoryPath = "G:\\Training in companies\\Exalt\\XMLFiles\\src\\main\\resources\\files";
    private final File directory = new File(directoryPath);
    public static File[] allFiles;
    private DeviceRepository deviceRepository;

    @Autowired
    private void readAllFiles() {
        allFiles = directory.listFiles();
        for (File file: allFiles ) {
            File newFile = file.getAbsoluteFile();
            System.out.println(newFile);
        }
    }
}
