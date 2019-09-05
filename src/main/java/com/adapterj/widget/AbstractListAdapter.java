package com.adapterj.widget;

import java.util.Collection;
import java.util.Iterator;

import com.adapterj.annotation.List;
import com.adapterj.util.DataSet;

/**
 * 
 * @author York/GuangYu DENG
 */
@List(classId = "_list", entryId = "entry", indexId = "i")
public abstract class AbstractListAdapter<T> implements ListAdapter<T>, DataSet<T> {

	private static final long serialVersionUID = 5651619575362600871L;

	protected final java.util.List<T> _list = new java.util.ArrayList<T>();

	protected final java.util.List<LinkGroup> _linksList = new java.util.ArrayList<LinkGroup>();

	protected final java.util.List<TextGroup> _textsList = new java.util.ArrayList<TextGroup>();
	
	protected String _placeholderForNull  = ("");
	
	protected String _placeholderForEmpty = ("");
	
	/**
	 * 
	 * @param links
	 * @return
	 */
    public boolean addLinkGroup(LinkGroup links) {
        return _linksList.add(links);
    }

    /**
     * 
     * @param position
     * @param links
     */
    public void addLinkGroup(int position, LinkGroup links) {
    	_linksList.add(position, links);
    }
	
	/**
	 * 
	 * @param texts
	 * @return
	 */
    public boolean addTextGroup(TextGroup texts) {
        return _textsList.add(texts);
    }

    /**
     * 
     * @param position
     * @param texts
     */
    public void addTextGroup(int position, TextGroup texts) {
    	_textsList.add(position, texts);
    }

	/**
	 * 
	 * @param placeholder
	 */
	public void setPlaceholderForNull(final String placeholder) {
		_placeholderForNull = placeholder;
	}

	/**
	 * 
	 * @param placeholder
	 */
	public void setPlaceholderForEmpty(final String placeholder) {
		_placeholderForEmpty = placeholder;
	}
	
	// ListAdapter methods
	
	@Override
	public String getPlaceholderForNull() {
		return _placeholderForNull;
	}
	
	@Override
	public String getPlaceholderForEmpty() {
		return _placeholderForEmpty;
	}
	
	// DataSet methods

    @Override
    public boolean addItem(T v) {
        return _list.add(v);
    }

    @Override
    public void addItem(int p, T v) {
    	_list.add(p, v);
    }

    @Override
    public boolean addAllItems(Collection<? extends T> collection) {
        return _list.addAll(collection);
    }

    @Override
    public boolean addAllItems(int p, Collection<? extends T> collection) {
        return _list.addAll(p, collection);
    }

    @Override
    public void clear() {
    	_list.clear();
    }

    @Override
    public boolean contains(T t) {
        return _list.contains(t);
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        return _list.containsAll(collection);
    }

    @Override
    public T getItem(int p) {
        return _list.get(p);
    }

    @Override
    public int getPosition(T t) {
        return _list.indexOf(t);
    }

    @Override
    public boolean isEmpty() {
        return _list.isEmpty();
    }

    @Override
    public Iterator<T> iterator() {
        return _list.iterator();
    }

    @Override
    public T removeItem(int p) {
        return _list.remove(p);
    }

    @Override
    public T setItem(int p, T v) {
        return _list.set(p, v);
    }

    @Override
    public int getItemCount() {
        return _list.size();
    }

	// ListAdapter methods
    
	@Override
	public LinkGroup getLinkGroup(int position) {
		return _linksList.get(position);
	}
	
	@Override
	public java.util.List<LinkGroup> getAllLinkGroup() {
		return _linksList;
	}
    
	@Override
	public TextGroup getTextGroup(int position) {
		return _textsList.get(position);
	}
	
	@Override
	public java.util.List<TextGroup> getAllTextGroup() {
		return _textsList;
	}
}
