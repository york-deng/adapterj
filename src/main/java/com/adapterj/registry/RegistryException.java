package com.adapterj.registry;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;

/**
 * 
 * @author York/GuangYu DENG
 */
public class RegistryException extends RuntimeException {

	private static final long serialVersionUID = -8612908013723250568L;

	/**
	 * 
	 * @param error
	 */
	public RegistryException(String error) {
		super(error);
	}

	/**
	 * 
	 * @param error
	 * @param thrown
	 */
	public RegistryException(String error, UnsupportedEncodingException thrown) {
		super(error, thrown);
	}

	/**
	 * 
	 * @param thrown
	 */
	public RegistryException(UnsupportedEncodingException thrown) {
		super(thrown);
	}
	
	/**
	 * 
	 * @param error
	 * @param thrown
	 */
	public RegistryException(String error, NullPointerException thrown) {
		super(error, thrown);
	}
	
	/**
	 * 
	 * @param thrown
	 */
	public RegistryException(NullPointerException thrown) {
		super(thrown);
	}
	
	/**
	 * 
	 * @param error
	 * @param thrown
	 */
	public RegistryException(String error, IllegalArgumentException thrown) {
		super(error, thrown);
	}

	/**
	 * 
	 * @param thrown
	 */
	public RegistryException(IllegalArgumentException thrown) {
		super(thrown);
	}

	/**
	 * 
	 * @param error
	 * @param thrown
	 */
	public RegistryException(String error, IOException thrown) {
		super(thrown);
	}
	
	/**
	 * 
	 * @param thrown
	 */
	public RegistryException(IOException thrown) {
		super(thrown);
	}
	
	/**
	 * 
	 * @param error
	 * @param thrown
	 */
	public RegistryException(String error, FileNotFoundException thrown) {
		super(thrown);
	}
	
	/**
	 * 
	 * @param thrown
	 */
	public RegistryException(FileNotFoundException thrown) {
		super(thrown);
	}
	
	/**
	 * 
	 * @param error
	 * @param thrown
	 */
	public RegistryException(String error, ClassNotFoundException thrown) {
		super(thrown);
	}
	
	/**
	 * 
	 * @param thrown
	 */
	public RegistryException(ClassNotFoundException thrown) {
		super(thrown);
	}
	
	/**
	 * 
	 * @param error
	 * @param thrown
	 */
	public RegistryException(String error, InstantiationException thrown) {
		super(thrown);
	}
	
	/**
	 * 
	 * @param thrown
	 */
	public RegistryException(InstantiationException thrown) {
		super(thrown);
	}

	/**
	 * 
	 * @param error
	 * @param thrown
	 */
	public RegistryException(String error, IllegalAccessException thrown) {
		super(thrown);
	}
	
	/**
	 * 
	 * @param thrown
	 */
	public RegistryException(IllegalAccessException thrown) {
		super(thrown);
	}

	/**
	 * 
	 * @param error
	 * @param thrown
	 */
	public RegistryException(String error, InvocationTargetException thrown) {
		super(thrown);
	}
	
	/**
	 * 
	 * @param thrown
	 */
	public RegistryException(InvocationTargetException thrown) {
		super(thrown);
	}

	/**
	 * 
	 * @param error
	 * @param thrown
	 */
	public RegistryException(String error, Throwable thrown) {
		super(thrown);
	}
	
	/**
	 * 
	 * @param thrown
	 */
	public RegistryException(Throwable thrown) {
		super(thrown);
	}
}
