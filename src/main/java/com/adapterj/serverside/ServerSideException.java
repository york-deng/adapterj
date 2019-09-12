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
package com.adapterj.serverside;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;

public class ServerSideException extends RuntimeException {

	private static final long serialVersionUID = -8612908013723250568L;

	/**
	 * Constructs a ServerSideException with the specified detail message.
	 * 
	 * @param error the specified detail message.
	 */
	public ServerSideException(String error) {
		super(error);
	}
	
	/**
	 * Constructs a ServerSideException with the specified detail message and a UnsupportedEncodingException instance.
	 * 
	 * @param error the specified detail message.
	 * @param thrown a UnsupportedEncodingException instance.
	 */
	public ServerSideException(String error, UnsupportedEncodingException thrown) {
		super(error, thrown);
	}
	
	/**
	 * Constructs a ServerSideException with a UnsupportedEncodingException instance.
	 * 
	 * @param thrown a UnsupportedEncodingException instance.
	 */
	public ServerSideException(UnsupportedEncodingException thrown) {
		super(thrown);
	}
	
	/**
	 * Constructs a ServerSideException with the specified detail message and a UnsupportedEncodingException instance.
	 * 
	 * @param error the specified detail message.
	 * @param thrown a UnsupportedEncodingException instance.
	 */
	public ServerSideException(String error, NullPointerException thrown) {
		super(error, thrown);
	}
	
	/**
	 * Constructs a NotImplementedException with a NullPointerException instance.
	 * 
	 * @param thrown a NullPointerException instance.
	 */
	public ServerSideException(NullPointerException thrown) {
		super(thrown);
	}
	
	/**
	 * Constructs a ServerSideException with the specified detail message and a IllegalArgumentException instance.
	 * 
	 * @param error the specified detail message.
	 * @param thrown a IllegalArgumentException instance.
	 */
	public ServerSideException(String error, IllegalArgumentException thrown) {
		super(error, thrown);
	}
	
	/**
	 * Constructs a NotImplementedException with a IllegalArgumentException instance.
	 * 
	 * @param thrown a IllegalArgumentException instance.
	 */
	public ServerSideException(IllegalArgumentException thrown) {
		super(thrown);
	}
	
	/**
	 * Constructs a ServerSideException with the specified detail message and a ClassNotFoundException instance.
	 * 
	 * @param error the specified detail message.
	 * @param thrown a ClassNotFoundException instance.
	 */
	public ServerSideException(String error, ClassNotFoundException thrown) {
		super(thrown);
	}
	
	/**
	 * Constructs a NotImplementedException with a ClassNotFoundException instance.
	 * 
	 * @param thrown a ClassNotFoundException instance.
	 */
	public ServerSideException(ClassNotFoundException thrown) {
		super(thrown);
	}
	
	/**
	 * Constructs a ServerSideException with the specified detail message and a InstantiationException instance.
	 * 
	 * @param error the specified detail message.
	 * @param thrown a InstantiationException instance.
	 */
	public ServerSideException(String error, InstantiationException thrown) {
		super(thrown);
	}
	
	/**
	 * Constructs a NotImplementedException with a InstantiationException instance.
	 * 
	 * @param thrown a InstantiationException instance.
	 */
	public ServerSideException(InstantiationException thrown) {
		super(thrown);
	}
	
	/**
	 * Constructs a ServerSideException with the specified detail message and a IllegalAccessException instance.
	 * 
	 * @param error the specified detail message.
	 * @param thrown a IllegalAccessException instance.
	 */
	public ServerSideException(String error, IllegalAccessException thrown) {
		super(thrown);
	}
	
	/**
	 * Constructs a NotImplementedException with a IllegalAccessException instance.
	 * 
	 * @param thrown a IllegalAccessException instance.
	 */
	public ServerSideException(IllegalAccessException thrown) {
		super(thrown);
	}
	
	/**
	 * Constructs a ServerSideException with the specified detail message and a InvocationTargetException instance.
	 * 
	 * @param error the specified detail message.
	 * @param thrown a InvocationTargetException instance.
	 */
	public ServerSideException(String error, InvocationTargetException thrown) {
		super(thrown);
	}
	
	/**
	 * Constructs a NotImplementedException with a InvocationTargetException instance.
	 * 
	 * @param thrown a InvocationTargetException instance.
	 */
	public ServerSideException(InvocationTargetException thrown) {
		super(thrown);
	}

	
	/**
	 * Constructs a ServerSideException with the specified detail message and a Throwable instance.
	 * 
	 * @param error the specified detail message.
	 * @param thrown a Throwable instance.
	 */
	public ServerSideException(String error, Throwable thrown) {
		super(thrown);
	}
	
	/**
	 * Constructs a NotImplementedException with a Throwable instance.
	 * 
	 * @param thrown a Throwable instance.
	 */
	public ServerSideException(Throwable thrown) {
		super(thrown);
	}
}
