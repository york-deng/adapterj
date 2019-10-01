/*
 * This file was automatically generated by EvoSuite
 * Fri Sep 27 15:10:09 GMT 2019
 */

package com.adapterj.widget;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import com.adapterj.widget.AnchorGroup;
import com.adapterj.widget.SelectOptions;
import com.adapterj.widget.SimpleListAdapter;
import com.adapterj.widget.SimpleSelectOptions;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class) @EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = true, useJEE = true) 
public class SimpleListAdapter_ESTest extends SimpleListAdapter_ESTest_scaffolding {

  @Test(timeout = 4000)
  public void test00()  throws Throwable  {
      SimpleListAdapter<Object> simpleListAdapter0 = new SimpleListAdapter<Object>();
      HashMap<String, String> hashMap0 = new HashMap<String, String>();
      SimpleSelectOptions simpleSelectOptions0 = new SimpleSelectOptions(":QpW`e~s[l", hashMap0);
      simpleListAdapter0.addItem((Object) simpleSelectOptions0);
      List<Object> list0 = simpleListAdapter0.getAllItems();
      assertFalse(list0.isEmpty());
  }

  @Test(timeout = 4000)
  public void test01()  throws Throwable  {
      SimpleListAdapter<AnchorGroup> simpleListAdapter0 = new SimpleListAdapter<AnchorGroup>();
      AnchorGroup anchorGroup0 = new AnchorGroup(3242);
      anchorGroup0.toJSONString();
      anchorGroup0.toJSONString();
      anchorGroup0.toJSONString();
      simpleListAdapter0.addAnchorGroup(anchorGroup0);
      // Undeclared exception!
      simpleListAdapter0.toJavaScript("[f_4}\":S,O^Go_h@g.5", ", _optionsObj");
  }

  @Test(timeout = 4000)
  public void test02()  throws Throwable  {
      SimpleListAdapter<String> simpleListAdapter0 = new SimpleListAdapter<String>();
      HashMap<String, String> hashMap0 = new HashMap<String, String>();
      hashMap0.put("vxJ'RHMp24Vg-FzwQa", "");
      // Undeclared exception!
      try { 
        simpleListAdapter0.putSelectOptions("vxJ'RHMp24Vg-FzwQa", (Map<String, String>) hashMap0, "_list");
        fail("Expecting exception: RuntimeException");
      
      } catch(RuntimeException e) {
         //
         // Illegal options: text is \"\"
         //
         verifyException("com.adapterj.widget.AbstractSelectOptions", e);
      }
  }

  @Test(timeout = 4000)
  public void test03()  throws Throwable  {
      SimpleListAdapter<String> simpleListAdapter0 = new SimpleListAdapter<String>();
      HashMap<String, String> hashMap0 = new HashMap<String, String>();
      hashMap0.put("", "function () {\nvar _id = \"\";\nvar _list = [];\nvar _anchorsArray = [];\nvar _length = !_anchorsArray[0] ? 0 : _anchorsArray[0].length;\n\nvar adapter = new ADAPTERJ.widget.SimpleListAdapter(_id);\n\nfor (var i = 0; i < _list.length; i ++) {\nadapter.addItem(_list[i]);\n\nvar anchors = new ADAPTERJ.widget.AnchorGroup(_length);\nfor (var j = 0; j < _length; j ++) {\nvar anchor = !_anchorsArray[i] ? undefined : _anchorsArray[i][j];\nif (anchor) anchors.setAnchor(j, new ADAPTERJ.widget.Anchor(anchor.url, anchor.title, anchor.label));\n};\n\nadapter.addAnchorGroup(anchors);\n};\n\nadapter.bindViewHolder();\n};");
      // Undeclared exception!
      try { 
        simpleListAdapter0.putSelectOptions("YaWCF", (Map<String, String>) hashMap0);
        fail("Expecting exception: RuntimeException");
      
      } catch(RuntimeException e) {
         //
         // Illegal options: value is \"\"
         //
         verifyException("com.adapterj.widget.AbstractSelectOptions", e);
      }
  }

