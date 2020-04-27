/**
 * 
 */
package com.tour.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tour.entity.Country;
import com.tour.services.CountryService;

/**
 * @author Ramanand
 *
 */
@RestController
@RequestMapping("")
public class CountryController {

	@Autowired
	private CountryService countryService;

	@GetMapping("/country")
	public List<Country> getAllState() {
		return countryService.getAllCountry();
	}

	@PostMapping("/country")
	public void saveCountry(@RequestBody Country country) {
		countryService.saveCountry(country);
	}

	@DeleteMapping("/state/{id}")
	public void deleteState(@PathVariable Long id) {
		countryService.deleteState(id);
	}

	@DeleteMapping("/country/{id}")
	public void deleteCountry(@PathVariable Long id) {
		countryService.deleteCountry(id);
	}

	@GetMapping("/country/{id}")
	public Country getCountryById(@PathVariable Long id) {
		return countryService.getCountryById(id);
	}

}
