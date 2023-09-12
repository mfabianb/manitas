package com.manitas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = {"com.manitas.*"})
@EnableJpaRepositories(basePackages = {"com.manitas.*"})
@ComponentScan(basePackages = {"com.manitas.*"})
public class ManitasApplication {

	public static void main(String[] args) {
		SpringApplication.run(ManitasApplication.class, args);
	}

}
