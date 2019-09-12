package com.adapterj.test.widget;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javassist.CannotCompileException;
import javassist.ClassClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.NotFoundException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Entities;
import org.jsoup.select.Elements;

import com.adapterj.annotation.GetMethod;
import com.adapterj.annotation.ID;
import com.adapterj.annotation.List;
import com.adapterj.ext.lang.AdapterJClassLoader;
import com.adapterj.logging.Debugger;
import com.adapterj.logging.Log;
import com.adapterj.test.Testable;
import com.adapterj.text.Formatter;
import com.adapterj.widget.AbstractView;
import com.adapterj.widget.Adapter;
import com.adapterj.widget.BindException;
import com.adapterj.widget.SimpleMapAdapter;
import com.adapterj.widget.Anchor;
import com.adapterj.widget.AnchorGroup;
import com.adapterj.widget.ListAdapter;
import com.adapterj.widget.NotStandardizedHTMLException;
import com.adapterj.widget.SelectOptions;
import com.adapterj.widget.SimpleFormAdapter;
import com.adapterj.widget.SimpleHTMLViewAccelerator;
import com.adapterj.widget.SimpleViewAdapter;
import com.adapterj.widget.ViewAccelerator;

/**
 * 目前只支持一个 elementId 仅处理第一个被找到的 element tag ... 如果不考虑 HTML 规范中强调 id 的唯一性，
 * 实际上，elementId 完全相同的 element tag 应该绑定同样的值 ... 
 * 
 * 服务端绑定的功能，代码已基本完成。需要把完全相同的重复代码移到 各个专门功能的函数中 
 * 浏览器端绑定，代码还没有实现。
 * 
 * 
 * 加速器:
 * 1. 基于 HTML 模板，动态生成一个方法，以 StringBuffer 方式输出 HTML string，
 *    在变量位置写上 _pojo.getName() 或 _list.get(i).getName() ... 等等  
 * 2. 每个模板文件对应一个特定的 SimpleHTMLViewAccelerator 子类，模板文件名 与 类名的 对应关系放入 view.properties 中
 *    动态修改模板对应的 SimpleHTMLViewAccelerator 子类。
 * 3. 如果 SimpleHTMLViewAccelerator 子类 存在 且不需要更新，则 直接 调用其 toHTMLString() 方法。
 * 
 * 关于加速器的更新:
 * 1. 目前，加速器没有更新到磁盘，仅保存到内存。当 Tomcat 重新启动，或者 项目被 Reload 时，会重新生成加速器。
 *    否者，会继续使用已经保存到 AdapterJClassLoader 内存中 的 加速器。
 *    这样已经提供了加速器生命周期的管理机制。
 *    当 HTML 模板更新之后，只需要在 Tomcat 管理界面中 Reload 项目，则可以重新生成加速器。
 * 2. 如果要提供更进一步的加速器生命周期管理机制，可以根据 模板文件 的 md5 或者 hash，
 *    以及 SimpleHTMLViewAccelerator 子类的 md5 或者 hash，检查 SimpleHTMLViewAccelerator 子类 是否需要更新？
 *    这或许需要一个 Properties 文件用于存储 映射关系，如果映射关系 与 实际计算值 一致，则说明不需要更新。
 *    为了确保性能，模板文件的 md5 或 hash，以及 class 的 md5 或 hash，可以在 Servlet 初始化的时候计算。
 * 
 * @author York/GuangYu DENG
 */
public class SimpleHTMLViewV2_1 extends AbstractView {

	private static final boolean DEBUG = Debugger.DEBUG ? false : false;
	
    private static final String TAG = SimpleHTMLViewV2_1.class.getName();

	private final Map<String, Adapter> _adapters = new HashMap<String, Adapter>();

	private final java.util.List<String> _externalScripts = new ArrayList<String>();
	
	private final Map<String, String> _scripts = new HashMap<String, String>();

	private final Map<String, String> _javas = new HashMap<String, String>();

	// The ViewAccelerator class name
	private String _acceleratorClass = null;
	
	// The HTML template Document
	private Document _document = null;

	// The original ViewAccelerator instance
	private Object _original = null;
	
	/**
	 * Constructors
	 * 
	 */
	public SimpleHTMLViewV2_1() {
		// do nothing
	}
	
	/**
	 * Constructors
	 * 
	 * @param acceleratorClass The ViewAccelerator class name
	 */
	public SimpleHTMLViewV2_1(final String acceleratorClass) {
		if (acceleratorClass == null) {
			throw new IllegalArgumentException("accelerator class is null");
		}

		try {
			_original = Class.forName(_acceleratorClass = acceleratorClass).newInstance();
		} catch (ClassNotFoundException e) {
			throw new IllegalArgumentException(String.format("ClassNotFoundException: %s", acceleratorClass), e);
		} catch (InstantiationException e) {
			throw new IllegalArgumentException(String.format("InstantiationException: %s", acceleratorClass), e);
		} catch (IllegalAccessException e) {
			throw new IllegalArgumentException(String.format("IllegalAccessException: %s", acceleratorClass), e);
		}
	}

	/**
	 * Constructors
	 * 
	 * @param acceleratorClass The ViewAccelerator class name
	 * @param document The HTML template document
	 */
	public SimpleHTMLViewV2_1(final String acceleratorClass, final Document document) {
		if (acceleratorClass == null) {
			throw new IllegalArgumentException("accelerator class is null");
		}
		
		if (document == null) {
			throw new IllegalArgumentException("document is null");
		}

		try {
			_original = Class.forName(_acceleratorClass = acceleratorClass).newInstance();
			
			if (_original instanceof Testable) {
				((Testable) _original).test("It's working now !!!");
			}
		} catch (ClassNotFoundException e) {
			throw new IllegalArgumentException(String.format("ClassNotFoundException: %s", acceleratorClass), e);
		} catch (InstantiationException e) {
			throw new IllegalArgumentException(String.format("InstantiationException: %s", acceleratorClass), e);
		} catch (IllegalAccessException e) {
			throw new IllegalArgumentException(String.format("IllegalAccessException: %s", acceleratorClass), e);
		}
		
		try {
			_document = document.clone();
		} catch (Throwable e) {
			throw new IllegalArgumentException(String.format("Clone document failure: %s", e.getMessage()), e);
		}
	}

	/**
	 * 
	 * @param uri
	 */
	public void addExternalScript(final String uri) {
		if (uri == null) {
			throw new IllegalArgumentException("uri is null");
		}
		_externalScripts.add(uri);
	}
	
	protected Formatter _formatter = new Formatter();

	public void setFormatter(Formatter formatter) {
		if (formatter == null) {
			throw new IllegalArgumentException("formatter is null");
		}
		_formatter = formatter;
	}
	
