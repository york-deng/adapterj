package com.adapterj.widget;

public class NotStandardizedHTMLException extends RuntimeException {

	private static final long serialVersionUID = 1064778620714645372L;

	/**
	 * 
	 * @param error
	 */
	public NotStandardizedHTMLException(String error) {
		super(error);
	}
	
	/**
	 * 
	 * @param error
	 * @param thrown
	 */
	public NotStandardizedHTMLException(String error, NullPointerException thrown) {
		super(error, thrown);
	}
	
	/**
	 * 
	 * @param thrown
	 */
	public NotStandardizedHTMLException(NullPointerException thrown) {
		super(thrown);
	}
	
	/**
	 * 
	 * @param error
	 * @param thrown
	 */
	public NotStandardizedHTMLException(String error, IllegalArgumentException thrown) {
		super(error, thrown);
	}

	/**
	 * 
	 * @param thrown
	 */
	public NotStandardizedHTMLException(IllegalArgumentException thrown) {
		super(thrown);
	}
}
