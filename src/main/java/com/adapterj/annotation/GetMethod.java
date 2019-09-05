package com.adapterj.annotation;

import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 
 * @author York/GuangYu DENG
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface GetMethod {

	public String methodName() default "get";

	public String returnType() default "Object";

	public String returnId() default "";
	
	public String format() default "";
	
	public String placeholderForNull() default "";
	
}
