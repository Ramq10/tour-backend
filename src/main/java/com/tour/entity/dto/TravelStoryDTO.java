/**
 * 
 */
package com.tour.entity.dto;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.tour.entity.File;
import com.tour.entity.TravelStory;

/**
 * @author Ramanand
 *
 */
public class TravelStoryDTO {

	private Long id;
	private String title;
	private String description;
	private Long userId;
	private String userName;
	private List<File> files;
	private Set<UserDTO> likedBy;
	private Long fileId;
	private long likeCount;
	private long commentCount;
	private LocalDate createDate;
	private List<HashTagsDTO> tags;
	
	private List<CommentDTO> comments;
	private Long view;

	public TravelStoryDTO(TravelStory travelStory) {
		this.id = travelStory.getId();
		try {
//			this.title = new String(travelStory.getTitle().getBytes("ISO-8859-1"), "UTF-8");
			this.description = new String(travelStory.getDescription().getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		this.title = travelStory.getTitle();
		this.fileId = (travelStory.getFiles() != null && travelStory.getFiles().size() != 0
				? travelStory.getFiles().get(0).getId()
				: null);
		this.userId = travelStory.getUser() != null ? travelStory.getUser().getId() : null;
		this.userName = travelStory.getUser() != null ? travelStory.getUser().getName() : "anonymous";
		this.files = travelStory.getFiles();
		this.likeCount = travelStory.getLikedBy() != null ? travelStory.getLikedBy().size() : 0;
		this.commentCount = travelStory.getComments() != null ? travelStory.getComments().size() : 0;
		this.likedBy = travelStory.getLikedBy() != null
				? travelStory.getLikedBy().stream().map(t -> new UserDTO(t, "")).collect(Collectors.toSet())
				: null;
		this.setCreateDate(travelStory.getCreateDate());
		this.view = travelStory.getView();
		this.setTags(travelStory.getTags().stream().map(m -> new HashTagsDTO(m)).collect(Collectors.toList()));
	}

	public TravelStoryDTO(TravelStory travelStory, String a) {
		this.id = travelStory.getId();
		try {
			this.title = new String(travelStory.getTitle().getBytes("ISO-8859-1"), "UTF-8");
			this.description = new String(travelStory.getDescription().getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		this.likeCount = travelStory.getLikedBy() != null ? travelStory.getLikedBy().size() : 0;
		this.setCreateDate(travelStory.getCreateDate());
		this.view = travelStory.getView();
	}

	/**
	 * 
	 */
	public TravelStoryDTO() {
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

	public List<File> getFiles() {
		return files;
	}

	public void setFiles(List<File> files) {
		this.files = files;
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

	public Long getFileId() {
		return fileId;
	}

	public void setFileId(Long fileId) {
		this.fileId = fileId;
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

	public long getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(long commentCount) {
		this.commentCount = commentCount;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public List<HashTagsDTO> getTags() {
		return tags;
	}

	public void setTags(List<HashTagsDTO> tags) {
		this.tags = tags;
	}

}
