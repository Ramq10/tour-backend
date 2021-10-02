/**
 * 
 */
package com.tour.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

//import com.brightlink.sidewinder.domain.DID;
import com.tour.entity.BlogPost;
import com.tour.enums.BlogGenres;

/**
 * @author 91945
 *
 */
@Repository
public interface BlogPostRepository extends JpaRepository<BlogPost, Long>, JpaSpecificationExecutor<BlogPost>, PagingAndSortingRepository<BlogPost, Long> {
	
	public BlogPost findByUrl(String url);
	
	public List<BlogPost> findByvBlogOrderByCreateDateDesc(boolean vBlog);
	
	public List<BlogPost> findByvBlogOrderByCreateDateAsc(boolean vBlog);
	
	public List<BlogPost> findByvBlogOrderByViewDesc(boolean vBlog);
	
	public List<BlogPost> findByvBlogAndBlogGenreOrderByCreateDateDesc(boolean vBlog, BlogGenres blogGenre);
	
	public Long countByvBlog(boolean vBlog);
	
	public List<BlogPost> findByvBlogAndTitleContainingIgnoreCase(boolean vBlog, String title);

	public BlogPost findFirstByOrderByIdDesc();
	

}
