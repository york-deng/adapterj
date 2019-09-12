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
public class AnchorGroup implements Serializable {

	private static final long serialVersionUID = -1636007518939701867L;
	
	protected final Anchor[] _anchors;

	/**
	 * Constructs a HTML anchor group with the given length.
	 * 
	 * @param length the given length.
	 */
	public AnchorGroup(final int length) {
		_anchors = new Anchor[length];
	}

	/**
	 * Returns the length of this anchor group.
	 * 
	 * @return the length of this anchor group.
	 */
	public int length() {
		return (_anchors == null ? 0 : _anchors.length);
	}
	
	/**
	 * Puts an anchor into this anchor group.
	 * 
	 * @param index the given index.
	 * @param anchor the anchor instance.
	 */
	public void anchor(final int index, final Anchor anchor) {
		if (index >= _anchors.length) {
			final String error = String.format("ArrayIndexOutOfBoundsException: index = %d, length = %d", index, _anchors.length);
			throw new ArrayIndexOutOfBoundsException(error);
		}
		_anchors[index] = anchor;
	}

	/**
	 * Retuens the anchor by the given index.
	 * 
	 * @param index the given index.
	 * @return the anchor by the given index.
	 */
	public Anchor anchor(final int index) {
		if (index >= _anchors.length) {
			final String error = String.format("ArrayIndexOutOfBoundsException: index = %d, length = %d", index, _anchors.length);
			throw new ArrayIndexOutOfBoundsException(error);
		}
		return _anchors[index];
	}
	
	public String toJSONString() {
		if (this._anchors == null || this._anchors.length == 0) {
			return "[]";
		} else {
			final StringBuffer s = new StringBuffer();
			s.append('[');
			for (int i = 0; i < this._anchors.length; i ++) {
				if (i > 0) s.append(',').append(' ');
				if (this._anchors[i] != null) s.append(this._anchors[i].toJSONString());
			}
			s.append(']');
			return s.toString();
		}
	}
}
