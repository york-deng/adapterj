package com.adapterj.widget;

import java.io.Serializable;

public interface ViewAccelerator extends Serializable {
	
	/**
	 * 
	 * @param id
	 * @param adapter
	 */
	void putAdapter(String id, Adapter adapter);

}
