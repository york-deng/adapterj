package com.adapterj.ext.vertx;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.vertx.core.MultiMap;
import io.vertx.core.http.HttpServerRequest;

import com.adapterj.annotation.SetMethod;
import com.adapterj.registry.Registry;
import com.adapterj.serverside.ServerSideException;

import com.adapterj.logging.Debugger;
import com.adapterj.logging.Log;

/**
 * 
 * @author York/GuangYu DENG
 */
@SuppressWarnings("rawtypes")
public class SimpleHttpParametersResolver<T> implements HttpParametersResolver<T> {
	
	private static final boolean DEBUG = !Debugger.DEBUG;
	
    private static final String TAG = SimpleHttpParametersResolver.class.getName();

    protected static final String charset = "utf-8";

    protected Registry _registry;

    protected String _charset;
    
	/**
	 * Basic Constructor.
	 */
	public SimpleHttpParametersResolver(final Registry registry) {
		_registry = registry;
		_charset = charset;
	}
	
	/**
	 * 
	 * @param pName
	 * @return
	 */
	protected final boolean isPlaintextParameter(final String pName) {
		final int p1 = pName.indexOf('.');
		return (p1 == -1);
	}
	
	/**
	 * 
	 * @param pName - Such as: product.name, order.price, product[0].name, order[1].price, ... 
	 * @return
	 */
	protected final String getInstanceIdFromParameterName(final String pName) {
		String instanceId = null;
		if (pName != null) {
			int p1 = pName.indexOf('.');
			if (p1 < 0) {
				p1 = pName.length();
			}
			instanceId = pName.substring(0, p1);
		}
		return instanceId;
	}

	/**
	 * 
	 * @param pName - Such as: product.name, order.price, product[0].name, order[1].price, ... 
	 * @return
	 */
	protected final String getClassIdFromParameterName(final String pName) {
		String classId = null;
		if (pName != null) {
			int p1 = pName.indexOf('[');
			if (p1 < 0) {
				p1 = pName.indexOf('.');
				if (p1 < 0) {
					p1 = pName.length();
				}
			}
			classId = pName.substring(0, p1);
		}
		return classId;
	}

	/**
	 * 
	 * @param instanceId - Such as: product, order, product[0], order[1], ... 
	 * @return
	 */
	public final String getClassIdFromInstanceId(final String instanceId) {
		String classId = null;
		if (instanceId != null) {
			int p1 = instanceId.indexOf('[');
			if (p1 < 0) {
				p1 = instanceId.length();
			}
			classId = instanceId.substring(0, p1);
		}
		return classId;
	}

	/**
	 * 
	 * @param pName - Such as: product.name, order.price, product[0].name, order[1].price, ... 
	 * @return
	 */
	protected final String getAttributeIdFromParameterName(final String pName) {
		String attributeId = null;
		if (pName != null) {
			int p1 = pName.indexOf('.');
			if (p1 < 0) {
				p1 = pName.length();
			}
			final String trail = p1 + 1 < pName.length() ? pName.substring(p1 + 1, pName.length()) : ("");
			if (!trail.isEmpty()) {
				int p2 = trail.indexOf('[');
				if (p2 < 0) {
					p2 = trail.indexOf('.');
					if (p2 < 0) {
						p2 = trail.length();
					}
				}
				attributeId = p2 > 0 ? trail.substring(0, p2) : ("");
			}
		}
		return attributeId;
	}

	// ParametersResolver methods

	@Override
	public T getParameter(final HttpServerRequest httpRequest) {
		return getParameter(httpRequest, charset);
	}
	
