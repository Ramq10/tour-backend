/**
 * 
 */
package com.tour.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.tour.entity.dto.BlogPostDTO;
import com.tour.enums.BlogGenres;

/**
 * @author 91945
 *
 */
@Entity
@Table(name = "blog_post")
public class BlogPost extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "url", length = 300)
	private String url;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "blog_image")
	private File blogImage;

	@Enumerated(EnumType.STRING)
	@JoinColumn(name = "blog_genre")
	private BlogGenres blogGenre;

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "candidate_id")
	private User blogger;

	@Column(name = "title", length = 100)
	private String title;
	
	@Column(name = "view")
	private Long view;

	@Column(name = "summery", length = 500)
	private String summery;

	@Column(name = "vblog")
	private Boolean vBlog;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "tag_blog_post", joinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "blog_id", referencedColumnName = "id"))
	private List<HashTag> tags;

	public BlogPost(BlogPostDTO blogPostDTO) {
		this.id = blogPostDTO.getId();
		this.title = blogPostDTO.getTitle();
		this.summery = blogPostDTO.getSummery();
		this.blogGenre = BlogGenres.getEnum(blogPostDTO.getBlogGenre());
		this.vBlog = blogPostDTO.isvBlog();
	}

	public BlogPost() {
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

	public File getBlogImage() {
		return blogImage;
	}

	public void setBlogImage(File blogImage) {
		this.blogImage = blogImage;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	public Long getView() {
		return view;
	}

	public void setView(Long view) {
		this.view = view;
	}

	public String getSummery() {
		return summery;
	}

	public void setSummery(String summery) {
		this.summery = summery;
	}

	public Boolean getvBlog() {
		return vBlog;
	}

	public void setvBlog(Boolean vBlog) {
		this.vBlog = vBlog;
	}

	public User getBlogger() {
		return blogger;
	}

	public void setBlogger(User blogger) {
		this.blogger = blogger;
	}

	public BlogGenres getBlogGenre() {
		return blogGenre;
	}

	public void setBlogGenre(BlogGenres blogGenre) {
		this.blogGenre = blogGenre;
	}

	public List<HashTag> getTags() {
		return tags;
	}

	public void setTags(List<HashTag> tags) {
		this.tags = tags;
	}

}