  @Test(timeout = 4000)
  public void test04()  throws Throwable  {
      SimpleListAdapter<String> simpleListAdapter0 = new SimpleListAdapter<String>();
      LinkedList<String> linkedList0 = new LinkedList<String>();
      linkedList0.add((String) null);
      // Undeclared exception!
      try { 
        simpleListAdapter0.putSelectOptions("function null() {\nvar _id = \"null\";\nvar _list = [];\nvar _anchorsArray = [];\nvar _length = !_anchorsArray[0] ? 0 : _anchorsArray[0].length;\n\nvar adapter = new ADAPTERJ.widget.SimpleListAdapter(_id);\n\nfor (var i = 0; i < _list.length; i ++) {\nadapter.addItem(_list[i]);\n\nvar anchors = new ADAPTERJ.widget.AnchorGroup(_length);\nfor (var j = 0; j < _length; j ++) {\nvar anchor = !_anchorsArray[i] ? undefined : _anchorsArray[i][j];\nif (anchor) anchors.setAnchor(j, new ADAPTERJ.widget.Anchor(anchor.url, anchor.title, anchor.label));\n};\n\nadapter.addAnchorGroup(anchors);\n};\n\nadapter.bindViewHolder();\n};", (List<String>) linkedList0, 0);
        fail("Expecting exception: RuntimeException");
      
      } catch(RuntimeException e) {
         //
         // Illegal options: text is null
         //
         verifyException("com.adapterj.widget.AbstractSelectOptions", e);
      }
  }

  @Test(timeout = 4000)
  public void test05()  throws Throwable  {
      SimpleListAdapter<Object> simpleListAdapter0 = new SimpleListAdapter<Object>();
      LinkedList<String> linkedList0 = new LinkedList<String>();
      linkedList0.add("");
      // Undeclared exception!
      try { 
        simpleListAdapter0.putSelectOptions("v6+", (List<String>) linkedList0);
        fail("Expecting exception: RuntimeException");
      
      } catch(RuntimeException e) {
         //
         // Illegal options: text is \"\"
         //
         verifyException("com.adapterj.widget.AbstractSelectOptions", e);
      }
  }

  @Test(timeout = 4000)
  public void test06()  throws Throwable  {
      SimpleListAdapter<String> simpleListAdapter0 = new SimpleListAdapter<String>();
      // Undeclared exception!
      try { 
        simpleListAdapter0.getItem(0);
        fail("Expecting exception: IndexOutOfBoundsException");
      
      } catch(IndexOutOfBoundsException e) {
         //
         // Index: 0, Size: 0
         //
         verifyException("java.util.ArrayList", e);
      }
  }

  @Test(timeout = 4000)
  public void test07()  throws Throwable  {
      SimpleListAdapter<AnchorGroup> simpleListAdapter0 = new SimpleListAdapter<AnchorGroup>();
      AnchorGroup anchorGroup0 = new AnchorGroup(3242);
      simpleListAdapter0.addAnchorGroup(anchorGroup0);
      simpleListAdapter0.addAnchorGroup(anchorGroup0);
      String string0 = simpleListAdapter0.toJavaScript("[f_4}\":S,O^Go_h@g.5", ", _optionsObj");
      assertNotNull(string0);
  }

  @Test(timeout = 4000)
  public void test08()  throws Throwable  {
      SimpleListAdapter<Integer> simpleListAdapter0 = new SimpleListAdapter<Integer>();
      simpleListAdapter0.addAnchorGroup((AnchorGroup) null);
      String string0 = simpleListAdapter0.toJavaScript("", (String) null);
      assertEquals("function null() {\nvar _id = \"\";\nvar _list = [];\nvar _anchorsArray = [];\nvar _length = !_anchorsArray[0] ? 0 : _anchorsArray[0].length;\n\nvar adapter = new ADAPTERJ.widget.SimpleListAdapter(_id);\n\nfor (var i = 0; i < _list.length; i ++) {\nadapter.addItem(_list[i]);\n\nvar anchors = new ADAPTERJ.widget.AnchorGroup(_length);\nfor (var j = 0; j < _length; j ++) {\nvar anchor = !_anchorsArray[i] ? undefined : _anchorsArray[i][j];\nif (anchor) anchors.setAnchor(j, new ADAPTERJ.widget.Anchor(anchor.url, anchor.title, anchor.label));\n};\n\nadapter.addAnchorGroup(anchors);\n};\n\nadapter.bindViewHolder();\n};", string0);
  }

