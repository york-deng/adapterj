package com.adapterj.widget;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import com.google.gson.Gson;

import com.adapterj.annotation.List;
import com.adapterj.exceptions.NotImplementedException;

/**
 * 
 * @author York/GuangYu DENG
 */
@List(classId = "_list", entryId = "item", indexId = "j")
public class SimpleListAdapter<T> extends AbstractListAdapter<T> {

	private static final long serialVersionUID = 3678749643968977504L;
	
	private final Map<String, SelectOptions> _optionsMap = new LinkedHashMap<String, SelectOptions>();

	/**
	 * Basic constructor.
	 */
	public SimpleListAdapter () {
		// do nothing
	}

	/**
	 * 
	 * @param id
	 * @param options
	 */
	public void putSelectOptions(String id, SelectOptions options) {
		if (id == null || id.isEmpty()) {
			throw new IllegalArgumentException(id == null ? "Illegal argument: id is null" : "Illegal argument: id is \"\"");
		}
		if (options == null || options.isEmpty()) {
			throw new IllegalArgumentException(id == null ? "Illegal argument: options is null" : "Illegal argument: options is empty");
		}
		_optionsMap.put(id, options);
	}
	
	/**
	 * 
	 * @param id
	 * @param options
	 */
	public void putSelectOptions(String id, Map<String, String> options) {
		if (id == null || id.isEmpty()) {
			throw new IllegalArgumentException(id == null ? "Illegal argument: id is null" : "Illegal argument: id is \"\"");
		}
		if (options == null || options.isEmpty()) {
			throw new IllegalArgumentException(id == null ? "Illegal argument: options is null" : "Illegal argument: options is empty");
		}
		_optionsMap.put(id, new SimpleSelectOptions(id, options));
	}
	
	/**
	 * 
	 * @param id
	 * @param options
	 * @param selected
	 */
	public void putSelectOptions(String id, Map<String, String> options, String selected) {
		if (id == null || id.isEmpty()) {
			throw new IllegalArgumentException(id == null ? "Illegal argument: id is null" : "Illegal argument: id is \"\"");
		}
		if (options == null || options.isEmpty()) {
			throw new IllegalArgumentException(id == null ? "Illegal argument: options is null" : "Illegal argument: options is empty");
		}
		_optionsMap.put(id, new SimpleSelectOptions(id, options, selected));
	}

	/**
	 * 
	 * @param id
	 * @param options
	 */
	public void putSelectOptions(String id, java.util.List<String> options) {
		if (id == null || id.isEmpty()) {
			throw new IllegalArgumentException(id == null ? "Illegal argument: id is null" : "Illegal argument: id is \"\"");
		}
		if (options == null || options.isEmpty()) {
			throw new IllegalArgumentException(id == null ? "Illegal argument: options is null" : "Illegal argument: options is empty");
		}
		_optionsMap.put(id, new SimpleSelectOptions(id, options));
	}

	/**
	 * 
	 * @param id
	 * @param options
	 * @param selected
	 */
	public void putSelectOptions(String id, java.util.List<String> options, int selected) {
		if (id == null || id.isEmpty()) {
			throw new IllegalArgumentException(id == null ? "Illegal argument: id is null" : "Illegal argument: id is \"\"");
		}
		if (options == null || options.isEmpty()) {
			throw new IllegalArgumentException(id == null ? "Illegal argument: options is null" : "Illegal argument: options is empty");
		}
		_optionsMap.put(id, new SimpleSelectOptions(id, options, selected));
	}

	// ListAdapter methods

	@Override
	public long getItemId(int position) {
		throw new NotImplementedException("can NOT get item id");
	}

	@Override
	public T getItem(int position) {
		return (_list.get(position));
	}

	@Override
	public java.util.List<T> getAllItems() {
		return (_list);
	}
	
	@Override
	public Set<String> idSetOfSelectOptions() {
		return (_optionsMap.keySet());
	}
	
	@Override
	public SelectOptions getSelectOptions(final String id) {
		return (_optionsMap.get(id));
	}

	// Adapter methods
	
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
		
		// list
		s.append("var").append(' ').append("_list").append(' ').append('=').append(' ');
		try {
			final Gson gson = new Gson();
			s.append(gson.toJson(_list));
		} catch (Throwable e) {
			e.printStackTrace();
			s.append('[').append(']');
		}
		s.append(';').append('\n');
		
		// select-options
		final Set<String> optionsIdSet = this._optionsMap.keySet();
		final int optionsCount = optionsIdSet.size();
		int i = 0;
		for (String optionsId : optionsIdSet) {
			final SelectOptions options = this._optionsMap.get(optionsId);
			s.append("var").append(' ').append("_optionsId" ).append(i).append(' ').append('=').append(' ').append('"').append(optionsId).append('"').append(';').append('\n');
			s.append("var").append(' ').append("_optionsObj").append(i).append(' ').append('=').append(' ').append(options.toJSONString()).append(';').append('\n');
			i ++;
		}
		
		// links
		s.append("var").append(' ').append("_linksArray").append(' ').append('=').append(' ').append('[');
		int j = 0;
		for (LinkGroup links : this._linksList) {
			if (j > 0) s.append(',').append(' ');
			if (links != null) {
				s.append(links.toJSONString());
				j ++;
			}
		}
		s.append(']').append(';').append('\n');
		s.append("var _length = !_linksArray[0] ? 0 : _linksArray[0].length;").append('\n');

		// adapter
		s.append('\n');
		s.append("var adapter = new ADAPTERJ.widget.SimpleListAdapter(_id);").append('\n');
		for (int k = 0; k < optionsCount; k ++) {
			s.append("adapter.putSelectOptions(_optionsId").append(k).append(", new ADAPTERJ.widget.SimpleSelectOptions(_optionsId").append(k).append(", _optionsObj").append(k).append("));").append('\n');
		}
		s.append('\n');
		s.append("for (var i = 0; i < _list.length; i ++) {").append('\n');
		s.append("adapter.addItem(_list[i]);").append('\n');
		s.append('\n');
		s.append("var links = new ADAPTERJ.widget.LinkGroup(_length);").append('\n');
		s.append("for (var j = 0; j < _length; j ++) {").append('\n');
		s.append("var link = !_linksArray[i] ? undefined : _linksArray[i][j];").append('\n');
		s.append("if (link) links.setLink(j, new ADAPTERJ.widget.Link(link.url, link.title, link.label));").append('\n');
		s.append('}').append(';').append('\n');
		s.append('\n');
		s.append("adapter.addLinkGroup(links);").append('\n');
		s.append('}').append(';').append('\n');
		s.append('\n');
		s.append("adapter.bindViewHolder();").append('\n');
		
		// function end
		s.append('}').append(';');
		
		return s.toString();
	}
}
