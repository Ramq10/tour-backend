package com.tour.services;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.tour.config.TourUserDetails;
import com.tour.entity.User;

/**
 * @author Ramanand
 *
 */
public class TourUserDetailService implements UserDetailsService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	protected UserService userService;

	/**
	 * Checks user's credentials (email and password), if he belongs to the firm,
	 *
	 * @param username the userName
	 * @return the user details
	 * @throws UsernameNotFoundException the userName not found exception
	 */
	@Override
	public UserDetails loadUserByUsername(String email) {
		logger.info("Insdide TourUserDetailService::loadUserByUsername(),To authenticate user.");

		User user = userService.getUserByEmail(email);

		if (user == null) {
			throw new UsernameNotFoundException("Invalid Username or Password.");

		}

		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority("Admin"));
		UserDetails userDetails = new TourUserDetails(user.getId(), email, user.getPassword(), authorities);
		logger.info("Successfully executed TourUserDetailService::loadUserByUsername()");
		return userDetails;
	}

	@Bean
	public AuthenticationProvider daoAuthenticationProvider() {
		logger.info("Insdide TourUserDetailService::daoAuthenticationProvider()");
		DaoAuthenticationProvider impl = new DaoAuthenticationProvider();
		impl.setUserDetailsService(this);
		impl.setHideUserNotFoundExceptions(false);
		return impl;
	}

}
