package com.dexbackend.dexbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(scanBasePackages = {"com.dexbackend.dexbackend"})
public class DexbackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(DexbackendApplication.class, args);
	}

}
