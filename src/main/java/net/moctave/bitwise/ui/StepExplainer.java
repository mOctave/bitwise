package net.moctave.bitwise.ui;

import net.moctave.bitwise.model.Computer;
import net.moctave.bitwise.model.Register;

// A class of helper methods for explaining the last step taken by the computer.
public abstract class StepExplainer {
	// MARK: Explain Last Step
	/**
	 * Explains the last step taken by the computer.
	 * @return an explanation of the last step
	 */
	public static String explainLastStep() {
		Computer computer = Computer.getInstance();
		switch (computer.getNextStep()) {
			case 0:
				return explainFetchDecodeStep(computer);
			case 1:
				return explainArithmeticStep(computer);
			case 2:
				return explainBranchStep(computer);
			case 3:
				return explainMemoryWriteStep(computer);
			default:
				return explainNoStep(computer);
		}
	}



	// MARK: Explain No Step
	/**
	 * Explains that no step has yet been taken by a computer.
	 * @param computer the computer being explained
	 * @return an explanation of the last step
	 */
	private static String explainNoStep(Computer computer) {
		String rsf = String.format("The computer has not yet taken any steps, so there is nothing to explain!%n");
		rsf += String.format("Type \\\"step\\\" or \\\"run\\\" to execute the computer's instructions.");
		return rsf;
	}



	// MARK: Explain Fetch/Decode
	/**
	 * Explains the fetch/decode step just taken by a computer.
	 * @param computer the computer being explained
	 * @return an explanation of the last step
	 */
	private static String explainFetchDecodeStep(Computer computer) {
		String rsf = String.format("FETCH/DECODE%n");
		String vasPC = vas(computer.getProgramCounter());
		String vasOC = vas(computer.getOpCode());
		rsf += String.format("The computer began by reading the byte at %s.%n", vasPC);
		rsf += String.format("The first four bits, %s, were stored in opCode.%n", vasOC);
		rsf += String.format("Since the value in opCode was %s, ", vasOC);
		rsf += describeBytesRead(computer);
		rsf += describeRegistersHardcoded(computer);
		rsf += describeValuesAssigned(computer);
		return rsf;
	}

	/**
	 * Describes how the computer handled the bytes after the opcode.
	 * @param computer the computer being explained
	 * @return an explanation of the last step
	 */
	private static String describeBytesRead(Computer computer) {
		String rsf = "";
		String fnStr = vas(computer.getFnCode());
		switch (computer.getOpCode().getValue()) {
			case 0:
				return String.format("the computer halted execution immediately.%n");
			case 1:
				rsf += String.format("the computer stored the next four bits (%s) in regA,", vas(computer.getRegA()));
				rsf += String.format("then stored the next four bytes (%s) in valC.%n", vas(computer.getValC()));
				return rsf;
			case 2:
				rsf += String.format("the computer stored the next four bits (%s) in fnCode,", fnStr);
				rsf += String.format(" followed by four bits (%s) in regA", vas(computer.getRegA()));
				rsf += String.format(" and four bits (%s) in regB%n", vas(computer.getRegB()));
				return rsf;
			case 3:
				rsf += String.format("the computer stored the next four bits (%s) in fnCode,", fnStr);
				rsf += String.format(" followed by four bits (%s) in regA.%n", vas(computer.getRegA()));
				return rsf;
			default:
				rsf += String.format("the computer stored the next four bits (%s) in fnCode,", fnStr);
				rsf += String.format(" then stored the next four bytes (%s) in valC.%n", vas(computer.getValC()));
				return rsf;
		}
	}

	/**
	 * Describes the registers that the computer set to a standard value.
	 * @param computer the computer being explained
	 * @return an explanation of the last step
	 */
	private static String describeRegistersHardcoded(Computer computer) {
		String rsf = "";
		switch (computer.getOpCode().getValue()) {
			case 0:
				return String.format("regA, regB, and valC were set to 0, and nextPC was set to PC.%n");
			case 1:
				rsf += String.format("fnCode and regB were set to 0, and nextPC was set to increase PC by 5.%n");
				rsf += String.format("regWrite was set to the value of regA, %s.%n", vas(computer.getRegA()));
				return rsf;
			case 2:
				rsf += String.format("valC was set to 0, and nextPC was set to increase PC by 2.%n");
				rsf += String.format("regWrite was set to the value of regA, %s.%n", vas(computer.getRegA()));
				return rsf;
			case 3:
				rsf += String.format("regB and valC were set to 0, and nextPC was set to increase PC by 2.%n");
				rsf += String.format("regWrite was set to the value of regA, %s.%n", vas(computer.getRegA()));
				return rsf;
			default:
				return String.format("regA, regB, and regWrite were set to 0, and nextPC was set to increase PC by 5.");
		}
	}

	/**
	 * Describes the values the computer loaded into valA and valB.
	 * @param computer the computer being explained
	 * @return an explanation of the last step
	 */
	private static String describeValuesAssigned(Computer computer) {
		String rsf = "Finally, the computer ";

		if (computer.getRegA().getValue() == 0) {
			rsf += "set valA to 0";
		} else {
			rsf += String.format("loaded the value from r%s (selected by regA) into valA", vas(computer.getRegA()));
		}

		rsf += " and ";

		if (computer.getRegB().getValue() == 0) {
			rsf += "set valB to 0";
		} else {
			rsf += String.format("loaded the value from r%s (selected by regB) into valB", vas(computer.getRegB()));
		}

		rsf += String.format(".%n");
		return rsf;
	}



