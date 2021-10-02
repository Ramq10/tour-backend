/**
 * 
 */
package com.tour.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tour.entity.City;
import com.tour.entity.Country;
import com.tour.entity.State;
import com.tour.entity.dto.CityDTO;
import com.tour.entity.dto.LocationDTO;
import com.tour.entity.dto.StateDTO;
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

	private CountryRepository countryRepository;
	private StateRepository stateRepository;
	private CityRepository cityRepository;

	@Autowired
	public CountryService(CountryRepository countryRepository, StateRepository stateRepository,
			CityRepository cityRepository) {
		this.countryRepository = countryRepository;
		this.cityRepository = cityRepository;
		this.stateRepository = stateRepository;
	}

	public List<Country> getAllCountry() {
		return countryRepository.findAllByOrderByName();
	}

	/**
	 * @param country
	 */
	public void saveCountry(Country country) {
		checkObjectForNull(country);
		validateName(country.getName());
		Country countryName;
		if (country.getId() == null) {
			countryName = new Country();
		} else {
			countryName = countryRepository.findById(country.getId()).get();
			checkObjectForNull(countryName);
		}
		countryName.setName(country.getName());
		countryRepository.save(country);
	}

	public void deleteState(Long id) {
		State state = getStateById(id);
		if (state.getCity().size() > 1) {
			throw new UnprocessableEntityException("State cannot deleted. It have active cities");
		}
		stateRepository.deleteById(id);
	}

	public void deleteCountry(Long id) {
		Country country = getCountryById(id);
		if (country.getState().size() > 1) {
			throw new UnprocessableEntityException("Country cannot deleted. It have active states");
		}
		countryRepository.deleteById(id);
	}

	public void deleteCity(Long id) {
		cityRepository.deleteById(id);
	}

	/**
	 * @param state
	 */
	public void saveState(StateDTO state) {
		checkObjectForNull(state);
		Country country = getCountryById(state.getCountryId());
		checkObjectForNull(country);
		validateName(state.getName());
		State stateName;
		if (state.getId() == null) {
			stateName = new State();
		} else {
			stateName = stateRepository.findById(state.getId()).get();
			checkObjectForNull(stateName);
		}
		stateName.setName(state.getName());
		stateName = stateRepository.save(stateName);
		List<State> states = country.getState();
		if (!states.contains(stateName)) {
			states.add(stateName);
			country.setState(states);
			countryRepository.save(country);
		}
	}

	/**
	 * @param city
	 */
	public void saveCity(CityDTO city) {
		checkObjectForNull(city);
		State state = getStateById(city.getStateId());
		checkObjectForNull(state);
		validateName(city.getName());
		City cityName;
		if (city.getId() == null) {
			cityName = new City(null, city.getName());
		} else {
			cityName = cityRepository.findById(city.getId()).get();
			checkObjectForNull(cityName);
			cityName.setName(city.getName());
		}
		cityName = cityRepository.save(cityName);
		List<City> cities = state.getCity();
		if (!cities.contains(cityName)) {
			cities.add(cityName);
			state.setCity(cities);
			stateRepository.save(state);
		}
	}

	private void checkObjectForNull(Object cityName) {
		if (Objects.isNull(cityName)) {
			throw new UnprocessableEntityException("Please provide valid Id");
		}
	}

	private void validateName(String name) {
		if (StringUtils.isBlank(name)) {
			throw new UnprocessableEntityException("Please provide city name.");
		}
	}

	public Country getCountryById(Long id) {
		return countryRepository.findById(id).get();
	}

	public City getCityById(Long id) {
		return cityRepository.findById(id).get();
	}

	public State getStateById(Long id) {
		return stateRepository.findById(id).get();
	}

	/**
	 * @param countries
	 */
	public void saveBulkCountry(List<Country> countries) {
		countries.forEach(country -> {
			saveCountry(country);
		});
	}

	/**
	 * @param states
	 */
	public void saveBulkState(List<StateDTO> states) {
		states.forEach(state -> {
			saveState(state);
		});
	}

	/**
	 * @param cities
	 */
	public void saveBulkCity(List<CityDTO> cities) {
		cities.forEach(city -> {
			saveCity(city);
		});
	}

	public Set<LocationDTO> searchLocation(String searchText) {
		Set<LocationDTO> loc = new LinkedHashSet<>();
		searchText = searchText.replaceAll(",", " ");
		List<String> txts = new ArrayList<>(Arrays.asList(searchText.split("\\s+")));
//		txts.add(searchText);
		loc.addAll(countryRepository.searchCountry(searchText.toLowerCase()).stream().collect(Collectors.toSet()));
		loc.addAll(countryRepository.searchState(searchText.toLowerCase()).stream().collect(Collectors.toSet()));
		loc.addAll(countryRepository.searchLocation(searchText.toLowerCase()).stream().collect(Collectors.toSet()));
		txts.forEach(t -> {
			loc.addAll(countryRepository.searchCountry(t.toLowerCase()).stream().collect(Collectors.toSet()));
			loc.addAll(countryRepository.searchState(t.toLowerCase()).stream().collect(Collectors.toSet()));
			loc.addAll(countryRepository.searchLocation(t.toLowerCase()).stream().collect(Collectors.toSet()));
		});
		return loc;
	}

}
