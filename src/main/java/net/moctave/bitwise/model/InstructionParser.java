package net.moctave.bitwise.model;

import java.util.Arrays;
import java.util.List;

import net.moctave.bitwise.exceptions.InstructionParseException;
import net.moctave.bitwise.model.instructions.*;
import net.moctave.bitwise.utils.Constants;

/** A class with helper methods to convert given lines of manually-entered text to instructions. */
public abstract class InstructionParser {
	public static final List<String> BINARY_INSTRUCTIONS =
			Arrays.asList(new String[]{"copy", "add", "sub", "and", "or", "xor"});
	public static final List<String> UNARY_INSTRUCTIONS =
			Arrays.asList(new String[]{"inc", "neg", "not"});
	public static final List<String> JUMP_INSTRUCTIONS =
			Arrays.asList(new String[]{"jump", "je", "jle", "jge", "jne", "jl", "jg"});



	// MARK: Methods
	/**
	 * Converts a given line of input to an instruction.
	 * @param line the line to parse
	 * @return the appropriate corresponding instruction
	 * @throws InstructionParseException if no suitable, well-formed instruction is found.
	 */
	public static Instruction convertToInstruction(String line)
			throws InstructionParseException {

		if (line.contains(":")) {
			return new Label(convertToLabel(line));
		}

		String type = line.split(" ")[0].toLowerCase();

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
	 * @param line the line to parse
	 * @return the appropriate corresponding instruction
	 * @throws InstructionParseException if no suitable, well-formed instruction is found.
	 */
	private static MoveInstruction convertToMoveInstruction(String line)
			throws InstructionParseException {
		try {
			String regAText = line.split(" ")[1].toLowerCase();
			String valCText = line.split(" ")[2].toLowerCase();
			return new MoveInstruction(convertToRegisterAddress(regAText), convertToInteger(valCText));
		} catch (IndexOutOfBoundsException e) {
			throw new InstructionParseException("Malformed move instruction.");
		}
	}

	/**
	 * Converts a given line of input to a binary ALU instruction.
	 * @param line the line to parse
	 * @return the appropriate corresponding instruction
	 * @throws InstructionParseException if no suitable, well-formed instruction is found.
	 */
	protected static BinaryInstruction convertToBinaryInstruction(String line)
			throws InstructionParseException {
		try {
			String type = line.split(" ")[0].toLowerCase();
			int regA = convertToRegisterAddress(line.split(" ")[1].toLowerCase());
			int regB = convertToRegisterAddress(line.split(" ")[2].toLowerCase());
			switch (type) {
				case "copy": return new CopyInstruction(regA, regB);
				case "add":  return new AddInstruction(regA, regB);
				case "sub":  return new SubInstruction(regA, regB);
				case "and":  return new AndInstruction(regA, regB);
				case "or":   return new OrInstruction(regA, regB);
				case "xor":  return new XorInstruction(regA, regB);
			}
		} catch (IndexOutOfBoundsException e) {
			// Intentionally left blank
		}
		throw new InstructionParseException("Malformed binary instruction.");
	}

	/**
	 * Converts a given line of input to a unary ALU instruction.
	 * @param line the line to parse
	 * @return the appropriate corresponding instruction
	 * @throws InstructionParseException if no suitable, well-formed instruction is found.
	 */
	protected static UnaryInstruction convertToUnaryInstruction(String line)
			throws InstructionParseException {
		try {
			String type = line.split(" ")[0].toLowerCase();
			int regA = convertToRegisterAddress(line.split(" ")[1].toLowerCase());
			switch (type) {
				case "inc": return new IncInstruction(regA);
				case "neg": return new NegInstruction(regA);
				case "not": return new NotInstruction(regA);
			}
		} catch (IndexOutOfBoundsException e) {
			// Intentionally left blank
		}
		throw new InstructionParseException("Malformed unary instruction.");
	}

	/**
	 * Converts a given line of input to a jump instruction.
	 * @param line the line to parse
	 * @return the appropriate corresponding instruction
	 * @throws InstructionParseException if no suitable, well-formed instruction is found.
	 */
	protected static JumpInstruction convertToJumpInstruction(String line)
			throws InstructionParseException {
		try {
			String type = line.split(" ")[0].toLowerCase();
			String label = line.split(" ", 2)[1].toLowerCase();
			switch (type) {
				case "jump": return new JumpAlwaysInstruction(label);
				case "je": return new JumpEqualsInstruction(label);
				case "jle": return new JumpLessEqualsInstruction(label);
				case "jge": return new JumpGreaterEqualsInstruction(label);
				case "jne": return new JumpNotEqualsInstruction(label);
				case "jl": return new JumpLessInstruction(label);
				case "jg": return new JumpGreaterInstruction(label);
			}
		} catch (IndexOutOfBoundsException e) {
			// Intentionally left blank
		}
		throw new InstructionParseException("Malformed jump instruction.");
	}

	/**
	 * Converts a given string in the form "rX" to the appropriate integer matching
	 * the hex digit X.
	 * @param text the string to parse
	 * @return the register address represented by the string
	 * @throws InstructionParseException if the string cannot be parsed to an integer.
	 */
	private static int convertToRegisterAddress(String text)
			throws InstructionParseException {
		try {
			char hexChar = text.toUpperCase().toCharArray()[1];
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
	 * @param text the string to parse
	 * @return the integer represented by the string
	 * @throws InstructionParseException if the string cannot be parsed to an integer.
	 */
	private static int convertToInteger(String text)
			throws InstructionParseException {
		try {
			return Integer.parseInt(text);
		} catch (Exception e) {
			throw new InstructionParseException("Invalid integer encountered.");
		}
	}

	/**
	 * Sanitizes a given string and returns it as label text.
	 * @param text the string to parse to a label (must contain a colon)
	 * @return the label stored in the string
	 * @throws InstructionParseException if the string cannot be parsed to a label.
	 */
	private static String convertToLabel(String text) throws InstructionParseException {
		return text.split(":")[0];
	}
}
