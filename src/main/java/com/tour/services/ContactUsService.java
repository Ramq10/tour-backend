/**
 * 
 */
package com.tour.services;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tour.entity.ContactUs;
import com.tour.exception.UnprocessableEntityException;
import com.tour.repository.ContactUsRepository;
import com.tour.utils.CommonUtil;
/**
 * @author Ramanand
 *
 */
@Service
public class ContactUsService {

	@Autowired
	private ContactUsRepository contactUsRepository;
	
	/**
	 * @param contactUs
	 * @return
	 */
	public void save(ContactUs contactUs) {
		validateContactUs(contactUs);
		contactUsRepository.save(contactUs);
	}

	private void validateContactUs(ContactUs contactUs) {
		if(StringUtils.isBlank(contactUs.getEmail()) || !CommonUtil.isEmailValid(contactUs.getEmail()) || contactUs.getEmail().length() > 65){
			throw new UnprocessableEntityException("Please enter a valid Email.");
		}
		if(StringUtils.isBlank(contactUs.getName()) || !CommonUtil.isNameValid(contactUs.getName()) || contactUs.getName().length() > 30){
			throw new UnprocessableEntityException("Please enter a valid Name.");
		}
		if(StringUtils.isBlank(contactUs.getMessage())){
			throw new UnprocessableEntityException("Please enter a valid Message.");
		}
	}
}
