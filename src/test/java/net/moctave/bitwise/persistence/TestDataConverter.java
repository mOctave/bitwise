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

package net.moctave.bitwise.persistence;

import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import net.moctave.bitwise.exceptions.InstructionParseException;
import net.moctave.bitwise.exceptions.LabelNotFoundException;
import net.moctave.bitwise.model.Computer;
import net.moctave.bitwise.model.instructions.*;

public class TestDataConverter {

	private Computer computer;
	private JSONObject object;

	public void initEmpty() {
		computer = new Computer();

		object = new JSONObject();
		object.put("version", 2);
		object.put("opCode", 0);
		object.put("fnCode", 0);
		object.put("regA", 0);
		object.put("regB", 0);
		object.put("regWrite", 0);
		object.put("valA", 0);
		object.put("valB", 0);
		object.put("valC", 0);
		object.put("valWrite", 0);
		object.put("programCounter", 0);
		object.put("nextProgramCounter", 0);
		object.put("flagZ", 0);
		object.put("flagN", 0);
		object.put("flagO", 0);
		object.put("instructions", new JSONArray());
		object.put("nextStep", -1);
		object.put("registers", initRegisterArray(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0));
	}

	public void initNonEmpty() {
		computer = new Computer();
		List<Instruction> instructions = computer.getInstructions();
		instructions.add(new MoveInstruction(1, 6));
		instructions.add(new IncInstruction(1));
		instructions.add(new MoveInstruction(2, 4));
		instructions.add(new MoveInstruction(3, 1));
		instructions.add(new Label("loop"));
		instructions.add(new AddInstruction(1, 2));
		instructions.add(new SubInstruction(2, 3));
		instructions.add(new JumpGreaterEqualsInstruction("loop"));
		instructions.add(new HaltInstruction());
		try {
			computer.step();
			computer.step();
		} catch (LabelNotFoundException e) {
			fail();
		}


		JSONArray nonEmptyInstructions = new JSONArray();
		nonEmptyInstructions.put("move r1 6");
		nonEmptyInstructions.put("inc r1");
		nonEmptyInstructions.put("move r2 4");
		nonEmptyInstructions.put("move r3 1");
		nonEmptyInstructions.put("loop:");
		nonEmptyInstructions.put("add r1 r2");
		nonEmptyInstructions.put("sub r2 r3");
		nonEmptyInstructions.put("jge loop");
		nonEmptyInstructions.put("halt");

		object = new JSONObject();
		object.put("version", 2);
		object.put("opCode", 1);
		object.put("fnCode", 0);
		object.put("regA", 1);
		object.put("regB", 0);
		object.put("regWrite", 1);
		object.put("valA", 0);
		object.put("valB", 0);
		object.put("valC", 6);
		object.put("valWrite", 6);
		object.put("programCounter", 0);
		object.put("nextProgramCounter", 5);
		object.put("flagZ", 0);
		object.put("flagN", 0);
		object.put("flagO", 0);
		object.put("instructions", nonEmptyInstructions);
		object.put("nextStep", 1);
		object.put("registers", initRegisterArray(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0));
	}

