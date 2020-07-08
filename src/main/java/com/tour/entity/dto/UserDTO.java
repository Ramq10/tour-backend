/**
 * 
 */
package com.tour.entity.dto;

import com.tour.entity.SocialSiteLink;
import com.tour.entity.User;

/**
 * @author Ramanand
 *
 */
public class UserDTO {

	private Long id;
	private String mobileNumber;
	private String email;
	private String hobby;
	private String gender;
	private boolean blogger;
	private String name;
	private Long profilePhotoId;
	private Long countryId;
	private String countryName;
	private String fbLink;
	private String instaLink;
	private Long socialSiteLinkId;
	private SocialSiteLink socialSiteLink;
	private Long stateId;
	private String stateName;
	private String password;

	public UserDTO(User user) {
		this.id = user.getId();
		this.name = user.getName();
		this.mobileNumber = user.getMobileNumber();
		this.email = user.getEmail();
		this.hobby = user.getHobby().getType();
		this.gender = user.getGender().getType();
		this.countryId = user.getCountry() != null ? user.getCountry().getId() : null;
		this.countryName = user.getCountry() != null ? user.getCountry().getName() : null;
		this.profilePhotoId = user.getProfilePhoto() != null ? user.getProfilePhoto().getId() : null;
		this.stateId = user.getState() != null ? user.getState().getId() : null;
		this.stateName = user.getState() != null ? user.getState().getName() : null;
		this.blogger = user.isBlogger();
		this.fbLink = user.getSocialSiteLink() != null ? user.getSocialSiteLink().getFbLink() : "";
		this.instaLink = user.getSocialSiteLink() != null ? user.getSocialSiteLink().getInstaLink() : "";
		this.socialSiteLinkId = user.getSocialSiteLink() != null ? user.getSocialSiteLink().getId() : null;
	}

	public UserDTO(User user, String s) {
		this.id = user.getId();
		this.name = user.getName();
		this.email = user.getEmail();
		this.countryName = user.getCountry() != null ? user.getCountry().getName() : null;
	}

	public UserDTO() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getHobby() {
		return hobby;
	}

	public void setHobby(String hobby) {
		this.hobby = hobby;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Long getCountryId() {
		return countryId;
	}

	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}

	public Long getStateId() {
		return stateId;
	}

	public void setStateId(Long stateId) {
		this.stateId = stateId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public boolean isBlogger() {
		return blogger;
	}

	public void setBlogger(boolean blogger) {
		this.blogger = blogger;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getProfilePhotoId() {
		return profilePhotoId;
	}

	public void setProfilePhotoId(Long profilePhotoId) {
		this.profilePhotoId = profilePhotoId;
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

	public SocialSiteLink getSocialSiteLink() {
		return socialSiteLink;
	}

	public void setSocialSiteLink(SocialSiteLink socialSiteLink) {
		this.socialSiteLink = socialSiteLink;
	}

	public Long getSocialSiteLinkId() {
		return socialSiteLinkId;
	}

	public void setSocialSiteLinkId(Long socialSiteLinkId) {
		this.socialSiteLinkId = socialSiteLinkId;
	}

}
