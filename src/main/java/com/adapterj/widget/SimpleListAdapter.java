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

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import com.google.gson.Gson;

import com.adapterj.annotation.List;
import com.adapterj.exceptions.NotImplementedException;

/**
 * The adapter contains only a List instances.
 * 
 */
@List(classId = "_list", entryId = "item", indexId = "j")
public class SimpleListAdapter<T> extends AbstractListAdapter<T> {

	private static final long serialVersionUID = 3678749643968977504L;
	
	private final Map<String, SelectOptions> _optionsMap = new LinkedHashMap<String, SelectOptions>();

	/**
	 * Constructs a SimpleListAdapter instance.
	 */
	public SimpleListAdapter () {
		// do nothing
	}

	/**
	 * Puts a select-options into this adapter.
	 * 
	 * @param id the HTML select tag/element id.
	 * @param options the select-options.
	 */
	public void putSelectOptions(String id, SelectOptions options) {
		if (id == null || id.isEmpty()) {
			throw new IllegalArgumentException(id == null ? "Illegal argument: id is null" : "Illegal argument: id is \"\"");
		}
		if (options == null || options.isEmpty()) {
			throw new IllegalArgumentException(id == null ? "Illegal argument: options is null" : "Illegal argument: options is empty");
		}
		_optionsMap.put(id, options);
	}
	
	/**
	 * Puts a select-options map into this adapter.
	 * 
	 * @param id the HTML select tag/element id.
	 * @param options the select-options map.
	 */
	public void putSelectOptions(String id, Map<String, String> options) {
		if (id == null || id.isEmpty()) {
			throw new IllegalArgumentException(id == null ? "Illegal argument: id is null" : "Illegal argument: id is \"\"");
		}
		if (options == null || options.isEmpty()) {
			throw new IllegalArgumentException(id == null ? "Illegal argument: options is null" : "Illegal argument: options is empty");
		}
		_optionsMap.put(id, new SimpleSelectOptions(id, options));
	}
	
	/**
	 * Puts a select-options map into this adapter.
	 * 
	 * @param id the HTML select tag/element id.
	 * @param options the select-options map.
	 * @param selected the selected option value.
	 */
	public void putSelectOptions(String id, Map<String, String> options, String selected) {
		if (id == null || id.isEmpty()) {
			throw new IllegalArgumentException(id == null ? "Illegal argument: id is null" : "Illegal argument: id is \"\"");
		}
		if (options == null || options.isEmpty()) {
			throw new IllegalArgumentException(id == null ? "Illegal argument: options is null" : "Illegal argument: options is empty");
		}
		_optionsMap.put(id, new SimpleSelectOptions(id, options, selected));
	}

	/**
	 * Puts a select-options list into this adapter.
	 * 
	 * @param id the HTML select tag/element id.
	 * @param options the select-options list.
	 */
	public void putSelectOptions(String id, java.util.List<String> options) {
		if (id == null || id.isEmpty()) {
			throw new IllegalArgumentException(id == null ? "Illegal argument: id is null" : "Illegal argument: id is \"\"");
		}
		if (options == null || options.isEmpty()) {
			throw new IllegalArgumentException(id == null ? "Illegal argument: options is null" : "Illegal argument: options is empty");
		}
		_optionsMap.put(id, new SimpleSelectOptions(id, options));
	}

	/**
	 * Puts a select-options list into this adapter.
	 * 
	 * @param id the HTML select tag/element id.
	 * @param options the select-options list.
	 * @param selected the selected option int value.
	 */
	public void putSelectOptions(String id, java.util.List<String> options, int selected) {
		if (id == null || id.isEmpty()) {
			throw new IllegalArgumentException(id == null ? "Illegal argument: id is null" : "Illegal argument: id is \"\"");
		}
		if (options == null || options.isEmpty()) {
			throw new IllegalArgumentException(id == null ? "Illegal argument: options is null" : "Illegal argument: options is empty");
		}
		_optionsMap.put(id, new SimpleSelectOptions(id, options, selected));
	}

