package com.adapterj.registry;

/**
 * 
 * @author York/GuangYu DENG
 */
public interface Registry {

	/**
	 * 
	 * @param identity
	 * @return
	 */
	String getPOJOClassName(String identity);
	
	/**
	 * 
	 * @param templateFile
	 * @return
	 */
	String getAcceleratorClassName(String templateFile);
	
}
