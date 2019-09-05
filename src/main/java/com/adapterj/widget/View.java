package com.adapterj.widget;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 
 * 支持两种绑定模式，HTML绑定、JavaScript绑定。
 * HTML绑定，默认就有加速器。用户可以自己定义加速器，以增加更复杂的处理逻辑。
 * 
 * @author York/GuangYu DENG
 */
public interface View {

	int SERVER_SIDE_BINDING  = 1;

	int BROWSER_SIDE_BINDING = 2;

	void loadHTMLResource(File file, String charset) throws IOException;

	void loadHTMLResource(URL url, String charset) throws MalformedURLException, IOException;

	void loadHTMLString(String html);

	void addExternalScript(String uri);
	
	void putAdapter(String id, Adapter adapter);

	void bindAll(int bindType) throws BindException;
	
	String getAcceleratorClassName();
	
	String toHTMLString();
	
}
