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
package com.adapterj.logging;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

import static java.util.logging.Level.CONFIG;
import static java.util.logging.Level.FINE;
import static java.util.logging.Level.INFO;
import static java.util.logging.Level.SEVERE;
import static java.util.logging.Level.WARNING;

public class Log {

    private static final boolean DEBUG = Debugger.DEBUG;

    private static final String pattern = "(%s:%d) %s: %s";

    private static final Map<String, Logger> loggers = new ConcurrentHashMap<String, Logger>();

    public static String getStackTraceString(Throwable thrown) {
    	final StringBuffer s = new StringBuffer();
    	s.append(thrown.getMessage()).append('\n');
    	final StackTraceElement[] traces = thrown.getStackTrace();
    	for (StackTraceElement t : traces) {
    		final String f = "(%s:%d) %s: ";
    		s.append(String.format(f, t.getFileName(), t.getLineNumber(), t.getMethodName())).append('\n');
    	}
    	return s.toString();
    }

    public static String getStackTraceHTMLString(Throwable e) {
    	final StringBuffer s = new StringBuffer();
    	s.append("<ul>");
    	s.append("<li>").append(e.getMessage());
    	s.append("<ul>");
    	final StackTraceElement[] traces = e.getStackTrace();
    	for (StackTraceElement t : traces) {
    		s.append(String.format("<li>(%s:%d) %s: </li>", t.getFileName(), t.getLineNumber(), t.getMethodName())).append('\n');
    	}
    	s.append("</ul>").append('\n');
    	s.append("</li>").append('\n');
    	s.append("</ul>");
    	return s.toString();
    }

    public static void v(String tag, String msg) {
        if (DEBUG) {
            Logger log = loggers.get(tag);
            if (log == null) {
                loggers.put(tag, log = Logger.getLogger(tag));
            }
            log.log(FINE, msg);
        }
    }

//    public static void v(String tag, String msg, StackTraceElement trace) {
//        if (DEBUG) {
//            Logger log = loggers.get(tag);
//            if (log == null) {
//                loggers.put(tag, log = Logger.getLogger(tag));
//            }
//            log.log(FINE, String.format(pattern, trace.getFileName(), trace.getLineNumber(), trace.getMethodName(), msg));
//        }
//    }

    public static void v(String tag, String msg, Throwable thrown) {
        if (DEBUG) {
            Logger log = loggers.get(tag);
            if (log == null) {
                loggers.put(tag, log = Logger.getLogger(tag));
            }
            StackTraceElement trace = (new Throwable()).getStackTrace()[0];
            log.log(FINE, String.format(pattern, trace.getFileName(), trace.getLineNumber(), trace.getMethodName(), msg), thrown);
        }
    }

    public static void d(String tag, String msg) {
        if (DEBUG) {
            Logger log = loggers.get(tag);
            if (log == null) {
                loggers.put(tag, log = Logger.getLogger(tag));
            }
            log.log(CONFIG, msg);
        }
    }

//    public static void d(String tag, String msg, StackTraceElement trace) {
//        if (DEBUG) {
//            Logger log = loggers.get(tag);
//            if (log == null) {
//                loggers.put(tag, log = Logger.getLogger(tag));
//            }
//            log.log(CONFIG, String.format(pattern, trace.getFileName(), trace.getLineNumber(), trace.getMethodName(), msg));
//        }
//    }

    public static void d(String tag, String msg, Throwable thrown) {
        if (DEBUG) {
            Logger log = loggers.get(tag);
            if (log == null) {
                loggers.put(tag, log = Logger.getLogger(tag));
            }
            StackTraceElement trace = (new Throwable()).getStackTrace()[0];
            log.log(CONFIG, String.format(pattern, trace.getFileName(), trace.getLineNumber(), trace.getMethodName(), msg), thrown);
        }
    }

    public static void i(String tag, String msg) {
        if (DEBUG) {
            Logger log = loggers.get(tag);
            if (log == null) {
                loggers.put(tag, log = Logger.getLogger(tag));
            }
            log.log(INFO, msg);
        }
    }

//    public static void i(String tag, String msg, StackTraceElement trace) {
//        if (DEBUG) {
//            Logger log = loggers.get(tag);
//            if (log == null) {
//                loggers.put(tag, log = Logger.getLogger(tag));
//            }
//            log.log(INFO, String.format(pattern, trace.getFileName(), trace.getLineNumber(), trace.getMethodName(), msg));
//        }
//    }

    public static void i(String tag, String msg, Throwable thrown) {
        if (DEBUG) {
            Logger log = loggers.get(tag);
            if (log == null) {
                loggers.put(tag, log = Logger.getLogger(tag));
            }
            StackTraceElement trace = (new Throwable()).getStackTrace()[0];
            log.log(INFO, String.format(pattern, trace.getFileName(), trace.getLineNumber(), trace.getMethodName(), msg), thrown);
        }
    }

    public static void w(String tag, String msg) {
        if (DEBUG) {
            Logger log = loggers.get(tag);
            if (log == null) {
                loggers.put(tag, log = Logger.getLogger(tag));
            }
            log.log(WARNING, msg);
        }
    }

//    public static void w(String tag, String msg, StackTraceElement trace) {
//        if (DEBUG) {
//            Logger log = loggers.get(tag);
//            if (log == null) {
//                loggers.put(tag, log = Logger.getLogger(tag));
//            }
//            log.log(WARNING, String.format(pattern, trace.getFileName(), trace.getLineNumber(), trace.getMethodName(), msg));
//        }
//    }

    public static void w(String tag, String msg, Throwable thrown) {
        if (DEBUG) {
            Logger log = loggers.get(tag);
            if (log == null) {
                loggers.put(tag, log = Logger.getLogger(tag));
            }
            StackTraceElement trace = (new Throwable()).getStackTrace()[0];
            StringBuffer buffer = new StringBuffer();
            buffer.append(msg).append('\n').append(getStackTraceString(thrown));
            log.log(WARNING, String.format(pattern, 
            		trace.getFileName(), trace.getLineNumber(), trace.getMethodName(), buffer.toString()), thrown);
        }
    }

    public static void e(String tag, String msg) {
        if (DEBUG) {
            Logger log = loggers.get(tag);
            if (log == null) {
                loggers.put(tag, log = Logger.getLogger(tag));
            }
            log.log(SEVERE, msg);
        }
    }

//    public static void e(String tag, String msg, StackTraceElement trace) {
//        if (DEBUG) {
//            Logger log = loggers.get(tag);
//            if (log == null) {
//                loggers.put(tag, log = Logger.getLogger(tag));
//            }
//            log.log(SEVERE, String.format(pattern, trace.getFileName(), trace.getLineNumber(), trace.getMethodName(), msg));
//        }
//    }

    public static void e(String tag, String msg, Throwable thrown) {
        if (DEBUG) {
            Logger log = loggers.get(tag);
            if (log == null) {
                loggers.put(tag, log = Logger.getLogger(tag));
            }
            StackTraceElement trace = (new Throwable()).getStackTrace()[0];
            StringBuffer buffer = new StringBuffer();
            buffer.append(msg).append('\n').append(getStackTraceString(thrown));
            log.log(SEVERE, String.format(pattern, 
            		trace.getFileName(), trace.getLineNumber(), trace.getMethodName(), buffer.toString()), thrown);
        }
    }
}
