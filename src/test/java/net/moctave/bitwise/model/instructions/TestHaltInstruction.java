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
