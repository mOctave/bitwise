package net.moctave.bitwise.model.instructions;

import org.jspecify.annotations.NonNull;

import net.moctave.bitwise.utils.Constants;

/** An instruction telling the computer to copy the value of one register to another. */
public class CopyInstruction extends BinaryInstruction {
	// MARK: Constructor
	/**
	 * Creates a new CopyInstruction with opCode 2, fnCode 0, given regA and
	 * regB, and all other options set to 0, null, or false.
	 * 
	 * @param regA the regA value for this instruction, bounded on [1, 15]
	 * @param regB the regB value for this instruction, bounded on [1, 15]
	 */
	public CopyInstruction(int regA, int regB) {
		super(0, regA, regB);
	}


	// MARK: Methods
	@Override
	public @NonNull String toString() {
		return String.format(
			"copy r%s r%s",
			Constants.HEX_DIGITS[getRegA()],
			Constants.HEX_DIGITS[getRegB()]
		);
	}
}
