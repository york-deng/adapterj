package com.adapterj.text;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 
 * @author York/GuangYu DENG
 */
public class Formatter {
	
	// The mapping from pattern to date format
	private static final Map<String, java.text.DateFormat>   _map1 = new ConcurrentHashMap<>();
	
	// The mapping from pattern to number format
	private static final Map<String, java.text.NumberFormat> _map2 = new ConcurrentHashMap<>();
	
	// The date format
	private String _format1 = "yyyy-MM-dd HH:mm";
	
	// The integer format
	private String _format2 = "#,###";
	
	// The long format
	private String _format3 = "#";
	
	// The float format
	private String _format4 = "#,##0.00";
	
	// The double format
	private String _format5 = "#";

	// The placeholder for date
	private String _placeholderForDate = "0000-00-00 00:00";
	
	// The placeholder for integer
	private String _placeholderForInteger = "0";
	
	// The placeholder for long
	private String _placeholderForLong = "0";
	
	// The placeholder for float
	private String _placeholderForFloat = "0.00";
	
	// The placeholder for double
	private String _placeholderForDouble = "0";
		
	// The placeholder for null
	private String _placeholderForNull = "N/A";
	
	// The placeholder for empty
	private String _placeholderForEmpty = "";
	
	/**
	 * Constructs
	 */
	public Formatter() { }
	
	/**
	 * 
	 * @param pattern
	 */
	public void setPatternForDate(final String pattern) {
		if (pattern != null && !pattern.isEmpty()) _format1 = pattern;
	}

	/**
	 * 
	 * @param placeholder
	 */
	public void setPlaceholderForDate(final String placeholder) {
		_placeholderForDate = placeholder;
	}

	/**
	 * 
	 * @param pattern
	 */
	public void setPatternForInteger(final String pattern) {
		if (pattern != null && !pattern.isEmpty()) _format2 = pattern;
	}

	/**
	 * 
	 * @param placeholder
	 */
	public void setPlaceholderForInteger(final String placeholder) {
		_placeholderForInteger = placeholder;
	}

	/**
	 * 
	 * @param pattern
	 */
	public void setPatternForLong(final String pattern) {
		if (pattern != null && !pattern.isEmpty()) _format3 = pattern;
	}

	/**
	 * 
	 * @param placeholder
	 */
	public void setPlaceholderForLong(final String placeholder) {
		_placeholderForLong = placeholder;
	}

	/**
	 * 
	 * @param pattern
	 */
	public void setPatternForFloat(final String pattern) {
		if (pattern != null && !pattern.isEmpty()) _format4 = pattern;
	}

	/**
	 * 
	 * @param placeholder
	 */
	public void setPlaceholderForFloat(final String placeholder) {
		_placeholderForFloat = placeholder;
	}

	/**
	 * 
	 * @param pattern
	 */
	public void setPatternForDouble(final String pattern) {
		if (pattern != null && !pattern.isEmpty()) _format5 = pattern;
	}

	/**
	 * 
	 * @param placeholder
	 */
	public void setPlaceholderForDouble(final String placeholder) {
		_placeholderForDouble = placeholder;
	}

	/**
	 * 
	 * @param placeholderForNull
	 */
	public void setPlaceholderForNull(final String placeholderForNull) {
		_placeholderForNull = placeholderForNull;
	}

	/**
	 * 
	 * @param placeholderForEmpty
	 */
	public void setPlaceholderForEmpty(final String placeholderForEmpty) {
		_placeholderForEmpty = placeholderForEmpty;
	}

	/**
	 * 
	 * @param value
	 * @return
	 */
	public String format(final Date value) {
		try {
			java.text.DateFormat format = _map1.get(_format1);
			if (format == null) {
				_map1.put(_format1, format = new SimpleDateFormat(_format1));
			}
			return format.format(value);
		} catch (NullPointerException e) {
			return _placeholderForNull;
		} catch (IllegalArgumentException e) {
			return _placeholderForDate;
		}
	}

	/**
	 * 
	 * @param value
	 * @param pattern
	 * @return
	 */
	public String format(final Date value, final String pattern) {
		try {
			final String pattern1 = (pattern == null || pattern.isEmpty()) ? _format1 : pattern;
			java.text.DateFormat format = _map1.get(pattern1);
			if (format == null) {
				_map1.put(pattern1, format = new SimpleDateFormat(pattern1));
			}
			return format.format(value);
		} catch (NullPointerException e) {
			return _placeholderForNull;
		} catch (IllegalArgumentException e) {
			return _placeholderForDate;
		}
	}
	
