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
	
	LinkGroup getLinkGroup(int position);
	
	java.util.List<LinkGroup> getAllLinkGroup();
	
	TextGroup getTextGroup(int position);
	
	java.util.List<TextGroup> getAllTextGroup();
	
	Set<String> idSetOfSelectOptions();
	
	SelectOptions getSelectOptions(String id);
	
	String getPlaceholderForNull();

	String getPlaceholderForEmpty();
	
}
