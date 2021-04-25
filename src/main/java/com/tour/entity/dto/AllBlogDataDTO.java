package com.tour.entity.dto;

import java.util.List;

public class AllBlogDataDTO {
	
	private List<BlogPostDTO> blogPosts;
	private long totalBlogs;

	public AllBlogDataDTO(List<BlogPostDTO> blogPosts, long totalBlogs) {
		this.blogPosts = blogPosts;
		this.totalBlogs = totalBlogs;
	}

	public AllBlogDataDTO() {}

	public List<BlogPostDTO> getBlogPosts() {
		return blogPosts;
	}

	public void setBlogPosts(List<BlogPostDTO> blogPosts) {
		this.blogPosts = blogPosts;
	}

	public long getTotalBlogs() {
		return totalBlogs;
	}

	public void setTotalBlogs(long totalBlogs) {
		this.totalBlogs = totalBlogs;
	}
	
	

}
