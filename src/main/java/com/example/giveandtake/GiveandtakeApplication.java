package com.example.giveandtake;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan
public class GiveandtakeApplication {

	public static void main(String[] args) {
		SpringApplication.run(GiveandtakeApplication.class, args);
	}

}
