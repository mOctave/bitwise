package net.moctave.bitwise.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestRegister {
	private Register flagRegister;
	private Register fourBitRegister;
	private Register thirtyTwoBitRegister;

	@BeforeEach
	public void init() {
		flagRegister = new Register("Flag", 1);
		fourBitRegister = new Register("Pointer to Register", 4);
		thirtyTwoBitRegister = new Register("Int Value", 32);
	}

	@Test
	public void testConstructor() {
		assertEquals("Flag", flagRegister.getName());
		assertEquals(1, flagRegister.getSize());
		assertEquals(0, flagRegister.getValue());

		assertEquals("Pointer to Register", fourBitRegister.getName());
		assertEquals(4, fourBitRegister.getSize());
		assertEquals(0, fourBitRegister.getValue());

		assertEquals("Int Value", thirtyTwoBitRegister.getName());
		assertEquals(32, thirtyTwoBitRegister.getSize());
		assertEquals(0, thirtyTwoBitRegister.getValue());
	}

	@Test
	public void testSetValueTypical() {
		assertEquals(0, fourBitRegister.getValue());

		fourBitRegister.setValue(-17);

		assertEquals(-17, fourBitRegister.getValue());
	}

	@Test
	public void testSetValueBoundary() {
		assertEquals(0, thirtyTwoBitRegister.getValue());

		thirtyTwoBitRegister.setValue(Integer.MAX_VALUE);

		assertEquals(Integer.MAX_VALUE, thirtyTwoBitRegister.getValue());

		thirtyTwoBitRegister.setValue(Integer.MIN_VALUE);

		assertEquals(Integer.MIN_VALUE, thirtyTwoBitRegister.getValue());
	}

	@Test
	public void testSetValueApparentlyOutOfRange() {
		assertEquals(0, flagRegister.getValue());

		// NOTE: flagRegister has an *apparent size* of 1 bit, but should still
		// store larger values
		flagRegister.setValue(35);

		assertEquals(35, flagRegister.getValue());
	}

	@Test
	public void testValueAsStringZero() {
		assertEquals("0", flagRegister.valueAsString());
		assertEquals("0", fourBitRegister.valueAsString());
		assertEquals("00000000", thirtyTwoBitRegister.valueAsString());
	}

	@Test
	public void testValueAsStringUpperBound() {
		flagRegister.setValue(1);
		fourBitRegister.setValue(15);
		thirtyTwoBitRegister.setValue(-1);

		assertEquals("1", flagRegister.valueAsString());
		assertEquals("F", fourBitRegister.valueAsString());
		assertEquals("FFFFFFFF", thirtyTwoBitRegister.valueAsString());
	}

	@Test
	public void testValueAsStringTypical() {
		fourBitRegister.setValue(7);
		thirtyTwoBitRegister.setValue(1398);

		assertEquals("7", fourBitRegister.valueAsString());
		assertEquals("00000576", thirtyTwoBitRegister.valueAsString());
	}

	@Test
	public void testValueAsStringOutOfRange() {
		flagRegister.setValue(3); // 0b1(1)
		fourBitRegister.setValue(18); // 0b1(0010)

		assertEquals("1", flagRegister.valueAsString());
		assertEquals("2", fourBitRegister.valueAsString());
	}
}
