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
public @interface POJO {

	/**
	 * @return Such as: "_pojo"
	 */
	public String classId() default "_pojo";
			
}
