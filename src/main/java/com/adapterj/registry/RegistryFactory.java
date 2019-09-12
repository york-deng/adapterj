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
	 * Returns a Registry instance loaded registry entries from default properties file.
	 * 
	 * @return a Registry instance loaded registry entries from default properties file.
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
	 * Returns a Registry instance loaded registry entries from the given properties file.
	 * 
	 * @param file the path of given Registry properties file.
	 * @return a Registry instance loaded registry entries from the given properties file.
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
	 * Returns a Registry instance loaded registry entries from the given properties file.
	 * 
	 * @param file the given Registry properties file.
	 * @return a Registry instance loaded registry entries from the given properties file.
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
