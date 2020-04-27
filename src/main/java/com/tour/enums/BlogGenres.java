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
public enum BlogGenres {

	FOOD("Food"), LIFESTYLE("Lifestyle"), TRAVEL("Travel"), FASHION("Fashion"), DIY("D.I.Y.(Do It Yourself)"),
	ADVENTURE("Adventure"), PHOTOGRAPHY("Photography"), FREESTYLE("Freestyle"), OTHERS("others");

	private String genre;

	/**
	 * @param genre
	 */
	private BlogGenres(String genre) {
		this.genre = genre;
	}

	/**
	 * @return the type
	 */
	public String getGenre() {
		return genre;
	}

	public static List<String> genreList() {
		return Stream.of(BlogGenres.values()).map(BlogGenres::getGenre).collect(Collectors.toList());

	}

	public static BlogGenres getEnum(String value) {
		for (BlogGenres genreValue : values()) {
			if (genreValue.getGenre().equalsIgnoreCase(value)) {
				return genreValue;
			}
		}
		throw new UnprocessableEntityException("Invalid Blog Genre.");
	}

}
