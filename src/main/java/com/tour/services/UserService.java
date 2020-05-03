/**
 * 
 */
package com.tour.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tour.entity.Country;
import com.tour.entity.File;
import com.tour.entity.State;
import com.tour.entity.Subscriber;
import com.tour.entity.User;
import com.tour.entity.dto.UserDTO;
import com.tour.exception.UnprocessableEntityException;
import com.tour.repository.CountryRepository;
import com.tour.repository.StateRepository;
import com.tour.repository.SubscriberRepository;
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

	/**
	 * @param user
	 * @return
	 */
	public UserDTO saveOrUpdate(UserDTO userDTO) {
		logger.info("Inside UserService::saveOrUpdate");
		validate(userDTO);
		User userFromDB = null;

		File profilePhoto = new File();
		if (userDTO.getProfilePhotoId() != null) {
			profilePhoto = validateProfilePhoto(userDTO.getProfilePhotoId());
		}
		if (userDTO.getId() == null) {
			validateEmail(userDTO.getEmail());
			userFromDB = new User(userDTO);
			userFromDB.setCreateDate(LocalDate.now());
			userFromDB.setPassword(passwordEncoder.encode(userDTO.getPassword()));
			userFromDB.setPasswordUpdateDate(LocalDateTime.now());
		} else {
			userFromDB = getUserById(userDTO.getId());
			userFromDB.setModifiedDate(LocalDate.now());
			userFromDB.setName(userDTO.getName());
			userFromDB.setMobileNumber(userDTO.getMobileNumber());
			userFromDB.setEmail(userDTO.getEmail());
		}
		userFromDB.setProfilePhoto(profilePhoto);
		setAddress(userFromDB, userDTO);
		logger.info("Completed UserService::saveOrUpdate");
//		return new UserDTO(userRepository.save(userFromDB));
		return saveUser(userFromDB);
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
		if (StringUtils.isBlank(user.getPassword())) {
			throw new UnprocessableEntityException("Please enter valid Password.");
		}
		logger.info("Completed UserService::validate");
	}

	/**
	 * @param id
	 * @return
	 */
	public User getUserById(Long id) {
		logger.info("Inside UserService::getUserById");
		Optional<User> user = userRepository.findById(id);
		if (user == null || !user.isPresent()) {
			throw new UnprocessableEntityException("Invalid User.");
		}
		logger.info("Completed UserService::getUserById");
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
		User user = getUserByEmail(userEmail);
		if(user == null) {
			logger.info("Email does not exist.");
			throw new UnprocessableEntityException("Email does not exist.");
		}
		emailSender.sendForgetPasswordMail(user.getEmail(), "Reset Password");
	}

}
