package net.moctave.bitwise.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import net.moctave.bitwise.exceptions.LabelNotFoundException;
import net.moctave.bitwise.model.instructions.*;

public class TestComputer {
	private Computer computer;
	
	@BeforeEach
	public void init() {
		computer = new Computer();
	}


	public void initSampleProgram() {
		List<Instruction> instructions = computer.getInstructions();
		instructions.add(new MoveInstruction(1, 6));
		instructions.add(new IncInstruction(1));
		instructions.add(new MoveInstruction(2, 4));
		instructions.add(new MoveInstruction(3, 1));
		instructions.add(new Label("loop"));
		instructions.add(new AddInstruction(1, 2));
		instructions.add(new SubInstruction(2, 3));
		instructions.add(new JumpGreaterEqualsInstruction("loop"));
		instructions.add(new HaltInstruction());
	}

	public void assertRegisterValuesEqual(int r1, int r2, int r3, int r4, int r5, int r6, int r7, int r8,
			int r9, int rA, int rB, int rC, int rD, int rE, int rF)
	{
		assertEquals(r1, computer.getRegisters()[0].getValue());
		assertEquals(r2, computer.getRegisters()[1].getValue());
		assertEquals(r3, computer.getRegisters()[2].getValue());
		assertEquals(r4, computer.getRegisters()[3].getValue());
		assertEquals(r5, computer.getRegisters()[4].getValue());
		assertEquals(r6, computer.getRegisters()[5].getValue());
		assertEquals(r7, computer.getRegisters()[6].getValue());
		assertEquals(r8, computer.getRegisters()[7].getValue());
		assertEquals(r9, computer.getRegisters()[8].getValue());
		assertEquals(rA, computer.getRegisters()[9].getValue());
		assertEquals(rB, computer.getRegisters()[10].getValue());
		assertEquals(rC, computer.getRegisters()[11].getValue());
		assertEquals(rD, computer.getRegisters()[12].getValue());
		assertEquals(rE, computer.getRegisters()[13].getValue());
		assertEquals(rF, computer.getRegisters()[14].getValue());
	}


	@Test
	public void testBasicConstructor() {
		Register opCode = computer.getOpCode();
		Register fnCode = computer.getFnCode();
		Register regA = computer.getRegA();
		Register regB = computer.getRegB();
		Register regWrite = computer.getRegWrite();
		Register valA = computer.getValA();
		Register valB = computer.getValB();
		Register valC = computer.getValC();
		Register valWrite = computer.getValWrite();
		Register programCounter = computer.getProgramCounter();
		Register nextProgramCounter = computer.getNextProgramCounter();
		Register flagZ = computer.getFlagZ();
		Register flagN = computer.getFlagN();
		Register flagO = computer.getFlagO();
		List<Instruction> instructions = computer.getInstructions();
		Register[] registers = computer.getRegisters();

		assertEquals("opCode", opCode.getName());
		assertEquals(0, opCode.getValue());
		assertEquals(4, opCode.getSize());

		assertEquals("fnCode", fnCode.getName());
		assertEquals(0, fnCode.getValue());
		assertEquals(4, fnCode.getSize());

		assertEquals("regA", regA.getName());
		assertEquals(0, regA.getValue());
		assertEquals(4, regA.getSize());

		assertEquals("regB", regB.getName());
		assertEquals(0, regB.getValue());
		assertEquals(4, regB.getSize());

		assertEquals("regWrite", regWrite.getName());
		assertEquals(0, regWrite.getValue());
		assertEquals(4, regWrite.getSize());

		assertEquals("valA", valA.getName());
		assertEquals(0, valA.getValue());
		assertEquals(32, valA.getSize());

		assertEquals("valB", valB.getName());
		assertEquals(0, valB.getValue());
		assertEquals(32, valB.getSize());

		assertEquals("valC", valC.getName());
		assertEquals(0, valC.getValue());
		assertEquals(32, valC.getSize());

		assertEquals("valWrite", valWrite.getName());
		assertEquals(0, valWrite.getValue());
		assertEquals(32, valWrite.getSize());

		assertEquals("PC", programCounter.getName());
		assertEquals(0, programCounter.getValue());
		assertEquals(32, programCounter.getSize());

		assertEquals("nextPC", nextProgramCounter.getName());
		assertEquals(0, nextProgramCounter.getValue());
		assertEquals(32, nextProgramCounter.getSize());

		assertEquals("Z", flagZ.getName());
		assertEquals(0, flagZ.getValue());
		assertEquals(1, flagZ.getSize());

		assertEquals("N", flagN.getName());
		assertEquals(0, flagN.getValue());
		assertEquals(1, flagN.getSize());

		assertEquals("O", flagO.getName());
		assertEquals(0, flagO.getValue());
		assertEquals(1, flagO.getSize());

		assertEquals(0, instructions.size());
		assertEquals(-1, computer.getNextStep());

		assertEquals("1", registers[0].getName());
		assertEquals(0, registers[0].getValue());
		assertEquals(32, registers[0].getSize());

		assertEquals("2", registers[1].getName());
		assertEquals(0, registers[1].getValue());
		assertEquals(32, registers[1].getSize());

		assertEquals("3", registers[2].getName());
		assertEquals(0, registers[2].getValue());
		assertEquals(32, registers[2].getSize());

		assertEquals("4", registers[3].getName());
		assertEquals(0, registers[3].getValue());
		assertEquals(32, registers[3].getSize());

		assertEquals("5", registers[4].getName());
		assertEquals(0, registers[4].getValue());
		assertEquals(32, registers[4].getSize());

		assertEquals("6", registers[5].getName());
		assertEquals(0, registers[5].getValue());
		assertEquals(32, registers[5].getSize());

		assertEquals("7", registers[6].getName());
		assertEquals(0, registers[6].getValue());
		assertEquals(32, registers[6].getSize());

		assertEquals("8", registers[7].getName());
		assertEquals(0, registers[7].getValue());
		assertEquals(32, registers[7].getSize());

		assertEquals("9", registers[8].getName());
		assertEquals(0, registers[8].getValue());
		assertEquals(32, registers[8].getSize());

		assertEquals("A", registers[9].getName());
		assertEquals(0, registers[9].getValue());
		assertEquals(32, registers[9].getSize());

		assertEquals("B", registers[10].getName());
		assertEquals(0, registers[10].getValue());
		assertEquals(32, registers[10].getSize());

		assertEquals("C", registers[11].getName());
		assertEquals(0, registers[11].getValue());
		assertEquals(32, registers[11].getSize());

		assertEquals("D", registers[12].getName());
		assertEquals(0, registers[12].getValue());
		assertEquals(32, registers[12].getSize());

		assertEquals("E", registers[13].getName());
		assertEquals(0, registers[13].getValue());
		assertEquals(32, registers[13].getSize());

		assertEquals("F", registers[14].getName());
		assertEquals(0, registers[14].getValue());
		assertEquals(32, registers[14].getSize());
	}


