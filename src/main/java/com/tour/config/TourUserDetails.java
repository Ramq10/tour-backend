package com.tour.config;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

/**
 * Holds the user's credentials.
 * 
 * @author Ramanand
 * @since 2020-01-18
 */
public class TourUserDetails extends User {

	private static final long serialVersionUID = 1L;

	private long id;

	/**
	 * Instantiates a new user details.
	 *
	 * @param id          the id
	 * @param username    the userName
	 * @param password    the password
	 * @param authorities the authorities
	 */
	public TourUserDetails(long id, String username, String password,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
		this.id = id;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(long id) {
		this.id = id;
	}

}
