package com.adapterj.util;

import java.util.Collection;
import java.util.Iterator;

/**
 * 
 * @author York/GuangYu DENG
 */
public interface DataSet<E> {

    /**
     *
     * @param e The element
     * @return
     */
    boolean addItem(E e);

    /**
     *
     * @param p The position
     * @param e The element
     */
    void addItem(int p, E e);

    /**
     *
     * @param c The collection
     * @return
     */
    boolean addAllItems(Collection<? extends E> c);

    /**
     *
     * @param p The position
     * @param c The collection
     * @return
     */
    boolean addAllItems(int p, Collection<? extends E> c);

    /**
     * Removes all of the elements from this data set.
     */
    void clear();

    /**
     *
     * @param e The element
     * @return
     */
    boolean contains(E e);

    /**
     *
     * @param c The collection
     * @return
     */
    boolean containsAll(Collection<?> c);

    /**
     *
     * @param p The position
     * @return
     */
    E getItem(int p);

    /**
     *
     * @param e The element
     * @return
     */
    int getPosition(E e);

    /**
     *
     * @return Returns true if this data set contains no elements.
     */
    boolean isEmpty();

    /**
     *
     * @return
     */
    Iterator<E> iterator();

    /**
     *
     * @param p The position
     * @return
     */
    E removeItem(int p);

//	public boolean removeItem(Object o);

//	public boolean removeAllItem(Collection<?> c);

//	public boolean retainAllItem(Collection<?> c);

    /**
     *
     * @param p The position
     * @param e The element
     * @return
     */
    E setItem(int p, E e);

    /**
     *
     * @return Returns the number of elements in this data set.
     */
    int getItemCount();

}
