/**
 * 
 */
package com.tour.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tour.entity.File;
import com.tour.entity.HashTag;
import com.tour.entity.TravelStory;
import com.tour.entity.User;
import com.tour.entity.dto.HashTagsDTO;
import com.tour.entity.dto.TravelStoryDTO;
import com.tour.exception.UnprocessableEntityException;
import com.tour.repository.HashTagRepository;
import com.tour.repository.TravelStoryRepository;

/**
 * @author Ramanand
 *
 */
@Service
public class TravelStoryService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private TravelStoryRepository travelStoryRepository;
	@Autowired
	private UserService userService;
	@Autowired
	private FileService fileService;
	@Autowired
	private HashTagRepository hashTagRepository;

	/**
	 * @param multipartfile
	 * @param travelStoryDTO
	 * @return
	 */
	public TravelStoryDTO save(TravelStoryDTO travelStoryDTO) {
		logger.info("Inside TravelStoryServce::save");
		validate(travelStoryDTO);
		User user = userService.getLoggedInUser();
		List<File> storyFiles = new ArrayList<File>();
		List<HashTag> tags = new ArrayList<HashTag>();
		if (travelStoryDTO.getFiles() != null) {
			storyFiles = validateStoryFile(travelStoryDTO.getFiles());
		}
		if (travelStoryDTO.getTags() != null) {
			tags = validateTags(travelStoryDTO.getTags());
		}
		TravelStory experienceStory = new TravelStory(travelStoryDTO);
		experienceStory.setUser(user);
		experienceStory.setFiles(storyFiles);
		experienceStory.setTags(tags);
		experienceStory.setCreateDate(LocalDate.now());
		experienceStory.setView(0l);
		TravelStoryDTO storyDTO = new TravelStoryDTO(travelStoryRepository.save(experienceStory), "");
		logger.info("Completed TravelStoryServce::save");
		return storyDTO;
	}

	/**
	 * @param tags
	 * @return
	 */
	private List<HashTag> validateTags(List<HashTagsDTO> tags) {
		List<HashTag> hashTags = new ArrayList<HashTag>();
		tags.stream().forEach(f -> {
			hashTags.add(hashTagRepository.findById(f.getId()).get());
		});
		return hashTags;
	}

	/**
	 * @param files
	 * @return
	 */
	private List<File> validateStoryFile(List<File> files) {
		List<File> storyFiles = new ArrayList<>();
		files.stream().forEach(f -> {
			storyFiles.add(fileService.getFileById(f.getId()));
		});
		return storyFiles;
	}

	/**
	 * @param experienceStoryDTO
	 */
	private void validate(TravelStoryDTO experienceStoryDTO) {
		logger.info("Inside TravelStoryServce::validate");
		if (StringUtils.isBlank(experienceStoryDTO.getTitle())) {
			throw new UnprocessableEntityException("Please enter title.");
		}
		if (StringUtils.isBlank(experienceStoryDTO.getDescription())) {
			throw new UnprocessableEntityException("Please enter discription of your journey.");
		}
		logger.info("Completed TravelStoryServce::save");
	}

	public void likeStory(Long storyId) {
		logger.info("Inside TravelStoryServce::likeStory");
		User user = userService.getLoggedInUser();
		TravelStory experienceStory = findById(storyId);
		experienceStory.getLikedBy().add(user);
		travelStoryRepository.save(experienceStory);
		logger.info("Completed TravelStoryServce::likeStory");
	}

	public TravelStory findById(Long id) {
		logger.info("Inside TravelStoryServce::findById");
		Optional<TravelStory> experienceStory = travelStoryRepository.findById(id);
		if (experienceStory == null || !experienceStory.isPresent()) {
			throw new UnprocessableEntityException("Invalid ExperienceStory.");
		}
		logger.info("Completed TravelStoryServce::findById");
		return experienceStory.get();
	}

	public TravelStoryDTO findDTOById(Long id) {
		logger.info("Inside TravelStoryServce::findDTOById");
		TravelStory travelStory = findById(id);
		Long view = travelStory.getView();
		view = view + 1;
		travelStory.setView(view);
		travelStoryRepository.save(travelStory);
		logger.info("Inside TravelStoryServce::findDTOById");
		return new TravelStoryDTO(travelStory);
	}

	public List<TravelStoryDTO> getAll(String sortedBy) {
		switch (sortedBy) {
		case "viewed":
			return travelStoryRepository.findAllByView().stream().map(n -> new TravelStoryDTO(n))
					.collect(Collectors.toList());
//		case "liked":
//			return travelStoryRepository.findAllByLike().stream().map(n -> new TravelStoryDTO(n)).collect(Collectors.toList());
		case "all-t":
			return travelStoryRepository.findAllLimit().stream().map(n -> new TravelStoryDTO(n))
					.collect(Collectors.toList());
//		case "viewed-t":
//			return travelStoryRepository.findAllByViewLimit().stream().map(n -> new TravelStoryDTO(n)).collect(Collectors.toList());
		default:
			return travelStoryRepository.findAll().stream().map(n -> new TravelStoryDTO(n))
					.collect(Collectors.toList());
		}
//		return travelStoryRepository.findAll().stream().map(n -> new TravelStoryDTO(n)).collect(Collectors.toList());
	}

	public void dislikeStory(Long storyId) {
		logger.info("Inside TravelStoryServce::dislikeStory");
		User user = userService.getLoggedInUser();
		TravelStory experienceStory = findById(storyId);
		if (experienceStory.getLikedBy().contains(user)) {
			experienceStory.getLikedBy().remove(user);
		}
		travelStoryRepository.save(experienceStory);
		logger.info("Completed TravelStoryServce::dislikeStory");

	}

	public List<HashTagsDTO> getAllTags() {
		return hashTagRepository.findAll().stream().map(n->new HashTagsDTO(n)).collect(Collectors.toList());
	}
	
	public List<TravelStoryDTO> getStoryByTag(Long tagId) {
		HashTag hashTag = hashTagRepository.findById(tagId).get();
		if(hashTag != null) {
			return hashTag.getTravelStory().stream().map(n -> new TravelStoryDTO(n))
					.collect(Collectors.toList());
		} else {
			logger.info("Invalid HashTag. id = {}",tagId);
			throw new UnprocessableEntityException("Invalid HashTag.");
		}
	}

}
