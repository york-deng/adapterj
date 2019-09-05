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
import java.util.Map;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.adapterj.annotation.GetMethod;
import com.adapterj.annotation.ID;
import com.adapterj.annotation.List;
import com.adapterj.logging.Debugger;
import com.adapterj.logging.Log;
import com.adapterj.text.Formatter;
import com.adapterj.widget.AbstractView;
import com.adapterj.widget.Adapter;
import com.adapterj.widget.BindException;
import com.adapterj.widget.SimpleMapAdapter;
import com.adapterj.widget.Link;
import com.adapterj.widget.LinkGroup;
import com.adapterj.widget.ListAdapter;
import com.adapterj.widget.NotStandardizedHTMLException;
import com.adapterj.widget.SelectOptions;
import com.adapterj.widget.SimpleFormAdapter;
import com.adapterj.widget.SimpleViewAdapter;

/**
 * NO ACCELERATOR MODEL: 
 * 没有加速器的模式。
 * 
 * 目前只支持一个 elementId 仅处理第一个被找到的 element tag ... 如果不考虑 HTML 规范中强调 id 的唯一性，
 * 实际上，elementId 完全相同的 element tag 应该绑定同样的值 ... 
 * 
 * 服务端绑定的功能，代码已基本完成。需要把完全相同的重复代码移到 各个专门功能的函数中 
 * 浏览器端绑定，代码还没有实现。
 * 
 * @author York/GuangYu DENG
 */
public class SimpleHTMLViewV1 extends AbstractView {

	private static final boolean DEBUG = Debugger.DEBUG ? false : false;
    private static final String TAG = SimpleHTMLViewV1.class.getName();

	private final Map<String, Adapter> _adapters = new HashMap<String, Adapter>();
	
	private final Map<String, String> _scripts = new HashMap<String, String>();
	
	private final java.util.List<String> _externalScripts = new ArrayList<String>();
	
	private Document _document = null;

	/**
	 * Constructors
	 */
	public SimpleHTMLViewV1() { }
	
	/**
	 * Constructors
	 */
	public SimpleHTMLViewV1(final Document doc) {
		if (doc == null) {
			throw new IllegalArgumentException("doc is null");
		}
		_document = doc.clone();
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
			final String f = "(%s:%d) %s: _doc is null";
	        final String error = String.format(f, t.getFileName(), t.getLineNumber(), t.getMethodName());
	        if (DEBUG)  Log.w(TAG, error);
			throw new BindException(error);
		} 

