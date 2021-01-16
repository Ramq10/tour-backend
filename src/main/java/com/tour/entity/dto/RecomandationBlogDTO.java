/**
 * 
 */
package com.tour.entity.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.tour.entity.File;
import com.tour.entity.RecomandationBlog;

/**
 * @author RamaNand
 *
 */
public class RecomandationBlogDTO {

	private Long id;
	private String title;
	private Long bannerId;
	private String primaryDescription;
	private List<File> files;
	private String secondaryDescription;
	private Long userId;
	private String userName;
	private Set<UserDTO> likedBy;
	private long likeCount;
//	private long commentCount;
	private LocalDate createDate;
//	private List<CommentDTO> comments;
	private Long view;

	public RecomandationBlogDTO() {
	}

	public RecomandationBlogDTO(RecomandationBlog recomandationBlog) {
		this.id = recomandationBlog.getId();
		this.primaryDescription = recomandationBlog.getPrimaryDescription();
		this.secondaryDescription = recomandationBlog.getSecondaryDescription();
		this.title = recomandationBlog.getTitle();
		this.files = recomandationBlog.getFiles();
		this.userId = recomandationBlog.getUser() != null ? recomandationBlog.getUser().getId() : null;
		this.userName = recomandationBlog.getUser() != null ? recomandationBlog.getUser().getName() : "anonymous";
		this.bannerId = recomandationBlog.getBanner().getId();
		this.likeCount = recomandationBlog.getLikedBy() != null ? recomandationBlog.getLikedBy().size() : 0;
//		this.commentCount = recomandationBlog.getComments() != null ? recomandationBlog.getComments().size() : 0;
		this.likedBy = recomandationBlog.getLikedBy() != null
				? recomandationBlog.getLikedBy().stream().map(t -> new UserDTO(t, "")).collect(Collectors.toSet())
				: null;
		this.setCreateDate(recomandationBlog.getCreateDate());
		this.view = recomandationBlog.getView();
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

	public Long getBannerId() {
		return bannerId;
	}

	public void setBannerId(Long bannerId) {
		this.bannerId = bannerId;
	}

	public String getPrimaryDescription() {
		return primaryDescription;
	}

	public void setPrimaryDescription(String primaryDescription) {
		this.primaryDescription = primaryDescription;
	}

	public List<File> getFiles() {
		return files;
	}

	public void setFiles(List<File> files) {
		this.files = files;
	}

	public String getSecondaryDescription() {
		return secondaryDescription;
	}

	public void setSecondaryDescription(String secondaryDescription) {
		this.secondaryDescription = secondaryDescription;
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

	public Set<UserDTO> getLikedBy() {
		return likedBy;
	}

	public void setLikedBy(Set<UserDTO> likedBy) {
		this.likedBy = likedBy;
	}

	public long getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(long likeCount) {
		this.likeCount = likeCount;
	}

	public LocalDate getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDate createDate) {
		this.createDate = createDate;
	}

	public Long getView() {
		return view;
	}

	public void setView(Long view) {
		this.view = view;
	}

}
