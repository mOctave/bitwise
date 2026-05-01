package net.moctave.bitwise.model.instructions;

import org.jspecify.annotations.NonNull;

import net.moctave.bitwise.utils.Constants;

/** An instruction telling the computer to increment the value in a register. */
public class IncInstruction extends UnaryInstruction {
	// MARK: Constructor
	/**
	 * Creates a new IncInstruction with opCode 3, fnCode 0, given regA,
	 * and all other options set to 0, null, or false.
	 * 
	 * @param regA the regA value for this instruction, bounded on [1, 15]
	 */
	public IncInstruction(int regA) {
		super(0, regA);
	}


	// MARK: Methods
	@Override
	public @NonNull String toString() {
		return String.format("inc r%s", Constants.HEX_DIGITS[getRegA()]);
	}
}
