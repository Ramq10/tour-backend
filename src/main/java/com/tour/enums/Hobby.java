/**
 * 
 */
package com.tour.enums;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.tour.exception.UnprocessableEntityException;

/**
 * @author Ramanand
 *
 */
public enum Hobby {
	
	PHOTOGRAPHY("Photography"), READINGnWRINTING("Reading/Writing"), TRECKING("Trecking"), MEDITATIONnYOGA("Meditation/Yoga"), DANCING("Dancing"), FOODING("Fooding");
	
	private String type;

	/**
	 * @param type
	 */
	private Hobby(String hobby) {
		this.type = hobby;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	public static List<String> hobbyList() {
		return  Stream.of(Hobby.values()).map(Hobby::getType)
					.collect(Collectors.toList());

		}
	
	public static Hobby getEnum(String value) {
		for (Hobby hobbyValue : values()) {
			if (hobbyValue.getType().equalsIgnoreCase(value)) {
				return hobbyValue;
			}
		}
		throw new UnprocessableEntityException("Invalid Hobby.");
	}



}