	// MARK: Explain ALU Step
	/**
	 * Explains the ALU step just taken by a computer.
	 * @param computer the computer being explained
	 * @return an explanation of the last step
	 */
	private static String explainArithmeticStep(Computer computer) {
		String rsf = String.format("EXECUTE%n");
		int opCode = computer.getOpCode().getValue();
		if (opCode < 1 || opCode > 3) {
			rsf += "Since the value of opCode was not 1, 2, or 3, the computer had";
			rsf += String.format(" no ALU operations to perform. Flag values were preserved.%n");
		} else {
			rsf += describeValWrite(computer);
			rsf += describeFlagsSet(computer);
		}
		return rsf;
	}

	/**
	 * Explains the steps taken to arrive at the value of valWrite.
	 * @param computer the computer being explained
	 * @return an explanation of the last step
	 */
	private static String describeValWrite(Computer computer) {
		switch (computer.getOpCode().getValue()) {
			case 1:
				return String.format("Since the value of opCode was 1, the computer copied valC to valWrite.%n");
			case 2:
				return "Since the value of opCode was 2, the computer performed a binary operation, "
						+ describeBinaryValWrite(computer);
			default:
				return "Since the value of opCode was 2, the computer performed a unary operation, "
						+ describeUnaryValWrite(computer);
		}
	}

	/**
	 * Explains the binary operation used to arrive at the value of valWrite.
	 * @param computer the computer being explained
	 * @return an explanation of the last step
	 */
	private static String describeBinaryValWrite(Computer computer) {
		switch (computer.getFnCode().getValue()) {
			case 0:
				return String.format("copying valB to valWrite.%n");
			case 1:
				return String.format("adding valA and valB and storing the result in valWrite.%n");
			case 2:
				return String.format("subtracting valB from valA and storing the result in valWrite.%n");
			case 3:
				return String.format("applying logical AND to valB and valA and storing the result in valWrite.%n");
			case 4:
				return String.format("applying logical OR to valB and valA and storing the result in valWrite.%n");
			default:
				return String.format("applying logical XOR to valB and valA and storing the result in valWrite.%n");
		}
	}

	/**
	 * Explains the unary operation used to arrive at the value of valWrite.
	 * @param computer the computer being explained
	 * @return an explanation of the last step
	 */
	private static String describeUnaryValWrite(Computer computer) {
		switch (computer.getFnCode().getValue()) {
			case 0:
				return String.format("adding one to valA and storing the result in valWrite.%n");
			case 1:
				return String.format("negating valA and storing the result in valWrite.%n");
			default:
				return String.format("applying logical NOT to valA and storing the result in valWrite.%n");
		}
	}

	/**
	 * Explains how the computer's flags were set.
	 * @param computer the computer being explained
	 * @return an explanation of the last step
	 */
	private static String describeFlagsSet(Computer computer) {
		String rsf = "";
		if (computer.getFlagZ().getValue() == 0) {
			rsf += "Since the result of the operation was nonzero, flag Z was set to 0.";
		} else {
			rsf += "Since the result of the operation was equal to 0, flag Z was set to 1.";
		}

		rsf += System.lineSeparator();

		if (computer.getFlagN().getValue() == 0) {
			rsf += "Since the result of the operation appeared positive, flag N was set to 0.";
		} else {
			rsf += "Since the result of the operation appeared negative, flag N was set to 1.";
		}

		rsf += System.lineSeparator();

		if (computer.getFlagO().getValue() == 0) {
			rsf += "Since there was no overflow, flag O was set to 0.";
		} else {
			rsf += "Since overflow was detected, flag O was set to 1.";
		}
		
		return rsf + System.lineSeparator();
	}



	// MARK: Explain Branch
	/**
	 * Explains the branch step just taken by a computer.
	 * @param computer the computer being explained
	 * @return an explanation of the last step
	 */
	private static String explainBranchStep(Computer computer) {
		String rsf = String.format("BRANCH%n");
		int opCode = computer.getOpCode().getValue();
		int fnCode = computer.getFnCode().getValue();
		int valZ = computer.getFlagZ().getValue();
		int valN = computer.getFlagN().getValue();
		int valO = computer.getFlagO().getValue();
		String vasVC = vas(computer.getValC());
		if (opCode != 4) {
			rsf += String.format("Since the value of opCode was not 4, branching was ignored.%n");
		} else if (Computer.shouldBranch(fnCode, valZ, valN, valO)) {
			rsf += "Based on the values stored in the three flags and fnCode, the computer decided";
			rsf += String.format(" to branch, setting nextPC to the value stored in valC, %s.%n", vasVC);
		} else {
			rsf += "Based on the values stored in the three flags and fnCode, the computer decided";
			rsf += String.format(" not to branch, maintaining the current value of nextPC.%n");
		}
		return rsf;
	}



	// MARK: Explain Memory Write
	/**
	 * Explains the memory write step just taken by a computer.
	 * @param computer the computer being explained
	 * @return an explanation of the last step
	 */
	private static String explainMemoryWriteStep(Computer computer) {
		String rsf = String.format("MEMORY WRITE%n");
		if (computer.getRegWrite().getValue() == 0) {
			rsf += String.format("Since regWrite was 0, the computer did not write to any register.%n");
		} else {
			String vasRW = vas(computer.getRegWrite());
			String vasVW = vas(computer.getValWrite());
			rsf += String.format("Since regWrite was %s, the computer wrote valWrite, %s to r%s.%n",
					vasRW, vasVW, vasRW);
		}
		rsf += String.format("Finally, the value of nextPC was copied to the program counter, ending the cycle.%n");

		return rsf;
	}



	// MARK: Helpers
	/**
	 * Calls {@link Register#valueAsString()} on the given register.
	 * @param r the register being represented
	 * @return a string representation of the value in the given register
	 */
	private static String vas(Register r) {
		return r.valueAsString();
	}
}
