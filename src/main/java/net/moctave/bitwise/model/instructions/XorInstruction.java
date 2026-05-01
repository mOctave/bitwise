package net.moctave.bitwise.model.instructions;

import org.jspecify.annotations.NonNull;

import net.moctave.bitwise.utils.Constants;

/** An instruction telling the computer to perform bitwise xor on two values. */
public class XorInstruction extends BinaryInstruction {
	// MARK: Constructor
	/**
	 * Creates a new XorInstruction with opCode 2, fnCode 5, given regA and
	 * regB, and all other options set to 0, null, or false.
	 * @param regA the regA value for this instruction, bounded on [1, 15]
	 * @param regB the regB value for this instruction, bounded on [1, 15]
	 */
	public XorInstruction(int regA, int regB) {
		super(5, regA, regB);
	}


	// MARK: Methods
	@Override
	public @NonNull String toString() {
		return String.format(
			"xor r%s r%s",
			Constants.HEX_DIGITS[getRegA()],
			Constants.HEX_DIGITS[getRegB()]
		);
	}
}
