package com.servicemanagement;

import com.servicemanagement.config.property.ApiProperty;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(ApiProperty.class)
public class ServiceManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceManagementApplication.class, args);
    }

}
