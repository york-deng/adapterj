package com.adapterj.widget;

import java.util.List;
import java.util.Map;

import com.adapterj.annotation.ID;

/**
 * The adapter contains only more than one POJO instances.
 * 
 * @author York/GuanugYu DENG
 * @param <T>
 */
@ID(identity = "_map")
public class SimpleMapAdapter extends AbstractMapAdapter {

	private static final long serialVersionUID = -6097024935769540181L;

	/**
	 * 
	 * @param id
	 * @param options
	 */
	public void putSelectOptions(final String id, final SelectOptions options) {
		if (id == null || id.isEmpty()) {
			throw new IllegalArgumentException(id == null ? "Illegal argument: id is null" : "Illegal argument: id is \"\"");
		}
		if (options == null || options.isEmpty()) {
			throw new IllegalArgumentException(id == null ? "Illegal argument: options is null" : "Illegal argument: options is empty");
		}
		_options.put(id, options);
	}
	
	/**
	 * 
	 * @param id
	 * @param options
	 */
	public void putSelectOptions(final String id, final Map<String, String> options) {
		if (id == null || id.isEmpty()) {
			throw new IllegalArgumentException(id == null ? "Illegal argument: id is null" : "Illegal argument: id is \"\"");
		}
		if (options == null || options.isEmpty()) {
			throw new IllegalArgumentException(id == null ? "Illegal argument: options is null" : "Illegal argument: options is empty");
		}
		_options.put(id, new SimpleSelectOptions(id, options));
	}
	
	/**
	 * 
	 * @param id
	 * @param options
	 * @param selected
	 */
	public void putSelectOptions(final String id, final Map<String, String> options, final String selected) {
		if (id == null || id.isEmpty()) {
			throw new IllegalArgumentException(id == null ? "Illegal argument: id is null" : "Illegal argument: id is \"\"");
		}
		if (options == null || options.isEmpty()) {
			throw new IllegalArgumentException(id == null ? "Illegal argument: options is null" : "Illegal argument: options is empty");
		}
		_options.put(id, new SimpleSelectOptions(id, options, selected));
	}

	/**
	 * 
	 * @param id
	 * @param options
	 */
	public void putSelectOptions(final String id, final List<String> options) {
		if (id == null || id.isEmpty()) {
			throw new IllegalArgumentException(id == null ? "Illegal argument: id is null" : "Illegal argument: id is \"\"");
		}
		if (options == null || options.isEmpty()) {
			throw new IllegalArgumentException(id == null ? "Illegal argument: options is null" : "Illegal argument: options is empty");
		}
		_options.put(id, new SimpleSelectOptions(id, options));
	}

	/**
	 * 
	 * @param id
	 * @param options
	 * @param selected
	 */
	public void putSelectOptions(final String id, final List<String> options, final int selected) {
		if (id == null || id.isEmpty()) {
			throw new IllegalArgumentException(id == null ? "Illegal argument: id is null" : "Illegal argument: id is \"\"");
		}
		if (options == null || options.isEmpty()) {
			throw new IllegalArgumentException(id == null ? "Illegal argument: options is null" : "Illegal argument: options is empty");
		}
		_options.put(id, new SimpleSelectOptions(id, options, selected));
	}

	// Adapter methods

    @Override
    public boolean isEmpty() {
        return (_map.isEmpty());
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
	public String toJavaScript(String id, String function) {
		// TODO Auto-generated method stub
		return null;
	}
}
