/**
 * 
 */
package com.tour.entity.dto;

import java.util.Set;

/**
 * @author 91945
 *
 */
public class SearchColumnDTO {
	
	private String title;
	private String url;
	private String summery;
	private String fromDate;
	private String toDate;
	private Set<String> blogger;
	private Set<String> genre;
	private Boolean ascView;
	private Boolean ascDate;
	private Boolean vlog;
	
	public SearchColumnDTO() {
		
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

}
