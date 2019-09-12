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

import java.util.Collection;
import java.util.Iterator;

import com.adapterj.annotation.List;
import com.adapterj.util.DataSet;

@List(classId = "_list", entryId = "entry", indexId = "i")
public abstract class AbstractListAdapter<T> implements ListAdapter<T>, DataSet<T> {

	private static final long serialVersionUID = 5651619575362600871L;

	protected final java.util.List<T> _list = new java.util.ArrayList<T>();

	protected final java.util.List<AnchorGroup> _anchorsList = new java.util.ArrayList<AnchorGroup>();

	protected final java.util.List<TextGroup> _textsList = new java.util.ArrayList<TextGroup>();
	
	protected String _placeholderForNull  = ("");
	
	protected String _placeholderForEmpty = ("");
	
	/**
	 * Add an anchor group for this adapter
	 * 
	 * @param anchors the anchor group
	 * @return true if success
	 */
    public boolean addAnchorGroup(AnchorGroup anchors) {
        return _anchorsList.add(anchors);
    }

    /**
     * Add an anchor group for this adapter
     * 
     * @param position the position
     * @param anchors the anchor group
     */
    public void addAnchorGroup(int position, AnchorGroup anchors) {
    	_anchorsList.add(position, anchors);
    }
	
	/**
	 * Add a text group for this adapter
	 * 
	 * @param texts the text group
	 * @return true if success
	 */
    public boolean addTextGroup(TextGroup texts) {
        return _textsList.add(texts);
    }

    /**
     * Add a text group for this adapter
     * 
     * @param position the position
     * @param texts the text group
     */
    public void addTextGroup(int position, TextGroup texts) {
    	_textsList.add(position, texts);
    }

	/**
	 * Sets a placeholder for null value
	 * 
	 * @param placeholder the placeholder
	 */
	public void setPlaceholderForNull(final String placeholder) {
		_placeholderForNull = placeholder;
	}

	/**
	 * Sets a placeholder for empty value
	 * 
	 * @param placeholder the placeholder
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
	public AnchorGroup getAnchorGroup(int position) {
		return _anchorsList.get(position);
	}
	
	@Override
	public java.util.List<AnchorGroup> getAllAnchorGroup() {
		return _anchorsList;
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
