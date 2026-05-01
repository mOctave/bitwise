package net.moctave.bitwise.model.instructions;

/** An instruction telling the computer to jump when last result ≠ 0. */
public class JumpNotEqualsInstruction extends JumpInstruction {
	// MARK: Constructor
	/**
	 * Creates a new JumpNotEqualsInstruction with opCode 4, fnCode 4, given label,
	 * and all other options set to 0, null, or false.
	 * @param label the label for this instruction to jump to
	 */
	public JumpNotEqualsInstruction(String label) {
		super(4, label);
	}


	// MARK: Methods
	@Override
	public String toString() {
		return String.format("jne %s", getLabel());
	}
}
