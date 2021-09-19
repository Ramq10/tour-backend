/**
 * 
 */
package com.tour.entity.dto;

import java.util.Set;

/**
 * @author Ramanand
 *
 */
public class SearchColumnDTO {
	
	private String title;
	private String url;
	private String summery;
	private String fromDate;
	private String toDate;
	private String country;
	private String city;
	private String state;
	private Set<String> blogger;
	private Set<String> genre;
	private Boolean ascView;
	private Boolean ascDate;
	private Boolean vlog;
	private int pageNo;
	
	public SearchColumnDTO() {
		
	}

	public String getCountry() {
		return country;
	}

	public String getCity() {
		return city;
	}

	public String getState() {
		return state;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getSummery() {
		return summery;
	}

	public void setSummery(String summery) {
		this.summery = summery;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public Set<String> getBlogger() {
		return blogger;
	}

	public void setBlogger(Set<String> blogger) {
		this.blogger = blogger;
	}

	public Set<String> getGenre() {
		return genre;
	}

	public void setGenre(Set<String> genre) {
		this.genre = genre;
	}

	public Boolean getAscView() {
		return ascView;
	}

	public void setAscView(Boolean ascView) {
		this.ascView = ascView;
	}

	public Boolean getAscDate() {
		return ascDate;
	}

	public void setAscDate(Boolean ascDate) {
		this.ascDate = ascDate;
	}

	public Boolean getVlog() {
		return vlog;
	}

	public void setVlog(Boolean vlog) {
		this.vlog = vlog;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

}
