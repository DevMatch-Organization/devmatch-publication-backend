package com.devmatch.publication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class DevmatchPublicationBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(DevmatchPublicationBackendApplication.class, args);
	}
}
