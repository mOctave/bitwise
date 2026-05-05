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

package net.moctave.bitwise.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import net.moctave.bitwise.exceptions.InstructionParseException;
import net.moctave.bitwise.model.instructions.*;

public class TestInstructionParser {
	public void assertInstructionsEqual(Instruction desiredInstruction, Instruction actualInstruction) {
		assertEquals(desiredInstruction.getOpCode(), actualInstruction.getOpCode());
		assertEquals(desiredInstruction.getFnCode(), actualInstruction.getFnCode());
		assertEquals(desiredInstruction.getRegA(), actualInstruction.getRegA());
		assertEquals(desiredInstruction.getRegB(), actualInstruction.getRegB());
		assertEquals(desiredInstruction.getValC(), actualInstruction.getValC());
		assertEquals(desiredInstruction.isLabel(), actualInstruction.isLabel());
		assertEquals(desiredInstruction.getLabel(), actualInstruction.getLabel());
	}

	// MARK: Invalid
	@Test
	public void testConvertToInstructionInvalid() {
		try {
			InstructionParser.convertToInstruction("break stuff");
			fail();
		} catch (InstructionParseException e) {
			// PASS
		}
	}


	// MARK: Halt
	@Test
	public void testConvertToInstructionHaltNormal() {
		try {
			Instruction actualInstruction = InstructionParser.convertToInstruction("halt");
			Instruction desiredInstruction = new HaltInstruction();
			assertEquals(desiredInstruction.getOpCode(), actualInstruction.getOpCode());
			assertEquals(desiredInstruction.getFnCode(), actualInstruction.getFnCode());
			assertEquals(desiredInstruction.getRegA(), actualInstruction.getRegA());
			assertEquals(desiredInstruction.getRegB(), actualInstruction.getRegB());
			assertEquals(desiredInstruction.getValC(), actualInstruction.getValC());
			assertEquals(desiredInstruction.isLabel(), actualInstruction.isLabel());
			assertEquals(desiredInstruction.getLabel(), actualInstruction.getLabel());
		} catch (InstructionParseException e) {
			fail();
		}
	}

	@Test
	public void testConvertToInstructionHaltBadlyFormatted() {
		try {
			Instruction actualInstruction = InstructionParser.convertToInstruction("hAlT NOW!");
			Instruction desiredInstruction = new HaltInstruction();
			assertInstructionsEqual(desiredInstruction, actualInstruction);
		} catch (InstructionParseException e) {
			fail();
		}
	}

	// MARK: Move
	@Test
	public void testConvertToInstructionMoveNormal() {
		try {
			Instruction actualInstruction = InstructionParser.convertToInstruction("move r5 72");
			Instruction desiredInstruction = new MoveInstruction(5, 72);
			assertInstructionsEqual(desiredInstruction, actualInstruction);
		} catch (InstructionParseException e) {
			fail();
		}
	}

	@Test
	public void testConvertToInstructionMoveBadlyFormatted() {
		try {
			Instruction actualInstruction = InstructionParser.convertToInstruction("move r1 2 3 4");
			Instruction desiredInstruction = new MoveInstruction(1, 2);
			assertInstructionsEqual(desiredInstruction, actualInstruction);
		} catch (InstructionParseException e) {
			fail();
		}
	}

	@Test
	public void testConvertToInstructionMoveIncomplete() {
		try {
			InstructionParser.convertToInstruction("move");
			fail();
		} catch (InstructionParseException e) {
			// PASS
		}
	}

	@Test
	public void testConvertToInstructionMoveInvalidRegA() {
		try {
			InstructionParser.convertToInstruction("move rQ 12");
			fail();
		} catch (InstructionParseException e) {
			// PASS
		}
	}

	@Test
	public void testConvertToInstructionMoveShortRegA() {
		try {
			InstructionParser.convertToInstruction("move 6 7");
			fail();
		} catch (InstructionParseException e) {
			// PASS
		}
	}

	@Test
	public void testConvertToInstructionMoveInvalidValC() {
		try {
			InstructionParser.convertToInstruction("move r5 3.6");
			fail();
		} catch (InstructionParseException e) {
			// PASS
		}
	}

