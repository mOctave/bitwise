package net.moctave.bitwise.model.instructions;

import java.util.Arrays;
import java.util.List;

/** An instruction telling the computer to halt executaion immediately. */
public class HaltInstruction extends Instruction {
	// MARK: Constructor
	/**
	 * Creates a new HaltInstruction with all other options set to 0, null, or false.
	 */
	public HaltInstruction() {
		super(0,0,0,0,0,null, false);
	}


	// MARK: Methods
	@Override
	public List<Byte> asBytes() {
		return Arrays.asList(new Byte[]{0x00});
	}

	@Override
	public String toString() {
		return "halt";
	}

	/**
	 * Returns 0, since the program counter should not be advanced after
	 * encountering a halt instruction.
	 * 
	 * @return The number of bytes to advance the program counter after
	 * processing this instruction
	 */
	public int size() {
		return 0;
	}
}
