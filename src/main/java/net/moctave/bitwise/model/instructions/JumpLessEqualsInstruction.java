package net.moctave.bitwise.model.instructions;

import org.jspecify.annotations.NonNull;

/** An instruction telling the computer to jump when last result ≤ 0. */
public class JumpLessEqualsInstruction extends JumpInstruction {
	// MARK: Constructor
	/**
	 * Creates a new JumpLessEqualsInstruction with opCode 4, fnCode 2, given label,
	 * and all other options set to 0, null, or false.
	 * @param label the label for this instruction to jump to
	 */
	public JumpLessEqualsInstruction(@NonNull String label) {
		super(2, label);
	}


	// MARK: Methods
	@Override
	public @NonNull String toString() {
		return String.format("jle %s", getLabel());
	}
}
