package net.moctave.bitwise.model.instructions;

/** An instruction telling the computer to jump when last result < 0. */
public class JumpLessInstruction extends JumpInstruction {
	// MARK: Constructor
	/**
	 * Creates a new JumpLessInstruction with opCode 4, fnCode 5, given label,
	 * and all other options set to 0, null, or false.
	 * @param label the label for this instruction to jump to
	 */
	public JumpLessInstruction(String label) {
		super(5, label);
	}


	// MARK: Methods
	@Override
	public String toString() {
		return String.format("jl %s", getLabel());
	}
}
