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

import java.text.DateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.adapterj.logging.Debugger;
import com.adapterj.logging.Log;
import com.adapterj.text.Formattable;
import com.adapterj.text.Formatter;

@SuppressWarnings("unused")
public class SimpleHTMLViewAccelerator implements ViewAccelerator, Formattable {

	private static final long serialVersionUID = -8032121207723274307L;

	private static final boolean DEBUG = Debugger.DEBUG;
	
    private static final String TAG = SimpleHTMLViewAccelerator.class.getName();

	protected final Map<String, Adapter> _adapters = new HashMap<String, Adapter>();

	public Set<String> idSet() {
		return _adapters.keySet();
	}
	
	public Adapter getAdapter(final String id) {
		return _adapters.get(id);
	}
	
	public SimpleListAdapter<?> getSimpleListAdapter(final String id) {
		final Adapter adapter = _adapters.get(id);
		
		if (adapter instanceof SimpleListAdapter) {
			return (SimpleListAdapter<?>) adapter;
		}
		return (null);
	}
	
	public SimpleFormAdapter<?> getSimpleFormAdapter(final String id) {
		final Adapter adapter = _adapters.get(id);
		
		if (adapter instanceof SimpleFormAdapter) {
			return (SimpleFormAdapter<?>) adapter;
		}
		return (null);
	}
	
	public SimpleViewAdapter<?> getSimpleViewAdapter(final String id) {
		final Adapter adapter = _adapters.get(id);
		
		if (adapter instanceof SimpleViewAdapter) {
			return (SimpleViewAdapter<?>) adapter;
		}
		return (null);
	}
	
	public SimpleMapAdapter getSimpleMapAdapter(final String id) {
		final Adapter adapter = _adapters.get(id);
		
		if (adapter instanceof SimpleMapAdapter) {
			return (SimpleMapAdapter) adapter;
		}
		return (null);
	}

	// ViewAccelerator methods

	@Override
	public void putAdapter(final String id, final Adapter adapter) {
		_adapters.put(id, adapter);
	}

	// Formattable methods
	
	protected Formatter _formatter = new Formatter();
	
	@Override
	public void setFormatter(Formatter formatter) {
		if (formatter == null) {
			throw new IllegalArgumentException("formatter is null");
		}
		_formatter = formatter;
	}
}
