package com.dexbackend.dexbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication(scanBasePackages = {"com.dexbackend.dexbackend"})
public class DexbackendApplication extends SpringBootServletInitializer {
	
	
	public static void main(String[] args) {
		SpringApplication.run(DexbackendApplication.class, args);
	}
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(DexbackendApplication.class);
	}
	
}
