package net.moctave.bitwise.utils;

/** A class of helper methods used to convert between data formats. */
public abstract class Conversion {
	/**
	 * Converts a byte to a two-bit hex string.
	 * @param b the byte to convert
	 * @return a string representation of the byte
	 */
	public static String toHexString(byte b) {
		char leading = Constants.HEX_DIGITS[(b >>> 4) & 0xF];
		char trailing = Constants.HEX_DIGITS[b & 0xF];
		return String.format("%s%s", leading, trailing);
	}
}
