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

import java.util.List;
import java.util.Map;

import com.adapterj.annotation.ID;

/**
 * The adapter contains only a Map instances.
 * 
 */
@ID(identity = "_map")
public class SimpleMapAdapter extends AbstractMapAdapter {

	private static final long serialVersionUID = -6097024935769540181L;

	/**
	 * Puts a select-options into this adapter.
	 * 
	 * @param id the HTML select tag/element id.
	 * @param options the select-options.
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
	 * Puts a select-options map into this adapter.
	 * 
	 * @param id the HTML select tag/element id.
	 * @param options the select-options map.
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
	 * Puts a select-options map into this adapter.
	 * 
	 * @param id the HTML select tag/element id.
	 * @param options the select-options map.
	 * @param selected the selected option value.
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
	 * Puts a select-options list into this adapter.
	 * 
	 * @param id the HTML select tag/element id.
	 * @param options the select-options list.
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
	 * Puts a select-options list into this adapter.
	 * 
	 * @param id the HTML select tag/element id.
	 * @param options the select-options list.
	 * @param selected the selected option int value.
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
