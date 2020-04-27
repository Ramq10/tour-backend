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

import com.tour.entity.HashTag;
import com.tour.entity.dto.TravelStoryDTO;
import com.tour.services.TravelStoryService;

/**
 * @author Ramanand
 *
 */
@RestController
@RequestMapping("")
public class TravelStoryController {

	@Autowired
	private TravelStoryService experienceStoryService;

	@PostMapping("/story")
	public TravelStoryDTO saveStory(@RequestBody TravelStoryDTO experienceStoryDTO) {
		return experienceStoryService.save(experienceStoryDTO);
	}

	@GetMapping("/story/{id}")
	public TravelStoryDTO getStory(@PathVariable Long id) {
		return experienceStoryService.findDTOById(id);
	}

	@PostMapping("/story/like/{storyId}")
	public void likeStory(@PathVariable Long storyId) {
		experienceStoryService.likeStory(storyId);
	}
	
	@PostMapping("/story/dislike/{storyId}")
	public void dislikeStory(@PathVariable Long storyId) {
		experienceStoryService.dislikeStory(storyId);
	}
	
	@GetMapping("/all-story")
	public List<TravelStoryDTO> getAllStory(@RequestParam(required = false) String sortedBy) {
		return experienceStoryService.getAll(sortedBy);
	}
	
	@GetMapping("/tags")
	public List<HashTag> getAllTags() {
		return experienceStoryService.getAllTags();
	}
	
	@GetMapping("/all-story/tags/{id}")
	public List<TravelStoryDTO> getAllStoryTags(@PathVariable Long id) {
		return experienceStoryService.getStoryByTag(id);
	}
	
}
