package com.example.demo.bip;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class BIPublisherServiceApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(BIPublisherServiceApplication.class, args);
	}
}