  @Test(timeout = 4000)
  public void test09()  throws Throwable  {
      SimpleListAdapter<Integer> simpleListAdapter0 = new SimpleListAdapter<Integer>();
      LinkedList<String> linkedList0 = new LinkedList<String>();
      linkedList0.add("j;q}fL_G2-VGE&03 o");
      simpleListAdapter0.putSelectOptions("*.n", (List<String>) linkedList0, (-1483));
      assertEquals("", simpleListAdapter0.getPlaceholderForNull());
  }

  @Test(timeout = 4000)
  public void test10()  throws Throwable  {
      SimpleListAdapter<AnchorGroup> simpleListAdapter0 = new SimpleListAdapter<AnchorGroup>();
      LinkedList<String> linkedList0 = new LinkedList<String>();
      // Undeclared exception!
      try { 
        simpleListAdapter0.putSelectOptions("p67~]Oh}L:'eyMCW_o", (List<String>) linkedList0, (-175));
        fail("Expecting exception: IllegalArgumentException");
      
      } catch(IllegalArgumentException e) {
         //
         // Illegal argument: options is empty
         //
         verifyException("com.adapterj.widget.SimpleListAdapter", e);
      }
  }

  @Test(timeout = 4000)
  public void test11()  throws Throwable  {
      LinkedList<String> linkedList0 = new LinkedList<String>();
      SimpleListAdapter<String> simpleListAdapter0 = new SimpleListAdapter<String>();
      // Undeclared exception!
      try { 
        simpleListAdapter0.putSelectOptions("", (List<String>) linkedList0, (-1483));
        fail("Expecting exception: IllegalArgumentException");
      
      } catch(IllegalArgumentException e) {
         //
         // Illegal argument: id is \"\"
         //
         verifyException("com.adapterj.widget.SimpleListAdapter", e);
      }
  }

  @Test(timeout = 4000)
  public void test12()  throws Throwable  {
      SimpleListAdapter<String> simpleListAdapter0 = new SimpleListAdapter<String>();
      // Undeclared exception!
      try { 
        simpleListAdapter0.putSelectOptions("can NOT get item id", (List<String>) null, (-714));
        fail("Expecting exception: IllegalArgumentException");
      
      } catch(IllegalArgumentException e) {
         //
         // Illegal argument: options is empty
         //
         verifyException("com.adapterj.widget.SimpleListAdapter", e);
      }
  }

  @Test(timeout = 4000)
  public void test13()  throws Throwable  {
      SimpleListAdapter<Integer> simpleListAdapter0 = new SimpleListAdapter<Integer>();
      LinkedList<String> linkedList0 = new LinkedList<String>();
      // Undeclared exception!
      try { 
        simpleListAdapter0.putSelectOptions((String) null, (List<String>) linkedList0, (-35));
        fail("Expecting exception: IllegalArgumentException");
      
      } catch(IllegalArgumentException e) {
         //
         // Illegal argument: id is null
         //
         verifyException("com.adapterj.widget.SimpleListAdapter", e);
      }
  }

  @Test(timeout = 4000)
  public void test14()  throws Throwable  {
      SimpleListAdapter<Object> simpleListAdapter0 = new SimpleListAdapter<Object>();
      LinkedList<String> linkedList0 = new LinkedList<String>();
      linkedList0.add("v6+");
      simpleListAdapter0.putSelectOptions("v6+", (List<String>) linkedList0);
      assertNull(simpleListAdapter0.toJSONString());
  }

  @Test(timeout = 4000)
  public void test15()  throws Throwable  {
      SimpleListAdapter<Object> simpleListAdapter0 = new SimpleListAdapter<Object>();
      // Undeclared exception!
      try { 
        simpleListAdapter0.putSelectOptions("?HU", (List<String>) null);
        fail("Expecting exception: IllegalArgumentException");
      
      } catch(IllegalArgumentException e) {
         //
         // Illegal argument: options is empty
         //
         verifyException("com.adapterj.widget.SimpleListAdapter", e);
      }
  }

