package com.adapterj.widget;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.adapterj.annotation.POJO;
import com.google.gson.Gson;

/**
 * The adapter contains only one POJO instance.
 * 
 * @author York/GuanugYu DENG
 * @param <T>
 */
@POJO(classId = "_form")
public class SimpleFormAdapter<T> extends AbstractPOJOAdapter<T> {

	private static final long serialVersionUID = 5709503500113644325L;

	private final Map<String, SelectOptions> _optionsMap = new LinkedHashMap<String, SelectOptions>();

	private String _formAction = null;
	
	/**
	 * 
	 * @param id
	 * @param options
	 */
	public void putSelectOptions(String id, SelectOptions options) {
		if (id == null) {
			// 处理为抛出异常
			return;
		}
		if (options == null || options.isEmpty()) {
			// 处理为抛出异常
			return;
		}
		_optionsMap.put(id, options);
	}
	
	/**
	 * 
	 * @param id
	 * @param options
	 */
	public void putSelectOptions(String id, Map<String, String> options) {
		if (id == null) {
			// 处理为抛出异常
			return;
		}
		if (options == null || options.isEmpty()) {
			// 处理为抛出异常
			return;
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
		if (id == null) {
			// 处理为抛出异常
			return;
		}
		if (options == null || options.isEmpty()) {
			// 处理为抛出异常
			return;
		}
		if (selected == null || selected.isEmpty()) {
			// 处理为抛出异常
			return;
		}
		_optionsMap.put(id, new SimpleSelectOptions(id, options, selected));
	}

	/**
	 * 
	 * @param id
	 * @param options
	 */
	public void putSelectOptions(String id, List<String> options) {
		if (id == null) {
			// 处理为抛出异常
			return;
		}
		if (options == null || options.isEmpty()) {
			// 处理为抛出异常
			return;
		}
		_optionsMap.put(id, new SimpleSelectOptions(id, options));
	}

	/**
	 * 
	 * @param id
	 * @param options
	 * @param selected
	 */
	public void putSelectOptions(String id, List<String> options, int selected) {
		if (id == null) {
			// 处理为抛出异常
			return;
		}
		if (options == null || options.isEmpty()) {
			// 处理为抛出异常
			return;
		}
		if (selected < 0) {
			// 处理为抛出异常
			return;
		}
		_optionsMap.put(id, new SimpleSelectOptions(id, options, selected));
	}

	/**
	 * 
	 * @param formAction
	 */
	public void setFormAction(final String formAction) {
		_formAction = formAction;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getFormAction() {
		return _formAction;
	}
	
	// POJOAdapter methods
	
	@Override
	public Set<String> idSetOfSelectOptions() {
		return (_optionsMap.keySet());
	}

	public SelectOptions getSelectOptions(String id) {
		return (_optionsMap.get(id));
	}

	// Adapter methods

    @Override
    public boolean isEmpty() {
        return (_data == null);
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
	public String toJavaScript(final String id, final String function) {
		final StringBuffer s = new StringBuffer();
		
		// function
		s.append("function").append(' ').append(function).append('(').append(')').append(' ').append('{').append('\n');
		
		// id
		s.append("var").append(' ').append("_id").append(' ').append('=').append(' ').append('"').append(id).append('"').append(';').append('\n');
		
		// pojo
		s.append("var").append(' ').append("_pojo").append(' ').append('=').append(' ');
		try {
			final Gson gson = new Gson();
			s.append(gson.toJson(_data));
		} catch (Throwable e) {
			e.printStackTrace();
			s.append('{').append('}');
		}
		s.append(';').append('\n');

		// select-options
		final Set<String> optionsIdSet = _optionsMap.keySet();
		final int optionsCount = optionsIdSet.size();
		int i = 0;
		for (String optionsId : optionsIdSet) {
			final SelectOptions options = _optionsMap.get(optionsId);
			s.append("var").append(' ').append("_optionsId" ).append(i).append(' ').append('=').append(' ').append('"').append(optionsId).append('"').append(';').append('\n');
			s.append("var").append(' ').append("_optionsObj").append(i).append(' ').append('=').append(' ').append(options.toJSONString()).append(';').append('\n');
			i ++;
		}

		// links
		if (_links != null) {
		s.append("var").append(' ').append("_links").append(' ').append('=').append(' ').append(_links.toJSONString()).append(';').append('\n');
		s.append("var _length = _links.length;").append('\n');
		}

		// adapter
		s.append('\n');
		s.append("var adapter = new ADAPTERJ.widget.SimpleFormAdapter(_id);").append('\n');
		for (int k = 0; k < optionsCount; k ++) {
			s.append("adapter.putSelectOptions(_optionsId").append(k).append(", new ADAPTERJ.widget.SimpleSelectOptions(_optionsId").append(k).append(", _optionsObj").append(k).append("));").append('\n');
		}
		s.append("adapter.setData(_pojo);").append('\n');
		if (_links != null) {
			s.append('\n');
			s.append("var links = new ADAPTERJ.widget.LinkGroup(_length);").append('\n');
			s.append("for (var i = 0; i < _length; i ++) {").append('\n');
			s.append("var link = _links[i];").append('\n');
			s.append("if (link) links.setLink(i, new ADAPTERJ.widget.Link(link.url, link.title, link.label));").append('\n');
			s.append('}').append(';').append('\n');
			s.append("adapter.setLinkGroup(links);").append('\n');
		}
		s.append('\n');
		s.append("adapter.bindViewHolder();").append('\n');
		
		// function end
		s.append('}').append(';');
		
		return s.toString();
	}
}
