package com.adapterj.registry;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 
 * @author York/GuangYu DENG
 */
public class SimpleRegistry implements Registry {

	protected static final String DEFAULT_PROPERTIES = "/adpj.properties";
    
	protected static final String POJO_PREFIX = "pojo";

	protected static final String INDENTITY_POSTFIX = "identity";
	
	protected static final String CLASS_POSTFIX = "class";
	
	protected static final String VIEW_PREFIX = "view";

	protected static final String TEMPLATE_FILE_POSTFIX = "templateFile";
	
	protected static final String ACCELERATOR_CALSS_POSTFIX = "acceleratorCalss";

    private final Properties _prop = new Properties();

    private final Map<String, String> _pojo = new ConcurrentHashMap<>();

    private final Map<String, String> _view = new ConcurrentHashMap<>();

    /**
     * 
     * @throws RegistryException
     */
	protected final void init() throws RegistryException {
		if (_prop.isEmpty()) {
			try {
				_prop.load(SimpleRegistry.class.getResourceAsStream(DEFAULT_PROPERTIES));
			} catch (IOException e) {
				throw new RegistryException(e);
			}
		}
		
		final StringBuffer s = new StringBuffer();
		final Set<String> names = _prop.stringPropertyNames();
		for (final String name : names) {
			if (name.startsWith(POJO_PREFIX) && name.endsWith(INDENTITY_POSTFIX)) {
				final int p = name.indexOf('.');
				if (p > 0) {
					final String nameValue = _prop.getProperty(name);
					
					s.delete(0, s.length());
					final String propertyValue = _prop.getProperty(s.append(name.substring(0, p)).append('.').append(CLASS_POSTFIX).toString());
					
					_pojo.put(nameValue, propertyValue);
				}
			} else if (name.startsWith(VIEW_PREFIX) && name.endsWith(TEMPLATE_FILE_POSTFIX)) {
				final int p = name.lastIndexOf('.');
				if (p > 0) {
					final String nameValue = _prop.getProperty(name);
					
					s.delete(0, s.length());
					final String propertyValue = _prop.getProperty(s.append(name.substring(0, p)).append('.').append(ACCELERATOR_CALSS_POSTFIX).toString());
					
					_view.put(nameValue, propertyValue);
				}
			}
		}
	}
	
    /**
     * 
     * @throws RegistryException
     */
	public final void load() throws RegistryException {
		try {
			_prop.load(SimpleRegistry.class.getResourceAsStream(DEFAULT_PROPERTIES));
		} catch (IOException e) {
			throw new RegistryException(e);
		} catch (Throwable e) {
			throw new RegistryException(e);
		}
		
		init();
	}

    /**
     * 
     * @param file
     * @throws RegistryException
     */
	public final void load(final String file) throws RegistryException {
		try {
			_prop.load(SimpleRegistry.class.getResourceAsStream(file));
		} catch (IOException e) {
			throw new RegistryException(e);
		} catch (Throwable e) {
			throw new RegistryException(e);
		}
		
		init();
	}

    /**
     * 
     * @param file
     * @throws RegistryException
     */
	public final void load(final File file) throws RegistryException {
		try {
			_prop.load(new FileInputStream(file));
		} catch (IOException e) {
			throw new RegistryException(e);
		} catch (Throwable e) {
			throw new RegistryException(e);
		}
		
		init();
	}

	// Registry methods
	
	/**
	 * 
	 * @param identity
	 * @return
	 */
	public final String getPOJOClassName(String identity) {
		if (_pojo.containsKey(identity)) {
			return _pojo.get(identity);
		} else {
			return (null);
		}
	}
	
	/**
	 * 
	 * @param identity
	 * @param defaultValue
	 * @return
	 */
	public final String getPOJOClass(String identity, String defaultValue) {
		if (_pojo.containsKey(identity)) {
			final String value = _pojo.get(identity);
			return value != null ? value : defaultValue;
		} else {
			return (defaultValue);
		}
	}
	
	/**
	 * 
	 * @param templateFile
	 * @return
	 */
	public final String getAcceleratorClassName(String templateFile) {
		if (_view.containsKey(templateFile)) {
			return _view.get(templateFile);
		} else {
			return (null);
		}
	}

	/**
	 * 
	 * @param templateFile
	 * @param defaultValue
	 * @return
	 */
	public final String getAcceleratorCalss(String templateFile, String defaultValue) {
		if (_view.containsKey(templateFile)) {
			final String value = _view.get(templateFile);
			return value != null ? value : defaultValue;
		} else {
			return (defaultValue);
		}
	}

	// Hashtable methods
	
	/**
	 * 
	 * @return
	 */
	public final boolean isEmpty() {
		return _pojo.isEmpty() && _view.isEmpty();
	}

	// Properties methods

	/**
	 * 
	 * @param in
	 * @throws IOException
	 */
	public final void load(final InputStream in) throws RegistryException {
		try {
			_prop.load(in);
		} catch (IOException e) {
			throw new RegistryException(e);
		} catch (Throwable e) {
			throw new RegistryException(e);
		}
		
		init();
	}
	
	/**
	 * 
	 * @param reader
	 * @throws RegistryException
	 */
	public void load(final Reader reader) throws RegistryException {
		try {
			_prop.load(reader);
		} catch (IOException e) {
			throw new RegistryException(e);
		} catch (Throwable e) {
			throw new RegistryException(e);
		}
		
		init();
	}
	
	// For test
	
	public static void main(String[] args) {
		try {
			final File file = new File("/Users/york/Documents/Komodo/AdapterJVertxExample/target/classes/adpj.properties");
			final Registry registry = RegistryFactory.getRegistry(file);
			
			System.out.println("-----分割线-----");
			
			final String clazz1 = registry.getPOJOClassName("version");
			System.out.println(clazz1);
			
			final String clazz2 = registry.getPOJOClassName("source");
			System.out.println(clazz2);
			
			final String clazz3 = registry.getAcceleratorClassName("/simplelist.html");
			System.out.println(clazz3);
			
			final String clazz4 = registry.getAcceleratorClassName("/simpleform.html");
			System.out.println(clazz4);
			
			final String clazz5 = registry.getAcceleratorClassName("/simpleview.html");
			System.out.println(clazz5);
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
}
