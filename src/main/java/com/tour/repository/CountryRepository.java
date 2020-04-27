/**
 * 
 */
package com.tour.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tour.entity.Country;

/**
 * @author Ramanand
 *
 */
@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {

	Optional<Country> findById(Long id);
	
	Country findByName(String name);
}
