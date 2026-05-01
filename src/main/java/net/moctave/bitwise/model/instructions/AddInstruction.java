package net.moctave.bitwise.model.instructions;

import org.jspecify.annotations.NonNull;

import net.moctave.bitwise.utils.Constants;

/** An instruction telling the computer to add two values. */
public class AddInstruction extends BinaryInstruction {
	// MARK: Constructor
	/**
	 * Creates a new AddInstruction with opCode 2, fnCode 1, given regA and
	 * regB, and all other options set to 0, null, or false.
	 * 
	 * @param regA the regA value for this instruction, bounded on [1, 15]
	 * @param regB the regB value for this instruction, bounded on [1, 15]
	 */
	public AddInstruction(int regA, int regB) {
		super(1, regA, regB);
	}


	// MARK: Methods
	@Override
	public @NonNull String toString() {
		return String.format(
			"add r%s r%s",
			Constants.HEX_DIGITS[getRegA()],
			Constants.HEX_DIGITS[getRegB()]
		);
	}
}
