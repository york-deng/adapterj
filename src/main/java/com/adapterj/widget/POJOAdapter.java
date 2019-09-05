package com.adapterj.widget;

import java.util.Set;

import com.adapterj.annotation.POJO;

/**
 * 
 * @author York/GuangYu DENG
 */
@POJO(classId = "_pojo")
public interface POJOAdapter<T> extends Adapter {

	T getData();

	LinkGroup getLinkGroup();

	TextGroup getTextGroup();

	Set<String> idSetOfSelectOptions();
	
	SelectOptions getSelectOptions(String id);
	
	String getPlaceholderForNull();
	
	String getPlaceholderForEmpty();
	
}
