/**
 * 
 */
package com.tour.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tour.entity.dto.RecomandationBlogDTO;
import com.tour.services.RecomandationBlogService;

/**
 * @author RamaNand
 *
 */
@RestController
@RequestMapping("")
public class RecomandationBlogController {

	@Autowired
	private RecomandationBlogService recomandationBlogService;

	@PostMapping("/recomandation-blog")
	public RecomandationBlogDTO saveBlog(@RequestBody RecomandationBlogDTO recomandationBlogDTO) {
		return recomandationBlogService.save(recomandationBlogDTO);
	}

	@GetMapping("/recomandation-blog/{id}")
	public RecomandationBlogDTO getBlog(@PathVariable Long id) {
		return recomandationBlogService.findDTOById(id);
	}
	
	@DeleteMapping("/recomandation-blog/{id}")
	public void deleteBlog(@PathVariable Long id) {
		recomandationBlogService.deleteBlog(id);
	}

	@PostMapping("/recomandation-blog/like/{storyId}")
	public void likeBlog(@PathVariable Long blogId) {
		recomandationBlogService.likeBlog(blogId);
	}

	@GetMapping("/all-recomandation-blog")
	public List<RecomandationBlogDTO> getAllBlog() {
		return recomandationBlogService.findAll();
	}

}
