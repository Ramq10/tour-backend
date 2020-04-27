/**
 * 
 */
package com.tour.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tour.entity.BlogPost;

/**
 * @author 91945
 *
 */
@Repository
public interface BlogPostRepository extends JpaRepository<BlogPost, Long> {
	
	public BlogPost findByUrl(String url);
	
	public List<BlogPost> findByvBlogOrderByCreateDateDesc(boolean vBlog);
	
	public List<BlogPost> findByvBlogOrderByViewAsc(boolean vBlog);

}