  @Test(timeout = 4000)
  public void test16()  throws Throwable  {
      SimpleListAdapter<Object> simpleListAdapter0 = new SimpleListAdapter<Object>();
      LinkedList<String> linkedList0 = new LinkedList<String>();
      // Undeclared exception!
      try { 
        simpleListAdapter0.putSelectOptions("", (List<String>) linkedList0);
        fail("Expecting exception: IllegalArgumentException");
      
      } catch(IllegalArgumentException e) {
         //
         // Illegal argument: id is \"\"
         //
         verifyException("com.adapterj.widget.SimpleListAdapter", e);
      }
  }

  @Test(timeout = 4000)
  public void test17()  throws Throwable  {
      SimpleListAdapter<Object> simpleListAdapter0 = new SimpleListAdapter<Object>();
      LinkedList<String> linkedList0 = new LinkedList<String>();
      // Undeclared exception!
      try { 
        simpleListAdapter0.putSelectOptions("v6+", (List<String>) linkedList0);
        fail("Expecting exception: IllegalArgumentException");
      
      } catch(IllegalArgumentException e) {
         //
         // Illegal argument: options is empty
         //
         verifyException("com.adapterj.widget.SimpleListAdapter", e);
      }
  }

  @Test(timeout = 4000)
  public void test18()  throws Throwable  {
      SimpleListAdapter<Object> simpleListAdapter0 = new SimpleListAdapter<Object>();
      LinkedList<String> linkedList0 = new LinkedList<String>();
      // Undeclared exception!
      try { 
        simpleListAdapter0.putSelectOptions((String) null, (List<String>) linkedList0);
        fail("Expecting exception: IllegalArgumentException");
      
      } catch(IllegalArgumentException e) {
         //
         // Illegal argument: id is null
         //
         verifyException("com.adapterj.widget.SimpleListAdapter", e);
      }
  }

  @Test(timeout = 4000)
  public void test19()  throws Throwable  {
      HashMap<String, String> hashMap0 = new HashMap<String, String>();
      hashMap0.put("if (anchor) anchors.setAnchor(j, new ADAPTERJ.widget.Anchor(anchor.url, anchor.title, anchor.label));", "function ]3&NZA})di:j,(\"a() {\nvar _id = \"]3&NZA})di:j,(\"a\";\nvar _list = [];\nvar _anchorsArray = [];\nvar _length = !_anchorsArray[0] ? 0 : _anchorsArray[0].length;\n\nvar adapter = new ADAPTERJ.widget.SimpleListAdapter(_id);\n\nfor (var i = 0; i < _list.length; i ++) {\nadapter.addItem(_list[i]);\n\nvar anchors = new ADAPTERJ.widget.AnchorGroup(_length);\nfor (var j = 0; j < _length; j ++) {\nvar anchor = !_anchorsArray[i] ? undefined : _anchorsArray[i][j];\nif (anchor) anchors.setAnchor(j, new ADAPTERJ.widget.Anchor(anchor.url, anchor.title, anchor.label));\n};\n\nadapter.addAnchorGroup(anchors);\n};\n\nadapter.bindViewHolder();\n};");
      SimpleListAdapter<Object> simpleListAdapter0 = new SimpleListAdapter<Object>();
      simpleListAdapter0.putSelectOptions("_optionsObj", (Map<String, String>) hashMap0, "_optionsObj");
      assertEquals("", simpleListAdapter0.getPlaceholderForNull());
  }

  @Test(timeout = 4000)
  public void test20()  throws Throwable  {
      SimpleListAdapter<SimpleSelectOptions> simpleListAdapter0 = new SimpleListAdapter<SimpleSelectOptions>();
      // Undeclared exception!
      try { 
        simpleListAdapter0.putSelectOptions("option", (Map<String, String>) null, "option");
        fail("Expecting exception: IllegalArgumentException");
      
      } catch(IllegalArgumentException e) {
         //
         // Illegal argument: options is empty
         //
         verifyException("com.adapterj.widget.SimpleListAdapter", e);
      }
  }

  @Test(timeout = 4000)
  public void test21()  throws Throwable  {
      SimpleListAdapter<Integer> simpleListAdapter0 = new SimpleListAdapter<Integer>();
      HashMap<String, String> hashMap0 = new HashMap<String, String>();
      // Undeclared exception!
      try { 
        simpleListAdapter0.putSelectOptions("", (Map<String, String>) hashMap0, "");
        fail("Expecting exception: IllegalArgumentException");
      
      } catch(IllegalArgumentException e) {
         //
         // Illegal argument: id is \"\"
         //
         verifyException("com.adapterj.widget.SimpleListAdapter", e);
      }
  }

