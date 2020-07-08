package com.tour.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.tour.entity.dto.UserDTO;
import com.tour.enums.Gender;
import com.tour.enums.Hobby;

/**
 * @author Ramanand
 *
 */
@Entity
@Table(name = "users")
public class User extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "name")
	private String name;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "profile_id")
	private File profilePhoto;

	@Enumerated(EnumType.STRING)
	private Gender gender;

	@OneToMany(mappedBy = "user")
	private List<TravelStory> story;

	@OneToMany(mappedBy = "blogger")
	private List<BlogPost> blogPost;

	@Column(name = "blogger")
	private boolean blogger;

	@Column(name = "email")
	private String email;

	@Column(name = "mobile_number")
	private String mobileNumber;

	// @Enumerated(EnumType.STRING)
	// @Column(name = "blog_genre")
	// private BlogGenres blogGenre;

	@Enumerated(EnumType.STRING)
	@Column(name = "hobby")
	private Hobby hobby;

	@OneToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "country_id", referencedColumnName = "id")
	private Country country;

	@OneToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "state_id", referencedColumnName = "id")
	private State state;
	
	@OneToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "socialSiteLink", referencedColumnName = "id")
	private SocialSiteLink socialSiteLink;

	@Column(name = "password")
	private String password;

	@Column(name = "password_update_date")
	private LocalDateTime passwordUpdateDate;

	public User(UserDTO userDTO) {
		this.id = userDTO.getId();
		this.name = userDTO.getName();
		this.gender = Gender.getEnum(userDTO.getGender());
		this.email = userDTO.getEmail();
		this.mobileNumber = userDTO.getMobileNumber();
		this.blogger = userDTO.isBlogger();
		this.hobby = Hobby.getEnum(userDTO.getHobby());
		this.socialSiteLink = userDTO.getSocialSiteLink();
	}

	public User() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Hobby getHobby() {
		return hobby;
	}

	public void setHobby(Hobby hobby) {
		this.hobby = hobby;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public LocalDateTime getPasswordUpdateDate() {
		return passwordUpdateDate;
	}

	public void setPasswordUpdateDate(LocalDateTime passwordUpdateDate) {
		this.passwordUpdateDate = passwordUpdateDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isBlogger() {
		return blogger;
	}

	public void setBlogger(boolean blogger) {
		this.blogger = blogger;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public File getProfilePhoto() {
		return profilePhoto;
	}

	public void setProfilePhoto(File profilePhoto) {
		this.profilePhoto = profilePhoto;
	}

	public List<TravelStory> getStory() {
		return story;
	}

	public void setStory(List<TravelStory> story) {
		this.story = story;
	}

	public List<BlogPost> getBlogPost() {
		return blogPost;
	}

	public void setBlogPost(List<BlogPost> blogPost) {
		this.blogPost = blogPost;
	}

	public SocialSiteLink getSocialSiteLink() {
		return socialSiteLink;
	}

	public void setSocialSiteLink(SocialSiteLink socialSiteLink) {
		this.socialSiteLink = socialSiteLink;
	}

}
