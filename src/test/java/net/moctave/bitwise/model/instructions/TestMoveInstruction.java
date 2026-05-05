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

public class TestMoveInstruction {
	private MoveInstruction instructionLowerBound;
	private MoveInstruction instructionMiddle;
	private MoveInstruction instructionUpperBound;
	
	@BeforeEach
	public void init() {
		instructionLowerBound = new MoveInstruction(0x1, Integer.MIN_VALUE);
		instructionMiddle = new MoveInstruction(0x8, 0);
		instructionUpperBound = new MoveInstruction(0xF, Integer.MAX_VALUE);
	}

	@Test
	public void testConstructorLowerBound() {
		assertEquals(0x1, instructionLowerBound.getOpCode());
		assertEquals(0x0, instructionLowerBound.getFnCode());
		assertEquals(0x1, instructionLowerBound.getRegA());
		assertEquals(0x0, instructionLowerBound.getRegB());
		assertEquals(Integer.MIN_VALUE, instructionLowerBound.getValC());
		assertEquals(null, instructionLowerBound.getLabel());
		assertFalse(instructionLowerBound.isLabel());
	}

	@Test
	public void testConstructorMiddle() {
		assertEquals(0x1, instructionMiddle.getOpCode());
		assertEquals(0x0, instructionMiddle.getFnCode());
		assertEquals(0x8, instructionMiddle.getRegA());
		assertEquals(0x0, instructionMiddle.getRegB());
		assertEquals(0, instructionMiddle.getValC());
		assertEquals(null, instructionMiddle.getLabel());
		assertFalse(instructionMiddle.isLabel());
	}

	@Test
	public void testConstructorUpperBound() {
		assertEquals(0x1, instructionUpperBound.getOpCode());
		assertEquals(0x0, instructionUpperBound.getFnCode());
		assertEquals(0xF, instructionUpperBound.getRegA());
		assertEquals(0x0, instructionUpperBound.getRegB());
		assertEquals(Integer.MAX_VALUE, instructionUpperBound.getValC());
		assertEquals(null, instructionUpperBound.getLabel());
		assertFalse(instructionUpperBound.isLabel());
	}

	@Test
	public void testAsBytesLowerBound() {
		List<Byte> bytes = instructionLowerBound.asBytes();

		assertEquals(5, bytes.size());
		assertEquals((byte) 0x11, bytes.get(0));
		assertEquals((byte) 0x80, bytes.get(1));
		assertEquals((byte) 0x00, bytes.get(2));
		assertEquals((byte) 0x00, bytes.get(3));
		assertEquals((byte) 0x00, bytes.get(4));
	}


	@Test
	public void testAsBytesMiddle() {
		List<Byte> bytes = instructionMiddle.asBytes();

		assertEquals(5, bytes.size());
		assertEquals((byte) 0x18, bytes.get(0));
		assertEquals((byte) 0x00, bytes.get(1));
		assertEquals((byte) 0x00, bytes.get(2));
		assertEquals((byte) 0x00, bytes.get(3));
		assertEquals((byte) 0x00, bytes.get(4));
	}


	@Test
	public void testAsBytesUpperBound() {
		List<Byte> bytes = instructionUpperBound.asBytes();

		assertEquals(5, bytes.size());
		assertEquals((byte) 0x1F, bytes.get(0));
		assertEquals((byte) 0x7F, bytes.get(1));
		assertEquals((byte) 0xFF, bytes.get(2));
		assertEquals((byte) 0xFF, bytes.get(3));
		assertEquals((byte) 0xFF, bytes.get(4));
	}

	@Test
	public void testToStringLowerBound() {
		assertEquals("move r1 -2147483648", instructionLowerBound.toString());
	}

	@Test
	public void testToStringMiddle() {
		assertEquals("move r8 0", instructionMiddle.toString());
	}

	@Test
	public void testToStringUpperBound() {
		assertEquals("move rF 2147483647", instructionUpperBound.toString());
	}

	@Test
	public void testSize() {
		assertEquals(5, instructionLowerBound.size());
		assertEquals(5, instructionMiddle.size());
		assertEquals(5, instructionUpperBound.size());
	}
}
