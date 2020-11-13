/**
 * 
 */
package com.tour.entity;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.tour.entity.dto.RecomandationBlogDTO;

/**
 * @author RamaNand
 *
 */
@Entity
@Table(name = "recomandation_blog")
public class RecomandationBlog extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "title", length = 100)
	private String title;

	@Column(name = "discription", length = 100000)
	private String description;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private User user;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "image_id")
	private File image;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "like_user_recommandation_blog", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "recomandation_blog_id", referencedColumnName = "id"))
	private Set<User> likedBy;

	@OneToMany(mappedBy = "recomandationBlog", cascade = CascadeType.ALL)
	private List<Comment> comments;

	@Column(name = "view")
	private Long view;

	public RecomandationBlog() {

	}

	public RecomandationBlog(RecomandationBlogDTO recomandationBlogDTO) {
		this.id = recomandationBlogDTO.getId();
		this.title = recomandationBlogDTO.getTitle();
		this.description = recomandationBlogDTO.getDescription();
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public File getImage() {
		return image;
	}

	public void setImage(File image) {
		this.image = image;
	}

	public Set<User> getLikedBy() {
		return likedBy;
	}

	public void setLikedBy(Set<User> likedBy) {
		this.likedBy = likedBy;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public Long getView() {
		return view;
	}

	public void setView(Long view) {
		this.view = view;
	}

}
