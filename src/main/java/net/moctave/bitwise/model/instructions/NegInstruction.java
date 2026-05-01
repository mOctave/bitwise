package net.moctave.bitwise.model.instructions;

import net.moctave.bitwise.utils.Constants;

/** An instruction telling the computer to negate (2's complement) the value in a register. */
public class NegInstruction extends UnaryInstruction {
	// MARK: Constructor
	/**
	 * Creates a new NegInstruction with opCode 3, fnCode 1, given regA,
	 * and all other options set to 0, null, or false.
	 * @param regA the regA value for this instruction, bounded on [1, 15]
	 */
	public NegInstruction(int regA) {
		super(1, regA);
	}


	// MARK: Methods
	@Override
	public String toString() {
		return String.format("neg r%s", Constants.HEX_DIGITS[getRegA()]);
	}
}
