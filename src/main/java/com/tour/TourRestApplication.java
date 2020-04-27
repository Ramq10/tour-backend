package com.tour;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;


@SpringBootApplication
public class TourRestApplication extends SpringBootServletInitializer {
	
	/**
	 * Called by SpringBoot framework configure the environment
	 * 
	 * @param application
	 * @return SpringApplicationBuilder
	 * 
	 */
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		setRegisterErrorPageFilter(Boolean.FALSE);
		return application.sources(TourRestApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(TourRestApplication.class, args);
	}

}
