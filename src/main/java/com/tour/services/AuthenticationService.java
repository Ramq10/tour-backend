package com.tour.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.stereotype.Service;

import com.tour.config.TokenStore;
import com.tour.config.TourUserDetails;
import com.tour.entity.User;
import com.tour.exception.UnauthorizedException;
import com.tour.repository.UserRepository;

/**
 * @author Ramanand
 *
 */
@Service
public class AuthenticationService {

	private UserService userService;

	@Autowired
	public AuthenticationService(UserService userService, UserRepository userRepository, TokenStore tokenStore,
			DefaultTokenServices tokenServices) {
		this.userService = userService;

	}

	/**
	 * Method to authenticate user.
	 * 
	 * @return user
	 */
	public User getAuthenticatedUser() {
		TourUserDetails wittyUserDetails;
		try {
			wittyUserDetails = (TourUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		} catch (Exception e) {
			throw new UnauthorizedException("Unauthorized Access.");
		}
		User user = userService.getUserById(wittyUserDetails.getId());

		return user;
	}
}
