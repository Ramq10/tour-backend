/**
 * 
 */
package com.tour.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tour.entity.SocialSiteLink;

/**
 * @author Ramanand
 *
 */
@Repository
public interface SocialSiteLinkRepository extends JpaRepository<SocialSiteLink, Long> {
	
	Optional<SocialSiteLink> findById(Long id);

}
