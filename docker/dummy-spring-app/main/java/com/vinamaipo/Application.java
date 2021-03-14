package com.vinamaipo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class Application {
	public static void main(String[] args) {
    System.out.println("\n\n*** Dummy application to cache dependency downloads with Docker ***\n\n");
	}
}