	/**
	 * Bind all adapters to these view holders (HTML tags)
	 * 
	 * @param bindType, HTML Binding or JavaScript Binding.
	 */
	@Override
	public void bindAll(final int bindType) {
		final Set<String> idSet = _adapters.keySet();
		if (!idSet.isEmpty()) {
			final Iterator<String> iter = idSet.iterator();
			while (iter.hasNext()) {
				final String id = iter.next();
				final Adapter adapter = _adapters.get(id);
				
				// HTML Binding
				final int html = bindType & SERVER_SIDE_BINDING;
				if (html > 0) {
					buildDOM(id, adapter);
				}
				
				// Script Binding
				final int script = bindType & BROWSER_SIDE_BINDING;
				if (script > 0) {
					buildScript(id, adapter);
				}
			}
		}
		
		if (!_scripts.isEmpty()) {
			// Build JavaScript 
			final StringBuffer js = new StringBuffer();
			final Set<String> callings = _scripts.keySet();
			if (!callings.isEmpty()) {
				final Iterator<String> itor = callings.iterator();
				while (itor.hasNext()) {
					js.append(_scripts.get(itor.next()));
				}
			}
			js.append('\n');
			js.append('\n');
			js.append("function bindAll() {").append('\n');
			if (!callings.isEmpty()) {
				final Iterator<String> itor = callings.iterator();
				while (itor.hasNext()) {
					js.append(itor.next()).append('\n');
				}
			}
			js.append('}').append(';');
			
			// Write JavaScript into HTML document
			final Elements heads = _document.getElementsByTag("head");
			if (heads == null) {
				throw new NotStandardizedHTMLException("No HEAD tag");
			} else {
				final Element head = heads.first();
				if (head == null) {
					throw new NotStandardizedHTMLException("No HEAD tag");
				} else {
					final StringBuilder script = new StringBuilder();
					for (String uri : _externalScripts) {
						if (uri != null) script.append("<script type=\"text/javascript\" src=\"").append(uri).append("\"></script>").append('\n');
					}
					script.append("<script type=\"text/javascript\">").append('\n');
					script.append(js).append('\n');
					script.append("</script>");
					
					head.append(script.toString());
				}
				
				// Bind JavaScript with DOM Event
				final Elements bodys = _document.getElementsByTag("body");
				if (bodys == null) {
					throw new NotStandardizedHTMLException("No BODY tag");
				} else {
					final Element body = bodys.first();
					if (body == null) {
						throw new NotStandardizedHTMLException("No BODY tag");
					} else {
						body.attr("onload", "javascript:bindAll();"); // BUG 扩展而不是替换
					}
				}
			}
		}
	}
	
	/**
	 * Bind the adapter to the view holder (HTML tags) in HTML Binding mode
	 * 
	 * @param id - The ID of Adapter
	 */
	@SuppressWarnings("rawtypes")
	public void buildDOM(final String id, final Adapter adapter) {
		if (adapter instanceof ListAdapter) {
			buildDOM(id, (ListAdapter) adapter);
		} else if (adapter instanceof SimpleFormAdapter) {
			buildDOM(id, (SimpleFormAdapter) adapter);
		} else if (adapter instanceof SimpleViewAdapter) {
			buildDOM(id, (SimpleViewAdapter) adapter);
		} else if (adapter instanceof SimpleMapAdapter) {
			buildDOM(id, (SimpleMapAdapter) adapter);
		} else {
            StackTraceElement t = (new Throwable()).getStackTrace()[0];
            String f = "(%s:%d) %s: Unimplemented bind: adapter is %s";
            String error = String.format(f, t.getFileName(), t.getLineNumber(), t.getMethodName(), adapter);
            if (DEBUG) Log.e(TAG, error);
            throw new BindException(error);
		}
	}
	
