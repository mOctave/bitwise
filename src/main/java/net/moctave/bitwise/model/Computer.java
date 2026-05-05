// Bitwise - A RISC simulator
// Copyright (C) 2026 mOctave
//
// This program is free software: you can redistribute it and/or modify
// it under the terms of the GNU Affero General Public License as published
// by the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU Affero General Public License for more details.
//
// You should have received a copy of the GNU Affero General Public License
// along with this program.  If not, see <https://www.gnu.org/licenses/>.

package net.moctave.bitwise.model;

import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.moctave.bitwise.exceptions.LabelNotFoundException;
import net.moctave.bitwise.model.instructions.Instruction;
import net.moctave.bitwise.utils.Constants;

/** A class storing registers, flags, and instructions, acting as a full computer. */
public class Computer {
	// MARK: Fields
	/** The primary instance of this computer that the app refers to. */
	private static Computer instance = new Computer();

	/** The opcode of the instruction currently being processed. */
	private @NonNull Register opCode;
	/** The function code of the instruction currently being processed. */
	private @NonNull Register fnCode;
	/** The first register of the instruction currently being processed. */
	private @NonNull Register regA;
	/** The second register of the instruction currently being processed. */
	private @NonNull Register regB;
	/** The register that will be written to at the end of the current cycle. */
	private @NonNull Register regWrite;
	/** The value stored in the register indicated by {@link #regA}. */
	private @NonNull Register valA;
	/** The value stored in the register indicated by {@link #regB}. */
	private @NonNull Register valB;
	/** The extra value from the instruction currently being processed. */
	private @NonNull Register valC;
	/** The value that will be written to a register at the end of the current cycle. */
	private @NonNull Register valWrite;
	/** The current value of the program counter. */
	private @NonNull Register programCounter;
	/** The next value of the program counter. */
	private @NonNull Register nextProgramCounter;
	/** The register storing the Z (zero) flag for this computer. */
	private @NonNull Register flagZ;
	/** The register storing the N (negative) flag for this computer. */
	private @NonNull Register flagN;
	/** The register storing the O (overflow) flag for this computer. */
	private @NonNull Register flagO;
	/** A list of all this computer's instructions. */
	private @NonNull List<Instruction> instructions;
	/** The next step to take in the machine cycle. */
	private int nextStep;
	/** This computer's array of 15 data registers. */
	private @NonNull Register[] registers;

	// MARK: Constructor
	/**
	 * Creates a new computer with fifteen accessible registers, the
	 * fourteen special registers and flags described in architecture.md, and
	 * no instructions.
	 */
	public Computer() {
		opCode = new Register("opCode", 4);
		fnCode = new Register("fnCode", 4);
		regA = new Register("regA", 4);
		regB = new Register("regB", 4);
		regWrite = new Register("regWrite", 4);
		valA = new Register("valA", 32);
		valB = new Register("valB", 32);
		valC = new Register("valC", 32);
		valWrite = new Register("valWrite", 32);
		programCounter = new Register("PC", 32);
		nextProgramCounter = new Register("nextPC", 32);
		flagZ = new Register("Z", 1);
		flagN = new Register("N", 1);
		flagO = new Register("O", 1);
		instructions = new ArrayList<>();
		nextStep = -1;
		registers = new Register[15];
		for (int i = 0; i < 15; i++) {
			registers[i] = new Register(String.valueOf(Constants.HEX_DIGITS[i + 1]), 32);
		}
	}


