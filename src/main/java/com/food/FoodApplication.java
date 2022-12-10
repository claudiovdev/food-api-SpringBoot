package com.food;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;

@SpringBootApplication
public class FoodApplication {

	public static void main(String[] args) {

		TimeZone.setDefault(TimeZone.getTimeZone("UTC")); // Este método está sendo implementado para pordemros usar como padrão o TimeZone UTC

		SpringApplication.run(FoodApplication.class, args);
	}

}
