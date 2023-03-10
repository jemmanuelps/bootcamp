package com.example.bootcamp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.cassandra.repository.config.EnableReactiveCassandraRepositories;

import com.example.bootcamp.config.CassandraConfig;

@SpringBootApplication
@EnableReactiveCassandraRepositories(basePackageClasses = {CassandraConfig.class})
public class BootcampApplication {

	public static void main(String[] args) {
		SpringApplication.run(BootcampApplication.class, args);
	}

}
