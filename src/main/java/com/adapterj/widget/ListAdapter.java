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

import com.adapterj.annotation.List;

/**
 * 
 * @author York/GuangYu DENG
 */
@List(classId = "_list", entryId = "entry", indexId = "i")
public interface ListAdapter<T> extends Adapter {

	int getItemCount();
	
	long getItemId(int position);
	
	T getItem(int position);
	
	java.util.List<T> getAllItems();
	
	AnchorGroup getAnchorGroup(int position);
	
	java.util.List<AnchorGroup> getAllAnchorGroup();
	
	TextGroup getTextGroup(int position);
	
	java.util.List<TextGroup> getAllTextGroup();
	
	Set<String> idSetOfSelectOptions();
	
	SelectOptions getSelectOptions(String id);
	
	String getPlaceholderForNull();

	String getPlaceholderForEmpty();
	
}