	/**
	 * Creates a new computer with the given values stored in each field or register.
	 * 
	 * @param ocVal The value to store in opCode, bounded on [0, 15]
	 * @param fcVal The value to store in fnCode, bounded on [0, 15]
	 * @param raVal The value to store in regA, bounded on [0, 15]
	 * @param rbVal The value to store in regB, bounded on [0, 15]
	 * @param rwVal The value to store in regWrite, bounded on [0, 15]
	 * @param vaVal The value to store in valA, unbounded
	 * @param vbVal The value to store in valB, unbounded
	 * @param vcVal The value to store in valC, unbounded
	 * @param vwVal The value to store in valWrite, unbounded
	 * @param pcVal The value to store in programCounter, bounded on [0, Integer.MAX_VALUE]
	 * @param npcVal The value to store in programCounter, bounded on [0, Integer.MAX_VALUE]
	 * @param fzVal The value to store in flagZ, bounded on [0, 1]
	 * @param fnVal The value to store in flagN, bounded on [0, 1]
	 * @param foVal The value to store in flagO, bounded on [0, 1]
	 * @param instructions A non-null list of instructions to add to this computer
	 * @param nextStep The next step in this computer's execution, bounded on [0, 3]
	 * @param regVals A list of fifteen integer values to store in the standard registers
	 */
	public Computer(int ocVal, int fcVal, int raVal, int rbVal, int rwVal, int vaVal, int vbVal, int vcVal, int vwVal,
				int pcVal, int npcVal, int fzVal, int fnVal, int foVal, @NonNull List<Instruction> instructions,
				int nextStep, @NonNull List<Integer> regVals) {
		this();
		opCode.setValue(ocVal);
		fnCode.setValue(fcVal);
		regA.setValue(raVal);
		regB.setValue(rbVal);
		regWrite.setValue(rwVal);
		valA.setValue(vaVal);
		valB.setValue(vbVal);
		valC.setValue(vcVal);
		valWrite.setValue(vwVal);
		programCounter.setValue(pcVal);
		nextProgramCounter.setValue(npcVal);
		flagZ.setValue(fzVal);
		flagN.setValue(fnVal);
		flagO.setValue(foVal);
		this.instructions = instructions;
		this.nextStep = nextStep;
		for (int i = 0; i < 15; i++) {
			registers[i].setValue(regVals.get(i));
		}
	}



	// MARK: Methods
	/**
	 * Converts all the instructions in this computer to machine code.
	 * 
	 * @return This computer's instructions, as a list of bytes
	 * @throws LabelNotFoundException If a missing label is found in conversion
	 */
	public @NonNull List<Byte> asByteList() throws LabelNotFoundException {
		updateAddressesToMatchLabels();
		final List<Byte> bytes = new ArrayList<>();
		for (Instruction instruction : instructions) {
			for (Byte instructionByte : instruction.asBytes()) {
				bytes.add(instructionByte);
			}
		}
		return bytes;
	}


	/**
	 * Blanks all registers, clears all flags, and resets the program counter
	 * to 0.
	 */
	public void reset() {
		opCode.setValue(0);
		fnCode.setValue(0);
		regA.setValue(0);
		regB.setValue(0);
		regWrite.setValue(0);
		valA.setValue(0);
		valB.setValue(0);
		valC.setValue(0);
		valWrite.setValue(0);
		programCounter.setValue(0);
		nextProgramCounter.setValue(0);
		flagZ.setValue(0);
		flagN.setValue(0);
		flagO.setValue(0);
		nextStep = -1;
		for (int i = 0; i < 15; i++) {
			registers[i].setValue(0);
		}
	}


	/**
	 * Repeatedly steps until a halt instruction is reached.
	 * 
	 * @throws LabelNotFoundException if there's an error resolving a label when
	 * interpreting the instructions
	 */
	public void runAll() throws LabelNotFoundException {
		reset();
		while (step()) {
			continue;
		}
	}


	/**
	 * Executes the next step of the instruction indicated by the program
	 * counter.
	 * 
	 * @return true if execution should continue, or false if the current
	 * instruction specifies halting
	 * @throws LabelNotFoundException if there's an error resolving a label when
	 * interpreting the instructions
	 */
	public boolean step() throws LabelNotFoundException {
		nextStep = (nextStep + 1) % 4;

		switch (nextStep) {
			case 0:
				return fetchAndDecodeWithReturn();
			case 1:
				runALU();
				break;
			case 2:
				branch();
				break;
			default:
				writeToMemory();
		}

		return true;
	}


