
package com.tour.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * Class for configuration of resource server.
 * 
 * @author Ramanand
 *
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private TokenStore tokenStore;

	@Autowired
	private TokenEnhancer tokenEnhancer;

	@Override
	public void configure(HttpSecurity http) throws Exception {
		logger.info("Inside ResourceServerConfig::configure(HttpSecurity http)");
		http.csrf().disable().authorizeRequests().antMatchers("/**/oauth/token").permitAll()
				.antMatchers(HttpMethod.OPTIONS, "/**/oauth/token").permitAll().antMatchers("/**/contact-us/")
				.permitAll().antMatchers("/**/forget-password/{mail}").permitAll().antMatchers("/**/set-password/")
				.permitAll().antMatchers("/**/user/").permitAll().antMatchers("/**/all-story/").permitAll()
				.antMatchers("/**/all-blog/**").permitAll().antMatchers("/**/file/upload/").permitAll()
				.antMatchers("/**/country/{id}").permitAll().antMatchers("/**/blog-genre/").permitAll()
				.antMatchers("/**/subscribe/").permitAll().antMatchers("/**/all-story/tags/{id}").permitAll()
				.antMatchers("/**/tags/").permitAll().antMatchers("/**/story/{id}").permitAll().antMatchers("/**/v-blog/**").permitAll()
				.antMatchers("/**/file/{id}").permitAll().antMatchers("/**/state/{id}").permitAll().antMatchers("/**/country").permitAll().anyRequest().authenticated();
	}

	@Primary
	@Bean
	public DefaultTokenServices defaultTokenServices() {
		logger.info("Inside ResourceServerConfig::defaultTokenServices()");
		DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
		defaultTokenServices.setTokenStore(tokenStore);
		defaultTokenServices.setTokenEnhancer(tokenEnhancer);
		defaultTokenServices.setReuseRefreshToken(false);
		return defaultTokenServices;
	}

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		logger.info("Inside ResourceServerConfig::configure(ResourceServerSecurityConfigurer resources)");
		DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
		defaultTokenServices.setTokenStore(tokenStore);
		defaultTokenServices.setTokenEnhancer(tokenEnhancer);
		defaultTokenServices.setSupportRefreshToken(false);
		resources.tokenServices(defaultTokenServices).resourceId("com.healthcare");
	}
}