  @Test(timeout = 4000)
  public void test22()  throws Throwable  {
      SimpleListAdapter<Integer> simpleListAdapter0 = new SimpleListAdapter<Integer>();
      HashMap<String, String> hashMap0 = new HashMap<String, String>();
      // Undeclared exception!
      try { 
        simpleListAdapter0.putSelectOptions("K", (Map<String, String>) hashMap0, (String) null);
        fail("Expecting exception: IllegalArgumentException");
      
      } catch(IllegalArgumentException e) {
         //
         // Illegal argument: options is empty
         //
         verifyException("com.adapterj.widget.SimpleListAdapter", e);
      }
  }

  @Test(timeout = 4000)
  public void test23()  throws Throwable  {
      SimpleListAdapter<Object> simpleListAdapter0 = new SimpleListAdapter<Object>();
      HashMap<String, String> hashMap0 = new HashMap<String, String>();
      // Undeclared exception!
      try { 
        simpleListAdapter0.putSelectOptions((String) null, (Map<String, String>) hashMap0, "org.jsoup.nodes.Document$OutputSettings$Syntax");
        fail("Expecting exception: IllegalArgumentException");
      
      } catch(IllegalArgumentException e) {
         //
         // Illegal argument: id is null
         //
         verifyException("com.adapterj.widget.SimpleListAdapter", e);
      }
  }

  @Test(timeout = 4000)
  public void test24()  throws Throwable  {
      HashMap<String, String> hashMap0 = new HashMap<String, String>();
      hashMap0.put("j;q}fL_G2-VGE&03 o", "var anchor = !_anchorsArray[i] ? undefined : _anchorsArray[i][j];");
      SimpleListAdapter<String> simpleListAdapter0 = new SimpleListAdapter<String>();
      simpleListAdapter0.putSelectOptions("j;q}fL_G2-VGE&03 o", (Map<String, String>) hashMap0);
      assertNull(simpleListAdapter0.toJSONString());
  }

  @Test(timeout = 4000)
  public void test25()  throws Throwable  {
      SimpleListAdapter<SimpleSelectOptions> simpleListAdapter0 = new SimpleListAdapter<SimpleSelectOptions>();
      // Undeclared exception!
      try { 
        simpleListAdapter0.putSelectOptions("can NOT get item id", (Map<String, String>) null);
        fail("Expecting exception: IllegalArgumentException");
      
      } catch(IllegalArgumentException e) {
         //
         // Illegal argument: options is empty
         //
         verifyException("com.adapterj.widget.SimpleListAdapter", e);
      }
  }

  @Test(timeout = 4000)
  public void test26()  throws Throwable  {
      HashMap<String, String> hashMap0 = new HashMap<String, String>();
      SimpleListAdapter<String> simpleListAdapter0 = new SimpleListAdapter<String>();
      // Undeclared exception!
      try { 
        simpleListAdapter0.putSelectOptions("j;q}fL_G2-VGE&03 o", (Map<String, String>) hashMap0);
        fail("Expecting exception: IllegalArgumentException");
      
      } catch(IllegalArgumentException e) {
         //
         // Illegal argument: options is empty
         //
         verifyException("com.adapterj.widget.SimpleListAdapter", e);
      }
  }

  @Test(timeout = 4000)
  public void test27()  throws Throwable  {
      HashMap<String, String> hashMap0 = new HashMap<String, String>();
      SimpleListAdapter<String> simpleListAdapter0 = new SimpleListAdapter<String>();
      // Undeclared exception!
      try { 
        simpleListAdapter0.putSelectOptions("", (Map<String, String>) hashMap0);
        fail("Expecting exception: IllegalArgumentException");
      
      } catch(IllegalArgumentException e) {
         //
         // Illegal argument: id is \"\"
         //
         verifyException("com.adapterj.widget.SimpleListAdapter", e);
      }
  }