		final Element element = _document.getElementById(id); // Such as: _list.item, _list[0].item, ... 
		if (element == null) {
	        final StackTraceElement t = (new Throwable()).getStackTrace()[0];
	        final String f = "(%s:%d) %s: get element by id return a null: id = \"%s\"";
	        final String error = String.format(f, t.getFileName(), t.getLineNumber(), t.getMethodName(), id);
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
				} else if ("input".equalsIgnoreCase(tagName)) { // Input: <input />
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
			final java.util.List<Map<String, String>> valueList = new ArrayList<Map<String, String>>();
			final java.util.List allItems = adapter.getAllItems();
			if (allItems != null) {
				int j = 0;
				final java.util.List<LinkGroup> allLinks = adapter.getAllLinkGroup();
				final java.util.Iterator iter = allItems.iterator();
				while (iter.hasNext()) {
					final Object item = iter.next();
					final Class clazz = item.getClass();
					if (clazz.isAnnotationPresent(ID.class)) {
						final Map<String, String> valueMap = new HashMap<String, String>();
						final ID classID = (ID) clazz.getAnnotation(ID.class);
						final String classId = classID.identity();
						final Method[] methods = clazz.getMethods();
						for (final Method method : methods) {
							if (method.isAnnotationPresent(GetMethod.class)) {
								Object object = null;
								final GetMethod getMethod = method.getAnnotation(GetMethod.class);
								try {
									object = method.invoke(item); // invoke getXXX method 
								} catch (IllegalAccessException e) {
									throw new BindException(e);
								} catch (IllegalArgumentException e) {
									throw new BindException(e);
								} catch (InvocationTargetException e) {
									throw new BindException(e);
								}
	
								final String returnId = getMethod.returnId(); // Such as: "type", "price"
								final String returnType = getMethod.returnType();
								final StringBuffer s = new StringBuffer().append(classId); // Such as: "product", "order"
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
									// 处理 Source XPath 等定义在 adapterj 中的类型 
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

						if (allLinks != null) {
							final LinkGroup links = allLinks.get(j);
							if (links != null) {
								final ID linkID = (ID) Link.class.getAnnotation(ID.class);
								final String linkId = linkID.identity();
								final StringBuffer buffer = new StringBuffer();
								buffer.append(linkId); // Such as: "link"
								if (array != null && !array.isEmpty()) buffer.append(array); // Such as: "[0]", "[1]"
								buffer.append(indexIj); // Such as: "[j]"

								for (int k = 0; k < links.length(); k ++) {
									final Link link = links.link(k);
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
													final String elementId = new StringBuffer(buffer)
															.append('[').append(k).append(']')
															.append('.').append(returnId).toString(); // Such as: "link[j][0].url", "link[0][j][3].title"
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
							}
						} // end if (allLinks != null)
						
						valueList.add(valueMap);
						j ++;
					}
				} // end while (iter.hasNext())
			} // end if (allItems != null)

			if (!valueList.isEmpty()) {
				int j = 0;
				for (final Map<String, String> valueMap : valueList) {
					final Set<String> idSet = valueMap.keySet();
					final Iterator<String> iter = idSet.iterator();
					while(iter.hasNext()) {
						final String elementId = iter.next(); /// Such as: "product[i].type", "order[1][j].price"
						final Element childElement = element.getElementById(elementId);
						if (childElement != null) {
							final String value = valueMap.get(elementId);
							final String tagName = childElement.tagName();
							if (DEBUG) {
					            StackTraceElement t = (new Throwable()).getStackTrace()[0];
					            String f = "(%s:%d) %s: child clement tagName is \"%s\"";
					            Log.i(TAG, String.format(f, t.getFileName(), t.getLineNumber(), t.getMethodName(), tagName));
							}
							
							// bind the value to the element by mapping from returnId to elementId
							if ("a".equalsIgnoreCase(tagName)) { // Anchor: <a/>
								childElement.attr("href", value);
							} else if ("img".equalsIgnoreCase(tagName)) { // Image: <img/>
								childElement.attr("src", value);
							} else {
								childElement.text(value);
							}
						}
					} // end while(iter.hasNext())

					// Such as: "[0]", "[1]"
					final String index01 = new StringBuffer().append('[').append(j).append(']').toString();
					
					// export the element into html
					// change such as "product[i].type", "order[1][j].price" into "product[0].type", "product[1].type", "order[1][3].price"
					final String html = element.toString().replace(indexIj, index01);
					
					// insert new element html before the template element
					element.before(html);
					
					j ++;
				}
				element.remove();
			} // end if (!valueList.isEmpty())
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

			final LinkGroup links = adapter.getLinkGroup();
			if (links != null) {
				final ID classID = Link.class.getAnnotation(ID.class);
				final String classId = classID.identity();
				for (int k = 0; k < links.length(); k ++) {
					final Link link = links.link(k);
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
			
			final LinkGroup links = adapter.getLinkGroup();
			if (links != null) {
				final ID classID = Link.class.getAnnotation(ID.class);
				final String classId = classID.identity();
				for (int k = 0; k < links.length(); k ++) {
					final Link link = links.link(k);
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
					final String function = new StringBuilder().append("bindList").append(this._scripts.size()).toString();
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
				final String function = new StringBuilder().append("bindForm").append(this._scripts.size()).toString();
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
				final String function = new StringBuilder().append("bindView").append(this._scripts.size()).toString();
				final String script = adapter.toJavaScript(classId, function);
				final String calling = new StringBuffer().append(function).append('(').append(')').append(';').toString();
				this._scripts.put(calling, script);
			}
		}
	}
	
	protected final void buildScript(final String id, final SimpleMapAdapter adapter) {
		if (_document == null) {
			final StackTraceElement t = (new Throwable()).getStackTrace()[0];
            final String f = "(%s:%d) %s: _doc is null";
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
	public void putAdapter(String id, Adapter adapter) {
		_adapters.put(id, adapter);
	}

	@Override
	public String getAcceleratorClassName() {
		return null;
	}

	@Override
	public String toHTMLString() {
		// 性能优化：
		// 基于 HTML 模板，动态生成一个方法，以 StringBuffer 方式输出 HTML string，
		// 在变量位置写上 _pojo.getName() 或 _list.get(i).getName() ... 等等  
		if (_document == null) {
            StackTraceElement t = (new Throwable()).getStackTrace()[0];
            String format = "(%s:%d) %s: _doc is null";
            Log.i(TAG, String.format(format, t.getFileName(), t.getLineNumber(), t.getMethodName()));
			return "_doc is null";
		}
		return _document.toString();
	}
}
