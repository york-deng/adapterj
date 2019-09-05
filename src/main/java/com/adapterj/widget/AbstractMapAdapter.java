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
	
	protected final java.util.Map<String, Link> _links = new HashMap<String, Link>();
	
	protected final java.util.Map<String, Text> _texts = new HashMap<String, Text>();
	
	protected final java.util.Map<String, String> _forms = new HashMap<String, String>();
	
	protected String _placeholder = ("");
	
	/**
	 * 
	 * @param id
	 * @param value
	 */
	public void setValue(final String id, final Object value) {
		_map.put(id, value);
	}
	
	/**
	 * 
	 * @param id
	 * @param link
	 */
	public void setLink(final String id, final Link link) {
		_links.put(id, link);
	}
	
	/**
	 * 
	 * @param id
	 * @param formAction
	 */
	public void setFormAction(final String id, final String formAction) {
		_forms.put(id, formAction);
	}

	/**
	 * 
	 * @param placeholder
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
	public Set<String> idSetOfLink() {
		return _links.keySet();
	}
	
	@Override
	public Link getLink(final String id) {
		return _links.get(id);
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