	/**
	 * Conducts fetch/decode operations.
	 * 
	 * @return false if the end of the program is reached, otherwise true
	 * @throws LabelNotFoundException if there's an error resolving labels
	 */
	private boolean fetchAndDecodeWithReturn() throws LabelNotFoundException {
		final int instructionLength = asByteList().size();
		updateAddressesToMatchLabels();
		if (programCounter.getValue() >= instructionLength) {
			return false;
		}
		fetchAndDecode();
		if (opCode.getValue() == 0) {
			return false;
		}
		return true;
	}


	/**
	 * Updates all instructions to reflect the current label addresses.
	 * 
	 * @throws LabelNotFoundException if there's an error resolving labels
	 */
	private void updateAddressesToMatchLabels() throws LabelNotFoundException {
		int address = 0;
		final Map<String, Integer> labels = new HashMap<>();

		for (Instruction instruction : instructions) {
			if (instruction.isLabel()) {
				labels.put(instruction.getLabel(), address);
			}
			address += instruction.size();
		}

		for (Instruction instruction : instructions) {
			instruction.updateAddressToMatchLabel(labels);
		}
	}


	/**
	 * Conducts fetch/decode operations for the instruction indicated by the
	 * program counter.
	 * 
	 * @throws LabelNotFoundException if there's an error resolving labels
	 */
	private void fetchAndDecode() throws LabelNotFoundException {
		final List<Byte> byteList = asByteList();
		final int pcValue = programCounter.getValue();
		opCode.setValue(byteList.get(pcValue) >>> 4);
		switch (opCode.getValue()) {
			case 0:
				fetchAndDecodeCaseHalt();
				break;
			case 1:
				fetchAndDecodeCaseMove(byteList, pcValue);
				break;
			case 2:
				fetchAndDecodeCaseBinary(byteList, pcValue);
				break;
			case 3:
				fetchAndDecodeCaseUnary(byteList, pcValue);
				break;
			case 4:
				fetchAndDecodeCaseJump(byteList, pcValue);
				break;
			default:
				throw new UnsupportedOperationException("Impossible case");
		}
		fetchAndDecodeValueAssignment();
	}

	/**
	 * Conducts fetch/decode operations for a halt instruction.
	 * 
	 * @throws LabelNotFoundException if there's an error resolving labels
	 */
	private void fetchAndDecodeCaseHalt() {
		fnCode.setValue(0);
		regA.setValue(0);
		regB.setValue(0);
		valC.setValue(0);
	}

	/**
	 * Conducts fetch/decode operations for a move instruction.
	 * 
	 * @param byteList The list of instructions in machine code format
	 * @param pcValue The current program counter value, bounded on
	 * [0, {@code byteList.size()})
	 */
	private void fetchAndDecodeCaseMove(List<Byte> byteList, int pcValue) {
		fnCode.setValue(0);
		regA.setValue(byteList.get(pcValue) & 0xF);
		regB.setValue(0);
		regWrite.setValue(regA.getValue());
		valC.setValue(unpackInteger(byteList.get(pcValue + 1), byteList.get(pcValue + 2),
				byteList.get(pcValue + 3), byteList.get(pcValue + 4)));
		nextProgramCounter.setValue(pcValue + 5);
	}

	/**
	 * Conducts fetch/decode operations for a binary ALU instruction.
	 * 
	 * @param byteList The list of instructions in machine code format
	 * @param pcValue The current program counter value, bounded on
	 * [0, {@code byteList.size()})
	 */
	private void fetchAndDecodeCaseBinary(List<Byte> byteList, int pcValue) {
		fnCode.setValue(byteList.get(pcValue) & 0xF);
		regA.setValue(byteList.get(pcValue + 1) >>> 4);
		regB.setValue(byteList.get(pcValue + 1) & 0xF);
		regWrite.setValue(regA.getValue());
		valC.setValue(0);
		nextProgramCounter.setValue(pcValue + 2);
	}

	/**
	 * Conducts fetch/decode operations for a unary ALU instruction.
	 * 
	 * @param byteList The list of instructions in machine code format
	 * @param pcValue The current program counter value, bounded on
	 * [0, {@code byteList.size()})
	 */
	private void fetchAndDecodeCaseUnary(List<Byte> byteList, int pcValue) {
		fnCode.setValue(byteList.get(pcValue) & 0xF);
		regA.setValue(byteList.get(pcValue + 1) >>> 4);
		regB.setValue(0);
		valC.setValue(0);
		regWrite.setValue(regA.getValue());
		nextProgramCounter.setValue(pcValue + 2);
	}

