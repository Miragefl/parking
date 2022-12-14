package com.viscum.common.util;

import java.nio.ByteBuffer;

public class HexUtil {
	private static final char[] hexArray = "0123456789ABCDEF".toCharArray();

	public static String toHex(byte[] bytes) {
		return toHex(bytes, bytes.length);
	}

	public static String toHex(byte[] bytes, int len) {
		char[] hexChars = new char[len * 2];
		for (int j = 0; j < len; j++) {
			int v = bytes[j] & 0xFF;
			hexChars[j * 2] = hexArray[v >>> 4];
			hexChars[j * 2 + 1] = hexArray[v & 0x0F];
		}
		return new String(hexChars);
	}

}
