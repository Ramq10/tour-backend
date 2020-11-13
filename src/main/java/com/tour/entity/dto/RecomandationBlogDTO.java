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
	private String description;
	private Long userId;
	private String userName;
	private File image;
	private Long imageId;
	private Set<UserDTO> likedBy;
	private long likeCount;
	private long commentCount;
	private LocalDate createDate;
	private List<CommentDTO> comments;
	private Long view;

	public RecomandationBlogDTO() {
	}

	public RecomandationBlogDTO(RecomandationBlog recomandationBlog) {
		this.id = recomandationBlog.getId();
		this.description = recomandationBlog.getDescription();
		this.title = recomandationBlog.getTitle();
		this.userId = recomandationBlog.getUser() != null ? recomandationBlog.getUser().getId() : null;
		this.userName = recomandationBlog.getUser() != null ? recomandationBlog.getUser().getName() : "anonymous";
		this.imageId = recomandationBlog.getImage().getId();
		this.likeCount = recomandationBlog.getLikedBy() != null ? recomandationBlog.getLikedBy().size() : 0;
		this.commentCount = recomandationBlog.getComments() != null ? recomandationBlog.getComments().size() : 0;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public long getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(long commentCount) {
		this.commentCount = commentCount;
	}

	public LocalDate getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDate createDate) {
		this.createDate = createDate;
	}

	public List<CommentDTO> getComments() {
		return comments;
	}

	public void setComments(List<CommentDTO> comments) {
		this.comments = comments;
	}

	public Long getView() {
		return view;
	}

	public void setView(Long view) {
		this.view = view;
	}

	public File getImage() {
		return image;
	}

	public void setImage(File image) {
		this.image = image;
	}

	public Long getImageId() {
		return imageId;
	}

	public void setImageId(Long imageId) {
		this.imageId = imageId;
	}

}