	@SuppressWarnings({ "unchecked" })
	@Override
	public T getParameter(final HttpServerRequest httpRequest, final String charset) {
//		// Sets character encoding first of all
//		try {
//			httpRequest.setCharacterEncoding(_charset = charset); // utf-8
//		} catch (UnsupportedEncodingException thrown) {
//            final StackTraceElement t = (new Throwable()).getStackTrace()[0];
//            final String f = "(%s:%d) %s: UnsupportedEncodingException: %s";
//            final String error = String.format(f, t.getFileName(), t.getLineNumber(), t.getMethodName(), charset);
//            if (DEBUG) Log.e(TAG, error, thrown);
//            throw new ServerSideException(error, thrown);
//		}

		if (httpRequest == null) {
            StackTraceElement t = (new Throwable()).getStackTrace()[0];
            String f = "(%s:%d) %s: The input parameter httpRequest is null. Return a null output parameter";
            final String error = String.format(f, t.getFileName(), t.getLineNumber(), t.getMethodName());
            if (DEBUG) Log.e(TAG, error);
			throw new ServerSideException(error);
		}

		// Define a parameter
		T instance = null;
		
		// Define  a map as <instanceId, <attributeId, attributeValue>>
		final Map<String, Map<String, String>> parameterMap = new HashMap<String, Map<String, String>>(); 
		
		// Gets http GET url parameters, or http POST form parameters
		MultiMap httpMap = httpRequest.params();
		//final Map<String, String[]> httpMap = httpRequest.getParameterMap();
		
		// Rebuild a map as <instanceId, <attributeId, attributeValue>> for init POJO instance 
		final Set<String> pSet = httpMap.names();
		final Iterator<String> iter_p = pSet.iterator();
		while (iter_p.hasNext()) {
			// Such as: product.name, order.price, product[0].name, order[1].price, ... 
			final String pName = iter_p.next();
			if (DEBUG) {
	            StackTraceElement t = (new Throwable()).getStackTrace()[0];
	            String f = "(%s:%d) %s: key is %s";
	            Log.i(TAG, String.format(f, t.getFileName(), t.getLineNumber(), t.getMethodName(), pName));
			}

			final String classId = getClassIdFromParameterName(pName);
			if (DEBUG) {
	            StackTraceElement t = (new Throwable()).getStackTrace()[0];
	            String f = "(%s:%d) %s: class id is %s";
	            Log.i(TAG, String.format(f, t.getFileName(), t.getLineNumber(), t.getMethodName(), classId));
			}
			
			if (classId != null) {
				final String attrId = getAttributeIdFromParameterName(pName);
				if (DEBUG) {
		            StackTraceElement t = (new Throwable()).getStackTrace()[0];
		            String f = "(%s:%d) %s: attr id is %s";
		            Log.i(TAG, String.format(f, t.getFileName(), t.getLineNumber(), t.getMethodName(), attrId));
				}
				
				final List<String> values = httpMap.getAll(pName);
				if (DEBUG) {
		            StackTraceElement t = (new Throwable()).getStackTrace()[0];
		            String f = "(%s:%d) %s: values is %s, values[0] is %s";
		            Log.i(TAG, String.format(f, t.getFileName(), t.getLineNumber(), t.getMethodName(), values, values != null ? values.get(0) : null));
				}
				
				if (values.size() > 0) {
					Map<String, String> valueMap = parameterMap.get(classId);
					if (valueMap == null) {
						valueMap = new HashMap<String, String>();
					}
					valueMap.put(attrId, values.get(0));
					parameterMap.put(classId, valueMap);
				}
			}
		}
		
		// Build POJO instance with the map as <instanceId, <attributeId, attributeValue>>
		final Set<String> idSet = parameterMap.keySet();
		final Iterator<String> iter_i = idSet.iterator();
		if (iter_i.hasNext()) { // Use while loop CAN handle multi-types parameters
			final String instanceId = iter_i.next();
			final String classId = instanceId;
			final String className = _registry.getPOJOClassName(instanceId);
			if (className == null) {
	            StackTraceElement t = (new Throwable()).getStackTrace()[0];
	            String f = "(%s:%d) %s: Unknown class id: \"%s\". You CAN check \"WEB-INF/classes/adpj.properties\" on server side for more information.";
	            String error = String.format(f, t.getFileName(), t.getLineNumber(), t.getMethodName(), classId);
	            if (DEBUG) Log.e(TAG, error);
	            throw new ServerSideException(error);
			}
			try {
				instance = (T) Class.forName(className).newInstance();
			} catch (ClassNotFoundException thrown) {
	            StackTraceElement t = (new Throwable()).getStackTrace()[0];
	            String f = "(%s:%d) %s: ClassNotFoundException: ";
	            final String error = String.format(f, t.getFileName(), t.getLineNumber(), t.getMethodName());
	            if (DEBUG) Log.e(TAG, error, thrown);
				throw new ServerSideException(error);
			} catch (InstantiationException thrown) {
	            StackTraceElement t = (new Throwable()).getStackTrace()[0];
	            String f = "(%s:%d) %s: InstantiationException: ";
	            final String error = String.format(f, t.getFileName(), t.getLineNumber(), t.getMethodName());
	            if (DEBUG) Log.e(TAG, error, thrown);
				throw new ServerSideException(error);
			} catch (IllegalAccessException thrown) {
	            StackTraceElement t = (new Throwable()).getStackTrace()[0];
	            String f = "(%s:%d) %s: IllegalAccessException: ";
	            final String error = String.format(f, t.getFileName(), t.getLineNumber(), t.getMethodName());
	            if (DEBUG) Log.e(TAG, error, thrown);
				throw new ServerSideException(error);
			} catch (Throwable thrown) {
	            StackTraceElement t = (new Throwable()).getStackTrace()[0];
	            String f = "(%s:%d) %s: Throwable: ";
	            final String error = String.format(f, t.getFileName(), t.getLineNumber(), t.getMethodName());
	            if (DEBUG) Log.e(TAG, error, thrown);
				throw new ServerSideException(error);
			}
			if (instance != null) {
				final Map<String, String> valueMap = parameterMap.get(classId);
				final Class classInstance = instance.getClass();
				final Method[] methods = classInstance.getMethods();
				for (final Method method : methods) {
					if (method.isAnnotationPresent(SetMethod.class)) {
						final SetMethod setMethod = method.getAnnotation(SetMethod.class);
						final String parameterId = setMethod.parameterId();
						final String parameterType = setMethod.parameterType();
						final String value = valueMap.get(parameterId);

						Object attribute = null;
						if ("String".equals(parameterType)) {
							attribute = value == null || value.isEmpty() ? null : value;
						} else if ("Boolean".equals(parameterType) || "boolean".equals(parameterType)) {
							attribute = value == null ? (Boolean) false : (Boolean) Boolean.parseBoolean(value);
						} else if ("Integer".equals(parameterType) || "int".equals(parameterType)) {
							try { attribute = value == null ? (Integer) 0 : (Integer) Integer.parseInt(value); } catch (NumberFormatException ignore) { };
						} else if ("Long".equals(parameterType) || "long".equals(parameterType)) {
							try { attribute = value == null ? (Long) 0L : (Long) Long.parseLong(value); } catch (NumberFormatException ignore) { };
						} else if ("Date".equals(parameterType)) {
							// 日期格式的解析 ...
						} else if (value != null) {
				            StackTraceElement t = (new Throwable()).getStackTrace()[0];
				            String f = "(%s:%d) %s: Unimplemented parameter type: %s";
				            final String error = String.format(f, t.getFileName(), t.getLineNumber(), t.getMethodName(), parameterType);
				            if (DEBUG) Log.e(TAG, error);
							throw new ServerSideException(error);
						} else {
							// do nothing
						}
						try {
							// Invoke setXXX method with the HTTP parameter value
							method.invoke(instance, attribute);
						} catch (IllegalAccessException thrown) {
							final StackTraceElement t = (new Throwable()).getStackTrace()[0];
				            final String f = "(%s:%d) %s: IllegalAccessException: ";
				            final String error = String.format(f, t.getFileName(), t.getLineNumber(), t.getMethodName());
				            if (DEBUG) Log.e(TAG, error, thrown);
							throw new ServerSideException(error);
						} catch (IllegalArgumentException thrown) {
							final StackTraceElement t = (new Throwable()).getStackTrace()[0];
				            final String f = "(%s:%d) %s: IllegalArgumentException: You CAN check %s class definitions for DEBUG it";
				            final String error = String.format(f, t.getFileName(), t.getLineNumber(), t.getMethodName(), className);
				            if (DEBUG) Log.e(TAG, error, thrown);
							throw new ServerSideException(error);
						} catch (InvocationTargetException thrown) {
							final StackTraceElement t = (new Throwable()).getStackTrace()[0];
							final String f = "(%s:%d) %s: InvocationTargetException: ";
				            final String error = String.format(f, t.getFileName(), t.getLineNumber(), t.getMethodName());
				            if (DEBUG) Log.e(TAG, error, thrown);
							throw new ServerSideException(error);
						} catch (Throwable thrown) {
							final StackTraceElement t = (new Throwable()).getStackTrace()[0];
							final String f = "(%s:%d) %s: Throwable: ";
				            final String error = String.format(f, t.getFileName(), t.getLineNumber(), t.getMethodName());
				            if (DEBUG) Log.e(TAG, error, thrown);
							throw new ServerSideException(error);
						}
					}
				}
			}
		}
		return (T) instance;
	}

