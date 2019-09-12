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
package com.adapterj.annotation;

import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Inherited;

/**
 * 
 * @author York/GuangYu DENG
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Map {

	/**
	 * Returns the class id of the Map class
	 * @return the class id of the Map class, such as "_map"
	 */
	public String classId() default "_map";

	/**
	 * Returns the entry id of the Map class
	 * @return the entry id of the Map class, such as: "entry" 
	 */
	public String entryId() default "entry";

	/**
	 * Returns the key id of the Map class
	 * @return the key id of the Map class, such as: "k"
	 */
	public String keyId() default "k";

	/**
	 * Returns the value id of the Map class
	 * @return the value id of the Map class, such as: "v" 
	 */
	public String valueId() default "v";
				
}
