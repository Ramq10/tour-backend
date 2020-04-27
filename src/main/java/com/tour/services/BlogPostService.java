/**
 * 
 */
package com.tour.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.tour.entity.BlogPost;
import com.tour.entity.File;
import com.tour.entity.HashTag;
import com.tour.entity.User;
import com.tour.entity.dto.BlogPostDTO;
import com.tour.enums.BlogGenres;
import com.tour.exception.UnprocessableEntityException;
import com.tour.repository.BlogPostRepository;
import com.tour.repository.HashTagRepository;

/**
 * @author 91945
 *
 */
@Service
public class BlogPostService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private BlogPostRepository blogPostRepository;
	@Autowired
	private FileService fileService;
	@Autowired
	@Lazy
	private AuthenticationService authenticationService;
	@Autowired
	private HashTagRepository hashTagRepository;

	/**
	 * @param blogPostDTO
	 */
	public void saveOrUpdate(BlogPostDTO blogPostDTO) {
		logger.info("Inside BlogPostService::saveOrUpdate");
		validate(blogPostDTO);
		BlogPost blogPostFromDB = null;
		List<HashTag> tags = new ArrayList<HashTag>();
		File blogImage = new File();
		if (blogPostDTO.getBlogImageId() != null) {
			blogImage = validateBlogImage(blogPostDTO.getBlogImageId());
		}
		if (blogPostDTO.getId() == null) {
			vaidateUrl(blogPostDTO.getUrl());
			blogPostFromDB = new BlogPost(blogPostDTO);
			blogPostFromDB.setUrl(blogPostDTO.getUrl());
			blogPostFromDB.setCreateDate(LocalDate.now());
		} else {
			blogPostFromDB = getBlogById(blogPostDTO.getId());
			validateUser(blogPostFromDB.getBlogger());
			blogPostFromDB.setModifiedDate(LocalDate.now());
		}
		if (blogPostDTO.getTags() != null) {
			tags = validateTags(blogPostDTO.getTags());
		}
		blogPostFromDB.setTags(tags);
		blogPostFromDB.setBlogger(authenticationService.getAuthenticatedUser());
		blogPostFromDB.setBlogImage(blogImage);
		blogPostFromDB.setView(0L);
		logger.info("Completed BlogPostService::saveOrUpdate");
		blogPostRepository.save(blogPostFromDB);
	}

	private List<HashTag> validateTags(List<HashTag> tags) {
		List<HashTag> hashTags = new ArrayList<HashTag>();
		tags.stream().forEach(f -> {
			hashTags.add(hashTagRepository.findById(f.getId()).get());
		});
		return hashTags;
	}

	private void validateUser(User blogger) {
		if (blogger.getId() != authenticationService.getAuthenticatedUser().getId()) {
			throw new UnprocessableEntityException("You are not allowed to edit this blog.");
		}

	}

	private void vaidateUrl(String url) {
		logger.info("Inside BlogPostService::vaidateUrl");
		BlogPost blogPost = blogPostRepository.findByUrl(url);
		if (!Objects.isNull(blogPost)) {
			throw new UnprocessableEntityException("Invalid BlogPost URL.");
		}
		logger.info("Completed BlogPostService::vaidateUrl");
	}

	/**
	 * @param id
	 * @return
	 */
	public BlogPost getBlogById(Long id) {
		logger.info("Inside BlogPostService::getBlogById");
		Optional<BlogPost> blogPost = blogPostRepository.findById(id);
		if (blogPost == null || !blogPost.isPresent()) {
			throw new UnprocessableEntityException("Invalid BlogPost.");
		}
//		Long view = blogPost.get().getView() + 1;
//		blogPost.get().setView(view);
//		blogPostRepository.save(blogPost.get());
		logger.info("Completed BlogPostService::getBlogById");
		return blogPost.get();
	}
	
	public BlogPostDTO getBlogDTOById(Long id) {
		logger.info("Inside BlogPostService::getBlogDTOById");
		Optional<BlogPost> blogPost = blogPostRepository.findById(id);
		if (blogPost == null || !blogPost.isPresent()) {
			throw new UnprocessableEntityException("Invalid BlogPost.");
		}
		if(blogPost.get().getView() == null) {
			blogPost.get().setView(0l);
		}
		Long view = blogPost.get().getView() + 1;
		blogPost.get().setView(view);
		blogPostRepository.save(blogPost.get());
		logger.info("Completed BlogPostService::getBlogDTOById");
		return new BlogPostDTO(blogPost.get());
	}

	/**
	 * @param blogImageId
	 * @return
	 */
	private File validateBlogImage(Long blogImageId) {
		logger.info("Inside BlogPostService::validateBlogImage");
		File profilePhoto = new File();
		profilePhoto = fileService.getFileById(blogImageId);
		logger.info("Completed BlogPostService::validateBlogImage");
		return profilePhoto;
	}

	/**
	 * @param blogPostDTO
	 */
	private void validate(BlogPostDTO blogPostDTO) {
		logger.info("Inside BlogPostService::validate");
		if (StringUtils.isBlank(blogPostDTO.getTitle())) {
			throw new UnprocessableEntityException("Invalid Title.");
		}
		if (blogPostDTO.getBlogGenre() == null) {
			throw new UnprocessableEntityException("Please select Blog Genre.");
		}
		if (StringUtils.isBlank(blogPostDTO.getUrl())) {
			throw new UnprocessableEntityException("Invalid Blog Post.");
		}
		logger.info("Complete BlogPostService::validate");
	}

	/**
	 * @return
	 */
	public List<BlogPostDTO> getAllBlogPost(String sortedBy) {
		switch (sortedBy) {
		case"date":
			return blogPostRepository.findByvBlogOrderByCreateDateDesc(false).stream().map(m -> {
				return new BlogPostDTO(m);
			}).collect(Collectors.toList());
		case"view":
			return blogPostRepository.findByvBlogOrderByViewAsc(false).stream().map(m -> {
				return new BlogPostDTO(m);
			}).collect(Collectors.toList());
		default:
			return blogPostRepository.findByvBlogOrderByCreateDateDesc(false).stream().map(m -> {
				return new BlogPostDTO(m);
			}).collect(Collectors.toList());
		}
	}
	
	public List<BlogPostDTO> getAllVideoBlogPost(String sortedBy) {
		switch (sortedBy) {
		case"date":
			return blogPostRepository.findByvBlogOrderByCreateDateDesc(true).stream().map(m -> {
				return new BlogPostDTO(m);
			}).collect(Collectors.toList());
		case"view":
			return blogPostRepository.findByvBlogOrderByViewAsc(true).stream().map(m -> {
				return new BlogPostDTO(m);
			}).collect(Collectors.toList());
		default:
			return blogPostRepository.findByvBlogOrderByCreateDateDesc(true).stream().map(m -> {
				return new BlogPostDTO(m);
			}).collect(Collectors.toList());
		}
	}

	public List<String> getAllBlog() {
		return BlogGenres.genreList();
	}
	
	public List<BlogPostDTO> getBlogPostByTag(Long tagId) {
		HashTag hashTag = hashTagRepository.findById(tagId).get();
		if(hashTag != null) {
			return hashTag.getBlogPost().stream().map(m -> {
				return new BlogPostDTO(m);
			}).collect(Collectors.toList());
		} else {
			logger.info("Invalid HashTag. id = {}",tagId);
			throw new UnprocessableEntityException("Invalid HashTag.");
		}
	}
}