	// MARK: Copy
	@Test
	public void testConvertToInstructionCopyNormal() {
		try {
			Instruction actualInstruction = InstructionParser.convertToInstruction("copy r3 r4");
			Instruction desiredInstruction = new CopyInstruction(3, 4);
			assertInstructionsEqual(desiredInstruction, actualInstruction);
		} catch (InstructionParseException e) {
			fail();
		}
	}

	@Test
	public void testConvertToInstructionCopyBadlyFormatted() {
		try {
			Instruction actualInstruction = InstructionParser.convertToInstruction("cOpY R5 r6 r7 8");
			Instruction desiredInstruction = new CopyInstruction(5, 6);
			assertInstructionsEqual(desiredInstruction, actualInstruction);
		} catch (InstructionParseException e) {
			fail();
		}
	}

	@Test
	public void testConvertToInstructionCopyIncomplete() {
		try {
			InstructionParser.convertToInstruction("copy");
			fail();
		} catch (InstructionParseException e) {
			// PASS
		}
	}

	@Test
	public void testConvertToInstructionCopyInvalidRegA() {
		try {
			InstructionParser.convertToInstruction("copy r0 rB");
			fail();
		} catch (InstructionParseException e) {
			// PASS
		}
	}

	@Test
	public void testConvertToInstructionCopyShortRegA() {
		try {
			InstructionParser.convertToInstruction("copy 6 r3");
			fail();
		} catch (InstructionParseException e) {
			// PASS
		}
	}

	@Test
	public void testConvertToInstructionCopyInvalidRegB() {
		try {
			InstructionParser.convertToInstruction("copy r2 rZ");
			fail();
		} catch (InstructionParseException e) {
			// PASS
		}
	}

	@Test
	public void testConvertToInstructionCopyShortRegB() {
		try {
			InstructionParser.convertToInstruction("copy r6 3");
			fail();
		} catch (InstructionParseException e) {
			// PASS
		}
	}

	// MARK: Add
	@Test
	public void testConvertToInstructionAddNormal() {
		try {
			Instruction actualInstruction = InstructionParser.convertToInstruction("add r3 r4");
			Instruction desiredInstruction = new AddInstruction(3, 4);
			assertInstructionsEqual(desiredInstruction, actualInstruction);
		} catch (InstructionParseException e) {
			fail();
		}
	}

	@Test
	public void testConvertToInstructionAddBadlyFormatted() {
		try {
			Instruction actualInstruction = InstructionParser.convertToInstruction("ADD R5 r6 r7 8");
			Instruction desiredInstruction = new AddInstruction(5, 6);
			assertInstructionsEqual(desiredInstruction, actualInstruction);
		} catch (InstructionParseException e) {
			fail();
		}
	}

	@Test
	public void testConvertToInstructionAddIncomplete() {
		try {
			InstructionParser.convertToInstruction("add");
			fail();
		} catch (InstructionParseException e) {
			// PASS
		}
	}

	@Test
	public void testConvertToInstructionAddInvalidRegA() {
		try {
			InstructionParser.convertToInstruction("add r0 rB");
			fail();
		} catch (InstructionParseException e) {
			// PASS
		}
	}

	@Test
	public void testConvertToInstructionAddShortRegA() {
		try {
			InstructionParser.convertToInstruction("add 6 r3");
			fail();
		} catch (InstructionParseException e) {
			// PASS
		}
	}

	@Test
	public void testConvertToInstructionAddInvalidRegB() {
		try {
			InstructionParser.convertToInstruction("add r2 rZ");
			fail();
		} catch (InstructionParseException e) {
			// PASS
		}
	}

	@Test
	public void testConvertToInstructionCopyAddRegB() {
		try {
			InstructionParser.convertToInstruction("add r6 3");
			fail();
		} catch (InstructionParseException e) {
			// PASS
		}
	}

