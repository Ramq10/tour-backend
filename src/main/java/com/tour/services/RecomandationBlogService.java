/**
 * 
 */
package com.tour.services;

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
import org.springframework.util.CollectionUtils;

import com.tour.entity.File;
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
			recomandationBlog.setPrimaryDescription(recomandationBlogDTO.getPrimaryDescription());
			recomandationBlog.setSecondaryDescription(recomandationBlogDTO.getSecondaryDescription());
		}
		recomandationBlog.setUser(user);
		recomandationBlog.setBanner(fileService.getFileById(recomandationBlogDTO.getBannerId()));
		recomandationBlog.setFiles(validateStoryFile(recomandationBlogDTO.getFiles()));

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
		if (StringUtils.isBlank(recomandationBlogDTO.getPrimaryDescription())) {
			logger.info("Please enter discription of your Blog.");
			throw new UnprocessableEntityException("Please enter discription of your Blog.");
		}
		if (StringUtils.isBlank(recomandationBlogDTO.getSecondaryDescription())) {
			logger.info("Please enter discription of your Blog.");
			throw new UnprocessableEntityException("Please enter discription of your Blog.");
		}
		if (recomandationBlogDTO.getTitle().length() > 100) {
			logger.info("Title length exceeded.");
			throw new UnprocessableEntityException("Title length exceeded.");
		}
		if (recomandationBlogDTO.getBannerId() == null) {
			logger.info("Please provide Banner-Image for your blog.");
			throw new UnprocessableEntityException("Please provide Banner-Image for your blog.");
		}
		if (CollectionUtils.isEmpty(recomandationBlogDTO.getFiles())) {
			logger.info("Please provide Image for your blog.");
			throw new UnprocessableEntityException("Please provide Image for your blog.");
		}
		logger.info("Completed RecomandationBlogService::validate");

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
		if (user.getId().equals(userService.getLoggedInUser().getId())) {
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

	public void deleteBlog(Long id) {
		// TODO Auto-generated method stub
//		this.findDTOById(id);
		recomandationBlogRepository.deleteById(id);
//		return null;
	}

}
