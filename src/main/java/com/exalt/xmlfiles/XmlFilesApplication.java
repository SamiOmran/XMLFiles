package com.exalt.xmlfiles;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class XmlFilesApplication {

    public static void main(String[] args) {
        SpringApplication.run(XmlFilesApplication.class, args);
    }

}