	/**
	 * Conducts fetch/decode operations for a jump instruction.
	 * 
	 * @param byteList The list of instructions in machine code format
	 * @param pcValue The current program counter value, bounded on
	 * [0, {@code byteList.size()})
	 */
	private void fetchAndDecodeCaseJump(List<Byte> byteList, int pcValue) {
		fnCode.setValue(byteList.get(pcValue) & 0xF);
		regA.setValue(0);
		regB.setValue(0);
		regWrite.setValue(0);
		valC.setValue(unpackInteger(byteList.get(pcValue + 1), byteList.get(pcValue + 2),
				byteList.get(pcValue + 3), byteList.get(pcValue + 4)));
		nextProgramCounter.setValue(pcValue + 5);
	}


	/**
	 * Assigns {@link #valA} and {@link #valB} based on the values of
	 * {@link #regA} and {@link #regB}.
	 */
	private void fetchAndDecodeValueAssignment() {
		if (regA.getValue() != 0) {
			valA.setValue(registers[regA.getValue() - 1].getValue());
		} else {
			valA.setValue(0);
		}

		if (regB.getValue() != 0) {
			valB.setValue(registers[regB.getValue() - 1].getValue());
		} else {
			valB.setValue(0);
		}
	}


	/**
	 * Conducts ALU operations for the instruction indicated by the program counter.
	 */
	private void runALU() {
		int[] result = new int[]{0, flagZ.getValue(), flagN.getValue(), flagO.getValue()};
		switch (opCode.getValue()) {
			case 1:
				result = moveMathResult(valC.getValue());
				break;
			case 2:
				result = binaryMathResult(valA.getValue(), valB.getValue(), fnCode.getValue());
				break;
			case 3:
				result = unaryMathResult(valA.getValue(), fnCode.getValue());
				break;
			default:
				break;
		}
		valWrite.setValue(result[0]);
		flagZ.setValue(result[1]);
		flagN.setValue(result[2]);
		flagO.setValue(result[3]);
	}


	/**
	 * Conducts branching operations for the instruction indicated by the
	 * program counter.
	 */
	private void branch() {
		if (opCode.getValue() == 4
				&& shouldBranch(fnCode.getValue(), flagZ.getValue(),
				flagN.getValue(), flagO.getValue())
		) {
			nextProgramCounter.setValue(valC.getValue());
		}
	}


	/**
	 * Writes the appropriate values to memory for the instruction indicated
	 * by the program counter.
	 */
	private void writeToMemory() {
		if (regWrite.getValue() != 0) {
			registers[regWrite.getValue() - 1].setValue(valWrite.getValue());
		}
		programCounter.setValue(nextProgramCounter.getValue());
	}



	/**
	 * Returns the result of conducting a move operation on the given value.
	 * 
	 * @param valC the value being moved
	 * @return An array of four integers containing
	 * (0) the result of the operation,
	 * (1) the value to be stored in the Z flag,
	 * (2) the value to be stored in the N flag, and
	 * (3) the value to be stored in the O flag
	 */
	public static int[] moveMathResult(int valC) {
		final int valZ = (valC == 0) ? 1 : 0;
		final int valN = (valC < 0) ? 1 : 0;
		return new int[]{valC, valZ, valN, 0};
	}

	/**
	 * Returns the result of applying the binary math operation with the given
	 * code to the given values.
	 * 
	 * @param valA the first operand
	 * @param valB the second operand
	 * @param function the function being executed, bounded on [0, 5]
	 * (see architecture.md for details)
	 * @return An array of four integers containing
	 * (0) the result of the operation,
	 * (1) the value to be stored in the Z flag,
	 * (2) the value to be stored in the N flag, and
	 * (3) the value to be stored in the O flag
	 */
	public static int[] binaryMathResult(int valA, int valB, int function) {
		int result = 0;
		int valO = 0;
		switch (function) {
			case 0:
				result = valB;
				break;
			case 1:
				result = valA + valB;
				valO = additionOverflowValue(valA, valB, result);
				break;
			case 2:
				result = valA - valB;
				valO = additionOverflowValue(valA, -valB, result);
				break;
			case 3:
				result = valA & valB;
				break;
			case 4:
				result = valA | valB;
				break;
			case 5:
				result = valA ^ valB;
				break;
			default:
				throw new UnsupportedOperationException("Impossible case");
		}
		return new int[]{result, (result == 0) ? 1 : 0, (result < 0) ? 1 : 0, valO};
	}

