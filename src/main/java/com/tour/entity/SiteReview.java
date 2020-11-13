package com.tour.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class SiteReview {
	
	@Column(name = "site_review_description")
	private String description;

	public SiteReview() {}

	public SiteReview(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
