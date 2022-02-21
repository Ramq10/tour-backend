/**
 * 
 */
package com.tour.repository;

import java.util.List;
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

	List<User> findAllByOrderByIdDesc();

//	for dropdown
	List<User> findAllByBlogger(boolean blogger);

//  for dropdown
	List<User> findAllByVlogger(boolean vlogger);

// for dropdown
	List<User> findAllByStoryWriter(boolean storyWriter);

	List<User> findAllByOrderByIdAsc();
}
