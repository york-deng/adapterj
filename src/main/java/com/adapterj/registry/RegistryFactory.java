package com.adapterj.registry;

import java.io.File;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 
 * @author York/GuangYu DENG
 */
public class RegistryFactory {

	private static final String defaultProperties = SimpleRegistry.DEFAULT_PROPERTIES;
	
	private static final Map<String, Registry> registries = new ConcurrentHashMap<String, Registry>();

	/**
	 * 
	 * @return A Registry instance (default)
	 */
	public static final Registry getRegistry() throws RegistryException {
		if (!registries.containsKey(defaultProperties)) {
			try {
				final SimpleRegistry defaultRegistry = new SimpleRegistry();
				defaultRegistry.load(defaultProperties);
				
				registries.put(defaultProperties, defaultRegistry);
			} catch (RegistryException e) {
				throw e;
			} catch (Throwable e) {
				throw new RegistryException(e);
			}
		}
		return registries.get(defaultProperties);
	}
	
	/**
	 * 
	 * @param file - The Registry properties file 
	 * @return A Registry instance 
	 */
	public static final Registry getRegistry(final String file) throws RegistryException {
		if (!registries.containsKey(file)) {
			try {
				final SimpleRegistry registry = new SimpleRegistry();
				registry.load(file);
				
				registries.put(file, registry);
			} catch (RegistryException e) {
				throw e;
			} catch (Throwable e) {
				throw new RegistryException(e);
			}
		}
		return registries.get(file);
	}
	
	/**
	 * 
	 * @param file - The Registry properties file 
	 * @return A Registry instance 
	 */
	public static final Registry getRegistry(final File file) throws RegistryException {
		final String path = file.getAbsolutePath();
		if (!registries.containsKey(path)) {
			try {
				final SimpleRegistry registry = new SimpleRegistry();
				registry.load(file);
				
				registries.put(path, registry);
			} catch (Throwable e) {
				throw new RegistryException(e);
			}
		}
		return registries.get(path);
	}
}
