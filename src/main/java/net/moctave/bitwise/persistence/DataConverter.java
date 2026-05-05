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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jspecify.annotations.NonNull;

import net.moctave.bitwise.exceptions.InstructionParseException;
import net.moctave.bitwise.model.Computer;
import net.moctave.bitwise.model.InstructionParser;
import net.moctave.bitwise.model.Register;
import net.moctave.bitwise.model.instructions.Instruction;

/** A class of helper methods to convert between a program state and JSON. */
public abstract class DataConverter {
	// MARK: Methods
	/**
	 * Converts all the data stored in the computer to a single JSON
	 * object ready to be written to a file.
	 * 
	 * @param computer the computer to convert to a JSON object
	 * @return the corresponding JSON object
	 */
	public static @NonNull JSONObject serialize(@NonNull Computer computer) {
		final JSONObject object = new JSONObject();

		object.put("version", 2);
		object.put("opCode", computer.getOpCode().getValue());
		object.put("fnCode", computer.getFnCode().getValue());
		object.put("regA", computer.getRegA().getValue());
		object.put("regB", computer.getRegB().getValue());
		object.put("regWrite", computer.getRegWrite().getValue());
		object.put("valA", computer.getValA().getValue());
		object.put("valB", computer.getValB().getValue());
		object.put("valC", computer.getValC().getValue());
		object.put("valWrite", computer.getValWrite().getValue());
		object.put("programCounter", computer.getProgramCounter().getValue());
		object.put("nextProgramCounter", computer.getNextProgramCounter().getValue());
		object.put("flagZ", computer.getFlagZ().getValue());
		object.put("flagN", computer.getFlagN().getValue());
		object.put("flagO", computer.getFlagO().getValue());
		object.put("instructions", new JSONArray(computer.getInstructions()
				.stream().map(Instruction::toString).collect(Collectors.toList())));
		object.put("nextStep", computer.getNextStep());
		object.put("registers", new JSONArray(Arrays.asList(computer.getRegisters())
				.stream().map(Register::getValue).collect(Collectors.toList())));

		return object;
	}


	/**
	 * Converts the given JSON object back into a computer.
	 * 
	 * @param object the original JSON object
	 * @return the deserialized computer state
	 * @throws InstructionParseException if an error is encountered parsing instructions
	 */
	public static @NonNull Computer deserialize(@NonNull JSONObject object) throws InstructionParseException {
		int fileVersion = 1;
		try {
			fileVersion = object.getInt("version");
		} catch (JSONException e) {
			// The original file format (version 1), had no version key pair. However, it can be parsed
			// just like the version 2 format.
		}

		if (fileVersion == 1 || fileVersion == 2) {
			final List<Instruction> instructions = deserializeInstructions(object.getJSONArray("instructions"));

			final List<Integer> registers = object.getJSONArray("registers").toList().stream()
					.map(r -> (int) r).collect(Collectors.toList());

			return new Computer(object.getInt("opCode"), object.getInt("fnCode"), object.getInt("regA"),
					object.getInt("regB"), object.getInt("regWrite"), object.getInt("valA"), object.getInt("valB"),
					object.getInt("valC"), object.getInt("valWrite"), object.getInt("programCounter"),
					object.getInt("nextProgramCounter"), object.getInt("flagZ"), object.getInt("flagN"),
					object.getInt("flagO"), instructions, object.getInt("nextStep"), registers);
		}

		// TODO: Handle this better later
		return null;
	}


	/**
	 * Converts the given JSON array to a list of instructions.
	 * 
	 * @param array the original JSON array
	 * @return the deserialized instruction list
	 * @throws InstructionParseException if an error is encountered parsing an instruction
	 */
	private static @NonNull List<Instruction> deserializeInstructions(@NonNull JSONArray array)
			throws InstructionParseException {
		final List<Instruction> instructions = new ArrayList<>();
		for (Object obj : array) {
			instructions.add(InstructionParser.convertToInstruction((String) obj));
		}
		return instructions;
	}
}
