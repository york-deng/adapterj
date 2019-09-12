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

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public interface View {

	int SERVER_SIDE_BINDING  = 1;

	int BROWSER_SIDE_BINDING = 2;

	/**
	 * Load HTML template resource from the given template file.
	 * 
	 * @param file the given template file.
	 * @param charset the given charset.
	 * @throws IOException if it occurs.
	 */
	void loadHTMLResource(File file, String charset) throws IOException;

	/**
	 * Load HTML template resource from the given url.
	 * 
	 * @param url the template url
	 * @param charset the given charset
	 * @throws MalformedURLException if it occurs
	 * @throws IOException if it occurs
	 */
	void loadHTMLResource(URL url, String charset) throws MalformedURLException, IOException;

	/**
	 * Load HTML template resource from the given HTML string.
	 * 
	 * @param html the template HTML string
	 */
	void loadHTMLString(String html);
	
	/**
	 * Add a HTML meta tag into this HTML view
	 * 
	 * @param charset the charset attribute of the HTML meta tag
	 */
	void addMeta(String charset);
	
	/**
	 * Add a HTML meta tag into this HTML view
	 * 
	 * @param name the name attribute of the HTML meta tag
	 * @param content the content attribute of the HTML meta tag
	 */
	void addMeta(String name, String content);

	/**
	 * Add a HTML meta tag into this HTML view
	 * 
	 * @param httpEquiv the name attribute of the HTML meta tag. It is an enum/int type (such as 
	 * Attributes.HTTP_EQUIV_CONTENT_LANGUAGE, Attributes.HTTP_EQUIV_CONTENT_LANGUAGE, ...)
	 * @param content the content attribute of the HTML meta tag
	 */
	void addMeta(int httpEquiv, String content);

	/**
	 * Add a HTML script tag into this HTML view
	 * 
	 * @param uri the src attribute of the HTML script tag
	 */
	void addExternalScript(String uri);

	/**
	 * Add a HTML script tag into this HTML view
	 * 
	 * @param type the type attribute of the HTML script tag
	 * @param uri the src attribute of the HTML script tag
	 */
	void addExternalScript(String type, String uri);

	/**
	 * Add a HTML script tag into this HTML view
	 * 
	 * @param type the type attribute of the HTML script tag
	 * @param uri the src attribute of the HTML script tag
	 * @param async the async attribute of the HTML script tag
	 * @param defer the defer attribute of the HTML script tag
	 */
	void addExternalScript(String type, String uri, boolean async, String defer);

	/**
	 * Puts the adapter into View. The adapter will be bind with the first HTML element returned by Jsoup Document 
	 * method getElementById(String id).
	 * 
	 * @param id the adapter/element id 
	 * @param adapter the adapter instance
	 */
	void putAdapter(String id, Adapter adapter);

	/**
	 * Bind all adapter instance into this HTML view.
	 * 
	 * @param bindType the bind type (such as SERVER_SIDE_BINDING, BROWSER_SIDE_BINDING)
	 * @throws BindException if it occurs
	 */
	void bindAll(int bindType) throws BindException;
	
	/**
	 * Returns the class name of the ViewAccelerator class that service for this HTML view.
	 * 
	 * @return the class name of the ViewAccelerator class that service for this HTML view.
	 */
	String getAcceleratorClassName();
	
	/**
	 * Returns a HTML string representation of this HTML view.
	 * 
	 * @return a HTML string representation of this HTML view.
	 */
	String toHTMLString();
	
}
