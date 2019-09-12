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
package com.adapterj.registry;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;

/**
 * 
 * @author York/GuangYu DENG
 */
public class RegistryException extends RuntimeException {

	private static final long serialVersionUID = -8612908013723250568L;

	/**
	 * Constructs a RegistryException with the specified detail message.
	 * 
	 * @param error the specified detail message.
	 */
	public RegistryException(String error) {
		super(error);
	}
	
	/**
	 * Constructs a RegistryException with the specified detail message and a UnsupportedEncodingException instance.
	 * 
	 * @param error the specified detail message.
	 * @param thrown a UnsupportedEncodingException instance.
	 */
	public RegistryException(String error, UnsupportedEncodingException thrown) {
		super(error, thrown);
	}
	
	/**
	 * Constructs a RegistryException with a UnsupportedEncodingException instance.
	 * 
	 * @param thrown a UnsupportedEncodingException instance.
	 */
	public RegistryException(UnsupportedEncodingException thrown) {
		super(thrown);
	}
	
	/**
	 * Constructs a RegistryException with the specified detail message and a NullPointerException instance.
	 * 
	 * @param error the specified detail message.
	 * @param thrown a NullPointerException instance.
	 */
	public RegistryException(String error, NullPointerException thrown) {
		super(error, thrown);
	}
	
	/**
	 * Constructs a RegistryException with a NullPointerException instance.
	 * 
	 * @param thrown a NullPointerException instance.
	 */
	public RegistryException(NullPointerException thrown) {
		super(thrown);
	}
	
	/**
	 * Constructs a RegistryException with the specified detail message and a IllegalArgumentException instance.
	 * 
	 * @param error the specified detail message.
	 * @param thrown a IllegalArgumentException instance.
	 */
	public RegistryException(String error, IllegalArgumentException thrown) {
		super(error, thrown);
	}
	
	/**
	 * Constructs a RegistryException with a IllegalArgumentException instance.
	 * 
	 * @param thrown a IllegalArgumentException instance.
	 */
	public RegistryException(IllegalArgumentException thrown) {
		super(thrown);
	}

	/**
	 * Constructs a RegistryException with the specified detail message and a IOException instance.
	 * 
	 * @param error the specified detail message.
	 * @param thrown a IOException instance.
	 */
	public RegistryException(String error, IOException thrown) {
		super(thrown);
	}

	/**
	 * Constructs a RegistryException with a IOException instance.
	 * 
	 * @param thrown a IOException instance.
	 */
	public RegistryException(IOException thrown) {
		super(thrown);
	}

	/**
	 * Constructs a RegistryException with the specified detail message and a FileNotFoundException instance.
	 * 
	 * @param error the specified detail message.
	 * @param thrown a FileNotFoundException instance.
	 */
	public RegistryException(String error, FileNotFoundException thrown) {
		super(thrown);
	}

	/**
	 * Constructs a RegistryException with a FileNotFoundException instance.
	 * 
	 * @param thrown a FileNotFoundException instance.
	 */
	public RegistryException(FileNotFoundException thrown) {
		super(thrown);
	}

	/**
	 * Constructs a RegistryException with the specified detail message and a ClassNotFoundException instance.
	 * 
	 * @param error the specified detail message.
	 * @param thrown a ClassNotFoundException instance.
	 */
	public RegistryException(String error, ClassNotFoundException thrown) {
		super(thrown);
	}

	/**
	 * Constructs a RegistryException with a ClassNotFoundException instance.
	 * 
	 * @param thrown a ClassNotFoundException instance.
	 */
	public RegistryException(ClassNotFoundException thrown) {
		super(thrown);
	}

	/**
	 * Constructs a RegistryException with the specified detail message and a InstantiationException instance.
	 * 
	 * @param error the specified detail message.
	 * @param thrown a InstantiationException instance.
	 */
	public RegistryException(String error, InstantiationException thrown) {
		super(thrown);
	}

	/**
	 * Constructs a RegistryException with a InstantiationException instance.
	 * 
	 * @param thrown a InstantiationException instance.
	 */
	public RegistryException(InstantiationException thrown) {
		super(thrown);
	}

	/**
	 * Constructs a RegistryException with the specified detail message and a IllegalAccessException instance.
	 * 
	 * @param error the specified detail message.
	 * @param thrown a IllegalAccessException instance.
	 */
	public RegistryException(String error, IllegalAccessException thrown) {
		super(thrown);
	}

	/**
	 * Constructs a RegistryException with a IllegalAccessException instance.
	 * 
	 * @param thrown a IllegalAccessException instance.
	 */
	public RegistryException(IllegalAccessException thrown) {
		super(thrown);
	}

	/**
	 * Constructs a RegistryException with the specified detail message and a InvocationTargetException instance.
	 * 
	 * @param error the specified detail message.
	 * @param thrown a InvocationTargetException instance.
	 */
	public RegistryException(String error, InvocationTargetException thrown) {
		super(thrown);
	}

	/**
	 * Constructs a RegistryException with a InvocationTargetException instance.
	 * 
	 * @param thrown a InvocationTargetException instance.
	 */
	public RegistryException(InvocationTargetException thrown) {
		super(thrown);
	}

	/**
	 * Constructs a RegistryException with the specified detail message and a Throwable instance.
	 * 
	 * @param error the specified detail message.
	 * @param thrown a Throwable instance.
	 */
	public RegistryException(String error, Throwable thrown) {
		super(thrown);
	}

	/**
	 * Constructs a RegistryException with a Throwable instance.
	 * 
	 * @param thrown a Throwable instance.
	 */
	public RegistryException(Throwable thrown) {
		super(thrown);
	}
}
