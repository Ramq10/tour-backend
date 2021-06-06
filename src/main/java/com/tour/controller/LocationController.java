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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tour.entity.Country;
import com.tour.entity.dto.CityDTO;
import com.tour.entity.dto.LocationDTO;
import com.tour.entity.dto.StateDTO;
import com.tour.services.CountryService;

/**
 * @author Ramanand
 *
 */
@RestController
@RequestMapping("")
public class LocationController {

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

	@PostMapping("/country/bulk-upload")
	public void saveBulkCountry(@RequestBody List<Country> countries) {
		countryService.saveBulkCountry(countries);
	}

	@PostMapping("/state")
	public void saveState(@RequestBody StateDTO country) {
		countryService.saveState(country);
	}

	@PostMapping("/state/bulk-upload")
	public void saveBulkState(@RequestBody List<StateDTO> state) {
		countryService.saveBulkState(state);
	}

	@PostMapping("/city")
	public void saveCity(@RequestBody CityDTO city) {
		countryService.saveCity(city);
	}

	@PostMapping("/city/bulk-upload")
	public void saveBulkCity(@RequestBody List<CityDTO> city) {
		countryService.saveBulkCity(city);
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
	
	@GetMapping("/location/")
	public List<LocationDTO> searchLocation(@RequestParam String search) {
		return countryService.searchLocation(search);
	}

}
