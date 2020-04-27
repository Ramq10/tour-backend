/**
 * 
 */
package com.tour.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tour.entity.dto.BlogPostDTO;
import com.tour.services.BlogPostService;

/**
 * @author 91945
 *
 */
@RestController
@RequestMapping("")
public class BlogPostController {

	@Autowired
	private BlogPostService blogPostService;

	@PostMapping("/blog")
	public void saveOrUpdate(@RequestBody BlogPostDTO blogPostDTO) {
		blogPostService.saveOrUpdate(blogPostDTO);
	}

	@GetMapping("/blog/{id}")
	public BlogPostDTO getBlogById(@PathVariable Long id) {
		return blogPostService.getBlogDTOById(id);
	}

	@GetMapping("/all-blog")
	public List<BlogPostDTO> getAllBlog(@RequestParam(required = false) String sortedBy) {
		return blogPostService.getAllBlogPost(sortedBy);
	}

	@GetMapping("/v-blog")
	public List<BlogPostDTO> getAllVideoBlog(@RequestParam(required = false) String sortedBy) {
		return blogPostService.getAllVideoBlogPost(sortedBy);
	}

	@GetMapping("/blog-genre")
	public List<String> getBlogGenre() {
		return blogPostService.getAllBlog();
	}

	@GetMapping("/all-blog/tags/{id}")
	public List<BlogPostDTO> getAllStoryTags(@PathVariable Long id) {
		return blogPostService.getBlogPostByTag(id);
	}

}