	/**
	 * Determines whether valA + valB = sum involved overflow or not.
	 * 
	 * @param valA the first addend
	 * @param valB the second addend
	 * @param sum the sum obtained
	 * @return 1 if overflow occurred, 0 otherwise
	 */
	protected static int additionOverflowValue(int valA, int valB, int sum) {
		return ((valA > 0 && valB > 0 && sum < 0) || (valA < 0 && valB < 0 && sum > 0)) ? 1 : 0;
	}

	/**
	 * Returns the result of applying the unary math operation with the given
	 * code to the given value.
	 * 
	 * @param valA the operand
	 * @param function the function being executed, bounded on [0, 2]
	 * (see architecture.md for details)
	 * @return An array of four integers containing
	 * (0) the result of the operation,
	 * (1) the value to be stored in the Z flag,
	 * (2) the value to be stored in the N flag, and
	 * (3) the value to be stored in the O flag
	 */
	public static int[] unaryMathResult(int valA, int function) {
		int result = 0;
		int valO = 0;

		switch (function) {
			case 0:
				result = valA + 1;
				valO = (valA > 0 && result < 0) ? 1 : 0;
				break;
			case 1:
				result = -valA;
				valO = (valA == Integer.MIN_VALUE) ? 1 : 0;
				break;
			case 2:
				result = ~valA;
				break;
			default:
				throw new UnsupportedOperationException("Impossible case");
		}

		final int valZ = (result == 0) ? 1 : 0;
		final int valN = (result < 0) ? 1 : 0;
		return new int[]{result, valZ, valN, valO};
	}


	/**
	 * Returns true if the computer should branch based on the
	 * given function code and flags (assuming operation code is 4).
	 * 
	 * @param function the function being executed, bounded on [0, 6]
	 * @param intZ the value of the Z flag
	 * @param intN the value of the N flag
	 * @param intO the value of the O flag
	 * @return True if the computer should branch, false otherwise
	 */
	public static boolean shouldBranch(int function, int intZ, int intN, int intO) {
		final boolean valZ = (intZ > 0);
		final boolean valN = (intN > 0);
		final boolean valO = (intO > 0);
		return (function == 0) // jump
				|| (function == 1 && (valZ)) // je
				|| (function == 2 && (valZ || (valN ^ valO))) // jle
				|| (function == 3 && (valZ || !(valN ^ valO))) // jge
				|| (function == 4 && (!valZ)) // jne
				|| (function == 5 && (!valZ && (valN ^ valO))) // jl
				|| (function == 6 && (!valZ && !(valN ^ valO))); // jg
	}


	/**
	 * Converts four bytes to a single 32-bit value. Little-endian.
	 * 
	 * @param byte1 The first (most significant) byte
	 * @param byte2 The second byte
	 * @param byte3 The third byte
	 * @param byte4 The fourth (least significant) byte
	 * @return The integer obtained from the four bytes.
	 */
	private int unpackInteger(byte byte1, byte byte2, byte byte3, byte byte4) {
		final int int1 = byte1 & 0xFF;
		final int int2 = byte2 & 0xFF;
		final int int3 = byte3 & 0xFF;
		final int int4 = byte4 & 0xFF;
		return (int1 << 24) + (int2 << 16) + (int3 << 8) + int4;
	}


	// MARK: List Helpers
	/**
	 * Adds an instruction to the end of the list.
	 * 
	 * @param instruction the instruction to add
	 */
	public void addInstruction(Instruction instruction) {
		instructions.add(instruction);
	}

