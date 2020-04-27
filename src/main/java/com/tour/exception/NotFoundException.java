package com.tour.exception;

/**
 * Request not found exception handler class.
 * 
 * @author Pardeep
 * @version 1.0
 */
public class NotFoundException extends RuntimeException {

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
	private static final long serialVersionUID = 1L;

	public NotFoundException() {
		super();
	}

	public NotFoundException(String message) {
		super(message);
	}
}
