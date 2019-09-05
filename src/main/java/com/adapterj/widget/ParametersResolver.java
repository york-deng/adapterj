package com.adapterj.widget;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author York/GuangYu DENG
 */
public interface ParametersResolver<R, T> {

	T getParameter(final R request);
	
	List<T> getParametersAsList(final R request);

	Map<String, T> getParametersAsMap(final R request);

}
