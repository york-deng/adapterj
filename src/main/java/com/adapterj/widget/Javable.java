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

public interface Javable {

	// Such as:
	// <!--<![CDATA[JAVA[ String className = Object.class.getName(); ]]]>--> 
	// or
	// <!--<![CDATA[JAVA[= Object.class.getName() ]]]>-->
	
	String TAG_STARTS = "<!--<![CDATA[JAVA[";

	String TAG_ASSIGN = "<!--<![CDATA[JAVA[=";

	String TAG_ENDS = "]]]>-->";
	
}
