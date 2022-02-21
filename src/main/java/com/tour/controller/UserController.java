/**
 * 
 */
package com.tour.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tour.entity.ContactUs;
import com.tour.entity.Subscriber;
import com.tour.entity.dto.BlogPostDTO;
import com.tour.entity.dto.SiteReviewDTO;
import com.tour.entity.dto.TravelStoryDTO;
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

	@PostMapping("/forget-password/{mail}")
	public void forgetPasswordRequest(@PathVariable String mail) {
		userService.forgetPasswordRequest(mail);
	}

	@PostMapping("/reset-password")
	public void resetPassword(@RequestParam String email, @RequestParam String password) {
		userService.resetPassword(email, password);
	}

	@GetMapping("/portal-data")
	public Map<String, Long> getPortalData() {
		return userService.getPortalData();
	}

	@GetMapping("/all-users")
	public List<UserDTO> getAllUsers(@RequestParam(required = false) String limit, @RequestParam(required = false) boolean blogger) {
		return userService.getAllUsers(limit, blogger);
	}

	@GetMapping("/all-user")
	public List<UserDTO> getAllUser(@RequestParam String param) {
		return userService.getAllUser(param);
	}

	@GetMapping("/all-subscriber")
	public List<Subscriber> getAllSubscriber() {
		return userService.getAllSubscriber();
	}

	@GetMapping("/all-contactus-request")
	public List<ContactUs> getAllContactUsRequest() {
		return userService.getAllContactUsRequest();
	}

	@PostMapping("/review")
	public UserDTO saveOrUpdateReview(@RequestBody SiteReviewDTO reviewDTO) {
		return userService.postSiteReview(reviewDTO);
	}
	
	@GetMapping("/user/blog-post/{userId}")
	public List<BlogPostDTO> getAllBlogByUser(@PathVariable Long userId) {
		return userService.getAllBlogByUser(userId);
	}
	
	@GetMapping("/user/story/{userId}")
	public List<TravelStoryDTO> getAllStoryByUser(@PathVariable Long userId) {
		return userService.getAllStoryById(userId);
	}
}
