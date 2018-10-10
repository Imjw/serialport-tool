package com.imjw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class SerialportToolApplication {

	public static void main(String[] args) {
		SpringApplication.run(SerialportToolApplication.class, args);
	}
}
