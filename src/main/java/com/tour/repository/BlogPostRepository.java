/**
 * 
 */
package com.tour.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

//import com.brightlink.sidewinder.domain.DID;
import com.tour.entity.BlogPost;
import com.tour.entity.dto.LocationDTO;
import com.tour.enums.BlogGenres;

/**
 * @author 91945
 *
 */
@Repository
public interface BlogPostRepository extends JpaRepository<BlogPost, Long>, JpaSpecificationExecutor<BlogPost>,
		PagingAndSortingRepository<BlogPost, Long> {

	public BlogPost findByUrl(String url);

	public List<BlogPost> findByvBlogOrderByCreateDateDesc(boolean vBlog);

	public List<BlogPost> findByvBlogOrderByCreateDateAsc(boolean vBlog);

	public List<BlogPost> findByvBlogOrderByViewDesc(boolean vBlog);

	public List<BlogPost> findByvBlogAndBlogGenreOrderByCreateDateDesc(boolean vBlog, BlogGenres blogGenre);

	public Long countByvBlog(boolean vBlog);

	public List<BlogPost> findByvBlogAndTitleContainingIgnoreCase(boolean vBlog, String title);

	public BlogPost findFirstByOrderByIdDesc();

	@Query(value = "SELECT new com.tour.entity.dto.LocationDTO(a, count(d)) FROM BlogPost d "
			+ "left join d.country a where a is not null and vBlog=:vBlog" + " group by a.id")
	public List<LocationDTO> findByCountryAndDeleted(@Param("vBlog") boolean vBlog);

}
