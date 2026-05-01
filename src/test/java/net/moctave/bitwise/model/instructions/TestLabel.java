package net.moctave.bitwise.model.instructions;

import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestLabel {
	private Label instructionFoo;
	private Label instructionEmptyLabel;
	private Map<String, Integer> labels;
	
	@BeforeEach
	public void init() {
		instructionFoo = new Label("Foo");
		instructionEmptyLabel = new Label("");
		labels = new HashMap<>();
		labels.put("Foo", 38);
	}

	@Test
	public void testConstructorNormal() {
		assertEquals(0x5, instructionFoo.getOpCode());
		assertEquals(0x0, instructionFoo.getFnCode());
		assertEquals(0x0, instructionFoo.getRegA());
		assertEquals(0x0, instructionFoo.getRegB());
		assertEquals(0, instructionFoo.getValC());
		assertEquals("Foo", instructionFoo.getLabel());
		assertTrue(instructionFoo.isLabel());
	}

	@Test
	public void testConstructorEmpty() {
		assertEquals(0x5, instructionEmptyLabel.getOpCode());
		assertEquals(0x0, instructionEmptyLabel.getFnCode());
		assertEquals(0x0, instructionEmptyLabel.getRegA());
		assertEquals(0x0, instructionEmptyLabel.getRegB());
		assertEquals(0, instructionEmptyLabel.getValC());
		assertEquals("", instructionEmptyLabel.getLabel());
		assertTrue(instructionEmptyLabel.isLabel());
	}

	@Test
	public void testAsBytesNormal() {
		List<Byte> bytes = instructionFoo.asBytes();

		assertEquals(0, bytes.size());
	}

	@Test
	public void testAsBytesEmpty() {
		List<Byte> bytes = instructionEmptyLabel.asBytes();

		assertEquals(0, bytes.size());
	}

	@Test
	public void testToStringNormal() {
		assertEquals("Foo:", instructionFoo.toString());
	}

	@Test
	public void testToStringEmpty() {
		assertEquals(":", instructionEmptyLabel.toString());
	}

	@Test
	public void testSize() {
		assertEquals(0, instructionFoo.size());
		assertEquals(0, instructionEmptyLabel.size());
	}

	@Test
	public void testUpdateAddressToMatchLabel() {
		try {
			instructionFoo.updateAddressToMatchLabel(labels);
		} catch (Exception e) {
			fail();
		}

		assertEquals(0x5, instructionFoo.getOpCode());
		assertEquals(0x0, instructionFoo.getFnCode());
		assertEquals(0x0, instructionFoo.getRegA());
		assertEquals(0x0, instructionFoo.getRegB());
		assertEquals(0, instructionFoo.getValC());
		assertEquals("Foo", instructionFoo.getLabel());
		assertTrue(instructionFoo.isLabel());
	}
}
