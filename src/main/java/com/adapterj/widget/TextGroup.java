package com.adapterj.widget;

import java.io.Serializable;

/**
 * 
 * @author York/GuangYu DENG
 */
public class TextGroup implements Serializable {

	private static final long serialVersionUID = -644312032177712572L;
	
	protected final Text[] _texts;

	/**
	 * 
	 * @param length
	 */
	public TextGroup(final int length) {
		_texts = new Text[length];
	}

	/**
	 * 
	 * @return
	 */
	public int length() {
		return (_texts == null ? 0 : _texts.length);
	}
	
	/**
	 * 
	 * @param index
	 * @param link
	 */
	public void text(final int index, final Text link) {
		if (index >= _texts.length) {
			final String error = String.format("ArrayIndexOutOfBoundsException: index = %d, length = %d", index, _texts.length);
			throw new ArrayIndexOutOfBoundsException(error);
		}
		_texts[index] = link;
	}

	/**
	 * 
	 * @param index
	 * @return
	 */
	public Text text(final int index) {
		if (index >= _texts.length) {
			final String error = String.format("ArrayIndexOutOfBoundsException: index = %d, length = %d", index, _texts.length);
			throw new ArrayIndexOutOfBoundsException(error);
		}
		return _texts[index];
	}
	
	/**
	 * 
	 * @return
	 */
	public String toJSONString() {
		if (this._texts == null || this._texts.length == 0) {
			return "[]";
		} else {
			final StringBuffer s = new StringBuffer();
			s.append('[');
			for (int i = 0; i < this._texts.length; i ++) {
				if (i > 0) s.append(',').append(' ');
				if (this._texts[i] != null) s.append(this._texts[i].toJSONString());
			}
			s.append(']');
			return s.toString();
		}
	}
}
