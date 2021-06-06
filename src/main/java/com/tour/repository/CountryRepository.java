/**
 * 
 */
package com.tour.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import com.tour.entity.Country;
import com.tour.entity.dto.LocationDTO;

/**
 * @author Ramanand
 *
 */
@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {

	Optional<Country> findById(Long id);

	Country findByName(String name);

	List<Country> findAllByOrderByName();

	@Query(value = "select new com.tour.entity.dto.LocationDTO(c.name, s.name, d.name, c.id, s.id, d.id) "
			+ "from Country c left join c.state s left join s.city d "
			+ "where lower(c.name) like %:search% "
			+ "or lower(s.name) like %:search% "
			+ "or lower(d.name) like %:search% "
			+ "order by s.name, d.name")
	public List<LocationDTO> searchLocation(@RequestParam(value = "search") String search);
}
