/**
 * 
 */
package com.tour.entity.dto;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.util.List;

import com.tour.entity.BlogPost;
import com.tour.entity.HashTag;

/**
 * @author 91945
 *
 */
public class BlogPostDTO {

	private Long id;
	private String url;
	private Long blogImageId;
	private String blogGenre;
	private String title;
	private String summery;
	private Long userId;
	private String userName;
	private Long view;
	private List<HashTag> tags;
	private boolean vBlog;
	private LocalDate postedDate;

	public BlogPostDTO(BlogPost blogPost) {
		this.id = blogPost.getId();
		try {
			this.title = new String(blogPost.getTitle().getBytes("ISO-8859-1"), "UTF-8");
			this.summery = new String(blogPost.getSummery().getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		this.url = blogPost.getUrl();
		this.blogImageId = blogPost.getBlogImage() != null ? blogPost.getBlogImage().getId() : null;
		this.blogGenre = blogPost.getBlogGenre() != null ? blogPost.getBlogGenre().getGenre() : null;
		this.title = blogPost.getTitle();
		this.userId = blogPost.getBlogger() !=null ? blogPost.getBlogger().getId() : null;
		this.userName = blogPost.getBlogger() !=null ? blogPost.getBlogger().getName() : null;
		this.summery = blogPost.getSummery();
		this.vBlog = blogPost.getvBlog();
		this.postedDate = blogPost.getCreateDate();
		this.view = blogPost.getView();
//		this.setTags(blogPost.getTags());
	}
	
	public BlogPostDTO(BlogPost blogPost, String s) {
		this.id = blogPost.getId();
		this.url = blogPost.getUrl();
		try {
			this.title = new String(blogPost.getTitle().getBytes("ISO-8859-1"), "UTF-8");
			this.summery = new String(blogPost.getSummery().getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		this.title = blogPost.getTitle();
		this.summery = blogPost.getSummery();
		this.vBlog = blogPost.getvBlog();
		this.postedDate = blogPost.getCreateDate();
		this.view = blogPost.getView();
	}


	public BlogPostDTO() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Long getBlogImageId() {
		return blogImageId;
	}

	public void setBlogImageId(Long blogImageId) {
		this.blogImageId = blogImageId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSummery() {
		return summery;
	}

	public void setSummery(String summery) {
		this.summery = summery;
	}

	public boolean isvBlog() {
		return vBlog;
	}

	public void setvBlog(boolean vBlog) {
		this.vBlog = vBlog;
	}

	public String getBlogGenre() {
		return blogGenre;
	}

	public void setBlogGenre(String blogGenre) {
		this.blogGenre = blogGenre;
	}

	public LocalDate getPostedDate() {
		return postedDate;
	}

	public void setPostedDate(LocalDate postedDate) {
		this.postedDate = postedDate;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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
