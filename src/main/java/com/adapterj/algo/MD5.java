/*
 * Copyright (c) 2013 Chris Banes.
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
package com.adapterj.algo;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Utilities for encoding and decoding the MD5 representation of string data.
 */
public class MD5 {

    /**
     * MD5-encode the given data and return a newly string with the result.
     * @param text the input String to decode, which is converted to MD5 string using the default charset
     * @return the MD5-encoded data
     */
    public static String encode(final String text) {
        try {
            return toHexString(MessageDigest.getInstance("MD5").digest(text.getBytes()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return (null);
    }

    /**
     * MD5-encode the given data and return a newly string with the result.
     * @param text the input String to decode, which is converted to MD5 string using the given charset
     * @param charset the given charset
     * @return the MD5-encoded data
     */
    public static String encode(final String text, final String charset) {
        try {
            return toHexString(MessageDigest.getInstance("MD5").digest(text.getBytes(charset)));
        } catch (UnsupportedEncodingException e) {
        	e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return (null);
    }

    // private methods

    private static final char[] DIGITS = {
    	'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 
    	'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 
    	'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 
        'u', 'v', 'w', 'x', 'y', 'z'
    };

    private static String toHexString(byte[] bytes) {
        final char[] buf = new char[bytes.length * 2];

        byte b;
        int c = 0;
        for (int i = 0, z = bytes.length; i < z; i++) {
            b = bytes[i];
            buf[c++] = DIGITS[(b >> 4) & 0xf];
            buf[c++] = DIGITS[b & 0xf];
        }

        return new String(buf);
    }
}
