package net.moctave.bitwise.model.instructions;

import java.util.Arrays;
import java.util.List;

import org.jspecify.annotations.NonNull;

/** An instruction telling the computer to peform an ALU operation on one value. */
public abstract class UnaryInstruction extends Instruction {
	// MARK: Constructor
	/**
	 * Creates a new JumpInstruction with opCode 3, the given fnCode and regA,
	 * and all other options set to 0, null, or false.
	 * 
	 * @param fnCode the function code for the specific unary operation to
	 * perform, bounded on [0, 2]
	 * @param regA the regA value for this instruction, bounded on [1, 15]
	 */
	public UnaryInstruction(int fnCode, int regA) {
		super(3, fnCode, regA, 0, 0, null, false);
	}


	// MARK: Methods
	@Override
	public @NonNull List<Byte> asBytes() {
		return Arrays.asList(new Byte[]{
			(byte) ((getOpCode() << 4) + getFnCode()),
			(byte) (getRegA() << 4)
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
