# Why AdapterJ？
*点击[这里](https://github.com/york-deng/adapterj/blob/master/README_CN.md)阅读[为什么需要AdapterJ (中文)](https://github.com/york-deng/adapterj/blob/master/README_CN.md)*

## A Brief Explanation   

AdapterJ is a real WYSIWYG (what you see is what you get), high-performance, java-based Web Layer framework. Its design goals are as follows: 

* Completely separate HTML and Java. 
* Do NOT introduce any special syntax, tags, tag attributes other than standard HTML and standard Java. 
* High performance. 

<div align="center">  
<img src="figure/figure_1-en.png"/>   
<p>Figure 1: Typical web application architecture based on AdapterJ (Simplified)</p>   
</div>

<br/>

The features and benefits of Web application development based on AdapterJ are as follows:
<div align="center">  
<img src="figure/table_1-en.png"/>   
<p>Table 1: The features and benefits of work with AdapterJ</p>   
</div>

The [JMH benchmark for popular Java template engines](https://github.com/york-deng/template-benchmark) was forked from [mbosecke/template-benchmark](https://github.com/mbosecke/template-benchmark), no more modifications except for adding AdapterJ.

The thinking of web application development based on AdapterJ is ```Write a static template file in standard HTML, and then you just need to adapt the data into the template in Java.```

Its approach is very similar to many front-end frameworks based on JavaScript and JSON, so I think these front-end developers will like it. It's also very similar to the GUI framework of Android and Java Swing, so I also think that many Java developers who are familiar with Android will like it.

<br/>

## A Detailed Explanation   

I will talk about the design idea of AdapterJ as below.

It started one day in March 2019.

After spending a long time focusing on building Android apps, I suddenly needed to build a web-based management application for an Android app. Time is very tight, we hope to reuse the source code of the Android app as much as possible, such as Domain Objects and Data Access Objects, so using Java to build the web application is the first choice. Even so, I still feel a strong sense of time pressure. I need to make a decision as soon as possible, but I can't choose the wrong way! In addition to reusing the Java source code already in the Android app, it is also necessary to ensure that the development of the web application is as simple and fast as possible, and I also have to make sure that the source code is easy to maintain in the future, and make sure it also keep good performance when needed.

Emotionally, I expect to have a development method similar to Ruby on Rails, but in addition to the potential performance factors, Ruby on Rails can't reuse the Java source code already in the Android app. In this case, choosing a non-java-based solution, the development cost of the web application should be difficult to control, and the maintenance cost of the **Android app + web application as a whole** should be difficult to reduce. Maintenance and upgrade of the source code is a long-term task. In addition to working hard to ensure that you need to maintain source code as little as possible. I also need to make sure that the Android app and the web application is reused each other's source code as much as possible.

This is a tricky thing! So I checked out the latest technical materials and demo source code of Struts, Spring MVC, Tapestry, FreeMarker, Thymeleaf, Velocity, JSF, ..., it’s been a long time since their beginning, but these are not any real change.
I went out to eat alone at noon, my brain kept running. I realized that there was a long-standing problem in the Web Layer. It was bad enough to mix Java with HTML and JavaScript. They also made a lot of new special tags and new special tag attributes, such as JSP tags, JSF tags, FreeMarker tags, Thymeleaf tag attributes, Velocity syntax, ..., they give us a bunch of toolboxes and a variety of exotic tools that go out of standard HTML and standard Java specifications. But NO ONE good enough 😓.

Looking back, MVC is a clean and capable guy in Windows MFC, Java Swing, Android, and even some web front-end frameworks based on JavaScript and JSON. Why is it becoming a troublesome here? This is not a complaint, but a technical issue!

In Java Swing and Android, which is used to associate simple data such as POJO, List, and Map with simple Views such as ListView and GridView, we call it Adapter. Using an Adapter to handle the association of data with views, we call it Adaptive.

What are the M, V, and C in the Web Layer? If we can say that Struts Action, Spring MVC Controller is a simple Controller (C), and POJO, Bean, Entity and their container such as List, Map is a simple Model (M), what is a simple View (V)?

Unlike Windows MFC, Java Swing, and Android applications, the web applications we talk about that's running in a production environment, its View is on a different machine than its Controller! For a web browser, HTML is its data or document. But for a web application, HTML is the View it wants to get. Usually, save these HTML codes in a web page into a HTML file, and open this HTML file with a web browser, we can still see what it was like.

When I think, if HTML is a simple View in such a web application, how should we implement a clean and capable MVC framework? If we already have one HTML file that is used as a view, how do we adapt the data into HTML view to get the same effect as Java Swing and Android? Why does the existing web framework always give us a feeling of being in the throat :confounded:？

The reason is not hard to think of, ```from PHP to JSP, JSF, and even Thymeleaf, they are "polluting" of standard HTML. The special tags and tag attributes they define, in addition to being used to adapt data into HTML, have no other use! ```A good technical solution, it should not let the developer see what they need to hide under the programming interface. ```Standard HTML, standard JavaScript, standard CSS, standard Java, these are programming interfaces, which are the syntax, tags, and attributes that developers need to know. It should not include the special syntax, tags, and attributes defined by an application framework.```

We don't want the syntax, tags, and tag attributes defined by an application framework, nor do we mix different things (Such as Java and HTML) and give them to others.

A dynamic web page can be obtained by outputting HTML directly in a Servlet (or Verticle). But this usually requires us to join these HTML string in the Servlet (or Verticle). This is an example of "polluting" Java with HTML. At this point, at another angle, since HTML is a simple View in a web application, Servlet should be a simple Controller. Servlet is originally a Controller. We can not put a large section of HTML code that belongs to View absolutely into a Controller.

Why aren't they "polluting" HTML in Java, or is it HTML "polluting" in Java? Otherwise, they must define some special syntax, tags, or attributes and then let them "polluting" HTML and Java. An application framework introduces some special syntax, tags, or attributes, and even a worse way. Unfortunately, JSP is all done:smiling_imp:！

In such web applications we discussed, HTML and Java, originally one belonged to some web browser and another belonged to any server. The original HTML is best to stay quietly where to keep it as it is, waiting to be sent to some web browser. Just, it’s so quiet! How to get a dynamic web page do we need?

Don't worry, ...

Another question, why are so many web front-end frameworks based on JavaScript and JSON that can be both clean and capable? In this web application architecture, the server only needs to be responsible for providing data, and JavaScript should adapt (or bind) the data into the HTML view. JavaScript can easily access the DOM structure of HTML, so JavaScript can do it very well. As for whether JavaScript is doing well enough, it mostly depends on the designers and developers of these web front-end frameworks.

Since JavaScript has access to the DOM structure of HTML, it has the ability to do a good job with data binding. Why not let Java handle data binding by accessing the DOM structure of HTML?

In this case, Java can find these HTML tags (to bind data) through getElementById() method.

If the id value of these HTML tags is written as "object.property" format, such as "order.amount", "product.name", "product.price", etc., by these name mapping, we have already established the association between POJO instance and HTML tags!

This is a good idea, maybe performance is a bit problematic, but intuitively this can be solved.

In addition to accessing the HTML DOM structure through Java, we can also output HTML, data and JavaScript code to the browser at one time. When the onload event of the BODY tag is triggered, the data binding should be completed by JavaScript, and the display result of the web page should be updated. This is a way to improve performance. The disadvantage is that using JavaScript to output data is not friendly to search engines, it can only be applied to certain application scenarios.

Another way to improve performance is similar to how JSP improves its performance. The web framework can dynamically generate a ViewAccelerator class from the HTML template at runtime, and splicing HTML string in this ViewAccelerator. In this way, the performance expected should be able to approximate the performance of direct splicing and outputting HTML string in Servlet (or Verticle). The JSP performance optimization method, the application server dynamically compiles a JSP into a Servlet class at runtime can be invisible to the application developers. Similarly, the web framework dynamically generating a ViewAccelerator class at runtime also can be invisible to the application developers.

When I got here, I already had its name, AdapterJ.

This name means that web application development requires us to solve in Web Layer with Java or any java-based technology, not about View, but Adapter.

```An HTML file is the only view of a web application and is a very flexible view. To match it, we need an elegant, simple and flexible Adapter!```

Due to the time pressure of the project at that time, I wrote the part that was enough to that time in two days. The rest, it spent me about a few weeks.

<div align="center">  
<img src="figure/figure_2-en.png"/>   
<p>Figure 2: AdapterJ core class diagram</p>   
</div>

<br/>

<div align="center">  
<img src="figure/figure_3-en.png"/>   
<p>Figure 3: Typical web application architecture based on AdapterJ</p>   
</div>

<br/>

<div align="center">  
<img src="figure/figure_4-en.png"/>   
<p>Figure 4: Typical request response process of the web application based on AdapterJ (sequence diagram)</p>   
</div>

<br/>

<div align="center">  
<img src="figure/chart_1.png"/>   
<p>Figure 5: JMH benchmark of the most popular Java template engines </p>   
</div>

<br/>

## Example Projects

* Example 1: [AdapterJ + Servlet](https://github.com/york-deng/adapterj-example-servlet)   

* Example 2: [AdapterJ + Spring MVC + Spring Boot](https://github.com/york-deng/adapterj-example-spring)   

* Example 3: [AdapterJ + Vert.x](https://github.com/york-deng/adapterj-example-vertx)   

* Example 4: [AdapterJ + Vert.x + Spring Boot](https://github.com/york-deng/adapterj-example-vertx-spring-boot)   

<br/>

## Benchmark Project
[JMH benchmark for popular Java template engines](https://github.com/york-deng/template-benchmark)   

<br/>

<br/>

