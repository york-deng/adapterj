package com.adapterj.widget;

import java.io.Serializable;

/**
 * 
 * @author York/GuangYu DENG
 */
public interface Adapter extends Serializable {

	boolean isEmpty();
	
	String toJSONString();

	String toXMLString();

	String toJavaScript(String id, String function);
	
}
