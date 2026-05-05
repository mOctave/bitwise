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

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestInstruction {
	private Instruction halt;
	private Instruction move;
	private Instruction binary;
	private Instruction unary;
	private Map<String, Integer> labels;

	@BeforeEach
	public void init() {
		halt = new HaltInstruction();
		move = new MoveInstruction(3, 4);
		binary = new AddInstruction(5, 6);
		unary = new NegInstruction(7);
		labels = new HashMap<>();
		labels.put("A", 0);
		labels.put("B", 83);
		labels.put("C", 2719);
	}


	@Test
	public void testEqualsNull() {
		assertFalse(unary.equals(null));
	}

	@Test
	public void testEqualsNonInstruction() {
		assertFalse(unary.equals("Not an Instruction!"));
	}

	@Test
	public void testEqualsSelf() {
		assertTrue(unary.equals(unary));
	}

	@Test
	public void testEqualsSelfCopy() {
		assertTrue(unary.equals(new NegInstruction(7)));
	}

	@Test
	public void testEqualsDifferentOpCode() {
		assertFalse(unary.equals(halt));
	}

	@Test
	public void testEqualsDifferentFnCode() {
		assertFalse(binary.equals(new SubInstruction(5, 6)));
	}

	@Test
	public void testEqualsDifferentRegA() {
		assertFalse(binary.equals(new AddInstruction(4, 6)));
	}

	@Test
	public void testEqualsDifferentRegB() {
		assertFalse(binary.equals(new AddInstruction(5, 8)));
	}

	@Test
	public void testEqualsDifferentValC() {
		assertFalse(move.equals(new MoveInstruction(3, -1)));
	}

	@Test
	public void testEqualsDifferentLabel() {
		try {
			assertFalse(new Label("Test 1").equals(new Label("Test 2")));
			assertFalse(unary.equals(new Label("something")));
			assertFalse(new Label(null).equals(new Label("Non-Null")));
			assertFalse(new Label("Non-Null").equals(new Label(null)));
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testEqualsDifferentIsLabel() {
		Instruction fakeLabel = new Instruction(5,0,0,0,0, "Test 3", false) {
			public String toString() {
				return null;
			}

			public List<Byte> asBytes() {
				return null;
			}

			public int size() {
				return 0;
			}
		};
		assertFalse(fakeLabel.equals(new Label("Test 3")));
		assertFalse(new Label("Test 3").equals(fakeLabel));
	}

	// I do not care what value the hash code gives, so long as it is the same for
	// objects that are equal.
	@Test
	public void testHashCodeSelf() {
		assertEquals(unary.hashCode(), unary.hashCode());
	}

	@Test
	public void testHashCodeEqual() {
		assertEquals(unary.hashCode(), new NegInstruction(7).hashCode());
	}


	@Test
	public void testHashCodeEqualLabel() {
		assertEquals(new Label("Foo").hashCode(), new Label("Foo").hashCode());
	}

	// All other test cases for all methods (including this one) can be found in
	// the test classes for subclasses of Instruction.
	// Other cases for the updateAddressToMatchLabel method can be found in test
	// classes for Label and the subclasses of JumpInstruction, since they are the 
	// only classes that should ever have a non-null label.
	@Test
	public void testUpdateAddressToMatchLabelNullLabel() {
		try {
			halt.updateAddressToMatchLabel(labels);
			move.updateAddressToMatchLabel(labels);
			binary.updateAddressToMatchLabel(labels);
			unary.updateAddressToMatchLabel(labels);
		} catch (Exception e) {
			fail();
		}

		assertEquals(0, halt.getValC());
		assertEquals(4, move.getValC());
		assertEquals(0, binary.getValC());
		assertEquals(0, unary.getValC());
	}
}
