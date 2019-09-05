package com.adapterj.logging;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

import static java.util.logging.Level.CONFIG;
import static java.util.logging.Level.FINE;
import static java.util.logging.Level.INFO;
import static java.util.logging.Level.SEVERE;
import static java.util.logging.Level.WARNING;

/**
 * 
 * @author York/GuangYu DENG
 */
public class Log {

    private static final boolean DEBUG = Debugger.DEBUG;

    private static final String pattern = "(%s:%d) %s: %s";

    private static final Map<String, Logger> loggers = new ConcurrentHashMap<String, Logger>();

    /**
     * 
     * @param throwable
     * @return
     */
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

    /**
     * 
     * @param throwable
     * @return
     */
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
    
    /**
     *
     * @param tag
     * @param msg
     */
    public static void v(String tag, String msg) {
        if (DEBUG) {
            Logger log = loggers.get(tag);
            if (log == null) {
                loggers.put(tag, log = Logger.getLogger(tag));
            }
            log.log(FINE, msg);
        }
    }

    /**
     *
     * @param tag
     * @param msg
     * @param trace
     */
//    public static void v(String tag, String msg, StackTraceElement trace) {
//        if (DEBUG) {
//            Logger log = loggers.get(tag);
//            if (log == null) {
//                loggers.put(tag, log = Logger.getLogger(tag));
//            }
//            log.log(FINE, String.format(pattern, trace.getFileName(), trace.getLineNumber(), trace.getMethodName(), msg));
//        }
//    }

    /**
     *
     * @param tag
     * @param msg
     * @param thrown
     */
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

    /**
     *
     * @param tag
     * @param msg
     */
    public static void d(String tag, String msg) {
        if (DEBUG) {
            Logger log = loggers.get(tag);
            if (log == null) {
                loggers.put(tag, log = Logger.getLogger(tag));
            }
            log.log(CONFIG, msg);
        }
    }

    /**
     *
     * @param tag
     * @param msg
     * @param trace
     */
//    public static void d(String tag, String msg, StackTraceElement trace) {
//        if (DEBUG) {
//            Logger log = loggers.get(tag);
//            if (log == null) {
//                loggers.put(tag, log = Logger.getLogger(tag));
//            }
//            log.log(CONFIG, String.format(pattern, trace.getFileName(), trace.getLineNumber(), trace.getMethodName(), msg));
//        }
//    }

    /**
     *
     * @param tag
     * @param msg
     * @param thrown
     */
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

    /**
     *
     * @param tag
     * @param msg
     */
    public static void i(String tag, String msg) {
        if (DEBUG) {
            Logger log = loggers.get(tag);
            if (log == null) {
                loggers.put(tag, log = Logger.getLogger(tag));
            }
            log.log(INFO, msg);
        }
    }

    /**
     *
     * @param tag
     * @param msg
     * @param trace
     */
//    public static void i(String tag, String msg, StackTraceElement trace) {
//        if (DEBUG) {
//            Logger log = loggers.get(tag);
//            if (log == null) {
//                loggers.put(tag, log = Logger.getLogger(tag));
//            }
//            log.log(INFO, String.format(pattern, trace.getFileName(), trace.getLineNumber(), trace.getMethodName(), msg));
//        }
//    }

    /**
     *
     * @param tag
     * @param msg
     * @param thrown
     */
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

    /**
     *
     * @param tag
     * @param msg
     */
    public static void w(String tag, String msg) {
        if (DEBUG) {
            Logger log = loggers.get(tag);
            if (log == null) {
                loggers.put(tag, log = Logger.getLogger(tag));
            }
            log.log(WARNING, msg);
        }
    }

    /**
     *
     * @param tag
     * @param msg
     * @param trace
     */
//    public static void w(String tag, String msg, StackTraceElement trace) {
//        if (DEBUG) {
//            Logger log = loggers.get(tag);
//            if (log == null) {
//                loggers.put(tag, log = Logger.getLogger(tag));
//            }
//            log.log(WARNING, String.format(pattern, trace.getFileName(), trace.getLineNumber(), trace.getMethodName(), msg));
//        }
//    }

    /**
     *
     * @param tag
     * @param msg
     * @param thrown
     */
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

    /**
     *
     * @param tag
     * @param msg
     */
    public static void e(String tag, String msg) {
        if (DEBUG) {
            Logger log = loggers.get(tag);
            if (log == null) {
                loggers.put(tag, log = Logger.getLogger(tag));
            }
            log.log(SEVERE, msg);
        }
    }

    /**
     *
     * @param tag
     * @param msg
     * @param trace
     */
//    public static void e(String tag, String msg, StackTraceElement trace) {
//        if (DEBUG) {
//            Logger log = loggers.get(tag);
//            if (log == null) {
//                loggers.put(tag, log = Logger.getLogger(tag));
//            }
//            log.log(SEVERE, String.format(pattern, trace.getFileName(), trace.getLineNumber(), trace.getMethodName(), msg));
//        }
//    }

    /**
     *
     * @param tag
     * @param msg
     * @param thrown
     */
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