  @Test(timeout = 4000)
  public void test28()  throws Throwable  {
      SimpleListAdapter<String> simpleListAdapter0 = new SimpleListAdapter<String>();
      HashMap<String, String> hashMap0 = new HashMap<String, String>();
      // Undeclared exception!
      try { 
        simpleListAdapter0.putSelectOptions((String) null, (Map<String, String>) hashMap0);
        fail("Expecting exception: IllegalArgumentException");
      
      } catch(IllegalArgumentException e) {
         //
         // Illegal argument: id is null
         //
         verifyException("com.adapterj.widget.SimpleListAdapter", e);
      }
  }

  @Test(timeout = 4000)
  public void test29()  throws Throwable  {
      SimpleListAdapter<Integer> simpleListAdapter0 = new SimpleListAdapter<Integer>();
      HashMap<String, String> hashMap0 = new HashMap<String, String>();
      hashMap0.put("j;q}fL_G2-VGE&03 o", "var anchor = !_anchorsArray[i] ? undefined : _anchorsArray[i][j];");
      SimpleSelectOptions simpleSelectOptions0 = new SimpleSelectOptions("var anchor = !_anchorsArray[i] ? undefined : _anchorsArray[i][j];", hashMap0, "*.n");
      simpleListAdapter0.putSelectOptions("var anchor = !_anchorsArray[i] ? undefined : _anchorsArray[i][j];", (SelectOptions) simpleSelectOptions0);
      String string0 = simpleListAdapter0.toJavaScript("*.n", "*.n");
      assertEquals("function *.n() {\nvar _id = \"*.n\";\nvar _list = [];\nvar _optionsId0 = \"var anchor = !_anchorsArray[i] ? undefined : _anchorsArray[i][j];\";\nvar _optionsObj0 = {\"j;q}fL_G2-VGE\\u002603 o\":\"var anchor \\u003d !_anchorsArray[i] ? undefined : _anchorsArray[i][j];\"};\nvar _anchorsArray = [];\nvar _length = !_anchorsArray[0] ? 0 : _anchorsArray[0].length;\n\nvar adapter = new ADAPTERJ.widget.SimpleListAdapter(_id);\nadapter.putSelectOptions(_optionsId0, new ADAPTERJ.widget.SimpleSelectOptions(_optionsId0, _optionsObj0));\n\nfor (var i = 0; i < _list.length; i ++) {\nadapter.addItem(_list[i]);\n\nvar anchors = new ADAPTERJ.widget.AnchorGroup(_length);\nfor (var j = 0; j < _length; j ++) {\nvar anchor = !_anchorsArray[i] ? undefined : _anchorsArray[i][j];\nif (anchor) anchors.setAnchor(j, new ADAPTERJ.widget.Anchor(anchor.url, anchor.title, anchor.label));\n};\n\nadapter.addAnchorGroup(anchors);\n};\n\nadapter.bindViewHolder();\n};", string0);
  }

  @Test(timeout = 4000)
  public void test30()  throws Throwable  {
      SimpleListAdapter<Object> simpleListAdapter0 = new SimpleListAdapter<Object>();
      // Undeclared exception!
      try { 
        simpleListAdapter0.putSelectOptions("4", (SelectOptions) null);
        fail("Expecting exception: IllegalArgumentException");
      
      } catch(IllegalArgumentException e) {
         //
         // Illegal argument: options is empty
         //
         verifyException("com.adapterj.widget.SimpleListAdapter", e);
      }
  }

  @Test(timeout = 4000)
  public void test31()  throws Throwable  {
      SimpleListAdapter<String> simpleListAdapter0 = new SimpleListAdapter<String>();
      Integer integer0 = new Integer(2908);
      HashMap<Integer, String> hashMap0 = new HashMap<Integer, String>();
      SimpleSelectOptions simpleSelectOptions0 = new SimpleSelectOptions("", hashMap0, integer0);
      // Undeclared exception!
      try { 
        simpleListAdapter0.putSelectOptions("", (SelectOptions) simpleSelectOptions0);
        fail("Expecting exception: IllegalArgumentException");
      
      } catch(IllegalArgumentException e) {
         //
         // Illegal argument: id is \"\"
         //
         verifyException("com.adapterj.widget.SimpleListAdapter", e);
      }
  }

