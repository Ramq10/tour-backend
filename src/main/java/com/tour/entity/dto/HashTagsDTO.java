package com.tour.entity.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.tour.entity.HashTag;

public class HashTagsDTO {
	
	private Long id;
	private String name;
	private List<TravelStoryDTO> story;
	private List<BlogPostDTO> blogPost;
	
	public HashTagsDTO() {}
	
	public HashTagsDTO(HashTag hashTag) {
		this.id = hashTag.getId();
		this.name = hashTag.getName();
		this.story = hashTag.getTravelStory().stream().map(n -> new TravelStoryDTO(n)).collect(Collectors.toList());
		this.blogPost = hashTag.getBlogPost().stream().map(n -> new BlogPostDTO(n)).collect(Collectors.toList());
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

	public List<TravelStoryDTO> getStory() {
		return story;
	}

	public void setStory(List<TravelStoryDTO> story) {
		this.story = story;
	}

	public List<BlogPostDTO> getBlogPost() {
		return blogPost;
	}

	public void setBlogPost(List<BlogPostDTO> blogPost) {
		this.blogPost = blogPost;
	}
	
	
	
	 
	

}
