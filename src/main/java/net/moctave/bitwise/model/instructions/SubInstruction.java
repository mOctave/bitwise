package net.moctave.bitwise.model.instructions;

import net.moctave.bitwise.utils.Constants;

/** An instruction telling the computer to subtract two values. */
public class SubInstruction extends BinaryInstruction {
	// MARK: Constructor
	/**
	 * Creates a new SubInstruction with opCode 2, fnCode 2, given regA and
	 * regB, and all other options set to 0, null, or false.
	 * @param regA the regA value for this instruction, bounded on [1, 15]
	 * @param regB the regB value for this instruction, bounded on [1, 15]
	 */
	public SubInstruction(int regA, int regB) {
		super(2, regA, regB);
	}


	// MARK: Methods
	@Override
	public String toString() {
		return String.format(
			"sub r%s r%s",
			Constants.HEX_DIGITS[getRegA()],
			Constants.HEX_DIGITS[getRegB()]
		);
	}
}
