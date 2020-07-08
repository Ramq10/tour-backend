/**
 * 
 */
package com.tour.services;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.ZoneOffset;
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
		TravelStory experienceStoryFromDB = null;
		if (travelStoryDTO.getId() == null) {
			experienceStoryFromDB = new TravelStory(travelStoryDTO);
			experienceStoryFromDB.setView(0l);
			experienceStoryFromDB.setCreateDate(LocalDate.now(ZoneOffset.UTC));
		} else {
			experienceStoryFromDB = findById(travelStoryDTO.getId());
			validateUser(experienceStoryFromDB.getUser());
			experienceStoryFromDB
					.setModifiedDate(LocalDate.now(ZoneOffset.UTC));
			try {
				experienceStoryFromDB.setTitle(new String(travelStoryDTO
						.getTitle().getBytes("UTF-8"), "ISO-8859-1"));
				experienceStoryFromDB.setDescription(new String(travelStoryDTO
						.getDescription().getBytes("UTF-8"), "ISO-8859-1"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}

		experienceStoryFromDB.setUser(user);
		experienceStoryFromDB.setFiles(storyFiles);
		experienceStoryFromDB.setTags(tags);

		TravelStoryDTO storyDTO = new TravelStoryDTO(
				travelStoryRepository.save(experienceStoryFromDB));
		logger.info("Completed TravelStoryServce::save");
		return storyDTO;
	}

	private void validateUser(User user) {
		if (user.getId() != userService.getLoggedInUser().getId()) {
			throw new UnprocessableEntityException(
					"You are not allowed to edit this Story.");
		}
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
			logger.info("Please enter title.");
			throw new UnprocessableEntityException("Please enter title.");
		}
		if (StringUtils.isBlank(experienceStoryDTO.getDescription())) {
			logger.info("Please enter discription of your journey.");
			throw new UnprocessableEntityException(
					"Please enter discription of your journey.");
		}
		if (experienceStoryDTO.getTitle().length() > 100) {
			logger.info("Title length exceeded.");
			throw new UnprocessableEntityException("Title length exceeded.");
		}
		// if (experienceStoryDTO.getDescription().length()<50) {
		// logger.info("Title length exceeded.");
		// throw new UnprocessableEntityException("Title length exceeded.");
		// }
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
		Optional<TravelStory> experienceStory = travelStoryRepository
				.findById(id);
		if (experienceStory == null || !experienceStory.isPresent()) {
			throw new UnprocessableEntityException("Invalid TravelStory.");
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
			return travelStoryRepository.findAllByView().stream()
					.map(n -> new TravelStoryDTO(n))
					.collect(Collectors.toList());
			// case "liked":
			// return travelStoryRepository.findAllByLike().stream().map(n ->
			// new TravelStoryDTO(n)).collect(Collectors.toList());
		case "all-t":
			return travelStoryRepository.findAllLimit().stream()
					.map(n -> new TravelStoryDTO(n))
					.collect(Collectors.toList());
			// case "viewed-t":
			// return travelStoryRepository.findAllByViewLimit().stream().map(n
			// -> new TravelStoryDTO(n)).collect(Collectors.toList());
		default:
			return travelStoryRepository.findAll().stream()
					.map(n -> new TravelStoryDTO(n))
					.collect(Collectors.toList());
		}
		// return travelStoryRepository.findAll().stream().map(n -> new
		// TravelStoryDTO(n)).collect(Collectors.toList());
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
		return hashTagRepository.findAll().stream()
				.map(n -> new HashTagsDTO(n)).collect(Collectors.toList());
	}

	public List<TravelStoryDTO> getStoryByTag(Long tagId) {
		HashTag hashTag = hashTagRepository.findById(tagId).get();
		if (hashTag != null) {
			return hashTag.getTravelStory().stream()
					.map(n -> new TravelStoryDTO(n))
					.collect(Collectors.toList());
		} else {
			logger.info("Invalid HashTag. id = {}", tagId);
			throw new UnprocessableEntityException("Invalid HashTag.");
		}
	}

	public List<TravelStoryDTO> getAllStoryByLoggedInUser() {
		logger.info("Inside travelStoryService::getAllStoryByLoggedInUser");
		User user = userService.getLoggedInUser();
		if (!user.getStory().isEmpty()) {
			return user.getStory().stream().map(n -> new TravelStoryDTO(n, ""))
					.collect(Collectors.toList());
		}
		logger.info("Completed travelStoryService::getAllStoryByLoggedInUser");
		return null;
	}

}