	public void assertComputersEqual(Computer desiredComputer, Computer actualComputer) {
		assertEquals(desiredComputer.getOpCode().getValue(), actualComputer.getOpCode().getValue());
		assertEquals(desiredComputer.getFnCode().getValue(), actualComputer.getFnCode().getValue());
		assertEquals(desiredComputer.getRegA().getValue(), actualComputer.getRegA().getValue());
		assertEquals(desiredComputer.getRegB().getValue(), actualComputer.getRegB().getValue());
		assertEquals(desiredComputer.getRegWrite().getValue(), actualComputer.getRegWrite().getValue());
		assertEquals(desiredComputer.getValA().getValue(), actualComputer.getValA().getValue());
		assertEquals(desiredComputer.getValB().getValue(), actualComputer.getValB().getValue());
		assertEquals(desiredComputer.getValC().getValue(), actualComputer.getValC().getValue());
		assertEquals(desiredComputer.getValWrite().getValue(), actualComputer.getValWrite().getValue());
		assertEquals(desiredComputer.getProgramCounter().getValue(), actualComputer.getProgramCounter().getValue());
		assertEquals(desiredComputer.getNextProgramCounter().getValue(), actualComputer.getNextProgramCounter().getValue());
		assertEquals(desiredComputer.getFlagZ().getValue(), actualComputer.getFlagZ().getValue());
		assertEquals(desiredComputer.getFlagN().getValue(), actualComputer.getFlagN().getValue());
		assertEquals(desiredComputer.getFlagO().getValue(), actualComputer.getFlagO().getValue());
		assertEquals(desiredComputer.getInstructions().size(), actualComputer.getInstructions().size());
		for (int i = 0; i < desiredComputer.getInstructions().size(); i++) {
			assertEquals(desiredComputer.getInstructions().get(i).toString(),
					actualComputer.getInstructions().get(i).toString());
		}
		assertEquals(desiredComputer.getNextStep(), actualComputer.getNextStep());
		for (int i = 0; i < 15; i++) {
			assertEquals(desiredComputer.getRegisters()[i].getValue(), actualComputer.getRegisters()[i].getValue());
		}
	}


	public JSONArray initRegisterArray(int r1, int r2, int r3, int r4, int r5, int r6, int r7, int r8, int r9, int rA,
			int rB, int rC, int rD, int rE, int rF) {
		JSONArray array = new JSONArray();
		array.put(r1);
		array.put(r2);
		array.put(r3);
		array.put(r4);
		array.put(r5);
		array.put(r6);
		array.put(r7);
		array.put(r8);
		array.put(r9);
		array.put(rA);
		array.put(rB);
		array.put(rC);
		array.put(rD);
		array.put(rE);
		array.put(rF);
		return array;
	}



	public void assertRegisterValuesEqual(Computer computer, int r1, int r2, int r3, int r4, int r5, int r6, int r7,
			int r8, int r9, int rA, int rB, int rC, int rD, int rE, int rF)
	{
		assertEquals(r1, computer.getRegisters()[0].getValue());
		assertEquals(r2, computer.getRegisters()[1].getValue());
		assertEquals(r3, computer.getRegisters()[2].getValue());
		assertEquals(r4, computer.getRegisters()[3].getValue());
		assertEquals(r5, computer.getRegisters()[4].getValue());
		assertEquals(r6, computer.getRegisters()[5].getValue());
		assertEquals(r7, computer.getRegisters()[6].getValue());
		assertEquals(r8, computer.getRegisters()[7].getValue());
		assertEquals(r9, computer.getRegisters()[8].getValue());
		assertEquals(rA, computer.getRegisters()[9].getValue());
		assertEquals(rB, computer.getRegisters()[10].getValue());
		assertEquals(rC, computer.getRegisters()[11].getValue());
		assertEquals(rD, computer.getRegisters()[12].getValue());
		assertEquals(rE, computer.getRegisters()[13].getValue());
		assertEquals(rF, computer.getRegisters()[14].getValue());
	}


	@Test
	public void testSerializeEmpty() {
		initEmpty();
		JSONObject actualResult = DataConverter.serialize(computer);
		assertEquals(object.toString(), actualResult.toString());
	}

	@Test
	public void testSerializeNonEmpty() {
		initNonEmpty();
		JSONObject actualResult = DataConverter.serialize(computer);
		assertEquals(object.toString(), actualResult.toString());
	}


	@Test
	public void testDeserializeEmpty() {
		initEmpty();
		try {
			Computer actualResult = DataConverter.deserialize(object);
			assertComputersEqual(computer, actualResult);
		} catch (InstructionParseException e) {
			fail();
		}
	}


	@Test
	public void testDeserializeNonEmpty() {
		initNonEmpty();
		try {
			Computer actualResult = DataConverter.deserialize(object);
			assertComputersEqual(computer, actualResult);
		} catch (InstructionParseException e) {
			fail();
		}
	}
}
