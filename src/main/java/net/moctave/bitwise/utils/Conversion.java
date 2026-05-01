package net.moctave.bitwise.utils;

import org.jspecify.annotations.NonNull;

/** A class of helper methods used to convert between data formats. */
public abstract class Conversion {
	/**
	 * Converts a byte to a two-bit hex string.
	 * 
	 * @param b the byte to convert
	 * @return a string representation of the byte
	 */
	public static @NonNull String toHexString(byte b) {
		final char leading = Constants.HEX_DIGITS[(b >>> 4) & 0xF];
		final char trailing = Constants.HEX_DIGITS[b & 0xF];
		return String.format("%s%s", leading, trailing);
	}
}
