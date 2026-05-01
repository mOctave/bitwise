package net.moctave.bitwise.model.instructions;

import org.jspecify.annotations.NonNull;

/** An instruction telling the computer to execute an unconditional jump. */
public class JumpAlwaysInstruction extends JumpInstruction {
	// MARK: Constructor
	/**
	 * Creates a new JumpAlwaysInstruction with opCode 4, fnCode 0, given label,
	 * and all other options set to 0, null, or false.
	 * 
	 * @param label the label for this instruction to jump to
	 */
	public JumpAlwaysInstruction(@NonNull String label) {
		super(0, label);
	}


	// MARK: Methods
	@Override
	public @NonNull String toString() {
		return String.format("jump %s", getLabel());
	}
}
