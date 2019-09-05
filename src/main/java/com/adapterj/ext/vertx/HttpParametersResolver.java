package com.adapterj.ext.vertx;

import java.util.List;
import java.util.Map;

import io.vertx.core.http.HttpServerRequest;

import com.adapterj.widget.ParametersResolver;

/**
 * 
 * @author York/GuangYu DENG
 */
public interface HttpParametersResolver<T> extends ParametersResolver<HttpServerRequest, T> {

	T getParameter(final HttpServerRequest httpRequest);

	T getParameter(final HttpServerRequest httpRequest, String charset);
	
	List<T> getParametersAsList(final HttpServerRequest httpRequest);
	
	List<T> getParametersAsList(final HttpServerRequest httpRequest, String charset);

	Map<String, T> getParametersAsMap(final HttpServerRequest httpRequest);

	Map<String, T> getParametersAsMap(final HttpServerRequest httpRequest, String charset);

}