	@Test
	public void testParameterizedConstructor() {
		computer = new Computer(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14,
				Arrays.asList(new Instruction[]{new HaltInstruction()}), 380,
				Arrays.asList(new Integer[]{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15}));

		Register opCode = computer.getOpCode();
		Register fnCode = computer.getFnCode();
		Register regA = computer.getRegA();
		Register regB = computer.getRegB();
		Register regWrite = computer.getRegWrite();
		Register valA = computer.getValA();
		Register valB = computer.getValB();
		Register valC = computer.getValC();
		Register valWrite = computer.getValWrite();
		Register programCounter = computer.getProgramCounter();
		Register nextProgramCounter = computer.getNextProgramCounter();
		Register flagZ = computer.getFlagZ();
		Register flagN = computer.getFlagN();
		Register flagO = computer.getFlagO();
		List<Instruction> instructions = computer.getInstructions();
		Register[] registers = computer.getRegisters();

		assertEquals("opCode", opCode.getName());
		assertEquals(1, opCode.getValue());
		assertEquals(4, opCode.getSize());

		assertEquals("fnCode", fnCode.getName());
		assertEquals(2, fnCode.getValue());
		assertEquals(4, fnCode.getSize());

		assertEquals("regA", regA.getName());
		assertEquals(3, regA.getValue());
		assertEquals(4, regA.getSize());

		assertEquals("regB", regB.getName());
		assertEquals(4, regB.getValue());
		assertEquals(4, regB.getSize());

		assertEquals("regWrite", regWrite.getName());
		assertEquals(5, regWrite.getValue());
		assertEquals(4, regWrite.getSize());

		assertEquals("valA", valA.getName());
		assertEquals(6, valA.getValue());
		assertEquals(32, valA.getSize());

		assertEquals("valB", valB.getName());
		assertEquals(7, valB.getValue());
		assertEquals(32, valB.getSize());

		assertEquals("valC", valC.getName());
		assertEquals(8, valC.getValue());
		assertEquals(32, valC.getSize());

		assertEquals("valWrite", valWrite.getName());
		assertEquals(9, valWrite.getValue());
		assertEquals(32, valWrite.getSize());

		assertEquals("PC", programCounter.getName());
		assertEquals(10, programCounter.getValue());
		assertEquals(32, programCounter.getSize());

		assertEquals("nextPC", nextProgramCounter.getName());
		assertEquals(11, nextProgramCounter.getValue());
		assertEquals(32, nextProgramCounter.getSize());

		assertEquals("Z", flagZ.getName());
		assertEquals(12, flagZ.getValue());
		assertEquals(1, flagZ.getSize());

		assertEquals("N", flagN.getName());
		assertEquals(13, flagN.getValue());
		assertEquals(1, flagN.getSize());

		assertEquals("O", flagO.getName());
		assertEquals(14, flagO.getValue());
		assertEquals(1, flagO.getSize());

		assertEquals(1, instructions.size());
		assertEquals(0, instructions.get(0).getOpCode());
		assertEquals(380, computer.getNextStep());

		assertEquals("1", registers[0].getName());
		assertEquals(1, registers[0].getValue());
		assertEquals(32, registers[0].getSize());

		assertEquals("2", registers[1].getName());
		assertEquals(2, registers[1].getValue());
		assertEquals(32, registers[1].getSize());

		assertEquals("3", registers[2].getName());
		assertEquals(3, registers[2].getValue());
		assertEquals(32, registers[2].getSize());

		assertEquals("4", registers[3].getName());
		assertEquals(4, registers[3].getValue());
		assertEquals(32, registers[3].getSize());

		assertEquals("5", registers[4].getName());
		assertEquals(5, registers[4].getValue());
		assertEquals(32, registers[4].getSize());

		assertEquals("6", registers[5].getName());
		assertEquals(6, registers[5].getValue());
		assertEquals(32, registers[5].getSize());

		assertEquals("7", registers[6].getName());
		assertEquals(7, registers[6].getValue());
		assertEquals(32, registers[6].getSize());

		assertEquals("8", registers[7].getName());
		assertEquals(8, registers[7].getValue());
		assertEquals(32, registers[7].getSize());

		assertEquals("9", registers[8].getName());
		assertEquals(9, registers[8].getValue());
		assertEquals(32, registers[8].getSize());

		assertEquals("A", registers[9].getName());
		assertEquals(10, registers[9].getValue());
		assertEquals(32, registers[9].getSize());

		assertEquals("B", registers[10].getName());
		assertEquals(11, registers[10].getValue());
		assertEquals(32, registers[10].getSize());

		assertEquals("C", registers[11].getName());
		assertEquals(12, registers[11].getValue());
		assertEquals(32, registers[11].getSize());

		assertEquals("D", registers[12].getName());
		assertEquals(13, registers[12].getValue());
		assertEquals(32, registers[12].getSize());

		assertEquals("E", registers[13].getName());
		assertEquals(14, registers[13].getValue());
		assertEquals(32, registers[13].getSize());

		assertEquals("F", registers[14].getName());
		assertEquals(15, registers[14].getValue());
		assertEquals(32, registers[14].getSize());
	}


	@Test
	public void testAsByteListEmpty() throws LabelNotFoundException {
		List<Byte> bytes = computer.asByteList();
		assertEquals(0, bytes.size());
	}

	@Test
	public void testAsByteListNotEmpty() throws LabelNotFoundException {
		initSampleProgram();
		List<Byte> bytes = computer.asByteList();
		assertEquals(27, bytes.size());
		assertEquals(0x11, (byte) bytes.get(0));
		assertEquals(0x00, (byte) bytes.get(1));
		assertEquals(0x00, (byte) bytes.get(2));
		assertEquals(0x00, (byte) bytes.get(3));
		assertEquals(0x06, (byte) bytes.get(4));

		assertEquals(0x30, (byte) bytes.get(5));
		assertEquals(0x10, (byte) bytes.get(6));

		assertEquals(0x12, (byte) bytes.get(7));
		assertEquals(0x00, (byte) bytes.get(8));
		assertEquals(0x00, (byte) bytes.get(9));
		assertEquals(0x00, (byte) bytes.get(10));
		assertEquals(0x04, (byte) bytes.get(11));

		assertEquals(0x13, (byte) bytes.get(12));
		assertEquals(0x00, (byte) bytes.get(13));
		assertEquals(0x00, (byte) bytes.get(14));
		assertEquals(0x00, (byte) bytes.get(15));
		assertEquals(0x01, (byte) bytes.get(16));

		// Label "loop" is at byte 17 (0x11)

		assertEquals(0x21, (byte) bytes.get(17));
		assertEquals(0x12, (byte) bytes.get(18));

		assertEquals(0x22, (byte) bytes.get(19));
		assertEquals(0x23, (byte) bytes.get(20));

		assertEquals(0x43, (byte) bytes.get(21));
		assertEquals(0x00, (byte) bytes.get(22));
		assertEquals(0x00, (byte) bytes.get(23));
		assertEquals(0x00, (byte) bytes.get(24));
		assertEquals(0x11, (byte) bytes.get(25));

		assertEquals(0x00, (byte) bytes.get(26));
	}