	/**
	 * 
	 * @param value
	 * @param pattern
	 * @param placeholder
	 * @return
	 */
	public String format(final Date value, final String pattern, final String placeholder) {
		try {
			final String pattern1 = (pattern == null || pattern.isEmpty()) ? _format1 : pattern;
			java.text.DateFormat format = _map1.get(pattern1);
			if (format == null) {
				_map1.put(pattern1, format = new SimpleDateFormat(pattern1));
			}
			return format.format(value);
		} catch (NullPointerException e) {
			return (placeholder == null || placeholder.isEmpty()) ? _placeholderForNull : placeholder;
		} catch (IllegalArgumentException e) {
			return (placeholder == null || placeholder.isEmpty()) ? _placeholderForDate : placeholder;
		}
	}
	
	/**
	 * 
	 * @param value
	 * @return
	 */
	public String format(final Integer value) {
		try {
			java.text.NumberFormat format = _map2.get(_format2);
			if (format == null) {
				_map2.put(_format2, format = new DecimalFormat(_format2));
			}
			return format.format(value);
		} catch (NullPointerException e) {
			return _placeholderForNull;
		} catch (IllegalArgumentException e) {
			return _placeholderForInteger;
		}
	}
	
	/**
	 * 
	 * @param value
	 * @return
	 */
	public String format(final int value) {
		try {
			java.text.NumberFormat format = _map2.get(_format2);
			if (format == null) {
				_map2.put(_format2, format = new DecimalFormat(_format2));
			}
			return format.format(value);
		} catch (NullPointerException e) {
			return _placeholderForNull;
		} catch (IllegalArgumentException e) {
			return _placeholderForInteger;
		}
	}

	/**
	 * 
	 * @param value
	 * @param pattern
	 * @return
	 */
	public String format(final Integer value, final String pattern) {
		try {
			final String pattern2 = (pattern == null || pattern.isEmpty()) ? _format2 : pattern;
			java.text.NumberFormat format = _map2.get(pattern2);
			if (format == null) {
				_map2.put(pattern2, format = new DecimalFormat(pattern2));
			}
			return format.format(value);
		} catch (NullPointerException e) {
			return _placeholderForNull;
		} catch (IllegalArgumentException e) {
			return _placeholderForInteger;
		}
	}

	/**
	 * 
	 * @param value
	 * @param pattern
	 * @return
	 */
	public String format(final int value, final String pattern) {
		try {
			final String pattern2 = (pattern == null || pattern.isEmpty()) ? _format2 : pattern;
			java.text.NumberFormat format = _map2.get(pattern2);
			if (format == null) {
				_map2.put(pattern2, format = new DecimalFormat(pattern2));
			}
			return format.format(value);
		} catch (NullPointerException e) {
			return _placeholderForNull;
		} catch (IllegalArgumentException e) {
			return _placeholderForInteger;
		}
	}
	
	/**
	 * 
	 * @param value
	 * @param pattern
	 * @param placeholder
	 * @return
	 */
	public String format(final Integer value, final String pattern, final String placeholder) {
		try {
			final String pattern2 = (pattern == null || pattern.isEmpty()) ? _format2 : pattern;
			java.text.NumberFormat format = _map2.get(pattern2);
			if (format == null) {
				_map2.put(pattern2, format = new DecimalFormat(pattern2));
			}
			return format.format(value);
		} catch (NullPointerException e) {
			return (placeholder == null || placeholder.isEmpty()) ? _placeholderForNull : placeholder;
		} catch (IllegalArgumentException e) {
			return (placeholder == null || placeholder.isEmpty()) ? _placeholderForInteger : placeholder;
		}
	}
	
	/**
	 * 
	 * @param value
	 * @param pattern
	 * @param placeholder
	 * @return
	 */
	public String format(final int value, final String pattern, final String placeholder) {
		try {
			final String pattern2 = (pattern == null || pattern.isEmpty()) ? _format2 : pattern;
			java.text.NumberFormat format = _map2.get(pattern2);
			if (format == null) {
				_map2.put(pattern2, format = new DecimalFormat(pattern2));
			}
			return format.format(value);
		} catch (NullPointerException e) {
			return (placeholder == null || placeholder.isEmpty()) ? _placeholderForNull : placeholder;
		} catch (IllegalArgumentException e) {
			return (placeholder == null || placeholder.isEmpty()) ? _placeholderForInteger : placeholder;
		}
	}
	
