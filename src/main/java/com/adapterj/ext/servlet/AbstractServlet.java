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
package com.adapterj.ext.servlet;

import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import com.adapterj.serverside.ServerSideContext;

public abstract class AbstractServlet extends HttpServlet implements ServerSideContext {

	private static final long serialVersionUID = -149068714872773711L;

    private final Properties _properties = new Properties();

    private String _propertiesFile = "/http.properties";
    
    private ServletContext _context;

	/**
	 * Returns the base uri by the given HttpServletRequest instance.
	 * 
	 * @param httpRequest the given HttpServletRequest instance.
	 * @return the base uri by the given HttpServletRequest instance.
	 */
	public final String getBaseURI(final HttpServletRequest httpRequest) {
		final String host = getHost();
		final int port = getPort();
		final boolean eighty = (80 == port);
		
		final String scheme = httpRequest.getScheme();
		final String contextPath = httpRequest.getContextPath();
		final String baseURI = new StringBuilder().append(scheme).append("://").append(host).append(eighty ? "" : ':').append(eighty ? "" : port).append(contextPath).append('/').toString();

		return (baseURI);
	}
	
    /**
     * Returns the ServletContext instance.
     * 
     * @return the ServletContext instance.
     */
	public final ServletContext getContext() {
		return _context;
	}

    // HttpServlet methods
    
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);

		try {
			_properties.load(AbstractServlet.class.getResourceAsStream(_propertiesFile));
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		_context = config.getServletContext();
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