	/**
	 * Adds an instruction at the given index.
	 * 
	 * @param i the index to add the instruction at
	 * @param instruction the instruction to add
	 */
	public void addInstruction(int i, Instruction instruction) {
		instructions.add(i, instruction);
	}

	/**
	 * Replaces the instruction at the given index.
	 * 
	 * @param i the index of the instruction to replace
	 * @param instruction the new instruction to add
	 */
	public void setInstruction(int i, Instruction instruction) {
		instructions.set(i, instruction);
	}

	/**
	 * Removes the instruction at the given index.
	 * 
	 * @param i the index of the instruction to remove
	 */
	public void removeInstruction(int i) {
		instructions.remove(i);
	}



	// MARK: Getters / Setters
	/**
	 * Getter for this computer's main instance.
	 * 
	 * @return {@link #instance}
	 */
	public static @NonNull Computer getInstance() {
		return instance;
	}

	/**
	 * Setter for this computer's main instance.
	 * 
	 * @param c value for {@link #instance}
	 */
	public static void setInstance(Computer c) {
		instance = c;
	}

	/**
	 * Getter for this computer's opcode register.
	 * 
	 * @return {@link #opCode}
	 */
	public @NonNull Register getOpCode() {
		return opCode;
	}

	/**
	 * Getter for this computer's function code register.
	 * 
	 * @return {@link #fnCode}
	 */
	public @NonNull Register getFnCode() {
		return fnCode;
	}

	/**
	 * Getter for this computer's regA register.
	 * 
	 * @return {@link #regA}
	 */
	public @NonNull Register getRegA() {
		return regA;
	}

	/**
	 * Getter for this computer's regB register.
	 * 
	 * @return {@link #regB}
	 */
	public @NonNull Register getRegB() {
		return regB;
	}

	/**
	 * Getter for this computer's regWrite register.
	 * 
	 * @return {@link #regWrite}
	 */
	public @NonNull Register getRegWrite() {
		return regWrite;
	}

	/**
	 * Getter for this computer's valA register.
	 * 
	 * @return {@link #valA}
	 */
	public @NonNull Register getValA() {
		return valA;
	}

	/**
	 * Getter for this computer's valB register.
	 * 
	 * @return {@link #valB}
	 */
	public @NonNull Register getValB() {
		return valB;
	}

	/**
	 * Getter for this computer's valC register.
	 * 
	 * @return {@link #valC}
	 */
	public @NonNull Register getValC() {
		return valC;
	}

	/**
	 * Getter for this computer's valWrite register.
	 * 
	 * @return {@link #valWrite}
	 */
	public @NonNull Register getValWrite() {
		return valWrite;
	}

	/**
	 * Getter for this computer's program counter.
	 * 
	 * @return {@link #programCounter}
	 */
	public @NonNull Register getProgramCounter() {
		return programCounter;
	}

	/**
	 * Getter for the register that store this computer's next
	 * program counter value.
	 * 
	 * @return {@link #nextProgramCounter}
	 */
	public @NonNull Register getNextProgramCounter() {
		return nextProgramCounter;
	}

	/**
	 * Getter for this computer's Z flag.
	 * 
	 * @return {@link #flagZ}
	 */
	public @NonNull Register getFlagZ() {
		return flagZ;
	}

	/**
	 * Getter for this computer's N flag.
	 * 
	 * @return {@link #flagN}
	 */
	public @NonNull Register getFlagN() {
		return flagN;
	}

	/**
	 * Getter for this computer's O flag.
	 * 
	 * @return {@link #flagO}
	 */
	public @NonNull Register getFlagO() {
		return flagO;
	}

	/**
	 * Getter for this computer's instruction list.
	 * 
	 * @return {@link #instructions}
	 */
	public @NonNull List<Instruction> getInstructions() {
		return instructions;
	}

	/**
	 * Getter for this computer's next step.
	 * 
	 * @return {@link #nextStep}
	 */
	public int getNextStep() {
		return nextStep;
	}

	/**
	 * Getter for this computer's data registers.
	 * 
	 * @return {@link #registers}
	 */
	public @NonNull Register[] getRegisters() {
		return registers;
	}
}
