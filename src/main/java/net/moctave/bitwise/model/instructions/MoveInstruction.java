package net.moctave.bitwise.model.instructions;

import java.util.Arrays;
import java.util.List;

import org.jspecify.annotations.NonNull;

import net.moctave.bitwise.utils.Constants;

/** An instruction telling the computer to move a value into a register. */
public class MoveInstruction extends Instruction {
	// MARK: Constructor
	/**
	 * Creates a new JumpInstruction with opCode 1, the given regA and valC,
	 * and all other options set to 0, null, or false.
	 * @param regA the regA value for this instruction, bounded on [1, 15]
	 * @param valC the valC value for this instruction (unbounded)
	 */
	public MoveInstruction(int regA, int valC) {
		super(1,0,regA,0,valC,null, false);
	}


	// MARK: Methods
	@Override
	public @NonNull List<Byte> asBytes() {
		int valC = getValC();

		return Arrays.asList(new Byte[]{
			(byte) ((getOpCode() << 4) + getRegA()),
			(byte) (valC >>> 24),
			(byte) (valC >>> 16),
			(byte) (valC >>> 8),
			(byte) valC
		});
	}

	@Override
	public @NonNull String toString() {
		return String.format("move r%s %d", Constants.HEX_DIGITS[getRegA()], getValC());
	}

	/**
	 * Returns 5, the number of bytes required to store this instruction.
	 * 
	 * @return The number of bytes to advance the program counter after
	 * processing this instruction
	 */
	@Override
	public int size() {
		return 5;
	}
}
