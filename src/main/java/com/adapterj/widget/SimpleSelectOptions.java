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

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jsoup.nodes.Entities;

import com.google.gson.Gson;

/**
 * 
 * @author York/GuangYu DENG
 */
public class SimpleSelectOptions extends AbstractSelectOptions implements Javable {

	private static final long serialVersionUID = 4962400260908222421L;
	
	private final String _id;
	
	/**
	 * Constructs a SimpleSelectOptions instance.
	 * 
	 * @param selectId the HTML select tag/element id. 
	 * @param options the select-options list.
	 */
	public SimpleSelectOptions(final String selectId, final List<String> options) {
		_id = selectId;
		int value = 1;
		for (final String text : options) {
			put(value, text);
			value ++;
		}
		super.setHelp("");
	}

	/**
	 * Constructs a SimpleSelectOptions instance.
	 * 
	 * @param selectId the HTML select tag/element id. 
	 * @param options the select-options list.
	 * @param selected the selected option value.
	 */
	public SimpleSelectOptions(String selectId, List<String> options, int selected) {
		_id = selectId;
		int value = 1;
		for (final String text : options) {
			put(value, text, selected);
			value ++;
		}
	}

	/**
	 * Constructs a SimpleSelectOptions instance.
	 * 
	 * @param selectId the HTML select tag/element id. 
	 * @param options the select-options map.
	 */
	public SimpleSelectOptions(String selectId, Map<String, String> options) {
		_id = selectId;
		final Set<String> valuesSet = options.keySet();
		final Iterator<String> iter = valuesSet.iterator();
		while (iter.hasNext()) {
			final String value = iter.next();
			final String text = options.get(value);
			put(value, text);
		}
	}

	/**
	 * Constructs a SimpleSelectOptions instance.
	 * 
	 * @param selectId the HTML select tag/element id. 
	 * @param options the select-options map.
	 * @param selected the selected option value.
	 */
	public SimpleSelectOptions(String selectId, Map<String, String> options, String selected) {
		_id = selectId;
		final Set<String> valuesSet = options.keySet();
		final Iterator<String> iter = valuesSet.iterator();
		while (iter.hasNext()) {
			final String value = iter.next();
			final String text = options.get(value);
			if (selected != null && !selected.isEmpty()) {
				put(value, text, selected);
			} else {
				put(value, text);
			}
		}
	}

	/**
	 * Constructs a SimpleSelectOptions instance.
	 * 
	 * @param selectId the HTML select tag/element id. 
	 * @param options the select-options map.
	 * @param selected the selected option value.
	 */
	public SimpleSelectOptions(String selectId, Map<Integer, String> options, Integer selected) {
		_id = selectId;
		final Set<Integer> valuesSet = options.keySet();
		final Iterator<Integer> iter = valuesSet.iterator();
		while (iter.hasNext()) {
			final Integer value = iter.next();
			final String text = options.get(value);
			if (selected != null) {
				put(Integer.toString(value), text, Integer.toString(selected));
			} else {
				put(Integer.toString(value), text);
			}
		}
	}

	// SelectOptions methods

	@Override
	public String selected(final String value, final boolean optionsHTML) {
		_selected = value;
		if (!optionsHTML) return selected(value);
		
		final StringBuffer s = new StringBuffer();
//		s.append('<').append("select").append(' ');
//		s.append("id").append('=').append('"').append(_id).append('"').append(' ');
//		s.append("name").append('=').append('"').append(_id).append('"').append(' ').append('>');
		
		if (_help != null && !_help.isEmpty()) {
			final String nothing = null;
			final String escaped = Entities.escape(_help);
			s.append('<').append("option").append(' ');
			s.append("value").append('=').append('"').append(nothing).append('"');
			s.append('>');
			s.append(escaped);
			s.append('<').append('/').append("option").append('>');
		}
		
		final Set<String> keySet = _options.keySet();
		final Iterator<String> iter = keySet.iterator();
		while (iter.hasNext()) {
			final String key = iter.next();
			final String text = _options.get(key);
			s.append('<').append("option").append(' ');
			s.append("value").append('=').append('"').append(key).append('"');
			if (_selected != null && _selected.equals(key)) {
				s.append(' ').append("selected").append('=').append('"').append("selected").append('"');
			}
			s.append('>');
			s.append(text);
			s.append('<').append('/').append("option").append('>');
		}
//		s.append('<').append('/').append("select").append('>');
		
		return s.toString();
	}
	
	@Override
	public String toJSONString() {
		final StringBuffer s = new StringBuffer();
		try {
			final Gson gson = new Gson();
			s.append(gson.toJson(this._options));
		} catch (Throwable e) {
			e.printStackTrace();
			s.append('{').append('}');
		}
		return s.toString();
	}

	@Override
	public String toHTMLString() {
		final StringBuffer s = new StringBuffer();
		s.append('<').append("select").append(' ');
		s.append("id").append('=').append('"').append(_id).append('"').append(' ');
		s.append("name").append('=').append('"').append(_id).append('"').append(' ').append('>');
		
		if (_help != null && !_help.isEmpty()) {
			final String nothing = null;
			final String escaped = Entities.escape(_help);
			s.append('<').append("option").append(' ');
			s.append("value").append('=').append('"').append(nothing).append('"');
			s.append('>');
			s.append(escaped);
			s.append('<').append('/').append("option").append('>');
		}
		
		final Set<String> keySet = _options.keySet();
		final Iterator<String> iter = keySet.iterator();
		while (iter.hasNext()) {
			final String key = iter.next();
			final String text = _options.get(key);
			s.append('<').append("option").append(' ');
			s.append("value").append('=').append('"').append(key).append('"');
			if (_selected != null && _selected.equals(key)) {
				s.append(' ').append("selected").append('=').append('"').append("selected").append('"');
			}
			s.append('>');
			s.append(text);
			s.append('<').append('/').append("option").append('>');
		}
		s.append('<').append('/').append("select").append('>');
		
		return s.toString();
	}

	@Override
	public String toJavaScript() {
		return null;
	}
}
