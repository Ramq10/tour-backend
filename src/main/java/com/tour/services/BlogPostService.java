/**
 * 
 */
package com.tour.services;

import java.time.LocalDate;
import java.time.ZoneId;
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
import com.tour.entity.User;
import com.tour.entity.dto.BlogPostDTO;
import com.tour.enums.BlogGenres;
import com.tour.exception.UnprocessableEntityException;
import com.tour.repository.BlogPostRepository;

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

	/**
	 * @param blogPostDTO
	 */
	public void saveOrUpdate(BlogPostDTO blogPostDTO) {
		logger.info("Inside BlogPostService::saveOrUpdate");
		validate(blogPostDTO);
		ZoneId zoneid1 = ZoneId.of("Asia/Kolkata");
		BlogPost blogPostFromDB = null;
		// List<HashTag> tags = new ArrayList<HashTag>();
		File blogImage = new File();
		if (blogPostDTO.getBlogImageId() != null) {
			blogImage = validateBlogImage(blogPostDTO.getBlogImageId());
		}
		if (blogPostDTO.getId() == null) {
			vaidateUrl(blogPostDTO.getUrl());
			blogPostFromDB = new BlogPost(blogPostDTO);
			blogPostFromDB.setCreateDate(LocalDate.now(zoneid1));
			blogPostFromDB.setView(0L);
		} else {
			blogPostFromDB = getBlogById(blogPostDTO.getId());
			validateUser(blogPostFromDB.getBlogger());
			blogPostFromDB.setModifiedDate(LocalDate.now(zoneid1));
		}
		// if (blogPostDTO.getTags() != null) {
		// tags = validateTags(blogPostDTO.getTags());
		// }
		// blogPostFromDB.setTags(tags);
		blogPostFromDB.setUrl(blogPostDTO.getUrl());
		if (!blogPostFromDB.getUrl().contains("http")) {
			blogPostFromDB.setUrl("http://" + blogPostFromDB.getUrl());
		}
		blogPostFromDB.setBlogger(authenticationService.getAuthenticatedUser());
		blogPostFromDB.setBlogImage(blogImage);

		logger.info("Completed BlogPostService::saveOrUpdate");
		blogPostRepository.save(blogPostFromDB);
	}

	// private List<HashTag> validateTags(List<HashTag> tags) {
	// List<HashTag> hashTags = new ArrayList<HashTag>();
	// tags.stream().forEach(f -> {
	// hashTags.add(hashTagRepository.findById(f.getId()).get());
	// });
	// return hashTags;
	// }

	private void validateUser(User blogger) {
		if (blogger.getId() != authenticationService.getAuthenticatedUser()
				.getId()) {
			throw new UnprocessableEntityException(
					"You are not allowed to edit this blog.");
		}

	}

	private void vaidateUrl(String url) {
		logger.info("Inside BlogPostService::vaidateUrl");
		BlogPost blogPost = blogPostRepository.findByUrl(url);
		if (!Objects.isNull(blogPost)) {
			throw new UnprocessableEntityException("Blog URL already exist.");
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
		// Long view = blogPost.get().getView() + 1;
		// blogPost.get().setView(view);
		// blogPostRepository.save(blogPost.get());
		logger.info("Completed BlogPostService::getBlogById");
		return blogPost.get();
	}

	public BlogPostDTO getBlogDTOById(Long id) {
		logger.info("Inside BlogPostService::getBlogDTOById");
		Optional<BlogPost> blogPost = blogPostRepository.findById(id);
		if (blogPost == null || !blogPost.isPresent()) {
			throw new UnprocessableEntityException("Invalid BlogPost.");
		}
		if (blogPost.get().getView() == null) {
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
			logger.info("Invalid Title.");
			throw new UnprocessableEntityException("Invalid Title.");
		}
		if (blogPostDTO.getBlogGenre() == null) {
			logger.info("Please select Blog Genre.");
			throw new UnprocessableEntityException("Please select Blog Genre.");
		}
		if (StringUtils.isBlank(blogPostDTO.getUrl())) {
			logger.info("Invalid Blog URL.");
			throw new UnprocessableEntityException("Invalid Blog URL.");
		}
		if (blogPostDTO.getTitle().length() > 100) {
			logger.info("Title limit exceeded.");
			throw new UnprocessableEntityException("Title limit exceeded.");
		}
		if (blogPostDTO.getUrl().length() > 300) {
			logger.info("URL limit exceeded.");
			throw new UnprocessableEntityException("URL limit exceeded.");
		}
		logger.info("Complete BlogPostService::validate");
	}

	/**
	 * @return
	 */
	public List<BlogPostDTO> getAllBlogPost(String sortedBy) {
		switch (sortedBy) {
		case "date":
			return blogPostRepository.findByvBlogOrderByCreateDateDesc(false)
					.stream().map(m -> {
						return new BlogPostDTO(m);
					}).collect(Collectors.toList());
		case "view":
			return blogPostRepository.findByvBlogOrderByViewAsc(false).stream()
					.map(m -> {
						return new BlogPostDTO(m);
					}).collect(Collectors.toList());
		default:
			return blogPostRepository.findByvBlogOrderByCreateDateDesc(false)
					.stream().map(m -> {
						return new BlogPostDTO(m);
					}).collect(Collectors.toList());
		}
	}

	public List<BlogPostDTO> getAllVideoBlogPost(String sortedBy) {
		switch (sortedBy) {
		case "date":
			return blogPostRepository.findByvBlogOrderByCreateDateDesc(true)
					.stream().map(m -> {
						return new BlogPostDTO(m);
					}).collect(Collectors.toList());
		case "view":
			return blogPostRepository.findByvBlogOrderByViewAsc(true).stream()
					.map(m -> {
						return new BlogPostDTO(m);
					}).collect(Collectors.toList());
		default:
			return blogPostRepository.findByvBlogOrderByCreateDateDesc(true)
					.stream().map(m -> {
						return new BlogPostDTO(m);
					}).collect(Collectors.toList());
		}
	}

	public List<String> getAllBlog() {
		return BlogGenres.genreList();
	}

	public List<BlogPostDTO> findAllByBlogGenre(String value) {
		BlogGenres blogGenre = BlogGenres.getEnum(value);
		return blogPostRepository
				.findByvBlogAndBlogGenreOrderByCreateDateDesc(false, blogGenre)
				.stream().map(b -> new BlogPostDTO(b))
				.collect(Collectors.toList());
	}

	public List<BlogPostDTO> findVlogByBlogGenre(String value) {
		BlogGenres blogGenre = BlogGenres.getEnum(value);
		return blogPostRepository
				.findByvBlogAndBlogGenreOrderByCreateDateDesc(true, blogGenre)
				.stream().map(b -> new BlogPostDTO(b))
				.collect(Collectors.toList());

	}

	public List<BlogPostDTO> getAllBlogByLoggedInUser() {
		logger.info("Inside BlogPostService::getAllBlogByLoggedInUser");
		User user = authenticationService.getAuthenticatedUser();
		if (!user.getStory().isEmpty()) {
			return user.getBlogPost().stream().map(v->new BlogPostDTO(v,""))
					.collect(Collectors.toList());
		}
		logger.info("Completed BlogPostService::getAllBlogByLoggedInUser");
		return null;
	}

	// public List<BlogPostDTO> getBlogPostByTag(Long tagId) {
	// HashTag hashTag = hashTagRepository.findById(tagId).get();
	// if (hashTag != null) {
	// return hashTag.getBlogPost().stream().map(m -> {
	// return new BlogPostDTO(m);
	// }).collect(Collectors.toList());
	// } else {
	// logger.info("Invalid HashTag. id = {}", tagId);
	// throw new UnprocessableEntityException("Invalid HashTag.");
	// }
	// }
}
