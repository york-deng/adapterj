package com.adapterj.web;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public interface Jsonable {

    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");

    String toJSONString();

}
