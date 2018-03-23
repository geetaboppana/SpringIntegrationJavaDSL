package com.hcsc.sprnl.services;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan (basePackages = {"com.hcsc.*"})
public class SprnlServicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(SprnlServicesApplication.class, args);
		
	}
}
