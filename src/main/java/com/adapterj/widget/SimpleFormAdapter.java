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
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.adapterj.annotation.POJO;
import com.google.gson.Gson;

/**
 * The adapter contains only one POJO instance for a FORM view.
 * 
 */
@POJO(classId = "_form")
public class SimpleFormAdapter<T> extends AbstractPOJOAdapter<T> {

	private static final long serialVersionUID = 5709503500113644325L;

	private final Map<String, SelectOptions> _optionsMap = new LinkedHashMap<String, SelectOptions>();

	private String _formAction = null;
	
	/**
	 * Puts a select-options into this adapter.
	 * 
	 * @param id the HTML select tag/element id.
	 * @param options the select-options.
	 */
	public void putSelectOptions(String id, SelectOptions options) {
		if (id == null) {
			// 处理为抛出异常
			return;
		}
		if (options == null || options.isEmpty()) {
			// 处理为抛出异常
			return;
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
		if (id == null) {
			// 处理为抛出异常
			return;
		}
		if (options == null || options.isEmpty()) {
			// 处理为抛出异常
			return;
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
		if (id == null) {
			// 处理为抛出异常
			return;
		}
		if (options == null || options.isEmpty()) {
			// 处理为抛出异常
			return;
		}
		if (selected == null || selected.isEmpty()) {
			// 处理为抛出异常
			return;
		}
		_optionsMap.put(id, new SimpleSelectOptions(id, options, selected));
	}

	/**
	 * Puts a select-options list into this adapter.
	 * 
	 * @param id the HTML select tag/element id.
	 * @param options the select-options list.
	 */
	public void putSelectOptions(String id, List<String> options) {
		if (id == null) {
			// 处理为抛出异常
			return;
		}
		if (options == null || options.isEmpty()) {
			// 处理为抛出异常
			return;
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
	public void putSelectOptions(String id, List<String> options, int selected) {
		if (id == null) {
			// 处理为抛出异常
			return;
		}
		if (options == null || options.isEmpty()) {
			// 处理为抛出异常
			return;
		}
		if (selected < 0) {
			// 处理为抛出异常
			return;
		}
		_optionsMap.put(id, new SimpleSelectOptions(id, options, selected));
	}

	/**
	 * Sets a form action for this adapter.
	 * 
	 * @param formAction the form action.
	 */
	public void setFormAction(final String formAction) {
		_formAction = formAction;
	}
	
	/**
	 * Returns the form action.
	 * 
	 * @return the form action.
	 */
	public String getFormAction() {
		return _formAction;
	}
	
	// POJOAdapter methods
	
	@Override
	public Set<String> idSetOfSelectOptions() {
		return (_optionsMap.keySet());
	}

	@Override
	public SelectOptions getSelectOptions(String id) {
		return (_optionsMap.get(id));
	}

	// Adapter methods

    @Override
    public boolean isEmpty() {
        return (_data == null);
    }

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
		
		// pojo
		s.append("var").append(' ').append("_pojo").append(' ').append('=').append(' ');
		try {
			final Gson gson = new Gson();
			s.append(gson.toJson(_data));
		} catch (Throwable e) {
			e.printStackTrace();
			s.append('{').append('}');
		}
		s.append(';').append('\n');

		// select-options
		final Set<String> optionsIdSet = _optionsMap.keySet();
		final int optionsCount = optionsIdSet.size();
		int i = 0;
		for (String optionsId : optionsIdSet) {
			final SelectOptions options = _optionsMap.get(optionsId);
			s.append("var").append(' ').append("_optionsId" ).append(i).append(' ').append('=').append(' ').append('"').append(optionsId).append('"').append(';').append('\n');
			s.append("var").append(' ').append("_optionsObj").append(i).append(' ').append('=').append(' ').append(options.toJSONString()).append(';').append('\n');
			i ++;
		}

		// anchors
		if (_anchors != null) {
		s.append("var").append(' ').append("_anchors").append(' ').append('=').append(' ').append(_anchors.toJSONString()).append(';').append('\n');
		s.append("var _length = _anchors.length;").append('\n');
		}

		// adapter
		s.append('\n');
		s.append("var adapter = new ADAPTERJ.widget.SimpleFormAdapter(_id);").append('\n');
		for (int k = 0; k < optionsCount; k ++) {
			s.append("adapter.putSelectOptions(_optionsId").append(k).append(", new ADAPTERJ.widget.SimpleSelectOptions(_optionsId").append(k).append(", _optionsObj").append(k).append("));").append('\n');
		}
		s.append("adapter.setData(_pojo);").append('\n');
		if (_anchors != null) {
			s.append('\n');
			s.append("var anchors = new ADAPTERJ.widget.AnchorGroup(_length);").append('\n');
			s.append("for (var i = 0; i < _length; i ++) {").append('\n');
			s.append("var anchor = _anchors[i];").append('\n');
			s.append("if (anchor) anchors.setAnchor(i, new ADAPTERJ.widget.Anchor(anchor.url, anchor.title, anchor.label));").append('\n');
			s.append('}').append(';').append('\n');
			s.append("adapter.setAnchorGroup(anchors);").append('\n');
		}
		s.append('\n');
		s.append("adapter.bindViewHolder();").append('\n');
		
		// function end
		s.append('}').append(';');
		
		return s.toString();
	}
}
