package com.adapterj.test.widget;

import java.text.DateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.adapterj.logging.Debugger;
import com.adapterj.logging.Log;
import com.adapterj.text.Formatter;
import com.adapterj.widget.Adapter;
import com.adapterj.widget.SimpleListAdapter;
import com.adapterj.widget.ViewAccelerator;

@SuppressWarnings("unused")
public class SimpleHTMLViewAcceleratorV2 implements ViewAccelerator {

	private static final long serialVersionUID = -8032121207723274307L;

	private static final boolean DEBUG = Debugger.DEBUG;
	
    private static final String TAG = SimpleHTMLViewV3.class.getName();
	
	protected Formatter _formatter = new Formatter();

	public void setFormatter(Formatter formatter) {
		if (formatter == null) {
			throw new IllegalArgumentException("formatter is null");
		}
		_formatter = formatter;
	}
	
	// BASED OBJECT-LIST & BYTES-COPY MODEL

	protected final Map<String, Adapter> _adapters = new HashMap<String, Adapter>();

	public void putAdapter(final String id, final Object adapter) {
		if (adapter instanceof Adapter) {
			_adapters.put(id, (Adapter) adapter);
		}
	}
	
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

	// BASED STRING-LIST & FIELD-COPY MODEL
	
	protected final Map<String, List<String>> _listMap1 = new HashMap<String, List<String>>();
	
	protected final Map<String, List<List<String>>> _listMap2 = new HashMap<String, List<List<String>>>();
	
	public Set<String> idSet1() {
		return _listMap1.keySet();
	}
	
	public List<String> getList1(final String id) {
		return _listMap1.get(id);
	}
	
	public Set<String> idSet2() {
		return _listMap2.keySet();
	}
	
	public List<List<String>> getList2(final String id) {
		return _listMap2.get(id);
	}
	
	// ViewAccelerator methods

	// BASED OBJECT-LIST & BYTES-COPY MODEL
	
	@Override
	public void putAdapter(final String id, final Adapter adapter) {
		_adapters.put(id, adapter);
	}

	// BASED STRING-LIST & FIELD-COPY MODEL
	
	//@Override
	public void putList1(final String id, final List<String> list) {
		_listMap1.put(id, list);
	}
	
	//@Override
	public void putList2(final String id, final List<List<String>> list) {
		_listMap2.put(id, list);
	}

	// TEST ONLY
	
	//@Override
	public String getName() {
		return "com.adapterj.widget.SimpleHTMLViewAccelerator";
	}
}
