/**
 * 
 */
package com.tour.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tour.entity.dto.UserDTO;
import com.tour.services.UserService;

/**
 * @author Ramanand
 *
 */
@RestController
@RequestMapping("")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/user")
	public UserDTO saveOrUpdateUser(@RequestBody UserDTO userDTO) {
		return userService.saveOrUpdate(userDTO);
	}

	@GetMapping("/user/{id}")
	public UserDTO getById(@PathVariable Long id) {
		return userService.getUserDTOById(id);
	}
	
	@GetMapping("/user/email/{email}")
	public UserDTO getByEmail(@PathVariable String email) {
		return userService.getUserDTOByEmail(email);
	}
	
	@GetMapping("/logged-in-user")
	public UserDTO getAuthenticatedUser() {
		return userService.getLoggedInUserDTO();
	}
	
	@PostMapping("/subscribe")
	public void subscribe(@RequestParam String email) {
		userService.subscribe(email);
	}

}
