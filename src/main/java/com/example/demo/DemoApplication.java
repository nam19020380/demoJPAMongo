package com.example.demo;

import lombok.Generated;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = { "com.example.demo.*" })
@EntityScan("com.example.demo.entity")
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class DemoApplication {
	@Generated
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
