package com.adapterj.widget;

import java.io.Serializable;

import com.adapterj.annotation.ID;
import com.adapterj.annotation.GetMethod;
import com.adapterj.annotation.SetMethod;

/**
 * 
 * @author York/GuangYu DENG
 */
@ID(identity = "text")
public class Text implements Serializable {
	
	private static final long serialVersionUID = 3273138306522898784L;

	/**
	 * 
	 * @param text
	 */
	public Text(final String text) {
		this.text = text;
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
		s.append("\"text\":\"").append(text).append("\"");
		s.append('}');
		return s.toString();
	}
}
