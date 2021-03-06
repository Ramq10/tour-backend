package com.tour.config;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

/**
 * Handles the user's access token.
 * 
 * @author Ramanand
 * @since 2020-01-18
 */
@Component
public class TourTokenEnhancer implements TokenEnhancer {

	/**
	 * Enhance the user's access token with his id. Currently not enhancing
	 *
	 * @param accessToken    the user's access token
	 * @param authentication the user's access authentication
	 * @return OAuth2AccessToken with the user's access token
	 */
	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		DefaultOAuth2AccessToken result = new DefaultOAuth2AccessToken(accessToken);

		return result;
	}

}
