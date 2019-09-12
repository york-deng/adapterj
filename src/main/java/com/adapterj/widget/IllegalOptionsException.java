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

public class IllegalOptionsException extends RuntimeException {

	private static final long serialVersionUID = -8287104049775006544L;

	/**
	 * Constructs a IllegalOptionsException with the specified detail message.
	 * 
	 * @param error the specified detail message.
	 */
	public IllegalOptionsException(String error) {
		super(error);
	}

	/**
	 * Constructs a IllegalOptionsException with the specified detail message and a NullPointerException instance.
	 * 
	 * @param error the specified detail message.
	 * @param thrown a NullPointerException instance.
	 */
	public IllegalOptionsException(String error, NullPointerException thrown) {
		super(error, thrown);
	}

	/**
	 * Constructs a IllegalOptionsException with a NullPointerException instance.
	 * 
	 * @param thrown a NullPointerException instance.
	 */
	public IllegalOptionsException(NullPointerException thrown) {
		super(thrown);
	}

	/**
	 * Constructs a IllegalOptionsException with the specified detail message and a IllegalArgumentException instance.
	 * 
	 * @param error the specified detail message.
	 * @param thrown a IllegalArgumentException instance.
	 */
	public IllegalOptionsException(String error, IllegalArgumentException thrown) {
		super(error, thrown);
	}

	/**
	 * Constructs a IllegalOptionsException with a IllegalArgumentException instance.
	 * 
	 * @param thrown a IllegalArgumentException instance.
	 */
	public IllegalOptionsException(IllegalArgumentException thrown) {
		super(thrown);
	}
}
