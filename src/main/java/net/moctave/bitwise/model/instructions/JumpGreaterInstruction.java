package net.moctave.bitwise.model.instructions;

import org.jspecify.annotations.NonNull;

/** An instruction telling the computer to jump when last result > 0. */
public class JumpGreaterInstruction extends JumpInstruction {
	// MARK: Constructor
	/**
	 * Creates a new JumpGreaterInstruction with opCode 4, fnCode 6, given label,
	 * and all other options set to 0, null, or false.
	 * 
	 * @param label the label for this instruction to jump to
	 */
	public JumpGreaterInstruction(@NonNull String label) {
		super(6, label);
	}


	// MARK: Methods
	@Override
	public @NonNull String toString() {
		return String.format("jg %s", getLabel());
	}
}
