package net.moctave.bitwise.model.instructions;

/** An instruction telling the computer to jump when last result ≥ 0. */
public class JumpGreaterEqualsInstruction extends JumpInstruction {
	// MARK: Constructor
	/**
	 * Creates a new JumpGreaterEqualsInstruction with opCode 4, fnCode 3, given label,
	 * and all other options set to 0, null, or false.
	 * @param label the label for this instruction to jump to
	 */
	public JumpGreaterEqualsInstruction(String label) {
		super(3, label);
	}


	// MARK: Methods
	@Override
	public String toString() {
		return String.format("jge %s", getLabel());
	}
}
