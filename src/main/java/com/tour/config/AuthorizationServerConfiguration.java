package com.tour.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * @author Ramanand
 *
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private TokenStore tokenStore;

	@Autowired
	private TokenEnhancer tokenEnhancer;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private PasswordEncoder passwordEncoder;
	

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		logger.info(
				"Inside AuthorizationServerConfiguration::configure(ClientDetailsServiceConfigurer clients), configure clients detail service");
		clients.inMemory().withClient("witty-web-client").secret(passwordEncoder.encode("1234567890"))
				.authorities("ROLE_CLIENT", "ROLE_TRUSTED_CLIENT", "ADMIN").scopes("read", "write", "trust")
				.authorizedGrantTypes("password").and().withClient("witty-mobile-client")
				.secret(passwordEncoder.encode("1234567890")).authorities("ROLE_CLIENT", "ROLE_TRUSTED_CLIENT", "ADMIN")
				.scopes("read", "write", "trust").authorizedGrantTypes("password");
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		logger.info(
				"Inside AuthorizationServerConfiguration::configure(AuthorizationServerEndpointsConfigurer endpoints), configure authorization server endpoints");
		DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
		defaultTokenServices.setTokenStore(tokenStore);
		defaultTokenServices.setTokenEnhancer(tokenEnhancer);
		defaultTokenServices.setSupportRefreshToken(false);
		endpoints.tokenServices(defaultTokenServices).authenticationManager(authenticationManager);
	}

}