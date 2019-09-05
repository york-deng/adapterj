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
	
	Set<String> idSetOfLink();
	
	Link getLink(String id);
	
	Set<String> idSetOfText();
	
	Text getText(String id);
	
	Set<String> idSetOfSelectOptions();
	
	SelectOptions getSelectOptions(String id);
	
	String getPlaceholderForNull();
	
}
