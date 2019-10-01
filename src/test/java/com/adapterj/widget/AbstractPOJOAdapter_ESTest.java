/*
 * This file was automatically generated by EvoSuite
 * Fri Sep 27 16:11:20 GMT 2019
 */

package com.adapterj.widget;

import org.junit.Test;
import static org.junit.Assert.*;
import com.adapterj.widget.AnchorGroup;
import com.adapterj.widget.SimpleFormAdapter;
import com.adapterj.widget.SimpleViewAdapter;
import com.adapterj.widget.TextGroup;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class) @EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = true, useJEE = true) 
public class AbstractPOJOAdapter_ESTest extends AbstractPOJOAdapter_ESTest_scaffolding {

  @Test(timeout = 4000)
  public void test00()  throws Throwable  {
      SimpleFormAdapter<Integer> simpleFormAdapter0 = new SimpleFormAdapter<Integer>();
      simpleFormAdapter0._placeholderForNull = null;
      String string0 = simpleFormAdapter0.getPlaceholderForNull();
      assertNull(string0);
  }

  @Test(timeout = 4000)
  public void test01()  throws Throwable  {
      SimpleViewAdapter<String> simpleViewAdapter0 = new SimpleViewAdapter<String>();
      simpleViewAdapter0.setPlaceholderForEmpty((String) null);
      String string0 = simpleViewAdapter0.getPlaceholderForEmpty();
      assertNull(string0);
  }

  @Test(timeout = 4000)
  public void test02()  throws Throwable  {
      SimpleViewAdapter<Integer> simpleViewAdapter0 = new SimpleViewAdapter<Integer>();
      simpleViewAdapter0._placeholderForEmpty = "com.adapterj.widget.AbstractPOJOAdapter";
      String string0 = simpleViewAdapter0.getPlaceholderForEmpty();
      assertEquals("com.adapterj.widget.AbstractPOJOAdapter", string0);
  }

  @Test(timeout = 4000)
  public void test03()  throws Throwable  {
      SimpleFormAdapter<String> simpleFormAdapter0 = new SimpleFormAdapter<String>();
      AnchorGroup anchorGroup0 = simpleFormAdapter0.getAnchorGroup();
      assertNull(anchorGroup0);
  }

  @Test(timeout = 4000)
  public void test04()  throws Throwable  {
      SimpleViewAdapter<String> simpleViewAdapter0 = new SimpleViewAdapter<String>();
      simpleViewAdapter0.setAnchorGroup((AnchorGroup) null);
      assertNull(simpleViewAdapter0.toXMLString());
  }

  @Test(timeout = 4000)
  public void test05()  throws Throwable  {
      SimpleFormAdapter<String> simpleFormAdapter0 = new SimpleFormAdapter<String>();
      simpleFormAdapter0.setData("3bh");
      String string0 = simpleFormAdapter0.getData();
      assertNotNull(string0);
      assertFalse(simpleFormAdapter0.isEmpty());
  }

  @Test(timeout = 4000)
  public void test06()  throws Throwable  {
      SimpleViewAdapter<Object> simpleViewAdapter0 = new SimpleViewAdapter<Object>();
      String string0 = simpleViewAdapter0.getPlaceholderForEmpty();
      assertEquals("", string0);
  }

  @Test(timeout = 4000)
  public void test07()  throws Throwable  {
      SimpleFormAdapter<String> simpleFormAdapter0 = new SimpleFormAdapter<String>();
      String string0 = simpleFormAdapter0.getPlaceholderForNull();
      assertEquals("", string0);
  }

  @Test(timeout = 4000)
  public void test08()  throws Throwable  {
      SimpleFormAdapter<Object> simpleFormAdapter0 = new SimpleFormAdapter<Object>();
      TextGroup textGroup0 = simpleFormAdapter0.getTextGroup();
      assertNull(textGroup0);
  }

  @Test(timeout = 4000)
  public void test09()  throws Throwable  {
      SimpleFormAdapter<String> simpleFormAdapter0 = new SimpleFormAdapter<String>();
      simpleFormAdapter0.setPlaceholderForNull("h^");
      String string0 = simpleFormAdapter0.getPlaceholderForNull();
      assertEquals("h^", string0);
  }

  @Test(timeout = 4000)
  public void test10()  throws Throwable  {
      SimpleViewAdapter<String> simpleViewAdapter0 = new SimpleViewAdapter<String>();
      simpleViewAdapter0.setTextGroup((TextGroup) null);
      assertEquals("", simpleViewAdapter0.getPlaceholderForNull());
  }

  @Test(timeout = 4000)
  public void test11()  throws Throwable  {
      SimpleFormAdapter<Object> simpleFormAdapter0 = new SimpleFormAdapter<Object>();
      Object object0 = simpleFormAdapter0.getData();
      assertNull(object0);
  }
}