	@Test
	public void testResetNoInstructions() {
		computer.reset();

		assertEquals(0, computer.getOpCode().getValue());
		assertEquals(0, computer.getFnCode().getValue());
		assertEquals(0, computer.getRegA().getValue());
		assertEquals(0, computer.getRegB().getValue());
		assertEquals(0, computer.getRegWrite().getValue());
		assertEquals(0, computer.getValA().getValue());
		assertEquals(0, computer.getValB().getValue());
		assertEquals(0, computer.getValC().getValue());
		assertEquals(0, computer.getValWrite().getValue());
		assertEquals(0, computer.getProgramCounter().getValue());
		assertEquals(0, computer.getNextProgramCounter().getValue());
		assertEquals(0, computer.getFlagZ().getValue());
		assertEquals(0, computer.getFlagN().getValue());
		assertEquals(0, computer.getFlagO().getValue());
		assertEquals(0, computer.getInstructions().size());
		assertEquals(-1, computer.getNextStep());
	}


	@Test
	public void testResetInstructionsNotExecuted() {
		initSampleProgram();

		computer.reset();

		assertEquals(0, computer.getOpCode().getValue());
		assertEquals(0, computer.getFnCode().getValue());
		assertEquals(0, computer.getRegA().getValue());
		assertEquals(0, computer.getRegB().getValue());
		assertEquals(0, computer.getRegWrite().getValue());
		assertEquals(0, computer.getValA().getValue());
		assertEquals(0, computer.getValB().getValue());
		assertEquals(0, computer.getValC().getValue());
		assertEquals(0, computer.getValWrite().getValue());
		assertEquals(0, computer.getProgramCounter().getValue());
		assertEquals(0, computer.getNextProgramCounter().getValue());
		assertEquals(0, computer.getFlagZ().getValue());
		assertEquals(0, computer.getFlagN().getValue());
		assertEquals(0, computer.getFlagO().getValue());
		assertEquals(9, computer.getInstructions().size());
		assertEquals(-1, computer.getNextStep());
	}


	@Test
	public void testResetInstructionsExecuted() throws LabelNotFoundException {
		initSampleProgram();

		for (int i = 0; i < 30; i++) {
			computer.step();
		}

		computer.reset();

		assertEquals(0, computer.getOpCode().getValue());
		assertEquals(0, computer.getFnCode().getValue());
		assertEquals(0, computer.getRegA().getValue());
		assertEquals(0, computer.getRegB().getValue());
		assertEquals(0, computer.getRegWrite().getValue());
		assertEquals(0, computer.getValA().getValue());
		assertEquals(0, computer.getValB().getValue());
		assertEquals(0, computer.getValC().getValue());
		assertEquals(0, computer.getValWrite().getValue());
		assertEquals(0, computer.getProgramCounter().getValue());
		assertEquals(0, computer.getNextProgramCounter().getValue());
		assertEquals(0, computer.getFlagZ().getValue());
		assertEquals(0, computer.getFlagN().getValue());
		assertEquals(0, computer.getFlagO().getValue());
		assertEquals(9, computer.getInstructions().size());
		assertEquals(-1, computer.getNextStep());
	}


	@Test
	public void testRunNoInstructions() throws LabelNotFoundException {
		computer.runAll();

		assertEquals(0, computer.getOpCode().getValue());
		assertEquals(0, computer.getFnCode().getValue());
		assertEquals(0, computer.getRegA().getValue());
		assertEquals(0, computer.getRegB().getValue());
		assertEquals(0, computer.getRegWrite().getValue());
		assertEquals(0, computer.getValA().getValue());
		assertEquals(0, computer.getValB().getValue());
		assertEquals(0, computer.getValC().getValue());
		assertEquals(0, computer.getValWrite().getValue());
		assertEquals(0, computer.getProgramCounter().getValue());
		assertEquals(0, computer.getNextProgramCounter().getValue());
		assertEquals(0, computer.getFlagZ().getValue());
		assertEquals(0, computer.getFlagN().getValue());
		assertEquals(0, computer.getFlagO().getValue());
		assertEquals(0, computer.getInstructions().size());
		assertEquals(0, computer.getNextStep());
	}


	@Test
	public void testRunWithInstructions() throws LabelNotFoundException {
		initSampleProgram();

		computer.runAll();

		assertEquals(0, computer.getOpCode().getValue());
		assertEquals(0, computer.getFnCode().getValue());
		assertEquals(0, computer.getRegA().getValue());
		assertEquals(0, computer.getRegB().getValue());
		assertEquals(0, computer.getRegWrite().getValue());
		assertEquals(0, computer.getValA().getValue());
		assertEquals(0, computer.getValB().getValue());
		assertEquals(0, computer.getValC().getValue());
		assertEquals(0, computer.getValWrite().getValue());
		assertEquals(26, computer.getProgramCounter().getValue());
		assertEquals(26, computer.getNextProgramCounter().getValue());
		assertEquals(0, computer.getFlagZ().getValue());
		assertEquals(1, computer.getFlagN().getValue());
		assertEquals(0, computer.getFlagO().getValue());
		assertEquals(9, computer.getInstructions().size());
		assertEquals(0, computer.getNextStep());
	}


