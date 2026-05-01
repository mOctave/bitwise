package net.moctave.bitwise.model.instructions;

import org.jspecify.annotations.NonNull;

/** An instruction telling the computer to jump when last result = 0. */
public class JumpEqualsInstruction extends JumpInstruction {
	// MARK: Constructor
	/**
	 * Creates a new JumpEqualsInstruction with opCode 4, fnCode 1, given label,
	 * and all other options set to 0, null, or false.
	 * @param label the label for this instruction to jump to
	 */
	public JumpEqualsInstruction(@NonNull String label) {
		super(1, label);
	}


	// MARK: Methods
	@Override
	public @NonNull String toString() {
		return String.format("je %s", getLabel());
	}
}
