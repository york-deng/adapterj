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
	 * 
	 * @param help
	 */
	public void setHelp(String help) {
		_help = help;
	}

	/**
	 * 
	 * @param placeholder
	 */
	public void setPlaceholderForEmpty(final String placeholder) {
		_placeholderForEmpty = placeholder;
	}
	
	/**
	 * 
	 * @param value
	 * @param text
	 */
	public void put(int value, String text) {
		if (text == null || text.isEmpty()) {
			throw new IllegalOptionsException(text == null ? "Illegal options: text is null" : "Illegal options: text is \"\"");
		}
		_options.put(Integer.toString(value), text);
	}
	
	/**
	 * 
	 * @param value
	 * @param text
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
	 * 
	 * @param value
	 * @param text
	 * @param selected
	 */
	public void put(int value, String text, int selected) {
		if (text == null || text.isEmpty()) {
			throw new IllegalOptionsException(text == null ? "Illegal options: text is null" : "Illegal options: text is \"\"");
		}
		_options.put(Integer.toString(value), text);
		_selected = Integer.toString(selected);
	}
	
	/**
	 * 
	 * @param value
	 * @param text
	 * @param selected
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
