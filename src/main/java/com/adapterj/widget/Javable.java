package com.adapterj.widget;

/**
 * Java TAG define and ... 
 * 
 * Such as: 
 * <!--<![CDATA[JAVA[ String className = Object.class.getName(); ]]]>--> and <!--<![CDATA[JAVA[= Object.class.getName() ]]]>-->
 * 
 * @author York/GuangYu DENG
 *
 */
public interface Javable {

	String TAG_STARTS = "<!--<![CDATA[JAVA[";

	String TAG_ASSIGN = "<!--<![CDATA[JAVA[=";

	String TAG_ENDS = "]]]>-->";
	
}