	// MARK: Sub
	@Test
	public void testConvertToInstructionSubNormal() {
		try {
			Instruction actualInstruction = InstructionParser.convertToInstruction("sub r3 r4");
			Instruction desiredInstruction = new SubInstruction(3, 4);
			assertInstructionsEqual(desiredInstruction, actualInstruction);
		} catch (InstructionParseException e) {
			fail();
		}
	}

	@Test
	public void testConvertToInstructionSubBadlyFormatted() {
		try {
			Instruction actualInstruction = InstructionParser.convertToInstruction("Sub R5 r6 r7 8");
			Instruction desiredInstruction = new SubInstruction(5, 6);
			assertInstructionsEqual(desiredInstruction, actualInstruction);
		} catch (InstructionParseException e) {
			fail();
		}
	}

	@Test
	public void testConvertToInstructionSubIncomplete() {
		try {
			InstructionParser.convertToInstruction("sub");
			fail();
		} catch (InstructionParseException e) {
			// PASS
		}
	}

	@Test
	public void testConvertToInstructionSubInvalidRegA() {
		try {
			InstructionParser.convertToInstruction("sub r0 rB");
			fail();
		} catch (InstructionParseException e) {
			// PASS
		}
	}

	@Test
	public void testConvertToInstructionSubShortRegA() {
		try {
			InstructionParser.convertToInstruction("sub 6 r3");
			fail();
		} catch (InstructionParseException e) {
			// PASS
		}
	}

	@Test
	public void testConvertToInstructionSubInvalidRegB() {
		try {
			InstructionParser.convertToInstruction("sub r2 rZ");
			fail();
		} catch (InstructionParseException e) {
			// PASS
		}
	}

	@Test
	public void testConvertToInstructionSubShortRegB() {
		try {
			InstructionParser.convertToInstruction("sub r6 3");
			fail();
		} catch (InstructionParseException e) {
			// PASS
		}
	}

	// MARK: And
	@Test
	public void testConvertToInstructionAndNormal() {
		try {
			Instruction actualInstruction = InstructionParser.convertToInstruction("and r3 r4");
			Instruction desiredInstruction = new AndInstruction(3, 4);
			assertInstructionsEqual(desiredInstruction, actualInstruction);
		} catch (InstructionParseException e) {
			fail();
		}
	}

	@Test
	public void testConvertToInstructionAndBadlyFormatted() {
		try {
			Instruction actualInstruction = InstructionParser.convertToInstruction("aNd R5 r6 r7 8");
			Instruction desiredInstruction = new AndInstruction(5, 6);
			assertInstructionsEqual(desiredInstruction, actualInstruction);
		} catch (InstructionParseException e) {
			fail();
		}
	}

	@Test
	public void testConvertToInstructionAndIncomplete() {
		try {
			InstructionParser.convertToInstruction("and");
			fail();
		} catch (InstructionParseException e) {
			// PASS
		}
	}

	@Test
	public void testConvertToInstructionAndInvalidRegA() {
		try {
			InstructionParser.convertToInstruction("and r0 rB");
			fail();
		} catch (InstructionParseException e) {
			// PASS
		}
	}

	@Test
	public void testConvertToInstructionAndShortRegA() {
		try {
			InstructionParser.convertToInstruction("and 6 r3");
			fail();
		} catch (InstructionParseException e) {
			// PASS
		}
	}

	@Test
	public void testConvertToInstructionAndInvalidRegB() {
		try {
			InstructionParser.convertToInstruction("and r2 rZ");
			fail();
		} catch (InstructionParseException e) {
			// PASS
		}
	}

	@Test
	public void testConvertToInstructionAndShortRegB() {
		try {
			InstructionParser.convertToInstruction("and r6 3");
			fail();
		} catch (InstructionParseException e) {
			// PASS
		}
	}

	// MARK: Or
	@Test
	public void testConvertToInstructionOrNormal() {
		try {
			Instruction actualInstruction = InstructionParser.convertToInstruction("or r3 r4");
			Instruction desiredInstruction = new OrInstruction(3, 4);
			assertInstructionsEqual(desiredInstruction, actualInstruction);
		} catch (InstructionParseException e) {
			fail();
		}
	}

