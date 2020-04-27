/**
 * 
 */
package com.tour.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tour.entity.ContactUs;

/**
 * @author Ramanand
 *
 */
@Repository
public interface ContactUsRepository extends JpaRepository<ContactUs,Long> {

}
