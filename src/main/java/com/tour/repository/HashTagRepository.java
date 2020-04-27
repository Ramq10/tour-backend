/**
 * 
 */
package com.tour.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tour.entity.HashTag;

/**
 * @author 91945
 *
 */
public interface HashTagRepository extends JpaRepository<HashTag, Long> {
	
	HashTag findByName(String name);
	
}
