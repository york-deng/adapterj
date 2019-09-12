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

import java.io.Serializable;

public interface ViewAccelerator extends Serializable {
	
	/**
	 * Puts the adapter into ViewAccelerator. The adapter will be bind with the first HTML element returned by Jsoup 
	 * Document method getElementById(String id).
	 * 
	 * @param id the adapter/element id 
	 * @param adapter the adapter instance
	 */
	void putAdapter(String id, Adapter adapter);

}
