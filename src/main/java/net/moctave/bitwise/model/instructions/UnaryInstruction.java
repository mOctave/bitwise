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

import java.util.Arrays;
import java.util.List;

import org.jspecify.annotations.NonNull;

/** An instruction telling the computer to peform an ALU operation on one value. */
public abstract class UnaryInstruction extends Instruction {
	// MARK: Constructor
	/**
	 * Creates a new JumpInstruction with opCode 3, the given fnCode and regA,
	 * and all other options set to 0, null, or false.
	 * 
	 * @param fnCode the function code for the specific unary operation to
	 * perform, bounded on [0, 2]
	 * @param regA the regA value for this instruction, bounded on [1, 15]
	 */
	public UnaryInstruction(int fnCode, int regA) {
		super(3, fnCode, regA, 0, 0, null, false);
	}


	// MARK: Methods
	@Override
	public @NonNull List<Byte> asBytes() {
		return Arrays.asList(new Byte[]{
			(byte) ((getOpCode() << 4) + getFnCode()),
			(byte) (getRegA() << 4)
		});
	}

	/**
	 * Returns 2, the number of bytes required to store this instruction.
	 * 
	 * @return The number of bytes to advance the program counter after
	 * processing this instruction
	 */
	@Override
	public int size() {
		return 2;
	}
}
