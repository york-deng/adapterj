package com.adapterj.ext.servlet;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.adapterj.widget.ParametersResolver;

/**
 * 
 * @author York/GuangYu DENG
 */
public interface HttpParametersResolver<T> extends ParametersResolver<HttpServletRequest, T> {

	T getParameter(final HttpServletRequest httpRequest);

	T getParameter(final HttpServletRequest httpRequest, String charset);
	
	List<T> getParametersAsList(final HttpServletRequest httpRequest);
	
	List<T> getParametersAsList(final HttpServletRequest httpRequest, String charset);

	Map<String, T> getParametersAsMap(final HttpServletRequest httpRequest);

	Map<String, T> getParametersAsMap(final HttpServletRequest httpRequest, String charset);

}
