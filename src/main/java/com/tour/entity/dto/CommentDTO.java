/**
 * 
 */
package com.tour.entity.dto;

import java.util.List;

import com.tour.entity.Comment;

/**
 * @author 91945
 *
 */
public class CommentDTO {

	private Long id;
	private String message;
	private Long parentCommentId;
	private Long postId;
	private Long commentedBy;
	private String commentByUser;
	private List<CommentDTO> childComment;

	public CommentDTO() {

	}

	public CommentDTO(Comment comment) {
		this.id = comment.getId();
		this.message = comment.getMessage();
		this.commentedBy = comment.getCommentedBy().getId();
		this.commentByUser = comment.getCommentedBy().getName();
		this.postId = comment.getPost().getId();
		this.parentCommentId = comment.getParentComment() != null ? comment.getParentComment().getId() : null;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Long getParentCommentId() {
		return parentCommentId;
	}

	public void setParentCommentId(Long parentCommentId) {
		this.parentCommentId = parentCommentId;
	}

	public Long getPostId() {
		return postId;
	}

	public void setPostId(Long postId) {
		this.postId = postId;
	}

	public Long getCommentedBy() {
		return commentedBy;
	}

	public void setCommentedBy(Long commentedBy) {
		this.commentedBy = commentedBy;
	}

	public List<CommentDTO> getChildComment() {
		return childComment;
	}

	public void setChildComment(List<CommentDTO> childComment) {
		this.childComment = childComment;
	}

	public String getCommentByUser() {
		return commentByUser;
	}

	public void setCommentByUser(String commentByUser) {
		this.commentByUser = commentByUser;
	}

}
