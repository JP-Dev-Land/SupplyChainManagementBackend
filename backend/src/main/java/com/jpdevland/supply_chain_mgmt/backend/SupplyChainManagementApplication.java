package com.jpdevland.supply_chain_mgmt.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.jpdevland.supply_chain_mgmt.backend.config.ApplicationProperties;

@SpringBootApplication
@EnableConfigurationProperties(ApplicationProperties.class)
public class SupplyChainManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(SupplyChainManagementApplication.class, args);
    }

}
