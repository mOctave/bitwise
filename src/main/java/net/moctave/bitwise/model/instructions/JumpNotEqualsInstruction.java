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

/** An instruction telling the computer to jump when last result ≠ 0. */
public class JumpNotEqualsInstruction extends JumpInstruction {
	// MARK: Constructor
	/**
	 * Creates a new JumpNotEqualsInstruction with opCode 4, fnCode 4, given label,
	 * and all other options set to 0, null, or false.
	 * 
	 * @param label the label for this instruction to jump to
	 */
	public JumpNotEqualsInstruction(@NonNull String label) {
		super(4, label);
	}


	// MARK: Methods
	@Override
	public @NonNull String toString() {
		return String.format("jne %s", getLabel());
	}
}
