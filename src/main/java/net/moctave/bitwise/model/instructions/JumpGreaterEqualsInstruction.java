package net.moctave.bitwise.model.instructions;

import org.jspecify.annotations.NonNull;

/** An instruction telling the computer to jump when last result ≥ 0. */
public class JumpGreaterEqualsInstruction extends JumpInstruction {
	// MARK: Constructor
	/**
	 * Creates a new JumpGreaterEqualsInstruction with opCode 4, fnCode 3, given label,
	 * and all other options set to 0, null, or false.
	 * @param label the label for this instruction to jump to
	 */
	public JumpGreaterEqualsInstruction(@NonNull String label) {
		super(3, label);
	}


	// MARK: Methods
	@Override
	public @NonNull String toString() {
		return String.format("jge %s", getLabel());
	}
}
