/*
 * Copyright (c) 2019 York/GuangYu Deng (york.deng@qq.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.adapterj.html;

public class Attributes {

	public static final int HTTP_EQUIV_CONTENT_LANGUAGE = 0x0101;

	public static final int HTTP_EQUIV_CONTENT_SECURITY_POLICY = 0x0102;

	public static final int HTTP_EQUIV_CONTENT_TYPE = 0x0103;

	public static final int HTTP_EQUIV_DEFAULT_STYLE = 0x0104;

	public static final int HTTP_EQUIV_REFRESH = 0x0105;

	public static final int HTTP_EQUIV_SET_COOKIE = 0x0106;
	
	/**
	 * Convert a enum/int type attribute value into its text value.
	 * 
	 * @param value the attribute enum/int value
	 * @return the attribute text value
	 */
	public static final String toString(int value) {
		String textValue = null;
		switch (value) {
			case Attributes.HTTP_EQUIV_CONTENT_LANGUAGE: {
				textValue = "content-language";
				break;
			}
			case Attributes.HTTP_EQUIV_CONTENT_SECURITY_POLICY: {
				textValue = "content-security-policy";
				break;
			}
			case Attributes.HTTP_EQUIV_CONTENT_TYPE: {
				textValue = "content-type";
				break;
			}
			case Attributes.HTTP_EQUIV_DEFAULT_STYLE: {
				textValue = "default-style";
				break;
			}
			case Attributes.HTTP_EQUIV_REFRESH: {
				textValue = "refresh";
				break;
			}
			case Attributes.HTTP_EQUIV_SET_COOKIE: {
				textValue = "set-cookie";
				break;
			}
		}
		return (textValue);
	}
}
