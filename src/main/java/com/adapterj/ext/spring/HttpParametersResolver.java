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
package com.adapterj.ext.spring;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.adapterj.widget.ParametersResolver;

// 核心:
// 1. 配置加载 - 与 Spring Framework 的集成，与 Spring Boot 的集成
// 2. 参数处理

/**
 * 
 * @author York/GuangYu DENG
 */
public interface HttpParametersResolver<T> extends ParametersResolver<HttpServletRequest, T> {

	T getParameter(final HttpServletRequest httpRequest);

	T getParameter(final HttpServletRequest httpRequest, String charset);
	
	List<T> getParametersAsList(final HttpServletRequest httpRequest);
	
	List<T> getParametersAsList(final HttpServletRequest httpRequest, String charset);

	Map<String, T> getParametersAsMap(final HttpServletRequest httpRequest);

	Map<String, T> getParametersAsMap(final HttpServletRequest httpRequest, String charset);

}
