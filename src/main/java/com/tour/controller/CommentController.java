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
import org.springframework.web.bind.annotation.RestController;

import com.tour.entity.dto.CommentDTO;
import com.tour.services.CommentService;

/**
 * @author 91945
 *
 */
@RestController
@RequestMapping("/comments")
public class CommentController {

	@Autowired
	private CommentService commentService;

	@PostMapping("")
	public void save(@RequestBody CommentDTO commentDTO) {
		commentService.save(commentDTO);
	}

	@GetMapping("/{id}")
	public CommentDTO getCommentById(@PathVariable Long id) {
		return commentService.getCommentDTOById(id);
	}

	@GetMapping("/parent-id/{id}")
	public List<CommentDTO> getCommentByParentId(@PathVariable Long id) {
		return commentService.getCommentDTOByParentId(id);
	}

	@GetMapping("/post-id/{id}")
	public List<CommentDTO> getCommentDTOByTravelStoryId(@PathVariable Long id) {
		return commentService.getCommentDTOByPostId(id);
	}
}
