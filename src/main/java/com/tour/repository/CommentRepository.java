/**
 * 
 */
package com.tour.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tour.entity.Comment;

/**
 * @author 91945
 *
 */
@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

	@Query(value = "SELECT * FROM comment c WHERE c.parent_id = :parentId", nativeQuery = true)
	public List<Comment> findByParantId(@Param("parentId") Long parentId);

	@Query(value = "SELECT * FROM comment c WHERE c.travel_story_id = :postId", nativeQuery = true)
	public List<Comment> findByTravelStoryId(@Param("postId") Long travelStoryId);

	@Query(value = "SELECT * FROM comment c WHERE c.travel_story_id = :postId AND c.parent_id = :parentId", nativeQuery = true)
	public List<Comment> findByTravelStoryIdAndParentId(@Param("postId") Long travelStoryId,
			@Param("parentId") Long parentId);

}
