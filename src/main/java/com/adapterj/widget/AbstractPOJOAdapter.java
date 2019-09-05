package com.adapterj.widget;

import com.adapterj.annotation.POJO;

/**
 * 
 * @author York/GuangYu DENG
 */
@POJO(classId = "_pojo")
public abstract class AbstractPOJOAdapter<T> implements POJOAdapter<T> {

	private static final long serialVersionUID = 661645352519888937L;

	protected T _data = null;

	protected LinkGroup _links = null;

	protected TextGroup _texts = null;
	
	protected String _placeholderForNull = ("");
	
	protected String _placeholderForEmpty = ("");
	
	/**
	 * 
	 * @param pojo
	 */
	public void setData(final T pojo) {
		_data = pojo;
	}

	/**
	 * 
	 * @param links
	 */
	public void setLinkGroup(final LinkGroup links) {
		_links = links;
	}

	/**
	 * 
	 * @param texts
	 */
	public void setTextGroup(final TextGroup texts) {
		_texts = texts;
	}

	/**
	 * 
	 * @param placeholder
	 */
	public void setPlaceholderForNull(final String placeholder) {
		_placeholderForNull = placeholder;
	}

	/**
	 * 
	 * @param placeholder
	 */
	public void setPlaceholderForEmpty(final String placeholder) {
		_placeholderForEmpty = placeholder;
	}
	
	// POJOAdapter methods
	
	@Override
	public T getData() {
		return _data;
	}
	
	@Override
	public LinkGroup getLinkGroup() {
		return _links;
	}
	
	@Override
	public TextGroup getTextGroup() {
		return _texts;
	}
	
	@Override
	public String getPlaceholderForNull() {
		return _placeholderForNull;
	}
	
	@Override
	public String getPlaceholderForEmpty() {
		return _placeholderForEmpty;
	}
}
