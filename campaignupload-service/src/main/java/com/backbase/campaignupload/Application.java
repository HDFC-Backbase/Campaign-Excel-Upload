package com.backbase.campaignupload;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.backbase.buildingblocks.jwt.internal.config.EnableInternalJwtConsumer;

@EnableJpaRepositories
@EntityScan
@SpringBootApplication
@EnableDiscoveryClient
@EnableInternalJwtConsumer
public class Application extends SpringBootServletInitializer {

    public static void main(final String[] args) {
        SpringApplication.run(Application.class, args);
    }

}