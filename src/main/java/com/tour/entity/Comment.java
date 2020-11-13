/**
 * 
 */
package com.tour.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.tour.entity.dto.CommentDTO;

/**
 * @author 91945
 *
 */
@Entity
@Table(name = "comment")
public class Comment extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "message", length = 100000)
	private String message;

//	@OneToMany(cascade = CascadeType.ALL)
//	@JoinColumn(name = "comment_id")
//	private List<Comment> comments;

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "parent_id")
	private Comment parentComment;

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "candidate_id")
	private User commentedBy;

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "travel_story_id")
	private TravelStory post;
	
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "recomandation_blog_id")
	private RecomandationBlog recomandationBlog;

	public Comment(CommentDTO commentDTO ) {
		this.id = commentDTO.getId();
		this.message = commentDTO.getMessage();
	}

	public Comment() {
		
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

	public Comment getParentComment() {
		return parentComment;
	}

	public void setParentComment(Comment parentComment) {
		this.parentComment = parentComment;
	}

	public User getCommentedBy() {
		return commentedBy;
	}

	public void setCommentedBy(User commentedBy) {
		this.commentedBy = commentedBy;
	}

	public TravelStory getPost() {
		return post;
	}

	public void setPost(TravelStory post) {
		this.post = post;
	}

}
