package com.tribal.creditlineapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class CreditlineApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(CreditlineApiApplication.class, args);
	}

}
