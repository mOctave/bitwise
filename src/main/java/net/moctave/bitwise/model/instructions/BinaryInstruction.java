package net.moctave.bitwise.model.instructions;

import java.util.Arrays;
import java.util.List;

import org.jspecify.annotations.NonNull;

/** An instruction telling the computer to peform an ALU operation on two values. */
public abstract class BinaryInstruction extends Instruction {
	// MARK: Constructor
	/**
	 * Creates a new JumpInstruction with opCode 2, the given fnCode, regA, and
	 * regB, and all other options set to 0, null, or false.
	 * @param fnCode the function code for the specific binary operation to
	 * perform, bounded on [0, 5]
	 * @param regA the regA value for this instruction, bounded on [1, 15]
	 * @param regB the regB value for this instruction, bounded on [1, 15]
	 */
	public BinaryInstruction(int fnCode, int regA, int regB) {
		super(2, fnCode, regA, regB, 0, null, false);
	}


	// MARK: Methods
	@Override
	public @NonNull List<Byte> asBytes() {
		return Arrays.asList(new Byte[]{
			(byte) ((getOpCode() << 4) + getFnCode()),
			(byte) ((getRegA() << 4) + getRegB())
		});
	}

	/**
	 * Returns 2, the number of bytes required to store this instruction.
	 * 
	 * @return The number of bytes to advance the program counter after
	 * processing this instruction
	 */
	@Override
	public int size() {
		return 2;
	}
}
