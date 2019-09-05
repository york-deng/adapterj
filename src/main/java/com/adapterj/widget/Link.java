package com.adapterj.widget;

import java.io.Serializable;

import com.adapterj.annotation.ID;
import com.adapterj.annotation.GetMethod;
import com.adapterj.annotation.SetMethod;

/**
 * 
 * @author York/GuangYu DENG
 */
@ID(identity = "link")
public class Link implements Serializable {

	private static final long serialVersionUID = -5853177696868526137L;

	/**
	 * 
	 * @param url
	 */
	public Link(final String url) {
		this.url = url;
	}

	/**
	 * 
	 * @param url
	 * @param text
	 */
	public Link(final String url, final String text) {
		this.url  = url;
		this.text = text;
	}
	
	private String url;
	
	@GetMethod(methodName = "getURL", returnType = "String", returnId = "url")
	public String getURL() {
		return url;
	}

	@SetMethod(methodName = "setURL", parameterType = "String", parameterId = "url")
	public void setURL(String url) {
		this.url = url;
	}

	private String text;
	
	@GetMethod(methodName = "getText", returnType = "String", returnId = "text")
	public String getText() {
		return text;
	}
	@SetMethod(methodName = "setText", parameterType = "String", parameterId = "text")
	public void setText(String text) {
		this.text = text;
	}
	
	/**
	 * 
	 * @return
	 */
	public String toJSONString() {
		final StringBuffer s = new StringBuffer();
		s.append('{');
		s.append("\"url\":\"").append(url).append("\", ");
		s.append("\"text\":\"").append(text).append("\"");
		s.append('}');
		return s.toString();
	}
}
