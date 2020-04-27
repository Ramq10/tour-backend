/**
 * 
 */
package com.tour.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tour.entity.User;

/**
 * @author Ramanand
 *
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	Optional<User> findById(Long id);
	
	User findByEmail(String email);
}
