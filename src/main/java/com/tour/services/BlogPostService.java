/**
 * 
 */
package com.tour.services;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
//import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

//import com.google.api.client.repackaged.com.google.common.base.Predicate;
import com.tour.entity.BlogPost;
import com.tour.entity.City;
import com.tour.entity.Country;
import com.tour.entity.File;
import com.tour.entity.State;
import com.tour.entity.User;
import com.tour.entity.dto.AllBlogDataDTO;
import com.tour.entity.dto.BlogPostDTO;
import com.tour.entity.dto.SearchColumnDTO;
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
	private CountryService countryService;
	@Autowired
	private BlogPostRepository blogPostRepository;
	@Autowired
	private FileService fileService;
	@Autowired
	@Lazy
	private UserService authenticationService;

	/**
	 * @param blogPostDTO
	 */
	public void saveOrUpdate(BlogPostDTO blogPostDTO) {
		logger.info("Inside BlogPostService::saveOrUpdate");
		validate(blogPostDTO);
		BlogPost blogPostFromDB = null;
		File blogImage = new File();

		if (blogPostDTO.getBlogImageId() != null) {
			blogImage = validateBlogImage(blogPostDTO.getBlogImageId());
		}
		if (blogPostDTO.getId() == null) {
			vaidateUrl(blogPostDTO.getUrl());
			blogPostFromDB = new BlogPost(blogPostDTO);
			blogPostFromDB.setCreateDate(LocalDate.now(ZoneOffset.UTC));
			blogPostFromDB.setView(0L);
		} else {
			blogPostFromDB = getBlogById(blogPostDTO.getId());
			try {
				blogPostFromDB.setTitle(new String(blogPostDTO.getTitle()
						.getBytes("UTF-8"), "ISO-8859-1"));
				blogPostFromDB.setSummery(new String(blogPostDTO.getSummery()
						.getBytes("UTF-8"), "ISO-8859-1"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			blogPostFromDB.setBlogGenre(BlogGenres.getEnum(blogPostDTO
					.getBlogGenre()));
			blogPostFromDB.setvBlog(blogPostDTO.isvBlog());
			validateUser(blogPostFromDB.getBlogger());
			blogPostFromDB.setModifiedDate(LocalDate.now(ZoneOffset.UTC));
		}
		blogPostFromDB = validateAndGetLocationData(blogPostFromDB, blogPostDTO);
		blogPostFromDB.setUrl(blogPostDTO.getUrl());
		if (!blogPostFromDB.getUrl().contains("http")) {
			blogPostFromDB.setUrl("http://" + blogPostFromDB.getUrl());
		}
		User loggedInUser = authenticationService.getLoggedInUser();
		blogPostFromDB.setBlogger(loggedInUser);
		blogPostFromDB.setBlogImage(blogImage);

		logger.info("BlogPostService::saveOrUpdate, blog post going to save.");
		blogPostRepository.save(blogPostFromDB);
		logger.info("BlogPostService::saveOrUpdate, blog post saved successfully.");

		if (blogPostFromDB.getvBlog() == Boolean.TRUE && loggedInUser.getVlogger() != Boolean.TRUE) {
			loggedInUser.setVlogger(true);
		}
		if (blogPostFromDB.getvBlog() == Boolean.FALSE && loggedInUser.getBlogger() != Boolean.TRUE) {
			loggedInUser.setBlogger(true);
		}
		authenticationService.updateUser(loggedInUser);
		logger.info("Completed BlogPostService::saveOrUpdate");
	}

	private BlogPost validateAndGetLocationData(BlogPost blogPostFromDB, BlogPostDTO blogPostDTO) {
		validateLocationForNull(blogPostDTO);
		Country country = countryService.getCountryById(blogPostDTO.getCountryId());
		State state = countryService.getStateById(blogPostDTO.getStateId());
		if (blogPostDTO.getCityId() != null) {
			City city = countryService.getCityById(blogPostDTO.getCityId());
			blogPostFromDB.setCity(city);
		}
		blogPostFromDB.setCountry(country);
		blogPostFromDB.setState(state);
		return blogPostFromDB;
	}

	private void validateLocationForNull(BlogPostDTO blogPostDTO) {
		if (blogPostDTO.getCountryId() == null || blogPostDTO.getStateId() == null) {
			throw new UnprocessableEntityException("Please select valid Location.");
		}
		
	}

	// private List<HashTag> validateTags(List<HashTag> tags) {
	// List<HashTag> hashTags = new ArrayList<HashTag>();
	// tags.stream().forEach(f -> {
	// hashTags.add(hashTagRepository.findById(f.getId()).get());
	// });
	// return hashTags;
	// }

	private void validateUser(User blogger) {
		System.out.println(authenticationService.getLoggedInUser().getEmail());
		if (blogger.getId() != authenticationService.getLoggedInUser()
				.getId() && !authenticationService.getLoggedInUser().getEmail().equals("admin@roverstrail.com")) {
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
		logger.info("Completed BlogPostService::getBlogDTOById");
		return new BlogPostDTO(blogPost.get());
	}
	
	public BlogPostDTO likeBlogById(Long id) {
		logger.info("Inside BlogPostService::likeBlogById");
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
		logger.info("Completed BlogPostService::likeBlogById");
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

	@PersistenceContext
	private EntityManager em;

	public List<BlogPostDTO> getAllBlogByLoggedInUser() {
		logger.info("Inside BlogPostService::getAllBlogByLoggedInUser");
		User user = authenticationService.getLoggedInUser();
		if (!user.getBlogPost().isEmpty()) {
			return user.getBlogPost().stream().map(v -> new BlogPostDTO(v, ""))
					.collect(Collectors.toList());
		}
		logger.info("Completed BlogPostService::getAllBlogByLoggedInUser");
		return null;
	}

	private List<BlogPostDTO> searchByTitle(String searchBy, boolean vlog) {
		List<String> substring = new ArrayList<>();
		Set<BlogPostDTO> blogs = new HashSet<BlogPostDTO>();
		String[] s = searchBy.split(" ");
		Collections.addAll(substring, s);
		for (int i = 0; i < substring.size(); i++) {
			if (substring.get(i).length() > 2) {
				List<BlogPost> localResult = blogPostRepository
						.findByvBlogAndTitleContainingIgnoreCase(vlog,
								substring.get(i));
				blogs.addAll(localResult.stream().map(BlogPostDTO::new)
						.collect(Collectors.toSet()));
			}
		}
		return blogs.stream().collect(Collectors.toList());
	}

	public AllBlogDataDTO filterBlogPost(String searchBy,
			SearchColumnDTO searchColumnDTO, boolean vlog) {
		if (!StringUtils.isBlank(searchBy)) {
			return new AllBlogDataDTO(searchByTitle(searchBy, vlog), 0);
		}
		if (searchColumnDTO != null) {
			int pageNo = searchColumnDTO.getPageNo();
			Pageable page = PageRequest.of(pageNo, 12);
			Page<BlogPost> blogPages = blogPostRepository
					.findAll(filterAllBlogPost(searchColumnDTO), page);
			List<BlogPost> blogs = blogPages.getContent();
			return new AllBlogDataDTO(blogs.stream().map(BlogPostDTO::new)
					.collect(Collectors.toList()), blogPages.getTotalElements());
		}
		return null;
	}

	public Specification<BlogPost> filterAllBlogPost(
			SearchColumnDTO searchColumnDTO) {

		logger.info("Starting Process For Creating Specification.");
		return new Specification<BlogPost>() {
			@Override
			public Predicate toPredicate(Root<BlogPost> root,
					CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				query.orderBy(criteriaBuilder.asc(root.get("id")));
				List<Predicate> predicates = new ArrayList<>();

				if (searchColumnDTO.getVlog() != null) {
					if (searchColumnDTO.getVlog()) {
						predicates.add(criteriaBuilder.equal(root.get("vBlog"),
								searchColumnDTO.getVlog()));
					} else if (!searchColumnDTO.getVlog()) {
						predicates.add(criteriaBuilder.equal(root.get("vBlog"),
								searchColumnDTO.getVlog()));
					}
				}

				if (!StringUtils.isBlank(searchColumnDTO.getTitle())) {
					predicates.add(criteriaBuilder.like(
							criteriaBuilder.lower(root.get("title")), "%"
									+ searchColumnDTO.getTitle().toLowerCase()
									+ "%"));
				}

				if (searchColumnDTO.getGenre() != null
						&& !searchColumnDTO.getGenre().isEmpty()) {
					Set<BlogGenres> genres = new HashSet<>();
					for (String s : searchColumnDTO.getGenre()) {
						BlogGenres blogGenre = BlogGenres.getEnum(s);
						genres.add(blogGenre);
					}
					predicates.add(root.get("blogGenre").in(genres));
				}

				if (searchColumnDTO.getAscView() != null) {
					if (searchColumnDTO.getAscView()) {
						query.orderBy(criteriaBuilder.asc(root.get("view")));
					} else if (!searchColumnDTO.getAscView()) {
						query.orderBy(criteriaBuilder.desc(root.get("view")));
					}

				}

				if (searchColumnDTO.getAscDate() != null) {
					if (searchColumnDTO.getAscDate()) {
						query.orderBy(criteriaBuilder.asc(root
								.get("createDate")));
					} else if (!searchColumnDTO.getAscDate()) {
						query.orderBy(criteriaBuilder.desc(root
								.get("createDate")));
					}
				}

				if (!StringUtils.isBlank(searchColumnDTO.getSummery())) {
					predicates.add(criteriaBuilder.like(
							criteriaBuilder.lower(root.get("summery")), "%"
									+ searchColumnDTO.getSummery()
											.toLowerCase() + "%"));
				}

				if (!StringUtils.isBlank(searchColumnDTO.getUrl())) {
					predicates.add(criteriaBuilder.like(
							criteriaBuilder.lower(root.get("url")), "%"
									+ searchColumnDTO.getUrl().toLowerCase()
									+ "%"));
				}

				if (searchColumnDTO.getBlogger() != null
						&& !searchColumnDTO.getBlogger().isEmpty()) {
					predicates.add(root.get("blogger").get("name")
							.in(searchColumnDTO.getBlogger()));
				}

				if (!StringUtils.isBlank(searchColumnDTO.getToDate())
						&& !StringUtils.isBlank(searchColumnDTO.getFromDate())) {
					LocalDate startDate = LocalDate.parse(searchColumnDTO
							.getFromDate());
					LocalDate endDate = LocalDate.parse(searchColumnDTO
							.getToDate());
					predicates.add(criteriaBuilder.between(
							root.get("createDate"), startDate, endDate));
				}
				return criteriaBuilder.and(criteriaBuilder.and(predicates
						.toArray(new Predicate[predicates.size()])));
			}
		};
	}
}
