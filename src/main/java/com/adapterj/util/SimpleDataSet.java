package com.adapterj.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * 
 * @author York/GuangYu DENG
 */
public class SimpleDataSet<E> implements DataSet<E>, Serializable {

	private static final long serialVersionUID = 1042379999618574421L;
	
	private List<E> _list;

    public SimpleDataSet() {
        _list = new ArrayList<E>();
    }

    public SimpleDataSet(List<E> list) {
        _list = list;
    }

    public List<E> toList() {
    	return _list;
    }
    
    // DataSet methods
    
    @Override
    public boolean addItem(E e) {
        return (_list.add(e));
    }

    @Override
    public void addItem(int p, E e) {
        _list.add(p, e);
    }

    @Override
    public boolean addAllItems(Collection<? extends E> c) {
        return (_list.addAll(c));
    }

    @Override
    public boolean addAllItems(int p, Collection<? extends E> c) {
        return (_list.addAll(p, c));
    }

    @Override
    public void clear() {
        _list.clear();
    }

    @Override
    public boolean contains(E e) {
        return (_list.contains(e));
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return (_list.containsAll(c));
    }

    @Override
    public E getItem(int p) {
        return (_list.get(p));
    }

    @Override
    public int getPosition(E e) {
        return (_list.indexOf(e));
    }

    @Override
    public boolean isEmpty() {
        return (_list.isEmpty());
    }

    @Override
    public Iterator<E> iterator() {
        return (_list.iterator());
    }

    @Override
    public E removeItem(int p) {
        return (_list.remove(p));
    }

    @Override
    public E setItem(int p, E e) {
        return (_list.set(p, e));
    }

    @Override
    public int getItemCount() {
        return (_list.size());
    }
}