	@Test
	public void testConvertToInstructionOrBadlyFormatted() {
		try {
			Instruction actualInstruction = InstructionParser.convertToInstruction("OR R5 r6 r7 8");
			Instruction desiredInstruction = new OrInstruction(5, 6);
			assertInstructionsEqual(desiredInstruction, actualInstruction);
		} catch (InstructionParseException e) {
			fail();
		}
	}

	@Test
	public void testConvertToInstructionOrIncomplete() {
		try {
			InstructionParser.convertToInstruction("or");
			fail();
		} catch (InstructionParseException e) {
			// PASS
		}
	}

	@Test
	public void testConvertToInstructionOrInvalidRegA() {
		try {
			InstructionParser.convertToInstruction("or r0 rB");
			fail();
		} catch (InstructionParseException e) {
			// PASS
		}
	}

	@Test
	public void testConvertToInstructionOrShortRegA() {
		try {
			InstructionParser.convertToInstruction("or 6 r3");
			fail();
		} catch (InstructionParseException e) {
			// PASS
		}
	}

	@Test
	public void testConvertToInstructionOrInvalidRegB() {
		try {
			InstructionParser.convertToInstruction("or r2 rZ");
			fail();
		} catch (InstructionParseException e) {
			// PASS
		}
	}

	@Test
	public void testConvertToInstructionOrShortRegB() {
		try {
			InstructionParser.convertToInstruction("or r6 3");
			fail();
		} catch (InstructionParseException e) {
			// PASS
		}
	}

	// MARK: Xor
	@Test
	public void testConvertToInstructionXorNormal() {
		try {
			Instruction actualInstruction = InstructionParser.convertToInstruction("xor r3 r4");
			Instruction desiredInstruction = new XorInstruction(3, 4);
			assertInstructionsEqual(desiredInstruction, actualInstruction);
		} catch (InstructionParseException e) {
			fail();
		}
	}

	@Test
	public void testConvertToInstructionXorBadlyFormatted() {
		try {
			Instruction actualInstruction = InstructionParser.convertToInstruction("xoR R5 r6 r7 8");
			Instruction desiredInstruction = new XorInstruction(5, 6);
			assertInstructionsEqual(desiredInstruction, actualInstruction);
		} catch (InstructionParseException e) {
			fail();
		}
	}

	@Test
	public void testConvertToInstructionXorIncomplete() {
		try {
			InstructionParser.convertToInstruction("xor");
			fail();
		} catch (InstructionParseException e) {
			// PASS
		}
	}

	@Test
	public void testConvertToInstructionXorInvalidRegA() {
		try {
			InstructionParser.convertToInstruction("xor r0 rB");
			fail();
		} catch (InstructionParseException e) {
			// PASS
		}
	}

	@Test
	public void testConvertToInstructionXorShortRegA() {
		try {
			InstructionParser.convertToInstruction("xor 6 r3");
			fail();
		} catch (InstructionParseException e) {
			// PASS
		}
	}

	@Test
	public void testConvertToInstructionXorInvalidRegB() {
		try {
			InstructionParser.convertToInstruction("xor r2 rZ");
			fail();
		} catch (InstructionParseException e) {
			// PASS
		}
	}

	@Test
	public void testConvertToInstructionXorShortRegB() {
		try {
			InstructionParser.convertToInstruction("xor r6 3");
			fail();
		} catch (InstructionParseException e) {
			// PASS
		}
	}

	// MARK: Inc
	@Test
	public void testConvertToInstructionIncNormal() {
		try {
			Instruction actualInstruction = InstructionParser.convertToInstruction("inc rA");
			Instruction desiredInstruction = new IncInstruction(10);
			assertInstructionsEqual(desiredInstruction, actualInstruction);
		} catch (InstructionParseException e) {
			fail();
		}
	}

	@Test
	public void testConvertToInstructionIncBadlyFormatted() {
		try {
			Instruction actualInstruction = InstructionParser.convertToInstruction("inC Re");
			Instruction desiredInstruction = new IncInstruction(14);
			assertInstructionsEqual(desiredInstruction, actualInstruction);
		} catch (InstructionParseException e) {
			fail();
		}
	}

