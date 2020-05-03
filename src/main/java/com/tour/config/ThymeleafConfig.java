/**
 * 
 */
package com.tour.config;

import java.nio.charset.StandardCharsets;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

/**
 * @author ramanand
 *
 */

@Configuration
public class ThymeleafConfig {

	/**
	 * This method is use to get ClassLoaderTemplateResolver.
	 * 
	 * @return ClassLoaderTemplateResolver.
	 */
	@Bean
	public TemplateEngine emailTemplateEngine() {
		TemplateEngine engine = new TemplateEngine();
		engine.setTemplateResolver(emailTemplateResolver());
		return engine;
	}

	/**
	 * @return emailTemplateResolver
	 */
	@Bean
	public ClassLoaderTemplateResolver emailTemplateResolver() {
		ClassLoaderTemplateResolver emailTemplateResolver = new ClassLoaderTemplateResolver();
		emailTemplateResolver.setPrefix("/templates/");
		emailTemplateResolver.setSuffix(".html");
		emailTemplateResolver.setTemplateMode("HTML");
		emailTemplateResolver.setCharacterEncoding(StandardCharsets.UTF_8.name());
		return emailTemplateResolver;
	}

}