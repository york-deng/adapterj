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

/**
 * 
 * @author York/GuangYu DENG
 */
public abstract class AbstractSelectOptions implements SelectOptions {

	private static final long serialVersionUID = -8528068077875278001L;

	protected final Map<String, String> _options = new LinkedHashMap<String, String>();
	
	protected String _help = "<Please select from the options below>";
	
	protected String _placeholderForEmpty = "N/A";

	protected String _selected = null;

	/**
	 * Sets a help info into this select-options as an option item
	 * 
	 * @param help the help info will show in this select-options
	 */
	public void setHelp(String help) {
		_help = help;
	}

	/**
	 * Sets a placeholder for empty value
	 * 
	 * @param placeholder the placeholder
	 */
	public void setPlaceholderForEmpty(final String placeholder) {
		_placeholderForEmpty = placeholder;
	}
	
	/**
	 * Puts a pair of value and text into this select-options as an option item
	 * 
	 * @param value the int value
	 * @param text the text will show in this select-options
	 */
	public void put(int value, String text) {
		if (text == null || text.isEmpty()) {
			throw new IllegalOptionsException(text == null ? "Illegal options: text is null" : "Illegal options: text is \"\"");
		}
		_options.put(Integer.toString(value), text);
	}
	
	/**
	 * Puts a pair of value and text into this select-options as an option item
	 * 
	 * @param value the value
	 * @param text the text will show in this select-options
	 */
	public void put(String value, String text) {
		if (value == null || value.isEmpty()) {
			throw new IllegalOptionsException(text == null ? "Illegal options: value is null" : "Illegal options: value is \"\"");
		}
		if (text == null || text.isEmpty()) {
			throw new IllegalOptionsException(text == null ? "Illegal options: text is null" : "Illegal options: text is \"\"");
		}
		_options.put(value, text);
	}
	
	/**
	 * Puts a pair of value and text into this select-options as the selected option item
	 * 
	 * @param value the int value
	 * @param text the text will show in this select-options
	 * @param selected whether this option item will be selected or not
	 */
	public void put(int value, String text, int selected) {
		if (text == null || text.isEmpty()) {
			throw new IllegalOptionsException(text == null ? "Illegal options: text is null" : "Illegal options: text is \"\"");
		}
		_options.put(Integer.toString(value), text);
		_selected = Integer.toString(selected);
	}
	
	/**
	 * Puts a pair of value and text into this select-options as the selected option item
	 * 
	 * @param value the value
	 * @param text the text will show in this select-options
	 * @param selected whether this option item will be selected or not
	 */
	public void put(String value, String text, String selected) {
		if (value == null || value.isEmpty()) {
			throw new IllegalOptionsException(text == null ? "Illegal options: value is null" : "Illegal options: value is \"\"");
		}
		if (text == null || text.isEmpty()) {
			throw new IllegalOptionsException(text == null ? "Illegal options: text is null" : "Illegal options: text is \"\"");
		}
		_options.put(value, text);
		_selected = selected;
	}

	// SelectOptions methods
	
	@Override
	public boolean isEmpty() {
		return _options.isEmpty();
	}
	
	@Override
	public String selected(final String value) {
		_selected = value;
		
		String text = null;
		if (_selected != null & !_selected.isEmpty()) text = _options.get(_selected);
		return text == null ? _placeholderForEmpty : text;
	}

	@Override
	public String selected() {
		String text = null;
		if (_selected != null & !_selected.isEmpty()) text = _options.get(_selected);
		return text == null ? _placeholderForEmpty : text;
	}

	@Override
	public String getPlaceholderForEmpty() {
		return _placeholderForEmpty;
	}
}