	/**
	 * Bind the adapter to the view holder (HTML tags) in Script Binding mode
	 * 
	 * @param id - The ID of Adapter
	 * @param adapter - The instance of Adapter
	 */
	@SuppressWarnings("rawtypes")
	public void buildScript(final String id, final Adapter adapter) {
		if (adapter instanceof ListAdapter) {
			buildScript(id, (ListAdapter) adapter);
		} else if (adapter instanceof SimpleFormAdapter) {
			buildScript(id, (SimpleFormAdapter) adapter);
		} else if (adapter instanceof SimpleViewAdapter) {
			buildScript(id, (SimpleViewAdapter) adapter);
		} else if (adapter instanceof SimpleMapAdapter) {
			buildScript(id, (SimpleMapAdapter) adapter);
		} else {
            StackTraceElement t = (new Throwable()).getStackTrace()[0];
            String f = "(%s:%d) %s: Unimplemented bind: adapter is %s";
            String error = String.format(f, t.getFileName(), t.getLineNumber(), t.getMethodName(), adapter);
            if (DEBUG) Log.e(TAG, error);
            throw new BindException(error);
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected final void buildDOM(final String id, final ListAdapter adapter) {
		if (_document == null) {
			final StackTraceElement t = (new Throwable()).getStackTrace()[0];
			final String f = "(%s:%d) %s: _document is null";
	        final String error = String.format(f, t.getFileName(), t.getLineNumber(), t.getMethodName());
	        if (DEBUG)  Log.w(TAG, error);
			throw new BindException(error);
		} 

		// Such as: _list.item, _list[0].item, _list[1].item, ...
		final int p1 = id.indexOf('[');
		final int p2 = id.indexOf(']');
		final String array = p1 > -1 && p2 > p1 ? id.substring(p1, p2 + 1) : "";
		if (DEBUG) {
            StackTraceElement t = (new Throwable()).getStackTrace()[0];
            String format = "(%s:%d) %s: list array index is \"%s\"";
            Log.i(TAG, String.format(format, t.getFileName(), t.getLineNumber(), t.getMethodName(), array));
		}

		final List anList = adapter.getClass().getAnnotation(List.class);
		final String indexId = anList.indexId(); // Such as: "i", "j" 
		final String indexIj = new StringBuffer().append('[').append(indexId).append(']').toString(); // Such as: "[i]", "[j]"
		
		final int itemCount = adapter.getItemCount();
		if (DEBUG) {
            StackTraceElement t = (new Throwable()).getStackTrace()[0];
            String format = "(%s:%d) %s: item count is %d";
            Log.i(TAG, String.format(format, t.getFileName(), t.getLineNumber(), t.getMethodName(), itemCount));
		}
		if (itemCount == 0) {
			// adapter 应该有一个 auto clean (if empty) 的属性及方法 
			// adapter 应该有一个 ignore clean list 不用进行处理
			final Element element = _document.getElementById(id); // Such as: _list.item, _list[0].item, ... 
			if (element == null) {
		        final StackTraceElement t = (new Throwable()).getStackTrace()[0];
		        final String f = "(%s:%d) %s: get element by id return a null: id = \"%s\"";
		        final String error = String.format(f, t.getFileName(), t.getLineNumber(), t.getMethodName(), id);
		        if (DEBUG)  Log.w(TAG, error);
				throw new BindException(error);
			}
			
			final String placeholder = adapter.getPlaceholderForNull();
			final Elements elements = element.select("*[id]");
			final Iterator<Element> iter = elements.iterator();
			while (iter.hasNext()) {
				final Element child = iter.next();
				final String tagName = child.tagName();
				if ("a".equalsIgnoreCase(tagName)) { // Anchor: <a />
					child.removeAttr("href");
					child.html(placeholder);
				} else if ("img".equalsIgnoreCase(tagName)) { // Image: <img />
					child.removeAttr("href");
				} else if ("input".equalsIgnoreCase(tagName)) { // INPUT: <input />
					child.attr("value", "");
				} else if ("span".equalsIgnoreCase(tagName)) { // SPAN: <span />
					child.html(placeholder);
				} else if ("p".equalsIgnoreCase(tagName)) { // P: <p />
					child.html(placeholder);
				} else {
					// do nothing
				}
			}
		} else if (itemCount > 0) {
			final StringBuilder s = new StringBuilder();
			
			s.append("list2").append(_javas.size() + 1);
			final String list20 = s.toString(); // Such as list21, list22, ... 
			
			s.delete(0, s.length());
			s.append("final").append(' ').append("String").append(' ').append("id");
			s.append(' ').append('=').append(' ');
			s.append('"').append(id).append('"').append(';').append('\n');
			s.append("final").append(' ').append("java.util.List").append(' ').append(list20);
			s.append(' ').append('=').append(' ');
			s.append("getList2(id);").append('\n');
			s.append("if").append(' ').append('(').append(list20).append(' ').append("==").append(' ').append("null").append(')').append(' ').append('{').append('\n');
			s.append("return (\"Get a null list: id = \" + id);").append('\n');
			s.append('}').append('\n').append('\n');
			_javas.put(id, s.toString());
			
			final java.util.Map<String, String> java = new LinkedHashMap<String, String>();
			final java.util.List<java.util.List<String>> list2 = new ArrayList<java.util.List<String>>();
			final java.util.List allItems = adapter.getAllItems();
			if (allItems != null) {
				int j = 0;
				final java.util.List<AnchorGroup> allLinks = adapter.getAllAnchorGroup();
				final java.util.Iterator iter = allItems.iterator();
				while (iter.hasNext()) {
					final Object item = iter.next();
					final Class clazz = item.getClass();
					if (clazz.isAnnotationPresent(ID.class)) {
						int index = 0;
						final java.util.List<String> list = new ArrayList<String>();
						final ID classID = (ID) clazz.getAnnotation(ID.class);
						final String classId = classID.identity();
						
						final Method[] methods = clazz.getMethods();
						for (final Method method : methods) {
							if (method.isAnnotationPresent(GetMethod.class)) {
								Object object = null;
								try {
									object = method.invoke(item); // invoke getXXX method 
								} catch (IllegalAccessException e) {
									throw new BindException(e);
								} catch (IllegalArgumentException e) {
									throw new BindException(e);
								} catch (InvocationTargetException e) {
									throw new BindException(e);
								}
								
								final GetMethod getMethod = method.getAnnotation(GetMethod.class);
								final String returnId = getMethod.returnId(); // Such as: "type", "price"
								final String returnType = getMethod.returnType();
								s.delete(0, s.length());
								s.append(classId); // Such as: "product", "order"
								if (array != null && !array.isEmpty()) s.append(array); // Such as: "[0]", "[1]"
								s.append(indexIj).append('.').append(returnId); // Such as: "[i].type", "[j].price"
								final String elementId = s.toString(); // Such as: "product[i].type", "order[1][j].price"

								String value = null;
								if (object == null) {
									value = adapter.getPlaceholderForNull();
								} else if ("String".equals(returnType)) {
									value = (String) object;
								} else if ("Boolean".equals(returnType) || "boolean".equals(returnType)) {
									value = Boolean.toString((Boolean) object);
								} else if ("Integer".equals(returnType) || "int".equals(returnType)) {
									value = Integer.toString((Integer) object);
								} else if ("Long".equals(returnType) || "long".equals(returnType)) {
									value = Long.toString((Long) object);
								} else if ("Date".equals(returnType)) {
									value = _formatter.format((Date) object);
								} else {
									value = object.toString();
								}

								final SelectOptions options = adapter.getSelectOptions(returnId); // Such as: "type", "price"
//								if (DEBUG) {
//						            StackTraceElement t = (new Throwable()).getStackTrace()[0];
//						            String f = "(%s:%d) %s: options is %s, return id is %s";
//						            Log.i(TAG, String.format(f, t.getFileName(), t.getLineNumber(), t.getMethodName(), options, returnId));
//								}
								if (object != null && options != null) {
									value = options.selected(value);
								}

								if (j == 0) {
									s.delete(0, s.length());
									// ((java.util.List)list21.get(j1)).get(0)
									s.append("<!--<%=").append(' ').append('(').append("(java.util.List)").append(' ').append(list20).append('.').append("get(j1)").append(')').append('.').append("get").append('(').append(index).append(')').append(' ').append("%>-->");
									java.put(elementId, s.toString());
									index ++;
								}
								
								list.add(value);
							}
						} // end for (final Method method : methods)

						if (allLinks != null) {
							final AnchorGroup links = allLinks.get(j);
							if (links != null) {
								final ID linkID = (ID) Anchor.class.getAnnotation(ID.class);
								final String linkId = linkID.identity();
								final StringBuilder builder = new StringBuilder();
								builder.append(linkId); // Such as: "link"
								if (array != null && !array.isEmpty()) builder.append(array); // Such as: "[0]", "[1]"
								builder.append(indexIj); // Such as: "[j]"

								for (int k = 0; k < links.length(); k ++) {
									final Anchor link = links.anchor(k);
									if (link != null) {
										final Method[] mthds = link.getClass().getMethods();
										for (final Method mthd : mthds) {
											if (mthd.isAnnotationPresent(GetMethod.class)) {
												Object value = null;
												final GetMethod getMethod = mthd.getAnnotation(GetMethod.class);
												try {
													value = mthd.invoke(link); // invoke getXXX method 
												} catch (IllegalAccessException e) {
													throw new BindException(e);
												} catch (IllegalArgumentException e) {
													throw new BindException(e);
												} catch (InvocationTargetException e) {
													throw new BindException(e);
												}
												
												if (value != null) {
													final String returnId = getMethod.returnId();
													
													s.delete(0, s.length());
													s.append(builder).append('[').append(k).append(']').append('.').append(returnId).toString(); // Such as: "link[j][0].url", "link[0][j][3].title"
													final String elementId = s.toString();

													if (j == 0) {
														s.delete(0, s.length());
														// ((java.util.List)list21.get(j1)).get(0)
														s.append("<!--<%=").append(' ').append('(').append("(java.util.List)").append(' ').append(list20).append('.').append("get(j1)").append(')').append('.').append("get").append('(').append(index).append(')').append(' ').append("%>-->");
														java.put(elementId, s.toString());
														index ++;
													}
													
													list.add((String) value);
													if (DEBUG) {
											            StackTraceElement t = (new Throwable()).getStackTrace()[0];
											            String f = "(%s:%d) %s: element id is \"%s\", value is %s";
											            Log.i(TAG, String.format(f, t.getFileName(), t.getLineNumber(), t.getMethodName(), elementId, value));
													}
												}
											}
										} // end for (final Method mthd : mthds)
									}
								} // end for (int k = 0; k < links.length(); k ++)
							}
						} // end if (allLinks != null)

						list2.add(list);
						j ++;
					}
				} // end while (iter.hasNext())
			} // end if (allItems != null)

			
			Log.i(TAG, String.format("_original is %s", _original));
			if (_original instanceof SimpleHTMLViewAcceleratorV2) {
				((SimpleHTMLViewAcceleratorV2) _original).putList2(id, list2);
				Log.i(TAG, String.format("id is %s, list2 is %s", id, ((SimpleHTMLViewAcceleratorV2) _original).getList2(id)));
			}
			
			final String className = getAcceleratorClassName();
			final AdapterJClassLoader classLoader = AdapterJClassLoader.getInstance();
			Class<?> newClazz = null;
			try {
				newClazz = classLoader.loadClass(className);
			} catch (Throwable ignore) {
				// ignore
			}
			
			if (newClazz != null) {
				if (DEBUG) {
		            StackTraceElement t = (new Throwable()).getStackTrace()[0];
		            String f = "(%s:%d) %s: Get a %s instance -> %s";
					Log.i(TAG, String.format(f, t.getFileName(), t.getLineNumber(), t.getMethodName(), className, newClazz));
				}
			} else {
				final Element element = _document.getElementById(id); // Such as: _list.item, _list[0].item, ... 
				if (element == null) {
			        final StackTraceElement t = (new Throwable()).getStackTrace()[0];
			        final String f = "(%s:%d) %s: get element by id return a null: id = \"%s\"";
			        final String error = String.format(f, t.getFileName(), t.getLineNumber(), t.getMethodName(), id);
			        if (DEBUG)  Log.w(TAG, error);
					throw new BindException(error);
				}
				if (!java.isEmpty()) {
					final Set<String> idSet = java.keySet();
					final Iterator<String> iter = idSet.iterator();
					while(iter.hasNext()) {
						final String elementId = iter.next(); /// Such as: "product[i].type", "order[1][j].price"
						final Element childElement = element.getElementById(elementId);
						if (childElement != null) {
							final String tagName = childElement.tagName();
							if (DEBUG) {
					            StackTraceElement t = (new Throwable()).getStackTrace()[0];
					            String f = "(%s:%d) %s: child clement tagName is \"%s\"";
					            Log.i(TAG, String.format(f, t.getFileName(), t.getLineNumber(), t.getMethodName(), tagName));
							}
	
							// bind the java value to the element by mapping from returnId to elementId
							if ("a".equalsIgnoreCase(tagName)) { // Anchor: <a/>
								childElement.attr("href", java.get(elementId));
							} else if ("img".equalsIgnoreCase(tagName)) { // Image: <img/>
								childElement.attr("src", java.get(elementId));
							} else {
								childElement.text(java.get(elementId));
							}
						}
					} // end while(iter.hasNext())
	
					final String html = element.toString();
					element.before("<!--<% for (int j1 = 0; j1 < list21.size(); j1 ++) { %>-->");
					element.before(html);
					element.before("<!--<% }; %>-->");
				}
				element.remove();
			}
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected final void buildDOM(final String id, final SimpleFormAdapter adapter) {
		if (_document == null) {
			final StackTraceElement t = (new Throwable()).getStackTrace()[0];
            final String f = "(%s:%d) %s: _doc is null";
            final String error = String.format(f, t.getFileName(), t.getLineNumber(), t.getMethodName());
            if (DEBUG) Log.e(TAG, error);
			throw new BindException(error);
		} 
		
		final Element container = _document.getElementById(id);
		if (container == null) {
            StackTraceElement t = (new Throwable()).getStackTrace()[0];
            String f = "(%s:%d) %s: find element by id return a null: id = \"%s\"";
            final String error = String.format(f, t.getFileName(), t.getLineNumber(), t.getMethodName(), id);
            if (DEBUG) Log.w(TAG, error);
			throw new BindException(error);
		}
		
		final Elements forms = container.getElementsByTag("form");
		if (forms == null) {
            StackTraceElement t = (new Throwable()).getStackTrace()[0];
            String f = "(%s:%d) %s: find form elements return a null";
            final String error = String.format(f, t.getFileName(), t.getLineNumber(), t.getMethodName());
            if (DEBUG) Log.w(TAG, error);
			throw new BindException(error);
		}
		
		final Element form = forms.first();
		if (form == null) {
            StackTraceElement t = (new Throwable()).getStackTrace()[0];
            String f = "(%s:%d) %s: get first form element return a null";
            final String error = String.format(f, t.getFileName(), t.getLineNumber(), t.getMethodName());
            if (DEBUG) Log.w(TAG, error);
			throw new BindException(error);
		}
		
		final String formAction = adapter.getFormAction();
		if (formAction != null) {
			form.attr("action", formAction);
		}

		// Such as: _form, _form[0], _form[1], ...
		final int p1 = id.indexOf('[');
		final int p2 = id.indexOf(']');
		final String array = p1 > -1 && p2 > p1 ? id.substring(p1, p2 + 1) : "";
		if (DEBUG) {
            StackTraceElement t = (new Throwable()).getStackTrace()[0];
            String f = "(%s:%d) %s: form array index is \"%s\"";
            Log.i(TAG, String.format(f, t.getFileName(), t.getLineNumber(), t.getMethodName(), array));
		}

		final Object data = adapter.getData();
		if (data == null) {
			// 清除模板中的演示数据 
			// 需要找到所有的 input textarea select 等等 
			// 然后处理 select options 数据
		} else {
			final Map<String, String> valueMap = new HashMap<String, String>();
			final Class clazz = data.getClass();
			if (clazz.isAnnotationPresent(ID.class)) {
				final ID classID = (ID) clazz.getAnnotation(ID.class);
				final String classId = classID.identity();
				final Method[] methods = data.getClass().getMethods();
				for (final Method method : methods) {
					if (method.isAnnotationPresent(GetMethod.class)) {
						Object value = null;
						final GetMethod getMethod = method.getAnnotation(GetMethod.class);
						try {
							value = method.invoke(data); // invoke getXXX method 
						} catch (IllegalAccessException e) {
							throw new BindException(e);
						} catch (IllegalArgumentException e) {
							throw new BindException(e);
						} catch (InvocationTargetException e) {
							throw new BindException(e);
						}

						final String returnId = getMethod.returnId();
						final String returnType = getMethod.returnType();
						final StringBuffer s = new StringBuffer().append(classId); // Such as: "product", "order"
						//if (array != null && !array.isEmpty()) s.append(array); // Such as: "[0]", "[1]"
						s.append('.').append(returnId); // Such as: ".type", ".price"
						final String elementId = s.toString(); // Such as: "product.type", "order.price"
						if (DEBUG) {
				            StackTraceElement t = (new Throwable()).getStackTrace()[0];
				            String f = "(%s:%d) %s: element: id is \"%s\"";
				            Log.i(TAG, String.format(f, t.getFileName(), t.getLineNumber(), t.getMethodName(), elementId));
						}
						
						if (value == null) {
							valueMap.put(elementId, "");
						} else if ("String".equals(returnType)) {
							valueMap.put(elementId, (String) value);
						} else if ("Boolean".equals(returnType) || "boolean".equals(returnType)) {
							valueMap.put(elementId, Boolean.toString((Boolean) value));
						} else if ("Integer".equals(returnType) || "int".equals(returnType)) {
							valueMap.put(elementId, Integer.toString((Integer) value));
						} else if ("Long".equals(returnType) || "long".equals(returnType)) {
							valueMap.put(elementId, Long.toString((Long) value));
						} else if ("Date".equals(returnType)) {
							valueMap.put(elementId, _formatter.format((Date) value));
						} else {
							valueMap.put(elementId, value.toString());
						}
					}
				} // end for (final Method method : methods)
			} // end if (clazz.isAnnotationPresent(ID.class))

			final AnchorGroup links = adapter.getAnchorGroup();
			if (links != null) {
				final ID classID = Anchor.class.getAnnotation(ID.class);
				final String classId = classID.identity();
				for (int k = 0; k < links.length(); k ++) {
					final Anchor link = links.anchor(k);
					if (link != null) {
						final Method[] mthds = link.getClass().getMethods();
						for (final Method mthd : mthds) {
							if (mthd.isAnnotationPresent(GetMethod.class)) {
								Object value = null;
								final GetMethod getMethod = mthd.getAnnotation(GetMethod.class);
								try {
									value = mthd.invoke(link); // invoke getXXX method 
								} catch (IllegalAccessException e) {
									throw new BindException(e);
								} catch (IllegalArgumentException e) {
									throw new BindException(e);
								} catch (InvocationTargetException e) {
									throw new BindException(e);
								}
								
								if (value != null) {
									final String returnId = getMethod.returnId();
									final String elementId = new StringBuffer(classId)
											.append('[').append(k).append(']')
											.append('.').append(returnId).toString(); // Such as: "link[0].url"
									valueMap.put(elementId, (String) value);
									if (DEBUG) {
							            StackTraceElement t = (new Throwable()).getStackTrace()[0];
							            String f = "(%s:%d) %s: element id is \"%s\", value is %s";
							            Log.i(TAG, String.format(f, t.getFileName(), t.getLineNumber(), t.getMethodName(), elementId, value));
									}
								}
							}
						} // end for (final Method mthd : mthds)
					}
				} // end for (int k = 0; k < links.length(); k ++)
			} // end if (links != null)

			final Set<String> idSet = valueMap.keySet();
			final Iterator<String> iter = idSet.iterator();
			while(iter.hasNext()) {
				final String elementId = iter.next();
				if (DEBUG) {
		            StackTraceElement t = (new Throwable()).getStackTrace()[0];
		            String f = "(%s:%d) %s: item element: id is \"%s\"";
		            Log.i(TAG, String.format(f, t.getFileName(), t.getLineNumber(), t.getMethodName(), elementId));
				}
				
				final Element element = container.getElementById(elementId);
				if (element != null) {
					final String value = valueMap.get(elementId);
					final String tagName = element.tagName();
					final String type = element.attr("type");
					if (DEBUG) {
			            StackTraceElement t = (new Throwable()).getStackTrace()[0];
			            String f = "(%s:%d) %s: child element: tagName is \"%s\", type is %s";
			            Log.i(TAG, String.format(f, t.getFileName(), t.getLineNumber(), t.getMethodName(), tagName, type));
					}
					// Bind the value to the element
					if ("a".equalsIgnoreCase(tagName)) { // Anchor: <a/>
						element.attr("href", value);
					} else if ("img".equalsIgnoreCase(tagName)) { // Image: <img/>
						element.attr("src", value);
					} else if ("input".equalsIgnoreCase(tagName)) { // Input: <input/>
						
						if ("text".equalsIgnoreCase(type)) {
							element.attr("value", value);
						} else if ("checkbox".equalsIgnoreCase(type)) {
							if ("true".equalsIgnoreCase(value)) element.attr("checked", "checked");
							element.attr("value", "true");
						} else if ("radio".equalsIgnoreCase(type)) {
							if ("true".equalsIgnoreCase(value)) element.attr("checked", "checked");
							element.attr("value", "true");
						} else {
							element.attr("value", value);
						}
					} else if ("select".equalsIgnoreCase(tagName)) { // Select: <select/> bind Options <option/>
						final String attributeId = getAttributeIdFromElementId(elementId);
						final SelectOptions options = adapter.getSelectOptions(attributeId);
						if (DEBUG) {
				            StackTraceElement t = (new Throwable()).getStackTrace()[0];
				            String f = "(%s:%d) %s: element id is \"%s\", attribute id is \"%s\", options is %s";
				            Log.i(TAG, String.format(f, t.getFileName(), t.getLineNumber(), t.getMethodName(), elementId, attributeId, options));
						}
						if (options != null) {
							options.selected(value);
							final String html = options.toHTMLString();
							if (DEBUG) {
					            StackTraceElement t = (new Throwable()).getStackTrace()[0];
					            String f = "(%s:%d) %s: select options: html is \"%s\"";
					            Log.i(TAG, String.format(f, t.getFileName(), t.getLineNumber(), t.getMethodName(), html));
							}
							element.html(html);
						}
					} else {
						element.text(value);
					}
				}
			} // end while(iter.hasNext())
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected final void buildDOM(final String id, final SimpleViewAdapter adapter) {
		if (_document == null) {
			final StackTraceElement t = (new Throwable()).getStackTrace()[0];
            final String f = "(%s:%d) %s: _doc is null";
            final String error = String.format(f, t.getFileName(), t.getLineNumber(), t.getMethodName());
            if (DEBUG) Log.e(TAG, error);
			throw new BindException(error);
		} 
		
		final Element view = _document.getElementById(id);
		if (view == null) {
            StackTraceElement t = (new Throwable()).getStackTrace()[0];
            String f = "(%s:%d) %s: find element by id return a null: id = \"%s\"";
            final String error = String.format(f, t.getFileName(), t.getLineNumber(), t.getMethodName(), id);
            if (DEBUG) Log.w(TAG, error);
			throw new BindException(error);
		}

		// Such as: _form, _form[0], _form[1], ...
		final int p1 = id.indexOf('[');
		final int p2 = id.indexOf(']');
		final String array = p1 > -1 && p2 > p1 ? id.substring(p1, p2 + 1) : "";
		if (DEBUG) {
            StackTraceElement t = (new Throwable()).getStackTrace()[0];
            String f = "(%s:%d) %s: form array index is \"%s\"";
            Log.i(TAG, String.format(f, t.getFileName(), t.getLineNumber(), t.getMethodName(), array));
		}

		final Object data = adapter.getData();
		if (data == null) {
			// 清除模板中的演示数据 
		} else {
			final Map<String, String> valueMap = new HashMap<String, String>();
			final Class clazz = data.getClass();
			if (clazz.isAnnotationPresent(ID.class)) {
				final ID classID = (ID) clazz.getAnnotation(ID.class);
				final String classId = classID.identity();
				final Method[] methods = data.getClass().getMethods();
				for (final Method method : methods) {
					if (method.isAnnotationPresent(GetMethod.class)) {
						Object object = null;
						final GetMethod getMethod = method.getAnnotation(GetMethod.class);
						try {
							object = method.invoke(data); // invoke getXXX method 
						} catch (IllegalAccessException e) {
							throw new BindException(e);
						} catch (IllegalArgumentException e) {
							throw new BindException(e);
						} catch (InvocationTargetException e) {
							throw new BindException(e);
						}

						final String returnId = getMethod.returnId();
						final String returnType = getMethod.returnType();
						final StringBuffer s = new StringBuffer().append(classId); // Such as: "product", "order"
					 // if (array != null && !array.isEmpty()) s.append(array); // Such as: "[0]", "[1]"
						s.append('.').append(returnId); // Such as: ".type", ".price"
						final String elementId = s.toString(); // Such as: "product.type", "order.price"
						if (DEBUG) {
				            StackTraceElement t = (new Throwable()).getStackTrace()[0];
				            String f = "(%s:%d) %s: item element: id is \"%s\"";
				            Log.i(TAG, String.format(f, t.getFileName(), t.getLineNumber(), t.getMethodName(), elementId));
						}
						
						String value = null;
						if (object == null) {
							value = adapter.getPlaceholderForNull();
						} else if ("String".equals(returnType)) {
							value = (String) object;
						} else if ("Boolean".equals(returnType) || "boolean".equals(returnType)) {
							value = Boolean.toString((Boolean) object);
						} else if ("Integer".equals(returnType) || "int".equals(returnType)) {
							value = Integer.toString((Integer) object);
						} else if ("Long".equals(returnType) || "long".equals(returnType)) {
							value = Long.toString((Long) object);
						} else if ("Date".equals(returnType)) {
							value = _formatter.format((Date) object);
						} else {
							value = object.toString();
						}

						final SelectOptions options = adapter.getSelectOptions(returnId); // Such as: "type", "price"
						if (DEBUG) {
				            StackTraceElement t = (new Throwable()).getStackTrace()[0];
				            String f = "(%s:%d) %s: options is %s, return id is %s";
				            Log.i(TAG, String.format(f, t.getFileName(), t.getLineNumber(), t.getMethodName(), options, returnId));
						}
						if (options != null) value = options.selected(value);

						valueMap.put(elementId, value);
					}
				} // end for (final Method method : methods)
			} // end if (clazz.isAnnotationPresent(ID.class))
			
			/* 
            final Set<String> idSetOfSelectOptions = adapter.idSetOfSelectOptions();
			final Iterator<String> iter2 = idSetOfSelectOptions.iterator();
			while (iter2.hasNext()) {
				final String elementId = iter2.next();
				final Element element = form.getElementById(elementId);
				if (element != null) {
					final String itemId = getItemIdFromElementId(elementId);
					if (!valuesMap.containsKey(itemId)) { // Important !!! by York/GuangYu DENG
						final String tagName = element.tagName();
						if ("select".equalsIgnoreCase(tagName)) { // Select: <select/> bind Options <option/>
							final SelectOptions options = adapter.getSelectOptions(elementId);
							if (DEBUG) {
					            StackTraceElement t = (new Throwable()).getStackTrace()[0];
					            String f = "(%s:%d) %s: element id is \"%s\", options is %s";
					            Log.i(TAG, String.format(f, t.getFileName(), t.getLineNumber(), t.getMethodName(), elementId, options));
							}
							if (options != null) {
								final String html = options.toHTMLString();
								if (DEBUG) {
						            StackTraceElement t = (new Throwable()).getStackTrace()[0];
						            String f = "(%s:%d) %s: select options: html is \"%s\"";
						            Log.i(TAG, String.format(f, t.getFileName(), t.getLineNumber(), t.getMethodName(), html));
								}
								element.html(html);
							}
						}
					}
				}
			} */
			
			final AnchorGroup links = adapter.getAnchorGroup();
			if (links != null) {
				final ID classID = Anchor.class.getAnnotation(ID.class);
				final String classId = classID.identity();
				for (int k = 0; k < links.length(); k ++) {
					final Anchor link = links.anchor(k);
					if (link != null) {
						final Method[] mthds = link.getClass().getMethods();
						for (final Method mthd : mthds) {
							if (mthd.isAnnotationPresent(GetMethod.class)) {
								Object value = null;
								final GetMethod getMethod = mthd.getAnnotation(GetMethod.class);
								try {
									value = mthd.invoke(link); // invoke getXXX method 
								} catch (IllegalAccessException e) {
									throw new BindException(e);
								} catch (IllegalArgumentException e) {
									throw new BindException(e);
								} catch (InvocationTargetException e) {
									throw new BindException(e);
								}
								
								if (value != null) {
									final String returnId = getMethod.returnId();
									final String elementId = new StringBuffer(classId)
											.append('[').append(k).append(']')
											.append('.').append(returnId).toString(); // Such as: "link[0].url"
									valueMap.put(elementId, (String) value);
									if (DEBUG) {
							            StackTraceElement t = (new Throwable()).getStackTrace()[0];
							            String f = "(%s:%d) %s: element id is \"%s\", value is %s";
							            Log.i(TAG, String.format(f, t.getFileName(), t.getLineNumber(), t.getMethodName(), elementId, value));
									}
								}
							}
						} // end for (final Method mthd : mthds)
					}
				} // end for (int k = 0; k < links.length(); k ++)
			} // end if (links != null)

			final Set<String> idSet = valueMap.keySet();
			final Iterator<String> iter = idSet.iterator();
			while(iter.hasNext()) {
				final String elementId = iter.next();
				if (DEBUG) {
		            StackTraceElement t = (new Throwable()).getStackTrace()[0];
		            String f = "(%s:%d) %s: element id is \"%s\"";
		            Log.i(TAG, String.format(f, t.getFileName(), t.getLineNumber(), t.getMethodName(), elementId));
				}
				final Element element = view.getElementById(elementId);
				if (element != null) {
					final String value = valueMap.get(elementId);
					final String tagName = element.tagName();
					if (DEBUG) {
			            StackTraceElement t = (new Throwable()).getStackTrace()[0];
			            String f = "(%s:%d) %s: element tagName is \"%s\"";
			            Log.i(TAG, String.format(f, t.getFileName(), t.getLineNumber(), t.getMethodName(), tagName));
					}
					// Bind the value to the child element
					if ("a".equalsIgnoreCase(tagName)) { // Anchor: <a/>
						element.attr("href", value);
					} else if ("img".equalsIgnoreCase(tagName)) { // Image: <img/>
						element.attr("src", value);
					} else if ("input".equalsIgnoreCase(tagName)) { // input: <input type="hidden"/>
						element.attr("value", value);
					} else {
						element.text(value);
					}
				}
			} // end while(iter.hasNext())
		}
	}
	
	protected final void buildDOM(final String id, final SimpleMapAdapter adapter) {
		if (_document == null) {
			final StackTraceElement t = (new Throwable()).getStackTrace()[0];
            final String f = "(%s:%d) %s: _doc is null";
            final String error = String.format(f, t.getFileName(), t.getLineNumber(), t.getMethodName());
            if (DEBUG) Log.e(TAG, error);
			throw new BindException(error);
		} 
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected final void buildScript(final String id, final ListAdapter adapter) {
		final java.util.List allItems = adapter.getAllItems();
		if (allItems != null) {
			final java.util.Iterator iter = allItems.iterator();
			if (iter.hasNext()) {
				final Object item = iter.next();
				final Class clazz = item.getClass();
				if (clazz.isAnnotationPresent(ID.class)) {
					final ID classID = (ID) clazz.getAnnotation(ID.class);
					final String classId = classID.identity();
					final int index = this._scripts.size() + 1;
					final String function = new StringBuilder().append("bindList").append(index).toString();
					final String script = adapter.toJavaScript(classId, function);
					final String calling = new StringBuffer().append(function).append('(').append(')').append(';').toString();
					this._scripts.put(calling, script);
				}
			}
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected final void buildScript(final String id, final SimpleFormAdapter adapter) {
		if (_document == null) {
			final StackTraceElement t = (new Throwable()).getStackTrace()[0];
            final String f = "(%s:%d) %s: _doc is null";
            final String error = String.format(f, t.getFileName(), t.getLineNumber(), t.getMethodName());
            if (DEBUG) Log.e(TAG, error);
			throw new BindException(error);
		}
		
		final String formAction = adapter.getFormAction();
		if (formAction != null) {
			final Element container = _document.getElementById(id);
			if (container == null) {
	            StackTraceElement t = (new Throwable()).getStackTrace()[0];
	            String f = "(%s:%d) %s: find element by id return a null: id = \"%s\"";
	            final String error = String.format(f, t.getFileName(), t.getLineNumber(), t.getMethodName(), id);
	            if (DEBUG) Log.w(TAG, error);
				throw new BindException(error);
			}
			
			final Elements forms = container.getElementsByTag("form");
			if (forms == null) {
	            StackTraceElement t = (new Throwable()).getStackTrace()[0];
	            String f = "(%s:%d) %s: find form elements return a null";
	            final String error = String.format(f, t.getFileName(), t.getLineNumber(), t.getMethodName());
	            if (DEBUG) Log.w(TAG, error);
				throw new BindException(error);
			}
			
			final Element form = forms.first();
			if (form == null) {
	            StackTraceElement t = (new Throwable()).getStackTrace()[0];
	            String f = "(%s:%d) %s: get first form element return a null";
	            final String error = String.format(f, t.getFileName(), t.getLineNumber(), t.getMethodName());
	            if (DEBUG) Log.w(TAG, error);
				throw new BindException(error);
			}

			form.attr("action", formAction);
		}
		
		final Object pojo = adapter.getData();
		if (pojo != null) {
			final Class clazz = pojo.getClass();
			if (clazz.isAnnotationPresent(ID.class)) {
				final ID classID = (ID) clazz.getAnnotation(ID.class);
				final String classId = classID.identity();
				final int index = this._scripts.size() + 1;
				final String function = new StringBuilder().append("bindForm").append(index).toString();
				final String script = adapter.toJavaScript(classId, function);
				final String calling = new StringBuffer().append(function).append('(').append(')').append(';').toString();
				this._scripts.put(calling, script);
			}
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected final void buildScript(final String id, final SimpleViewAdapter adapter) {
		if (_document == null) {
			final StackTraceElement t = (new Throwable()).getStackTrace()[0];
            final String f = "(%s:%d) %s: _doc is null";
            final String error = String.format(f, t.getFileName(), t.getLineNumber(), t.getMethodName());
            if (DEBUG) Log.e(TAG, error);
			throw new BindException(error);
		} 
		
		final Object pojo = adapter.getData();
		if (pojo != null) {
			final Class clazz = pojo.getClass();
			if (clazz.isAnnotationPresent(ID.class)) {
				final ID classID = (ID) clazz.getAnnotation(ID.class);
				final String classId = classID.identity();
				final int index = this._scripts.size() + 1;
				final String function = new StringBuilder().append("bindView").append(index).toString();
				final String script = adapter.toJavaScript(classId, function);
				final String calling = new StringBuffer().append(function).append('(').append(')').append(';').toString();
				this._scripts.put(calling, script);
			}
		}
	}
	
	protected final void buildScript(final String id, final SimpleMapAdapter adapter) {
		if (_document == null) {
			final StackTraceElement t = (new Throwable()).getStackTrace()[0];
            final String f = "(%s:%d) %s: _document is null";
            final String error = String.format(f, t.getFileName(), t.getLineNumber(), t.getMethodName());
            if (DEBUG) Log.e(TAG, error);
			throw new BindException(error);
		}
	}
	
	/**
	 * 
	 * @param elementId - Such as: product.name, order.price, product[0].name, order[1].price, ... 
	 * @return The item id. Such as: product, order, product[0], order[1], ...
	 */
	protected final String getItemIdFromElementId(final String elementId) {
		String itemId = null;
		if (elementId != null) {
			int p1 = elementId.indexOf('.');
			if (p1 < 0) {
				p1 = elementId.length();
			}
			itemId = elementId.substring(0, p1);
		}
		return itemId;
	}

	/**
	 * 
	 * @param elementId - Such as: product.name, order.price, product[0].name, order[1].price, ... 
	 * @return The attribute id. Such as: name, price, ... 
	 */
	protected final String getAttributeIdFromElementId(final String elementId) {
		String attributeId = null;
		if (elementId != null) {
			int p1 = elementId.indexOf('.');
			attributeId = elementId.substring(p1 + 1, elementId.length());
		}
		return attributeId;
	}

	// View methods
	
	@Override
	public void loadHTMLResource(File file, String charset) throws IOException {
		_document = Jsoup.parse(file, charset);
	}

	@Override
	public void loadHTMLResource(URL url, String charset) throws MalformedURLException, IOException {
		_document = Jsoup.parse(url.openStream(), charset, null);
	}

	@Override
	public void loadHTMLString(String html) {
		_document = Jsoup.parse(html);
	}
	
	@Override
	public void putAdapter(final String id, final Adapter adapter) {
		_adapters.put(id, adapter);
	}

	@Override
	public String getAcceleratorClassName() {
		return _acceleratorClass;
	}

	@Override
	public String toHTMLString() {
		if (_document == null) {
            StackTraceElement t = (new Throwable()).getStackTrace()[0];
            String format = "(%s:%d) %s: _document is null";
            Log.i(TAG, String.format(format, t.getFileName(), t.getLineNumber(), t.getMethodName()));
			return "_document is null";
		}
		
		try {
			final ClassPool classPool = ClassPool.getDefault();
			
			final String className0 = ViewAccelerator.class.getName();
			final Class<?> oldClazz0 = ViewAccelerator.class;
			classPool.insertClassPath(new ClassClassPath(oldClazz0));
			
			final String className1 = SimpleHTMLViewAccelerator.class.getName();
			final Class<?> oldClazz1 = SimpleHTMLViewAccelerator.class;
	        classPool.insertClassPath(new ClassClassPath(oldClazz1));
			
			final String className2 = Testable.class.getName();
			final Class<?> oldClazz2 = Testable.class;
	        classPool.insertClassPath(new ClassClassPath(oldClazz2));
	        
	        final String className = getAcceleratorClassName();
	        final Class<?> oldClazz = Class.forName(className);
	        classPool.insertClassPath(new ClassClassPath(oldClazz));

			final AdapterJClassLoader classLoader = AdapterJClassLoader.getInstance();
			
			// Load ViewAccelerator into AdapterJClassLoader
			Class<?> newClazz0 = null;
			try {
				newClazz0 = classLoader.loadClass(className0);
			} catch (Throwable ignore) {
				// ignore
			}
			if (newClazz0 == null) {
				final CtClass newClass0 = classPool.get(className0);
				final byte[] classBytes0 = newClass0.toBytecode();
				newClass0.defrost();
				classLoader.findClassByBytes(className0, classBytes0);
			}
			
			// Load SimpleHTMLViewAccelerator into AdapterJClassLoader
			Class<?> newClazz1 = null;
			try {
				newClazz1 = classLoader.loadClass(className1);
			} catch (Throwable ignore) {
				// ignore
			}
			if (newClazz1 == null) {
				final CtClass newClass1 = classPool.get(className1);
				final byte[] classBytes1 = newClass1.toBytecode();
				newClass1.defrost();
				classLoader.findClassByBytes(className1, classBytes1);
			}
			
			// Load Testable into AdapterJClassLoader
			Class<?> newClazz2 = null;
			try {
				newClazz2 = classLoader.loadClass(className2);
			} catch (Throwable ignore) {
				// ignore
			}
			if (newClazz2 == null) {
				final CtClass newClass2 = classPool.get(className2);
				final byte[] classBytes2 = newClass2.toBytecode();
				newClass2.defrost();
				classLoader.findClassByBytes(className2, classBytes2);
			}
			
			Class<?> newClazz = null;
			try {
				newClazz = classLoader.loadClass(className);
			} catch (Throwable ignore) {
				// ignore
			}
			if (newClazz == null) {
		        final CtClass newClass = classPool.get(className);
		        
		        CtMethod oldMethod = null;
		        try {
		        	oldMethod = newClass.getDeclaredMethod("toHTMLString");
		        } catch (Throwable ignore) {
		        	// ignore
		        }
			    if (oldMethod == null) {
			    	final StringBuffer s = new StringBuffer();
			    	s.append("public String toHTMLString() {").append('\n');
			    	
			    	final Iterator<String> iter = _javas.values().iterator();
			    	while (iter.hasNext()) {
			    		s.append(iter.next());
			    	}
			    	s.append("final StringBuffer s = new StringBuffer();").append('\n');
			    	
			    	final String html = _document.toString();
					int beginIndex = 0;
					int endIndex = html.indexOf('\n');
					while (endIndex >= 0) {
						String line = html.substring(beginIndex, endIndex);
						if (!line.isEmpty()) {
							line = Entities.unescape(line);
							
							int fromIndex = 0;
							int beginJava = line.indexOf("<!--<%", fromIndex);
							int endJava = line.indexOf("%>-->", fromIndex);
							if (beginJava >= 0 && endJava >= 0) {
								while (beginJava >= 0 && endJava >= 0) {
									final String left = line.substring(fromIndex, beginJava);
									if (!left.isEmpty()) {
										s.append("s.append").append('(');
										s.append('"').append(left.replaceAll("\"", "\\\\\"")).append('"');
										s.append(')').append(';').append('\n');
									}
									
									final String java = line.substring(beginJava + 6, endJava); // 6 = "<!--<%".length()
									if (!java.isEmpty()) {
										if (java.startsWith("=")) {
											s.append("s.append").append('(');
											s.append(java.substring(1).trim());
											s.append(')').append(';').append('\n');
										} else {
											s.append(java.trim()).append('\n');
										}
									}
									
									// For next
									try {
										fromIndex = endJava + 5; // 5 = "%>-->".length()
										beginJava = line.indexOf("<!--<%", fromIndex);
										endJava = line.indexOf("%>-->", fromIndex);
									} catch (Throwable ignore) {
										// ignore
									} finally {
										if (beginJava < 0 || endJava < 0) {
											if (fromIndex < line.length()) {
												final String right = line.substring(fromIndex);
												if (!right.isEmpty()) {
													s.append("s.append").append('(');
													s.append('"').append(right.replaceAll("\"", "\\\\\"")).append("\\n").append('"');
													s.append(')').append(';').append('\n');
												}
											}
										}
									}
								}
							} else {
								s.append("s.append").append('(');
								s.append('"').append(line.replaceAll("\"", "\\\\\"")).append("\\n").append('"');
								s.append(')').append(';').append('\n');
							}
						}
						
						// For next
						try {
							beginIndex = endIndex + 1; // 1 = "\n".length()
							endIndex = html.indexOf('\n', beginIndex);
						} catch (Throwable ignore) {
							// ignore
						}  finally {
							if (endIndex <= 0) {
								Log.i(TAG, "beginIndex is " + beginIndex);
								if (beginIndex < html.length()) {
									line = html.substring(beginIndex);
//									Log.i(TAG, "Last Line: " + line);
									if (!line.isEmpty()) {
										line = Entities.unescape(line);
										
										s.append("s.append").append('(');
										s.append('"').append(line.replaceAll("\"", "\\\\\"")).append("\\n").append('"');
										s.append(')').append(';').append('\n');
									}
								}
							}
						}
					}
					s.append("return s.toString();").append('\n');
					s.append('}').append('\n');
					
					
					// 需要在生成 Java 代码字符串的时候，用 List<List<String>> (ListAdapter) 或 List<String> (POJOAdapter) 
					// 保存所有需要输出的 POJO 属性
					
					// 基于上述生成 的 Java 代码字符串，注入到 newClass 中
			    	// final String javaCode = "public String toHTMLString() { return test(); }"; // 仅用于测试 
					// final String javaCode = "public String toHTMLString() { return String.valueOf(getList2(\"_list.item\")); }"; // 仅用于测试 
					// final String javaCode = "public String toHTMLString() { getList2(\"\"); return \"OK!!!\"; }"; // 仅用于测试 
					// final String javaCode = "public String toHTMLString() { Object list21 = getList2(\"\"); return \"OK!!!\"; }"; // 仅用于测试 
					// final String javaCode = "public String toHTMLString() { java.util.List list21 = getList2(\"\"); return \"OK!!!\"; }"; // 仅用于测试 
					// final String javaCode = "public String toHTMLString() { final String id = \"_list.item\"; java.util.List list21 = getList2(id); return \"OK!!!\"; }"; // 仅用于测试 
					
					final String javaCode = s.toString();
//					Log.i(TAG, javaCode);
					final CtMethod newMethod = CtNewMethod.make(javaCode, newClass);
					newClass.addMethod(newMethod);
			    }
			    
			    final byte[] classBytes = newClass.toBytecode();
				newClass.defrost();
				newClazz = classLoader.findClassByBytes(className, classBytes);
			}
			
			final Object boost = classLoader.copyObject(newClazz, _original != null ? _original : oldClazz.newInstance());
//			Log.i(TAG, String.format("boost is %s (%s, %s)", boost, boost instanceof SimpleHTMLViewAccelerator, boost instanceof Testable));
//			Log.i(TAG, String.format("_original is %s (%s)", _original, _original instanceof SimpleHTMLViewAccelerator));
			if (_original instanceof SimpleHTMLViewAcceleratorV2) {
				Log.i(TAG, "Deep copy from _original to boost ... ");

				final SimpleHTMLViewAcceleratorV2 f = (SimpleHTMLViewAcceleratorV2) _original;

				final Set<String> idSet1 = f.idSet1();
				for (String id1 : idSet1) {
//					Log.i(TAG, "Deep copy from _original to boost .1. ");
					boost.getClass().getSuperclass().getDeclaredMethod("putList1", String.class, java.util.List.class).invoke(boost, id1, f.getList1(id1));
				}
				
				final Set<String> idSet2 = f.idSet2();
				for (String id2 : idSet2) {
					Log.i(TAG, "Deep copy from _original to boost .2. ");

//					boost.getClass().getDeclaredMethod("test");			
//					boost.getClass().getSuperclass().getDeclaredMethod("getName");
//					boost.getClass().getSuperclass().getDeclaredMethod("getList2", String.class);
					boost.getClass().getSuperclass().getDeclaredMethod("putList2", String.class, java.util.List.class).invoke(boost, id2, f.getList2(id2));
				}
			}
			
			final String dhtml = (String) boost.getClass().getDeclaredMethod("toHTMLString").invoke(boost);
            if (dhtml != null) {
            	return (dhtml);
            }
		} catch (CannotCompileException e) {
			Log.e(TAG, "CannotCompileException", e);
		} catch (NotFoundException e) {
			Log.e(TAG, "NotFoundException", e);
		} catch (IOException e) {
			Log.e(TAG, "IOException", e);
		} catch (IllegalAccessException e) {
			Log.e(TAG, "IllegalAccessException", e);
		} catch (IllegalArgumentException e) {
			Log.e(TAG, "IllegalArgumentException", e);
		} catch (InvocationTargetException e) {
			Log.e(TAG, "InvocationTargetException", e);
		} catch (NoSuchMethodException e) {
			Log.e(TAG, "NoSuchMethodException", e);
		} catch (SecurityException e) {
			Log.e(TAG, "SecurityException", e);
		} catch (Throwable e) {
			Log.e(TAG, "Throwable", e);
		}
        
		final String html = _document.toString();
		return (html);
	}

	@Override
	public void addMeta(String charset) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addMeta(String name, String content) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addMeta(int httpEquiv, String content) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addExternalScript(String type, String uri) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addExternalScript(String type, String uri, boolean async, String defer) {
		// TODO Auto-generated method stub
		
	}
}