	// ListAdapter methods

	@Override
	public long getItemId(int position) {
		throw new NotImplementedException("can NOT get item id");
	}

	@Override
	public T getItem(int position) {
		return (_list.get(position));
	}

	@Override
	public java.util.List<T> getAllItems() {
		return (_list);
	}
	
	@Override
	public Set<String> idSetOfSelectOptions() {
		return (_optionsMap.keySet());
	}
	
	@Override
	public SelectOptions getSelectOptions(final String id) {
		return (_optionsMap.get(id));
	}

	// Adapter methods
	
	@Override
	public String toJSONString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String toXMLString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String toJavaScript(final String id, final String function) {
		final StringBuffer s = new StringBuffer();
		
		// function
		s.append("function").append(' ').append(function).append('(').append(')').append(' ').append('{').append('\n');
		
		// id
		s.append("var").append(' ').append("_id").append(' ').append('=').append(' ').append('"').append(id).append('"').append(';').append('\n');
		
		// list
		s.append("var").append(' ').append("_list").append(' ').append('=').append(' ');
		try {
			final Gson gson = new Gson();
			s.append(gson.toJson(_list));
		} catch (Throwable e) {
			e.printStackTrace();
			s.append('[').append(']');
		}
		s.append(';').append('\n');
		
		// select-options
		final Set<String> optionsIdSet = this._optionsMap.keySet();
		final int optionsCount = optionsIdSet.size();
		int i = 0;
		for (String optionsId : optionsIdSet) {
			final SelectOptions options = this._optionsMap.get(optionsId);
			s.append("var").append(' ').append("_optionsId" ).append(i).append(' ').append('=').append(' ').append('"').append(optionsId).append('"').append(';').append('\n');
			s.append("var").append(' ').append("_optionsObj").append(i).append(' ').append('=').append(' ').append(options.toJSONString()).append(';').append('\n');
			i ++;
		}
		
		// anchors
		s.append("var").append(' ').append("_anchorsArray").append(' ').append('=').append(' ').append('[');
		int j = 0;
		for (AnchorGroup anchors : this._anchorsList) {
			if (j > 0) s.append(',').append(' ');
			if (anchors != null) {
				s.append(anchors.toJSONString());
				j ++;
			}
		}
		s.append(']').append(';').append('\n');
		s.append("var _length = !_anchorsArray[0] ? 0 : _anchorsArray[0].length;").append('\n');

		// adapter
		s.append('\n');
		s.append("var adapter = new ADAPTERJ.widget.SimpleListAdapter(_id);").append('\n');
		for (int k = 0; k < optionsCount; k ++) {
			s.append("adapter.putSelectOptions(_optionsId").append(k).append(", new ADAPTERJ.widget.SimpleSelectOptions(_optionsId").append(k).append(", _optionsObj").append(k).append("));").append('\n');
		}
		s.append('\n');
		s.append("for (var i = 0; i < _list.length; i ++) {").append('\n');
		s.append("adapter.addItem(_list[i]);").append('\n');
		s.append('\n');
		s.append("var anchors = new ADAPTERJ.widget.AnchorGroup(_length);").append('\n');
		s.append("for (var j = 0; j < _length; j ++) {").append('\n');
		s.append("var anchor = !_anchorsArray[i] ? undefined : _anchorsArray[i][j];").append('\n');
		s.append("if (anchor) anchors.setAnchor(j, new ADAPTERJ.widget.Anchor(anchor.url, anchor.title, anchor.label));").append('\n');
		s.append('}').append(';').append('\n');
		s.append('\n');
		s.append("adapter.addAnchorGroup(anchors);").append('\n');
		s.append('}').append(';').append('\n');
		s.append('\n');
		s.append("adapter.bindViewHolder();").append('\n');
		
		// function end
		s.append('}').append(';');
		
		return s.toString();
	}
}
