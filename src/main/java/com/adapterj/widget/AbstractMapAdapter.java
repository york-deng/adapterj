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

import java.util.Set;
import java.util.HashMap;

import com.adapterj.annotation.Map;

/**
 * 
 * @author York/GuangYu DENG
 */
@Map(classId = "_map")
public abstract class AbstractMapAdapter implements MapAdapter {

	private static final long serialVersionUID = 4556301201920081648L;

	protected final java.util.Map<String, Object> _map = new HashMap<String, Object>();

	protected final java.util.Map<String, SelectOptions> _options = new HashMap<String, SelectOptions>();
	
	protected final java.util.Map<String, Anchor> _anchors = new HashMap<String, Anchor>();
	
	protected final java.util.Map<String, Text> _texts = new HashMap<String, Text>();
	
	protected final java.util.Map<String, String> _forms = new HashMap<String, String>();
	
	protected String _placeholder = ("");
	
	/**
	 * Sets a value for this adapter
	 * 
	 * @param id the value/element id
	 * @param value the value instance
	 */
	public void setValue(final String id, final Object value) {
		_map.put(id, value);
	}
	
	/**
	 * Sets an anchor for this adapter
	 * 
	 * @param id the anchor/element id
	 * @param anchor the anchor instance
	 */
	public void setAnchor(final String id, final Anchor anchor) {
		_anchors.put(id, anchor);
	}
	
	/**
	 * Sets a form action for this adapter
	 * 
	 * @param id the form element id.
	 * @param formAction the form action.
	 */
	public void setFormAction(final String id, final String formAction) {
		_forms.put(id, formAction);
	}

	/**
	 * Sets a placeholder for null value
	 * 
	 * @param placeholder the placeholder
	 */
	public void setPlaceholderForNull(final String placeholder) {
		_placeholder = placeholder;
	}
	
	// MapAdapter methods

	@Override
	public Set<String> idSetOfValue() {
		return _map.keySet();
	}
	
	@Override
	public Object getValue(final String id) {
		return _map.get(id);
	}
	
	@Override
	public Set<String> idSetOfForm() {
		return _forms.keySet();
	}
	
	@Override
	public String getFormAction(String id) {
		return _forms.get(id);
	}
	
	@Override
	public Set<String> idSetOfAnchor() {
		return _anchors.keySet();
	}
	
	@Override
	public Anchor getAnchor(final String id) {
		return _anchors.get(id);
	}
	
	@Override
	public Set<String> idSetOfText() {
		return _texts.keySet();
	}
	
	@Override
	public Text getText(final String id) {
		return _texts.get(id);
	}

	@Override
	public Set<String> idSetOfSelectOptions() {
		return (_options.keySet());
	}

	@Override
	public SelectOptions getSelectOptions(final String id) {
		return (_options.get(id));
	}

	@Override
	public String getPlaceholderForNull() {
		return _placeholder;
	}
}