	@Override
	public List<T> getParametersAsList(final HttpServerRequest httpRequest) {
		return getParametersAsList(httpRequest, charset);
	}
	
	@SuppressWarnings({ "unchecked" })
	@Override
	public List<T> getParametersAsList(final HttpServerRequest httpRequest, final String charset) {
//		// Sets character encoding first of all
//		try {
//			httpRequest.setCharacterEncoding(_charset = charset); // utf-8
//		} catch (UnsupportedEncodingException thrown) {
//            final StackTraceElement t = (new Throwable()).getStackTrace()[0];
//            final String f = "(%s:%d) %s: UnsupportedEncodingException: %s";
//            final String error = String.format(f, t.getFileName(), t.getLineNumber(), t.getMethodName(), charset);
//            if (DEBUG) Log.e(TAG, error, thrown);
//            throw new ServerSideException(error, thrown);
//		}

		if (httpRequest == null) {
            StackTraceElement t = (new Throwable()).getStackTrace()[0];
            String f = "(%s:%d) %s: The input parameter httpRequest is null. Return a null output parameter";
            final String error = String.format(f, t.getFileName(), t.getLineNumber(), t.getMethodName());
            if (DEBUG) Log.e(TAG, error);
			throw new ServerSideException(error);
		}

		// Define a return list
		final List<T> returnList = new ArrayList<T>();
		
		// Define a map as <instanceId, <attributeId, attributeValue>>
		final Map<String, Map<String, String>> parameterMap = new HashMap<String, Map<String, String>>(); 
		
		// Gets http GET url parameters, or http POST form parameters
		final MultiMap httpMap = httpRequest.params();
		
		// Rebuild a map as <instanceId, <attributeId, attributeValue>> for init POJO instance 
		final Set<String> pSet = httpMap.names();
		final Iterator<String> iter_p = pSet.iterator();
		while (iter_p.hasNext()) {
			// Such as: product.name, order.price, product[0].name, order[1].price, ... 
			final String pName = iter_p.next();
			if (DEBUG) {
	            StackTraceElement t = (new Throwable()).getStackTrace()[0];
	            String f = "(%s:%d) %s: key is %s";
	            Log.i(TAG, String.format(f, t.getFileName(), t.getLineNumber(), t.getMethodName(), pName));
			}

			// Such as: product, order, product[0], order[1], ... 
			final String instId = getInstanceIdFromParameterName(pName);
			if (DEBUG) {
	            StackTraceElement t = (new Throwable()).getStackTrace()[0];
	            String f = "(%s:%d) %s: item id is %s";
	            Log.i(TAG, String.format(f, t.getFileName(), t.getLineNumber(), t.getMethodName(), instId));
			}
			
			if (instId != null) {
				final String attrId = getAttributeIdFromParameterName(pName);
				if (DEBUG) {
		            StackTraceElement t = (new Throwable()).getStackTrace()[0];
		            String f = "(%s:%d) %s: attr id is %s";
		            Log.i(TAG, String.format(f, t.getFileName(), t.getLineNumber(), t.getMethodName(), attrId));
				}
				
				final List<String> values = httpMap.getAll(pName);
				if (DEBUG) {
		            StackTraceElement t = (new Throwable()).getStackTrace()[0];
		            String f = "(%s:%d) %s: values is %s";
		            Log.i(TAG, String.format(f, t.getFileName(), t.getLineNumber(), t.getMethodName(), values));
				}
				
				if (values.size() > 0) {
					Map<String, String> valueMap = parameterMap.get(instId);
					if (valueMap == null) {
						valueMap = new HashMap<String, String>();
					}
					valueMap.put(attrId, values.get(0));
					parameterMap.put(instId, valueMap);
				}
			}
		}
		
		// Build POJO instance with the map as <instanceId, <attributeId, attributeValue>>
		final Set<String> idSet = parameterMap.keySet();
		final Iterator<String> iter_i = idSet.iterator();
		while (iter_i.hasNext()) { // Use while loop CAN handle multi-types parameters
			final String instanceId = iter_i.next();
			final String classId = getClassIdFromInstanceId(instanceId);
			if (DEBUG) {
				final StackTraceElement t = (new Throwable()).getStackTrace()[0];
	            final String f = "(%s:%d) %s: class id is %s";
	            Log.e(TAG, String.format(f, t.getFileName(), t.getLineNumber(), t.getMethodName(), classId));
			}
			final String className = _registry.getPOJOClassName(classId);
			if (className == null) {
	            StackTraceElement t = (new Throwable()).getStackTrace()[0];
	            String f = "(%s:%d) %s: Unknown class id: \"%s\". You CAN check \"WEB-INF/classes/adpj.properties\" on server side for more information.";
	            String error = String.format(f, t.getFileName(), t.getLineNumber(), t.getMethodName(), classId);
	            if (DEBUG) Log.e(TAG, error);
	            throw new ServerSideException(error);
			}
			T instance = null;
			try {
				instance = (T) Class.forName(className).newInstance();
			} catch (ClassNotFoundException thrown) {
	            StackTraceElement t = (new Throwable()).getStackTrace()[0];
	            String f = "(%s:%d) %s: ClassNotFoundException: ";
	            final String error = String.format(f, t.getFileName(), t.getLineNumber(), t.getMethodName());
	            if (DEBUG) Log.e(TAG, error, thrown);
				throw new ServerSideException(error);
			} catch (InstantiationException thrown) {
	            StackTraceElement t = (new Throwable()).getStackTrace()[0];
	            String f = "(%s:%d) %s: InstantiationException: ";
	            final String error = String.format(f, t.getFileName(), t.getLineNumber(), t.getMethodName());
	            if (DEBUG) Log.e(TAG, error, thrown);
				throw new ServerSideException(error);
			} catch (IllegalAccessException thrown) {
	            StackTraceElement t = (new Throwable()).getStackTrace()[0];
	            String f = "(%s:%d) %s: IllegalAccessException: ";
	            final String error = String.format(f, t.getFileName(), t.getLineNumber(), t.getMethodName());
	            if (DEBUG) Log.e(TAG, error, thrown);
				throw new ServerSideException(error);
			} catch (Throwable thrown) {
	            StackTraceElement t = (new Throwable()).getStackTrace()[0];
	            String f = "(%s:%d) %s: Throwable: ";
	            final String error = String.format(f, t.getFileName(), t.getLineNumber(), t.getMethodName());
	            if (DEBUG) Log.e(TAG, error, thrown);
				throw new ServerSideException(error);
			}
			if (instance != null) {
				final Map<String, String> valueMap = parameterMap.get(classId);
				final Class classInstance = instance.getClass();
				final Method[] methods = classInstance.getMethods();
				for (final Method method : methods) {
					if (method.isAnnotationPresent(SetMethod.class)) {
						final SetMethod setMethod = method.getAnnotation(SetMethod.class);
						final String parameterId = setMethod.parameterId();
						final String parameterType = setMethod.parameterType();
						final String value = valueMap.get(parameterId);
						Object attribute = null;
						if ("String".equals(parameterType)) {
							attribute = value == null || value.isEmpty() ? null : value;
						} else if ("Boolean".equals(parameterType) || "boolean".equals(parameterType)) {
							attribute = value == null ? (Boolean) false : (Boolean) Boolean.parseBoolean(value);
						} else if ("Integer".equals(parameterType) || "int".equals(parameterType)) {
							try { attribute = value == null ? (Integer) 0 : (Integer) Integer.parseInt(value); } catch (NumberFormatException ignore) { };
						} else if ("Long".equals(parameterType) || "long".equals(parameterType)) {
							try { attribute = value == null ? (Long) 0L : (Long) Long.parseLong(value); } catch (NumberFormatException ignore) { };
						} else if ("Date".equals(parameterType)) {
							// 日期格式的匹配 ...
						} else if (value != null) {
				            StackTraceElement t = (new Throwable()).getStackTrace()[0];
				            String f = "(%s:%d) %s: Unimplemented parameter type: %s";
				            final String error = String.format(f, t.getFileName(), t.getLineNumber(), t.getMethodName(), parameterType);
				            if (DEBUG) Log.e(TAG, error);
							throw new ServerSideException(error);
						} else {
							// do nothing
						}
						try {
							// Invoke setXXX method with the HTTP parameter value
							method.invoke(instance, attribute);
						} catch (IllegalAccessException thrown) {
							final StackTraceElement t = (new Throwable()).getStackTrace()[0];
				            final String f = "(%s:%d) %s: IllegalAccessException: ";
				            final String error = String.format(f, t.getFileName(), t.getLineNumber(), t.getMethodName());
				            if (DEBUG) Log.e(TAG, error, thrown);
							throw new ServerSideException(error);
						} catch (IllegalArgumentException thrown) {
							final StackTraceElement t = (new Throwable()).getStackTrace()[0];
				            final String f = "(%s:%d) %s: IllegalArgumentException: You CAN check %s class definitions for DEBUG it";
				            final String error = String.format(f, t.getFileName(), t.getLineNumber(), t.getMethodName(), className);
				            if (DEBUG) Log.e(TAG, error, thrown);
							throw new ServerSideException(error);
						} catch (InvocationTargetException thrown) {
							final StackTraceElement t = (new Throwable()).getStackTrace()[0];
							final String f = "(%s:%d) %s: InvocationTargetException: ";
				            final String error = String.format(f, t.getFileName(), t.getLineNumber(), t.getMethodName());
				            if (DEBUG) Log.e(TAG, error, thrown);
							throw new ServerSideException(error);
						} catch (Throwable thrown) {
							final StackTraceElement t = (new Throwable()).getStackTrace()[0];
							final String f = "(%s:%d) %s: Throwable: ";
				            final String error = String.format(f, t.getFileName(), t.getLineNumber(), t.getMethodName());
				            if (DEBUG) Log.e(TAG, error, thrown);
							throw new ServerSideException(error);
						}
					}
				}
				returnList.add(instance);
			} // end if (instance != null) 
		} // end while loop
		return (returnList);
	}

