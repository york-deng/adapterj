package com.adapterj.widget;

import java.io.Serializable;

/**
 * SelectOptions 的问题在于，有 option 的 value 属性，以及 option 的 text 两个部分需要做填充 ？！
 *  
 * @author York/GuangYu DENG
 * @param <T>
 */
public interface SelectOptions extends Serializable {

	boolean isEmpty();

	String selected(String value, boolean optionsHTML);
	
	String selected(String value);
	
	String selected();
	
	String getPlaceholderForEmpty();
	
	String toJSONString();
	
	String toHTMLString();
	
	String toJavaScript();
	
}