	/**
	 * 
	 * @param value
	 * @return
	 */
	public String format(final Long value) {
		try {
			java.text.NumberFormat format = _map2.get(_format3);
			if (format == null) {
				_map2.put(_format3, format = new DecimalFormat(_format3));
			}
			return format.format(value);
		} catch (NullPointerException e) {
			return _placeholderForNull;
		} catch (IllegalArgumentException e) {
			return _placeholderForLong;
		}
	}
	
	/**
	 * 
	 * @param value
	 * @return
	 */
	public String format(final long value) {
		try {
			java.text.NumberFormat format = _map2.get(_format3);
			if (format == null) {
				_map2.put(_format3, format = new DecimalFormat(_format3));
			}
			return format.format(value);
		} catch (NullPointerException e) {
			return _placeholderForNull;
		} catch (IllegalArgumentException e) {
			return _placeholderForLong;
		}
	}

	/**
	 * 
	 * @param value
	 * @param pattern
	 * @return
	 */
	public String format(final Long value, final String pattern) {
		try {
			final String pattern3 = (pattern == null || pattern.isEmpty()) ? _format3 : pattern;
			java.text.NumberFormat format = _map2.get(pattern3);
			if (format == null) {
				_map2.put(pattern3, format = new DecimalFormat(pattern3));
			}
			return format.format(value);
		} catch (NullPointerException e) {
			return _placeholderForNull;
		} catch (IllegalArgumentException e) {
			return _placeholderForLong;
		}
	}

	/**
	 * 
	 * @param value
	 * @param pattern
	 * @return
	 */
	public String format(final long value, final String pattern) {
		try {
			final String pattern3 = (pattern == null || pattern.isEmpty()) ? _format3 : pattern;
			java.text.NumberFormat format = _map2.get(pattern3);
			if (format == null) {
				_map2.put(pattern3, format = new DecimalFormat(pattern3));
			}
			return format.format(value);
		} catch (NullPointerException e) {
			return _placeholderForNull;
		} catch (IllegalArgumentException e) {
			return _placeholderForLong;
		}
	}
	
	/**
	 * 
	 * @param value
	 * @param pattern
	 * @param placeholder
	 * @return
	 */
	public String format(final Long value, final String pattern, final String placeholder) {
		try {
			final String pattern3 = (pattern == null || pattern.isEmpty()) ? _format3 : pattern;
			java.text.NumberFormat format = _map2.get(pattern3);
			if (format == null) {
				_map2.put(pattern3, format = new DecimalFormat(pattern3));
			}
			return format.format(value);
		} catch (NullPointerException e) {
			return (placeholder == null || placeholder.isEmpty()) ? _placeholderForNull : placeholder;
		} catch (IllegalArgumentException e) {
			return (placeholder == null || placeholder.isEmpty()) ? _placeholderForLong : placeholder;
		}
	}
	
	/**
	 * 
	 * @param value
	 * @param pattern
	 * @param placeholder
	 * @return
	 */
	public String format(final long value, final String pattern, final String placeholder) {
		try {
			final String pattern3 = (pattern == null || pattern.isEmpty()) ? _format3 : pattern;
			java.text.NumberFormat format = _map2.get(pattern3);
			if (format == null) {
				_map2.put(pattern3, format = new DecimalFormat(pattern3));
			}
			return format.format(value);
		} catch (NullPointerException e) {
			return (placeholder == null || placeholder.isEmpty()) ? _placeholderForNull : placeholder;
		} catch (IllegalArgumentException e) {
			return (placeholder == null || placeholder.isEmpty()) ? _placeholderForLong : placeholder;
		}
	}
	
	/**
	 * 
	 * @param value
	 * @return
	 */
	public String format(final Float value) {
		try {
			java.text.NumberFormat format = _map2.get(_format4);
			if (format == null) {
				_map2.put(_format4, format = new DecimalFormat(_format4));
			}
			return format.format(value);
		} catch (NullPointerException e) {
			return _placeholderForNull;
		} catch (IllegalArgumentException e) {
			return _placeholderForFloat;
		}
	}
	
