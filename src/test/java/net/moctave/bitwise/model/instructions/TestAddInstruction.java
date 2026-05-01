package net.moctave.bitwise.model.instructions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestAddInstruction {
	private AddInstruction instructionLowerBound;
	private AddInstruction instructionMiddle;
	private AddInstruction instructionUpperBound;
	
	@BeforeEach
	public void init() {
		instructionLowerBound = new AddInstruction(0x1, 0xF);
		instructionMiddle = new AddInstruction(0x8, 0x7);
		instructionUpperBound = new AddInstruction(0xF, 0x1);
	}

	@Test
	public void testConstructorLowerBound() {
		assertEquals(0x2, instructionLowerBound.getOpCode());
		assertEquals(0x1, instructionLowerBound.getFnCode());
		assertEquals(0x1, instructionLowerBound.getRegA());
		assertEquals(0xF, instructionLowerBound.getRegB());
		assertEquals(0x0, instructionLowerBound.getValC());
		assertEquals(null, instructionLowerBound.getLabel());
		assertFalse(instructionLowerBound.isLabel());
	}

	@Test
	public void testConstructorMiddle() {
		assertEquals(0x2, instructionLowerBound.getOpCode());
		assertEquals(0x1, instructionMiddle.getFnCode());
		assertEquals(0x8, instructionMiddle.getRegA());
		assertEquals(0x7, instructionMiddle.getRegB());
		assertEquals(0x0, instructionMiddle.getValC());
		assertEquals(null, instructionMiddle.getLabel());
		assertFalse(instructionMiddle.isLabel());
	}

	@Test
	public void testConstructorUpperBound() {
		assertEquals(0x2, instructionUpperBound.getOpCode());
		assertEquals(0x1, instructionUpperBound.getFnCode());
		assertEquals(0xF, instructionUpperBound.getRegA());
		assertEquals(0x1, instructionUpperBound.getRegB());
		assertEquals(0x0, instructionUpperBound.getValC());
		assertEquals(null, instructionUpperBound.getLabel());
		assertFalse(instructionUpperBound.isLabel());
	}

	@Test
	public void testAsBytesLowerBound() {
		List<Byte> bytes = instructionLowerBound.asBytes();

		assertEquals(2, bytes.size());
		assertEquals((byte) 0x21, bytes.get(0));
		assertEquals((byte) 0x1F, bytes.get(1));
	}


	@Test
	public void testAsBytesMiddle() {
		List<Byte> bytes = instructionMiddle.asBytes();

		assertEquals(2, bytes.size());
		assertEquals((byte) 0x21, bytes.get(0));
		assertEquals((byte) 0x87, bytes.get(1));
	}


	@Test
	public void testAsBytesUpperBound() {
		List<Byte> bytes = instructionUpperBound.asBytes();

		assertEquals(2, bytes.size());
		assertEquals((byte) 0x21, bytes.get(0));
		assertEquals((byte) 0xF1, bytes.get(1));
	}

	@Test
	public void testToStringLowerBound() {
		assertEquals("add r1 rF", instructionLowerBound.toString());
	}

	@Test
	public void testToStringMiddle() {
		assertEquals("add r8 r7", instructionMiddle.toString());
	}

	@Test
	public void testToStringUpperBound() {
		assertEquals("add rF r1", instructionUpperBound.toString());
	}

	@Test
	public void testSize() {
		assertEquals(2, instructionLowerBound.size());
		assertEquals(2, instructionMiddle.size());
		assertEquals(2, instructionUpperBound.size());
	}
}
