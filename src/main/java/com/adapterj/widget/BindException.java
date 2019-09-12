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

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;

/**
 * 
 * @author York/GuangYu DENG
 */
public class BindException extends RuntimeException {

	private static final long serialVersionUID = -892680949218903822L;

	/**
	 * Constructs a BindException with the specified detail message.
	 * 
	 * @param error the specified detail message.
	 */
	public BindException(String error) {
		super(error);
	}

	/**
	 * Constructs a BindException with the specified detail message and a MalformedURLException instance.
	 * 
	 * @param error the specified detail message.
	 * @param thrown a MalformedURLException instance.
	 */
	public BindException(String error, MalformedURLException thrown) {
		super(error, thrown);
	}

	/**
	 * Constructs a BindException with a MalformedURLException instance.
	 * 
	 * @param thrown a MalformedURLException instance.
	 */
	public BindException(MalformedURLException thrown) {
		super(thrown);
	}

	/**
	 * Constructs a BindException with the specified detail message and a IOException instance.
	 * 
	 * @param error the specified detail message.
	 * @param thrown a IOException instance.
	 */
	public BindException(String error, IOException thrown) {
		super(error, thrown);
	}

	/**
	 * Constructs a BindException with a IOException instance.
	 * 
	 * @param thrown a IOException instance.
	 */
	public BindException(IOException thrown) {
		super(thrown);
	}

	/**
	 * Constructs a BindException with the specified detail message and a UnsupportedEncodingException instance.
	 * 
	 * @param error the specified detail message.
	 * @param thrown a UnsupportedEncodingException instance.
	 */
	public BindException(String error, UnsupportedEncodingException thrown) {
		super(error, thrown);
	}

	/**
	 * Constructs a BindException with a UnsupportedEncodingException instance.
	 * 
	 * @param thrown a UnsupportedEncodingException instance.
	 */
	public BindException(UnsupportedEncodingException thrown) {
		super(thrown);
	}

	/**
	 * Constructs a BindException with the specified detail message and a NullPointerException instance.
	 * 
	 * @param error the specified detail message.
	 * @param thrown a NullPointerException instance.
	 */
	public BindException(String error, NullPointerException thrown) {
		super(error, thrown);
	}
	
	/**
	 * Constructs a BindException with a NullPointerException instance.
	 * 
	 * @param thrown a NullPointerException instance.
	 */
	public BindException(NullPointerException thrown) {
		super(thrown);
	}

	/**
	 * Constructs a BindException with the specified detail message and a IllegalArgumentException instance.
	 * 
	 * @param error the specified detail message.
	 * @param thrown a IllegalArgumentException instance.
	 */
	public BindException(String error, IllegalArgumentException thrown) {
		super(error, thrown);
	}
	
	/**
	 * Constructs a BindException with a IllegalArgumentException instance.
	 * 
	 * @param thrown a IllegalArgumentException instance.
	 */
	public BindException(IllegalArgumentException thrown) {
		super(thrown);
	}

	/**
	 * Constructs a BindException with the specified detail message and a ClassNotFoundException instance.
	 * 
	 * @param error the specified detail message.
	 * @param thrown a ClassNotFoundException instance.
	 */
	public BindException(String error, ClassNotFoundException thrown) {
		super(thrown);
	}
	
	/**
	 * Constructs a BindException with a ClassNotFoundException instance.
	 * 
	 * @param thrown a ClassNotFoundException instance.
	 */
	public BindException(ClassNotFoundException thrown) {
		super(thrown);
	}

	/**
	 * Constructs a BindException with the specified detail message and a IllegalAccessException instance.
	 * 
	 * @param error the specified detail message.
	 * @param thrown a IllegalAccessException instance.
	 */
	public BindException(String error, IllegalAccessException thrown) {
		super(thrown);
	}
	
	/**
	 * Constructs a BindException with a IllegalAccessException instance.
	 * 
	 * @param thrown a IllegalAccessException instance.
	 */
	public BindException(IllegalAccessException thrown) {
		super(thrown);
	}

	/**
	 * Constructs a BindException with the specified detail message and a InvocationTargetException instance.
	 * 
	 * @param error the specified detail message.
	 * @param thrown a InvocationTargetException instance.
	 */
	public BindException(String error, InvocationTargetException thrown) {
		super(thrown);
	}
	
	/**
	 * Constructs a BindException with a InvocationTargetException instance.
	 * 
	 * @param thrown a InvocationTargetException instance.
	 */
	public BindException(InvocationTargetException thrown) {
		super(thrown);
	}
}