	@Test
	public void testConvertToInstructionIncIncomplete() {
		try {
			InstructionParser.convertToInstruction("inc");
			fail();
		} catch (InstructionParseException e) {
			// PASS
		}
	}

	@Test
	public void testConvertToInstructionIncInvalidRegA() {
		try {
			InstructionParser.convertToInstruction("inc ré");
			fail();
		} catch (InstructionParseException e) {
			// PASS
		}
	}

	@Test
	public void testConvertToInstructionIncShortRegA() {
		try {
			InstructionParser.convertToInstruction("inc 8");
			fail();
		} catch (InstructionParseException e) {
			// PASS
		}
	}

	// MARK: Neg
	@Test
	public void testConvertToInstructionNegNormal() {
		try {
			Instruction actualInstruction = InstructionParser.convertToInstruction("neg rA");
			Instruction desiredInstruction = new NegInstruction(10);
			assertInstructionsEqual(desiredInstruction, actualInstruction);
		} catch (InstructionParseException e) {
			fail();
		}
	}

	@Test
	public void testConvertToInstructionNegBadlyFormatted() {
		try {
			Instruction actualInstruction = InstructionParser.convertToInstruction("Neg Re");
			Instruction desiredInstruction = new NegInstruction(14);
			assertInstructionsEqual(desiredInstruction, actualInstruction);
		} catch (InstructionParseException e) {
			fail();
		}
	}

	@Test
	public void testConvertToInstructionNegIncomplete() {
		try {
			InstructionParser.convertToInstruction("neg");
			fail();
		} catch (InstructionParseException e) {
			// PASS
		}
	}

	@Test
	public void testConvertToInstructionNegInvalidRegA() {
		try {
			InstructionParser.convertToInstruction("neg ré");
			fail();
		} catch (InstructionParseException e) {
			// PASS
		}
	}

	@Test
	public void testConvertToInstructionNegShortRegA() {
		try {
			InstructionParser.convertToInstruction("neg 8");
			fail();
		} catch (InstructionParseException e) {
			// PASS
		}
	}

	// MARK: Not
	@Test
	public void testConvertToInstructionNotNormal() {
		try {
			Instruction actualInstruction = InstructionParser.convertToInstruction("not rA");
			Instruction desiredInstruction = new NotInstruction(10);
			assertInstructionsEqual(desiredInstruction, actualInstruction);
		} catch (InstructionParseException e) {
			fail();
		}
	}

	@Test
	public void testConvertToInstructionNotBadlyFormatted() {
		try {
			Instruction actualInstruction = InstructionParser.convertToInstruction("nOT Re");
			Instruction desiredInstruction = new NotInstruction(14);
			assertInstructionsEqual(desiredInstruction, actualInstruction);
		} catch (InstructionParseException e) {
			fail();
		}
	}

	@Test
	public void testConvertToInstructionNotIncomplete() {
		try {
			InstructionParser.convertToInstruction("not");
			fail();
		} catch (InstructionParseException e) {
			// PASS
		}
	}

	@Test
	public void testConvertToInstructionNotInvalidRegA() {
		try {
			InstructionParser.convertToInstruction("not ré");
			fail();
		} catch (InstructionParseException e) {
			// PASS
		}
	}

	@Test
	public void testConvertToInstructionNotShortRegA() {
		try {
			InstructionParser.convertToInstruction("not 8");
			fail();
		} catch (InstructionParseException e) {
			// PASS
		}
	}



	// MARK: Jump
	@Test
	public void testConvertToInstructionJumpNormal() {
		try {
			Instruction actualInstruction = InstructionParser.convertToInstruction("jump label");
			Instruction desiredInstruction = new JumpAlwaysInstruction("label");
			assertInstructionsEqual(desiredInstruction, actualInstruction);
		} catch (InstructionParseException e) {
			fail();
		}
	}

