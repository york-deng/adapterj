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
public @interface Map {

	/**
	 * @return Such as: "_map"
	 */
	public String classId() default "_map";

	/**
	 * @return Such as: "entry" 
	 */
	public String entryId() default "entry";

	/**
	 * @return Such as: "k"
	 */
	public String keyId() default "k";

	/**
	 * @return Such as: "v" 
	 */
	public String valueId() default "v";
				
}
