package net.moctave.bitwise.model.instructions;

import net.moctave.bitwise.utils.Constants;

/** An instruction telling the computer to perform bitwise and on two values. */
public class AndInstruction extends BinaryInstruction {
	// MARK: Constructor
	/**
	 * Creates a new AndInstruction with opCode 2, fnCode 3, given regA and
	 * regB, and all other options set to 0, null, or false.
	 * @param regA the regA value for this instruction, bounded on [1, 15]
	 * @param regB the regB value for this instruction, bounded on [1, 15]
	 */
	public AndInstruction(int regA, int regB) {
		super(3, regA, regB);
	}


	// MARK: Methods
	@Override
	public String toString() {
		return String.format(
			"and r%s r%s",
			Constants.HEX_DIGITS[getRegA()],
			Constants.HEX_DIGITS[getRegB()]
		);
	}
}
