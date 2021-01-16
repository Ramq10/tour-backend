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

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "banner_id")
	private File banner;
	
	@Column(name = "title", length = 100)
	private String title;

	@Column(name = "primary_discription", length = 180)
	private String primaryDescription;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "recomandation_blog_id")
	private List<File> files;
	
	@Column(name = "secondary_discription", length = 100000)
	private String secondaryDescription;

	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "like_user_recommandation_blog", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "recomandation_blog_id", referencedColumnName = "id"))
	private Set<User> likedBy;

//	@OneToMany(mappedBy = "recomandationBlog", cascade = CascadeType.ALL)
//	private List<Comment> comments;

	@Column(name = "view")
	private Long view;

	public RecomandationBlog() {

	}

	public RecomandationBlog(RecomandationBlogDTO recomandationBlogDTO) {
		this.id = recomandationBlogDTO.getId();
		this.title = recomandationBlogDTO.getTitle();
		this.primaryDescription = recomandationBlogDTO.getPrimaryDescription();
		this.secondaryDescription = recomandationBlogDTO.getSecondaryDescription();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public File getBanner() {
		return banner;
	}

	public void setBanner(File banner) {
		this.banner = banner;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Set<User> getLikedBy() {
		return likedBy;
	}

	public void setLikedBy(Set<User> likedBy) {
		this.likedBy = likedBy;
	}

	public Long getView() {
		return view;
	}

	public void setView(Long view) {
		this.view = view;
	}


}
