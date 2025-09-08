package com.banking.user_account_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication(scanBasePackages = "com.banking.user_account_service")
@EnableDiscoveryClient
public class UserAccountServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserAccountServiceApplication.class, args);
	}

}
