package com.adapterj.widget;

/**
 * 
 * @author York/GuangYu DENG
 */
public class IllegalOptionsException extends RuntimeException {

	private static final long serialVersionUID = -8287104049775006544L;

	/**
	 * 
	 * @param error
	 */
	public IllegalOptionsException(String error) {
		super(error);
	}
	
	/**
	 * 
	 * @param error
	 * @param thrown
	 */
	public IllegalOptionsException(String error, NullPointerException thrown) {
		super(error, thrown);
	}
	
	/**
	 * 
	 * @param thrown
	 */
	public IllegalOptionsException(NullPointerException thrown) {
		super(thrown);
	}
	
	/**
	 * 
	 * @param error
	 * @param thrown
	 */
	public IllegalOptionsException(String error, IllegalArgumentException thrown) {
		super(error, thrown);
	}

	/**
	 * 
	 * @param thrown
	 */
	public IllegalOptionsException(IllegalArgumentException thrown) {
		super(thrown);
	}
}
