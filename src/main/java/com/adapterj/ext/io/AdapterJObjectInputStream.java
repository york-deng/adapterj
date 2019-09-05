package com.adapterj.ext.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;

public class AdapterJObjectInputStream extends ObjectInputStream {

	protected ClassLoader _classLoader = getClass().getClassLoader();

	public AdapterJObjectInputStream(InputStream in) throws IOException {
		super(in);
	}

	public AdapterJObjectInputStream(InputStream in, ClassLoader classLoader) throws IOException {
		super(in);
		
		_classLoader = classLoader;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.io.ObjectInputStream#resolveClass(java.io.ObjectStreamClass)
	 */
	@Override
	protected Class<?> resolveClass(ObjectStreamClass desc) throws IOException, ClassNotFoundException {
		final String className = desc.getName();
		try {
			return Class.forName(className, false, _classLoader);
		} catch (ClassNotFoundException e) {
			return super.resolveClass(desc);
		}
	}
}
