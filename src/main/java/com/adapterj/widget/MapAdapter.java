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

import java.util.Set;

import com.adapterj.annotation.ID;

/**
 * 
 * @author York/GuangYu DENG
 */
@ID(identity = "_map")
public interface MapAdapter extends Adapter {

	Set<String> idSetOfValue();
	
	Object getValue(String id);

	Set<String> idSetOfForm();
	
	String getFormAction(String id);
	
	Set<String> idSetOfAnchor();
	
	Anchor getAnchor(String id);
	
	Set<String> idSetOfText();
	
	Text getText(String id);
	
	Set<String> idSetOfSelectOptions();
	
	SelectOptions getSelectOptions(String id);
	
	String getPlaceholderForNull();
	
}
