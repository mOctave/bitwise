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

package net.moctave.bitwise.model.instructions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestNotInstruction {
	private NotInstruction instructionLowerBound;
	private NotInstruction instructionMiddle;
	private NotInstruction instructionUpperBound;
	
	@BeforeEach
	public void init() {
		instructionLowerBound = new NotInstruction(0x1);
		instructionMiddle = new NotInstruction(0x8);
		instructionUpperBound = new NotInstruction(0xF);
	}

	@Test
	public void testConstructorLowerBound() {
		assertEquals(0x3, instructionLowerBound.getOpCode());
		assertEquals(0x2, instructionLowerBound.getFnCode());
		assertEquals(0x1, instructionLowerBound.getRegA());
		assertEquals(0x0, instructionLowerBound.getRegB());
		assertEquals(0x0, instructionLowerBound.getValC());
		assertEquals(null, instructionLowerBound.getLabel());
		assertFalse(instructionLowerBound.isLabel());
	}

	@Test
	public void testConstructorMiddle() {
		assertEquals(0x3, instructionLowerBound.getOpCode());
		assertEquals(0x2, instructionMiddle.getFnCode());
		assertEquals(0x8, instructionMiddle.getRegA());
		assertEquals(0x0, instructionMiddle.getRegB());
		assertEquals(0x0, instructionMiddle.getValC());
		assertEquals(null, instructionMiddle.getLabel());
		assertFalse(instructionMiddle.isLabel());
	}

	@Test
	public void testConstructorUpperBound() {
		assertEquals(0x3, instructionUpperBound.getOpCode());
		assertEquals(0x2, instructionUpperBound.getFnCode());
		assertEquals(0xF, instructionUpperBound.getRegA());
		assertEquals(0x0, instructionUpperBound.getRegB());
		assertEquals(0x0, instructionUpperBound.getValC());
		assertEquals(null, instructionUpperBound.getLabel());
		assertFalse(instructionUpperBound.isLabel());
	}

	@Test
	public void testAsBytesLowerBound() {
		List<Byte> bytes = instructionLowerBound.asBytes();

		assertEquals(2, bytes.size());
		assertEquals((byte) 0x32, bytes.get(0));
		assertEquals((byte) 0x10, bytes.get(1));
	}


	@Test
	public void testAsBytesMiddle() {
		List<Byte> bytes = instructionMiddle.asBytes();

		assertEquals(2, bytes.size());
		assertEquals((byte) 0x32, bytes.get(0));
		assertEquals((byte) 0x80, bytes.get(1));
	}


	@Test
	public void testAsBytesUpperBound() {
		List<Byte> bytes = instructionUpperBound.asBytes();

		assertEquals(2, bytes.size());
		assertEquals((byte) 0x32, bytes.get(0));
		assertEquals((byte) 0xF0, bytes.get(1));
	}

	@Test
	public void testToStringLowerBound() {
		assertEquals("not r1", instructionLowerBound.toString());
	}

	@Test
	public void testToStringMiddle() {
		assertEquals("not r8", instructionMiddle.toString());
	}

	@Test
	public void testToStringUpperBound() {
		assertEquals("not rF", instructionUpperBound.toString());
	}

	@Test
	public void testSize() {
		assertEquals(2, instructionLowerBound.size());
		assertEquals(2, instructionMiddle.size());
		assertEquals(2, instructionUpperBound.size());
	}
}
