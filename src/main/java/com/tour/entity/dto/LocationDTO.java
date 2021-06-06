package com.tour.entity.dto;

import com.tour.entity.Country;

public class LocationDTO {

	private String countryName;
	private String stateName;
	private String cityName;
	private Long countryId;
	private Long stateId;
	private Long cityId;
	
	public LocationDTO(Country c) {
		// TODO Auto-generated constructor stub
	}
	
	
	public LocationDTO(String countryName, String stateName, String cityName, Long countryId, Long stateId,
			Long cityId) {
		super();
		this.countryName = countryName;
		this.stateName = stateName;
		this.cityName = cityName;
		this.countryId = countryId;
		this.stateId = stateId;
		this.cityId = cityId;
	}


	public String getCountryName() {
		return countryName;
	}
	public String getStateName() {
		return stateName;
	}
	public String getCityName() {
		return cityName;
	}
	public Long getCountryId() {
		return countryId;
	}
	public Long getStateId() {
		return stateId;
	}
	public Long getCityId() {
		return cityId;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}
	public void setStateId(Long stateId) {
		this.stateId = stateId;
	}
	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}
	
	
}