	@Override
	public Map<String, T> getParametersAsMap(HttpServerRequest httpRequest) {
		return getParametersAsMap(httpRequest, charset);
	}
	
	@SuppressWarnings({ "unchecked" })
	@Override
	public Map<String, T> getParametersAsMap(HttpServerRequest httpRequest, final String charset) {
//		// Sets character encoding first of all
//		try {
//			httpRequest.setCharacterEncoding(_charset = charset); // utf-8
//		} catch (UnsupportedEncodingException thrown) {
//            final StackTraceElement t = (new Throwable()).getStackTrace()[0];
//            final String f = "(%s:%d) %s: UnsupportedEncodingException: %s";
//            final String error = String.format(f, t.getFileName(), t.getLineNumber(), t.getMethodName(), charset);
//            if (DEBUG) Log.e(TAG, error, thrown);
//            throw new ServerSideException(error, thrown);
//		}

		if (httpRequest == null) {
            StackTraceElement t = (new Throwable()).getStackTrace()[0];
            String f = "(%s:%d) %s: The input parameter httpRequest is null. Return a null output parameter";
            final String error = String.format(f, t.getFileName(), t.getLineNumber(), t.getMethodName());
            if (DEBUG) Log.e(TAG, error);
			throw new ServerSideException(error);
		}

		// Define a parameter
		final Map<String, T> returnMap = new HashMap<String, T>();
		
		// Define a map as <instanceId, <attributeId, attributeValue>>
		final Map<String, Map<String, String>> parameterMap = new HashMap<String, Map<String, String>>(); 

		// Gets http GET url parameters, or http POST form parameters
		final MultiMap httpMap = httpRequest.params();
		
		// Rebuild a map as <instanceId, <attributeId, attributeValue>> for init POJO instance 
		final Set<String> pSet = httpMap.names();
		final Iterator<String> iter_p = pSet.iterator();
		while (iter_p.hasNext()) {
			// Such as: product.name, order.price, product[0].name, order[1].price, ... 
			final String pName = iter_p.next();
			if (DEBUG) {
	            StackTraceElement t = (new Throwable()).getStackTrace()[0];
	            String f = "(%s:%d) %s: pName is \"%s\"";
	            Log.i(TAG, String.format(f, t.getFileName(), t.getLineNumber(), t.getMethodName(), pName));
			}
			
			final boolean plaintext = isPlaintextParameter(pName);
			if (plaintext) {
				if (DEBUG) {
		            StackTraceElement t = (new Throwable()).getStackTrace()[0];
		            String f = "(%s:%d) %s: a plain-text parameter: pName is \"%s\"";
		            Log.w(TAG, String.format(f, t.getFileName(), t.getLineNumber(), t.getMethodName(), pName));
				}
				final List<String> pValues = httpMap.getAll(pName);
				final Object instance = pValues.size() == 1 ? (Object) pValues.get(0) : (Object) pValues;
				try { 
					returnMap.put(pName, (T) instance);
				} catch (Throwable thrown) { 
		            StackTraceElement t = (new Throwable()).getStackTrace()[0];
		            String f = "(%s:%d) %s: error when converting an instance into T: pName is \"%s\"";
		            Log.w(TAG, String.format(f, t.getFileName(), t.getLineNumber(), t.getMethodName(), pName));
				}
			} else {
				// Such as: product, order, product[0], order[1], ... 
				final String instanceId = getInstanceIdFromParameterName(pName);
				if (DEBUG) {
		            StackTraceElement t = (new Throwable()).getStackTrace()[0];
		            String f = "(%s:%d) %s: instance id is %s";
		            Log.i(TAG, String.format(f, t.getFileName(), t.getLineNumber(), t.getMethodName(), instanceId));
				}
				if (instanceId != null) {
					final String attributeId = getAttributeIdFromParameterName(pName);
					if (DEBUG) {
			            StackTraceElement t = (new Throwable()).getStackTrace()[0];
			            String f = "(%s:%d) %s: attribute id is %s";
			            Log.i(TAG, String.format(f, t.getFileName(), t.getLineNumber(), t.getMethodName(), attributeId));
					}
					
					final List<String> pValues = httpMap.getAll(pName);
					if (DEBUG) {
			            StackTraceElement t = (new Throwable()).getStackTrace()[0];
			            String f = "(%s:%d) %s: parameter values is %s (%d)";
			            Log.i(TAG, String.format(f, t.getFileName(), t.getLineNumber(), t.getMethodName(), pValues, pValues.size()));
					}
					
					if (pValues.size() > 0) {
						Map<String, String> instanceMap = parameterMap.get(instanceId);
						if (instanceMap == null) {
							instanceMap = new HashMap<String, String>();
						}
						if (DEBUG) {
				            StackTraceElement t = (new Throwable()).getStackTrace()[0];
				            String f = "(%s:%d) %s: parameter values[0] is \"%s\"";
				            Log.i(TAG, String.format(f, t.getFileName(), t.getLineNumber(), t.getMethodName(), pValues.get(0)));
						}
						instanceMap.put(attributeId, pValues.get(0));
						parameterMap.put(instanceId, instanceMap);
					}
				}
			}
		}
		
		// Build POJO instance with the parameters map as <instanceId, <attributeId, attributeValue>>
		final Set<String> idSet = parameterMap.keySet();
		final Iterator<String> iter_i = idSet.iterator();
		while (iter_i.hasNext()) { // Use while loop CAN handle multi-types parameters
			final String instanceId = iter_i.next();
			final String classId = getClassIdFromInstanceId(instanceId);
			if (DEBUG) {
				final StackTraceElement t = (new Throwable()).getStackTrace()[0];
	            final String f = "(%s:%d) %s: class id is %s, instance id is %s";
	            Log.e(TAG, String.format(f, t.getFileName(), t.getLineNumber(), t.getMethodName(), classId, instanceId));
			}
			
			final String className = _registry.getPOJOClassName(classId);
			if (className == null) {
	            StackTraceElement t = (new Throwable()).getStackTrace()[0];
	            String f = "(%s:%d) %s: Unknown class id: \"%s\". You CAN check \"WEB-INF/classes/adpj.properties\" on server side for more information.";
	            String error = String.format(f, t.getFileName(), t.getLineNumber(), t.getMethodName(), classId);
	            if (DEBUG) Log.e(TAG, error);
	            throw new ServerSideException(error);
			} else {
				T instance = null;
				try {
					instance = (T) Class.forName(className).newInstance();
				} catch (ClassNotFoundException thrown) {
		            StackTraceElement t = (new Throwable()).getStackTrace()[0];
		            String f = "(%s:%d) %s: ClassNotFoundException: ";
		            final String error = String.format(f, t.getFileName(), t.getLineNumber(), t.getMethodName());
		            if (DEBUG) Log.e(TAG, error, thrown);
					throw new ServerSideException(error);
				} catch (InstantiationException thrown) {
		            StackTraceElement t = (new Throwable()).getStackTrace()[0];
		            String f = "(%s:%d) %s: InstantiationException: ";
		            final String error = String.format(f, t.getFileName(), t.getLineNumber(), t.getMethodName());
		            if (DEBUG) Log.e(TAG, error, thrown);
					throw new ServerSideException(error);
				} catch (IllegalAccessException thrown) {
		            StackTraceElement t = (new Throwable()).getStackTrace()[0];
		            String f = "(%s:%d) %s: IllegalAccessException: ";
		            final String error = String.format(f, t.getFileName(), t.getLineNumber(), t.getMethodName());
		            if (DEBUG) Log.e(TAG, error, thrown);
					throw new ServerSideException(error);
				} catch (Throwable thrown) {
		            StackTraceElement t = (new Throwable()).getStackTrace()[0];
		            String f = "(%s:%d) %s: Throwable: ";
		            final String error = String.format(f, t.getFileName(), t.getLineNumber(), t.getMethodName());
		            if (DEBUG) Log.e(TAG, error, thrown);
					throw new ServerSideException(error);
				}
				if (instance != null) {
					final Map<String, String> instanceMap = parameterMap.get(instanceId);
					final Class classInstance = instance.getClass();
					final Method[] methods = classInstance.getMethods();
					for (final Method method : methods) {
						if (method.isAnnotationPresent(SetMethod.class)) {
							final SetMethod setMethod = method.getAnnotation(SetMethod.class);
							final String parameterId = setMethod.parameterId();
							final String parameterType = setMethod.parameterType();
							final String value = instanceMap.get(parameterId);
							Object attribute = null;
							if ("String".equals(parameterType)) {
								attribute = value == null || value.isEmpty() ? null : value;
							} else if ("Boolean".equals(parameterType) || "boolean".equals(parameterType)) {
								attribute = value == null ? (Boolean) false : (Boolean) Boolean.parseBoolean(value);
							} else if ("Integer".equals(parameterType) || "int".equals(parameterType)) {
								try { attribute = value == null ? (Integer) 0 : (Integer) Integer.parseInt(value); } catch (NumberFormatException ignore) { };
							} else if ("Long".equals(parameterType) || "long".equals(parameterType)) {
								try { attribute = value == null ? (Long) 0L : (Long) Long.parseLong(value); } catch (NumberFormatException ignore) { };
							} else if ("Date".equals(parameterType)) {
								// 日期格式的解析 ... 增加 类的方法与属性 可以定义日期解析格式 ... 
							} else if (value != null) {
					            StackTraceElement t = (new Throwable()).getStackTrace()[0];
					            String f = "(%s:%d) %s: Unimplemented parameter type: %s";
					            final String error = String.format(f, t.getFileName(), t.getLineNumber(), t.getMethodName(), parameterType);
					            if (DEBUG) Log.w(TAG, error);
								throw new ServerSideException(error);
							} else {
								// do nothing
							}
							try {
								// Invoke setXXX method with the HTTP parameter value
								method.invoke(instance, attribute);
							} catch (IllegalAccessException thrown) {
								final StackTraceElement t = (new Throwable()).getStackTrace()[0];
					            final String f = "(%s:%d) %s: IllegalAccessException: ";
					            final String error = String.format(f, t.getFileName(), t.getLineNumber(), t.getMethodName());
					            if (DEBUG) Log.e(TAG, error, thrown);
								throw new ServerSideException(error);
							} catch (IllegalArgumentException thrown) {
								final StackTraceElement t = (new Throwable()).getStackTrace()[0];
					            final String f = "(%s:%d) %s: IllegalArgumentException: You CAN check %s class definitions for DEBUG it";
					            final String error = String.format(f, t.getFileName(), t.getLineNumber(), t.getMethodName(), className);
					            if (DEBUG) Log.e(TAG, error, thrown);
								throw new ServerSideException(error);
							} catch (InvocationTargetException thrown) {
								final StackTraceElement t = (new Throwable()).getStackTrace()[0];
								final String f = "(%s:%d) %s: InvocationTargetException: ";
					            final String error = String.format(f, t.getFileName(), t.getLineNumber(), t.getMethodName());
					            if (DEBUG) Log.e(TAG, error, thrown);
								throw new ServerSideException(error);
							} catch (Throwable thrown) {
								final StackTraceElement t = (new Throwable()).getStackTrace()[0];
								final String f = "(%s:%d) %s: Throwable: ";
					            final String error = String.format(f, t.getFileName(), t.getLineNumber(), t.getMethodName());
					            if (DEBUG) Log.e(TAG, error, thrown);
								throw new ServerSideException(error);
							}
						}
					}
					if (DEBUG) {
						final StackTraceElement t = (new Throwable()).getStackTrace()[0];
			            final String f = "(%s:%d) %s: instance id is %s, instance is %s";
			            Log.e(TAG, String.format(f, t.getFileName(), t.getLineNumber(), t.getMethodName(), instanceId, instance.toString()));
					}
					returnMap.put(instanceId, instance);
				} // end if (instance != null) 
			} // end if (className == null) 
		} // end while loop
		return (returnMap);
	}
}
