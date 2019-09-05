package com.adapterj.test.widget;

import com.adapterj.test.Testable;
import com.adapterj.widget.SimpleHTMLViewAccelerator;

/**
 * 
 * @author York/GuangYu DENG
 */
class SimpleHTMLViewAcceleratorV4_1 extends SimpleHTMLViewAccelerator implements Testable {

	private static final long serialVersionUID = 7593164507465716607L;
	
	private String _test = "It's NOT working ...";
	
	// Testable methods
	
	@Override
	public void test(final String test) {
		_test = test;
	}
	
	@Override
	public String test() {
		return _test;
	}
}