	@Test
	public void testConvertToInstructionJumpBadlyFormatted() {
		try {
			Instruction actualInstruction = InstructionParser.convertToInstruction("jUmP label 35");
			Instruction desiredInstruction = new JumpAlwaysInstruction("label 35");
			assertInstructionsEqual(desiredInstruction, actualInstruction);
		} catch (InstructionParseException e) {
			fail();
		}
	}

	@Test
	public void testConvertToInstructionJumpIncomplete() {
		try {
			InstructionParser.convertToInstruction("jump");
			fail();
		} catch (InstructionParseException e) {
			// PASS
		}
	}



	// MARK: Je
	@Test
	public void testConvertToInstructionJeNormal() {
		try {
			Instruction actualInstruction = InstructionParser.convertToInstruction("je label");
			Instruction desiredInstruction = new JumpEqualsInstruction("label");
			assertInstructionsEqual(desiredInstruction, actualInstruction);
		} catch (InstructionParseException e) {
			fail();
		}
	}

	@Test
	public void testConvertToInstructionJeBadlyFormatted() {
		try {
			Instruction actualInstruction = InstructionParser.convertToInstruction("JE label 35");
			Instruction desiredInstruction = new JumpEqualsInstruction("label 35");
			assertInstructionsEqual(desiredInstruction, actualInstruction);
		} catch (InstructionParseException e) {
			fail();
		}
	}

	@Test
	public void testConvertToInstructionJeIncomplete() {
		try {
			InstructionParser.convertToInstruction("je");
			fail();
		} catch (InstructionParseException e) {
			// PASS
		}
	}



	// MARK: Jle
	@Test
	public void testConvertToInstructionJleNormal() {
		try {
			Instruction actualInstruction = InstructionParser.convertToInstruction("jle label");
			Instruction desiredInstruction = new JumpLessEqualsInstruction("label");
			assertInstructionsEqual(desiredInstruction, actualInstruction);
		} catch (InstructionParseException e) {
			fail();
		}
	}

	@Test
	public void testConvertToInstructionJleBadlyFormatted() {
		try {
			Instruction actualInstruction = InstructionParser.convertToInstruction("JLE label 35");
			Instruction desiredInstruction = new JumpLessEqualsInstruction("label 35");
			assertInstructionsEqual(desiredInstruction, actualInstruction);
		} catch (InstructionParseException e) {
			fail();
		}
	}

	@Test
	public void testConvertToInstructionJleIncomplete() {
		try {
			InstructionParser.convertToInstruction("jle");
			fail();
		} catch (InstructionParseException e) {
			// PASS
		}
	}



	// MARK: Jge
	@Test
	public void testConvertToInstructionJgeNormal() {
		try {
			Instruction actualInstruction = InstructionParser.convertToInstruction("jge label");
			Instruction desiredInstruction = new JumpGreaterEqualsInstruction("label");
			assertInstructionsEqual(desiredInstruction, actualInstruction);
		} catch (InstructionParseException e) {
			fail();
		}
	}

	@Test
	public void testConvertToInstructionJgeBadlyFormatted() {
		try {
			Instruction actualInstruction = InstructionParser.convertToInstruction("jGe label 35");
			Instruction desiredInstruction = new JumpGreaterEqualsInstruction("label 35");
			assertInstructionsEqual(desiredInstruction, actualInstruction);
		} catch (InstructionParseException e) {
			fail();
		}
	}

	@Test
	public void testConvertToInstructionJgeIncomplete() {
		try {
			InstructionParser.convertToInstruction("jge");
			fail();
		} catch (InstructionParseException e) {
			// PASS
		}
	}



	// MARK: Jne
	@Test
	public void testConvertToInstructionJneNormal() {
		try {
			Instruction actualInstruction = InstructionParser.convertToInstruction("jne label");
			Instruction desiredInstruction = new JumpNotEqualsInstruction("label");
			assertInstructionsEqual(desiredInstruction, actualInstruction);
		} catch (InstructionParseException e) {
			fail();
		}
	}

