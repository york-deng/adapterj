package com.adapterj.exceptions;

public class NotImplementedException extends RuntimeException {

	private static final long serialVersionUID = 9108478124051126615L;

	/**
	 * 
	 * @param error
	 */
	public NotImplementedException(String error) {
		super(error);
	}
	
	/**
	 * 
	 * @param error
	 * @param thrown
	 */
	public NotImplementedException(String error, NullPointerException thrown) {
		super(error, thrown);
	}
	
	/**
	 * 
	 * @param thrown
	 */
	public NotImplementedException(NullPointerException thrown) {
		super(thrown);
	}
	
	/**
	 * 
	 * @param error
	 * @param thrown
	 */
	public NotImplementedException(String error, IllegalArgumentException thrown) {
		super(error, thrown);
	}

	/**
	 * 
	 * @param thrown
	 */
	public NotImplementedException(IllegalArgumentException thrown) {
		super(thrown);
	}
}
