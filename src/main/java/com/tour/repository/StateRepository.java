/**
 * 
 */
package com.tour.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tour.entity.State;

/**
 * @author Ramanand
 *
 */
@Repository
public interface StateRepository extends JpaRepository<State, Long> {

	State findByName(String name);
	

}
