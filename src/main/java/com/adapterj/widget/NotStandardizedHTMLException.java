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

public class NotStandardizedHTMLException extends RuntimeException {

	private static final long serialVersionUID = 1064778620714645372L;

	/**
	 * Constructs a NotStandardizedHTMLException with the specified detail message.
	 * 
	 * @param error the specified detail message.
	 */
	public NotStandardizedHTMLException(String error) {
		super(error);
	}

	/**
	 * Constructs a NotStandardizedHTMLException with the specified detail message and a NullPointerException instance.
	 * 
	 * @param error the specified detail message.
	 * @param thrown a NullPointerException instance.
	 */
	public NotStandardizedHTMLException(String error, NullPointerException thrown) {
		super(error, thrown);
	}

	/**
	 * Constructs a NotStandardizedHTMLException with a NullPointerException instance.
	 * 
	 * @param thrown a NullPointerException instance.
	 */
	public NotStandardizedHTMLException(NullPointerException thrown) {
		super(thrown);
	}

	/**
	 * Constructs a NotStandardizedHTMLException with the specified detail message and a IllegalArgumentException instance.
	 * 
	 * @param error the specified detail message.
	 * @param thrown a IllegalArgumentException instance.
	 */
	public NotStandardizedHTMLException(String error, IllegalArgumentException thrown) {
		super(error, thrown);
	}

	/**
	 * Constructs a NotStandardizedHTMLException with a IllegalArgumentException instance.
	 * 
	 * @param thrown a IllegalArgumentException instance.
	 */
	public NotStandardizedHTMLException(IllegalArgumentException thrown) {
		super(thrown);
	}
}
