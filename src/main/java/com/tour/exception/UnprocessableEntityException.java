package com.tour.exception;

/**
 * Unprocessable entity request exception handler class.
 * 
 * @author Pardeep
 * @version 1.0
 */
public class UnprocessableEntityException extends RuntimeException {

	/*
	 * Deserialization is the reverse process where the byte stream is used to
	 * recreate the actual Java object in memory.
	 * 
	 * This is used during the deserialization of an object, to ensure that a loaded
	 * class is compatible with the serialized object. If no matching class is
	 * found, an InvalidClassException is thrown.
	 * 
	 * If a serializable class does not explicitly declare a serialVersionUID, then
	 * the serialization runtime will calculate a default serialVersionUID value for
	 * that class based on various aspects of the class.
	 */
	private static final long serialVersionUID = -4648015318860275959L;

	public UnprocessableEntityException() {
		super();
	}

	public UnprocessableEntityException(String message) {
		super(message);
	}
}