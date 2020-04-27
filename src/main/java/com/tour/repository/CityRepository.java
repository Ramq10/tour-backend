/**
 * 
 */
package com.tour.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tour.entity.City;

/**
 * @author Ramanand
 *
 */
@Repository
public interface CityRepository extends JpaRepository<City, Long> {

}
