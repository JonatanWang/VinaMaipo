package com.vinamaipo.hrm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
public class HrmApplication {

	public static void main(String[] args) {

		SpringApplication.run(HrmApplication.class, args);
	}
}
