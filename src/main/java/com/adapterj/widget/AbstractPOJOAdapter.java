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

import com.adapterj.annotation.POJO;

/**
 * 
 * @author York/GuangYu DENG
 */
@POJO(classId = "_pojo")
public abstract class AbstractPOJOAdapter<T> implements POJOAdapter<T> {

	private static final long serialVersionUID = 661645352519888937L;

	protected T _data = null;

	protected AnchorGroup _anchors = null;

	protected TextGroup _texts = null;
	
	protected String _placeholderForNull = ("");
	
	protected String _placeholderForEmpty = ("");
	
	/**
	 * Sets a POJO instance for this adapter
	 * 
	 * @param pojo the POJO instance
	 */
	public void setData(final T pojo) {
		_data = pojo;
	}

	/**
	 * Sets an anchor group for this adapter
	 * 
	 * @param anchors the anchor group
	 */
	public void setAnchorGroup(final AnchorGroup anchors) {
		_anchors = anchors;
	}

	/**
	 * Sets a text group for this adapter
	 * 
	 * @param texts the text group
	 */
	public void setTextGroup(final TextGroup texts) {
		_texts = texts;
	}

	/**
	 * Sets a placeholder for null value
	 * 
	 * @param placeholder the placeholder
	 */
	public void setPlaceholderForNull(final String placeholder) {
		_placeholderForNull = placeholder;
	}

	/**
	 * Sets a placeholder for empty value
	 * 
	 * @param placeholder the placeholder
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
	public AnchorGroup getAnchorGroup() {
		return _anchors;
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
