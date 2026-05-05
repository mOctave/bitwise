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

package net.moctave.bitwise.exceptions;

import org.jspecify.annotations.NonNull;

/** An exception to indicate a general error with parsing an instruction. */
public class InstructionParseException extends Exception {
	/**
	 * Creates an instruction parse exception with the given message.
	 * 
	 * @param message An error message explaining why instruction parsing failed
	 */
	public InstructionParseException(@NonNull String message) {
		super(message);
	}
}