	/**
	 * 
	 * @param value
	 * @return
	 */
	public String format(final float value) {
		try {
			java.text.NumberFormat format = _map2.get(_format4);
			if (format == null) {
				_map2.put(_format4, format = new DecimalFormat(_format4));
			}
			return format.format(value);
		} catch (NullPointerException e) {
			return _placeholderForNull;
		} catch (IllegalArgumentException e) {
			return _placeholderForFloat;
		}
	}

	/**
	 * 
	 * @param value
	 * @param pattern
	 * @return
	 */
	public String format(final Float value, final String pattern) {
		try {
			final String pattern4 = (pattern == null || pattern.isEmpty()) ? _format4 : pattern;
			java.text.NumberFormat format = _map2.get(pattern4);
			if (format == null) {
				_map2.put(pattern4, format = new DecimalFormat(pattern4));
			}
			return format.format(value);
		} catch (NullPointerException e) {
			return _placeholderForNull;
		} catch (IllegalArgumentException e) {
			return _placeholderForFloat;
		}
	}

	/**
	 * 
	 * @param value
	 * @param pattern
	 * @return
	 */
	public String format(final float value, final String pattern) {
		try {
			final String pattern4 = (pattern == null || pattern.isEmpty()) ? _format4 : pattern;
			java.text.NumberFormat format = _map2.get(pattern4);
			if (format == null) {
				_map2.put(pattern4, format = new DecimalFormat(pattern4));
			}
			return format.format(value);
		} catch (NullPointerException e) {
			return _placeholderForNull;
		} catch (IllegalArgumentException e) {
			return _placeholderForFloat;
		}
	}
	
	/**
	 * 
	 * @param value
	 * @param pattern
	 * @param placeholder
	 * @return
	 */
	public String format(final Float value, final String pattern, final String placeholder) {
		try {
			final String pattern4 = (pattern == null || pattern.isEmpty()) ? _format4 : pattern;
			java.text.NumberFormat format = _map2.get(pattern4);
			if (format == null) {
				_map2.put(pattern4, format = new DecimalFormat(pattern4));
			}
			return format.format(value);
		} catch (NullPointerException e) {
			return (placeholder == null || placeholder.isEmpty()) ? _placeholderForNull : placeholder;
		} catch (IllegalArgumentException e) {
			return (placeholder == null || placeholder.isEmpty()) ? _placeholderForFloat : placeholder;
		}
	}
	
	/**
	 * 
	 * @param value
	 * @param pattern
	 * @param placeholder
	 * @return
	 */
	public String format(final float value, final String pattern, final String placeholder) {
		try {
			final String pattern4 = (pattern == null || pattern.isEmpty()) ? _format4 : pattern;
			java.text.NumberFormat format = _map2.get(pattern4);
			if (format == null) {
				_map2.put(pattern4, format = new DecimalFormat(pattern4));
			}
			return format.format(value);
		} catch (NullPointerException e) {
			return (placeholder == null || placeholder.isEmpty()) ? _placeholderForNull : placeholder;
		} catch (IllegalArgumentException e) {
			return (placeholder == null || placeholder.isEmpty()) ? _placeholderForFloat : placeholder;
		}
	}
	
	/**
	 * 
	 * @param value
	 * @return
	 */
	public String format(final Double value) {
		try {
			java.text.NumberFormat format = _map2.get((Math.round(value) - value == 0) ? _format5 : _format4);
			if (format == null) {
				_map2.put(_format4, format = new DecimalFormat(_format4));
			}
			return format.format(value);
		} catch (NullPointerException e) {
			return _placeholderForNull;
		} catch (IllegalArgumentException e) {
			return _placeholderForDouble;
		}
	}
	
	/**
	 * 
	 * @param value
	 * @return
	 */
	public String format(final double value) {
		try {
			java.text.NumberFormat format = _map2.get((Math.round(value) - value == 0) ? _format5 : _format4);
			if (format == null) {
				_map2.put(_format4, format = new DecimalFormat(_format4));
			}
			return format.format(value);
		} catch (NullPointerException e) {
			return _placeholderForNull;
		} catch (IllegalArgumentException e) {
			return _placeholderForDouble;
		}
	}
	
