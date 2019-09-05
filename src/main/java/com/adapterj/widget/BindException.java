package com.adapterj.widget;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;

/**
 * 
 * @author York/GuangYu DENG
 */
public class BindException extends RuntimeException {

	private static final long serialVersionUID = -892680949218903822L;

	/**
	 * 
	 * @param error
	 */
	public BindException(String error) {
		super(error);
	}

	/**
	 * 
	 * @param error
	 * @param thrown
	 */
	public BindException(String error, MalformedURLException thrown) {
		super(error, thrown);
	}

	/**
	 * 
	 * @param thrown
	 */
	public BindException(MalformedURLException thrown) {
		super(thrown);
	}

	/**
	 * 
	 * @param error
	 * @param thrown
	 */
	public BindException(String error, IOException thrown) {
		super(error, thrown);
	}

	/**
	 * 
	 * @param thrown
	 */
	public BindException(IOException thrown) {
		super(thrown);
	}

	/**
	 * 
	 * @param error
	 * @param thrown
	 */
	public BindException(String error, UnsupportedEncodingException thrown) {
		super(error, thrown);
	}

	/**
	 * 
	 * @param thrown
	 */
	public BindException(UnsupportedEncodingException thrown) {
		super(thrown);
	}

	/**
	 * 
	 * @param error
	 * @param thrown
	 */
	public BindException(String error, NullPointerException thrown) {
		super(error, thrown);
	}
	
	/**
	 * 
	 * @param thrown
	 */
	public BindException(NullPointerException thrown) {
		super(thrown);
	}
	
	/**
	 * 
	 * @param error
	 * @param thrown
	 */
	public BindException(String error, IllegalArgumentException thrown) {
		super(error, thrown);
	}

	/**
	 * 
	 * @param thrown
	 */
	public BindException(IllegalArgumentException thrown) {
		super(thrown);
	}

	/**
	 * 
	 * @param error
	 * @param thrown
	 */
	public BindException(String error, ClassNotFoundException thrown) {
		super(thrown);
	}
	
	/**
	 * 
	 * @param thrown
	 */
	public BindException(ClassNotFoundException thrown) {
		super(thrown);
	}
	
	/**
	 * 
	 * @param error
	 * @param thrown
	 */
	public BindException(String error, IllegalAccessException thrown) {
		super(thrown);
	}
	
	/**
	 * 
	 * @param thrown
	 */
	public BindException(IllegalAccessException thrown) {
		super(thrown);
	}

	/**
	 * 
	 * @param error
	 * @param thrown
	 */
	public BindException(String error, InvocationTargetException thrown) {
		super(thrown);
	}
	
	/**
	 * 
	 * @param thrown
	 */
	public BindException(InvocationTargetException thrown) {
		super(thrown);
	}
}
