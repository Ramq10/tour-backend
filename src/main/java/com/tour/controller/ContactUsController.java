/**
 * 
 */
package com.tour.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tour.entity.ContactUs;
import com.tour.services.ContactUsService;

/**
 * @author Ramanand
 *
 */
@RestController
@RequestMapping("")
public class ContactUsController {

	@Autowired
	private ContactUsService contactUsService;

	@PostMapping("/contact-us")
	public void save(@RequestBody ContactUs contactUs) {
		 contactUsService.save(contactUs);
	}
}
