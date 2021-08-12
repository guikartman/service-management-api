package com.servicemanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
public class ServiceManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceManagementApplication.class, args);
    }

}
