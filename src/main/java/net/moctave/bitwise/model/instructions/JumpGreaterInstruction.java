package net.moctave.bitwise.model.instructions;

/** An instruction telling the computer to jump when last result > 0. */
public class JumpGreaterInstruction extends JumpInstruction {
	// MARK: Constructor
	/**
	 * Creates a new JumpGreaterInstruction with opCode 4, fnCode 6, given label,
	 * and all other options set to 0, null, or false.
	 * @param label the label for this instruction to jump to
	 */
	public JumpGreaterInstruction(String label) {
		super(6, label);
	}


	// MARK: Methods
	@Override
	public String toString() {
		return String.format("jg %s", getLabel());
	}
}
