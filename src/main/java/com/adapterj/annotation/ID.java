package com.adapterj.annotation;

import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 
 * @author York/GuangYu DENG
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ID {

	/**
	 * @return An identity string of POJO class, for example: "product", "order", "link" 
	 */
	public String identity() default "";
	
	/**
	 * @return An initials letter of identifier, for example: 'p', 'o', 'k'
	 */
	public String initials() default "";

}
