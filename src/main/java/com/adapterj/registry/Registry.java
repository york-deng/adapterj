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

/**
 * 
 * @author York/GuangYu DENG
 */
public interface Registry {

	/**
	 * Gets the class name of the POJO class from the registry by the identity.
	 * 
	 * @param identity the identity of the POJO class
	 * @return the class name of the POJO class
	 */
	String getPOJOClassName(String identity);

	/**
	 * Gets the class name of the POJO class from the registry by the identity.
	 * 
	 * @param identity the identity of the POJO class
	 * @param defaultValue the default class name
	 * @return the class name of the POJO class
	 */
	String getPOJOClassName(String identity, String defaultValue);
	
	/**
	 * Gets the class name of the ViewAccelerator class from the registry by the template file path.
	 * 
	 * @param templateFile the template file path of the ViewAccelerator class
	 * @return the class name of the ViewAccelerator class
	 */
	String getAcceleratorClassName(String templateFile);
	
	/**
	 * Gets the class name of the ViewAccelerator class from the registry by the template file path.
	 * 
	 * @param templateFile the template file path of the ViewAccelerator class
	 * @param defaultValue the default class name
	 * @return the class name of the ViewAccelerator class
	 */
	String getAcceleratorClassName(String templateFile, String defaultValue);
	
}
