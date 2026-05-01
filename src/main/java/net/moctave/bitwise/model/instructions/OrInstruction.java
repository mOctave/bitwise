package net.moctave.bitwise.model.instructions;

import net.moctave.bitwise.utils.Constants;

/** An instruction telling the computer to perform bitwise or on two values. */
public class OrInstruction extends BinaryInstruction {
	// MARK: Constructor
	/**
	 * Creates a new OrInstruction with opCode 2, fnCode 4, given regA and
	 * regB, and all other options set to 0, null, or false.
	 * @param regA the regA value for this instruction, bounded on [1, 15]
	 * @param regB the regB value for this instruction, bounded on [1, 15]
	 */
	public OrInstruction(int regA, int regB) {
		super(4, regA, regB);
	}


	// MARK: Methods
	@Override
	public String toString() {
		return String.format(
			"or r%s r%s",
			Constants.HEX_DIGITS[getRegA()],
			Constants.HEX_DIGITS[getRegB()]
		);
	}
}
