package com.tour.enums;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.tour.exception.UnprocessableEntityException;


/**
 * @author Ramanand
 *
 */
public enum Gender {

	MALE("male"), FEMALE("female"), TRANSGENDER("transgender");
	
	private String type;

	/**
	 * @param type
	 */
	private Gender(String gender) {
		this.type = gender;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	public static List<String> genderList() {
		return  Stream.of(Gender.values()).map(Gender::getType)
					.collect(Collectors.toList());

		}
	
	public static Gender getEnum(String value) {
		for (Gender genderValue : values()) {
			if (genderValue.getType().equalsIgnoreCase(value)) {
				return genderValue;
			}
		}
		throw new UnprocessableEntityException("Invalid Gender.");
	}


	
}
