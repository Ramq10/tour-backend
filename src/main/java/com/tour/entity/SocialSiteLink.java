/**
 * 
 */
package com.tour.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Ramanand
 *
 */
@Entity
@Table(name = "social_site_link")
public class SocialSiteLink extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "fb_link")
	private String fbLink;
	
	@Column(name = "insta_link")
	private String instaLink;

	public SocialSiteLink() {
		
	}

	public SocialSiteLink(Long id, String fbLink, String instaLink) {
		this.id = id;
		this.fbLink = fbLink;
		this.instaLink = instaLink;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFbLink() {
		return fbLink;
	}

	public void setFbLink(String fbLink) {
		this.fbLink = fbLink;
	}

	public String getInstaLink() {
		return instaLink;
	}

	public void setInstaLink(String instaLink) {
		this.instaLink = instaLink;
	}
	
	
	
}
