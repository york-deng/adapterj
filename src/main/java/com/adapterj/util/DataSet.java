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
package com.adapterj.util;

import java.util.Collection;
import java.util.Iterator;

/**
 * The user of this interface has precise control over where in the dataset each element is inserted. The user can 
 * access elements by their integer index (position in the dataset), and search for elements in the dataset.
 * 
 * Unlike sets, datasets typically allow duplicate elements. More formally, datasets typically allow pairs of elements 
 * e1 and e2 such that e1.equals(e2), and they typically allow multiple null elements if they allow null elements at 
 * all. It is not inconceivable that someone might wish to implement a dataset that prohibits duplicates, by throwing 
 * runtime exceptions when the user attempts to insert them, but we expect this usage to be rare.
 */
public interface DataSet<E> {

    /**
     * Appends the specified element to the end of this dataset. 
     * @param e the element
     * @return true if this dataset changed as a result of the call
     */
    boolean addItem(E e);

    /**
     * Inserts the specified element at the specified position in this dataset. Shifts the element currently at that 
     * position (if any) and any subsequent elements to the right (adds one to their indices).
     * 
     * @param p the position
     * @param e the element
     */
    void addItem(int p, E e);

    /**
     * Appends all of the elements in the specified collection to the end of this dataset, in the order that they are 
     * returned by the specified collection's iterator. The behavior of this operation is undefined if the specified 
     * collection is modified while the operation is in progress.
     * 
     * @param c the collection
     * @return true if this dataset changed as a result of the call.
     */
    boolean addAllItems(Collection<? extends E> c);

    /**
     * Inserts all of the elements in the specified collection into this dataset at the specified position. Shifts the 
     * element currently at that position (if any) and any subsequent elements to the right (increases their indices). 
     * The new elements will appear in this dataset in the order that they are returned by the specified collection's 
     * iterator. The behavior of this operation is undefined if the specified collection is modified while the 
     * operation is in progress.
     * 
     * @param p the position
     * @param c the collection
     * @return true if this dataset changed as a result of the call.
     */
    boolean addAllItems(int p, Collection<? extends E> c);

    /**
     * Removes all of the elements from this data set.
     */
    void clear();

    /**
     * Returns true if this dataset contains the specified element. More formally, returns true if and only if this 
     * dataset contains at least one element e such that (o==null ? e==null : o.equals(e)).
     * 
     * @param e the element
     * @return true if this dataset contains the specified element.
     */
    boolean contains(E e);

    /**
     * Returns true if this dataset contains all of the elements of the specified collection.
     * 
     * @param c the collection
     * @return true if this dataset contains all of the elements in the specified collection.
     */
    boolean containsAll(Collection<?> c);

    /**
     * Returns the element at the specified position in this dataset.
     * 
     * @param p the position
     * @return the element at the specified position in this dataset.
     */
    E getItem(int p);

    /**
     * Returns the position of the first occurrence of the specified element in this dataset, or -1 if this dataset 
     * does not contain the element. More formally, returns the lowest position i such that (o==null ? get(i)==null
     *  : o.equals(get(i))), or -1 if there is no such position.
     *  
     * @param e the element
     * @return the position of the first occurrence of the specified element in this dataset, or -1 if this dataset 
     *         does not contain the element
     */
    int getPosition(E e);

    /**
     * Returns true if this data set contains no elements.
     * 
     * @return true if this data set contains no elements.
     */
    boolean isEmpty();

    /**
     * Returns an iterator over the elements in this dataset in proper sequence.
     * 
     * @return an iterator over the elements in this dataset in proper sequence.
     */
    Iterator<E> iterator();

    /**
     * Removes the element at the specified position in this dataset. Shifts any subsequent elements to the left 
     * (subtracts one from their indices). Returns the element that was removed from the dataset.
     * 
     * @param p the position
     * @return the element previously at the specified position.
     */
    E removeItem(int p);

//	public boolean removeItem(Object o);

//	public boolean removeAllItem(Collection<?> c);

//	public boolean retainAllItem(Collection<?> c);

    /**
     * Replaces the element at the specified position in this dataset with the specified element.
     * 
     * @param p the position
     * @param e the element
     * @return the element previously at the specified position.
     */
    E setItem(int p, E e);

    /**
     * Returns the number of elements in this dataset.
     * 
     * @return the number of elements in this dataset.
     */
    int getItemCount();

}
