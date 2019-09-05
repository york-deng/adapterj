package com.adapterj.widget;

import java.io.Serializable;

/**
 * 
 * @author York/GuangYu DENG
 */
public class LinkGroup implements Serializable {

	private static final long serialVersionUID = -1636007518939701867L;
	
	protected final Link[] _links;

	/**
	 * 
	 * @param length
	 */
	public LinkGroup(final int length) {
		_links = new Link[length];
	}

	/**
	 * 
	 * @return
	 */
	public int length() {
		return (_links == null ? 0 : _links.length);
	}
	
	/**
	 * 
	 * @param index
	 * @param link
	 */
	public void link(final int index, final Link link) {
		if (index >= _links.length) {
			final String error = String.format("ArrayIndexOutOfBoundsException: index = %d, length = %d", index, _links.length);
			throw new ArrayIndexOutOfBoundsException(error);
		}
		_links[index] = link;
	}

	/**
	 * 
	 * @param index
	 * @return
	 */
	public Link link(final int index) {
		if (index >= _links.length) {
			final String error = String.format("ArrayIndexOutOfBoundsException: index = %d, length = %d", index, _links.length);
			throw new ArrayIndexOutOfBoundsException(error);
		}
		return _links[index];
	}
	
	/**
	 * 
	 * @return
	 */
	public String toJSONString() {
		if (this._links == null || this._links.length == 0) {
			return "[]";
		} else {
			final StringBuffer s = new StringBuffer();
			s.append('[');
			for (int i = 0; i < this._links.length; i ++) {
				if (i > 0) s.append(',').append(' ');
				if (this._links[i] != null) s.append(this._links[i].toJSONString());
			}
			s.append(']');
			return s.toString();
		}
	}
}
