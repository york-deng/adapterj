package com.adapterj.widget;

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
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

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

import com.adapterj.algo.MD5;
import com.adapterj.annotation.GetMethod;
import com.adapterj.annotation.ID;
import com.adapterj.annotation.List;
import com.adapterj.test.Testable;
import com.adapterj.text.Formattable;
import com.adapterj.text.Formatter;
import com.adapterj.logging.Debugger;
import com.adapterj.logging.Log;

/**
 * BASED OBJECT-LIST MODEL: 
 * 生成基于 Object 与 List<Object> 的动态方法，不需要在 ClassLoader 之间复制数据。
 * 
 * 目前只支持一个 elementId 仅处理第一个被找到的 element tag。
 * 如果不考虑 HTML 规范中强调 id 的唯一性，通常认为 elementId 完全相同的 element tag 应该绑定同样的值。
 * 
 * 加速器:
 * 1. 基于 HTML 模板，动态生成一个 toHTMLString 方法，以 StringBuffer 方式输出 HTML string。 
 * 2. 每个模板文件对应一个特定的 SimpleHTMLViewAccelerator 子类，模板文件名 与 类名的 对应关系放入 view.properties 中。
 * 3. 动态修改模板对应的 SimpleHTMLViewAccelerator 子类，或者动态创建一个新的类作为加速器。
 * 4. 如果 SimpleHTMLViewAccelerator 子类 存在 且不需要更新，则 直接 调用其 toHTMLString() 方法。
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
public class SimpleHTMLView extends AbstractView implements Javable, Formattable {

	private static final long serialVersionUID = 6205756869666618426L;
	private static final boolean DEBUG = Debugger.DEBUG ? false : false;
    private static final String TAG = SimpleHTMLView.class.getName();
    
    private static final String SIMPLE_LIST_VAR_LINE1      = "final String id = \"%s\";";
    private static final String SIMPLE_LIST_VAR_LINE2      = "final com.adapterj.widget.SimpleListAdapter %s = getSimpleListAdapter(id);";
    private static final String SIMPLE_LIST_VAR_LINE3      = "final java.util.List %s = %s.getAllItems();";
    private static final String SIMPLE_LIST_VAR_LINE4      = "final java.util.List %s = %s.getAllLinkGroup();";
    private static final String SIMPLE_LIST_VAR_LINE5      = "final java.util.List %s = %s.getAllTextGroup();";
    private static final String SIMPLE_LIST_FOR_LOOP1      = "<!--<![CDATA[JAVA[ for (int %s = 0; %s < %s.size(); %s ++) { ]]]>-->";
    private static final String SIMPLE_LIST_FOR_LOOP2      = "<!--<![CDATA[JAVA[ } ]]]>-->";
    private static final String SIMPLE_LIST_STRING_VALUE   = "<!--<![CDATA[JAVA[= _formatter.format(((%s) %s.get(%s)).%s()) ]]]>-->";
    private static final String SIMPLE_LIST_BOOLEAN_VALUE  = "<!--<![CDATA[JAVA[= _formatter.format(((%s) %s.get(%s)).%s()) ]]]>-->";
    private static final String SIMPLE_LIST_INTEGER_VALUE  = "<!--<![CDATA[JAVA[= _formatter.format(((%s) %s.get(%s)).%s(), \"%s\", \"%s\") ]]]>-->";
    private static final String SIMPLE_LIST_LONG_VALUE     = "<!--<![CDATA[JAVA[= _formatter.format(((%s) %s.get(%s)).%s(), \"%s\", \"%s\") ]]]>-->";
    private static final String SIMPLE_LIST_FLOAT_VALUE    = "<!--<![CDATA[JAVA[= _formatter.format(((%s) %s.get(%s)).%s(), \"%s\", \"%s\") ]]]>-->";
    private static final String SIMPLE_LIST_DOUBLE_VALUE   = "<!--<![CDATA[JAVA[= _formatter.format(((%s) %s.get(%s)).%s(), \"%s\", \"%s\") ]]]>-->";
    private static final String SIMPLE_LIST_DATE_VALUE     = "<!--<![CDATA[JAVA[= _formatter.format(((%s) %s.get(%s)).%s(), \"%s\", \"%s\") ]]]>-->";
    private static final String SIMPLE_LIST_OBJECT_VALUE   = "<!--<![CDATA[JAVA[= _formatter.format(((%s) %s.get(%s)).%s()) ]]]>-->";
    private static final String SIMPLE_LIST_OPTION_VALUE   = "<!--<![CDATA[JAVA[= %s.getSelectOptions(\"%s\").selected(String.valueOf(((%s) %s.get(%s)).%s())) ]]]>-->";
    private static final String SIMPLE_LIST_LINK_VALUE     = "<!--<![CDATA[JAVA[= (((com.adapterj.widget.LinkGroup) %s.get(%s)).link(%d)).%s() ]]]>-->";
    private static final String SIMPLE_LIST_TEXT_VALUE     = "<!--<![CDATA[JAVA[= (((com.adapterj.widget.TextGroup) %s.get(%s)).text(%d)).%s() ]]]>-->";
    
	private static final String SIMPLE_FORM_VAR_LINE1      = "final String id = \"%s\";";
    private static final String SIMPLE_FORM_VAR_LINE2      = "final com.adapterj.widget.SimpleFormAdapter %s = getSimpleFormAdapter(id);";
    private static final String SIMPLE_FORM_VAR_LINE3      = "final %s %s = (%s) %s.getData();";
    private static final String SIMPLE_FORM_VAR_LINE4      = "final com.adapterj.widget.LinkGroup %s = %s.getLinkGroup();";
    private static final String SIMPLE_FORM_VAR_LINE5      = "final java.util.List %s = %s.getTextGroup();";
    private static final String SIMPLE_FORM_STRING_VALUE   = "<!--<![CDATA[JAVA[= _formatter.format(%s.%s(), \"\") ]]]>-->";
    private static final String SIMPLE_FORM_BOOLEAN_VALUE  = "<!--<![CDATA[JAVA[= _formatter.format(%s.%s()) ]]]>-->";
    private static final String SIMPLE_FORM_INTEGER_VALUE  = "<!--<![CDATA[JAVA[= _formatter.format(%s.%s(), \"#\", \"0\") ]]]>-->";
    private static final String SIMPLE_FORM_LONG_VALUE     = "<!--<![CDATA[JAVA[= _formatter.format(%s.%s(), \"#\", \"0\") ]]]>-->";
    private static final String SIMPLE_FORM_FLOAT_VALUE    = "<!--<![CDATA[JAVA[= _formatter.format(%s.%s(), \"#0.00\", \"0.00\") ]]]>-->";
    private static final String SIMPLE_FORM_DOUBLE_VALUE   = "<!--<![CDATA[JAVA[= _formatter.format(%s.%s(), \"#\", \"0\") ]]]>-->";
    private static final String SIMPLE_FORM_DATE_VALUE     = "<!--<![CDATA[JAVA[= _formatter.format(%s.%s(), \"%s\", \"%s\") ]]]>-->";
    private static final String SIMPLE_FORM_OBJECT_VALUE   = "<!--<![CDATA[JAVA[= _formatter.format(%s.%s()) ]]]>-->";
    private static final String SIMPLE_FORM_OPTION_VALUE   = "<!--<![CDATA[JAVA[= %s.getSelectOptions(\"%s\").selected(String.valueOf(%s.%s()), %%s) ]]]>-->";
    private static final String SIMPLE_FORM_LINK_VALUE     = "<!--<![CDATA[JAVA[= %s.link(%d).%s() ]]]>-->";
    private static final String SIMPLE_FORM_TEXT_VALUE     = "<!--<![CDATA[JAVA[= %s.text(%d).%s() ]]]>-->";
    
    private static final String SIMPLE_VIEW_VAR_LINE1      = "final String id = \"%s\";";
    private static final String SIMPLE_VIEW_VAR_LINE2      = "final com.adapterj.widget.SimpleViewAdapter %s = getSimpleViewAdapter(id);";
    private static final String SIMPLE_VIEW_VAR_LINE3      = "final %s %s = (%s) %s.getData();";
    private static final String SIMPLE_VIEW_VAR_LINE4      = "final com.adapterj.widget.LinkGroup %s = %s.getLinkGroup();";
    private static final String SIMPLE_VIEW_VAR_LINE5      = "final java.util.List %s = %s.getTextGroup();";
    private static final String SIMPLE_VIEW_STRING_VALUE   = "<!--<![CDATA[JAVA[= _formatter.format(%s.%s()) ]]]>-->";
    private static final String SIMPLE_VIEW_BOOLEAN_VALUE  = "<!--<![CDATA[JAVA[= _formatter.format(%s.%s()) ]]]>-->";
    private static final String SIMPLE_VIEW_INTEGER_VALUE  = "<!--<![CDATA[JAVA[= _formatter.format(%s.%s(), \"%s\", \"%s\") ]]]>-->";
    private static final String SIMPLE_VIEW_LONG_VALUE     = "<!--<![CDATA[JAVA[= _formatter.format(%s.%s(), \"%s\", \"%s\") ]]]>-->";
    private static final String SIMPLE_VIEW_FLOAT_VALUE    = "<!--<![CDATA[JAVA[= _formatter.format(%s.%s(), \"%s\", \"%s\") ]]]>-->";
    private static final String SIMPLE_VIEW_DOUBLE_VALUE   = "<!--<![CDATA[JAVA[= _formatter.format(%s.%s(), \"%s\", \"%s\") ]]]>-->";
    private static final String SIMPLE_VIEW_DATE_VALUE     = "<!--<![CDATA[JAVA[= _formatter.format(%s.%s(), \"%s\", \"%s\") ]]]>-->";
    private static final String SIMPLE_VIEW_OBJECT_VALUE   = "<!--<![CDATA[JAVA[= _formatter.format(%s.%s()) ]]]>-->";
    private static final String SIMPLE_VIEW_OPTION_VALUE   = "<!--<![CDATA[JAVA[= %s.getSelectOptions(\"%s\").selected(String.valueOf(%s.%s())) ]]]>-->";
    private static final String SIMPLE_VIEW_LINK_VALUE     = "<!--<![CDATA[JAVA[= %s.link(%d).%s() ]]]>-->";
    private static final String SIMPLE_VIEW_TEXT_VALUE     = "<!--<![CDATA[JAVA[= %s.text(%d).%s() ]]]>-->";
    
	private static final String SIMPLE_MAP_VAR_LINE1       = "final String id = \"%s\";";
    private static final String SIMPLE_MAP_VAR_LINE2       = "final com.adapterj.widget.SimpleMapAdapter %s = getSimpleMapAdapter(id);";
    private static final String SIMPLE_MAP_STRING_VALUE    = "<!--<![CDATA[JAVA[= _formatter.format((String) %s.getValue(\"%s\")) ]]]>-->";
    private static final String SIMPLE_MAP_BOOLEAN_VALUE   = "<!--<![CDATA[JAVA[= _formatter.format((Boolean) %s.getValue(\"%s\")) ]]]>-->";
    private static final String SIMPLE_MAP_INTEGER_VALUE   = "<!--<![CDATA[JAVA[= _formatter.format((Integer) %s.getValue(\"%s\"), \"#\", \"0\") ]]]>-->";
    private static final String SIMPLE_MAP_LONG_VALUE      = "<!--<![CDATA[JAVA[= _formatter.format((Long) %s.getValue(\"%s\"), \"#\", \"0\") ]]]>-->";
    private static final String SIMPLE_MAP_FLOAT_VALUE     = "<!--<![CDATA[JAVA[= _formatter.format((Float) %s.getValue(\"%s\"), \"#0.00\", \"0.00\") ]]]>-->";
    private static final String SIMPLE_MAP_DOUBLE_VALUE    = "<!--<![CDATA[JAVA[= _formatter.format((Double) %s.getValue(\"%s\"), \"#\", \"0\") ]]]>-->";
    private static final String SIMPLE_MAP_DATE_VALUE      = "<!--<![CDATA[JAVA[= _formatter.format((java.util.Date) %s.getValue(\"%s\")) ]]]>-->";
    private static final String SIMPLE_MAP_OBJECT_VALUE    = "<!--<![CDATA[JAVA[= _formatter.format(%s.getValue(\"%s\")) ]]]>-->";
    private static final String SIMPLE_MAP_OPTION_VALUE    = "<!--<![CDATA[JAVA[= %s.getSelectOptions(\"%s\").selected(String.valueOf(%s.%s()), %%s) ]]]>-->";
    private static final String SIMPLE_MAP_LINK_VALUE      = "<!--<![CDATA[JAVA[= %s.getLink(%s).%s() ]]]>-->";
    private static final String SIMPLE_MAP_TEXT_VALUE      = "<!--<![CDATA[JAVA[= %s.getText(%s).%s() ]]]>-->";
    
    private static final Executor executer = Executors.newFixedThreadPool(2);
    
	private final java.util.Map<String, Adapter> _adapters = new HashMap<String, Adapter>();
	private final java.util.List<String> _externalScripts = new ArrayList<String>();
	private final java.util.Map<String, String> _scripts = new HashMap<String, String>();
	private final java.util.Map<String, String> _java = new HashMap<String, String>();

	// The original ViewAccelerator class name
	private String _acceleratorClass = null;
	
	// Whether write ViewAccelerator class file
	private boolean _writeFile = true;
	
	// The original ViewAccelerator instance
	private Object _original = null;
	
	// The original HTML template Document clone
	private Document _document = null;
	
	// The original HTML template file MD5
	private String _md5 = null;
	
	/**
	 * Constructors
	 * 
	 */
	public SimpleHTMLView() {
		try {
			_acceleratorClass = SimpleHTMLViewAccelerator1.class.getName();
			
			_original = Class.forName(_acceleratorClass).newInstance();
			if (_original instanceof Testable) {
				((Testable) _original).test("It's working now !!!");
			}
		} catch (ClassNotFoundException e) {
			throw new IllegalArgumentException(String.format("ClassNotFoundException: %s", _acceleratorClass), e);
		} catch (InstantiationException e) {
			throw new IllegalArgumentException(String.format("InstantiationException: %s", _acceleratorClass), e);
		} catch (IllegalAccessException e) {
			throw new IllegalArgumentException(String.format("IllegalAccessException: %s", _acceleratorClass), e);
		} catch (Throwable e) {
			throw new IllegalArgumentException(String.format("Throwable: %s", _acceleratorClass), e);
		}
	}
	
	/**
	 * Constructors
	 * 
	 * @param writeFile
	 */
	public SimpleHTMLView(final Boolean writeFile) {
		try {
			_acceleratorClass = SimpleHTMLViewAccelerator1.class.getName();
			_writeFile = writeFile;
			
			_original = Class.forName(_acceleratorClass).newInstance();
			if (_original instanceof Testable) {
				((Testable) _original).test("It's working now !!!");
			}
		} catch (ClassNotFoundException e) {
			throw new IllegalArgumentException(String.format("ClassNotFoundException: %s", _acceleratorClass), e);
		} catch (InstantiationException e) {
			throw new IllegalArgumentException(String.format("InstantiationException: %s", _acceleratorClass), e);
		} catch (IllegalAccessException e) {
			throw new IllegalArgumentException(String.format("IllegalAccessException: %s", _acceleratorClass), e);
		} catch (Throwable e) {
			throw new IllegalArgumentException(String.format("Throwable: %s", _acceleratorClass), e);
		}
	}
	
	/**
	 * Constructors
	 * 
	 * @param acceleratorClass The ViewAccelerator class name
	 */
	public SimpleHTMLView(final String acceleratorClass) {
		if (acceleratorClass == null) {
			throw new IllegalArgumentException("accelerator class is null");
		}

		try {
			_acceleratorClass = acceleratorClass;
			
			_original = Class.forName(_acceleratorClass).newInstance();
			if (_original instanceof Testable) {
				((Testable) _original).test("It's working now !!!");
			}
		} catch (ClassNotFoundException e) {
			throw new IllegalArgumentException(String.format("ClassNotFoundException: %s", acceleratorClass), e);
		} catch (InstantiationException e) {
			throw new IllegalArgumentException(String.format("InstantiationException: %s", acceleratorClass), e);
		} catch (IllegalAccessException e) {
			throw new IllegalArgumentException(String.format("IllegalAccessException: %s", acceleratorClass), e);
		} catch (Throwable e) {
			throw new IllegalArgumentException(String.format("Throwable: %s", acceleratorClass), e);
		}
	}

	/**
	 * Constructors
	 * 
	 * @param acceleratorClass The ViewAccelerator class name
	 * @param writeFile
	 */
	public SimpleHTMLView(final String acceleratorClass, final Boolean writeFile) {
		if (acceleratorClass == null) {
			throw new IllegalArgumentException("accelerator class is null");
		}

		try {
			_acceleratorClass = acceleratorClass;
			_writeFile = writeFile;
			
			_original = Class.forName(_acceleratorClass).newInstance();
			if (_original instanceof Testable) {
				((Testable) _original).test("It's working now !!!");
			}
		} catch (ClassNotFoundException e) {
			throw new IllegalArgumentException(String.format("ClassNotFoundException: %s", acceleratorClass), e);
		} catch (InstantiationException e) {
			throw new IllegalArgumentException(String.format("InstantiationException: %s", acceleratorClass), e);
		} catch (IllegalAccessException e) {
			throw new IllegalArgumentException(String.format("IllegalAccessException: %s", acceleratorClass), e);
		} catch (Throwable e) {
			throw new IllegalArgumentException(String.format("Throwable: %s", acceleratorClass), e);
		}
	}

	/**
	 * Constructors
	 * 
	 * @param acceleratorClass The ViewAccelerator class name
	 * @param writeFile
	 * @param document The HTML template document
	 * @param md5 The MD5 of HTML template file
	 */
	public SimpleHTMLView(final Boolean writeFile, final Document document, final String md5) {
		if (document == null) {
			throw new IllegalArgumentException("document is null");
		}
		
		if (md5 == null) {
			throw new IllegalArgumentException("md5 is null");
		}
		
		try {
			_acceleratorClass = SimpleHTMLViewAccelerator1.class.getName();
			_writeFile = writeFile;
			
			_original = Class.forName(_acceleratorClass).newInstance();
			if (_original instanceof Testable) {
				((Testable) _original).test("It's working now !!!");
			}
		} catch (ClassNotFoundException e) {
			throw new IllegalArgumentException(String.format("ClassNotFoundException: %s", _acceleratorClass), e);
		} catch (InstantiationException e) {
			throw new IllegalArgumentException(String.format("InstantiationException: %s", _acceleratorClass), e);
		} catch (IllegalAccessException e) {
			throw new IllegalArgumentException(String.format("IllegalAccessException: %s", _acceleratorClass), e);
		} catch (Throwable e) {
			throw new IllegalArgumentException(String.format("Throwable: %s", _acceleratorClass), e);
		}
		
		try {
			_document = document.clone();
			
			_md5 = md5;
		} catch (Throwable e) {
			throw new IllegalArgumentException(String.format("Clone document failure: %s", e.getMessage()), e);
		}
	}

	/**
	 * Constructors
	 * 
	 * @param document The HTML template document
	 * @param md5 The MD5 of template file
	 */
	public SimpleHTMLView(final Document document, final String md5) {
		if (document == null) {
			throw new IllegalArgumentException("document is null");
		}
		
		if (md5 == null) {
			throw new IllegalArgumentException("md5 is null");
		}
		
		try {
			_acceleratorClass = SimpleHTMLViewAccelerator1.class.getName();
			
			_original = Class.forName(_acceleratorClass).newInstance();
			if (_original instanceof Testable) {
				((Testable) _original).test("It's working now !!!");
			}
		} catch (ClassNotFoundException e) {
			throw new IllegalArgumentException(String.format("ClassNotFoundException: %s", _acceleratorClass), e);
		} catch (InstantiationException e) {
			throw new IllegalArgumentException(String.format("InstantiationException: %s", _acceleratorClass), e);
		} catch (IllegalAccessException e) {
			throw new IllegalArgumentException(String.format("IllegalAccessException: %s", _acceleratorClass), e);
		} catch (Throwable e) {
			throw new IllegalArgumentException(String.format("Throwable: %s", _acceleratorClass), e);
		}
		
		try {
			_document = document.clone();
			
			_md5 = md5;
		} catch (Throwable e) {
			throw new IllegalArgumentException(String.format("Clone document failure: %s", e.getMessage()), e);
		}
	}

	/**
	 * Constructors
	 * 
	 * @param acceleratorClass The ViewAccelerator class name
	 * @param document The HTML template document
	 * @param md5 The MD5 of HTML template file
	 */
	public SimpleHTMLView(final String acceleratorClass, final Document document, final String md5) {
		if (acceleratorClass == null) {
			throw new IllegalArgumentException("accelerator class is null");
		}
		
		if (document == null) {
			throw new IllegalArgumentException("document is null");
		}
		
		if (md5 == null) {
			throw new IllegalArgumentException("md5 is null");
		}
		
		try {
			_acceleratorClass = acceleratorClass;
			
			_original = Class.forName(_acceleratorClass).newInstance();
			if (_original instanceof Testable) {
				((Testable) _original).test("It's working now !!!");
			}
		} catch (ClassNotFoundException e) {
			throw new IllegalArgumentException(String.format("ClassNotFoundException: %s", acceleratorClass), e);
		} catch (InstantiationException e) {
			throw new IllegalArgumentException(String.format("InstantiationException: %s", acceleratorClass), e);
		} catch (IllegalAccessException e) {
			throw new IllegalArgumentException(String.format("IllegalAccessException: %s", acceleratorClass), e);
		} catch (Throwable e) {
			throw new IllegalArgumentException(String.format("Throwable: %s", acceleratorClass), e);
		}
		
		try {
			_document = document.clone();
			
			_md5 = md5;
		} catch (Throwable e) {
			throw new IllegalArgumentException(String.format("Clone document failure: %s", e.getMessage()), e);
		}
	}

	/**
	 * Constructors
	 * 
	 * @param acceleratorClass The ViewAccelerator class name
	 * @param writeFile
	 * @param document The HTML template document
	 * @param md5 The MD5 of HTML template file
	 */
	public SimpleHTMLView(final String acceleratorClass, final Boolean writeFile, final Document document, final String md5) {
		if (acceleratorClass == null) {
			throw new IllegalArgumentException("accelerator class is null");
		}
		
		if (document == null) {
			throw new IllegalArgumentException("document is null");
		}
		
		if (md5 == null) {
			throw new IllegalArgumentException("md5 is null");
		}
		
		try {
			_acceleratorClass = acceleratorClass;
			_writeFile = writeFile;
			
			_original = Class.forName(_acceleratorClass).newInstance();
			if (_original instanceof Testable) {
				((Testable) _original).test("It's working now !!!");
			}
		} catch (ClassNotFoundException e) {
			throw new IllegalArgumentException(String.format("ClassNotFoundException: %s", acceleratorClass), e);
		} catch (InstantiationException e) {
			throw new IllegalArgumentException(String.format("InstantiationException: %s", acceleratorClass), e);
		} catch (IllegalAccessException e) {
			throw new IllegalArgumentException(String.format("IllegalAccessException: %s", acceleratorClass), e);
		} catch (Throwable e) {
			throw new IllegalArgumentException(String.format("Throwable: %s", acceleratorClass), e);
		}
		
		try {
			_document = document.clone();
			
			_md5 = md5;
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
				
				// Server Side Binding
				final int html = bindType & SERVER_SIDE_BINDING;
				if (html > 0) {
					bindHTML(id, adapter);
				}
				
				// Browser Side Binding
				final int script = bindType & BROWSER_SIDE_BINDING;
				if (script > 0) {
					bindScript(id, adapter);
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
	protected final void bindHTML(final String id, final Adapter adapter) {
		if (adapter instanceof ListAdapter) {
			bindHTML(id, (ListAdapter) adapter);
		} else if (adapter instanceof SimpleFormAdapter) {
			bindHTML(id, (SimpleFormAdapter) adapter);
		} else if (adapter instanceof SimpleViewAdapter) {
			bindHTML(id, (SimpleViewAdapter) adapter);
		} else if (adapter instanceof SimpleMapAdapter) {
			bindHTML(id, (SimpleMapAdapter) adapter);
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
	protected final void bindScript(final String id, final Adapter adapter) {
		if (adapter instanceof ListAdapter) {
			bindScript(id, (ListAdapter) adapter);
		} else if (adapter instanceof SimpleFormAdapter) {
			bindScript(id, (SimpleFormAdapter) adapter);
		} else if (adapter instanceof SimpleViewAdapter) {
			bindScript(id, (SimpleViewAdapter) adapter);
		} else if (adapter instanceof SimpleMapAdapter) {
			bindScript(id, (SimpleMapAdapter) adapter);
		} else {
            StackTraceElement t = (new Throwable()).getStackTrace()[0];
            String f = "(%s:%d) %s: Unimplemented bind: adapter is %s";
            String error = String.format(f, t.getFileName(), t.getLineNumber(), t.getMethodName(), adapter);
            if (DEBUG) Log.e(TAG, error);
            throw new BindException(error);
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected final void bindHTML(final String id, final ListAdapter adapter) {
		if (_document == null) {
			final StackTraceElement t = (new Throwable()).getStackTrace()[0];
			final String f = "(%s:%d) %s: _document is null";
	        final String error = String.format(f, t.getFileName(), t.getLineNumber(), t.getMethodName());
	        if (DEBUG)  Log.w(TAG, error);
			throw new BindException(error);
		} 

		if (_original instanceof SimpleHTMLViewAccelerator) {
			((SimpleHTMLViewAccelerator) _original).putAdapter(id, adapter);
		}
		
		final String className = getAcceleratorClassName();
		final ClassLoader classLoader = getClass().getClassLoader();
		Class<?> newClazz = null;
		try {
			newClazz = classLoader.loadClass(className);
		} catch (Throwable ignore) {
			// ignore
		}
		
		if (newClazz != null) {
			if (DEBUG) {
	            StackTraceElement t = (new Throwable()).getStackTrace()[0];
	            String f = "(%s:%d) %s: Get a class instance: %s";
				Log.i(TAG, String.format(f, t.getFileName(), t.getLineNumber(), t.getMethodName(), newClazz));
			}
		} else {
			final int itemCount = adapter.getItemCount();
			if (DEBUG) {
	            StackTraceElement t = (new Throwable()).getStackTrace()[0];
	            String format = "(%s:%d) %s: item count is %d";
	            Log.i(TAG, String.format(format, t.getFileName(), t.getLineNumber(), t.getMethodName(), itemCount));
			}
			if (itemCount == 0) {
				final Element element = _document.getElementById(id); // Such as: _list.item, _list[0].item, ... 
				if (element == null) {
			        final StackTraceElement t = (new Throwable()).getStackTrace()[0];
			        final String f = "(%s:%d) %s: get element by id return a null: id = \"%s\"";
			        final String error = String.format(f, t.getFileName(), t.getLineNumber(), t.getMethodName(), id);
			        if (DEBUG)  Log.w(TAG, error);
					throw new BindException(error);
				} else {
					final Boolean needRemove = true;
					afterSiblings(element, needRemove);
				}
			} else if (itemCount > 0) {
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
				final StringBuilder s = new StringBuilder();
				final String indexIj = s.append('[').append(indexId).append(']').toString(); // Such as: "[i]", "[j]" // new StringBuffer() 
				
				final int index1 = _java.size() + 1;
				
				s.delete(0, s.length());
				s.append("adapter").append(index1);
				final String adapter1 = s.toString();
				
				s.delete(0, s.length());
				s.append("list").append(index1).append('1');
				final String list11 = s.toString(); // Such as "list11", "list21", ... 
				
				s.delete(0, s.length());
				s.append("list").append(index1).append('2');
				final String list12 = s.toString(); // Such as "list12", "list22", ... 
				
				s.delete(0, s.length());
				s.append("list").append(index1).append('3');
				final String list13 = s.toString(); // Such as "list13", "list23", ... 
				
				s.delete(0, s.length());
				s.append("j").append(index1);
				final String j1 = s.toString();
				
				if (_java.get(id) == null) {
					s.delete(0, s.length());
					s.append(String.format(SIMPLE_LIST_VAR_LINE1, id)).append('\n');
					s.append(String.format(SIMPLE_LIST_VAR_LINE2, adapter1)).append('\n');
					s.append(String.format(SIMPLE_LIST_VAR_LINE3, list11, adapter1)).append('\n');
					s.append(String.format(SIMPLE_LIST_VAR_LINE4, list12, adapter1)).append('\n');
					s.append(String.format(SIMPLE_LIST_VAR_LINE5, list13, adapter1)).append('\n');
					_java.put(id, s.toString());
				}
				
				final java.util.Map<String, String> java = new LinkedHashMap<String, String>();
				final java.util.List allItems = adapter.getAllItems();
				if (allItems != null && !allItems.isEmpty()) {
					final Object sample = allItems.get(0);
					final Class clazz = sample.getClass();
					final String type = clazz.getName();
					
					final ID classID = (ID) clazz.getAnnotation(ID.class);
					final String classId = classID.identity();
					final Method[] methods = clazz.getMethods();
	
					if (clazz.isAnnotationPresent(ID.class)) {
						for (final Method method : methods) {
							if (method.isAnnotationPresent(GetMethod.class)) {
								final GetMethod getMethod = method.getAnnotation(GetMethod.class);
								final String getField = getMethod.methodName();
								final String returnId = getMethod.returnId(); // Such as: "type", "price"
								final String returnType = getMethod.returnType();
								final String format = getMethod.format();
								final String placeholder = getMethod.placeholderForNull();
								s.delete(0, s.length());
								s.append(classId); // Such as: "product", "order"
								if (array != null && !array.isEmpty()) s.append(array); // Such as: "[0]", "[1]"
								s.append(indexIj).append('.').append(returnId); // Such as: "[i].type", "[j].price"
								final String elementId = s.toString(); // Such as: "product[i].type", "order[1][j].price"

								String value = null;
								if ("String".equals(returnType)) {
									value = String.format(SIMPLE_LIST_STRING_VALUE, type, list11, j1, getField);
								} else if ("Boolean".equals(returnType) || "boolean".equals(returnType)) {
									value = String.format(SIMPLE_LIST_BOOLEAN_VALUE, type, list11, j1, getField);
								} else if ("Integer".equals(returnType) || "int".equals(returnType)) {
									value = String.format(SIMPLE_LIST_INTEGER_VALUE, type, list11, j1, getField, format, placeholder);
								} else if ("Long".equals(returnType) || "long".equals(returnType)) {
									value = String.format(SIMPLE_LIST_LONG_VALUE, type, list11, j1, getField, format, placeholder);
								} else if ("Float".equals(returnType) || "float".equals(returnType)) {
									value = String.format(SIMPLE_LIST_FLOAT_VALUE, type, list11, j1, getField, format, placeholder);
								} else if ("Double".equals(returnType) || "double".equals(returnType)) {
									value = String.format(SIMPLE_LIST_DOUBLE_VALUE, type, list11, j1, getField, format, placeholder);
								} else if ("Date".equals(returnType)) {
									value = String.format(SIMPLE_LIST_DATE_VALUE, type, list11, j1, getField, format, placeholder);
								} else {
									value = String.format(SIMPLE_LIST_OBJECT_VALUE, type, list11, j1, getField);
								}

								final SelectOptions options = adapter.getSelectOptions(returnId);
								if (options != null) {
									value = String.format(SIMPLE_LIST_OPTION_VALUE, adapter1, returnId, type, list11, j1, getField);
								}
								
								java.put(elementId, value);
							} // if (method.isAnnotationPresent(GetMethod.class)) 
						} // end for (final Method method : methods)
						
						final java.util.List<LinkGroup> allLinks = adapter.getAllLinkGroup();
						if (allLinks != null && !allLinks.isEmpty()) {
							final ID linkID = (ID) Link.class.getAnnotation(ID.class);
							final String linkId = linkID.identity();
							
							s.delete(0, s.length());
							s.append(linkId); // Such as: "link"
							if (array != null && !array.isEmpty()) s.append(array); // Such as: "[0]", "[1]"
							s.append(indexIj); // Such as: "[j]"
							final String linkIj = s.toString();
							
							final LinkGroup links = allLinks.get(0);
							if (links != null) {
								final Method[] mthds = Link.class.getMethods();
								for (int k = 0; k < links.length(); k ++) {
									for (final Method mthd : mthds) {
										if (mthd.isAnnotationPresent(GetMethod.class)) {
											final GetMethod getMethod = mthd.getAnnotation(GetMethod.class);
											final String getField = getMethod.methodName();
											final String returnId = getMethod.returnId();
											s.delete(0, s.length());
											s.append(linkIj).append('[').append(k).append(']').append('.').append(returnId).toString(); // Such as: "link[j][0].url", "link[0][j][3].title"
											final String elementId = s.toString();

											final String value = String.format(SIMPLE_LIST_LINK_VALUE, list12, j1, k, getField);
											java.put(elementId, value);
											if (DEBUG) {
									            StackTraceElement t = (new Throwable()).getStackTrace()[0];
									            String f = "(%s:%d) %s: element id is \"%s\", value is %s";
									            Log.i(TAG, String.format(f, t.getFileName(), t.getLineNumber(), t.getMethodName(), elementId, value));
											}
										} // if (mthd.isAnnotationPresent(GetMethod.class))
									} // end for (final Method mthd : mthds)
								} // end for (int k = 0; k < links.length(); k ++)
							} // end for if (links != null)
						} // end if (allLinks != null && !allLinks.isEmpty())
						
						final java.util.List<TextGroup> allTexts = adapter.getAllTextGroup();
						if (allTexts != null && !allTexts.isEmpty()) {
							final ID textID = (ID) Text.class.getAnnotation(ID.class);
							final String textId = textID.identity();
							
							s.delete(0, s.length());
							s.append(textId); // Such as: "text"
							if (array != null && !array.isEmpty()) s.append(array); // Such as: "[0]", "[1]"
							s.append(indexIj); // Such as: "[j]"
							final String textIj = s.toString();
							
							final TextGroup texts = allTexts.get(0);
							if (texts != null) {
								final Method[] mthds = Text.class.getMethods();
								for (int k = 0; k < texts.length(); k ++) {
									for (final Method mthd : mthds) {
										if (mthd.isAnnotationPresent(GetMethod.class)) {
											final GetMethod getMethod = mthd.getAnnotation(GetMethod.class);
											final String getField = getMethod.methodName();
											final String returnId = getMethod.returnId();
											s.delete(0, s.length());
											s.append(textIj).append('[').append(k).append(']').append('.').append(returnId).toString(); // Such as: "text[j][0].text", "text[0][j][3].label"
											final String elementId = s.toString();

											final String value = String.format(SIMPLE_LIST_TEXT_VALUE, list13, j1, k, getField);
											java.put(elementId, value);
											if (DEBUG) {
									            StackTraceElement t = (new Throwable()).getStackTrace()[0];
									            String f = "(%s:%d) %s: element id is \"%s\", value is %s";
									            Log.i(TAG, String.format(f, t.getFileName(), t.getLineNumber(), t.getMethodName(), elementId, value));
											}
										} // if (mthd.isAnnotationPresent(GetMethod.class))
									} // end for (final Method mthd : mthds)
								} // end for (int k = 0; k < texts.length(); k ++)
							} // end for if (texts != null)
						} // end if (allTexts != null && !allTexts.isEmpty())
					} // if (clazz.isAnnotationPresent(ID.class))
				} // end if (allItems != null && !allItems.isEmpty())

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
	
							if ("a".equalsIgnoreCase(tagName)) { // Anchor: <a/>
								childElement.attr("href", java.get(elementId));
							} else if ("img".equalsIgnoreCase(tagName)) { // Image: <img/>
								childElement.attr("src", java.get(elementId));
							} else {
								childElement.text(java.get(elementId));
							}
						}
					} // end for while(iter.hasNext())
	
					final String html = element.toString();
					element.before(String.format(SIMPLE_LIST_FOR_LOOP1, j1, j1, list11, j1));
					element.before(html);
					element.before(SIMPLE_LIST_FOR_LOOP2);
				}

				final Boolean needRemove = true;
				afterSiblings(element, needRemove);
			}
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected final void bindHTML(final String id, final SimpleFormAdapter adapter) {
		if (_document == null) {
			final StackTraceElement t = (new Throwable()).getStackTrace()[0];
			final String f = "(%s:%d) %s: _document is null";
	        final String error = String.format(f, t.getFileName(), t.getLineNumber(), t.getMethodName());
	        if (DEBUG)  Log.w(TAG, error);
			throw new BindException(error);
		} 

		if (_original instanceof SimpleHTMLViewAccelerator) {
			((SimpleHTMLViewAccelerator) _original).putAdapter(id, adapter);
		}
		
		final String className = getAcceleratorClassName();
		final ClassLoader classLoader = getClass().getClassLoader();
		Class<?> newClazz = null;
		try {
			newClazz = classLoader.loadClass(className);
		} catch (Throwable ignore) {
			// ignore
		}
		
		if (newClazz != null) {
			if (DEBUG) {
	            StackTraceElement t = (new Throwable()).getStackTrace()[0];
	            String f = "(%s:%d) %s: Get a class instance: %s";
				Log.i(TAG, String.format(f, t.getFileName(), t.getLineNumber(), t.getMethodName(), newClazz));
			}
		} else {
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

			final boolean empty = adapter.isEmpty();
			if (DEBUG) {
	            StackTraceElement t = (new Throwable()).getStackTrace()[0];
	            String format = "(%s:%d) %s: adapter is %s empty";
	            Log.i(TAG, String.format(format, t.getFileName(), t.getLineNumber(), t.getMethodName(), empty ? "" : "NOT"));
			}
			if (empty) {
				// 清除模板中的演示数据 
				// 需要找到所有的 input textarea select 等等 
				// 然后处理 select options 数据
			} else {
				// Such as: _form, _form[0], _form[1], ...
				final int p1 = id.indexOf('[');
				final int p2 = id.indexOf(']');
				final String array = p1 > -1 && p2 > p1 ? id.substring(p1, p2 + 1) : "";
				if (DEBUG) {
		            StackTraceElement t = (new Throwable()).getStackTrace()[0];
		            String format = "(%s:%d) %s: form array index is \"%s\"";
		            Log.i(TAG, String.format(format, t.getFileName(), t.getLineNumber(), t.getMethodName(), array));
				}
				
				final StringBuilder s = new StringBuilder();
				final int index1 = _java.size() + 1;
				
				s.delete(0, s.length());
				s.append("adapter").append(index1);
				final String adapter1 = s.toString();
				
				s.delete(0, s.length());
				s.append("data").append(index1);
				final String data1 = s.toString(); // Such as "data1", "data2", ... 
				
				s.delete(0, s.length());
				s.append("links").append(index1);
				final String links1 = s.toString(); // Such as "links1", "links2", ... 
				
				s.delete(0, s.length());
				s.append("texts").append(index1);
				final String texts1 = s.toString(); // Such as "texts1", "texts2", ... 
				
//				s.delete(0, s.length());
//				s.append("j").append(index1);
//				final String j1 = s.toString();
				
				final Object data = adapter.getData();
				final Class clazz = data.getClass();
				final String type = clazz.getName();
				
				if (_java.get(id) == null) {
					s.delete(0, s.length());
					s.append(String.format(SIMPLE_FORM_VAR_LINE1, id)).append('\n');
					s.append(String.format(SIMPLE_FORM_VAR_LINE2, adapter1)).append('\n');
					s.append(String.format(SIMPLE_FORM_VAR_LINE3, type, data1, type, adapter1)).append('\n');
					s.append(String.format(SIMPLE_FORM_VAR_LINE4, links1, adapter1)).append('\n');
					s.append(String.format(SIMPLE_FORM_VAR_LINE5, texts1, adapter1)).append('\n');
					_java.put(id, s.toString());
				}
				
				final java.util.Map<String, String> java = new LinkedHashMap<String, String>();
				if (true) {
					if (clazz.isAnnotationPresent(ID.class)) {
						final ID classID = (ID) clazz.getAnnotation(ID.class);
						final String classId = classID.identity();
						final Method[] methods = clazz.getMethods();
						
						for (final Method method : methods) {
							if (method.isAnnotationPresent(GetMethod.class)) {
								final GetMethod getMethod = method.getAnnotation(GetMethod.class);
								final String getField = getMethod.methodName();
								final String returnId = getMethod.returnId(); // Such as: "type", "price"
								final String returnType = getMethod.returnType();
								final String format = getMethod.format();
								final String placeholder = getMethod.placeholderForNull();
								s.delete(0, s.length());
								s.append(classId); // Such as: "product", "order"
								if (array != null && !array.isEmpty()) s.append(array); // Such as: "[0]", "[1]"
								s.append('.').append(returnId); // Such as: "[i].type", "[j].price"
								final String elementId = s.toString(); // Such as: "product[i].type", "order[1][j].price"
								
								String value = null;
								if ("String".equals(returnType)) {
									value = String.format(SIMPLE_FORM_STRING_VALUE, data1, getField);
								} else if ("Boolean".equals(returnType) || "boolean".equals(returnType)) {
									value = String.format(SIMPLE_FORM_BOOLEAN_VALUE, data1, getField);
								} else if ("Integer".equals(returnType) || "int".equals(returnType)) {
									value = String.format(SIMPLE_FORM_INTEGER_VALUE, data1, getField);
								} else if ("Long".equals(returnType) || "long".equals(returnType)) {
									value = String.format(SIMPLE_FORM_LONG_VALUE, data1, getField);
								} else if ("Float".equals(returnType) || "float".equals(returnType)) {
									value = String.format(SIMPLE_FORM_FLOAT_VALUE, data1, getField);
								} else if ("Double".equals(returnType) || "double".equals(returnType)) {
									value = String.format(SIMPLE_FORM_DOUBLE_VALUE, data1, getField);
								} else if ("Date".equals(returnType)) {
									value = String.format(SIMPLE_FORM_DATE_VALUE, data1, getField, format, placeholder);
								} else {
									value = String.format(SIMPLE_FORM_OBJECT_VALUE, data1, getField);
								}

								final SelectOptions options = adapter.getSelectOptions(returnId);
								if (options != null) {
									value = String.format(SIMPLE_FORM_OPTION_VALUE, adapter1, returnId, data1, getField);
								}
								
								java.put(elementId, value);
							} // if (method.isAnnotationPresent(GetMethod.class)) 
						} // end for (final Method method : methods)
						
						final LinkGroup links = adapter.getLinkGroup();
						if (links != null && links.length() > 0) {
							final ID linkID = (ID) Link.class.getAnnotation(ID.class);
							final String linkId = linkID.identity();
							
							s.delete(0, s.length());
							s.append(linkId); // Such as: "link"
							if (array != null && !array.isEmpty()) s.append(array); // Such as: "[0]", "[1]"
							final String linkIj = s.toString();
							
							final Method[] mthds = Link.class.getMethods();
							for (int k = 0; k < links.length(); k ++) {
								for (final Method mthd : mthds) {
									if (mthd.isAnnotationPresent(GetMethod.class)) {
										final GetMethod getMethod = mthd.getAnnotation(GetMethod.class);
										final String getField = getMethod.methodName();
										final String returnId = getMethod.returnId();
										s.delete(0, s.length());
										s.append(linkIj).append('[').append(k).append(']').append('.').append(returnId).toString(); // Such as: "link[j][0].url", "link[0][j][3].title"
										final String elementId = s.toString();
										
										final String value = String.format(SIMPLE_FORM_LINK_VALUE, links1, k, getField);
										java.put(elementId, value);
										if (DEBUG) {
								            StackTraceElement t = (new Throwable()).getStackTrace()[0];
								            String f = "(%s:%d) %s: element id is \"%s\", value is %s";
								            Log.i(TAG, String.format(f, t.getFileName(), t.getLineNumber(), t.getMethodName(), elementId, value));
										}
									} // if (mthd.isAnnotationPresent(GetMethod.class))
								} // end for (final Method mthd : mthds)
							} // end for (int k = 0; k < links.length(); k ++)
						} // end if (links != null && links.length() > 0)
						
						final TextGroup texts = adapter.getTextGroup();
						if (texts != null && texts.length() > 0) {
							final ID textID = (ID) Text.class.getAnnotation(ID.class);
							final String textId = textID.identity();
							
							s.delete(0, s.length());
							s.append(textId); // Such as: "text"
							if (array != null && !array.isEmpty()) s.append(array); // Such as: "[0]", "[1]"
							final String textIj = s.toString();
							
							final Method[] mthds = Text.class.getMethods();
							for (int k = 0; k < texts.length(); k ++) {
								for (final Method mthd : mthds) {
									if (mthd.isAnnotationPresent(GetMethod.class)) {
										final GetMethod getMethod = mthd.getAnnotation(GetMethod.class);
										final String getField = getMethod.methodName();
										final String returnId = getMethod.returnId();
										s.delete(0, s.length());
										s.append(textIj).append('[').append(k).append(']').append('.').append(returnId).toString(); // Such as: "text[j][0].url", "text[0][j][3].title"
										final String elementId = s.toString();
										
										final String value = String.format(SIMPLE_FORM_TEXT_VALUE, texts1, k, getField);
										java.put(elementId, value);
										if (DEBUG) {
								            StackTraceElement t = (new Throwable()).getStackTrace()[0];
								            String f = "(%s:%d) %s: element id is \"%s\", value is %s";
								            Log.i(TAG, String.format(f, t.getFileName(), t.getLineNumber(), t.getMethodName(), elementId, value));
										}
									} // if (mthd.isAnnotationPresent(GetMethod.class))
								} // end for (final Method mthd : mthds)
							} // end for (int k = 0; k < texts.length(); k ++)
						} // end if (texts != null && texts.length() > 0)
					} // if (clazz.isAnnotationPresent(ID.class))
				} // end if (data != null)

				if (!java.isEmpty()) {
					final Set<String> idSet = java.keySet();
					final Iterator<String> iter = idSet.iterator();
					while(iter.hasNext()) {
						final String elementId = iter.next(); /// Such as: "product[i].type", "order[1][j].price"
						final Element childElement = container.getElementById(elementId);
						if (childElement != null) {
							final String tagName = childElement.tagName();
							if (DEBUG) {
					            StackTraceElement t = (new Throwable()).getStackTrace()[0];
					            String f = "(%s:%d) %s: child clement tagName is \"%s\"";
					            Log.i(TAG, String.format(f, t.getFileName(), t.getLineNumber(), t.getMethodName(), tagName));
							}
							
							// Bind the value to the element
							if ("a".equalsIgnoreCase(tagName)) { // Anchor: <a/>
								childElement.attr("href", java.get(elementId));
							} else if ("img".equalsIgnoreCase(tagName)) { // Image: <img/>
								childElement.attr("src", java.get(elementId));
							} else if ("input".equalsIgnoreCase(tagName)) { // Input: <input/>
								final String inputType = childElement.attr("type");
								if ("text".equalsIgnoreCase(inputType)) {
									childElement.attr("value", java.get(elementId));
								} else if ("checkbox".equalsIgnoreCase(inputType)) {
									if ("true".equalsIgnoreCase(java.get(elementId))) childElement.attr("checked", "checked");
									childElement.attr("value", "true");
								} else if ("radio".equalsIgnoreCase(inputType)) {
									if ("true".equalsIgnoreCase(java.get(elementId))) childElement.attr("checked", "checked");
									childElement.attr("value", "true");
								} else {
									childElement.attr("value", java.get(elementId));
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
									childElement.html(String.format(java.get(elementId), true));
								}
							} else {
								final String attributeId = getAttributeIdFromElementId(elementId);
								final SelectOptions options = adapter.getSelectOptions(attributeId);
								if (DEBUG) {
						            StackTraceElement t = (new Throwable()).getStackTrace()[0];
						            String f = "(%s:%d) %s: element id is \"%s\", attribute id is \"%s\", options is %s";
						            Log.i(TAG, String.format(f, t.getFileName(), t.getLineNumber(), t.getMethodName(), elementId, attributeId, options));
								}
								if (options != null) {
									childElement.html(String.format(java.get(elementId), false));
								} else {
									childElement.text(java.get(elementId));
								}
							}
						} // end if (childElement != null)
					} // end for while(iter.hasNext())
				}
			}
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected final void bindHTML(final String id, final SimpleViewAdapter adapter) {
		if (_document == null) {
			final StackTraceElement t = (new Throwable()).getStackTrace()[0];
			final String f = "(%s:%d) %s: _document is null";
	        final String error = String.format(f, t.getFileName(), t.getLineNumber(), t.getMethodName());
	        if (DEBUG)  Log.w(TAG, error);
			throw new BindException(error);
		} 

		if (_original instanceof SimpleHTMLViewAccelerator) {
			((SimpleHTMLViewAccelerator) _original).putAdapter(id, adapter);
		}
		
		final String className = getAcceleratorClassName();
		final ClassLoader classLoader = getClass().getClassLoader();
		Class<?> newClazz = null;
		try {
			newClazz = classLoader.loadClass(className);
		} catch (Throwable ignore) {
			// ignore
		}
		
		if (newClazz != null) {
			if (DEBUG) {
	            StackTraceElement t = (new Throwable()).getStackTrace()[0];
	            String f = "(%s:%d) %s: Get a class instance: %s";
				Log.i(TAG, String.format(f, t.getFileName(), t.getLineNumber(), t.getMethodName(), newClazz));
			}
		} else {
			final Element container = _document.getElementById(id);
			if (container == null) {
	            StackTraceElement t = (new Throwable()).getStackTrace()[0];
	            String f = "(%s:%d) %s: find element by id return a null: id = \"%s\"";
	            final String error = String.format(f, t.getFileName(), t.getLineNumber(), t.getMethodName(), id);
	            if (DEBUG) Log.w(TAG, error);
				throw new BindException(error);
			}

			final boolean empty = adapter.isEmpty();
			if (DEBUG) {
	            StackTraceElement t = (new Throwable()).getStackTrace()[0];
	            String format = "(%s:%d) %s: adapter is %s empty";
	            Log.i(TAG, String.format(format, t.getFileName(), t.getLineNumber(), t.getMethodName(), empty ? "" : "NOT"));
			}
			if (empty) {
				// 清除模板中的演示数据 
				// 需要找到所有的 input textarea select 等等 
				// 然后处理 select options 数据
			} else {
				// Such as: _view, _view[0], _view[1], ...
				final int p1 = id.indexOf('[');
				final int p2 = id.indexOf(']');
				final String array = p1 > -1 && p2 > p1 ? id.substring(p1, p2 + 1) : "";
				if (DEBUG) {
		            StackTraceElement t = (new Throwable()).getStackTrace()[0];
		            String format = "(%s:%d) %s: view array index is \"%s\"";
		            Log.i(TAG, String.format(format, t.getFileName(), t.getLineNumber(), t.getMethodName(), array));
				}
				
				final StringBuilder s = new StringBuilder();
				final int index1 = _java.size() + 1;
				
				s.delete(0, s.length());
				s.append("adapter").append(index1);
				final String adapter1 = s.toString();
				
				s.delete(0, s.length());
				s.append("data").append(index1);
				final String data1 = s.toString(); // Such as "data1", "data2", ... 
				
				s.delete(0, s.length());
				s.append("links").append(index1);
				final String links1 = s.toString(); // Such as "links1", "links2", ... 
				
				s.delete(0, s.length());
				s.append("texts").append(index1);
				final String texts1 = s.toString(); // Such as "texts1", "texts2", ... 
				
				final Object data = adapter.getData();
				final Class clazz = data.getClass();
				final String type = clazz.getName();
				
				if (_java.get(id) == null) {
					s.delete(0, s.length());
					s.append(String.format(SIMPLE_VIEW_VAR_LINE1, id)).append('\n');
					s.append(String.format(SIMPLE_VIEW_VAR_LINE2, adapter1)).append('\n');
					s.append(String.format(SIMPLE_VIEW_VAR_LINE3, type, data1, type, adapter1)).append('\n');
					s.append(String.format(SIMPLE_VIEW_VAR_LINE4, links1, adapter1)).append('\n');
					s.append(String.format(SIMPLE_VIEW_VAR_LINE5, texts1, adapter1)).append('\n');
					_java.put(id, s.toString());
				}
				
				final java.util.Map<String, String> java = new LinkedHashMap<String, String>();
				if (true) {
					if (clazz.isAnnotationPresent(ID.class)) {
						final ID classID = (ID) clazz.getAnnotation(ID.class);
						final String classId = classID.identity();
						final Method[] methods = clazz.getMethods();
						
						for (final Method method : methods) {
							if (method.isAnnotationPresent(GetMethod.class)) {
								final GetMethod getMethod = method.getAnnotation(GetMethod.class);
								final String getField = getMethod.methodName();
								final String returnId = getMethod.returnId(); // Such as: "type", "price"
								final String returnType = getMethod.returnType();
								final String format = getMethod.format();
								final String placeholder = getMethod.placeholderForNull();
								s.delete(0, s.length());
								s.append(classId); // Such as: "product", "order"
								if (array != null && !array.isEmpty()) s.append(array); // Such as: "[0]", "[1]"
								s.append('.').append(returnId); // Such as: "[i].type", "[j].price"
								final String elementId = s.toString(); // Such as: "product[i].type", "order[1][j].price"
								
								String value = null;
								if ("String".equals(returnType)) {
									value = String.format(SIMPLE_VIEW_STRING_VALUE, data1, getField);
								} else if ("Boolean".equals(returnType) || "boolean".equals(returnType)) {
									value = String.format(SIMPLE_VIEW_BOOLEAN_VALUE, data1, getField);
								} else if ("Integer".equals(returnType) || "int".equals(returnType)) {
									value = String.format(SIMPLE_VIEW_INTEGER_VALUE, data1, getField, format, placeholder);
								} else if ("Long".equals(returnType) || "long".equals(returnType)) {
									value = String.format(SIMPLE_VIEW_LONG_VALUE, data1, getField, format, placeholder);
								} else if ("Float".equals(returnType) || "float".equals(returnType)) {
									value = String.format(SIMPLE_VIEW_FLOAT_VALUE, data1, getField, format, placeholder);
								} else if ("Double".equals(returnType) || "double".equals(returnType)) {
									value = String.format(SIMPLE_VIEW_DOUBLE_VALUE, data1, getField, format, placeholder);
								} else if ("Date".equals(returnType)) {
									value = String.format(SIMPLE_VIEW_DATE_VALUE, data1, getField, format, placeholder);
								} else {
									value = String.format(SIMPLE_VIEW_OBJECT_VALUE, data1, getField);
								}

								final SelectOptions options = adapter.getSelectOptions(returnId);
								if (options != null) {
									value = String.format(SIMPLE_VIEW_OPTION_VALUE, adapter1, returnId, data1, getField);
								}
								
								java.put(elementId, value);
							} // if (method.isAnnotationPresent(GetMethod.class)) 
						} // end for (final Method method : methods)
						
						final LinkGroup links = adapter.getLinkGroup();
						if (links != null && links.length() > 0) {
							final ID linkID = (ID) Link.class.getAnnotation(ID.class);
							final String linkId = linkID.identity();
							
							s.delete(0, s.length());
							s.append(linkId); // Such as: "link"
							if (array != null && !array.isEmpty()) s.append(array); // Such as: "[0]", "[1]"
							final String linkIj = s.toString();
							
							final Method[] mthds = Link.class.getMethods();
							for (int k = 0; k < links.length(); k ++) {
								for (final Method mthd : mthds) {
									if (mthd.isAnnotationPresent(GetMethod.class)) {
										final GetMethod getMethod = mthd.getAnnotation(GetMethod.class);
										final String getField = getMethod.methodName();
										final String returnId = getMethod.returnId();
										s.delete(0, s.length());
										s.append(linkIj).append('[').append(k).append(']').append('.').append(returnId).toString(); // Such as: "link[j][0].url", "link[0][j][3].title"
										final String elementId = s.toString();
										
										final String value = String.format(SIMPLE_VIEW_LINK_VALUE, links1, k, getField);
										java.put(elementId, value);
										if (DEBUG) {
								            StackTraceElement t = (new Throwable()).getStackTrace()[0];
								            String f = "(%s:%d) %s: element id is \"%s\", value is %s";
								            Log.i(TAG, String.format(f, t.getFileName(), t.getLineNumber(), t.getMethodName(), elementId, value));
										}
									} // if (mthd.isAnnotationPresent(GetMethod.class))
								} // end for (final Method mthd : mthds)
							} // end for (int k = 0; k < links.length(); k ++)
						} // end if (links != null && links.length() > 0)
						
						final TextGroup texts = adapter.getTextGroup();
						if (texts != null && texts.length() > 0) {
							final ID textID = (ID) Text.class.getAnnotation(ID.class);
							final String textId = textID.identity();
							
							s.delete(0, s.length());
							s.append(textId); // Such as: "text"
							if (array != null && !array.isEmpty()) s.append(array); // Such as: "[0]", "[1]"
							final String textIj = s.toString();
							
							final Method[] mthds = Text.class.getMethods();
							for (int k = 0; k < links.length(); k ++) {
								for (final Method mthd : mthds) {
									if (mthd.isAnnotationPresent(GetMethod.class)) {
										final GetMethod getMethod = mthd.getAnnotation(GetMethod.class);
										final String getField = getMethod.methodName();
										final String returnId = getMethod.returnId();
										s.delete(0, s.length());
										s.append(textIj).append('[').append(k).append(']').append('.').append(returnId).toString(); // Such as: "text[j][0].url", "text[0][j][3].title"
										final String elementId = s.toString();
										
										final String value = String.format(SIMPLE_VIEW_TEXT_VALUE, texts1, k, getField);
										java.put(elementId, value);
										if (DEBUG) {
								            StackTraceElement t = (new Throwable()).getStackTrace()[0];
								            String f = "(%s:%d) %s: element id is \"%s\", value is %s";
								            Log.i(TAG, String.format(f, t.getFileName(), t.getLineNumber(), t.getMethodName(), elementId, value));
										}
									} // if (mthd.isAnnotationPresent(GetMethod.class))
								} // end for (final Method mthd : mthds)
							} // end for (int k = 0; k < texts.length(); k ++)
						} // end if (texts != null && texts.length() > 0)
					} // if (clazz.isAnnotationPresent(ID.class))
				} // end if (data != null)

				if (!java.isEmpty()) {
					final Set<String> idSet = java.keySet();
					final Iterator<String> iter = idSet.iterator();
					while(iter.hasNext()) {
						final String elementId = iter.next(); /// Such as: "product[i].type", "order[1][j].price"
						final Element childElement = container.getElementById(elementId);
						if (childElement != null) {
							final String tagName = childElement.tagName();
							if (DEBUG) {
					            StackTraceElement t = (new Throwable()).getStackTrace()[0];
					            String f = "(%s:%d) %s: child clement tagName is \"%s\"";
					            Log.i(TAG, String.format(f, t.getFileName(), t.getLineNumber(), t.getMethodName(), tagName));
							}
							
							// Bind the value to the element
							if ("a".equalsIgnoreCase(tagName)) { // Anchor: <a/>
								childElement.attr("href", java.get(elementId));
							} else if ("img".equalsIgnoreCase(tagName)) { // Image: <img/>
								childElement.attr("src", java.get(elementId));
							} else if ("input".equalsIgnoreCase(tagName)) { // Input: <input/>
								
								if ("hidden".equalsIgnoreCase(type)) childElement.attr("value", java.get(elementId));
							} else {
								final String attributeId = getAttributeIdFromElementId(elementId);
								final SelectOptions options = adapter.getSelectOptions(attributeId);
								if (DEBUG) {
						            StackTraceElement t = (new Throwable()).getStackTrace()[0];
						            String f = "(%s:%d) %s: element id is \"%s\", attribute id is \"%s\", options is %s";
						            Log.i(TAG, String.format(f, t.getFileName(), t.getLineNumber(), t.getMethodName(), elementId, attributeId, options));
								}
								if (options != null) {
									childElement.html(String.format(java.get(elementId), false));
								} else {
									childElement.text(java.get(elementId));
								}
							}
						} // end if (childElement != null)
					} // end for while(iter.hasNext())
				}
			}
		}
	}
	
	protected final void bindHTML(final String id, final SimpleMapAdapter adapter) {
		if (_document == null) {
			final StackTraceElement t = (new Throwable()).getStackTrace()[0];
			final String f = "(%s:%d) %s: _document is null";
	        final String error = String.format(f, t.getFileName(), t.getLineNumber(), t.getMethodName());
	        if (DEBUG)  Log.w(TAG, error);
			throw new BindException(error);
		} 

		if (_original instanceof SimpleHTMLViewAccelerator) {
			((SimpleHTMLViewAccelerator) _original).putAdapter(id, adapter);
		}
		
		final String className = getAcceleratorClassName();
		final ClassLoader classLoader = getClass().getClassLoader();
		Class<?> newClazz = null;
		try {
			newClazz = classLoader.loadClass(className);
		} catch (Throwable ignore) {
			// ignore
		}
		
		if (newClazz != null) {
			if (DEBUG) {
	            StackTraceElement t = (new Throwable()).getStackTrace()[0];
	            String f = "(%s:%d) %s: Get a class instance: %s";
				Log.i(TAG, String.format(f, t.getFileName(), t.getLineNumber(), t.getMethodName(), newClazz));
			}
		} else {
			final Element container = _document.getElementById(id);
			if (container == null) {
	            StackTraceElement t = (new Throwable()).getStackTrace()[0];
	            String f = "(%s:%d) %s: find element by id return a null: id = \"%s\"";
	            final String error = String.format(f, t.getFileName(), t.getLineNumber(), t.getMethodName(), id);
	            if (DEBUG) Log.w(TAG, error);
				throw new BindException(error);
			}

			final boolean empty = adapter.isEmpty();
			if (DEBUG) {
	            StackTraceElement t = (new Throwable()).getStackTrace()[0];
	            String format = "(%s:%d) %s: adapter is %s empty";
	            Log.i(TAG, String.format(format, t.getFileName(), t.getLineNumber(), t.getMethodName(), empty ? "" : "NOT"));
			}
			if (empty) {
				// 清除模板中的演示数据 
				// 需要找到所有的 input textarea select 等等 
				// 然后处理 select options 数据
			} else {
				// Such as: _map, _map[0], _map[1], ...
				final int p1 = id.indexOf('[');
				final int p2 = id.indexOf(']');
				final String array = p1 > -1 && p2 > p1 ? id.substring(p1, p2 + 1) : "";
				if (DEBUG) {
		            StackTraceElement t = (new Throwable()).getStackTrace()[0];
		            String format = "(%s:%d) %s: view array index is \"%s\"";
		            Log.i(TAG, String.format(format, t.getFileName(), t.getLineNumber(), t.getMethodName(), array));
				}
				
				final StringBuilder s = new StringBuilder();
				final int index1 = _java.size() + 1;
				
				s.delete(0, s.length());
				s.append("adapter").append(index1);
				final String adapter1 = s.toString(); // Such as "adapter1", "adapter2", ... 
				
				final Set<String> idSetOfValue = adapter.idSetOfValue();
				if (idSetOfValue.isEmpty()) {
					// 清除模板中的演示数据 
					// 以 classId.returnId 的形式找到所有的 Element 并处理 text
					
					// 找出模板中有 id 属性的元素
					// 其 id 属性值 形如 abc.def 或 abd[i].def ... 
				} else {
					if (_java.get(id) == null) {
						s.delete(0, s.length());
						s.append(String.format(SIMPLE_MAP_VAR_LINE1, id)).append('\n');
						s.append(String.format(SIMPLE_MAP_VAR_LINE2, adapter1)).append('\n');
						_java.put(id, s.toString());
					}
					
					final Map<String, String> java = new LinkedHashMap<String, String>();
					
					final Iterator<String> iter1 = idSetOfValue.iterator();
					while (iter1.hasNext()) { // has next item 
						final String elementId = iter1.next();
						final Object value = adapter.getValue(elementId);
						
						String assignment = null;
						if (value instanceof String) {
							assignment = String.format(SIMPLE_MAP_STRING_VALUE, adapter1, elementId);
						} else if (value instanceof Boolean) {
							assignment = String.format(SIMPLE_MAP_BOOLEAN_VALUE, adapter1, elementId);
						} else if (value instanceof Integer) {
							assignment = String.format(SIMPLE_MAP_INTEGER_VALUE, adapter1, elementId);
						} else if (value instanceof Long) {
							assignment = String.format(SIMPLE_MAP_LONG_VALUE, adapter1, elementId);
						} else if (value instanceof Float) {
							assignment = String.format(SIMPLE_MAP_FLOAT_VALUE, adapter1, elementId);
						} else if (value instanceof Double) {
							assignment = String.format(SIMPLE_MAP_DOUBLE_VALUE, adapter1, elementId);
						} else if (value instanceof Date) {
							assignment = String.format(SIMPLE_MAP_DATE_VALUE, adapter1, elementId);
						} else {
							assignment = String.format(SIMPLE_MAP_OBJECT_VALUE, adapter1, elementId);
						}
		
						final SelectOptions options = adapter.getSelectOptions(elementId);
						if (options != null) {
							assignment = String.format(SIMPLE_MAP_OPTION_VALUE, adapter1, elementId, adapter1, elementId);
						}
						
						java.put(elementId, assignment);
					} // end while (iter1.hasNext())
					
					final Set<String> idSetOfLink = adapter.idSetOfLink();
					final Iterator<String> iter2 = idSetOfLink.iterator();
					while (iter2.hasNext()) {
						final String elementId = iter2.next();
						final Link link = adapter.getLink(elementId);
						if (link != null) {
							final Method[] mthds = Link.class.getMethods();
							for (final Method mthd : mthds) {
								if (mthd.isAnnotationPresent(GetMethod.class)) {
									final GetMethod getMethod = mthd.getAnnotation(GetMethod.class);
									final String getField = getMethod.methodName();
									final String assignment = String.format(SIMPLE_MAP_LINK_VALUE, adapter1, elementId, getField);
									
									java.put(elementId, assignment);
									if (DEBUG) {
							            StackTraceElement t = (new Throwable()).getStackTrace()[0];
							            String f = "(%s:%d) %s: element id is \"%s\", assignment is %s";
							            Log.i(TAG, String.format(f, t.getFileName(), t.getLineNumber(), t.getMethodName(), elementId, assignment));
									}
								} // if (mthd.isAnnotationPresent(GetMethod.class))
							} // end for (final Method mthd : mthds)
						} // end if (links != null && links.length() > 0)
					} // end while (iter2.hasNext())
					
					final Set<String> idSetOfText = adapter.idSetOfText();
					final Iterator<String> iter3 = idSetOfText.iterator();
					while (iter3.hasNext()) {
						final String elementId = iter3.next();
						final Text text = adapter.getText(elementId);
						if (text != null) {
							final Method[] mthds = Text.class.getMethods();
							for (final Method mthd : mthds) {
								if (mthd.isAnnotationPresent(GetMethod.class)) {
									final GetMethod getMethod = mthd.getAnnotation(GetMethod.class);
									final String getField = getMethod.methodName();
									final String assignment = String.format(SIMPLE_MAP_TEXT_VALUE, adapter1, elementId, getField);
									
									java.put(elementId, assignment);
									if (DEBUG) {
							            StackTraceElement t = (new Throwable()).getStackTrace()[0];
							            String f = "(%s:%d) %s: element id is \"%s\", assignment is %s";
							            Log.i(TAG, String.format(f, t.getFileName(), t.getLineNumber(), t.getMethodName(), elementId, assignment));
									}
								} // if (mthd.isAnnotationPresent(GetMethod.class))
							} // end for (final Method mthd : mthds)
						} // end if (texts != null && texts.length() > 0)
					} // end while (iter3.hasNext())
					
					if (!java.isEmpty()) {
						final Set<String> idSet = java.keySet();
						final Iterator<String> iter = idSet.iterator();
						while(iter.hasNext()) {
							final String elementId = iter.next(); // Such as: "product[i].type", "order[1][j].price"
							final Element childElement = container.getElementById(elementId);
							if (childElement != null) {
								final String tagName = childElement.tagName();
								if (DEBUG) {
						            StackTraceElement t = (new Throwable()).getStackTrace()[0];
						            String f = "(%s:%d) %s: child clement tagName is \"%s\"";
						            Log.i(TAG, String.format(f, t.getFileName(), t.getLineNumber(), t.getMethodName(), tagName));
								}
								
								// Bind the value to the element
								if ("a".equalsIgnoreCase(tagName)) { // Anchor: <a/>
									childElement.attr("href", java.get(elementId));
								} else if ("img".equalsIgnoreCase(tagName)) { // Image: <img/>
									childElement.attr("src", java.get(elementId));
								} else if ("input".equalsIgnoreCase(tagName)) { // Input: <input/>
									final String inputType = childElement.attr("type");
									if ("hidden".equalsIgnoreCase(inputType)) {
										childElement.attr("value", java.get(elementId));
									}
								} else {
									final String attributeId = getAttributeIdFromElementId(elementId);
									final SelectOptions options = adapter.getSelectOptions(attributeId);
									if (DEBUG) {
							            StackTraceElement t = (new Throwable()).getStackTrace()[0];
							            String f = "(%s:%d) %s: element id is \"%s\", attribute id is \"%s\", options is %s";
							            Log.i(TAG, String.format(f, t.getFileName(), t.getLineNumber(), t.getMethodName(), elementId, attributeId, options));
									}
									if (options != null) {
										childElement.html(String.format(java.get(elementId), false));
									} else {
										childElement.text(java.get(elementId));
									}
								}
							} // end if (childElement != null) 
						} // end for while(iter.hasNext()) 
					} // end if (!java.isEmpty()) 
				} // end if (idSetOfValue.isEmpty())
			}
		}
	}

	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected final void bindScript(final String id, final ListAdapter adapter) {
		if (_document == null) {
			final StackTraceElement t = (new Throwable()).getStackTrace()[0];
            final String f = "(%s:%d) %s: _document is null";
            final String error = String.format(f, t.getFileName(), t.getLineNumber(), t.getMethodName());
            if (DEBUG) Log.e(TAG, error);
			throw new BindException(error);
		}
		
		final java.util.List allItems = adapter.getAllItems();
		if (!allItems.isEmpty()) {
			final java.util.Iterator iter = allItems.iterator();
			if (iter.hasNext()) {
				final Object item = iter.next();
				final Class clazz = item.getClass();
				if (clazz.isAnnotationPresent(ID.class)) {
					final ID classID = (ID) clazz.getAnnotation(ID.class);
					final String classId = classID.identity();
					final int index = _scripts.size() + 1;
					final String function = new StringBuilder().append("bindList").append(index).toString();
					final String script = adapter.toJavaScript(classId, function);
					final String calling = new StringBuffer().append(function).append('(').append(')').append(';').toString();
					
					_scripts.put(calling, script);
				}
			}
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected final void bindScript(final String id, final SimpleFormAdapter adapter) {
		if (_document == null) {
			final StackTraceElement t = (new Throwable()).getStackTrace()[0];
            final String f = "(%s:%d) %s: _document is null";
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
				final int index = _scripts.size() + 1;
				final String function = new StringBuilder().append("bindForm").append(index).toString();
				final String script = adapter.toJavaScript(classId, function);
				final String calling = new StringBuffer().append(function).append('(').append(')').append(';').toString();
				
				_scripts.put(calling, script);
			}
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected final void bindScript(final String id, final SimpleViewAdapter adapter) {
		if (_document == null) {
			final StackTraceElement t = (new Throwable()).getStackTrace()[0];
            final String f = "(%s:%d) %s: _document is null";
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
				final int index = _scripts.size() + 1;
				final String function = new StringBuilder().append("bindView").append(index).toString();
				final String script = adapter.toJavaScript(classId, function);
				final String calling = new StringBuffer().append(function).append('(').append(')').append(';').toString();
				
				_scripts.put(calling, script);
			}
		}
	}
	
	protected final void bindScript(final String id, final SimpleMapAdapter adapter) {
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
	
	/**
	 * 
	 * @param element
	 * @param needRemove
	 */
	protected final void afterSiblings(final Element element, final Boolean needRemove) {
		final Element sibling = element.nextElementSibling();
		if (sibling != null) {
			afterSiblings(sibling, needRemove);
		}
		if (needRemove) element.remove();
	}
	
	/**
	 * 
	 * @param element
	 * @param list
	 * @return
	 */
	protected final java.util.List<Element> afterSiblings(final Element element, final java.util.List<Element> list) {
		list.add(element);
		
		final Element sibling = element.nextElementSibling();
		if (sibling != null) {
			afterSiblings(sibling, list);
		}
		return (list);
	}
	
	// View methods
	
	@Override
	public void loadHTMLResource(File file, String charset) throws IOException {
		_document = Jsoup.parse(file, charset);
		
		_md5 = MD5.encode(_document.html(), charset);
	}

	@Override
	public void loadHTMLResource(URL url, String charset) throws MalformedURLException, IOException {
		_document = Jsoup.parse(url.openStream(), charset, null);
		
		_md5 = MD5.encode(_document.html(), charset);
	}

	@Override
	public void loadHTMLString(String html) {
		_document = Jsoup.parse(html);
		
		_md5 = MD5.encode(_document.html(), "utf-8");
	}
	
	@Override
	public void putAdapter(final String id, final Adapter adapter) {
		_adapters.put(id, adapter);
	}

	@Override
	public String getAcceleratorClassName() {
		return (new StringBuilder()).append(_acceleratorClass).append('_').append(_md5).toString();
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
	        final String className = getAcceleratorClassName();
			final ClassLoader classLoader = getClass().getClassLoader();
			Class<?> newClazz = null;
			try {
				newClazz = classLoader.loadClass(className);
			} catch (Throwable ignore) {
				// ignore
			}
			
			if (newClazz != null) {
				if (DEBUG) {
		            StackTraceElement t = (new Throwable()).getStackTrace()[0];
		            String f = "(%s:%d) %s: Get a class instance: %s ";
					Log.i(TAG, String.format(f, t.getFileName(), t.getLineNumber(), t.getMethodName(), newClazz));
				}
			} else {
				/*
				 * 如第一次进入时数据集为空，
				 * 则不能继续构建动态加速器，
				 * 否则所构建动态加速器代码完全可能不符合预期 !!! 
				 */
				if (_java.isEmpty()) { // DON't Move, DON't Remove !!! - by York/GuangYu DENG 
					final String html = _document.toString(); // DON't Move, DON't Remove !!! - by York/GuangYu DENG 
					return (html); // DON't Move, DON't Remove !!! - by York/GuangYu DENG 
				} // DON't Move, DON't Remove !!! - by York/GuangYu DENG 
				
				final ClassPool classPool = ClassPool.getDefault();
				final Class<?> oldClazz0 = ViewAccelerator.class;
				classPool.insertClassPath(new ClassClassPath(oldClazz0));
				
				final String className1 = SimpleHTMLViewAccelerator.class.getName();
				final Class<?> oldClazz1 = SimpleHTMLViewAccelerator.class;
		        classPool.insertClassPath(new ClassClassPath(oldClazz1));
				
				final CtClass newClass = classPool.makeClass(className);
				newClass.setSuperclass(classPool.get(className1));
				
				// toHTMLString()
		        CtMethod oldMethod = null;
		        try {
		        	oldMethod = newClass.getDeclaredMethod("toHTMLString");
		        } catch (Throwable ignore) {
		        	// ignore
		        }
			    if (oldMethod != null) {
					if (DEBUG) {
			            StackTraceElement t = (new Throwable()).getStackTrace()[0];
			            String f = "(%s:%d) %s: Get a declared method: %s ";
						Log.i(TAG, String.format(f, t.getFileName(), t.getLineNumber(), t.getMethodName(), oldMethod));
					}
			    } else {
			    	final StringBuffer s = new StringBuffer();
			    	s.append("public String toHTMLString() {").append('\n');
			    	
			    	final Iterator<String> iter = _java.values().iterator();
			    	while (iter.hasNext()) {
			    		s.append(iter.next());
			    	}
			    	s.append("final StringBuffer s = new StringBuffer();").append('\n');
			    	
			    	final String html = _document.toString(); // Log.i(TAG, "HTML: \n" + html);
					int beginIndex = 0;
					int endIndex = html.indexOf('\n');
					while (endIndex >= 0) {
						String line = html.substring(beginIndex, endIndex);
						if (!line.isEmpty()) {
							line = Entities.unescape(line);
							
							int fromIndex = 0;
							int beginJava = line.indexOf(TAG_STARTS, fromIndex);
							int endJava = line.indexOf(TAG_ENDS, fromIndex);
							if (beginJava >= 0 && endJava >= 0) {
								while (beginJava >= 0 && endJava >= 0) {
									final String left = line.substring(fromIndex, beginJava);
									if (!left.isEmpty()) {
										s.append("s.append").append('(');
										s.append('"').append(left.replaceAll("\"", "\\\\\"")).append('"');
										s.append(')').append(';').append('\n');
									}
									
									final String java = line.substring(beginJava + TAG_STARTS.length(), endJava);
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
										fromIndex = endJava + TAG_ENDS.length();
										beginJava = line.indexOf(TAG_STARTS, fromIndex);
										endJava = line.indexOf(TAG_ENDS, fromIndex);
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
								if (beginIndex < html.length()) {
									line = html.substring(beginIndex);
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

					final String javaCode = s.toString(); // Log.i(TAG, "JAVA: \n" + javaCode);
					final CtMethod newMethod = CtNewMethod.make(javaCode, newClass);
					newClass.addMethod(newMethod);
					newClazz = newClass.toClass();
					
					if (_writeFile) {
						executer.execute(new Runnable() {
							@Override
							public void run() {
								try {
									newClass.writeFile(classLoader.getResource("").getPath());
									newClass.defrost();
								} catch (CannotCompileException e) {
									Log.e(TAG, "new Class write file failue", e);
								} catch (IOException e) {
									Log.e(TAG, "new Class write file failue", e);
								} catch (NullPointerException e) {
									Log.e(TAG, "new Class write file failue", e);
								} catch (Throwable e) {
									Log.e(TAG, "new Class write file failue", e);
								}
							}
						});
					}
			    }
			}

			final Object boost = classLoader.loadClass(className).newInstance();
			if (_original instanceof SimpleHTMLViewAccelerator && boost instanceof SimpleHTMLViewAccelerator) {
				final SimpleHTMLViewAccelerator from = (SimpleHTMLViewAccelerator) _original;
				final SimpleHTMLViewAccelerator to = (SimpleHTMLViewAccelerator) boost;
				
				final Set<String> idSet = from.idSet();
				for (String id : idSet) {
					to.putAdapter(id, from.getAdapter(id));
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
	
	// Formattable methods
	
	protected Formatter _formatter = new Formatter();
	
	@Override
	public void setFormatter(final Formatter formatter) {
		_formatter = formatter;
	}
}
