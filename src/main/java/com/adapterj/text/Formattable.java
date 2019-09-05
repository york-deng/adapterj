package com.adapterj.text;

import java.io.Serializable;

/**
 * Formattable 与 自定义的 Formatter，在 AdapterJ 中是另一个重要概念。
 * 
 * 响应请求之后，整件事情首先是获取数据，然后，一件事 就是处理 数据 与 视图 的 适配，而另一件，则是 处理数据的呈现格式，具体一点说，是对象的呈现格式。
 * 绝大多数对象的呈现格式，就是 对象 转化为 字符串 的具体格式。 
 * 对相互关联的数据域对象，同样如此，例如，订单 与 产品 这一对关联的对象，在订单中，会有一个 产品 的id，通过 产品id 就可以获得一条订单记录的产品数据。
 * 接下来的问题则是，怎样在订单中呈现 产品。在基于 HTML 与 JSON 等的应用中，产品 需要转化成怎样的 字符串 ？ 一段怎样的 文本、HTML 或 JSON ?
 * 在不同的场景下，会有不同的转换需求。怎样来适应难以预料的不同场景下的不同需求，需要我们用到 自定义的 Formatter 。
 * 而具体处理数据呈现格式的 ViewAccelerator，则需要是 Formattable 的。
 * 
 * @author York/GuangYu DENG
 *
 */
public interface Formattable extends Serializable {

	/**
	 * 
	 * @param formatter
	 */
	void setFormatter(Formatter formatter);
	
}
