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
	 * 
	 * @param selectId
	 * @param options
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
	 * 
	 * @param selectId
	 * @param options
	 * @param selected
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
	 * 
	 * @param selectId
	 * @param options
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
	 * 
	 * @param selectId
	 * @param options
	 * @param selected
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
	 * 
	 * @param selectId
	 * @param options
	 * @param selected
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
