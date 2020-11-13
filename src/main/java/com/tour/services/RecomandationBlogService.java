/**
 * 
 */
package com.tour.services;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.tour.entity.RecomandationBlog;
import com.tour.entity.User;
import com.tour.entity.dto.RecomandationBlogDTO;
import com.tour.exception.UnprocessableEntityException;
import com.tour.repository.RecomandationBlogRepository;

/**
 * @author RamaNand
 *
 */
@Service
public class RecomandationBlogService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private RecomandationBlogRepository recomandationBlogRepository;
	@Autowired
	private UserService userService;
	@Autowired
	private FileService fileService;

	public RecomandationBlogDTO save(RecomandationBlogDTO recomandationBlogDTO) {
		logger.info("Inside RecomandationBlogService::save");
		validate(recomandationBlogDTO);
		User user = userService.getLoggedInUser();
		RecomandationBlog recomandationBlog = null;
		if (recomandationBlogDTO.getId() == null) {
			recomandationBlog = new RecomandationBlog(recomandationBlogDTO);
			recomandationBlog.setView(0l);
			recomandationBlog.setCreateDate(LocalDate.now(ZoneOffset.UTC));
		} else {
			recomandationBlog = findById(recomandationBlogDTO.getId());
			validateUser(recomandationBlog.getUser());
			recomandationBlog.setModifiedDate(LocalDate.now(ZoneOffset.UTC));
			recomandationBlog.setTitle(recomandationBlogDTO.getTitle());
			recomandationBlog.setDescription(recomandationBlogDTO.getDescription());
		}
		recomandationBlog.setUser(user);
		recomandationBlog.setImage(fileService.getFileById(recomandationBlogDTO.getImageId()));

		RecomandationBlogDTO blog = new RecomandationBlogDTO(recomandationBlogRepository.save(recomandationBlog));
		logger.info("Completed RecomandationBlogService::save");
		return blog;
	}

	private void validate(RecomandationBlogDTO recomandationBlogDTO) {
		logger.info("Inside RecomandationBlogService::validate");
		if (StringUtils.isBlank(recomandationBlogDTO.getTitle())) {
			logger.info("Please enter title.");
			throw new UnprocessableEntityException("Please enter title.");
		}
		if (StringUtils.isBlank(recomandationBlogDTO.getDescription())) {
			logger.info("Please enter discription of your Blog.");
			throw new UnprocessableEntityException("Please enter discription of your Blog.");
		}
		if (recomandationBlogDTO.getTitle().length() > 100) {
			logger.info("Title length exceeded.");
			throw new UnprocessableEntityException("Title length exceeded.");
		}
		if (recomandationBlogDTO.getImageId() == null) {
			logger.info("Please provide Image for your blog.");
			throw new UnprocessableEntityException("Please provide Image for your blog.");
		}
		logger.info("Completed RecomandationBlogService::validate");

	}

	public RecomandationBlog findById(Long id) {
		logger.info("Inside RecomandationBlogService::findById");
		Optional<RecomandationBlog> recomandationBlog = recomandationBlogRepository.findById(id);
		if (recomandationBlog == null || !recomandationBlog.isPresent()) {
			throw new UnprocessableEntityException("Invalid RecomandationBlog.");
		}
		logger.info("Completed RecomandationBlogService::findById");
		return recomandationBlog.get();
	}

	private void validateUser(User user) {
		if (user.getId() != userService.getLoggedInUser().getId()) {
			throw new UnprocessableEntityException("You are not allowed to edit this Recomandation Blog.");
		}
	}

	public RecomandationBlogDTO likeBlog(Long storyId) {
		logger.info("Inside RecomandationBlogService::likeBlog");
		User user = userService.getLoggedInUser();
		RecomandationBlog recomandationBlog = findById(storyId);
		recomandationBlog.getLikedBy().add(user);
		logger.info("Completed RecomandationBlogService::likeBlog");
		return new RecomandationBlogDTO(recomandationBlogRepository.save(recomandationBlog));
	}
	
	public List<RecomandationBlogDTO> findAll() {
		List<RecomandationBlogDTO> list = null;
		List<RecomandationBlog> recomandationBlog = recomandationBlogRepository.findAll();
		if(!CollectionUtils.isEmpty(recomandationBlog)) {
			list = recomandationBlog.stream().map(RecomandationBlogDTO::new).collect(Collectors.toList());
		}
		return list;
	}

	public RecomandationBlogDTO findDTOById(Long id) {
		logger.info("Inside RecomandationBlogService::findById");
		Optional<RecomandationBlog> recomandationBlog = recomandationBlogRepository.findById(id);
		if (recomandationBlog == null || !recomandationBlog.isPresent()) {
			throw new UnprocessableEntityException("Invalid RecomandationBlog.");
		}
		logger.info("Completed RecomandationBlogService::findById");
		return new RecomandationBlogDTO(recomandationBlog.get());
	}

}
