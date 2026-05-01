package net.moctave.bitwise.model.instructions;

import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestJumpLessEqualsInstruction {
	private JumpLessEqualsInstruction instruction;
	private JumpLessEqualsInstruction instructionTwo;
	private JumpLessEqualsInstruction instructionThree;
	private Map<String, Integer> labels;
	
	@BeforeEach
	public void init() {
		instruction = new JumpLessEqualsInstruction("Label 1");
		instructionTwo = new JumpLessEqualsInstruction("Label 2");
		instructionThree = new JumpLessEqualsInstruction("Label 3");
		labels = new HashMap<>();
		labels.put("Label 1", 0);
		labels.put("Label 2", 193);
	}

	@Test
	public void testConstructor() {
		assertEquals(0x4, instruction.getOpCode());
		assertEquals(0x2, instruction.getFnCode());
		assertEquals(0x0, instruction.getRegA());
		assertEquals(0x0, instruction.getRegB());
		assertEquals(0, instruction.getValC());
		assertEquals("Label 1", instruction.getLabel());
		assertFalse(instruction.isLabel());

		assertFalse(instructionTwo.isLabel());
		assertEquals(0x4, instructionTwo.getOpCode());
		assertEquals(0x2, instructionTwo.getFnCode());
		assertEquals(0x0, instructionTwo.getRegA());
		assertEquals(0x0, instructionTwo.getRegB());
		assertEquals(0, instructionTwo.getValC());
		assertEquals("Label 2", instructionTwo.getLabel());

		assertFalse(instructionThree.isLabel());
		assertEquals(0x4, instructionThree.getOpCode());
		assertEquals(0x2, instructionThree.getFnCode());
		assertEquals(0x0, instructionThree.getRegA());
		assertEquals(0x0, instructionThree.getRegB());
		assertEquals(0, instructionThree.getValC());
		assertEquals("Label 3", instructionThree.getLabel());
	}

	@Test
	public void testUpdateAddressToMatchLabelBoundary() {
		try {
			instruction.updateAddressToMatchLabel(labels);
		} catch (Exception e) {
			fail();
		}

		assertFalse(instruction.isLabel());
		assertEquals(0x4, instruction.getOpCode());
		assertEquals(0x2, instruction.getFnCode());
		assertEquals(0x0, instruction.getRegA());
		assertEquals(0x0, instruction.getRegB());
		assertEquals(0, instruction.getValC());
		assertEquals("Label 1", instruction.getLabel());
	}

	@Test
	public void testUpdateAddressToMatchLabelTypical() {
		try {
			instructionTwo.updateAddressToMatchLabel(labels);
		} catch (Exception e) {
			fail();
		}

		assertFalse(instructionTwo.isLabel());
		assertEquals(0x4, instructionTwo.getOpCode());
		assertEquals(0x2, instructionTwo.getFnCode());
		assertEquals(0x0, instructionTwo.getRegA());
		assertEquals(0x0, instructionTwo.getRegB());
		assertEquals(193, instructionTwo.getValC());
		assertEquals("Label 2", instructionTwo.getLabel());
	}


	@Test
	public void testUpdateAddressToMatchLabelNotFound() {
		try {
			instructionThree.updateAddressToMatchLabel(labels);
			fail();
		} catch (Exception e) {
			assertFalse(instructionThree.isLabel());
			assertEquals(0x4, instructionThree.getOpCode());
			assertEquals(0x2, instructionThree.getFnCode());
			assertEquals(0x0, instructionThree.getRegA());
			assertEquals(0x0, instructionThree.getRegB());
			assertEquals(0, instructionThree.getValC());
			assertEquals("Label 3", instructionThree.getLabel());
		}
	}

	@Test
	public void testAsBytesNotUpdated() {
		List<Byte> bytes = instructionTwo.asBytes();

		assertEquals(5, bytes.size());
		assertEquals((byte) 0x42, bytes.get(0));
		assertEquals((byte) 0x00, bytes.get(1));
		assertEquals((byte) 0x00, bytes.get(2));
		assertEquals((byte) 0x00, bytes.get(3));
		assertEquals((byte) 0x00, bytes.get(4));
	}


	@Test
	public void testAsBytesUpdated() {
		try {
			instructionTwo.updateAddressToMatchLabel(labels);
		} catch (Exception e) {
			fail();
		}

		List<Byte> bytes = instructionTwo.asBytes();

		assertEquals(5, bytes.size());
		assertEquals((byte) 0x42, bytes.get(0));
		assertEquals((byte) 0x00, bytes.get(1));
		assertEquals((byte) 0x00, bytes.get(2));
		assertEquals((byte) 0x00, bytes.get(3));
		assertEquals((byte) 0xC1, bytes.get(4));
	}

	@Test
	public void testToString() {
		assertEquals("jle Label 1", instruction.toString());
		assertEquals("jle Label 3", instructionThree.toString());
	}

	@Test
	public void testSize() {
		assertEquals(5, instruction.size());
		assertEquals(5, instructionTwo.size());
		assertEquals(5, instructionThree.size());
	}
}
