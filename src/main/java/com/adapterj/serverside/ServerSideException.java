package com.adapterj.serverside;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;

/**
 * 
 * @author York/GuangYu DENG
 */
public class ServerSideException extends RuntimeException {

	private static final long serialVersionUID = -8612908013723250568L;

	/**
	 * 
	 * @param error
	 */
	public ServerSideException(String error) {
		super(error);
	}

	/**
	 * 
	 * @param error
	 * @param thrown
	 */
	public ServerSideException(String error, UnsupportedEncodingException thrown) {
		super(error, thrown);
	}

	/**
	 * 
	 * @param thrown
	 */
	public ServerSideException(UnsupportedEncodingException thrown) {
		super(thrown);
	}
	
	/**
	 * 
	 * @param error
	 * @param thrown
	 */
	public ServerSideException(String error, NullPointerException thrown) {
		super(error, thrown);
	}
	
	/**
	 * 
	 * @param thrown
	 */
	public ServerSideException(NullPointerException thrown) {
		super(thrown);
	}
	
	/**
	 * 
	 * @param error
	 * @param thrown
	 */
	public ServerSideException(String error, IllegalArgumentException thrown) {
		super(error, thrown);
	}

	/**
	 * 
	 * @param thrown
	 */
	public ServerSideException(IllegalArgumentException thrown) {
		super(thrown);
	}

	/**
	 * 
	 * @param error
	 * @param thrown
	 */
	public ServerSideException(String error, ClassNotFoundException thrown) {
		super(thrown);
	}
	
	/**
	 * 
	 * @param thrown
	 */
	public ServerSideException(ClassNotFoundException thrown) {
		super(thrown);
	}
	
	/**
	 * 
	 * @param error
	 * @param thrown
	 */
	public ServerSideException(String error, InstantiationException thrown) {
		super(thrown);
	}
	
	/**
	 * 
	 * @param thrown
	 */
	public ServerSideException(InstantiationException thrown) {
		super(thrown);
	}

	/**
	 * 
	 * @param error
	 * @param thrown
	 */
	public ServerSideException(String error, IllegalAccessException thrown) {
		super(thrown);
	}
	
	/**
	 * 
	 * @param thrown
	 */
	public ServerSideException(IllegalAccessException thrown) {
		super(thrown);
	}

	/**
	 * 
	 * @param error
	 * @param thrown
	 */
	public ServerSideException(String error, InvocationTargetException thrown) {
		super(thrown);
	}
	
	/**
	 * 
	 * @param thrown
	 */
	public ServerSideException(InvocationTargetException thrown) {
		super(thrown);
	}

//	/**
//	 * 
//	 * @param error
//	 * @param thrown
//	 */
//	public ServerSideException(String error, Throwable thrown) {
//		super(thrown);
//	}
//	
//	/**
//	 * 
//	 * @param thrown
//	 */
//	public ServerSideException(Throwable thrown) {
//		super(thrown);
//	}
}
