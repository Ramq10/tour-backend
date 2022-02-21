/**
 * 
 */
package com.tour.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tour.entity.ContactUs;
import com.tour.entity.Country;
import com.tour.entity.File;
import com.tour.entity.SiteReview;
import com.tour.entity.SocialSiteLink;
import com.tour.entity.State;
import com.tour.entity.Subscriber;
import com.tour.entity.User;
import com.tour.entity.dto.BlogPostDTO;
import com.tour.entity.dto.SiteReviewDTO;
import com.tour.entity.dto.TravelStoryDTO;
import com.tour.entity.dto.UserDTO;
import com.tour.enums.Hobby;
import com.tour.exception.UnprocessableEntityException;
import com.tour.repository.BlogPostRepository;
import com.tour.repository.ContactUsRepository;
import com.tour.repository.CountryRepository;
import com.tour.repository.SocialSiteLinkRepository;
import com.tour.repository.StateRepository;
import com.tour.repository.SubscriberRepository;
import com.tour.repository.TravelStoryRepository;
import com.tour.repository.UserRepository;
import com.tour.utils.CommonUtil;
import com.tour.utils.EmailSender;

/**
 * @author Ramanand
 *
 */
@Service
public class UserService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CountryRepository countryRepository;
	@Autowired
	private StateRepository stateRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private AuthenticationService authenticationService;
	@Autowired
	private FileService fileService;
	@Autowired
	private SubscriberRepository subscriberRepository;
	@Autowired
	private EmailSender emailSender;
	@Autowired
	private SocialSiteLinkRepository socialSiteLinkRepository;
	@Autowired
	private TravelStoryRepository travelStoryRepository;
	@Autowired
	private ContactUsRepository contactUsRepository;
	@Autowired
	private BlogPostRepository blogPostRepository;

	/**
	 * @param user
	 * @return
	 */
	public UserDTO saveOrUpdate(UserDTO userDTO) {
		logger.info("Inside UserService::saveOrUpdate");
		validate(userDTO);
		User userFromDB = null;
		File profilePhoto = null;
		if (userDTO.getProfilePhotoId() != null) {
			profilePhoto = validateProfilePhoto(userDTO.getProfilePhotoId());
		}
		boolean firstUser = false;
		if (userDTO.getId() == null) {
			firstUser = true;
			validateEmail(userDTO.getEmail());
			userFromDB = new User(userDTO);
			userFromDB.setCreateDate(LocalDate.now(ZoneOffset.UTC));
			if (StringUtils.isBlank(userDTO.getPassword())) {
				throw new UnprocessableEntityException("Please enter valid Password.");
			}
			userFromDB.setPassword(passwordEncoder.encode(userDTO.getPassword()));
			userFromDB.setPasswordUpdateDate(LocalDateTime.now(ZoneOffset.UTC));
		} else {
			firstUser = false;
			userFromDB = getUserById(userDTO.getId());
			userFromDB.setModifiedDate(LocalDate.now(ZoneOffset.UTC));
			userFromDB.setName(userDTO.getName());
			userFromDB.setMobileNumber(userDTO.getMobileNumber());
			userFromDB.setHobby(Hobby.getEnum(userDTO.getHobby()));
		}
		if (StringUtils.isNotBlank(userDTO.getInstaLink()) || StringUtils.isNotBlank(userDTO.getFbLink())) {
			validateAndSaveSocialLink(userFromDB, userDTO);
		}
		userFromDB.setProfilePhoto(profilePhoto);
		setAddress(userFromDB, userDTO);
		logger.info("Completed UserService::saveOrUpdate");
		if (firstUser) {
			return saveUser(userFromDB);
		} else {
			return updateUser(userFromDB);
		}
	}

	private void validateAndSaveSocialLink(User userFromDB, UserDTO userDTO) {
		logger.info("Inside UserService::validateAndSaveSocialLink");
		SocialSiteLink socialSiteLink = null;
		if (userDTO.getSocialSiteLinkId() != null) {
			socialSiteLink = socialSiteLinkRepository.findById(userDTO.getSocialSiteLinkId()).get();
			socialSiteLink.setInstaLink(StringUtils.isNotBlank(userDTO.getInstaLink()) ? userDTO.getInstaLink()
					: socialSiteLink.getInstaLink());
			socialSiteLink.setFbLink(
					StringUtils.isNotBlank(userDTO.getFbLink()) ? userDTO.getFbLink() : socialSiteLink.getFbLink());
			socialSiteLink.setModifiedDate(LocalDate.now(ZoneOffset.UTC));
		} else {
			socialSiteLink = new SocialSiteLink(null, userDTO.getFbLink(), userDTO.getInstaLink());
			socialSiteLink.setCreateDate(LocalDate.now(ZoneOffset.UTC));
		}
		userFromDB.setSocialSiteLink(socialSiteLink);
		logger.info("Completed UserService::validateAndSaveSocialLink");
	}

	private UserDTO saveUser(User userFromDB) {
		logger.info("Inside UserService::saveUser");
		UserDTO user = new UserDTO(userRepository.save(userFromDB));
		Runnable mailThread = () -> {
			emailSender.sendWelcomeMail(user.getName(), user.getEmail(), "Welcome To Roverstrail",
					"User " + user.getEmail() + " has successfully registered with RoversTrail.");
		};
		new Thread(mailThread).start();
		logger.info("Completed UserService::saveUser");
		return user;
	}

	public UserDTO updateUser(User userFromDB) {
		logger.info("Inside UserService::updateUser");
		UserDTO user = new UserDTO(userRepository.save(userFromDB));
		logger.info("Completed UserService::updateUser");
		return user;
	}

	/**
	 * @param profilePhotoId
	 * @return
	 */
	private File validateProfilePhoto(Long profilePhotoId) {
		logger.info("Inside UserService::validateProfilePhoto");
		File profilePhoto = new File();
		profilePhoto = fileService.getFileById(profilePhotoId);
		logger.info("Completed UserService::validateProfilePhoto");
		return profilePhoto;
	}

	/**
	 * @param email
	 */
	private void validateEmail(String email) {
		logger.info("Inside UserService::validateEmail");
		User user = userRepository.findByEmail(email);
		if (!Objects.isNull(user)) {
			throw new UnprocessableEntityException("Email already exist.");
		}
		logger.info("Completed UserService::validateEmail");
	}

	/**
	 * @return
	 */
	public UserDTO getLoggedInUserDTO() {
		return new UserDTO(authenticationService.getAuthenticatedUser());
	}

	/**
	 * @return
	 */
	public User getLoggedInUser() {
		return authenticationService.getAuthenticatedUser();
	}

	/**
	 * @param userFromDB
	 * @param userDTO
	 */
	private void setAddress(User userFromDB, UserDTO userDTO) {
		logger.info("Inside UserService::setAddress");
		Country country = getCountryById(userDTO.getCountryId());
		if (Objects.isNull(country)) {
			throw new UnprocessableEntityException("Invalid Country.");
		}
		if (userDTO.getStateId() != null) {
			State state = getStateById(userDTO.getStateId());
			if (Objects.isNull(state)) {
				throw new UnprocessableEntityException("Invalid State.");
			}
			List<State> stateList = country.getState();
			if (!stateList.contains(state)) {
				throw new UnprocessableEntityException("State does not belong to this Country.");
			}
			userFromDB.setState(state);
		}
		userFromDB.setCountry(country);
		logger.info("Completed UserService::setAddress");
	}

	/**
	 * @param id
	 * @return
	 */
	public State getStateById(Long id) {
		Optional<State> state = stateRepository.findById(id);
		if (state == null || !state.isPresent()) {
			throw new UnprocessableEntityException("Invalid State.");
		}
		return state.get();
	}

	/**
	 * @param id
	 * @return
	 */
	public Country getCountryById(Long id) {
		Optional<Country> country = countryRepository.findById(id);
		if (country == null || !country.isPresent()) {
			throw new UnprocessableEntityException("Invalid Country.");
		}
		return country.get();
	}

	/**
	 * @param user
	 */
	private void validate(UserDTO user) {
		logger.info("Inside UserService::validate");
		if (StringUtils.isBlank(user.getName()) || !CommonUtil.isNameValid(user.getName())
				|| user.getName().length() > 30) {
			throw new UnprocessableEntityException("Please enter a valid Name.");
		}
		if (StringUtils.isBlank(user.getEmail()) || !CommonUtil.isEmailValid(user.getEmail())
				|| user.getEmail().length() > 65) {
			throw new UnprocessableEntityException("Please enter a valid email.");
		}
		if (StringUtils.isBlank(user.getHobby())) {
			throw new UnprocessableEntityException("Please enter valid Hobby.");
		}
		if (StringUtils.isBlank(user.getGender())) {
			throw new UnprocessableEntityException("Please enter valid Gender.");
		}
		if (Objects.isNull(user.getCountryId())) {
			throw new UnprocessableEntityException("Please enter valid Country.");
		}
		logger.info("Completed UserService::validate");
	}

	/**
	 * @param id
	 * @return
	 */
	public User getUserById(Long id) {
		Optional<User> user = userRepository.findById(id);
		if (user == null || !user.isPresent()) {
			throw new UnprocessableEntityException("Invalid User.");
		}
		return user.get();

	}

	/**
	 * @param id
	 * @return
	 */
	public UserDTO getUserDTOById(Long id) {
		return new UserDTO(getUserById(id));
	}

	/**
	 * @param email
	 * @return
	 */
	public User getUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	/**
	 * @param email
	 * @return
	 */
	public UserDTO getUserDTOByEmail(String email) {
		return new UserDTO(getUserByEmail(email));
	}

	public void subscribe(String email) {
		logger.info("Inside UserService::subscribe");
		Subscriber subscriber = new Subscriber();
		subscriber.setEmail(email);
		subscriberRepository.save(subscriber);
		logger.info("Competed UserService::subscribe");
	}

	public void forgetPasswordRequest(String userEmail) {
		logger.info("Inside UserService::forgetPasswordRequest");
		User user = getUserByEmail(userEmail);
		if (user == null) {
			logger.info("Email does not exist.");
			throw new UnprocessableEntityException("Email does not exist.");
		}
		emailSender.sendForgetPasswordMail(user.getEmail(), "Reset Password");
		logger.info("Competed UserService::forgetPasswordRequest");
	}

	public void resetPassword(String email, String password) {
		logger.info("Inside UserService::resetPassword");
		User user = getUserByEmail(email);
		if (user == null) {
			logger.info("Email does not exist.");
			throw new UnprocessableEntityException("Email does not exist.");
		}
		user.setPassword(passwordEncoder.encode(password));
		user.setPasswordUpdateDate(LocalDateTime.now(ZoneOffset.UTC));
		userRepository.save(user);
		logger.info("Competed UserService::resetPassword");
	}

	public Map<String, Long> getPortalData() {
		Long userCount = userRepository.count();
		Long storyCount = travelStoryRepository.count();
		Long subscriberCount = subscriberRepository.count();
		Long contactUsCount = contactUsRepository.count();
		Long blogCount = blogPostRepository.countByvBlog(false);
		Long vlogCount = blogPostRepository.countByvBlog(true);
		Map<String, Long> portalData = new HashMap<>();
		portalData.put("userCount", userCount);
		portalData.put("storyCount", storyCount);
		portalData.put("subscriberCount", subscriberCount);
		portalData.put("contactUsCount", contactUsCount);
		portalData.put("blogCount", blogCount);
		portalData.put("vlogCount", vlogCount);
		return portalData;

	}

	public List<UserDTO> getAllUsers(String limit, boolean blogger) {
		if (StringUtils.isBlank(limit)) {
			return userRepository.findAllByOrderByIdDesc().stream().map(UserDTO::new).collect(Collectors.toList());
		} else {
			if (blogger) {
				return userRepository.findAllByBlogger(true).stream().map(x -> new UserDTO(x, Boolean.FALSE))
						.sorted(Comparator.comparingInt(UserDTO::getBlogPostCount).reversed())
						.limit(Long.parseLong(limit)).collect(Collectors.toList());
			} else {
				return userRepository.findAllByBlogger(false).stream().map(x -> new UserDTO(x, Boolean.FALSE))
						.sorted(Comparator.comparingInt(UserDTO::getVlogPostCount).reversed())
						.limit(Long.parseLong(limit)).collect(Collectors.toList());
			}
		}
	}

	public List<UserDTO> getAllUser(String param) {
		switch (param) {
		case "blogger":
			return userRepository.findAllByBlogger(true).stream().map(x -> new UserDTO(x, ""))
					.collect(Collectors.toList());

		case "vlogger":
			return userRepository.findAllByVlogger(true).stream().map(x -> new UserDTO(x, ""))
					.collect(Collectors.toList());

		case "storywriter":
			return userRepository.findAllByStoryWriter(true).stream().map(x -> new UserDTO(x, ""))
					.collect(Collectors.toList());
		}
		return userRepository.findAll().stream().filter(s -> !s.getBlogPost().isEmpty()).map(x -> new UserDTO(x, ""))
				.collect(Collectors.toList());
	}

	public List<Subscriber> getAllSubscriber() {
		return subscriberRepository.findAll().stream().collect(Collectors.toList());
	}

	public List<ContactUs> getAllContactUsRequest() {
		return contactUsRepository.findAll().stream().collect(Collectors.toList());
	}

	public UserDTO postSiteReview(SiteReviewDTO siteReviewDTO) {
		User user = getLoggedInUser();
		if (Objects.isNull(user)) {
			throw new UnprocessableEntityException("Please Login First to Submit review.");
		}
		validateSiteReview(siteReviewDTO);
		SiteReview siteReview = new SiteReview(siteReviewDTO.getDescription());
		user.setSiteReview(siteReview);
		return new UserDTO(userRepository.save(user));
	}

	private void validateSiteReview(SiteReviewDTO siteReviewDTO) {
		if (siteReviewDTO.getDescription().length() > 500) {
			logger.info("Error in ValidateSiteReview: Maximum Limit reached for review.");
			throw new UnprocessableEntityException("Maximum Limit reached for review.");
		}
	}

	public List<BlogPostDTO> getAllBlogByUser(Long userId) {
		User user = getUserById(userId);
		return user.getBlogPost().stream().map(BlogPostDTO::new).collect(Collectors.toList());
	}

	public List<TravelStoryDTO> getAllStoryById(Long userId) {
		User user = getUserById(userId);
		return user.getStory().stream().map(TravelStoryDTO::new).collect(Collectors.toList());
	}

}
