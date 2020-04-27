/**
 * 
 */
package com.tour.services;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tour.entity.City;
import com.tour.entity.Country;
import com.tour.exception.UnprocessableEntityException;
import com.tour.repository.CityRepository;
import com.tour.repository.CountryRepository;
import com.tour.repository.StateRepository;

/**
 * @author Ramanand
 *
 */
@Service
public class CountryService {

	@Autowired
	private CountryRepository countryRepository;
	@Autowired
	private StateRepository stateRepository;
	@Autowired
	private CityRepository cityRepository;

	public List<Country> getAllCountry() {
		return countryRepository.findAll();
	}

	public void saveCountry(Country country) {
		if (StringUtils.isBlank(country.getName())) {
			throw new UnprocessableEntityException("Please provide Country name.");
		}
		Country countryName = countryRepository.findByName(country.getName());
		if (countryName != null && country.getId() == null) {
			throw new UnprocessableEntityException("Country exists.");
		}
		countryRepository.save(country);
	}

	public void deleteState(Long id) {
		stateRepository.deleteById(id);
	}

	public void deleteCountry(Long id) {
		countryRepository.deleteById(id);
	}

	public void saveCity(City city) {

	}

	public Country getCountryById(Long id) {
		return countryRepository.findById(id).get();
	}

}
