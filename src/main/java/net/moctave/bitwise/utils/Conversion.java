// Bitwise - A RISC simulator
// Copyright (C) 2026 mOctave
//
// This program is free software: you can redistribute it and/or modify
// it under the terms of the GNU Affero General Public License as published
// by the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU Affero General Public License for more details.
//
// You should have received a copy of the GNU Affero General Public License
// along with this program.  If not, see <https://www.gnu.org/licenses/>.

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