	@Test
	public void testConvertToInstructionJneBadlyFormatted() {
		try {
			Instruction actualInstruction = InstructionParser.convertToInstruction("Jne label 35");
			Instruction desiredInstruction = new JumpNotEqualsInstruction("label 35");
			assertInstructionsEqual(desiredInstruction, actualInstruction);
		} catch (InstructionParseException e) {
			fail();
		}
	}

	@Test
	public void testConvertToInstructionJneIncomplete() {
		try {
			InstructionParser.convertToInstruction("jne");
			fail();
		} catch (InstructionParseException e) {
			// PASS
		}
	}



	// MARK: Jl
	@Test
	public void testConvertToInstructionJlNormal() {
		try {
			Instruction actualInstruction = InstructionParser.convertToInstruction("jl label");
			Instruction desiredInstruction = new JumpLessInstruction("label");
			assertInstructionsEqual(desiredInstruction, actualInstruction);
		} catch (InstructionParseException e) {
			fail();
		}
	}

	@Test
	public void testConvertToInstructionJlBadlyFormatted() {
		try {
			Instruction actualInstruction = InstructionParser.convertToInstruction("jL label 35");
			Instruction desiredInstruction = new JumpLessInstruction("label 35");
			assertInstructionsEqual(desiredInstruction, actualInstruction);
		} catch (InstructionParseException e) {
			fail();
		}
	}

	@Test
	public void testConvertToInstructionJlIncomplete() {
		try {
			InstructionParser.convertToInstruction("jl");
			fail();
		} catch (InstructionParseException e) {
			// PASS
		}
	}



	// MARK: Jg
	@Test
	public void testConvertToInstructionJgNormal() {
		try {
			Instruction actualInstruction = InstructionParser.convertToInstruction("jg label");
			Instruction desiredInstruction = new JumpGreaterInstruction("label");
			assertInstructionsEqual(desiredInstruction, actualInstruction);
		} catch (InstructionParseException e) {
			fail();
		}
	}

	@Test
	public void testConvertToInstructionJgBadlyFormatted() {
		try {
			Instruction actualInstruction = InstructionParser.convertToInstruction("JG label 35");
			Instruction desiredInstruction = new JumpGreaterInstruction("label 35");
			assertInstructionsEqual(desiredInstruction, actualInstruction);
		} catch (InstructionParseException e) {
			fail();
		}
	}

	@Test
	public void testConvertToInstructionJgIncomplete() {
		try {
			InstructionParser.convertToInstruction("jg");
			fail();
		} catch (InstructionParseException e) {
			// PASS
		}
	}



	// MARK: Label
	@Test
	public void testConvertToInstructionLabelNormal() {
		try {
			Instruction actualInstruction = InstructionParser.convertToInstruction("text:");
			Instruction desiredInstruction = new Label("text");
			assertInstructionsEqual(desiredInstruction, actualInstruction);
		} catch (InstructionParseException e) {
			fail();
		}
	}

	@Test
	public void testConvertToInstructionLabelBadlyFormatted() {
		try {
			Instruction actualInstruction = InstructionParser.convertToInstruction("long label: stuff after");
			Instruction desiredInstruction = new Label("long label");
			assertInstructionsEqual(desiredInstruction, actualInstruction);
		} catch (InstructionParseException e) {
			fail();
		}
	}

	@Test
	public void testConvertToInstructionLabelIncomplete() {
		try {
			InstructionParser.convertToInstruction("text");
			fail();
		} catch (InstructionParseException e) {
			// PASS
		}
	}



	// MARK: Constants Errors
	@Test
	public void testConvertToBinaryInstructionWrongType() {
		try {
			InstructionParser.convertToBinaryInstruction("neg r1 r2");
			fail();
		} catch (InstructionParseException e) {
			// PASS
		}
	}

	@Test
	public void testConvertToUnaryInstructionWrongType() {
		try {
			InstructionParser.convertToUnaryInstruction("or r3");
			fail();
		} catch (InstructionParseException e) {
			// PASS
		}
	}

	@Test
	public void testConvertToJumpInstructionWrongType() {
		try {
			InstructionParser.convertToJumpInstruction("inc label");
			fail();
		} catch (InstructionParseException e) {
			// PASS
		}
	}
}
