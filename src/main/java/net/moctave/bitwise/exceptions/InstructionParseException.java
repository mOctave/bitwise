package net.moctave.bitwise.exceptions;

import org.jspecify.annotations.NonNull;

/** An exception to indicate a general error with parsing an instruction. */
public class InstructionParseException extends Exception {
	/**
	 * Creates an instruction parse exception with the given message.
	 * @param message An error message explaining why instruction parsing failed
	 */
	public InstructionParseException(@NonNull String message) {
		super(message);
	}
}
