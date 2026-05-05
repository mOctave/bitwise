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

import java.util.Set;

import org.jspecify.annotations.NonNull;

import net.moctave.bitwise.exceptions.InstructionParseException;
import net.moctave.bitwise.model.instructions.*;
import net.moctave.bitwise.utils.Constants;

/** A class with helper methods to convert given lines of manually-entered text to instructions. */
public abstract class InstructionParser {
	/** The instructions the computer recognizes as binary. */
	public static final @NonNull Set<String> BINARY_INSTRUCTIONS =
			Set.of("copy", "add", "sub", "and", "or", "xor");
	/** The instructions the computer recognizes as unary. */
	public static final @NonNull Set<String> UNARY_INSTRUCTIONS = Set.of("inc", "neg", "not");
	/** The instructions the computer recognizes as jump instructions. */
	public static final @NonNull Set<String> JUMP_INSTRUCTIONS =
			Set.of("jump", "je", "jle", "jge", "jne", "jl", "jg");



	// MARK: Methods
	/**
	 * Converts a given line of input to an instruction.
	 * 
	 * @param line the line to parse
	 * @return the appropriate corresponding instruction
	 * @throws InstructionParseException if no suitable, well-formed instruction is found.
	 */
	public static @NonNull Instruction convertToInstruction(@NonNull String line)
			throws InstructionParseException {

		if (line.contains(":")) {
			return new Label(convertToLabel(line));
		}

		final String type = line.split(" ")[0].toLowerCase();

		if (type.equals("halt")) {
			return new HaltInstruction();
		} else if (type.equals("move")) {
			return convertToMoveInstruction(line);
		} else if (BINARY_INSTRUCTIONS.contains(type)) {
			return convertToBinaryInstruction(line);
		} else if (UNARY_INSTRUCTIONS.contains(type)) {
			return convertToUnaryInstruction(line);
		} else if (JUMP_INSTRUCTIONS.contains(type)) {
			return convertToJumpInstruction(line);
		} else {
			throw new InstructionParseException("Invalid instruction type.");
		}
	}

	/**
	 * Converts a given line of input to a move instruction.
	 * 
	 * @param line the line to parse
	 * @return the appropriate corresponding instruction
	 * @throws InstructionParseException if no suitable, well-formed instruction is found.
	 */
	private static @NonNull MoveInstruction convertToMoveInstruction(@NonNull String line)
			throws InstructionParseException {
		try {
			final String regAText = line.split(" ")[1].toLowerCase();
			final String valCText = line.split(" ")[2].toLowerCase();
			return new MoveInstruction(convertToRegisterAddress(regAText), convertToInteger(valCText));
		} catch (IndexOutOfBoundsException e) {
			throw new InstructionParseException("Malformed move instruction.");
		}
	}

	/**
	 * Converts a given line of input to a binary ALU instruction.
	 * 
	 * @param line the line to parse
	 * @return the appropriate corresponding instruction
	 * @throws InstructionParseException if no suitable, well-formed instruction is found.
	 */
	protected static @NonNull BinaryInstruction convertToBinaryInstruction(@NonNull String line)
			throws InstructionParseException {
		try {
			final String type = line.split(" ")[0].toLowerCase();
			final int regA = convertToRegisterAddress(line.split(" ")[1].toLowerCase());
			final int regB = convertToRegisterAddress(line.split(" ")[2].toLowerCase());
			switch (type) {
				case "copy":
					return new CopyInstruction(regA, regB);
				case "add":
					return new AddInstruction(regA, regB);
				case "sub":
					return new SubInstruction(regA, regB);
				case "and":
					return new AndInstruction(regA, regB);
				case "or":
					return new OrInstruction(regA, regB);
				case "xor":
					return new XorInstruction(regA, regB);
				default:
					throw new InstructionParseException("Instruction miscategorized as binary.");
			}
		} catch (IndexOutOfBoundsException e) {
			// Intentionally left blank
		}
		throw new InstructionParseException("Malformed binary instruction.");
	}

	/**
	 * Converts a given line of input to a unary ALU instruction.
	 * 
	 * @param line the line to parse
	 * @return the appropriate corresponding instruction
	 * @throws InstructionParseException if no suitable, well-formed instruction is found.
	 */
	protected static @NonNull UnaryInstruction convertToUnaryInstruction(@NonNull String line)
			throws InstructionParseException {
		try {
			final String type = line.split(" ")[0].toLowerCase();
			final int regA = convertToRegisterAddress(line.split(" ")[1].toLowerCase());
			switch (type) {
				case "inc":
					return new IncInstruction(regA);
				case "neg":
					return new NegInstruction(regA);
				case "not":
					return new NotInstruction(regA);
				default:
					throw new InstructionParseException("Instruction miscategorized as unary.");
			}
		} catch (IndexOutOfBoundsException e) {
			// Intentionally left blank
		}
		throw new InstructionParseException("Malformed unary instruction.");
	}

	/**
	 * Converts a given line of input to a jump instruction.
	 * 
	 * @param line the line to parse
	 * @return the appropriate corresponding instruction
	 * @throws InstructionParseException if no suitable, well-formed instruction is found.
	 */
	protected static @NonNull JumpInstruction convertToJumpInstruction(@NonNull String line)
			throws InstructionParseException {
		try {
			final String type = line.split(" ")[0].toLowerCase();
			final String label = line.split(" ", 2)[1].toLowerCase();
			switch (type) {
				case "jump":
					return new JumpAlwaysInstruction(label);
				case "je":
					return new JumpEqualsInstruction(label);
				case "jle":
					return new JumpLessEqualsInstruction(label);
				case "jge":
					return new JumpGreaterEqualsInstruction(label);
				case "jne":
					return new JumpNotEqualsInstruction(label);
				case "jl":
					return new JumpLessInstruction(label);
				case "jg":
					return new JumpGreaterInstruction(label);
				default:
					throw new InstructionParseException("Instruction miscategorized as binary.");
			}
		} catch (IndexOutOfBoundsException e) {
			// Intentionally left blank
		}
		throw new InstructionParseException("Malformed jump instruction.");
	}

	/**
	 * Converts a given string in the form "rX" to the appropriate integer matching
	 * the hex digit X.
	 * 
	 * @param text the string to parse
	 * @return the register address represented by the string
	 * @throws InstructionParseException if the string cannot be parsed to an integer.
	 */
	private static int convertToRegisterAddress(@NonNull String text)
			throws InstructionParseException {
		try {
			final char hexChar = text.toUpperCase().toCharArray()[1];
			for (int i = 1; i < Constants.HEX_DIGITS.length; i++) {
				if (Constants.HEX_DIGITS[i] == hexChar) {
					return i;
				}
			}
		} catch (Exception e) {
			// Intentionally left blank
		}

		throw new InstructionParseException("Invalid register encountered.");
	}

	/**
	 * Converts a given string in the form "X" to the appropriate integer X.
	 * 
	 * @param text the string to parse
	 * @return the integer represented by the string
	 * @throws InstructionParseException if the string cannot be parsed to an integer.
	 */
	private static int convertToInteger(@NonNull String text)
			throws InstructionParseException {
		try {
			return Integer.parseInt(text);
		} catch (Exception e) {
			throw new InstructionParseException("Invalid integer encountered.");
		}
	}

	/**
	 * Sanitizes a given string and returns it as label text.
	 * 
	 * @param text the string to parse to a label (must contain a colon)
	 * @return the label stored in the string
	 * @throws InstructionParseException if the string cannot be parsed to a label.
	 */
	private static @NonNull String convertToLabel(@NonNull String text) throws InstructionParseException {
		return text.split(":")[0];
	}
}
