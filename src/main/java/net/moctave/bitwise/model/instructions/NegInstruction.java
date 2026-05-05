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

package net.moctave.bitwise.model.instructions;

import org.jspecify.annotations.NonNull;

import net.moctave.bitwise.utils.Constants;

/** An instruction telling the computer to negate (2's complement) the value in a register. */
public class NegInstruction extends UnaryInstruction {
	// MARK: Constructor
	/**
	 * Creates a new NegInstruction with opCode 3, fnCode 1, given regA,
	 * and all other options set to 0, null, or false.
	 * 
	 * @param regA the regA value for this instruction, bounded on [1, 15]
	 */
	public NegInstruction(int regA) {
		super(1, regA);
	}


	// MARK: Methods
	@Override
	public @NonNull String toString() {
		return String.format("neg r%s", Constants.HEX_DIGITS[getRegA()]);
	}
}
