package com.tour.config;

import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.stereotype.Component;

/**
 * @author Ramanand
 *
 */
@Component
public class TokenStore extends InMemoryTokenStore{
}
