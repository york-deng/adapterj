/*
 * Copyright (c) 2019 York/GuangYu Deng (york.deng@qq.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
	 * Constructs a HTML text group with the given length
	 * 
	 * @param length the given length
	 */
	public TextGroup(final int length) {
		_texts = new Text[length];
	}

	/**
	 * Returns the length of this text group.
	 * 
	 * @return the length of this text group.
	 */
	public int length() {
		return (_texts == null ? 0 : _texts.length);
	}
	
	/**
	 * Puts a text instance into this text group.
	 * 
	 * @param index the given index.
	 * @param text the text instance.
	 */
	public void text(final int index, final Text text) {
		if (index >= _texts.length) {
			final String error = String.format("ArrayIndexOutOfBoundsException: index = %d, length = %d", index, _texts.length);
			throw new ArrayIndexOutOfBoundsException(error);
		}
		_texts[index] = text;
	}

	/**
	 * Retuens the text instance by the given index.
	 * 
	 * @param index the given index.
	 * @return the text instance by the given index.
	 */
	public Text text(final int index) {
		if (index >= _texts.length) {
			final String error = String.format("ArrayIndexOutOfBoundsException: index = %d, length = %d", index, _texts.length);
			throw new ArrayIndexOutOfBoundsException(error);
		}
		return _texts[index];
	}

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
