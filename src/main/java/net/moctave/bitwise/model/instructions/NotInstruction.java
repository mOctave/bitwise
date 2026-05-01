package net.moctave.bitwise.model.instructions;

import org.jspecify.annotations.NonNull;

import net.moctave.bitwise.utils.Constants;

/** An instruction telling the computer to apply bitwise not to the value in a register. */
public class NotInstruction extends UnaryInstruction {
	// MARK: Constructor
	/**
	 * Creates a new NotInstruction with opCode 3, fnCode 2, given regA,
	 * and all other options set to 0, null, or false.
	 * 
	 * @param regA the regA value for this instruction, bounded on [1, 15]
	 */
	public NotInstruction(int regA) {
		super(2, regA);
	}


	// MARK: Methods
	@Override
	public @NonNull String toString() {
		return String.format("not r%s", Constants.HEX_DIGITS[getRegA()]);
	}
}
