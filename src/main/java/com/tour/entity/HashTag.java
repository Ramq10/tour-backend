/**
 * 
 */
package com.tour.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.tour.entity.dto.HashTagsDTO;

/**
 * @author 91945
 *
 */
@Entity
@Table(name = "tags")
public class HashTag {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "name")
	private String name;

	@ManyToMany(mappedBy = "tags", cascade = CascadeType.PERSIST)
	private List<TravelStory> travelStory;

	@ManyToMany(mappedBy = "tags", cascade = CascadeType.PERSIST)
	private List<BlogPost> blogPost;

	public HashTag(HashTagsDTO hashTagsDTO) {
		this.id = hashTagsDTO.getId();
		this.name = hashTagsDTO.getName();
//		this.travelStory = travelStory;
	}

	public HashTag() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<TravelStory> getTravelStory() {
		return travelStory;
	}

	public void setTravelStory(List<TravelStory> travelStory) {
		this.travelStory = travelStory;
	}

	public List<BlogPost> getBlogPost() {
		return blogPost;
	}

	public void setBlogPost(List<BlogPost> blogPost) {
		this.blogPost = blogPost;
	}

}
