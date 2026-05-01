package net.moctave.bitwise.model.instructions;

import java.util.ArrayList;
import java.util.List;

import org.jspecify.annotations.NonNull;

/** An instruction telling the computer to peform a conditional jump. */
public class Label extends Instruction {
	// MARK: Constructor
	/**
	 * Creates a new label with opCode 5, the given label text,
	 * isLabel set to true, and all other options set to 0, null, or false.
	 * 
	 * @param labelText the text of this label
	 */
	public Label(@NonNull String labelText) {
		super(5, 0, 0, 0, 0, labelText, true);
	}


	@Override
	public @NonNull List<Byte> asBytes() {
		return new ArrayList<>();
	}


	@Override
	public @NonNull String toString() {
		return getLabel() + ":";
	}

	/**
	 * Returns 0, since labels are not actually converted directly to machine code.
	 * 
	 * @return The number of bytes to advance the program counter after
	 * processing this instruction
	 */
	@Override
	public int size() {
		return 0;
	}
}
