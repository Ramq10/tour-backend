package com.tour.entity.dto;

public class StateDTO {
	
	private Long id;
	private String name;
	private Long countryId;
	public StateDTO() {
		// TODO Auto-generated constructor stub
	}
	public Long getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public Long getCountryId() {
		return countryId;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}
}
