package com.adapterj.annotation;

import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Inherited;

/**
 * 
 * @author York/GuangYu DENG
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface List {

	/**
	 * @return Such as: "_list"
	 */
	public String classId() default "_list";

	/**
	 * @return Such as: "entry"
	 */
	public String entryId() default "entry";

	/**
	 * @return Such as: "i", "j"
	 */
	public String indexId() default "i";
		
}
