package net.moctave.bitwise.model.instructions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestHaltInstruction {
	private HaltInstruction instruction;
	
	@BeforeEach
	public void init() {
		instruction = new HaltInstruction();
	}

	@Test
	public void testConstructor() {
		assertEquals(0x0, instruction.getOpCode());
		assertEquals(0x0, instruction.getFnCode());
		assertEquals(0x0, instruction.getRegA());
		assertEquals(0x0, instruction.getRegB());
		assertEquals(0, instruction.getValC());
		assertEquals(null, instruction.getLabel());
		assertFalse(instruction.isLabel());
	}

	@Test
	public void testAsBytes() {
		List<Byte> bytes = instruction.asBytes();

		assertEquals(1, bytes.size());
		assertEquals((byte) 0x00, bytes.get(0));
	}

	@Test
	public void testToString() {
		assertEquals("halt", instruction.toString());
	}

	@Test
	public void testSize() {
		assertEquals(0, instruction.size());
	}
}
