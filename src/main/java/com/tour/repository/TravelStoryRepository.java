/**
 * 
 */
package com.tour.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tour.entity.HashTag;
import com.tour.entity.TravelStory;

/**
 * @author Ramanand
 *
 */
@Repository
public interface TravelStoryRepository extends JpaRepository<TravelStory, Long> {
	
	@Query("select t from TravelStory t order by t.createDate desc")
	public List<TravelStory> findAll();
	
	@Query("select t from TravelStory t order by  t.view desc")
	public List<TravelStory> findAllByView();
	
//	@Query("select t from TravelStory t order by t.likedBy desc")
//	public List<TravelStory> findAllByLike();
	
	@Query("select t from TravelStory t order by  t.view desc")
	public List<TravelStory> findAllByViewLimit();
	
	@Query("select t from TravelStory t order by t.createDate desc")
	public List<TravelStory> findAllLimit();
	
//	public List<TravelStory> findByTags(List tags);
}
