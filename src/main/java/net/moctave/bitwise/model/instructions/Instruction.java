package net.moctave.bitwise.model.instructions;

import java.util.List;
import java.util.Map;

import net.moctave.bitwise.exceptions.LabelNotFoundException;

import org.jspecify.annotations.*;

/** A single instruction (in assembly or machine code) to be executed by the RISC. */
public abstract class Instruction {
	// MARK: Fields
	private int opCode;
	private int fnCode;
	private int regA;
	private int regB;
	private int valC;
	private @Nullable String label;
	private boolean isLabel;


	// MARK: Constructor
	/**
	 * A constructor which provides full control over all an instruction's fields
	 * @param opCode the operation code for the instruction, bounded on [0, 15]
	 * @param fnCode the function code for the instruction, bounded on [0, 15]
	 * @param regA the regA value for the instruction, bounded on [0, 15]
	 * @param regB the regB value for the instruction, bounded on [0, 15]
	 * @param valC the valC value for the instruction (unbounded)
	 * @param label a string representing a label associated with the instruction
	 * @param isLabel true if this instruction is a label, false otherwise
	 */
	public Instruction(int opCode, int fnCode, int regA, int regB, int valC, @Nullable String label, boolean isLabel) {
		this.opCode = opCode;
		this.fnCode = fnCode;
		this.regA = regA;
		this.regB = regB;
		this.valC = valC;
		this.label = label;
		this.isLabel = isLabel;
	}


	// MARK: Methods
	/**
	 * Indicates whether another object is equal to this instruction.
	 * 
	 * If the other object is not an instruction, it is not equal. Otherwise,
	 * the two instructions are considered equal if and only if all their fields
	 * are equal.
	 * 
	 * @param other The reference object with which to compare
	 * @return true if the two objects are equal, false otherwise
	 */
	@Override
	public boolean equals(@Nullable Object other) {
		if (other == this) {
			return true;
		}
		if (other == null) {
			return false;
		}
		if (Instruction.class.isAssignableFrom(other.getClass())) {
			return instructionEquals((Instruction) other);
		} else {
			return false;
		}
	}


	/**
	 * Determines whether this instruction is equal to another.
	 * Instructions are considered equal if and only if all their fields are
	 * the same.
	 * 
	 * @param other The instruction to compare with
	 * @return true if the instructions are equal, false otherwise
	 */
	private boolean instructionEquals(@NonNull Instruction other) {
		if (this.opCode != other.getOpCode()) {
			return false;
		}
		if (this.fnCode != other.getFnCode()) {
			return false;
		}
		if (this.regA != other.getRegA()) {
			return false;
		}
		if (this.regB != other.getRegB()) {
			return false;
		}
		if (this.valC != other.getValC()) {
			return false;
		}
		if (this.isLabel != other.isLabel()) {
			return false;
		}
		if (this.label == null) {
			return (other.getLabel() == null);
		} else {
			return (this.label.equals(other.getLabel()));
		}
	}


	/**
	 * Returns a hash code for this instruction based on the values of all fields
	 * 
	 * @return An integer hash code
	 */
	@Override
	public int hashCode() {
		final int prime = 23;
		int rsf = opCode;
		rsf = rsf * prime + fnCode;
		rsf = rsf * prime + regA;
		rsf = rsf * prime + regB;
		rsf = rsf * prime + valC;
		rsf = rsf * prime + (isLabel ? 1 : 0);
		if (label != null) {
			rsf = rsf * prime + label.hashCode();
		}
		return rsf;
	}


	/**
	 * Represents this instruction in machine code and returns the result as a list
	 * 
	 * @return A byte-list representation of this instruction
	 */
	public abstract @NonNull List<Byte> asBytes();


	/**
	 * Represents this instruction in assembly and returns the result as a string
	 * 
	 * @return A string representation of the assembly code for this instruction
	 */
	@Override
	public abstract @NonNull String toString();

	/**
	 * Determines the number of bytes this instruction takes in machine code.
	 * Practically, this is how far the program counter is advanced after executing
	 * this instruction, so this canonical size may differ from the size of
	 * {@link #asBytes()}.
	 * 
	 * @return The number of bytes to advance the program counter after
	 * processing this instruction
	 */
	public abstract int size();


	/**
	 * If there is an entry for this instruction's label in the given
	 * map, and this instruction is not a label itself, sets valC to the address
	 * found in the map. If no such entry exists, throws a LabelNotFoundException.
	 * If there isn't a label to set, this method does nothing.
	 * 
	 * @param labelAddresses A map providing the memory address each label points to
	 * @throws LabelNotFoundException If this instruction has a label that does not exist
	 */
	public void updateAddressToMatchLabel(@NonNull Map<String, Integer> labelAddresses) throws LabelNotFoundException {
		if (label == null || isLabel) {
			return;
		} else {
			Integer address = labelAddresses.get(label);
			if (address == null) {
				throw new LabelNotFoundException();
			} else {
				valC = address;
			}
		}
	}



	// MARK: Getters
	public int getOpCode() {
		return opCode;
	}

	public int getFnCode() {
		return fnCode;
	}

	public int getRegA() {
		return regA;
	}

	public int getRegB() {
		return regB;
	}

	public int getValC() {
		return valC;
	}

	public String getLabel() {
		return label;
	}

	public boolean isLabel() {
		return isLabel;
	}
}