	@Test
	public void testRunCopy() throws LabelNotFoundException {
		List<Instruction> instructions = computer.getInstructions();
		instructions.add(new MoveInstruction(1, 3));
		instructions.add(new MoveInstruction(2, 6));

		instructions.add(new CopyInstruction(1, 2));

		instructions.add(new HaltInstruction());
		computer.runAll();

		assertRegisterValuesEqual(6, 6, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
	}

	@Test
	public void testRunAdd() throws LabelNotFoundException {
		List<Instruction> instructions = computer.getInstructions();
		instructions.add(new MoveInstruction(1, 3));
		instructions.add(new MoveInstruction(2, 6));

		instructions.add(new AddInstruction(1, 2));

		instructions.add(new HaltInstruction());
		computer.runAll();

		assertRegisterValuesEqual(9, 6, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
	}

	@Test
	public void testRunSub() throws LabelNotFoundException {
		List<Instruction> instructions = computer.getInstructions();
		instructions.add(new MoveInstruction(1, 3));
		instructions.add(new MoveInstruction(2, 6));

		instructions.add(new SubInstruction(1, 2));

		instructions.add(new HaltInstruction());
		computer.runAll();

		assertRegisterValuesEqual(-3, 6, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
	}

	@Test
	public void testRunAnd() throws LabelNotFoundException {
		List<Instruction> instructions = computer.getInstructions();
		instructions.add(new MoveInstruction(1, 3));
		instructions.add(new MoveInstruction(2, 6));

		instructions.add(new AndInstruction(1, 2));

		instructions.add(new HaltInstruction());
		computer.runAll();

		assertRegisterValuesEqual(2, 6, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
	}

	@Test
	public void testRunOr() throws LabelNotFoundException {
		List<Instruction> instructions = computer.getInstructions();
		instructions.add(new MoveInstruction(1, 3));
		instructions.add(new MoveInstruction(2, 6));

		instructions.add(new OrInstruction(1, 2));

		instructions.add(new HaltInstruction());
		computer.runAll();

		assertRegisterValuesEqual(7, 6, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
	}

	@Test
	public void testRunXor() throws LabelNotFoundException {
		List<Instruction> instructions = computer.getInstructions();
		instructions.add(new MoveInstruction(1, 3));
		instructions.add(new MoveInstruction(2, 6));

		instructions.add(new XorInstruction(1, 2));

		instructions.add(new HaltInstruction());
		computer.runAll();

		assertRegisterValuesEqual(5, 6, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
	}

	@Test
	public void testRunInc() throws LabelNotFoundException {
		List<Instruction> instructions = computer.getInstructions();
		instructions.add(new MoveInstruction(3, 4));

		instructions.add(new IncInstruction(3));

		instructions.add(new HaltInstruction());
		computer.runAll();

		assertRegisterValuesEqual(0, 0, 5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
	}

	@Test
	public void testRunNeg() throws LabelNotFoundException {
		List<Instruction> instructions = computer.getInstructions();
		instructions.add(new MoveInstruction(3, 4));

		instructions.add(new NegInstruction(3));

		instructions.add(new HaltInstruction());
		computer.runAll();

		assertRegisterValuesEqual(0, 0, -4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
	}

	@Test
	public void testRunNot() throws LabelNotFoundException {
		List<Instruction> instructions = computer.getInstructions();
		instructions.add(new MoveInstruction(3, 4));

		instructions.add(new NotInstruction(3));

		instructions.add(new HaltInstruction());
		computer.runAll();

		assertRegisterValuesEqual(0, 0, ~4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
	}

	@Test
	public void testRunJumpAlways() throws LabelNotFoundException {
		List<Instruction> instructions = computer.getInstructions();
		instructions.add(new MoveInstruction(4, -1));
		instructions.add(new JumpAlwaysInstruction("End"));

		instructions.add(new IncInstruction(4));
		instructions.add(new JumpAlwaysInstruction("End"));

		instructions.add(new IncInstruction(4));
		instructions.add(new JumpAlwaysInstruction("End"));

		instructions.add(new IncInstruction(4));

		instructions.add(new Label("End"));

		instructions.add(new HaltInstruction());
		computer.runAll();

		assertRegisterValuesEqual(0, 0, 0, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
	}

	@Test
	public void testRunJumpEquals() throws LabelNotFoundException {
		List<Instruction> instructions = computer.getInstructions();

		instructions.add(new MoveInstruction(4, -1));
		instructions.add(new JumpEqualsInstruction("End"));

		instructions.add(new IncInstruction(4));
		instructions.add(new JumpEqualsInstruction("End"));

		instructions.add(new IncInstruction(4));
		instructions.add(new JumpEqualsInstruction("End"));

		instructions.add(new IncInstruction(4));

		instructions.add(new Label("End"));

		instructions.add(new HaltInstruction());
		computer.runAll();

		assertRegisterValuesEqual(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
	}

	@Test
	public void testRunJumpLessEquals() throws LabelNotFoundException {
		List<Instruction> instructions = computer.getInstructions();

		instructions.add(new MoveInstruction(4, -1));
		instructions.add(new JumpLessEqualsInstruction("End"));

		instructions.add(new IncInstruction(4));
		instructions.add(new JumpLessEqualsInstruction("End"));

		instructions.add(new IncInstruction(4));
		instructions.add(new JumpLessEqualsInstruction("End"));

		instructions.add(new IncInstruction(4));

		instructions.add(new Label("End"));

		instructions.add(new HaltInstruction());
		computer.runAll();

		assertRegisterValuesEqual(0, 0, 0, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
	}

	@Test
	public void testRunJumpGreaterEquals() throws LabelNotFoundException {
		List<Instruction> instructions = computer.getInstructions();

		instructions.add(new MoveInstruction(4, -1));
		instructions.add(new JumpGreaterEqualsInstruction("End"));

		instructions.add(new IncInstruction(4));
		instructions.add(new JumpGreaterEqualsInstruction("End"));

		instructions.add(new IncInstruction(4));
		instructions.add(new JumpGreaterEqualsInstruction("End"));

		instructions.add(new IncInstruction(4));

		instructions.add(new Label("End"));

		instructions.add(new HaltInstruction());
		computer.runAll();

		assertRegisterValuesEqual(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
	}

	@Test
	public void testRunJumpNotEquals() throws LabelNotFoundException {
		List<Instruction> instructions = computer.getInstructions();

		instructions.add(new MoveInstruction(4, -1));
		instructions.add(new JumpNotEqualsInstruction("End"));

		instructions.add(new IncInstruction(4));
		instructions.add(new JumpNotEqualsInstruction("End"));

		instructions.add(new IncInstruction(4));
		instructions.add(new JumpNotEqualsInstruction("End"));

		instructions.add(new IncInstruction(4));

		instructions.add(new Label("End"));

		instructions.add(new HaltInstruction());
		computer.runAll();

		assertRegisterValuesEqual(0, 0, 0, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
	}

	@Test
	public void testRunJumpLess() throws LabelNotFoundException {
		List<Instruction> instructions = computer.getInstructions();

		instructions.add(new MoveInstruction(4, -1));
		instructions.add(new JumpLessInstruction("End"));

		instructions.add(new IncInstruction(4));
		instructions.add(new JumpLessInstruction("End"));

		instructions.add(new IncInstruction(4));
		instructions.add(new JumpLessInstruction("End"));

		instructions.add(new IncInstruction(4));

		instructions.add(new Label("End"));

		instructions.add(new HaltInstruction());
		computer.runAll();

		assertRegisterValuesEqual(0, 0, 0, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
	}

	@Test
	public void testRunJumpGreater() throws LabelNotFoundException {
		List<Instruction> instructions = computer.getInstructions();

		instructions.add(new MoveInstruction(4, -1));
		instructions.add(new JumpGreaterInstruction("End"));

		instructions.add(new IncInstruction(4));
		instructions.add(new JumpGreaterInstruction("End"));

		instructions.add(new IncInstruction(4));
		instructions.add(new JumpGreaterInstruction("End"));
		
		instructions.add(new IncInstruction(4));

		instructions.add(new Label("End"));

		instructions.add(new HaltInstruction());
		computer.runAll();

		assertRegisterValuesEqual(0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
	}

	@Test
	public void testStepNoInstructions() throws LabelNotFoundException {
		assertFalse(computer.step());

		assertEquals(0, computer.getOpCode().getValue());
		assertEquals(0, computer.getFnCode().getValue());
		assertEquals(0, computer.getRegA().getValue());
		assertEquals(0, computer.getRegB().getValue());
		assertEquals(0, computer.getRegWrite().getValue());
		assertEquals(0, computer.getValA().getValue());
		assertEquals(0, computer.getValB().getValue());
		assertEquals(0, computer.getValC().getValue());
		assertEquals(0, computer.getValWrite().getValue());
		assertEquals(0, computer.getProgramCounter().getValue());
		assertEquals(0, computer.getNextProgramCounter().getValue());
		assertEquals(0, computer.getFlagZ().getValue());
		assertEquals(0, computer.getFlagN().getValue());
		assertEquals(0, computer.getFlagO().getValue());
		assertEquals(0, computer.getInstructions().size());
		assertEquals(0, computer.getNextStep());
	}

	@Test
	public void testStepOneInstruction() throws LabelNotFoundException {
		initSampleProgram();

		assertTrue(computer.step());

		assertEquals(1, computer.getOpCode().getValue());
		assertEquals(0, computer.getFnCode().getValue());
		assertEquals(1, computer.getRegA().getValue());
		assertEquals(0, computer.getRegB().getValue());
		assertEquals(1, computer.getRegWrite().getValue());
		assertEquals(0, computer.getValA().getValue());
		assertEquals(0, computer.getValB().getValue());
		assertEquals(6, computer.getValC().getValue());
		assertEquals(0, computer.getValWrite().getValue());
		assertEquals(0, computer.getProgramCounter().getValue());
		assertEquals(5, computer.getNextProgramCounter().getValue());
		assertEquals(0, computer.getFlagZ().getValue());
		assertEquals(0, computer.getFlagN().getValue());
		assertEquals(0, computer.getFlagO().getValue());
		assertEquals(9, computer.getInstructions().size());
		assertEquals(0, computer.getNextStep());

		assertTrue(computer.step());

		assertEquals(1, computer.getOpCode().getValue());
		assertEquals(0, computer.getFnCode().getValue());
		assertEquals(1, computer.getRegA().getValue());
		assertEquals(0, computer.getRegB().getValue());
		assertEquals(1, computer.getRegWrite().getValue());
		assertEquals(0, computer.getValA().getValue());
		assertEquals(0, computer.getValB().getValue());
		assertEquals(6, computer.getValC().getValue());
		assertEquals(6, computer.getValWrite().getValue());
		assertEquals(0, computer.getProgramCounter().getValue());
		assertEquals(5, computer.getNextProgramCounter().getValue());
		assertEquals(0, computer.getFlagZ().getValue());
		assertEquals(0, computer.getFlagN().getValue());
		assertEquals(0, computer.getFlagO().getValue());
		assertEquals(9, computer.getInstructions().size());
		assertEquals(1, computer.getNextStep());

		assertTrue(computer.step());

		assertEquals(1, computer.getOpCode().getValue());
		assertEquals(0, computer.getFnCode().getValue());
		assertEquals(1, computer.getRegA().getValue());
		assertEquals(0, computer.getRegB().getValue());
		assertEquals(1, computer.getRegWrite().getValue());
		assertEquals(0, computer.getValA().getValue());
		assertEquals(0, computer.getValB().getValue());
		assertEquals(6, computer.getValC().getValue());
		assertEquals(6, computer.getValWrite().getValue());
		assertEquals(0, computer.getProgramCounter().getValue());
		assertEquals(5, computer.getNextProgramCounter().getValue());
		assertEquals(0, computer.getFlagZ().getValue());
		assertEquals(0, computer.getFlagN().getValue());
		assertEquals(0, computer.getFlagO().getValue());
		assertEquals(9, computer.getInstructions().size());
		assertEquals(2, computer.getNextStep());



		assertTrue(computer.step());

		assertEquals(1, computer.getOpCode().getValue());
		assertEquals(0, computer.getFnCode().getValue());
		assertEquals(1, computer.getRegA().getValue());
		assertEquals(0, computer.getRegB().getValue());
		assertEquals(1, computer.getRegWrite().getValue());
		assertEquals(0, computer.getValA().getValue());
		assertEquals(0, computer.getValB().getValue());
		assertEquals(6, computer.getValC().getValue());
		assertEquals(6, computer.getValWrite().getValue());
		assertEquals(5, computer.getProgramCounter().getValue());
		assertEquals(5, computer.getNextProgramCounter().getValue());
		assertEquals(0, computer.getFlagZ().getValue());
		assertEquals(0, computer.getFlagN().getValue());
		assertEquals(0, computer.getFlagO().getValue());
		assertEquals(9, computer.getInstructions().size());
		assertEquals(3, computer.getNextStep());
	}


	@Test
	public void testMoveMathResultNegative() {
		assertArrayEquals(new int[]{-65, 0, 1, 0}, Computer.moveMathResult(-65));
	}

	@Test
	public void testMoveMathResultZero() {
		assertArrayEquals(new int[]{0, 1, 0, 0}, Computer.moveMathResult(0));
	}

	@Test
	public void testMoveMathResultPositive() {
		assertArrayEquals(new int[]{65, 0, 0, 0}, Computer.moveMathResult(65));
	}


	@Test
	public void testBinaryMathResultCopy() {
		assertArrayEquals(new int[]{2, 0, 0, 0}, Computer.binaryMathResult(1, 2, 0));
		assertArrayEquals(new int[]{0, 1, 0, 0}, Computer.binaryMathResult(64, 0, 0));
		assertArrayEquals(new int[]{-308, 0, 1, 0}, Computer.binaryMathResult(17, -308, 0));
	}

	@Test
	public void testBinaryMathResultAdd() {
		// a > 0, b > 0, r > 0
		assertArrayEquals(new int[]{3, 0, 0, 0}, Computer.binaryMathResult(1, 2, 1));
		// a > 0, b = 0, r > 0
		assertArrayEquals(new int[]{4, 0, 0, 0}, Computer.binaryMathResult(4, 0, 1));
		// a > 0, b > 0, r < 0
		assertArrayEquals(new int[]{Integer.MAX_VALUE + 6, 0, 1, 1},
					Computer.binaryMathResult(Integer.MAX_VALUE, 6, 1));
		// a > 0, b < 0, r > 0
		assertArrayEquals(new int[]{Integer.MIN_VALUE -5, 0, 0, 1},
					Computer.binaryMathResult(Integer.MIN_VALUE, -5, 1));
		// a > 0, b < 0, r = 0
		assertArrayEquals(new int[]{0, 1, 0, 0}, Computer.binaryMathResult(8, -8, 1));
		// a > 0, b < 0, r < 0
		assertArrayEquals(new int[]{-1, 0, 1, 0}, Computer.binaryMathResult(7, -8, 1));
		// a < 0, b = 0, r < 0
		assertArrayEquals(new int[]{-5, 0, 1, 0}, Computer.binaryMathResult(-5, 0, 1));
		// a = 0, b > 0, r > 0
		assertArrayEquals(new int[]{31, 0, 0, 0}, Computer.binaryMathResult(0, 31, 1));
		// a = 0, b < 0, r < 0
		assertArrayEquals(new int[]{-31, 0, 1, 0}, Computer.binaryMathResult(0, -31, 1));
		// a < 0, b > 0, r > 0
		assertArrayEquals(new int[]{10, 0, 0, 0}, Computer.binaryMathResult(-2, 12, 1));
		// a < 0, b > 0, r = 0
		assertArrayEquals(new int[]{0, 1, 0, 0}, Computer.binaryMathResult(-12, 12, 1));
		// a < 0, b > 0, r < 0
		assertArrayEquals(new int[]{-2, 0, 1, 0}, Computer.binaryMathResult(-12, 10, 1));
	}

	@Test
	public void testBinaryMathResultSubtract() {
		// a > 0, b < 0, r > 0
		assertArrayEquals(new int[]{3, 0, 0, 0}, Computer.binaryMathResult(1, -2, 2));
		// a > 0, b = 0, r > 0
		assertArrayEquals(new int[]{4, 0, 0, 0}, Computer.binaryMathResult(4, 0, 2));
		// a > 0, b < 0, r < 0
		assertArrayEquals(new int[]{Integer.MAX_VALUE + 6, 0, 1, 1},
					Computer.binaryMathResult(Integer.MAX_VALUE, -6, 2));
		// a > 0, b > 0, r > 0
		assertArrayEquals(new int[]{Integer.MIN_VALUE -5, 0, 0, 1},
					Computer.binaryMathResult(Integer.MIN_VALUE, 5, 2));
		// a > 0, b > 0, r = 0
		assertArrayEquals(new int[]{0, 1, 0, 0}, Computer.binaryMathResult(8, 8, 2));
		// a > 0, b > 0, r < 0
		assertArrayEquals(new int[]{-1, 0, 1, 0}, Computer.binaryMathResult(7, 8, 2));
		// a < 0, b = 0, r < 0
		assertArrayEquals(new int[]{-5, 0, 1, 0}, Computer.binaryMathResult(-5, 0, 2));
		// a = 0, b > 0, r > 0
		assertArrayEquals(new int[]{31, 0, 0, 0}, Computer.binaryMathResult(0, -31, 2));
		// a = 0, b > 0, r < 0
		assertArrayEquals(new int[]{-31, 0, 1, 0}, Computer.binaryMathResult(0, 31, 2));
		// a < 0, b < 0, r > 0
		assertArrayEquals(new int[]{10, 0, 0, 0}, Computer.binaryMathResult(-2, -12, 2));
		// a < 0, b < 0, r = 0
		assertArrayEquals(new int[]{0, 1, 0, 0}, Computer.binaryMathResult(-12, -12, 2));
		// a < 0, b < 0, r < 0
		assertArrayEquals(new int[]{-2, 0, 1, 0}, Computer.binaryMathResult(-12, -10, 2));
	}

	@Test
	public void testBinaryMathResultInvalidFunction() {
		try {
			Computer.binaryMathResult(150, 700, 0xF);
			fail();
		} catch (UnsupportedOperationException e) {
			// Expected
		}
	}


	@Test
	public void testAdditionOverflowValueNegativeSum() {
		assertEquals(0, Computer.additionOverflowValue(-1, -1, -1));
		assertEquals(0, Computer.additionOverflowValue(-1, 0, -1));
		assertEquals(0, Computer.additionOverflowValue(-1, 1, -1));

		assertEquals(0, Computer.additionOverflowValue(0, -1, -1));
		assertEquals(0, Computer.additionOverflowValue(0, 0, -1));
		assertEquals(0, Computer.additionOverflowValue(0, 1, -1));

		assertEquals(0, Computer.additionOverflowValue(1, -1, -1));
		assertEquals(0, Computer.additionOverflowValue(1, 0, -1));
		assertEquals(1, Computer.additionOverflowValue(1, 1, -1));
	}

	@Test
	public void testAdditionOverflowValueZeroSum() {
		assertEquals(0, Computer.additionOverflowValue(-1, -1, 0));
		assertEquals(0, Computer.additionOverflowValue(-1, 0, 0));
		assertEquals(0, Computer.additionOverflowValue(-1, 1, 0));

		assertEquals(0, Computer.additionOverflowValue(0, -1, 0));
		assertEquals(0, Computer.additionOverflowValue(0, 0, 0));
		assertEquals(0, Computer.additionOverflowValue(0, 1, 0));

		assertEquals(0, Computer.additionOverflowValue(1, -1, 0));
		assertEquals(0, Computer.additionOverflowValue(1, 0, 0));
		assertEquals(0, Computer.additionOverflowValue(1, 1, 0));
	}

	@Test
	public void testAdditionOverflowValuePositiveSum() {
		assertEquals(1, Computer.additionOverflowValue(-1, -1, 1));
		assertEquals(0, Computer.additionOverflowValue(-1, 0, 1));
		assertEquals(0, Computer.additionOverflowValue(-1, 1, 1));

		assertEquals(0, Computer.additionOverflowValue(0, -1, 1));
		assertEquals(0, Computer.additionOverflowValue(0, 0, 1));
		assertEquals(0, Computer.additionOverflowValue(0, 1, 1));

		assertEquals(0, Computer.additionOverflowValue(1, -1, 1));
		assertEquals(0, Computer.additionOverflowValue(1, 0, 1));
		assertEquals(0, Computer.additionOverflowValue(1, 1, 1));
	}

	@Test
	public void testUnaryMathResultIncrement() {
		// a < 0, r < 0
		assertArrayEquals(new int[]{-7, 0, 1, 0}, Computer.unaryMathResult(-8, 0));
		// a < 0, r = 0
		assertArrayEquals(new int[]{0, 1, 0, 0}, Computer.unaryMathResult(-1, 0));
		// a = 0, r > 0
		assertArrayEquals(new int[]{1, 0, 0, 0}, Computer.unaryMathResult(0, 0));
		// a > 0, r > 0
		assertArrayEquals(new int[]{35, 0, 0, 0}, Computer.unaryMathResult(34, 0));
		// a > 0, r < 0
		assertArrayEquals(new int[]{Integer.MAX_VALUE + 1, 0, 1, 1}, Computer.unaryMathResult(Integer.MAX_VALUE, 0));
	}

	@Test
	public void testUnaryMathResultNegate() {
		// Normal
		assertArrayEquals(new int[]{-6, 0, 1, 0}, Computer.unaryMathResult(6, 1));
		// a == MIN_VALUE
		assertArrayEquals(new int[]{Integer.MIN_VALUE, 0, 1, 1}, Computer.unaryMathResult(Integer.MIN_VALUE, 1));
	}

	@Test
	public void testUnaryMathResultInvalidFunction() {
		try {
			Computer.unaryMathResult(800, 3);
			fail();
		} catch (UnsupportedOperationException e) {
			// Expected
		}
	}

	@Test
	public void testShouldBranchAlways() {
		assertTrue(Computer.shouldBranch(0, 0, 0, 0));
		assertTrue(Computer.shouldBranch(0, 0, 0, 1));
		assertTrue(Computer.shouldBranch(0, 0, 1, 0));
		assertTrue(Computer.shouldBranch(0, 0, 1, 1));
		assertTrue(Computer.shouldBranch(0, 1, 0, 0));
		assertTrue(Computer.shouldBranch(0, 1, 0, 1));
		assertTrue(Computer.shouldBranch(0, 1, 1, 0));
		assertTrue(Computer.shouldBranch(0, 1, 1, 1));
	}

	@Test
	public void testShouldBranchEquals() {
		assertFalse(Computer.shouldBranch(1, 0, 0, 0));
		assertFalse(Computer.shouldBranch(1, 0, 0, 1));
		assertFalse(Computer.shouldBranch(1, 0, 1, 0));
		assertFalse(Computer.shouldBranch(1, 0, 1, 1));
		assertTrue(Computer.shouldBranch(1, 1, 0, 0));
		assertTrue(Computer.shouldBranch(1, 1, 0, 1));
		assertTrue(Computer.shouldBranch(1, 1, 1, 0));
		assertTrue(Computer.shouldBranch(1, 1, 1, 1));
	}

	@Test
	public void testShouldBranchLessEquals() {
		assertFalse(Computer.shouldBranch(2, 0, 0, 0));
		assertTrue(Computer.shouldBranch(2, 0, 0, 1));
		assertTrue(Computer.shouldBranch(2, 0, 1, 0));
		assertFalse(Computer.shouldBranch(2, 0, 1, 1));
		assertTrue(Computer.shouldBranch(2, 1, 0, 0));
		assertTrue(Computer.shouldBranch(2, 1, 0, 1));
		assertTrue(Computer.shouldBranch(2, 1, 1, 0));
		assertTrue(Computer.shouldBranch(2, 1, 1, 1));
	}

	@Test
	public void testShouldBranchGreaterEquals() {
		assertTrue(Computer.shouldBranch(3, 0, 0, 0));
		assertFalse(Computer.shouldBranch(3, 0, 0, 1));
		assertFalse(Computer.shouldBranch(3, 0, 1, 0));
		assertTrue(Computer.shouldBranch(3, 0, 1, 1));
		assertTrue(Computer.shouldBranch(3, 1, 0, 0));
		assertTrue(Computer.shouldBranch(3, 1, 0, 1));
		assertTrue(Computer.shouldBranch(3, 1, 1, 0));
		assertTrue(Computer.shouldBranch(3, 1, 1, 1));
	}

	@Test
	public void testShouldBranchNotEquals() {
		assertTrue(Computer.shouldBranch(4, 0, 0, 0));
		assertTrue(Computer.shouldBranch(4, 0, 0, 1));
		assertTrue(Computer.shouldBranch(4, 0, 1, 0));
		assertTrue(Computer.shouldBranch(4, 0, 1, 1));
		assertFalse(Computer.shouldBranch(4, 1, 0, 0));
		assertFalse(Computer.shouldBranch(4, 1, 0, 1));
		assertFalse(Computer.shouldBranch(4, 1, 1, 0));
		assertFalse(Computer.shouldBranch(4, 1, 1, 1));
	}

	@Test
	public void testShouldBranchLess() {
		assertFalse(Computer.shouldBranch(5, 0, 0, 0));
		assertTrue(Computer.shouldBranch(5, 0, 0, 1));
		assertTrue(Computer.shouldBranch(5, 0, 1, 0));
		assertFalse(Computer.shouldBranch(5, 0, 1, 1));
		assertFalse(Computer.shouldBranch(5, 1, 0, 0));
		assertFalse(Computer.shouldBranch(5, 1, 0, 1));
		assertFalse(Computer.shouldBranch(5, 1, 1, 0));
		assertFalse(Computer.shouldBranch(5, 1, 1, 1));
	}

	@Test
	public void testShouldBranchGreater() {
		assertTrue(Computer.shouldBranch(6, 0, 0, 0));
		assertFalse(Computer.shouldBranch(6, 0, 0, 1));
		assertFalse(Computer.shouldBranch(6, 0, 1, 0));
		assertTrue(Computer.shouldBranch(6, 0, 1, 1));
		assertFalse(Computer.shouldBranch(6, 1, 0, 0));
		assertFalse(Computer.shouldBranch(6, 1, 0, 1));
		assertFalse(Computer.shouldBranch(6, 1, 1, 0));
		assertFalse(Computer.shouldBranch(6, 1, 1, 1));
	}


	@Test
	public void testSetInstanceNull() {
		Computer.setInstance(null);
		assertNull(Computer.getInstance());
	}


	@Test
	public void testSetInstanceNotNull() {
		Computer.setInstance(computer);

		computer = new Computer();
		initSampleProgram();

		computer = Computer.getInstance();


		Register opCode = computer.getOpCode();
		Register fnCode = computer.getFnCode();
		Register regA = computer.getRegA();
		Register regB = computer.getRegB();
		Register regWrite = computer.getRegWrite();
		Register valA = computer.getValA();
		Register valB = computer.getValB();
		Register valC = computer.getValC();
		Register valWrite = computer.getValWrite();
		Register programCounter = computer.getProgramCounter();
		Register nextProgramCounter = computer.getNextProgramCounter();
		Register flagZ = computer.getFlagZ();
		Register flagN = computer.getFlagN();
		Register flagO = computer.getFlagO();
		List<Instruction> instructions = computer.getInstructions();
		Register[] registers = computer.getRegisters();

		assertEquals("opCode", opCode.getName());
		assertEquals(0, opCode.getValue());
		assertEquals(4, opCode.getSize());

		assertEquals("fnCode", fnCode.getName());
		assertEquals(0, fnCode.getValue());
		assertEquals(4, fnCode.getSize());

		assertEquals("regA", regA.getName());
		assertEquals(0, regA.getValue());
		assertEquals(4, regA.getSize());

		assertEquals("regB", regB.getName());
		assertEquals(0, regB.getValue());
		assertEquals(4, regB.getSize());

		assertEquals("regWrite", regWrite.getName());
		assertEquals(0, regWrite.getValue());
		assertEquals(4, regWrite.getSize());

		assertEquals("valA", valA.getName());
		assertEquals(0, valA.getValue());
		assertEquals(32, valA.getSize());

		assertEquals("valB", valB.getName());
		assertEquals(0, valB.getValue());
		assertEquals(32, valB.getSize());

		assertEquals("valC", valC.getName());
		assertEquals(0, valC.getValue());
		assertEquals(32, valC.getSize());

		assertEquals("valWrite", valWrite.getName());
		assertEquals(0, valWrite.getValue());
		assertEquals(32, valWrite.getSize());

		assertEquals("PC", programCounter.getName());
		assertEquals(0, programCounter.getValue());
		assertEquals(32, programCounter.getSize());

		assertEquals("nextPC", nextProgramCounter.getName());
		assertEquals(0, nextProgramCounter.getValue());
		assertEquals(32, nextProgramCounter.getSize());

		assertEquals("Z", flagZ.getName());
		assertEquals(0, flagZ.getValue());
		assertEquals(1, flagZ.getSize());

		assertEquals("N", flagN.getName());
		assertEquals(0, flagN.getValue());
		assertEquals(1, flagN.getSize());

		assertEquals("O", flagO.getName());
		assertEquals(0, flagO.getValue());
		assertEquals(1, flagO.getSize());

		assertEquals(0, instructions.size());
		assertEquals(-1, computer.getNextStep());

		assertEquals("1", registers[0].getName());
		assertEquals(0, registers[0].getValue());
		assertEquals(32, registers[0].getSize());

		assertEquals("2", registers[1].getName());
		assertEquals(0, registers[1].getValue());
		assertEquals(32, registers[1].getSize());

		assertEquals("3", registers[2].getName());
		assertEquals(0, registers[2].getValue());
		assertEquals(32, registers[2].getSize());

		assertEquals("4", registers[3].getName());
		assertEquals(0, registers[3].getValue());
		assertEquals(32, registers[3].getSize());

		assertEquals("5", registers[4].getName());
		assertEquals(0, registers[4].getValue());
		assertEquals(32, registers[4].getSize());

		assertEquals("6", registers[5].getName());
		assertEquals(0, registers[5].getValue());
		assertEquals(32, registers[5].getSize());

		assertEquals("7", registers[6].getName());
		assertEquals(0, registers[6].getValue());
		assertEquals(32, registers[6].getSize());

		assertEquals("8", registers[7].getName());
		assertEquals(0, registers[7].getValue());
		assertEquals(32, registers[7].getSize());

		assertEquals("9", registers[8].getName());
		assertEquals(0, registers[8].getValue());
		assertEquals(32, registers[8].getSize());

		assertEquals("A", registers[9].getName());
		assertEquals(0, registers[9].getValue());
		assertEquals(32, registers[9].getSize());

		assertEquals("B", registers[10].getName());
		assertEquals(0, registers[10].getValue());
		assertEquals(32, registers[10].getSize());

		assertEquals("C", registers[11].getName());
		assertEquals(0, registers[11].getValue());
		assertEquals(32, registers[11].getSize());

		assertEquals("D", registers[12].getName());
		assertEquals(0, registers[12].getValue());
		assertEquals(32, registers[12].getSize());

		assertEquals("E", registers[13].getName());
		assertEquals(0, registers[13].getValue());
		assertEquals(32, registers[13].getSize());

		assertEquals("F", registers[14].getName());
		assertEquals(0, registers[14].getValue());
		assertEquals(32, registers[14].getSize());
	}


	@Test
	public void testAddInstructions() {
		Instruction i1 = new MoveInstruction(1, 2);
		Instruction i2 = new CopyInstruction(3, 4);
		Instruction i3 = new HaltInstruction();

		computer.addInstruction(i1);
		computer.addInstruction(i2);
		computer.addInstruction(i3);

		assertEquals(3, computer.getInstructions().size());
		assertEquals(i1, computer.getInstructions().get(0));
		assertEquals(i2, computer.getInstructions().get(1));
		assertEquals(i3, computer.getInstructions().get(2));
	}

	@Test
	public void testAddInstructionsWithIndex() {
		Instruction i1 = new MoveInstruction(1, 2);
		Instruction i2 = new CopyInstruction(3, 4);
		Instruction i3 = new HaltInstruction();

		try {
			computer.addInstruction(0, i1);
		} catch (IndexOutOfBoundsException e) {
			fail();
		}

		try {
			computer.addInstruction(-1, i2);
			fail();
		} catch (IndexOutOfBoundsException e) {
			// Expected
		}

		try {
			computer.addInstruction(2, i2);
			fail();
		} catch (IndexOutOfBoundsException e) {
			// Expected
		}

		try {
			computer.addInstruction(1, i3);
		} catch (IndexOutOfBoundsException e) {
			fail();
		}

		try {
			computer.addInstruction(1, i2);
		} catch (IndexOutOfBoundsException e) {
			fail();
		}

		assertEquals(3, computer.getInstructions().size());
		assertEquals(i1, computer.getInstructions().get(0));
		assertEquals(i2, computer.getInstructions().get(1));
		assertEquals(i3, computer.getInstructions().get(2));
	}


	@Test
	public void testSetInstructionsValid() {
		Instruction i1 = new MoveInstruction(1, 2);
		Instruction i2 = new CopyInstruction(3, 4);
		Instruction i3 = new HaltInstruction();

		computer.addInstruction(i1);
		computer.addInstruction(i3);
		computer.addInstruction(i3);

		computer.setInstruction(1, i2);

		assertEquals(3, computer.getInstructions().size());
		assertEquals(i1, computer.getInstructions().get(0));
		assertEquals(i2, computer.getInstructions().get(1));
		assertEquals(i3, computer.getInstructions().get(2));
	}

	@Test
	public void testSetInstructionsOutOfBounds() {
		Instruction i1 = new MoveInstruction(1, 2);
		Instruction i2 = new CopyInstruction(3, 4);
		Instruction i3 = new HaltInstruction();

		computer.addInstruction(i1);
		computer.addInstruction(i3);
		computer.addInstruction(i3);

		try {
			computer.setInstruction(-1, i2);
			fail();
		} catch (IndexOutOfBoundsException e) {
			// Expected
		}

		try {
			computer.setInstruction(3, i2);
			fail();
		} catch (IndexOutOfBoundsException e) {
			// Expected
		}
	}

	@Test
	public void testRemoveInstructionsValid() {
		Instruction i1 = new MoveInstruction(1, 2);
		Instruction i2 = new CopyInstruction(3, 4);
		Instruction i3 = new HaltInstruction();

		computer.addInstruction(i1);
		computer.addInstruction(i2);
		computer.addInstruction(i3);

		try {
			computer.removeInstruction(1);
		} catch (IndexOutOfBoundsException e) {
			fail();
		}

		assertEquals(2, computer.getInstructions().size());
		assertEquals(i1, computer.getInstructions().get(0));
		assertEquals(i3, computer.getInstructions().get(1));
	}

	@Test
	public void testRemoveInstructionsOutOfBounds() {
		Instruction i1 = new MoveInstruction(1, 2);
		Instruction i3 = new HaltInstruction();

		computer.addInstruction(i1);
		computer.addInstruction(i3);
		computer.addInstruction(i3);

		try {
			computer.removeInstruction(-1);
			fail();
		} catch (IndexOutOfBoundsException e) {
			// Expected
		}

		try {
			computer.removeInstruction(3);
			fail();
		} catch (IndexOutOfBoundsException e) {
			// Expected
		}
	}
}