  @Test(timeout = 4000)
  public void test32()  throws Throwable  {
      SimpleListAdapter<Integer> simpleListAdapter0 = new SimpleListAdapter<Integer>();
      HashMap<String, String> hashMap0 = new HashMap<String, String>();
      SimpleSelectOptions simpleSelectOptions0 = new SimpleSelectOptions("var anchor = !_anchorsArray[i] ? undefined : _anchorsArray[i][j];", hashMap0, "*.n");
      // Undeclared exception!
      try { 
        simpleListAdapter0.putSelectOptions("var anchor = !_anchorsArray[i] ? undefined : _anchorsArray[i][j];", (SelectOptions) simpleSelectOptions0);
        fail("Expecting exception: IllegalArgumentException");
      
      } catch(IllegalArgumentException e) {
         //
         // Illegal argument: options is empty
         //
         verifyException("com.adapterj.widget.SimpleListAdapter", e);
      }
  }

  @Test(timeout = 4000)
  public void test33()  throws Throwable  {
      SimpleListAdapter<Integer> simpleListAdapter0 = new SimpleListAdapter<Integer>();
      HashMap<Integer, String> hashMap0 = new HashMap<Integer, String>();
      Integer integer0 = new Integer(0);
      SimpleSelectOptions simpleSelectOptions0 = new SimpleSelectOptions("var anchors = new ADAPTERJ.widget.AnchorGroup(_length);", hashMap0, integer0);
      // Undeclared exception!
      try { 
        simpleListAdapter0.putSelectOptions((String) null, (SelectOptions) simpleSelectOptions0);
        fail("Expecting exception: IllegalArgumentException");
      
      } catch(IllegalArgumentException e) {
         //
         // Illegal argument: id is null
         //
         verifyException("com.adapterj.widget.SimpleListAdapter", e);
      }
  }

  @Test(timeout = 4000)
  public void test34()  throws Throwable  {
      SimpleListAdapter<AnchorGroup> simpleListAdapter0 = new SimpleListAdapter<AnchorGroup>();
      // Undeclared exception!
      try { 
        simpleListAdapter0.getItem((-2958));
        fail("Expecting exception: ArrayIndexOutOfBoundsException");
      
      } catch(ArrayIndexOutOfBoundsException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  @Test(timeout = 4000)
  public void test35()  throws Throwable  {
      SimpleListAdapter<Integer> simpleListAdapter0 = new SimpleListAdapter<Integer>();
      String string0 = simpleListAdapter0.toJSONString();
      assertNull(string0);
  }

  @Test(timeout = 4000)
  public void test36()  throws Throwable  {
      SimpleListAdapter<Integer> simpleListAdapter0 = new SimpleListAdapter<Integer>();
      Set<String> set0 = simpleListAdapter0.idSetOfSelectOptions();
      assertTrue(set0.isEmpty());
  }

  @Test(timeout = 4000)
  public void test37()  throws Throwable  {
      SimpleListAdapter<Object> simpleListAdapter0 = new SimpleListAdapter<Object>();
      List<Object> list0 = simpleListAdapter0.getAllItems();
      assertEquals(0, list0.size());
  }

  @Test(timeout = 4000)
  public void test38()  throws Throwable  {
      SimpleListAdapter<AnchorGroup> simpleListAdapter0 = new SimpleListAdapter<AnchorGroup>();
      // Undeclared exception!
      try { 
        simpleListAdapter0.getItemId(1650);
        fail("Expecting exception: RuntimeException");
      
      } catch(RuntimeException e) {
         //
         // can NOT get item id
         //
         verifyException("com.adapterj.widget.SimpleListAdapter", e);
      }
  }

  @Test(timeout = 4000)
  public void test39()  throws Throwable  {
      SimpleListAdapter<Integer> simpleListAdapter0 = new SimpleListAdapter<Integer>();
      SelectOptions selectOptions0 = simpleListAdapter0.getSelectOptions("can NOT get item id");
      assertNull(selectOptions0);
  }

  @Test(timeout = 4000)
  public void test40()  throws Throwable  {
      SimpleListAdapter<Integer> simpleListAdapter0 = new SimpleListAdapter<Integer>();
      String string0 = simpleListAdapter0.toXMLString();
      assertNull(string0);
  }
}