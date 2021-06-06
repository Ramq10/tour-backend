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
	private Long countryId;
	private String countryName;
	private Long stateId;
	private String stateName;
	private Long cityId;
	private String cityName;

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
		this.cityId = blogPost.getCity() != null ? blogPost.getCity().getId() : null;
		this.cityName = blogPost.getCity() != null ? blogPost.getCity().getName() : null;
		this.stateId = blogPost.getState() != null ? blogPost.getState().getId() : null;
		this.stateName = blogPost.getState() != null ? blogPost.getState().getName() : null;
		this.countryId = blogPost.getCountry() != null ? blogPost.getCountry().getId() : null;
		this.countryName = blogPost.getCountry() != null ? blogPost.getCountry().getName() : null;
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
		this.cityId = blogPost.getCity() != null ? blogPost.getCity().getId() : null;
		this.cityName = blogPost.getCity() != null ? blogPost.getCity().getName() : null;
		this.stateId = blogPost.getState() != null ? blogPost.getState().getId() : null;
		this.stateName = blogPost.getState() != null ? blogPost.getState().getName() : null;
		this.countryId = blogPost.getCountry() != null ? blogPost.getCountry().getId() : null;
		this.countryName = blogPost.getCountry() != null ? blogPost.getCountry().getName() : null;
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

	public Long getCountryId() {
		return countryId;
	}

	public String getCountryName() {
		return countryName;
	}

	public Long getStateId() {
		return stateId;
	}

	public String getStateName() {
		return stateName;
	}

	public Long getCityId() {
		return cityId;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public void setStateId(Long stateId) {
		this.stateId = stateId;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BlogPostDTO other = (BlogPostDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	

}
