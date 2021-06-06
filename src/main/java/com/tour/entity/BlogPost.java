/**
 * 
 */
package com.tour.entity;

import java.io.UnsupportedEncodingException;
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
	
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "country_id")
	private Country country;
	
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "state_id")
	private State state;
	
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "city_id")
	private City city;

	public BlogPost(BlogPostDTO blogPostDTO) {
		this.id = blogPostDTO.getId();
		try {
			this.title = new String(blogPostDTO.getTitle().getBytes("UTF-8"), "ISO-8859-1");
			this.summery = new String(blogPostDTO.getSummery().getBytes("UTF-8"), "ISO-8859-1");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
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
	
	public Country getCountry() {
		return country;
	}

	public State getState() {
		return state;
	}

	public City getCity() {
		return city;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public void setState(State state) {
		this.state = state;
	}

	public void setCity(City city) {
		this.city = city;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BlogPost other = (BlogPost) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
