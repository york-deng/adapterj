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
package com.adapterj.text;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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
	 * Constructs a Formatter instance.
	 */
	public Formatter() { }
	
	/**
	 * Sets a pattern for java.util.Date type.
	 * 
	 * @param pattern the pattern.
	 */
	public void setPatternForDate(final String pattern) {
		if (pattern != null && !pattern.isEmpty()) _format1 = pattern;
	}

	/**
	 * Sets a placeholder for java.util.Date type.
	 * 
	 * @param placeholder the placeholder.
	 */
	public void setPlaceholderForDate(final String placeholder) {
		_placeholderForDate = placeholder;
	}

	/**
	 * Sets a pattern for int type.
	 * 
	 * @param pattern the pattern.
	 */
	public void setPatternForInteger(final String pattern) {
		if (pattern != null && !pattern.isEmpty()) _format2 = pattern;
	}

	/**
	 * Sets a placeholder for int type.
	 * 
	 * @param placeholder the placeholder.
	 */
	public void setPlaceholderForInteger(final String placeholder) {
		_placeholderForInteger = placeholder;
	}

	/**
	 * Sets a pattern for long type.
	 * 
	 * @param pattern the pattern.
	 */
	public void setPatternForLong(final String pattern) {
		if (pattern != null && !pattern.isEmpty()) _format3 = pattern;
	}

	/**
	 * Sets a placeholder for long type.
	 * 
	 * @param placeholder the placeholder.
	 */
	public void setPlaceholderForLong(final String placeholder) {
		_placeholderForLong = placeholder;
	}

	/**
	 * Sets a pattern for float type.
	 * 
	 * @param pattern the pattern.
	 */
	public void setPatternForFloat(final String pattern) {
		if (pattern != null && !pattern.isEmpty()) _format4 = pattern;
	}

	/**
	 * Sets a placeholder for float type.
	 * 
	 * @param placeholder the placeholder.
	 */
	public void setPlaceholderForFloat(final String placeholder) {
		_placeholderForFloat = placeholder;
	}

	/**
	 * Sets a pattern for double type.
	 * 
	 * @param pattern the pattern.
	 */
	public void setPatternForDouble(final String pattern) {
		if (pattern != null && !pattern.isEmpty()) _format5 = pattern;
	}

	/**
	 * Sets a placeholder for double type.
	 * 
	 * @param placeholder the placeholder.
	 */
	public void setPlaceholderForDouble(final String placeholder) {
		_placeholderForDouble = placeholder;
	}

	/**
	 * Sets a placeholder for null.
	 * 
	 * @param placeholderForNull the placeholder for null.
	 */
	public void setPlaceholderForNull(final String placeholderForNull) {
		_placeholderForNull = placeholderForNull;
	}

	/**
	 * Sets a placeholder for empty value/string.
	 * 
	 * @param placeholderForEmpty the placeholder for empty value/string.
	 */
	public void setPlaceholderForEmpty(final String placeholderForEmpty) {
		_placeholderForEmpty = placeholderForEmpty;
	}

	/**
	 * Returns a formatted string by the given value.
	 * 
	 * @param value the given value.
	 * @return a formatted string by the given value.
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
	 * Returns a formatted string by the given value, and the given pattern.
	 * 
	 * @param value the given value.
	 * @param pattern the given pattern.
	 * @return a formatted string by the given value, and the given pattern.
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
	 * Returns a formatted string by the given value, the given pattern, and the given placeholder.
	 * 
	 * @param value the given value.
	 * @param pattern the given pattern.
	 * @param placeholder the given placeholder.
	 * @return a formatted string by the given value, the given pattern, and the given placeholder.
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
	 * Returns a formatted string by the given value.
	 * 
	 * @param value the given value.
	 * @return a formatted string by the given value.
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
	 * Returns a formatted string by the given value.
	 * 
	 * @param value the given value.
	 * @return a formatted string by the given value.
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
	 * Returns a formatted string by the given value, and the given pattern.
	 * 
	 * @param value the given value.
	 * @param pattern the given pattern.
	 * @return a formatted string by the given value, and the given pattern.
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
	 * Returns a formatted string by the given value, and the given pattern.
	 * 
	 * @param value the given value.
	 * @param pattern the given pattern.
	 * @return a formatted string by the given value, and the given pattern.
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
	 * Returns a formatted string by the given value, the given pattern, and the given placeholder.
	 * 
	 * @param value the given value.
	 * @param pattern the given pattern.
	 * @param placeholder the given placeholder.
	 * @return a formatted string by the given value, the given pattern, and the given placeholder.
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
	 * Returns a formatted string by the given value, the given pattern, and the given placeholder.
	 * 
	 * @param value the given value.
	 * @param pattern the given pattern.
	 * @param placeholder the given placeholder.
	 * @return a formatted string by the given value, the given pattern, and the given placeholder.
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
	 * Returns a formatted string by the given value.
	 * 
	 * @param value
	 * @return a formatted string by the given value.
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
	 * Returns a formatted string by the given value.
	 * 
	 * @param value the given value
	 * @return a formatted string by the given value.
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
	 * Returns a formatted string by the given value, the given pattern.
	 * 
	 * @param value the given value.
	 * @param pattern the given pattern.
	 * @return a formatted string by the given value, the given pattern.
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
	 * Returns a formatted string by the given value, the given pattern.
	 * 
	 * @param value the given value.
	 * @param pattern the given pattern.
	 * @return a formatted string by the given value, the given pattern.
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
	 * Returns a formatted string by the given value, the given pattern, and the given placeholder.
	 * 
	 * @param value the given value.
	 * @param pattern the given pattern.
	 * @param placeholder the given placeholder.
	 * @return a formatted string by the given value, the given pattern, and the given placeholder.
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
	 * Returns a formatted string by the given value, the given pattern, and the given placeholder.
	 * 
	 * @param value the given value.
	 * @param pattern the given pattern.
	 * @param placeholder the given placeholder.
	 * @return a formatted string by the given value, the given pattern, and the given placeholder.
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
	 * Returns a formatted string by the given value.
	 * 
	 * @param value the given value.
	 * @return a formatted string by the given value.
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
	 * Returns a formatted string by the given value.
	 * 
	 * @param value the given value.
	 * @return a formatted string by the given value.
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
	 * Returns a formatted string by the given value, and the given pattern.
	 * 
	 * @param value the given value.
	 * @param pattern the given pattern.
	 * @return a formatted string by the given value, and the given pattern.
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
	 * Returns a formatted string by the given value, and the given pattern.
	 * 
	 * @param value the given value
	 * @param pattern the given pattern
	 * @return a formatted string by the given value, and the given pattern.
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
	 * Returns a formatted string by the given value, the given pattern, and the given placeholder.
	 * 
	 * @param value the given value
	 * @param pattern the given pattern
	 * @param placeholder the given placeholder
	 * @return a formatted string by the given value, the given pattern, and the given placeholder.
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
	 * Returns a formatted string by the given value, the given pattern, and the given placeholder.
	 * 
	 * @param value the given value
	 * @param pattern the given pattern
	 * @param placeholder the given placeholder
	 * @return a formatted string by the given value, the given pattern, and the given placeholder.
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
	 * Returns a formatted string by the given value.
	 * 
	 * @param value the given value.
	 * @return a formatted string.
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
	 * Returns a formatted string by the given value.
	 * 
	 * @param value the given value.
	 * @return a formatted string.
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
	 * Returns a formatted string by the given value, the given pattern.
	 * 
	 * @param value the given value.
	 * @param pattern the given pattern.
	 * @return a formatted string.
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
	 * Returns a formatted string by the given value, the given pattern.
	 * 
	 * @param value the given value.
	 * @param pattern the given pattern.
	 * @return a formatted string.
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
	 * Returns a formatted string by the given value, the given pattern, and the given placeholder.
	 * 
	 * @param value the given value.
	 * @param pattern the given pattern.
	 * @param placeholder the given placeholder.
	 * @return a formatted string.
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
	 * Returns a formatted string by the given value, the given pattern, and the given placeholder.
	 * 
	 * @param value the given value.
	 * @param pattern the given pattern.
	 * @param placeholder the given placeholder.
	 * @return a formatted string.
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
	 * Returns a formatted string by the given value.
	 * 
	 * @param value the given value.
	 * @return a formatted string.
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
	 * Returns a formatted string by the given value and the given placeholder.
	 * 
	 * @param value the given value.
	 * @param placeholder the given placeholder.
	 * @return a formatted string.
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
	 * Returns a formatted string by the given value.
	 * 
	 * @param value the given value.
	 * @return a formatted string.
	 */
	public String format(final Boolean value) {
		if (value == null) {
			return _placeholderForNull;
		} else {
			return value.toString();
		}
	}
	
	/**
	 * Returns a formatted string by the given value.
	 * 
	 * @param value the given value.
	 * @return a formatted string.
	 */
	public String format(final boolean value) {
		return Boolean.toString(value);
	}
	
	/**
	 * Returns a formatted string by the given value.
	 * 
	 * @param value the given value.
	 * @return a formatted string.
	 */
	public String format(final Boolean value, final Boolean upperCase) {
		if (value == null) {
			return _placeholderForNull;
		} else {
			return upperCase ? value.toString().toUpperCase() : value.toString();
		}
	}
	
	/**
	 * Returns a formatted string by the given value.
	 * 
	 * @param value the given value.
	 * @return a formatted string.
	 */
	public String format(final boolean value, final Boolean upperCase) {
		return upperCase ? Boolean.toString(value).toUpperCase() : Boolean.toString(value);
	}
	
	/**
	 * Returns a formatted string by the given value.
	 * 
	 * @param value the given value.
	 * @return a formatted string.
	 */
	public String format(final Object value) {
		if (value == null) {
			return _placeholderForNull;
		} else {
			return value.toString();
		}
	}
}
