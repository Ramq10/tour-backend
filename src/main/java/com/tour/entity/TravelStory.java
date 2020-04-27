/**
 * 
 */
package com.tour.entity;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.tour.entity.dto.TravelStoryDTO;

/**
 * @author Ramanand
 *
 */
@Entity
@Table(name = "travel_story")
public class TravelStory extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "story_title")
	private String title;

	@Column(name = "discription", length = 100000)
	private String description;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "candidate_id")
	private User user;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "travel_story_id")
	private List<File> files;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "like_user_experience", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "experience_id", referencedColumnName = "id"))
	private Set<User> likedBy;
	
	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comment> comments;
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "tag_travel_story", joinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "experience_id", referencedColumnName = "id"))
	private List<HashTag> tags;
	
	@Column(name = "view")
	private Long view; 

	public TravelStory(TravelStoryDTO experienceStoryDTO) {
		this.id = experienceStoryDTO.getId();
		this.title = experienceStoryDTO.getTitle();
		this.description = experienceStoryDTO.getDescription();
	}

	/**
	 * 
	 */
	public TravelStory() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<File> getFiles() {
		return files;
	}

	public void setFiles(List<File> files) {
		this.files = files;
	}

	public Set<User> getLikedBy() {
		return likedBy;
	}

	public void setLikedBy(Set<User> likedBy) {
		this.likedBy = likedBy;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public Long getView() {
		return view;
	}

	public void setView(Long view) {
		this.view = view;
	}

	public List<HashTag> getTags() {
		return tags;
	}

	public void setTags(List<HashTag> tags) {
		this.tags = tags;
	}
	

}
