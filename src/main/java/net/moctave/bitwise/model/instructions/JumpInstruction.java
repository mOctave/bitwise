package net.moctave.bitwise.model.instructions;

import java.util.Arrays;
import java.util.List;

import org.jspecify.annotations.NonNull;

/** An instruction telling the computer to peform a conditional jump. */
public abstract class JumpInstruction extends Instruction {
	// MARK: Constructor
	/**
	 * Creates a new JumpInstruction with opCode 4, the given fnCode and label,
	 * and all other options set to 0, null, or false.
	 * 
	 * @param fnCode the function code for the specific conditions in which
	 * to jump, bounded on [0, 6]
	 * @param label the label for this instruction to jump to
	 */
	public JumpInstruction(int fnCode, @NonNull String label) {
		super(4, fnCode, 0, 0, 0, label, false);
	}


	// MARK: Methods
	@Override
	public @NonNull List<Byte> asBytes() {
		final int valC = getValC();

		return Arrays.asList(new Byte[]{
			(byte) ((getOpCode() << 4) + getFnCode()),
			(byte) (valC >>> 24),
			(byte) (valC >>> 16),
			(byte) (valC >>> 8),
			(byte) valC
		});
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
