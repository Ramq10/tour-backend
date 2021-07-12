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


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cityId == null) ? 0 : cityId.hashCode());
		result = prime * result + ((cityName == null) ? 0 : cityName.hashCode());
		result = prime * result + ((countryId == null) ? 0 : countryId.hashCode());
		result = prime * result + ((countryName == null) ? 0 : countryName.hashCode());
		result = prime * result + ((stateId == null) ? 0 : stateId.hashCode());
		result = prime * result + ((stateName == null) ? 0 : stateName.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LocationDTO other = (LocationDTO) obj;
		if (cityId == null) {
			if (other.cityId != null)
				return false;
		} else if (!cityId.equals(other.cityId))
			return false;
		if (cityName == null) {
			if (other.cityName != null)
				return false;
		} else if (!cityName.equals(other.cityName))
			return false;
		if (countryId == null) {
			if (other.countryId != null)
				return false;
		} else if (!countryId.equals(other.countryId))
			return false;
		if (countryName == null) {
			if (other.countryName != null)
				return false;
		} else if (!countryName.equals(other.countryName))
			return false;
		if (stateId == null) {
			if (other.stateId != null)
				return false;
		} else if (!stateId.equals(other.stateId))
			return false;
		if (stateName == null) {
			if (other.stateName != null)
				return false;
		} else if (!stateName.equals(other.stateName))
			return false;
		return true;
	}
	
	
	
	
}
