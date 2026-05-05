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

/** An instruction telling the computer to halt executaion immediately. */
public class HaltInstruction extends Instruction {
	// MARK: Constructor
	/**
	 * Creates a new HaltInstruction with all other options set to 0, null, or false.
	 */
	public HaltInstruction() {
		super(0, 0, 0, 0, 0, null, false);
	}


	// MARK: Methods
	@Override
	public @NonNull List<Byte> asBytes() {
		return Arrays.asList(new Byte[]{0x00});
	}

	@Override
	public @NonNull String toString() {
		return "halt";
	}

	/**
	 * Returns 0, since the program counter should not be advanced after
	 * encountering a halt instruction.
	 * 
	 * @return The number of bytes to advance the program counter after
	 * processing this instruction
	 */
	public int size() {
		return 0;
	}
}
