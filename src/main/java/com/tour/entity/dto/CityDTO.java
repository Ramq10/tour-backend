package com.tour.entity.dto;

public class CityDTO {
	
	private Long id;
	private String name;
	private Long stateId;
	/**
	 * 
	 */
	public CityDTO() {
	}
	public Long getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public Long getStateId() {
		return stateId;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setStateId(Long stateId) {
		this.stateId = stateId;
	}
	
	
	

}
