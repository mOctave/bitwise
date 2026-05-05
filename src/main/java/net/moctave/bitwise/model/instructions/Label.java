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

import java.util.ArrayList;
import java.util.List;

import org.jspecify.annotations.NonNull;

/** An instruction telling the computer to peform a conditional jump. */
public class Label extends Instruction {
	// MARK: Constructor
	/**
	 * Creates a new label with opCode 5, the given label text,
	 * isLabel set to true, and all other options set to 0, null, or false.
	 * 
	 * @param labelText the text of this label
	 */
	public Label(@NonNull String labelText) {
		super(5, 0, 0, 0, 0, labelText, true);
	}


	@Override
	public @NonNull List<Byte> asBytes() {
		return new ArrayList<>();
	}


	@Override
	public @NonNull String toString() {
		return getLabel() + ":";
	}

	/**
	 * Returns 0, since labels are not actually converted directly to machine code.
	 * 
	 * @return The number of bytes to advance the program counter after
	 * processing this instruction
	 */
	@Override
	public int size() {
		return 0;
	}
}