	/**
	 * 
	 * @param value
	 * @param pattern
	 * @return
	 */
	public String format(final Double value, final String pattern) {
		try {
			final String pattern5 = (pattern == null || pattern.isEmpty()) ? ((Math.round(value) - value == 0) ? _format5 : _format4) : pattern;
			java.text.NumberFormat format = _map2.get(pattern5);
			if (format == null) {
				_map2.put(pattern5, format = new DecimalFormat(pattern5));
			}
			return format.format(value);
		} catch (NullPointerException e) {
			return _placeholderForNull;
		} catch (IllegalArgumentException e) {
			return _placeholderForDouble;
		}
	}
	
	/**
	 * 
	 * @param value
	 * @param pattern
	 * @return
	 */
	public String format(final double value, final String pattern) {
		try {
			final String pattern5 = (pattern == null || pattern.isEmpty()) ? ((Math.round(value) - value == 0) ? _format5 : _format4) : pattern;
			java.text.NumberFormat format = _map2.get(pattern5);
			if (format == null) {
				_map2.put(pattern5, format = new DecimalFormat(pattern5));
			}
			return format.format(value);
		} catch (NullPointerException e) {
			return _placeholderForNull;
		} catch (IllegalArgumentException e) {
			return _placeholderForDouble;
		}
	}
	
	/**
	 * 
	 * @param value
	 * @param pattern
	 * @param placeholder
	 * @return
	 */
	public String format(final Double value, final String pattern, final String placeholder) {
		try {
			final String pattern5 = (pattern == null || pattern.isEmpty()) ? ((Math.round(value) - value == 0) ? _format5 : _format4) : pattern;
			java.text.NumberFormat format = _map2.get(pattern5);
			if (format == null) {
				_map2.put(pattern5, format = new DecimalFormat(pattern5));
			}
			return format.format(value);
		} catch (NullPointerException e) {
			return (placeholder == null || placeholder.isEmpty()) ? _placeholderForNull : placeholder;
		} catch (IllegalArgumentException e) {
			return (placeholder == null || placeholder.isEmpty()) ? _placeholderForDouble : placeholder;
		}
	}
	
	/**
	 * 
	 * @param value
	 * @param pattern
	 * @param placeholder
	 * @return
	 */
	public String format(final double value, final String pattern, final String placeholder) {
		try {
			final String pattern5 = (pattern == null || pattern.isEmpty()) ? ((Math.round(value) - value == 0) ? _format5 : _format4) : pattern;
			java.text.NumberFormat format = _map2.get(pattern5);
			if (format == null) {
				_map2.put(pattern5, format = new DecimalFormat(pattern5));
			}
			return format.format(value);
		} catch (NullPointerException e) {
			return (placeholder == null || placeholder.isEmpty()) ? _placeholderForNull : placeholder;
		} catch (IllegalArgumentException e) {
			return (placeholder == null || placeholder.isEmpty()) ? _placeholderForDouble : placeholder;
		}
	}
	
	/**
	 * 
	 * @param value
	 * @return
	 */
	public String format(final String value) {
		if (value == null) {
			return _placeholderForNull;
		} else if (value.isEmpty()) {
			return _placeholderForEmpty;
		} else {
			return value;
		}
	}
	
	/**
	 * 
	 * @param value
	 * @param placeholder
	 * @return
	 */
	public String format(final String value, final String placeholder) {
		if (value == null) {
			return (placeholder);
		} else if (value.isEmpty()) {
			return (placeholder);
		} else {
			return value;
		}
	}
	
	/**
	 * 
	 * @param value
	 * @return
	 */
	public String format(final Boolean value) {
		if (value == null) {
			return _placeholderForNull;
		} else {
			return value.toString();
		}
	}
	
	/**
	 * 
	 * @param value
	 * @return
	 */
	public String format(final boolean value) {
		return Boolean.toString(value);
	}
	
	/**
	 * 
	 * @param value
	 * @return
	 */
	public String format(final Boolean value, final Boolean upperCase) {
		if (value == null) {
			return _placeholderForNull;
		} else {
			return upperCase ? value.toString().toUpperCase() : value.toString();
		}
	}
	
	/**
	 * 
	 * @param value
	 * @return
	 */
	public String format(final boolean value, final Boolean upperCase) {
		return upperCase ? Boolean.toString(value).toUpperCase() : Boolean.toString(value);
	}
	
	/**
	 * 
	 * @param value
	 * @return
	 */
	public String format(final Object value) {
		if (value == null) {
			return _placeholderForNull;
		} else {
			return value.toString();
		}
	}
}
