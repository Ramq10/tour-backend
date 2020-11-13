/**
 * 
 */
package com.tour.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.tour.entity.BlogPost;
import com.tour.entity.RecomandationBlog;

/**
 * @author RamaNand
 *
 */
@Repository
public interface RecomandationBlogRepository extends JpaRepository<RecomandationBlog, Long>, JpaSpecificationExecutor<BlogPost> {

}
