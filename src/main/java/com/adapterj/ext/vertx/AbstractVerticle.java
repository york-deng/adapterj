package com.adapterj.ext.vertx;

import java.util.Properties;

import io.vertx.core.Context;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerRequest;

import com.adapterj.serverside.ServerSideContext;

/**
 * 
 * 
 * @author York/GuangYu DENG
 */
public abstract class AbstractVerticle extends io.vertx.core.AbstractVerticle implements ServerSideContext {

    private final Properties _properties = new Properties();

    private String _propertiesFile = "/http.properties";
	
	// public methods

	/**
	 * 
	 * @param httpRequest
	 * @return
	 */
	public final String getBaseURI(final HttpServerRequest httpRequest) {
		final String host = getHost();
		final int port = getPort();
		final boolean eighty = (80 == port);
		
		final String scheme = httpRequest.scheme();
		final String baseURI = new StringBuilder().append(scheme).append("://").append(host).append(eighty ? "" : ':').append(eighty ? "" : port).append('/').toString();

		return (baseURI);
	}

    // AbstractVerticle methods
    
	@Override
	public void init(Vertx vertx, Context context) {
		super.init(vertx, context);
		
		try {
			_properties.load(AbstractVerticle.class.getResourceAsStream(_propertiesFile));
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	// ServerSideContext methods
	
	@Override
	public final String getHost() {
		try {
			return _properties.getProperty("host");
		} catch (Throwable ignore) {
			return ("localhost");
		}
	}
	
	@Override
	public final int getPort() {
		try {
			return Integer.parseInt(_properties.getProperty("port"));
		} catch (Throwable ignore) {
			return (80);
		}
	}
}
