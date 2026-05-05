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

package net.moctave.bitwise.model;

import org.jspecify.annotations.NonNull;

import net.moctave.bitwise.utils.Constants;

/** A register capable of storing a value of a certain size. */
public class Register {
	// MARK: Fields
	/** The name of this register. */
	private final @NonNull String name;
	/** The value stored in this register (unbounded). */
	private int value;
	/** The apparent size of this register, bounded on [1, 32]. */
	private final int size;



	// MARK: Constructor
	/**
	 * Creates a new register with the given name and apparent size.
	 * 
	 * @param name The name associated with this register
	 * @param size The apparent size of this register, in bits, between 1 and 32 inclusive
	 */
	public Register(@NonNull String name, int size) {
		this.name = name;
		this.value = 0;
		this.size = size;
	}



	// MARK: Methods
	/**
	 * Sets this register to a new value.
	 * 
	 * @param value The new value for this register
	 */
	public void setValue(int value) {
		this.value = value;
	}



	/**
	 * Represents the value stored in the register as a string of hex
	 * digits, limited by the apparent size.
	 * 
	 * @return a string representation of the value stored in this register
	 */
	public @NonNull String valueAsString() {
		String resultSoFar = "";
		int valueRemaining = getValue();
		int parsedBits = 0;

		while (parsedBits + 4 <= getSize()) {
			final int hexDigit = valueRemaining & 0xF;
			resultSoFar = Constants.HEX_DIGITS[hexDigit] + resultSoFar;
			valueRemaining = valueRemaining >>> 4;
			parsedBits += 4;
		}

		final int bitsLeft = getSize() - parsedBits;
		if (bitsLeft > 0) {
			final int hexDigit = valueRemaining & ((int) Math.pow(2, bitsLeft) - 1);
			resultSoFar = Constants.HEX_DIGITS[hexDigit] + resultSoFar;
		}

		return resultSoFar;
	}



	// MARK: Getters
	/**
	 * Getter for this register's name.
	 * 
	 * @return {@link #name}
	 */
	public @NonNull String getName() {
		return name;
	}

	/**
	 * Getter for this register's value.
	 * 
	 * @return {@link #value}
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Getter for this register's apparent size.
	 * 
	 * @return {@link #size}
	 */
	public int getSize() {
		return size;
	}
}
